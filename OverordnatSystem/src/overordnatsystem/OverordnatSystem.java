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
        DataStore ds4 = new DataStore();
        ds3 = new DataStore();

        //ds.setFileName("C:/Users/oskst764/Desktop/hej/OverordnatSystem/Lagernatverk_20130213.csv");
        //ds2.setFileName("C:/Users/oskst764/Desktop/hej/OverordnatSystem/Orders_20130211.csv");
        ds.setFileName("C:/Users/Groggy/Documents/GitHub/bastaprojektarbetetjava/OverordnatSystem/Lagernatverk_20130213.csv");
        ds2.setFileName("C:/Users/Groggy/Documents/GitHub/bastaprojektarbetetjava/OverordnatSystem/Orders_20130211.csv");

        ds.readNet();
        ds2.readOrders();

        /*
         System.out.println(ds2.orderStart[0] + " orderstart");
         System.out.println(ds2.orderEnd[0] + " orderEnd");
         */

        ds3.orders = ds2.orders;
        ds3.fileName = ds2.fileName;
        ds4.orders = ds2.orders;
        ds4.fileName = ds2.fileName;

        System.out.println("\n\n\n\n\n");

        int i = 0;
        int k, l, m, diff = 0;
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
            diff = 0;
            int mindiff = 10000, nextnode = 0;
            for (int j = 0; j < ds3.orders; j++) {
                if (i == 0) {
                    start = (int) ds.shelfNode[0];
                    stop = (int) ds.shelfNode[ds3.orderStart[j]];
                    if (start != stop) {
                        path = op.createPlan(start, stop);
                        for (int q = 0; q < path.size(); q++) {
                            diff = (int) Math.max(Math.abs(ds.nodeY[start - 1] - ds.nodeY[stop - 1]), Math.abs(ds.nodeX[start - 1] - ds.nodeX[stop - 1]));
                        }
                        mindiff = Math.min(mindiff, diff);
                        //System.out.println("ds3.orderEnd[j] " + ds3.orderEnd[j]);
                        if (mindiff == diff) {
                            nextnode = j;
                            ds4.orderStart[i] = ds3.orderStart[j];
                            ds4.orderEnd[i] = ds3.orderEnd[j];
                            System.out.println("ds4.orderStart[i] " + ds4.orderStart[i]);
                            System.out.println("ds4.orderEnd[i] " + ds4.orderEnd[i]);
                        }
                    }
                } else {
                    start = ds.shelfNode[ds4.orderEnd[i - 1]];
                    stop = ds.shelfNode[ds3.orderEnd[j]];
                    if (start != stop) {
                        path = op.createPlan(start, stop);
                        for (int q = 0; q < path.size(); q++) {
                            diff = diff + (int) Math.max(Math.abs(ds.nodeY[start - 1] - ds.nodeY[stop - 1]), Math.abs(ds.nodeX[start - 1] - ds.nodeX[stop - 1]));
                        }
                        mindiff = Math.min(mindiff, diff);
                        //System.out.println("ds3.orderEnd[j] " + ds3.orderEnd[j]);
                        if (mindiff == diff) {
                            nextnode = j;
                            ds4.orderStart[i] = ds3.orderStart[j];
                            ds4.orderEnd[i] = ds3.orderEnd[j];
                            ds3.orderStart[j] = 0;
                            ds3.orderEnd[j] = 0;
                            System.out.println("ds4.orderStart[i] " + ds4.orderStart[i]);
                            System.out.println("ds4.orderEnd[i] " + ds4.orderEnd[i]);
                        }
                    }
                }
            }
            System.out.println("I " + i + " DIFF " + diff + " nextnode " + nextnode);
        }

        for(int j = 0;j < ds4.orders; j++){
            System.out.println("ds4.orderStart[j] " + ds4.orderStart[j]);
            System.out.println("ds4.orderEnd[j] " + ds4.orderEnd[j]);
        }
        
        ds3 = ds4;
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
                        int a = Integer.parseInt(path.get(j - 1).getId());
                        int b = Integer.parseInt(path.get(j).getId());
                        int c = Integer.parseInt(path.get(j + 1).getId());
                        //System.out.println("FUUUU");
                        //System.out.println("A " + a);
                        //System.out.println("B " + b);
                        //System.out.println("C " + c);

                        if (Math.abs(a - b) > 1 && Math.abs(b - c) > 1) {
                            //rakt fram lodrätt
                            GPS[j] = "F";
                            System.out.println("KUL");
                        } else if (a - b > 1 && Math.abs(c - b) == 1 && b != 15) {
                            //Vänster/höger sväng om riktning norr -> söder
                            if (c - b == 1) {
                                GPS[j] = "L";
                            } else if (b - c == 1) {
                                GPS[j] = "R";
                            }
                            System.out.println("KUL3");
                        } else if (Math.abs(a - b) == 1 && c - b > 1 && a != 14) {
                            //Vänster/höger sväng om riktning vågrätt
                            if (a - b == 1) {
                                GPS[j] = "R";
                            } else if (b - a == 1) {
                                GPS[j] = "L";
                            }
                            System.out.println("KUL5");
                        } else if (a == 14 || b == 14 || c == 14) {
                            //ta hand om alla elaka bågar via nod 14...
                            if (a == 16 && b == 15 && c == 14) {
                                GPS[j] = "L";
                            } else if (a == 6 && b == 14 && c == 15) {
                                GPS[j] = "F";
                            } else if (a == 14 && b == 15 && c == 16) {
                                GPS[j] = "R";
                            } else if (a == 14 && b == 15 && c == 23) {
                                GPS[j] = "F";
                            } else if (a == 23 && b == 15 && c == 14) {
                                GPS[j] = "F";
                            }
                            System.out.println("KUL2");
                        } else if (Math.abs(a - b) == 1 && Math.abs(b - c) == 1) {
                            //rakt fram vågrätt
                            GPS[j] = "F";
                            System.out.println("KUL4");
                        } else if (Math.abs(a - b) == 1 && b - c > 1) {
                            //Vänster/höger sväng om riktning vågrätt
                            if (a - b == 1) {
                                GPS[j] = "L";
                            } else if (b - a == 1) {
                                GPS[j] = "R";
                            }
                            System.out.println("KUL7");
                        } else if (Math.abs(a - b) > 1 && c - b == 1) {
                            //Vänster/höger sväng om riktning vågrätt
                            if (a - b > 1) {
                                GPS[j] = "L";
                            } else if (b - a > 1) {
                                GPS[j] = "R";
                            }
                            System.out.println("KUL6");
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
