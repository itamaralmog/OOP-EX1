1. file list:
    1.1 src file:
    node_info interface
    weighted_graph interface
    weighted_graph_algorithms interface
    WGraph_DS- implements weighted_graph interface and as an internal class implements node_info 
    WGraph_Algo- implements weighted_graph_algorithms interface
    NodeCompareForQueue
    
    1.2 tests file:
    WGraphATest
    WGraphDSTEST
 
2. classes 
    2.1 class WGraph_DS 
    This class is the implements of the interface weighted_graph and as an internal class implements node_info.
    In this class, I have implemented several Int variables and two data structures of type HashMap.
    This class is actually creating a weighted graph using the internal class.  
    
    2.2  internal class NodeInfo implements node_info
    This class redeems the nodes in the graph along with weight on each edge.
    This class has all the functions that help the external class work.
    
    2.3 class WGraph_Algo
    This department implements weighted graph algorithms
    This class has functions that check whether a graph is binding to the shortest distance between nodes, and a list of nodes
    that pass through them at this distance.
    The distance is basically the weights of each edge between a particular node and another.
    
3. Why HashMap data structure?
    I chose this data structure because it has very good running times and has a key and value.
    To remove/get/put from it this O(1).
    Because it has value, and a key you can do a lot of functions with good efficiency that get either the key or the value.
    The value and key can be anything both of which can be objects or variables or one variable and the other 
    object and all that makes this data structure very convenient.
    I used this data structure in all class because of this convenience.
    
4.How I implemented the priority queue?
     I did a class called NodeCompareForQueue I'll explain her use now.
     This class is used to prioritize nodes by tag.
     I tried to do a comparison function for the priority queue, but I couldn't help but do another class.

5.Complexity of functions- except from functions with O(1) Complexity.
    5.1 Complexity of functions in WGraph_DS
    1. public WGraph_DS(WGraph_DS g)- It's a copying constructor with the complexity of the size of the nodes or edges
    (The bigger of the two, of course).(O(edgeSize())||(O(nodeSize()))
    2. public Collection<node_info> getV(int node_id)- The complication is the number of neighbors to have for the node.(O(neighbors.size))
    3. public node_info removeNode(int key)- The reasoning is the number of neighbors to node because of the use of the function
    removeNodeAndWeightTotal().(O(neighbors.size))   
    4. public boolean equals(Object g) -It's equals function with the complexity of the size of the nodes or edges
    (The bigger of the two, of course).(O(edgeSize())||(O(nodeSize()))
    5. public String toString() - Complexity of (O(edgeSize())||(O(nodeSize())) The bigger of the two, of course.
    5.2 Complexity of functions in the internal class NodeInfo
    1. public NodeInfo(int key,double tag,String info) -Constructor that generates a different key for each node if necessary The reason is the number of nodes.
    2. public void removeNodeAndWeightTotal() - Function with complexity of the number of neighbors (O(neighbors.size)).
    3.public boolean equals(Object n) - Function with complexity of the number of neighbors (O(neighbors.size)).
    5.3 Complexity of functions in WGraph_Algo
    1. public boolean isConnected() - (O(edgeSize())||(O(nodeSize())).
    2. public weighted_graph copy() - public WGraph_DS(WGraph_DS g) Complexity. 
    3. public double shortestPathDist(int src, int dest) - (O(edgeSize())||(O(nodeSize())).
    4. public List<node_info> shortestPath(int src, int dest) - The same complexity as the last one is very similar.
    5. 
    6.
    
6.  test-run:
    To run the project you must press the pointy green button to the left of each class head of test.
    When you click it, a pane appears, and at the top of it will be a run, and the name of the class to click.