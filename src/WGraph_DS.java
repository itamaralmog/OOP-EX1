package ex1.src;

import java.io.Serializable;
import java.util.*;
public class WGraph_DS implements weighted_graph,Serializable {
    private int numberKey=0;
    private HashSet<Integer> chekUnique=new HashSet<>();
    private HashMap<Integer, node_info> nodesWG= new HashMap<>();
    private int edges;
    private int MC;
    private int kS;
    //Constructor with no arguments
    public WGraph_DS(){
        this.nodesWG = new HashMap<>();
        this.chekUnique=new HashSet<Integer>();
        this.edges=0;
        this.MC=0;
        numberKey=0;
        kS=0;
    }
    //Copy constructor
    public WGraph_DS(WGraph_DS g){
        if(g==null){
            nodesWG = new HashMap<>();
            chekUnique = new HashSet<>();
            edges = 0;
            MC = 0;
            numberKey = 0;
            kS = 0;
        }
        else{
            edges=g.edges;
            MC=g.MC;
            numberKey=g.numberKey;
            kS=g.kS;
            // Copies the nodes and their neighbors and weights
            for(node_info n:g.nodesWG.values()){ //(O(g.nodesWG.size))
                NodeInfo ni= new NodeInfo(n.getKey(),n.getTag(),n.getInfo());
                nodesWG.put(ni.getKey(), ni);
            }
            for (node_info n:g.nodesWG.values()) {//(O(g.nodesWG.size))
               NodeInfo t=(NodeInfo)n;
               NodeInfo tN=(NodeInfo) nodesWG.get(t.getKey());
               for(node_info nei: t.getNeighbors().values()){//(O(t.getNeighbors().size))
                   NodeInfo node =(NodeInfo) nodesWG.get(nei.getKey());
                   if(!tN.hasNi(node)){
                       tN.addNi(node,t.getWeight(node.getKey()));
                       //Copying the weights and the neighbors themselves is done here with addNi function
                   }
               }
            }
            //O(g.edgeSize())
            //Copying keys for uniqueness check
            Iterator<Integer> keys= g.chekUnique.iterator();
            while(keys.hasNext()){
                int n= keys.next();
                chekUnique.add(n);
            }
        }
    }
    @Override //Get this node fom the graph
    public node_info getNode(int key) { return nodesWG.get(key);}
    @Override//Chek if has edge between that nodes
    public boolean hasEdge(int node1, int node2) {
        if(getNode(node1)==null||getNode(node2)==null)
        return false;
        NodeInfo n1 =(NodeInfo)getNode(node1);
       return n1.hasNi(getNode(node2));
    }
    @Override//Get the Weight on the edge
    public double getEdge(int node1, int node2) {
       NodeInfo n1=(NodeInfo)getNode(node1);
       return n1.getWeight(node2);
    }
    @Override//To add to the graph
    public void addNode(int key) {
        NodeInfo n=new NodeInfo(key,0,"");
        nodesWG.put(n.getKey(),n);
        MC++;
    }
    @Override//Put a edge between the nodes
    public void connect(int node1, int node2, double w) {
        if(getNode(node1)==null||getNode(node2)==null) return;
        if(getNode(node1)==getNode(node2)) return;
        node_info n1=nodesWG.get(node1);//O(1)
        node_info n2=nodesWG.get(node2);//O(1)
        NodeInfo n1N=(NodeInfo)n1;//O(1)
        NodeInfo n2N=(NodeInfo)n2;//O(1)
        if(n1N.hasNi(n2)&&n2N.hasNi(n1)) {
            if(n1N.getWeight(node2)!=w){
                n1N.setW(node2,w);//O(1)
                n2N.setW(node1,w);//O(1)
                ++MC;
            }
            return;
        }
        n1N.addNi(n2,w);
        n2N.addNi(n1,w);
        ++MC;
        ++edges;
    }
    @Override//To get a Collection of the graph nodes
    public Collection<node_info> getV() { return nodesWG.values();}

