/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package overordnatsystem;

import java.util.Arrays;
import java.util.LinkedList;

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

        /*
         System.out.println(ds2.orderStart[0] + " orderstart");
         System.out.println(ds2.orderEnd[0] + " orderEnd");
         */

        ds3.orders = ds2.orders;
        ds3.fileName = ds2.fileName;

        System.out.println("\n\n\n\n\n");

        int i = 0;
        int k, l, m;
        l = 0;
        int[] numbers = new int[ds3.orders];
        while (i < ds3.orders) {
            k = 0;
            m = 0;
            for (int j = i; j < ds3.orders; j++) {
                if (ds2.orderStart[j] == ds2.orderEnd[i]) {
                    ds3.orderStart[j] = ds2.orderStart[i];
                    ds3.orderEnd[j] = ds2.orderEnd[j];
                    numbers[l] = j;
                    l++;
                    k++;
                    break;
                }
            }
            for (int n = 0; n < l; n++) {
                if (numbers[n] == i) {
                    m++;
                }
            }
            if (k == 0 && m == 0) {
                ds3.orderStart[i] = ds2.orderStart[i];
                ds3.orderEnd[i] = ds2.orderEnd[i];
            }
            //System.out.println("i " + i + "\nStart: " + ds3.orderStart[i] + "\nStop: " + ds3.orderEnd[i] + "\n");
            i++;
        }

        System.out.println("\n\n");
        int start;
        int stop;
        OptPlan op = new OptPlan(ds);

        LinkedList<Vertex> path;

        for (i = 0; i < ds3.orders; i++) {
            if (ds3.orderStart[i] != ds3.orderEnd[i]) {
                //System.out.println("ds3.orderStart[i] " + ds3.orderStart[i]);
                //System.out.println("ds.shelfNode[ds3.orderEnd[i]] " + ds.shelfNode[ds3.orderEnd[i]]);
                start = (int) ds.shelfNode[ds3.orderStart[i]];
                stop = (int) ds.shelfNode[ds3.orderEnd[i]];
                path = op.createPlan(start, stop);

                //System.out.println("ds.shelfDirection[ds3.orderStart[i]] " + ds.shelfDirection[ds3.orderStart[i]]);

                String[] GPS = new String[path.size()];
                for (int j = 0; j < path.size(); j++) {
                    System.out.println(path.get(j).getId());
                    if (j == 0) {
                        if (ds.shelfDirection[ds3.orderStart[i]].equalsIgnoreCase("N")) {
                            int a = Integer.parseInt(path.get(j).getId());
                            int b = Integer.parseInt(path.get(j + 1).getId());
                            if (a - b == 1) {
                                GPS[j] = "L";
                                //System.out.println("KUL!!!");
                                //System.out.println("GPS[j] " + GPS[j]);
                            } else if (b - a == 1) {
                                GPS[j] = "R";
                                //System.out.println("KUL2!!!");
                                //System.out.println("GPS[j] " + GPS[j]);
                            }
                        } else if (ds.shelfDirection[ds3.orderStart[i]].equalsIgnoreCase("S")) {
                            int a = Integer.parseInt(path.get(j).getId());
                            int b = Integer.parseInt(path.get(j + 1).getId());
                            if (a - b == 1) {
                                GPS[j] = "R";
                                //System.out.println("KUL3!!!");
                                //System.out.println("GPS[j] " + GPS[j]);
                            } else if (b - a == 1) {
                                GPS[j] = "L";
                                //System.out.println("KUL4!!!");
                                //System.out.println("GPS[j] " + GPS[j]);
                            }
                            //System.out.println("Kul, kanske1");
                        } else if (ds.shelfDirection[ds3.orderStart[i]].equalsIgnoreCase("V")) {
                            int a = Integer.parseInt(path.get(j).getId());
                            int b = Integer.parseInt(path.get(j + 1).getId());
                            if (a - b == 8) {
                                GPS[j] = "L";
                                //System.out.println("KUL5!!!");
                                //System.out.println("GPS[j] " + GPS[j]);
                            } else if (b - a == 1) {
                                GPS[j] = "R";
                                //System.out.println("KUL6!!!");
                                //System.out.println("GPS[j] " + GPS[j]);
                            }
                            //System.out.println("Kul, kanske2");
                        }
                    } else if (j == path.size() - 1) {
                        if (ds.shelfDirection[ds3.orderEnd[i]].equalsIgnoreCase("N")) {
                            int a = Integer.parseInt(path.get(j - 1).getId());
                            int b = Integer.parseInt(path.get(j).getId());
                            if (a - b == 1) {
                                GPS[j] = "A";
                                //System.out.println("KUL7!!!");
                                //System.out.println("GPS[j] " + GPS[j]);
                            } else if (b - a == 1) {
                                GPS[j] = "B";
                                //System.out.println("KUL8!!!");
                                //System.out.println("GPS[j] " + GPS[j]);
                            }
                            //System.out.println("Kul, kanske3");
                        } else if (ds.shelfDirection[ds3.orderEnd[i]].equalsIgnoreCase("S")) {
                            int a = Integer.parseInt(path.get(j - 1).getId());
                            int b = Integer.parseInt(path.get(j).getId());
                            if (a - b == 1) {
                                GPS[j] = "B";
                                //System.out.println("KUL9!!!");
                                //System.out.println("GPS[j] " + GPS[j]);
                            } else if (b - a == 1) {
                                GPS[j] = "A";
                                //System.out.println("KUL10!!!");
                                //System.out.println("GPS[j] " + GPS[j]);
                            }
                            //System.out.println("Kul, kanske4");
                        } else if (ds.shelfDirection[ds3.orderEnd[i]].equalsIgnoreCase("V")) {
                            int a = Integer.parseInt(path.get(j - 1).getId());
                            int b = Integer.parseInt(path.get(j).getId());
                            if (a - b == 1) {
                                GPS[j] = "A";
                                //System.out.println("KUL11!!!");
                                //System.out.println("GPS[j] " + GPS[j]);
                            } else if (b - a == 8) {
                                GPS[j] = "B";
                                //System.out.println("KUL12!!!");
                                //System.out.println("GPS[j] " + GPS[j]);
                            }
                            //System.out.println("Kul, kanske5");
                        }
                    } else {
                        /* Det är något som strular... Ger felaktiga tecken för
                         * fram/höger/vänster.
                         * 24 -> 23 -> 15 -> 15 -> 14... L,L,R,A.. R är fel!
                         * 7 -> 6 -> 14... L,L,B... Andra L är fel...
                         * 16->15->14 ger rakt... INTE RÄTT!!!!
                         * 
                         * Rätt!
                         * 17 -> 16 -> 8 -> 7
                         * 32 -> 31 -> 25 -> 26 -> 18 -> 17
                         */
                        int a = Integer.parseInt(path.get(j).getId());
                        int b = Integer.parseInt(path.get(j + 1).getId());
                        int c = Integer.parseInt(path.get(j - 1).getId());
                        if (Math.abs(a - b) == 1 && Math.abs(a - c) == 1) {
                            GPS[j] = "F";
                            //System.out.println("KUL13!!!");
                            //System.out.println("GPS[j] " + GPS[j]);
                        } else if (Math.abs(a - b) == 1 && Math.abs(a - c) > 1) {
                            if (a - b == 1) {
                                GPS[j] = "R";
                                //System.out.println("KUL14!!!");
                                //System.out.println("GPS[j] " + GPS[j]);
                            }
                            if (b - a == 1) {
                                GPS[j] = "L";
                                //System.out.println("KUL15!!!");
                                //System.out.println("GPS[j] " + GPS[j]);
                            }
                        } else if (Math.abs(a - b) > 1 && Math.abs(a - c) == 1) {
                            if (a - c == 1) {
                                GPS[j] = "R";
                                //System.out.println("KUL16!!!");
                                //System.out.println("GPS[j] " + GPS[j]);
                            }
                            if (c - a == 1) {
                                GPS[j] = "L";
                                //System.out.println("KUL17!!!");
                                //System.out.println("GPS[j] " + GPS[j]);
                            }
                        }
                    }
                }
                System.out.println("\n" + Arrays.toString(GPS));
            }
            System.out.println("\n");
        }
    }

    /*
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        OverordnatSystem x = new OverordnatSystem();
    }
}
