/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package overordnatsystem;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.LinkedList;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;

/**
 *
 * @author oskst764
 */
public class OverordnatSystem {

    DataStore ds;
    DataStore ds3;
    ControlUI cui;

    public String GPSkoordinater(LinkedList<Vertex> path, int istart, int istop) {
        String GPS = "";
        for (int j = 0; j < path.size(); j++) {
            System.out.println(path.get(j).getId());
            if (j == 0) {
                if (ds.shelfDirection[ds3.orderStart[istart]].equalsIgnoreCase("N")) {
                    int a = Integer.parseInt(path.get(j).getId());
                    int b = Integer.parseInt(path.get(j + 1).getId());
                    if (a - b == 1) {
                        GPS += "L";
                        //System.out.println("KUL!!!");
                        //System.out.println("GPS +" + GPS[j]);
                    } else if (b - a == 1) {
                        GPS += "R";
                        //System.out.println("KUL2!!!");
                        //System.out.println("GPS +" + GPS[j]);
                    }
                } else if (ds.shelfDirection[ds3.orderStart[istart]].equalsIgnoreCase("S")) {
                    int a = Integer.parseInt(path.get(j).getId());
                    int b = Integer.parseInt(path.get(j + 1).getId());
                    if (a - b == 1) {
                        GPS += "R";
                        //System.out.println("KUL3!!!");
                        //System.out.println("GPS +" + GPS[j]);
                    } else if (b - a == 1) {
                        GPS += "L";
                        //System.out.println("KUL4!!!");
                        //System.out.println("GPS +" + GPS[j]);
                    }
                    //System.out.println("Kul, kanske1");
                } else if (ds.shelfDirection[ds3.orderStart[istart]].equalsIgnoreCase("V")) {
                    int a = Integer.parseInt(path.get(j).getId());
                    int b = Integer.parseInt(path.get(j + 1).getId());
                    if (a - b == 8) {
                        GPS += "L";
                        //System.out.println("KUL5!!!");
                        //System.out.println("GPS +" + GPS[j]);
                    } else if (b - a == 1) {
                        GPS += "R";
                        //System.out.println("KUL6!!!");
                        //System.out.println("GPS +" + GPS[j]);
                    }
                    //System.out.println("Kul, kanske2");
                }
            } else if (j == path.size() - 1) {
                if (ds.shelfDirection[ds3.orderEnd[istop]].equalsIgnoreCase("N")) {
                    int a = Integer.parseInt(path.get(j - 1).getId());
                    int b = Integer.parseInt(path.get(j).getId());
                    if (a - b == 1) {
                        GPS += "A";
                        //System.out.println("KUL7!!!");
                        //System.out.println("GPS +" + GPS[j]);
                    } else if (b - a == 1) {
                        GPS += "B";
                        //System.out.println("KUL8!!!");
                        //System.out.println("GPS +" + GPS[j]);
                    }
                    //System.out.println("Kul, kanske3");
                } else if (ds.shelfDirection[ds3.orderEnd[istop]].equalsIgnoreCase("S")) {
                    int a = Integer.parseInt(path.get(j - 1).getId());
                    int b = Integer.parseInt(path.get(j).getId());
                    if (a - b == 1) {
                        GPS += "B";
                        //System.out.println("KUL9!!!");
                        //System.out.println("GPS +" + GPS[j]);
                    } else if (b - a == 1) {
                        GPS += "A";
                        //System.out.println("KUL10!!!");
                        //System.out.println("GPS +" + GPS[j]);
                    }
                    //System.out.println("Kul, kanske4");
                } else if (ds.shelfDirection[ds3.orderEnd[istop]].equalsIgnoreCase("V")) {
                    int a = Integer.parseInt(path.get(j - 1).getId());
                    int b = Integer.parseInt(path.get(j).getId());
                    if (a - b == 1) {
                        GPS += "A";
                        //System.out.println("KUL11!!!");
                        //System.out.println("GPS +" + GPS[j]);
                    } else if (b - a == 8) {
                        GPS += "B";
                        //System.out.println("KUL12!!!");
                        //System.out.println("GPS +" + GPS[j]);
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
                    GPS += "F";
                    System.out.println("KUL");
                } else if (a - b > 1 && Math.abs(c - b) == 1 && b != 15) {
                    //Vänster/höger sväng om riktning norr -> söder
                    if (c - b == 1) {
                        GPS += "L";
                    } else if (b - c == 1) {
                        GPS += "R";
                    }
                    System.out.println("KUL3");
                } else if (b - a > 1 && Math.abs(c - b) == 1 && b != 15) {
                    //Vänster/höger sväng om riktning norr -> söder
                    if (c - b == 1) {
                        GPS += "R";
                    } else if (b - c == 1) {
                        GPS += "L";
                    }
                    System.out.println("KUL3");
                } else if (Math.abs(a - b) == 1 && c - b > 1 && a != 14) {
                    //Vänster/höger sväng om riktning vågrätt
                    if (a - b == 1) {
                        GPS += "R";
                    } else if (b - a == 1) {
                        GPS += "L";
                    }
                    System.out.println("KUL5");
                } else if (a == 14 || b == 14 || c == 14) {
                    //ta hand om alla elaka bågar via nod 14...
                    if (a == 16 && b == 15 && c == 14) {
                        GPS += "L";
                    } else if (a == 6 && b == 14 && c == 15) {
                        GPS += "F";
                    } else if (a == 14 && b == 15 && c == 16) {
                        GPS += "R";
                    } else if (a == 14 && b == 15 && c == 23) {
                        GPS += "F";
                    } else if (a == 23 && b == 15 && c == 14) {
                        GPS += "F";
                    }
                    System.out.println("KUL2");
                } else if (Math.abs(a - b) == 1 && Math.abs(b - c) == 1) {
                    //rakt fram vågrätt
                    GPS += "F";
                    System.out.println("KUL4");
                } else if (Math.abs(a - b) == 1 && b - c > 1) {
                    //Vänster/höger sväng om riktning vågrätt
                    if (a - b == 1) {
                        GPS += "L";
                    } else if (b - a == 1) {
                        GPS += "R";
                    }
                    System.out.println("KUL7");
                } else if (Math.abs(a - b) > 1 && c - b == 1) {
                    //Vänster/höger sväng om riktning vågrätt
                    if (a - b > 1) {
                        GPS += "L";
                    } else if (b - a > 1) {
                        GPS += "R";
                    }
                    System.out.println("KUL6");
                }
            }
        }
        return GPS;
    }

    public String bluetoothkom(BufferedReader bluetooth_in) {
        String meddelande_ut = "";
        try {
            String meddelande_in = bluetooth_in.readLine();

            String O = "" + meddelande_in.charAt(0);
            String U = meddelande_in.substring(1);

            if (meddelande_in.equalsIgnoreCase("C")) {
                System.out.println("Jag har fått ack");
                System.out.println("Jag ska vänta 5000ms");
                Thread.sleep(5000);
                System.out.println("Jag har väntat");

                meddelande_in = bluetooth_in.readLine();


                if (meddelande_in.equalsIgnoreCase("y")) {
                    System.out.println("klar");
                    System.out.println("kalla på opti");
                    meddelande_ut = "start";
                } else if (meddelande_in.equalsIgnoreCase("n")) {
                    System.out.println("Jag är LITHe vilse");
                    meddelande_ut = "start";

                } else if (meddelande_in.equalsIgnoreCase("e")) {
                    System.out.println("Skicka om");
                    meddelande_ut = "start";
                }
            } else if (O.equalsIgnoreCase("D")) {
                System.out.println("Distansen är: " + U + "cm");
            } else if (meddelande_in.equalsIgnoreCase("e")) {
                System.out.println("Skicka om");
                meddelande_ut = "start";
            }
        } catch (Exception e) {
            System.out.print(e.toString());
        }
        return meddelande_ut;
    }

    OverordnatSystem() {
        try {
            StreamConnection anslutning = (StreamConnection) Connector.open("btspp://F07BCBF04304:8");
            PrintStream bluetooth_ut = new PrintStream(anslutning.openOutputStream());
// F07BCBF04304:8 testdator
            BufferedReader bluetooth_in = new BufferedReader(new InputStreamReader(anslutning.openInputStream()));

            BufferedReader tangentbord = new BufferedReader(new InputStreamReader(System.in));

            String meddelande_ut = "start";

            ds = new DataStore();
            DataStore ds2 = new DataStore();
            DataStore ds4 = new DataStore();
            ds3 = new DataStore();

            //ds.setFileName("C:/Users/oskst764/Desktop/hej/OverordnatSystem/Lagernatverk_20130213.csv");
            //ds2.setFileName("C:/Users/oskst764/Desktop/hej/OverordnatSystem/Orders_20130211.csv");
            //ds.setFileName("C:/Users/Groggy/Documents/GitHub/bastaprojektarbetetjava/OverordnatSystem/Lagernatverk_20130213.csv");
            //ds.readNet();
            //ds.setFileName("C:/Users/Groggy/Documents/GitHub/bastaprojektarbetetjava/OverordnatSystem/Orders_20130211.csv");
            //ds.readOrders();
            //ds2.setFileName("C:/Users/Groggy/Documents/GitHub/bastaprojektarbetetjava/OverordnatSystem/Orders_20130211.csv");
            //ds2.readOrders();

            ds.setFileName("/home/itn/Desktop/bastaprojektarbetetjava/OverordnatSystem/Lagernatverk_20130213.csv");
            ds.readNet();
            ds.setFileName("/home/itn/Desktop/bastaprojektarbetetjava/OverordnatSystem/Orders_20130211.csv");
            ds.readOrders();
            ds2.setFileName("/home/itn/Desktop/bastaprojektarbetetjava/OverordnatSystem/Orders_20130211.csv");
            ds2.readOrders();

            cui = new ControlUI(ds);
            cui.setVisible(true);
            cui.showStatus();

            GuiUpdate g1 = new GuiUpdate(ds, cui);
            Thread t2 = new Thread(g1);
            t2.start();

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
                int pppp = 0;
                for (int j = i; j < ds3.orders; j++) {
                    if (ds2.orderStart[j] == ds2.orderEnd[i] && i != j) {
                        System.out.println("heej!");
                        ds3.orderStart[j] = ds2.orderStart[i];
                        ds3.orderEnd[j] = ds2.orderEnd[j];
                        System.out.println("ds3.orderStart[j]\t" + ds3.orderStart[j]);
                        System.out.println("ds3.orderEnd[j]\t\t" + ds3.orderEnd[j]);
                        ds2.orderStart[j] = -1;
                        ds2.orderEnd[j] = -1;
                        numbers[l] = j;
                        l++;
                        k++;
                        pppp++;
                        break;
                    }
                }
                for (int n = 0; n < l; n++) {
                    if (numbers[n] == i) {
                        m++;
                    }
                }
                if (k == 0 && m == 0) {
                    System.out.println("heej2!");
                    ds3.orderStart[i] = ds2.orderStart[i];
                    ds3.orderEnd[i] = ds2.orderEnd[i];
                    System.out.println("ds3.orderStart[j]\t" + ds3.orderStart[i]);
                    System.out.println("ds3.orderEnd[j]\t\t" + ds3.orderEnd[i]);
                    ds2.orderStart[i] = -1;
                    ds2.orderEnd[i] = -1;
                } else if (pppp != 0) {
                    ds3.orderStart[i] = 0;
                    ds3.orderEnd[i] = 0;
                }
                i++;
            }

            System.out.println("\n\n");
            int start;
            int stop;
            OptPlan op = new OptPlan(ds);

            LinkedList<Vertex> path;

            for (int j = 0; j < ds3.orders; j++) {
                System.out.println("ds3.orderStart[j]\t" + ds3.orderStart[j]);
                System.out.println("ds3.orderEnd[j]\t\t" + ds3.orderEnd[j]);
                System.out.println("");
            }

            System.out.println("\n\n");

            String GPS;
            for (i = 0; i < ds3.orders; i++) {
                //Förflyttning mellan ordrar
                GPS = "";
                if (i == 0) {
                    start = ds.shelfNode[0];
                    stop = (int) ds.shelfNode[ds3.orderStart[i]];
                } else {
                    start = (int) ds.shelfNode[ds3.orderEnd[i - 1]];
                    stop = (int) ds.shelfNode[ds3.orderStart[i]];
                }
                if (start != stop) {
                    path = op.createPlan(start, stop);
                    GPS = this.GPSkoordinater(path, i, i);
                } else if (start == stop && start == 24) {
                    GPS += "J";
                }
                System.out.println("\nGPS:" + GPS);
                System.out.println("GPS.längd " + GPS.length() + "\n");

                //Om ingen längd på GPS-koordinaterna ges behöver ingenting skickas till roboten
                if (GPS.length() > 0) {
                    if (meddelande_ut.equalsIgnoreCase("start")) {
                        meddelande_ut = GPS;
                        meddelande_ut = "x" + meddelande_ut + "z";
                        bluetooth_ut.println(meddelande_ut);
                    }
                    meddelande_ut = this.bluetoothkom(bluetooth_in);

                    ds.robotX = ds.nodeX[start - 1];
                    ds.robotY = ds.nodeY[start - 1];
                    cui.repaint();
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException ex) {
                        System.out.println("Fel");
                    }
                }
                Arrays.fill(ds.arcColor, 0);

                //Räkna ut förflyttning av LÅDA!
                GPS = "";
                start = (int) ds.shelfNode[ds3.orderStart[i]];
                stop = (int) ds.shelfNode[ds3.orderEnd[i]];

                if (start != stop) {
                    path = op.createPlan(start, stop);
                    GPS = this.GPSkoordinater(path, i, i);
                } else if (start == stop && start == 24) {
                    GPS += "J";
                }
                System.out.println("\nGPS:" + GPS);
                System.out.println("GPS.längd " + GPS.length() + "\n");

                //Om ingen längd på GPS-koordinaterna ges behöver ingenting skickas till roboten
                if (GPS.length() > 0) {
                    if (meddelande_ut.equalsIgnoreCase("start")) {
                        meddelande_ut = GPS;
                        meddelande_ut = "x" + meddelande_ut + "z";
                        bluetooth_ut.println(meddelande_ut);
                    }
                    meddelande_ut = this.bluetoothkom(bluetooth_in);

                    ds.robotX = ds.nodeX[start - 1];
                    ds.robotY = ds.nodeY[start - 1];
                    cui.repaint();
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException ex) {
                        System.out.println("Fel");
                    }
                }
                Arrays.fill(ds.arcColor, 0);
            }
        } catch (Exception e) {
            System.out.print(e.toString());
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
