package org.findPath;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.util.Queue;

public class Config extends JPanel implements MouseListener, Runnable {

    int cellSize = 25;
    int width;
    int height;
    Color[][] colorGrid;
    Graph graph;
    int currentNode;
    int currentNodeIndex;
    private Thread thread;

    private int currentX;
    private int currentY;
    private Queue<Integer> path;

    public Config(Dimension dimension, Graph G) {
        setBackground(Color.black);
        graph = G;
        //path = p;

        width = (int)Math.sqrt(graph.V());
        height = width;

        colorGrid = new Color[width][height];

        setSize(dimension);
        setPreferredSize(dimension);
        addMouseListener(this);

        for (int row = 0; row < colorGrid.length; row++) {
            for (int col = 0; col < colorGrid[0].length; col++) {
                colorGrid[row][col]=Color.white;
            }
        }

        // Start
        colorGrid[0][0] = Color.green;

        // Goal
        colorGrid[colorGrid.length-1][colorGrid.length-1] = Color.red;

    }

    @Override
    public void paintComponent(Graphics g) {

        Graphics2D gridGraph = (Graphics2D)g;
        Dimension d = getSize();
        gridGraph.setColor(Color.white);
        gridGraph.fillRect(0,0,d.width,d.height);

        int x = 0;
        int y = 0;

        for (int row = 0; row < colorGrid.length; row++) {
            for (int col = 0; col < colorGrid[0].length; col++) {
                gridGraph.setColor(colorGrid[row][col]);
                gridGraph.fillRect(x, y, cellSize, cellSize);

                gridGraph.setColor(Color.black);
                gridGraph.drawRect(x, y, cellSize, cellSize);

                x += cellSize;

            }
            x = 0;
            y += cellSize;
        }

    }

    private void startThread() {
        if (thread == null) {
            thread = new Thread(this);
            thread.start();
        }
    }

    private void stopThread() {
        if (thread != null) {
            Thread temp = thread;
            thread = null;
            temp.interrupt();
        }
    }

    public void run() {
        while (Thread.currentThread() == thread) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                break;
            }

            if (colorGrid[currentX][currentY] != Color.yellow &&
                    colorGrid[currentX][currentY] != Color.green &&
                    colorGrid[currentX][currentY] != Color.red &&
                    colorGrid[currentX][currentY] != Color.black) {
                colorGrid[currentX][currentY] = new Color(0, 153, 204);

            }
            //System.out.println(currentNode);
            //if (currentNodeIndex < path.size()-1){
            //    currentNodeIndex += 1;
                //currentNode = path.get(currentNodeIndex);

            //}
            if (path.size() > 0) {
                currentNode = path.poll();
            }
            currentX = Math.round(currentNode / height);
            currentY = currentNode % width;

            repaint();
        }
    }

    public static void pause(int t) {
        try {
            Thread.sleep(t);
        }
        catch (InterruptedException e) {
            System.out.println("Error sleeping");
        }
    }

    public void mousePressed(MouseEvent e) {

        int x = e.getX();
        int y = e.getY();

        int row = y / cellSize;
        int column = x / cellSize;

        currentY = 0;

        if (colorGrid[row][column] == Color.white) {
            colorGrid[row][column] = Color.black;
            /*
            LinkedList<Integer> temp = (LinkedList) graph.adjacency(column + row * width).clone();
            for (int adj : temp) {
                if (adj != column + row * width) {
                    graph.removeEdge(column + row * width, adj);
                }
            }
            */
            graph.removeNode(graph, column + row * width);
            //repaint();
        } else if (colorGrid[row][column] == Color.black) {
            colorGrid[row][column] = Color.white;
            /*
            List<Integer> temp = (List) graph.adjacency(column + row * width).clone();
            for (int adj : temp) {
                if (adj != column + row * width) {
                    graph.addEdge(column + row * width, adj);
                }
            }
            */
            //System.out.println(column + row * width);
            graph.restoreNode(graph, column + row * width);
            //repaint();
        } else if (colorGrid[row][column] == Color.green) {

            Search search = new Search(graph);
            path = search.getPath();

            currentNodeIndex = 0;
            //currentNode = path.get(currentNodeIndex);
            if (path == null) {
                colorGrid[0][0] = Color.red;
            } else {
                currentNode = path.poll();
                //currentX = currentNode % 10;
                //currentY = Math.round(currentNode / 10);
                startThread();
            }
        } else if (colorGrid[row][column] == Color.red) {
            stopThread();
        }

        repaint();
    }

    public void mouseClicked(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

}
