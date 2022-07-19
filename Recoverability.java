import java.util.ArrayList;
import java.util.Scanner;

public class Recoverability {

    public static void main(String[] args) {
        // transactions --> Nodes

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


        // first loop for initializing nodes
        //example input: w1a,w1b,w2a,r2b,c1,c2


        // Print total number of transactions
        System.out.println(transactions.size());
        System.out.println(checkForRecoverability(arr) ? 1 : 0);
        // if loop doesn't exist ( conflict serializable because loops doesn't exists) so return 1

        scanner.close();

    }

    public static boolean checkForRecoverability(String[] arr) {

        int index = 0;
        for (String c : arr) {
            char rw = c.charAt(0);
            int node = c.charAt(1) - '0';
            if (rw == 'c') continue;
            char var2 = c.charAt(2);
            if (c.charAt(0) == 'r') {
                for (int i = index; i >= 0; i--) {
                    if (arr[i].charAt(0) == 'w') {
                        if (arr[i].charAt(2) == var2) {
                            int node2 = arr[i].charAt(1) - '0';
                            int a = 0;
                            int b = 0;
                            for (int j = 0; j < arr.length; j++) {
                                if (arr[j].charAt(0) == 'c' && arr[j].charAt(1) - '0' == node2)
                                    b = j;
                                if (arr[j].charAt(0) == 'c' && arr[j].charAt(1) - '0' == node)
                                    a = j;

                            }
                            return b < a;
                        }

                    }


                }

            }
            index++;

        }
        return true;

    }
}
