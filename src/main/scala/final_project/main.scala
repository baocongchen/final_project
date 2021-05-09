package final_project

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.graphx._
import org.apache.spark.storage.StorageLevel
import org.apache.log4j.{Level, Logger}

object main{
  val rootLogger = Logger.getRootLogger()
  rootLogger.setLevel(Level.ERROR)

  Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
  Logger.getLogger("org.spark-project").setLevel(Level.WARN)

  def MyMIS(g_in: Graph[Int, Int]): Graph[Int, Int] = {
    val r = scala.util.Random;
    var remaining_vertices = 2;
    var loop = 0;
    var g = g_in.mapVertices((id, vd) => (1.asInstanceOf[Int]));
    while (remaining_vertices > 1) {
        loop = loop + 1;
        g = g.mapVertices((id, vd) => (vd));
        val v = g.aggregateMessages[(Int, Int)](
        //make a random proposal
                triplet => {
                  triplet.sendToDst(if (triplet.srcAttr == 1) (triplet.srcId.toInt,1) else (0,0));
                  triplet.sendToSrc(if (triplet.dstAttr == 1) (triplet.dstId.toInt,1) else (0,0))
                },
                //accept a proposal at probability = x/(x+y)
                (x, y) => if (r.nextFloat() < (x._2 / (x._2 + y._2).asInstanceOf[Float])) (x._1, y._2 + y._2) else (y._1, x._2 + y._2)
              );
        val g1 = Graph(v, g.edges).mapVertices((id,vd) => (vd._1, if (r.nextFloat >= 0.5) 0 else 1)); //randomly assign 0 or 1 to each vertex
        
        val v2 = g1.aggregateMessages[Int](
        // edge from u to v that satisfies conditions will join M
            triplet => {
                triplet.sendToDst(if (triplet.dstId == triplet.srcAttr._1 && triplet.srcAttr._2 == 1 && triplet.dstAttr._2 == 0) triplet.srcId.toInt else 0);
                triplet.sendToSrc(if (triplet.srcId == triplet.dstAttr._1 && triplet.dstAttr._2 == 1 && triplet.srcAttr._2 == 0) triplet.dstId.toInt else 0)
              },
              (x, y) => if (x > y) x else y //choose a max/non-zero value 
            );
        // deactivate u and v after they're added to M   
        g = Graph(v2, g.edges).mapTriplets(triplet => if (triplet.srcId == triplet.srcAttr) 0 else 1);
        remaining_vertices = g.vertices.filter({ case (id, x) => (x == 1) }).count().toInt;
        g.cache();
        
        println("Remaining vertices: " + remaining_vertices);
    }
    println("The algorithm completed in " + loop + " loops");
    
    return g.subgraph(vpred = (id, x) => x != 0);
  }

  def main(args: Array[String]) {

    val conf = new SparkConf().setAppName("final")
    val sc = new SparkContext(conf)
    val spark = SparkSession.builder.config(conf).getOrCreate()
/* You can either use sc or spark */

    if(args.length == 0) {
      println("Usage: final option = {compute, verify}")
      sys.exit(1)
    }
    if(args(0)=="compute") {
      if(args.length != 3) {
        println("Usage: final_project compute graph_path output_path")
        sys.exit(1)
      }
      val startTimeMillis = System.currentTimeMillis()
      //val edges = sc.textFile(args(1)).map(line => {val x = line.split(","); Edge(x(0).toLong, x(1).toLong , 1)} )
      val edges = sc.textFile(args(1)).map(line => {val x = line.split(","); Edge(x(0).toLong, x(1).toLong , 1)} ).filter({case Edge(a,b,c) => a!=b})
      val g = Graph.fromEdges[Int, Int](edges, 0, edgeStorageLevel = StorageLevel.MEMORY_AND_DISK, vertexStorageLevel = StorageLevel.MEMORY_AND_DISK)
      val g2 = MyMIS(g)

      val endTimeMillis = System.currentTimeMillis()
      val durationSeconds = (endTimeMillis - startTimeMillis) / 1000
      println("==================================")
      println("The algorithm completed in " + durationSeconds + "s.")
      println("==================================")

      val g2df = spark.createDataFrame(g2.vertices)
      g2df.coalesce(1).write.format("csv").mode("overwrite").save(args(2))
    }
    
    else
    {
        println("Usage: final_project option = {compute}")
        sys.exit(1)
    }
  }
}



