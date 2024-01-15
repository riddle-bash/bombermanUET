package uet.oop.bomberman.mapGame;

import uet.oop.bomberman.BombermanGame;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Convert2DToAdjMatrix {
    private static int height = BombermanGame.HEIGHT;
    private static int width = BombermanGame.WIDTH;
    private static int no_nodes = height * width;
    public static boolean[][] AdjMatrixBackUp = new boolean[no_nodes][no_nodes];
    public static boolean[][] AdjMatrixBackUp_copy = new boolean[no_nodes][no_nodes];


    public static void modifyMatrixUnit (int temp, int adj_x, int adj_y, boolean[][] adjacencyMatrix) {
        if (adj_x >= 0 && adj_y >= 0) {
            if (adj_y * width + adj_x < no_nodes) {
                adjacencyMatrix[temp][adj_y * width + adj_x] = true;    //has relationship
                adjacencyMatrix[adj_y * width + adj_x][temp] = true;
            }
        }
    }
    public static void modifyMatrix(int x, int y, boolean[][] adjacencyMatrix) {
        /*
            -->x
            y       left_up  |mid_up  |right_up
                    mid_left |*       |mid_right
                    left_down|mid_down|right_down
         */
        int temp = y * width + x;
        //get 8 direction
        int adj_x, adj_y;
        //mid_up
        adj_x = x;
        adj_y = y - 1;
        modifyMatrixUnit(temp, adj_x, adj_y, adjacencyMatrix);
        //mid_left
        adj_x = x - 1;
        adj_y = y;
        modifyMatrixUnit(temp, adj_x, adj_y, adjacencyMatrix);
        //mid_right
        adj_x = x + 1;
        adj_y = y;
        modifyMatrixUnit(temp, adj_x, adj_y, adjacencyMatrix);
        //mid_down
        adj_x = x;
        adj_y = y + 1;
        modifyMatrixUnit(temp, adj_x, adj_y, adjacencyMatrix);
    }
    public static void convert(boolean[][] mapGame, boolean[][] AdjMatrix) {
        //init AdjMatrix
        for (int i = 0; i < no_nodes; i++) {
            for (int j = 0; j < no_nodes; j++) {
                AdjMatrix[i][j] = false;
            }
        }

        for (int i = 1; i < height - 1; i++) {
            for (int j = 1; j < width - 1; j++) {
                modifyMatrix(j, i, AdjMatrix);
            }
        }

        //make back up AdjMatrix
        for (int i = 0; i < no_nodes; i++) {
            for (int j = 0; j < no_nodes; j++) {
                AdjMatrixBackUp[i][j] = AdjMatrix[i][j];
                AdjMatrixBackUp_copy[i][j] = AdjMatrix[i][j];
            }
        }

        //remove all location which don't really has relationship
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (mapGame[i][j]) {
                    //current position has wall or brick
                    int index = i * width + j;
                    for (int k = 0; k < no_nodes; k ++) {
                        AdjMatrix[index][k] = false;
                        AdjMatrix[k][index] = false;
                    }
                }
            }
        }
    }

    //find the shortest path from oneal to bomber
    private static int[] BFS(boolean[][] AdjMatrix, int source, int dest) {
        boolean[] visited = new boolean[no_nodes];
        int[] parent = new int[no_nodes];

        for (int i = 0; i < no_nodes; i++) {
            visited[i] = false;
            parent[i] = -1;
        }

        LinkedList<Integer> queue = new LinkedList<Integer>();
        queue.add(source);
        visited[source] = true;
        while (queue.size() != 0)
        {
            source = queue.peek();
            queue.remove();
            for (int u = 0; u < no_nodes; u++) {
                if (!visited[u] && AdjMatrix[source][u]) {
                    queue.add(u);
                    visited[u] = true;
                    parent[u] = source;
                    if (u == dest) {
                        return parent;
                    }
                }
            }
        }
        return parent;
    }

    public static ArrayList<Integer> findWay(boolean[][] AdjMatrix, int source, int dest) {
        int[] parent = BFS(AdjMatrix, source, dest);
        ArrayList<Integer> wayBackHome = new  ArrayList<>();
        int u = dest;
        while (parent[u] != -1) {
            wayBackHome.add(parent[u]);
            u = parent[u];
        }
        return wayBackHome;
    }
}
