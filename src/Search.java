package org.findPath;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Collections;

public class Search {

    private Graph graph;
    private boolean[] marked;    // marked[v] = is there an s-v path?
    private Queue<Integer> preorder;   // vertices in preorder
    private Queue<Integer> postorder;  // vertices in postorder
    private Queue<Integer> path; // path
    private int[] edgeTo;        // edgeTo[v] = last adjacent node on s-v path

    public void dfs(Graph G) { dfs(G, 0); }

    private void dfs(Graph G, int v) {

        marked[v] = true;
        preorder.add(v);

        Collections.shuffle(G.adjacency(v));
        for (int w : G.adjacency(v)) {
            if (!marked[w]) {
                //Bellow
                edgeTo[w] = v;

                dfs(G, w);
            }
        }
        postorder.add(v);
    }

    public boolean hasPathTo(int v) {
        //graph.validateNode(v);
        graph.getNodeList().contains(v);
        return marked[v];
    }

    public LinkedList<Integer> pathTo() {
        int v = graph.V()-1;

        if(!hasPathTo(v)){
            return null;
        }
        LinkedList<Integer> path = new LinkedList<>();
        for(int w = v; w != 0; w = edgeTo[w]){
            path.addFirst(w);
        }
        path.addFirst(0);
        return path;
    }

    public Search(Graph G) {
        graph = G;
        marked = new boolean[graph.V()+1];
        edgeTo = new int[graph.V()+1];
        preorder = new LinkedList<Integer>();
        postorder = new LinkedList<Integer>();
        path = new LinkedList<Integer>();

        dfs(G);

        path = pathTo();

    }

    public Queue<Integer> getPath() { return path; }

}
