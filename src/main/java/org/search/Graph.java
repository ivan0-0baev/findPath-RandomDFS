package org.search;

import java.util.LinkedList;

public class Graph {

    private int V;
    private int E;
    private LinkedList<Integer> nodeList;
    private LinkedList<Integer>[] adjacency;

    public Graph(int V) {
        if (V < 0) throw new IllegalArgumentException("Number Of Nodes Must Be Over 0");
        this.V = V;
        this.E = 0;
        adjacency = new LinkedList[V];
        for (int i = 0; i < V; i++) {
            adjacency[i] = new LinkedList<Integer>();
        }
        nodeList = new LinkedList<Integer>();
        for (int i = 0; i < V; i++) {
            nodeList.add(i);
        }
    }

    public int V() { return V; }

    public int E() { return E; }

    public LinkedList<Integer> adjacency(int v) { return adjacency[v]; }

    public LinkedList<Integer> getNodeList() { return nodeList; }

    /*
    public void validateNode(int v) {
        if (v < 0 || v > V) {
            throw new IllegalArgumentException("Node does not exist in Graph");
        }
    }
    */
    public boolean validateNode(int v) {
        if (v < 0 || v > V) {
            //throw new IllegalArgumentException("Node does not exist in Graph");
            return false;
        }
        return true;
    }

    public void addEdge(int u, int v) {
        //validateNode(u);
        //validateNode(v);
        if (validateNode(u) && validateNode(v)) {
            E++;
            adjacency[u].add(v);
            adjacency[v].add(u);
        }
    }

    public void removeEdge(int u, int v) {
        //validateNode(u);
        //validateNode(v);
        if (validateNode(u) && validateNode(v)) {
            E--;
            adjacency[u].remove(adjacency[u].indexOf(v));
            adjacency[v].remove(adjacency[v].indexOf(u));
        }
    }

    public void removeNode(Graph G, int v) {
        validateNode(v);

        LinkedList<Integer> temp = (LinkedList) G.adjacency(v).clone();
        for (int adj : temp) {
            if (adj != v) {
                G.removeEdge(v, adj);
            }
        }

        G.nodeList.set(v, 0);

        //V--;
    }
    public void restoreNode(Graph G, int v) {

        G.nodeList.set(v, v);

        int above = (int)(v-Math.sqrt(V));
        G.nodeList.contains(above);
        G.addEdge(v, above);

        int bellow = (int)(v+Math.sqrt(V));
        G.nodeList.contains(bellow);
        G.addEdge(v, bellow);

        int right = v+1;
        G.nodeList.contains(right);
        G.addEdge(v, right);

        int left = v-1;
        G.nodeList.contains(left);
        G.addEdge(v, left);

        //V++;
    }

}
