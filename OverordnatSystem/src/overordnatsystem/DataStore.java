package overordnatsystem;

import java.awt.Color;
import java.io.File;
import java.util.Scanner;

/**
 *
 * @author clary35
 */
public class DataStore {

    String fileName = null;
    int nodes;
    int arcs;
    int shelves;
    double[] nodeX;
    double[] nodeY;
    int[] arcStart;
    int[] arcEnd;
    boolean networkRead;
    boolean ordersRead;
    int orders;
    int[] orderStart;
    int[] orderEnd;
    int[] shelfNumber = new int[20];
    int[] shelfNode = new int[20];
    String[] shelfDirection = new String[20];
    int[] arcColor = new int[1000];
    boolean updateUIflag = false;
    double robotX;
    double robotY;
    boolean startnod14anvand = false;
    boolean start = false;

    public DataStore() {
        // Initialize the datastore with fixed size arrays for storing the network data
        nodes = 0;
        arcs = 0;
        shelves = 0;
        nodeX = new double[99];
        nodeY = new double[99];
        arcStart = new int[1000];
        arcEnd = new int[1000];



        /*
         * TO DO: Add data storage for the shelf data
         */

        networkRead = false;


        orders = 0;
        orderStart = new int[99];
        orderEnd = new int[99];
        ordersRead = false;
    }

    public void setFileName(String newFileName) {
        this.fileName = newFileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void readNet() {
        final Color BLUE_COLOR = new Color(0, 0, 200);
        String line;

        if (fileName == null) {
            System.err.println("No file name set. Data read aborted.");
            return;
        }
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file, "iso-8859-1");
            String[] sline;

            // Read number of nodes
            line = (scanner.nextLine());
            nodes = Integer.parseInt(line.trim());
            line = scanner.nextLine();
            arcs = Integer.parseInt(line.trim());
            line = scanner.nextLine();
            shelves = Integer.parseInt(line.trim());

            // Debug printout: network size data
            System.out.println("Nodes: " + nodes);
            System.out.println("Arcs: " + arcs);
            System.out.println("Shelves: " + shelves);

            // Read nodes as number, x, y
            for (int i = 0; i < nodes; i++) {
                line = scanner.nextLine();
                //split space separated data on line
                sline = line.split(" ");
                nodeX[i] = Double.parseDouble(sline[1].trim());
                nodeY[i] = Double.parseDouble(sline[2].trim());
            }

            // Debug printout: print data for node 1
            System.out.println("Node 1: " + nodeX[0] + " " + nodeY[0]);


            // Read arc list as start node number, end node number
            for (int i = 0; i < arcs; i++) {
                line = scanner.nextLine();
                //split space separated data on line
                sline = line.split(" ");
                arcStart[i] = Integer.parseInt(sline[0].trim());
                arcEnd[i] = Integer.parseInt(sline[1].trim());
            }

            for (int i = 0; i < shelves; i++) {
                line = scanner.nextLine();
                //split space separated data on line
                sline = line.split(" ");
                if ("A".equals(sline[0].trim())) {
                    shelfNumber[i] = 0; // Let the A shelf be represented by 0
                } else {
                    shelfNumber[i] = Integer.parseInt(sline[0].trim());
                }
                shelfNode[i] = Integer.parseInt(sline[1].trim());
                shelfDirection[i] = sline[2].trim();
            }
            // Debug printout: print data for the first shelf
            System.out.println("Shelf 1: " + shelfNumber[0] + " " + shelfNode[0] + " " + shelfDirection[0]);


            networkRead = true;  // Indicate that all network data is in place in the DataStore

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void readOrders() {
        String line;

        if (fileName == null) {
            System.err.println("No file name set for order list. Data read aborted.");
            return;
        }
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file, "iso-8859-1");
            String[] sline;

            // Read number of orders
            line = (scanner.nextLine());
            orders = Integer.parseInt(line.trim());

            // Debug printout: network size data
            System.out.println("Orders: " + orders);

            // Read nodes as number, x, y
            for (int i = 0; i < orders; i++) {
                line = scanner.nextLine();
                //split space separated data on line
                sline = line.split(" ");
                if ("A".equals(sline[0].trim())) {
                    orderStart[i] = 0; // Let the A shelf be represented by 0
                } else {
                    orderStart[i] = Integer.parseInt(sline[0].trim());
                }

                if ("A".equals(sline[1].trim())) {
                    orderEnd[i] = 0;
                } else {
                    orderEnd[i] = Integer.parseInt(sline[1].trim());
                }
            }

            // Debug printout: print data for node 1
            System.out.println("Order 1: " + orderStart[0] + " " + orderEnd[0]);


            ordersRead = true;  // Indicate that all network data is in place in the DataStore

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
