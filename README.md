# Large Scale Data Processing: Final Project
## Group 3: Buu Thong Tran, Baichuan Guo, Byungmoo Kim


|           File name           |        Number of edges       |
| ------------------------------| ---------------------------- |
| com-orkut.ungraph.csv         | 117185083                    |
| twitter_original_edges.csv    | 63555749                     |
| soc-LiveJournal1.csv          | 42851237                     |
| soc-pokec-relationships.csv   | 22301964                     |
| musae_ENGB_edges.csv          | 35324                        |
| log_normal_100.csv            | 2671                         |  

|        Graph file             |        Number of edges       |   Running time  | Num of matchings | Iterations | Computational Power
| ------------------------------| ---------------------------- |-----------------|--------------    |------------|------------------------------------
| log_normal_100.csv            | 117185083                    |        5s       |      22          |  1         | MacOS 1.6 GHz Intel Core i5, 8GB
| musae_ENGB_edges.csv          | 63555749                     |        5s       |      1004        |  1         | MacOS 1.6 GHz Intel Core i5, 8GB
| soc-pokec-relationships.csv   | 42851237                     |        82s      |      257493      |  1         | N1 series, 1 Master and 4 Nodes, each having 4 CPU and 15GB
| soc-LiveJournal1.csv          | 22301964                     |        114s     |      723199      |  1         | N1 series, 1 Master and 4 Nodes, each having 4 CPU and 15GB
| twitter_original_edges.csv    | 35324                        |        322s     |      50142       |  1         | N1 series, 1 Master and 4 Nodes, each having 4 CPU and 15GB
| com-orkut.ungraph.csv         | 2671                         |        155s     |      513654      |  1         | N1 series, 1 Master and 4 Nodes, each having 4 CPU and 15GB


## Approach


## Discussion
- Advantages:
- Limitations:





