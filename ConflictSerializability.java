import java.util.*;
import java.util.Scanner;


public class ConflictSerializability {

    public static void main(String[] args) {

        // transactions --> Nodes
        int t;
        String statement;
        Scanner scanner = new Scanner(System.in);
        statement = scanner.nextLine();

        String[] arr = statement.split(",");

        ArrayList<Integer> transactions = new ArrayList<Integer>();
        // first loop for initializing nodes
        for (String a : arr) {
            int node = a.charAt(1) - '0';
            if (!transactions.contains(node)) {
                transactions.add(node);
            }
        }

        Graph graph = new Graph(transactions.size());


        // second loop for adding edges

        int index = 0;

        for (String c : arr) {
            //read/write
            int index2 = 0;
            char rw = c.charAt(0);
            int node = c.charAt(1) - '0';
            char var = c.charAt(2);

            for (String c2 : arr) {
                if (index2 <= index) {
                    index2++;
                    continue;
                }
                char rw2 = c2.charAt(0);
                int node2 = c2.charAt(1) - '0';
                char var2 = c2.charAt(2);
                // first check if variables are equal
                if (var == var2 && node != node2) {
                    if ((rw == 'r' && rw2 == 'w') || (rw == 'w' && rw2 == 'r') || (rw == 'w' && rw2 == 'w')) {
                        if (!graph.checkEdge(node - 1, node2 - 1))
                            graph.addEdge(node - 1, node2 - 1);
                    }

                }
            }
            index++;
        }

        // Print total number of transactions
        System.out.println(transactions.size());
        System.out.println(graph.isCycle() ? 0 : 1); // if loop exists (true) print 0
        // if loop doesn't exist ( conflict serializable because loops doesn't exists) so return 1
//        graph.print();
        scanner.close();

    }
}


class Graph {

    private final int V;
    private final List<List<Integer>> adj;

    public Graph(int V) {
        this.V = V;
        adj = new ArrayList<>(V);

        for (int i = 0; i < V; i++)
            adj.add(new LinkedList<>());
    }


    void addEdge(int source, int dest) {
        adj.get(source).add(dest);
    }


    // using dfs we search for loops.

    public boolean isCycle() {
        int vertices = V;
        boolean visited[] = new boolean[vertices];
        boolean recursiveArr[] = new boolean[vertices];

        //do DFS from each node
        for (int i = 0; i < vertices; i++) {
            if (isCyclicUtil(i, visited, recursiveArr))
                return true;
        }
        return false;
    }

    private boolean isCyclicUtil(int i, boolean[] visited,
                                 boolean[] recStack) {

        // Mark the current node as visited and
        // part of recursion stack
        if (recStack[i])
            return true;

        if (visited[i])
            return false;

        visited[i] = true;

        recStack[i] = true;
        List<Integer> children = adj.get(i);

        for (Integer c : children)
            if (isCyclicUtil(c, visited, recStack))
                return true;

        recStack[i] = false;

        return false;
    }

    public boolean checkEdge(int src, int dst) {
        List<Integer> currentList = adj.get(src);
        if (adj.get(dst).size() > 0) {
            int dstNode = adj.get(dst).get(0);
            for (int node : currentList) {
                if (node == dstNode) {
                    return true;
                }
            }
        }

        return false;
    }


}