    @Override//To get a Collection of neighbors of node
    public Collection<node_info> getV(int node_id) {
        NodeInfo v = (NodeInfo) getNode(node_id);
        if(v==null) return null;
        HashSet<node_info> nei=new HashSet<>();
        for (node_info i:v.getNeighbors().values()) {//O(v.getNeighbors().size())
            nei.add(i);
        }
        return nei;
    }
    @Override// To remove node from the graph
    public node_info removeNode(int key) {
        if(nodesWG.get(key)==null) return null;
        NodeInfo nN =(NodeInfo)nodesWG.get(key);
        edges-=nN.getNeighbors().size();
        MC+=nN.getNeighbors().size()+1;
        nN.removeNodeAndWeightTotal();//O(nN.getNeighbors().size())
        return nodesWG.remove(key);
    }
    @Override// To remove edge from the graph
    public void removeEdge(int node1, int node2) {
        if(getNode(node1)==null||getNode(node2)==null) return;
        if(getNode(node1)==getNode(node2)) return;
        node_info n1=nodesWG.get(node1);//O(1)
        node_info n2=nodesWG.get(node2);//O(1)
        NodeInfo n1N=(NodeInfo)n1;//O(1)
        NodeInfo n2N=(NodeInfo)n2;//O(1)
        if(n1N.hasNi(n2)&&n2N.hasNi(n1)){
            n1N.removeNi(n2);//O(1)
            n2N.removeNi(n1);//O(1)
            --edges;
            ++MC;
        }
    }
    @Override//
    public int nodeSize() {return nodesWG.size();}
    @Override//
    public int edgeSize() {return edges; }
    @Override//
    public int getMC() {return MC; }
    @Override//Part of checking whether two graphs are equal
    public boolean equals(Object g){
        WGraph_DS o=(WGraph_DS)g;
        if(this.edgeSize()!=o.edgeSize()||this.getV().size()!=o.getV().size()) return false;
        //Compare the node keys on each graph
        for(node_info i: o.getV()){//(O(o.getV().size())
            NodeInfo n = (NodeInfo)i;
            if(!this.getV().contains(i)) return false;
            if(!n.equals(this.nodesWG.get(i.getKey())))return false;
        }
        //Compare the contents of nodes
        for(node_info i: this.getV()){//(O(this.getV().size())
            NodeInfo n = (NodeInfo)i;
            if(!o.getV().contains(i)) return false;
            NodeInfo nN= (NodeInfo)o.nodesWG.get(i.getKey());
            if(!n.equals(nN))return false;
        }
        return true;
    }
    //Turns the graph into a string
    public String toString(){
        String s="";
        for(node_info n: getV()){//O(getV().size())
            NodeInfo temp1=(NodeInfo)n;
            s+=temp1.toSting()+"] ";
            for(node_info i:getV(temp1.getKey())){//O(getV(key).size())
                NodeInfo t=(NodeInfo)i;
                s+=t.toSting()+"("+t.getWeight(n.getKey())+")]";
            }
            s+="\n";
        }
        return s;
    }
    public class NodeInfo implements node_info,Serializable{
        private int key;
        private double tag;
        private String info;
        private HashMap<Integer, node_info> ni;
        private HashMap<Integer, Double> neighborsWeight;

        public NodeInfo(int key,double tag,String info) // Constructor
        {
            //Make sure all nodes have a unique key
            if (chekUnique.contains(key)) {
                while (chekUnique.contains(kS)) { kS++; }
                   key=kS;
            }
            chekUnique.add(key);
            this.setKey(key);
            this.tag=tag;
            this.info=info;
            this.ni = new HashMap<>();
            this.neighborsWeight= new HashMap<>();
            ++numberKey;
            kS=0;
        }

        //This function is designed to return the neighbors so that a change can be made
        public HashMap<Integer,node_info> getNeighbors(){ return ni; }//O(1)

        //This function is designed to regain the weights of the neighbors so that a change can be made
        public HashMap<Integer,Double> getNeighborsWeight(){return neighborsWeight; }//O(1)

        @Override
        public int getKey() { return this.key; }//O(1)

        private void setKey(int n){ key=n;}//O(1)

        @Override
        public String getInfo() { return this.info;}//O(1)

        @Override
        public void setInfo(String s) {info=s;}//O(1)

        @Override
        public double getTag() { return this.tag; }//O(1)

        @Override
        public void setTag(double t) { tag=t; }//O(1)

        //This function is designed to insert a neighbor and its weight into the data structures
        public void addNi(node_info n,double w){
                this.ni.put(n.getKey(),n);//O(1)
                this.neighborsWeight.put(n.getKey(),w);//O(1)
        }

        //This function is designed to erase a neighbor and its weight from the data structures
        public void removeNi(node_info n){
                ni.remove(n.getKey(),n);//O(1)
                neighborsWeight.remove(n.getKey());//O(1)
        }

        //See if the node has this neighbor.
        public boolean hasNi(node_info n){ return ni.containsKey(n.getKey());}//O(1)

        public double getWeight(int key){ return this.neighborsWeight.get(key); }//O(1)

        public void setW(int key,double w){ this.neighborsWeight.put(key,w); }//O(1)

        //The function is responsible for deleting a node and all the edges that come out of it
        public void removeNodeAndWeightTotal(){
            for (node_info i: getV(getKey())) {//(O(getV(key).size())
              NodeInfo n = (NodeInfo)i;
              n.removeNi(this);
            }
        }

        @Override//This function compares the content of nodes itself
        public boolean equals(Object n){
            NodeInfo o=(NodeInfo)n;
            if(o.getKey()!=getKey()) return false;
            for(node_info i: o.getNeighbors().values()){//O(getNeighbors().size())
                if(!this.getNeighbors().containsKey(i.getKey())) return false;
                if(this.getWeight(i.getKey())!=o.getWeight(i.getKey())) return false;
            }
            return true;
        }

        //Turns a node into a string
        public String toSting(){ return "[Node("+getKey()+")"; }//O(1)
    }
}
