package ex1.tests;

import ex1.src.WGraph_DS;
import ex1.src.*;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class WGraphDSTEST {
    private static Random _rnd = null;
    @Test
    void hasEdge(){
        weighted_graph gW = small_graph();
        WGraph_DS g = (WGraph_DS)gW;
        int [] a={1,2,3,4,5};
        int [] b={0,10,3,0,10};
        boolean ans=true;
        for(int i=0;i<5;i++){
            ans&=g.hasEdge(0, a[i]);
            ans&=g.hasEdge(2, b[i]);
        }
        assertTrue(ans);
        g.removeNode(10);
        for(int i=0;i<5;i++){
            ans&=g.hasEdge(0, a[i]);
            ans&=g.hasEdge(2, b[i]);
        }
        assertFalse(ans);
    }
    @Test
    void getEdge(){
        weighted_graph gW = small_graph();
        WGraph_DS g = (WGraph_DS)gW;
        int [] n0Nei={1,2,3,4,5};
        double [] n0W={1,2,1,3,1};
        for(int i=0;i<5;i++){
            assertEquals(g.getEdge(0,n0Nei[i]), n0W[i]);
        }

    }
    @Test
    void connect(){
        weighted_graph gW = small_graph();
        WGraph_DS g = (WGraph_DS)gW;
        int [] n0Nei={0,1,2,3,4,5,9};
        double [] n0W={345,2,3,2,4,2,123};
        for(int i=0;i<7;i++){
            g.connect(0,n0Nei[i],n0W[i]);
        }
        assertFalse(g.hasEdge(0,0));
        for(int i=1;i<7;i++){
            assertEquals(n0W[i],g.getEdge(0,n0Nei[i]));
        }
    }
    @Test
    void removeNode(){
        weighted_graph gW = small_graph();
        WGraph_DS g = (WGraph_DS)gW;
        g.removeNode(0);
        assertEquals(10,g.edgeSize());
        g.removeNode(0);
        assertEquals(10,g.nodeSize());
        g.removeNode(10);
        assertEquals(6,g.edgeSize());
    }
    @Test
    void addNode(){
        weighted_graph g = new WGraph_DS();
        for(int i = 0; i< 11 ; ++i){
            g.addNode(i);
        }
        for(int i = 0; i< 10 ; ++i){
            g.removeNode(i);
            assertEquals(11-i-1,g.nodeSize());
        }
        System.out.println(g);
        g.addNode(10);
        System.out.println(g);
        assertEquals(2,g.nodeSize());
    }
    @Test
    void removeEdge() {
        weighted_graph gW = small_graph();
        WGraph_DS g = (WGraph_DS) gW;
        System.out.println(g);
        assertEquals(15,g.edgeSize());
        for(int i = 0; i< 17;++i){
            g.removeEdge(i,i+1);
        }
        assertEquals(10,g.edgeSize());
        g.removeEdge(9,3);
        assertNotEquals(10,g.edgeSize());
    }
    @Test
    void equals(){
        weighted_graph gW1 = small_graph();
        WGraph_DS g1 = (WGraph_DS) gW1;
        weighted_graph gW2 = small_graph();
        WGraph_DS g2 = (WGraph_DS) gW2;
        assertTrue(g1.equals(g2));
        g1.removeEdge(1,0);
        assertFalse(g1.equals(g2));
        g1.connect(1,0,4);
        assertFalse(g1.equals(g2));
        g1.connect(1,0,1);
        assertTrue(g1.equals(g2));
    }
    static weighted_graph small_graph() {
        weighted_graph g0=new WGraph_DS();
        for(int i = 0; i< 11 ; ++i){
            g0.addNode(i);
        }
        g0.connect(0,1,1);
        g0.connect(0,2,2);
        g0.connect(0,3,1);
        g0.connect(0,4,3);
        g0.connect(0,5,1);
        g0.connect(5,6,4);
        g0.connect(6,8,3);
        g0.connect(7,6,1);
        g0.connect(4,9,7);
        g0.connect(4,10,6);
        g0.connect(9,10,5);
        g0.connect(3,9,1);
        g0.connect(3,10,8);
        g0.connect(2,3,90);
        g0.connect(2,10,15);
        return g0;
    }
    static weighted_graph small_graph1() {
        weighted_graph g0=new WGraph_DS();
        for(int i = 0; i< 12 ; ++i){
            g0.addNode(i);
        }
        g0.connect(0,1,1);
        g0.connect(0,2,2);
        g0.connect(0,3,1);
        g0.connect(0,4,3);
        g0.connect(0,5,1);
        g0.connect(5,6,4);
        g0.connect(6,8,3);
        g0.connect(7,6,1);
        g0.connect(4,9,7);
        g0.connect(4,10,6);
        g0.connect(9,10,5);
        g0.connect(3,9,1);
        g0.connect(3,10,8);
        g0.connect(2,3,90);
        g0.connect(2,10,15);
        g0.connect(11,2,45);
        return g0;
    }
}
