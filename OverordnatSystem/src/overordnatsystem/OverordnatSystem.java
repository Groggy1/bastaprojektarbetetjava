/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package overordnatsystem;

/**
 *
 * @author oskst764
 */
public class OverordnatSystem {

    DataStore ds;
    DataStore ds3;

    OverordnatSystem() {
        ds = new DataStore();
        DataStore ds2 = new DataStore();
        ds3 = new DataStore();

        ds.setFileName("C:/Users/oskst764/Desktop/hej/OverordnatSystem/Lagernatverk_20130213.csv");
        ds2.setFileName("C:/Users/oskst764/Desktop/hej/OverordnatSystem/Orders_20130211.csv");

        ds.readNet();
        ds2.readOrders();

        /*System.out.println(ds2.orderStart[0] + " orderstart");
        System.out.println(ds2.orderEnd[0] + " orderEnd");*/

        ds3.orders = ds2.orders;
        ds3.fileName = ds2.fileName;

        System.out.println("\n\n\n\n\n");
        
        for (int i = 0; i < ds3.orders; i++) {
            int k = 0;
            for (int j = i; j < ds3.orders; j++) {
                if (ds2.orderStart[j] == ds2.orderEnd[i]) {
                    ds3.orderStart[i] = ds2.orderStart[i];
                    ds3.orderEnd[i] = ds2.orderEnd[j];
                    System.out.println("Hej!");
                    k = 1;
                    ds3.orders--;
                    break;
                }
            }
            if (k == 0) {
                ds3.orderStart[i] = ds2.orderStart[i];
                ds3.orderEnd[i] = ds2.orderEnd[i];
                System.out.println("Hej2!");
            }
            System.out.println("i " + i + "\nStart: " + ds3.orderStart[i] + "\nStop: " + ds3.orderEnd[i] + "\n");
        }
        
        System.out.println("\nDS3.ORDERS " + ds3.orders + "\n\n\n\n");

        int start = 3;
        int stop = 32;
        OptPlan op = new OptPlan(ds);
        op.createPlan(start, stop);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        OverordnatSystem x = new OverordnatSystem();
    }
}
