# Large Scale Data Processing: Final Project
# Report

## Group 3: Buu Thong Tran, Baichuan Guo, Byungmoo Kim


|        Graph file             |  Number of edges  |   Running time  | Num of matchings | Iterations | Computational Power
| ------------------------------| ----------------- |-----------------|--------------    |------------|------------------------------------
| log_normal_100.csv            | 117185083         |        5s       |      22          |  1         | MacOS 1.6 GHz Intel Core i5, 8GB
| musae_ENGB_edges.csv          | 63555749          |        5s       |      1004        |  1         | MacOS 1.6 GHz Intel Core i5, 8GB
| soc-pokec-relationships.csv   | 42851237          |        82s      |      257493      |  1         | N1 series, 1 Master and 4 Nodes, each having 4 CPU and 15GB
| soc-LiveJournal1.csv          | 22301964          |        114s     |      723199      |  1         | N1 series, 1 Master and 4 Nodes, each having 4 CPU and 15GB
| twitter_original_edges.csv    | 35324             |        322s     |      50142       |  1         | N1 series, 1 Master and 4 Nodes, each having 4 CPU and 15GB
| com-orkut.ungraph.csv         | 2671              |        155s     |      513654      |  1         | N1 series, 1 Master and 4 Nodes, each having 4 CPU and 15GB


## Approach
In the beginning, we tried to use Luby's algorithm to solve th problem. However, we encountered technical issues that forced us to switch to Israeli-Itai algorithm. First, we set the value of each vertex to 1. If there are active vertices, each active vertex will send messages to its neighbor. The neighbors accept a proposal at probability of x/(x+y) with x being the value of the vertex, and x+y being the sum of all values of neighboring vertices. We used aggregateMessages to achieve this mechanism. Next, we use mapVertices and the nextFloat function to randomly assign 0 or 1 to each vertex. Next, we add an edge from u to v to M if u and v accept the proprosal and u=0 and v=1. Then, we deactivate u and v by assign a value of 0 to them. The loop will stop once there are no more active vertices. Finally, we use the subgraph function to export only edges in M.

## Discussion
- Advantages:
- Limitations:



## Materials:
- code:
- output files:


