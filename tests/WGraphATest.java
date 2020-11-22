package ex1.tests;

import ex1.src.node_info;
import ex1.src.*;
import org.junit.jupiter.api.Test;
import ex1.src.WGraph_Algo;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WGraphATest {

    @Test
    void copy()
    {
        weighted_graph gW= WGraphDSTEST.small_graph();
        WGraph_DS g0= (WGraph_DS)gW;
        //System.out.println(g0);
        WGraph_Algo gA0=new WGraph_Algo();
        gA0.init(g0);
        WGraph_DS g1= (WGraph_DS)gA0.copy();
        assertEquals(g0,g1);
        WGraph_DS gN=new WGraph_DS();
        gA0.init(null);
        g1= (WGraph_DS)gA0.copy();
        assertEquals(gN,g1);

    }

    @Test
    void isConnected(){
        WGraph_DS g0=new WGraph_DS();
        for (int i = 2; i <100; i++) {
            g0.addNode(i);
        }
        for (int i = 2; i <100; i++) {
            g0.connect(i,i-2,1);
            g0.connect(i,i-1,1);
            g0.connect(i,i+1,1);
            g0.connect(i,i+2,1);
        }
        WGraph_Algo gA0=new WGraph_Algo();
        gA0.init(g0);
        assertTrue(gA0.isConnected());
        gA0.getGraph().removeNode(50);
        assertTrue(gA0.isConnected());
        gA0.getGraph().removeNode(49);
        gA0.init(g0);
        assertFalse(gA0.isConnected());
    }
    @Test
    void shortestPathDist()  {
        weighted_graph g0=WGraphDSTEST.small_graph();
        weighted_graph_algorithms ag0 = new WGraph_Algo();
        ag0.init(g0);
        int[] a = {7,8,9,6,6,8,12,13,15,5,0};
        for (int i = 0; i <11 ; i++) {
            assertEquals(ag0.shortestPathDist(10,i),a[i]);
        }
        ag0.getGraph().removeNode(0);
        int[] b = {-1,-1,15,6,6,-1,-1,-1,-1,5,0};
        for (int i = 0; i <11 ; i++) {
            assertEquals(ag0.shortestPathDist(10,i),b[i]);
        }
        ag0.getGraph().removeNode(9);
        ag0.getGraph().removeEdge(4,10);
        ag0.getGraph().removeEdge(3,10);
        int[] c = {-1,-1,15,105,-1,-1,-1,-1,-1,-1,0};
        for (int i = 0; i <11 ; i++) {
            assertEquals(ag0.shortestPathDist(10,i),c[i]);
        }
    }

    @Test
    void shortestPath() {
        weighted_graph g0=WGraphDSTEST.small_graph();
        weighted_graph_algorithms ag0 = new WGraph_Algo();
        ag0.init(g0);
        List<node_info> sp = ag0.shortestPath(0,10);
        //System.out.println(sp);
        int[] a={4,3,3,3};
        int[] b={9,3,2,4};
        for (int i = 0; i < 4; i++) {
            assertEquals(a[i],sp.size());
            ag0.getGraph().removeNode(b[i]);
            sp = ag0.shortestPath(0,10);
        }
        assertNull(sp);
    }

    @Test
    void save_load() {
        weighted_graph g0 =  WGraphDSTEST.small_graph();
        weighted_graph_algorithms ag0 = new WGraph_Algo();
        ag0.init(g0);
        String str = "g0.file";
        ag0.save(str);
        weighted_graph g1 =  WGraphDSTEST.small_graph1();
        ag0.init(g1);
        ag0.load(str);
        assertEquals(g0,ag0.getGraph());
        weighted_graph g3 = WGraphDSTEST.small_graph1();
        ag0.init(g1);
        ag0.save(str);
        ag0.load(str);
        assertEquals(g3,ag0.getGraph());
        ag0.getGraph().removeNode(0);
        ag0.save(str);
        ag0.load(str);
        assertNotEquals(g3,ag0.getGraph());
    }

}
