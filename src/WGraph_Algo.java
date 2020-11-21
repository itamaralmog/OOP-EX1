package ex1.src;

import java.io.*;
import java.util.*;

public class WGraph_Algo implements weighted_graph_algorithms,Serializable{
    private WGraph_DS g;
    public WGraph_Algo(){
        g=new WGraph_DS();
    }
    @Override
    public void init(weighted_graph o) {
        this.g=(WGraph_DS)o;
    }

    @Override
    public weighted_graph getGraph() {
        return g;
    }

    @Override
    public weighted_graph copy() {
        if(this!=null) {
            return new WGraph_DS(g);
        }
        return null;
    }

    @Override
    public boolean isConnected() {
        if(g.nodeSize()==1||g.nodeSize()==0) return true;
        Iterator<node_info> itrNodes= g.getV().iterator();//Need to run on the collection with iterator.
        node_info src=itrNodes.next();// Need to start from a node.
        while(itrNodes.hasNext()) {// O(node.size);
            node_info v = itrNodes.next();
            v.setTag(0); // to make the tag zero because that I need the tag.
        }
        int count=0;
        LinkedList<node_info> nodesQ = new LinkedList<>();//Here will add all the Neighbors list
        nodesQ.add(src);
        src.setTag(0);
        while (!nodesQ.isEmpty()) { //O(node.size);
            node_info temporary = nodesQ.poll();// That poll actually pulls one node from the intersection but also deletes it from there
            for (node_info neighbor : g.getV(temporary.getKey())) {//This is where we go over and over every node we pull. O(ni.size()).
                if (neighbor.getTag() == 0) {
                    neighbor.setTag(1); //So as not to go over it again because of the condition.
                    nodesQ.add(neighbor);//Add to neighbors list the neighbors of this node
                    ++count;
                }
            }
        }
        return count==g.nodeSize();
    }


    @Override
    public double shortestPathDist(int src, int dest) {
        if(g.getNode(src)==null||g.getNode(dest)==null) return -1;
        if(g.getV().size()==0||g.getV().size()==1) return 0;
        if(src==dest) return 0;
        WGraph_DS.NodeInfo destN=(WGraph_DS.NodeInfo)g.getNode(dest);
        //This priority queue whose priority is realized in the class NodeCompareForQueue
        PriorityQueue<WGraph_DS.NodeInfo> pQ=new PriorityQueue<>(g.getV().size(),new NodeCompareForQueue());
        HashSet<WGraph_DS.NodeInfo> vis= new HashSet<>();//HashSet for visited nodes
        Iterator<node_info> upDist=g.getV().iterator();//Need to run on the collection with iterator.
        while (upDist.hasNext()){
            node_info n=upDist.next();
            n.setTag(Double.MAX_VALUE);//Turn the tag into infinity so it's safe to get a smaller distance into it
        }
        WGraph_DS.NodeInfo source=(WGraph_DS.NodeInfo)g.getNode(src);
        source.setTag(0);
        pQ.add(source);
        while (!pQ.isEmpty()){//When the priority queue is not yet empty - (O(nodeSize()).
            //Pull nodes from the queue that is to make them the current node and delete from the queue
            WGraph_DS.NodeInfo current=pQ.poll();
            if(!vis.contains(current)) {
                vis.add(current);

                if(current==destN) return destN.getTag();
                //Just a casting
                HashSet<WGraph_DS.NodeInfo> cast = new HashSet<>();
                for (node_info i : g.getV(current.getKey())) { //(O(neighbors.size))
                    WGraph_DS.NodeInfo castI = (WGraph_DS.NodeInfo) i;
                    cast.add(castI);
                }
                //Takes the nodes and puts them in the distance switch if it is smaller than the previous distance
                for (WGraph_DS.NodeInfo i : cast) {//(O(neighbors.size))
                    if (!vis.contains(i)) {
                        //The distance is actually the tag of the current node and the weight of the edge
                        if (current.getTag() + i.getWeight(current.getKey()) < i.getTag()) {
                            i.setTag(current.getTag() + i.getWeight(current.getKey()));
                            pQ.add(i);
                        }
                    }
                }
            }
        }
        return -1;
    }

