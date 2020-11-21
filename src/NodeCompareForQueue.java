package ex1.src;

import java.io.Serializable;
import java.util.Comparator;
public class NodeCompareForQueue implements Comparator<WGraph_DS.NodeInfo>, Serializable {
    @Override
    public int compare(WGraph_DS.NodeInfo o1, WGraph_DS.NodeInfo o2) {
        if(o1.getTag()<o2.getTag()) return -1;
        if(o1.getTag()>o2.getTag()) return 1;
        return 0;
    }
}
