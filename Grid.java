package org.findPath;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import java.awt.Color;

import java.util.Scanner;

public class Grid {

    private JFrame frame;
    public Graph graph;

    public void constructGraph(int n) {
        int N = n;
        graph = new Graph(N*N);

        for(int i = 0; i < N*N ; i++){

            if(i+1 < N*N && (i+1)%N != 0) {
                graph.addEdge(i, i + 1);
            }
            if(i+N < N*N) {
                graph.addEdge(i, i + N);
            }
        }
    }

    public Grid(int n) {

        frame = new JFrame("Grid");
        frame.setSize(800, 600);
        frame.setPreferredSize(frame.getSize());
        frame.setBackground(Color.black);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        constructGraph(n);

        frame.add(new Config(frame.getSize(), graph));
        frame.pack();
        frame.setVisible(true);

    }

    public static void main(String... argv) {

        System.out.print("Enter the size of the side of the [square] Grid: ");

        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();

        new Grid(n);

    }

}