    @Override
    //The basis of this function is similar to the last one, only something different needs to be returned here.
    public List<node_info> shortestPath(int src, int dest)
    {
        if(g.getNode(src)==null||g.getNode(dest)==null) { return null;}
        if(g.getV().size()==0) return null;
        if(src==dest) {
            ArrayList<node_info> s= new ArrayList<>();
            s.add(g.getNode(src));
            return s;
        }
        WGraph_DS.NodeInfo destN=(WGraph_DS.NodeInfo)g.getNode(dest);
        PriorityQueue<WGraph_DS.NodeInfo> pQ=new PriorityQueue<>(g.getV().size(),new NodeCompareForQueue());
        HashSet<WGraph_DS.NodeInfo> vis= new HashSet<>();
        //So that a list of nodes can be returned, then we'll mark who the node ordered.
        HashMap<WGraph_DS.NodeInfo, WGraph_DS.NodeInfo> parent=new HashMap<>();//
        Iterator<node_info> upDist=g.getV().iterator();
        while (upDist.hasNext()){
            node_info n=upDist.next();
            n.setTag(Double.MAX_VALUE);
        }
        node_info c=g.getNode(src);
        WGraph_DS.NodeInfo source=(WGraph_DS.NodeInfo) c;
        source.setTag(0);
        pQ.add(source);
        while (!pQ.isEmpty()){
            WGraph_DS.NodeInfo current=pQ.poll();
            if(!vis.contains(current)) {
                vis.add(current);
                if(current==destN){break;}
                HashSet<WGraph_DS.NodeInfo> cast = new HashSet<>();
                for (node_info i : g.getV(current.getKey())) {
                    WGraph_DS.NodeInfo castI = (WGraph_DS.NodeInfo) i;
                    cast.add(castI);
                }
                for (WGraph_DS.NodeInfo i : cast) {
                    if (!vis.contains(i)) {
                        if (current.getTag() + i.getWeight(current.getKey()) < i.getTag()) {
                            i.setTag(current.getTag() + i.getWeight(current.getKey()));
                            //In order to be able to accurately identify how to arrive in the short form the
                            // destination needs to delete everyone who has reached the destination before
                            if((i.getKey()==dest)){parent.remove(i); }
                            //You put the current node as the parent of his neighbor.
                            parent.put(i, current);
                            pQ.add(i);
                        }
                    }
                }
            }
        }
        //You have to return a list, so this is where it's built.
        node_info node = g.getNode(dest);
        WGraph_DS.NodeInfo nH =(WGraph_DS.NodeInfo) node;
        if(!parent.containsKey(nH)) return null;
        ArrayList<node_info> s= new ArrayList<>();
        s.add(node);
        node_info n=g.getNode(nH.getKey());
        while(n.getKey()!=src){
            nH=parent.get(nH);
            n=g.getNode(nH.getKey());
            s.add(n);
        }
        Collections.reverse(s);
        return s;
    }

    @Override
    public boolean save(String file) {
        //Function to save a file and if there is an error in the procedure that will catch it
        try {
            ObjectOutputStream objOutPut = new ObjectOutputStream(new FileOutputStream(file));
            objOutPut.writeObject(this.g);
            objOutPut.close();
            return true;
        }
        catch (IOException e) {}
        return false;
    }

    @Override
    public boolean load(String file) {
        //Function to load a file and if there is an error in the procedure that will catch it
        try {
            ObjectInputStream objectInput = new ObjectInputStream(new FileInputStream(file));
            g = (WGraph_DS) objectInput.readObject();
            objectInput.close();
            return true;
        }
        catch (IOException | ClassNotFoundException e) {}
        return false;
    }
}
