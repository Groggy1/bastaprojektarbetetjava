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

    public String bluetoothkom(BufferedReader bluetooth_in) {
        String meddelande_ut = "";
        try {
            cui.jTextArea1.append("väntar\n");
            cui.jTextArea1.setCaretPosition(cui.jTextArea1.getDocument().getLength());
            //Väntar på meddelande från robot
            String meddelande_in = bluetooth_in.readLine();
            System.out.println(meddelande_in);

            //Om distans används följande variabler
            String O = "" + meddelande_in.charAt(0);
            String U = meddelande_in.substring(1);

            //Om robot mottar orderlista med x som start och z som slut fås ack tillbaka
            if (meddelande_in.equalsIgnoreCase("C")) {
                cui.jTextArea1.append("Jag har fått ack\n");
                cui.jTextArea1.setCaretPosition(cui.jTextArea1.getDocument().getLength());
                // System.out.println("Jag ska vänta 5000ms");
                // Thread.sleep(5000);
                // System.out.println("väntar");

                // meddelande_in = null;
                //Vänta på hur det går för roboten, dvs om den klarade orderlistan eller inte
                meddelande_in = bluetooth_in.readLine();

                System.out.println("meddelande_in efter ack " + meddelande_in);

                //Klarade orderlistan
                if (meddelande_in.equalsIgnoreCase("y")) {
                    /*try { //test med 10 sek vila
                    Thread.sleep(10000);
                    } catch (Exception e) {
                    System.out.print(e.toString());
                    }*/
                    cui.jTextArea1.append("klar\nkalla på optimeringen\n");
                    cui.jTextArea1.setCaretPosition(cui.jTextArea1.getDocument().getLength()); //autoscroll
                    //System.out.println("");
                    meddelande_ut = "start";
                } else if (meddelande_in.equalsIgnoreCase("n")) {
                    //Klarade inte orderlistan
                    cui.jTextArea1.append("Jag är LITHe vilse\n");
                    meddelande_ut = "start";

                }
            } else if (meddelande_in.equalsIgnoreCase("e")) {
                //Felsändning
                System.out.println("Skicka om");
                meddelande_ut = "start";
            } else if (O.equalsIgnoreCase("D")) {
                //Distans
                System.out.println("Distansen är: " + U + "cm");
            }
        } catch (Exception e) {
            System.out.print(e.toString());
        }
        return meddelande_ut;
    }

    public String GPSkoordinater(LinkedList<Vertex> path, int istart, int istop) {
        String GPS = "";
        //System.out.println("start " + istart + "stop " + istop + " ds.shelfDirection[ds3.orderStart[istart]] " + ds.shelfDirection[ds3.orderStart[istart]]);
        for (int j = 0; j < path.size(); j++) {
            boolean cont = true;
            for (int i = 0; i < ds.shelves; i++) {
                if (Integer.parseInt(path.get(j).getId()) == ds.shelfNode[i] && j > 0 && j != path.size() - 1) {
                    cont = false;
                }
            }
            System.out.println(path.get(j).getId());
            if (cont) {
                boolean startnod14a = true;
                boolean startnod14b = true;
                if (j < path.size() - 1) {
                    //System.out.println("path.get(j).getId() " + path.get(j).getId());
                    //System.out.println("path.get(j+1).getId() " + path.get(j + 1).getId());
                    if (Integer.parseInt(path.get(j).getId()) == 14 && Integer.parseInt(path.get(j + 1).getId()) == 15 && !ds.startnod14anvand) {
                        System.out.println("hej");
                        startnod14a = false;
                        ds.startnod14anvand = true;
                    } else if (Integer.parseInt(path.get(j).getId()) == 14 && Integer.parseInt(path.get(j + 1).getId()) == 6 && !ds.startnod14anvand) {
                        startnod14b = false;
                        ds.startnod14anvand = true;
                    }
                }
                if (j == 0 && startnod14a && startnod14b) {
                    //Fixa fyra
                    if (ds.shelfDirection[ds3.orderStart[istart]].equalsIgnoreCase("N")) {
                        int a = Integer.parseInt(path.get(j).getId());
                        int b = Integer.parseInt(path.get(j + 1).getId());
                        System.out.println("Norr");
                        System.out.println("ds3.orderEnd[istart] " + ds3.orderEnd[istart]);
                        if (a == 32 && b == 31) {
                            GPS += "R";
                        } else if (a == 19 && b == 20) {
                            GPS += "L";
                        } else if (a == 19 && b == 18) {
                            GPS += "R";
                        } else if (a == 14 && b == 6) {
                            GPS += "L";
                        }else if (a - b == 1) {
                            GPS += "L";
                            System.out.println("Norr L");
                            //System.out.println("KUL!!!");
                            //System.out.println("GPS +" + GPS[j]);
                        } else if (b - a == 1) {
                            GPS += "R";
                            System.out.println("Norr R");
                            //System.out.println("KUL2!!!");
                            //System.out.println("GPS +" + GPS[j]);
                        }
                    } else if (ds.shelfDirection[ds3.orderStart[istart]].equalsIgnoreCase("S")) {
                        int a = Integer.parseInt(path.get(j).getId());
                        int b = Integer.parseInt(path.get(j + 1).getId());
                        System.out.println("Söder1");
                        System.out.println("ds3.orderEnd[istart] " + ds3.orderEnd[istart]);
                        boolean kul = false;
                        if (ds3.orderStart[istart] == 4) {
                            GPS += "R";
                            kul = true;
                        } else if (a == 7 && b == 6) {
                            GPS += "L";
                            kul = true;
                        } else if (a == 7 && b == 8) {
                            GPS += "R";
                            kul = true;
                        } else if (a == 14 && b == 6) {
                            GPS += "L";
                            System.out.println("a == 14 && b == 6");
                            kul = true;
                        }
                        if (a - b == 1 && !kul) {
                            GPS += "R";
                            //System.out.println("KUL3!!!");
                            //System.out.println("GPS +" + GPS[j]);
                        } else if (b - a == 1 && !kul) {
                            GPS += "L";
                            //System.out.println("KUL4!!!");
                            //System.out.println("GPS +" + GPS[j]);
                        }
                        //System.out.println("Kul, kanske1");
                    } else if (ds.shelfDirection[ds3.orderStart[istart]].equalsIgnoreCase("V")) {
                        int a = Integer.parseInt(path.get(j).getId());
                        int b = Integer.parseInt(path.get(j + 1).getId());
                        System.out.println("Väster");
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
                    System.out.println("ds.shelfDirection[ds3.orderEnd[istop]] " + ds.shelfDirection[ds3.orderEnd[istop]]);
                    if (ds.shelfDirection[ds3.orderEnd[istop]].equalsIgnoreCase("N")) {
                        int a = Integer.parseInt(path.get(j - 1).getId());
                        int b = Integer.parseInt(path.get(j).getId());
                        boolean kul1 = false;
                        if (a == 31 && b == 32) {
                            GPS += "A";
                            kul1 = true;
                        }
                        if (a - b == 1 && !kul1) {
                            GPS += "A";
                            //System.out.println("KUL7!!!");
                            //System.out.println("GPS +" + GPS[j]);
                        } else if (b - a == 1 && !kul1) {
                            GPS += "B";
                            //System.out.println("KUL8!!!");
                            //System.out.println("GPS +" + GPS[j]);
                        }
                        //System.out.println("Kul, kanske3");
                    } else if (ds.shelfDirection[ds3.orderEnd[istop]].equalsIgnoreCase("S")) {
                        int a = Integer.parseInt(path.get(j - 1).getId());
                        int b = Integer.parseInt(path.get(j).getId());
                        System.out.println("ds3.orderStart[istop] i S " + ds3.orderStart[istop]);
                        if (ds3.orderStart[istop] == 5 && a == 16 && b == 17) {
                            GPS += "B";
                        } else if (ds3.orderEnd[istop] == 2 && a == 31 && b == 32) {
                            GPS += "A";
                        } else if (a - b == 1) {
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
                        System.out.println("ds3.orderStart[istart] " + ds3.orderStart[istart]);
                        if (ds3.orderStart[istart] == 8 && a == 8 && b == 9) {
                            GPS += "A";
                        } else if (ds3.orderStart[istart] == 4) {
                            GPS += "A";
                        } else if (ds3.orderStart[istart] == 4) {
                            GPS += "A";
                        } else if (a - b == 1) {
                            GPS += "A";
                            //System.out.println("KUL11!!!");
                            //System.out.println("GPS +" + GPS[j]);
                        } else if (b - a == 8) {
                            GPS += "B";
                            //System.out.println("KUL12!!!");
                            //System.out.println("GPS +" + GPS[j]);
                        } else if (b - a == 1) {
                            GPS += "B";
                        }
                        //System.out.println("Kul, kanske5");
                    }
                } else if (!startnod14a) {
                    GPS += "J";
                } else if (!startnod14b) {
                    //GPS += "F";
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
                    } else if (a - b > 1 && Math.abs(c - b) == 1 && b != 15 && b != 14) {
                        //Vänster/höger sväng om riktning norr -> söder
                        if (c - b == 1) {
                            GPS += "L";
                        } else if (b - c == 1) {
                            GPS += "R";
                        }
                        System.out.println("KUL43");
                    } else if (b - a > 1 && Math.abs(c - b) == 1 && b != 15 && b != 14) {
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
                //System.out.println("GPS " + GPS);
            }
        }
        return GPS;
    }

    public DataStore optorderlista(DataStore ds3, OptPlan op) {
        LinkedList<Vertex> path;
        DataStore ds4 = new DataStore();
        ds4.orders = ds3.orders;
        int start, stop, diff, mindiff;
        int[] nextnode = new int[20];
        Arrays.fill(nextnode, 20);
        for (int i = 0; i < ds3.orders; i++) {
            boolean notj;
            int[] notja = new int[30];
            Arrays.fill(notja, 50);
            do {
                notj = false;
                mindiff = 1000000000;
                for (int j = 0; j < ds3.orders; j++) {
                    boolean stopp = true;
                    for (int q = 0; q < nextnode.length; q++) {
                        //System.out.println("nextnode[q] " + nextnode[q]);
                        if (nextnode[q] == j || notja[q] == j) {
                            stopp = false;
                        }
                    }
                    if (stopp) {
                        diff = 0;
                        if (i == 0) {
                            start = ds.shelfNode[0];
                            stop = ds.shelfNode[ds3.orderStart[j]];
                            if (start != stop) {
                                System.out.println("Start " + start + " Stop " + stop);
                                path = op.createPlan(start, stop);
                                for (int k = 0; k < path.size() - 1; k++) {
                                    diff = diff + (int) Math.max(Math.abs(ds.nodeY[Integer.parseInt(path.get(k).getId()) - 1] - ds.nodeY[Integer.parseInt(path.get(k + 1).getId()) - 1]), Math.abs(ds.nodeX[Integer.parseInt(path.get(k).getId()) - 1] - ds.nodeX[Integer.parseInt(path.get(k + 1).getId()) - 1]));
                                    System.out.println("Integer.parseInt(path.get(k).getId()) " + Integer.parseInt(path.get(k).getId()));
                                }
                            } else if (start == stop && start == 24) {
                                diff = 0;
                                System.out.println("Hej diff = 0 start");
                            }
                        } else {
                            System.out.println("ds4.orderEnd[i - 1] " + ds4.orderEnd[i - 1] + " ds3.orderStart[j] " + ds3.orderStart[j]);
                            start = ds.shelfNode[ds4.orderEnd[i - 1]];
                            stop = ds.shelfNode[ds3.orderStart[j]];
                            if (start != stop) {
                                //System.out.println("Start " + start + " Stop " + stop);
                                path = op.createPlan(start, stop);
                                for (int k = 0; k < path.size() - 1; k++) {
                                    diff = diff + (int) Math.max(Math.abs(ds.nodeY[Integer.parseInt(path.get(k).getId()) - 1] - ds.nodeY[Integer.parseInt(path.get(k + 1).getId()) - 1]), Math.abs(ds.nodeX[Integer.parseInt(path.get(k).getId()) - 1] - ds.nodeX[Integer.parseInt(path.get(k + 1).getId()) - 1]));
                                    //System.out.println("Integer.parseInt(path.get(k).getId()) " + Integer.parseInt(path.get(k).getId()));
                                }
                            } else if (start == stop && start == 24) {
                                diff = 0;
                                System.out.println("Hej diff = 0 inte start");
                                System.out.println("Start " + start + " stop " + stop);
                            }
                        }
                        System.out.println("Diff " + diff + " mindiff " + mindiff);
                        System.out.println("Start " + start + " stop " + stop);
                        System.out.println("i " + i + " j " + j);
                        if (diff < mindiff) {
                            System.out.println("Inne start mindiff");
                            for (int k = 0; k < j - 1; k++) {
                                System.out.println("ds3.orderEnd[j] " + ds3.orderEnd[j] + " ds3.orderStart[k] " + ds3.orderStart[k]);
                                if (ds3.orderEnd[j] == ds3.orderStart[k] + 1) {
                                    notj = true;
                                    notja[k] = j;
                                    System.out.println("CPCPCPCPCPCP! k " + k + " j " + j);
                                }
                            }
                            System.out.println("Inne i mindiff");
                            mindiff = diff;
                            nextnode[i] = j;
                            ds4.orderStart[i] = ds3.orderStart[j];
                            ds4.orderEnd[i] = ds3.orderEnd[j];
                            //System.out.println(diff + " next node " + nextnode[i]);
                            System.out.println("ds4.orderStart[i] " + ds4.orderStart[i]);
                            System.out.println("ds4.orderEnd[i] " + ds4.orderEnd[i]);
                        }
                    }
                }
            } while (notj);
        }
        return ds4;
    }

    public DataStore onodigaforflytt(DataStore ds2) {
        boolean plockatorder = false;
        for (int i = 0; i < ds3.orders; i++) {
            System.out.println("\n\n\n" + "i " + i);
            for (int j = i + 1; j < ds3.orders; j++) {
                System.out.println("ds2.orderStart[j]\t" + ds2.orderStart[j]);
                System.out.println("ds2.orderEnd[j]\t\t" + ds2.orderEnd[j]);
                System.out.println("j " + j);
                if (ds2.orderStart[j] == ds2.orderEnd[i]) {
                    ds3.orderStart[j] = ds2.orderStart[i];
                    ds3.orderEnd[j] = ds2.orderEnd[j];
                    plockatorder = true;
                    break;
                } else if (j == ds3.orders - 1) {
                    ds3.orderStart[i] = ds2.orderStart[i];
                    ds3.orderEnd[i] = ds2.orderEnd[i];
                }
            }
        }
        if (!plockatorder) {
            ds3 = ds2;
        }
        return ds3;
    }

    OverordnatSystem() {
        ds = new DataStore();
        DataStore ds2 = new DataStore();
        DataStore ds4 = new DataStore();
        ds3 = new DataStore();

        ds.setFileName("/home/itn/Desktop/bastaprojektarbetetjava/OverordnatSystem/Lagernatverk_20130213.csv");
        ds.readNet();
        ds.setFileName("/home/itn/Desktop/bastaprojektarbetetjava/OverordnatSystem/Orders_20130524.csv");
        ds.readOrders();
        ds2.setFileName("/home/itn/Desktop/bastaprojektarbetetjava/OverordnatSystem/Orders_20130524.csv");
        ds2.readOrders();

        cui = new ControlUI(ds);
        cui.setVisible(true);
        cui.showStatus();

        GuiUpdate g1 = new GuiUpdate(ds, cui);
        Thread t2 = new Thread(g1);
        t2.start();

        cui.jTextArea2.append("Ej optimerad orderlista:\n");
        for (int j = 0; j < ds2.orders; j++) {
            if (ds2.orderStart[j] != ds2.orderEnd[j]) {
                cui.jTextArea2.append("" + ds2.orderStart[j]);
                cui.jTextArea2.append(" " + ds2.orderEnd[j] + "\n");
                System.out.println("");
            }
        }
        
        ds3.orders = ds2.orders;
            ds3.fileName = ds2.fileName;
            ds4.orders = ds2.orders;
            ds4.fileName = ds2.fileName;

            System.out.println("\n\n\n\n\n");

            System.out.println("\n\n");
            int start;
            int stop = 0;
            OptPlan op = new OptPlan(ds);

            LinkedList<Vertex> path;

            ds3 = this.onodigaforflytt(ds2);
            ds3 = this.optorderlista(ds3, op);

            Arrays.fill(ds.arcColor, 0);
            cui.repaint();
            //skriv ut den optimerade orderlistan i orderlistafönstret
            /*for(int i = 0; i < ds3.orders; i++) {
            cui.jTextArea2.append(ds3.arcStart[i] + " " + ds3.arcEnd[i]);
            }*/

            cui.jTextArea2.append("Optimerad orderlista:\n");
            for (int j = 0; j < ds3.orders; j++) {
                if (ds3.orderStart[j] != ds3.orderEnd[j]) {
                    cui.jTextArea2.append("" + ds3.orderStart[j]);
                    cui.jTextArea2.append(" " + ds3.orderEnd[j] + "\n");
                    System.out.println("");
                    cui.jTextArea2.setCaretPosition(cui.jTextArea2.getDocument().getLength());
                }
            }

            System.out.println("\n\n");

        while (!ds.start) {
            try {
                Thread.sleep(5000);
            } catch (Exception e) {
                System.out.print(e.toString());
            }
        }

        try {
            StreamConnection anslutning = (StreamConnection) Connector.open("btspp://001204067209:1");//isch robit
            //StreamConnection anslutning = (StreamConnection) Connector.open("btspp://F07BCBF04304:8");
            PrintStream bluetooth_ut = new PrintStream(anslutning.openOutputStream());
            //F07BCBF04304:8 testdator
            BufferedReader bluetooth_in = new BufferedReader(new InputStreamReader(anslutning.openInputStream()));

            BufferedReader tangentbord = new BufferedReader(new InputStreamReader(System.in));

            String meddelande_ut = "start";



            //ds.setFileName("C:/Users/oskst764/Desktop/hej/OverordnatSystem/Lagernatverk_20130213.csv");
            //ds2.setFileName("C:/Users/oskst764/Desktop/hej/OverordnatSystem/Orders_20130211.csv");
            //ds.setFileName("C:/Users/Groggy/Documents/GitHub/bastaprojektarbetetjava/OverordnatSystem/Lagernatverk_20130213.csv");
            //ds.readNet();
            //ds.setFileName("C:/Users/Groggy/Documents/GitHub/bastaprojektarbetetjava/OverordnatSystem/Orders_20130211.csv");
            //ds.readOrders();
            //ds2.setFileName("C:/Users/Groggy/Documents/GitHub/bastaprojektarbetetjava/OverordnatSystem/Orders_20130211.csv");
            //ds2.readOrders();



            

            String GPS;
            for (int i = 0; i < ds3.orders + 1; i++) {
                //stoppa roboten när den precis har lämnat en låda
                while (!ds.start) {
                    try {
                        Thread.sleep(5000);
                    } catch (Exception e) {
                        System.out.print(e.toString());
                    }
                }
                //Förflyttning mellan ordrar
                GPS = "";
                if (i == 0) {
                    start = ds.shelfNode[0];
                    stop = (int) ds.shelfNode[ds3.orderStart[i]];
                    cui.jTextArea3.setText("A -> " + ds3.orderStart[i] + "\n");
                } else {
                    if (i < ds3.orders) {
                        start = (int) ds.shelfNode[ds3.orderEnd[i - 1]];
                        stop = (int) ds.shelfNode[ds3.orderStart[i]];
                        cui.jTextArea3.setText(ds3.orderEnd[i - 1] + " -> " + ds3.orderStart[i] + "\n");
                    } else {
                        start = stop;
                        cui.jTextArea3.setText("FÄRDIG!");
                    }
                }
                if (start != stop) {
                    path = op.createPlan(start, stop);
                    GPS = this.GPSkoordinater(path, i, i);
                } else if (start == stop && start == 24) {
                    GPS += "J";
                }
                cui.jTextArea1.append("\nGPS utan låda:\n" + GPS + "\n\n");
                System.out.println("GPS.längd " + GPS.length() + "\n");
                cui.jTextArea1.setCaretPosition(cui.jTextArea1.getDocument().getLength());

                //Om ingen längd på GPS-koordinaterna ges behöver ingenting skickas till roboten
                if (GPS.length() > 0) {
                    if (meddelande_ut.equalsIgnoreCase("start")) {
                        for (int o = GPS.length(); o < 12; o++) {
                            GPS += "z";
                        }
                        meddelande_ut = GPS;
                        //meddelande_ut = "x" + meddelande_ut + "z";
                        System.out.println("mellan meddelande_ut " + meddelande_ut);
                        ds.robotX = ds.nodeX[start - 1];
                        ds.robotY = ds.nodeY[start - 1];
                        cui.repaint();
                        bluetooth_ut.print(meddelande_ut);
                    }
                    meddelande_ut = this.bluetoothkom(bluetooth_in);

                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException ex) {
                        System.out.println("Fel");
                    }
                }
                Arrays.fill(ds.arcColor, 0);

                //Räkna ut förflyttning av LÅDA!
                GPS = "";
                if (i < ds3.orders) {
                    start = (int) ds.shelfNode[ds3.orderStart[i]];
                    stop = (int) ds.shelfNode[ds3.orderEnd[i]];
                    cui.jTextArea3.setText(ds3.orderStart[i] + " -> " + ds3.orderEnd[i] + "\n");
                } else {
                    start = stop;
                    cui.jTextArea3.setText("Färdigt");
                }
                if (start != stop) {
                    path = op.createPlan(start, stop);
                    GPS = this.GPSkoordinater(path, i, i);
                } else if (start == stop && start == 24) {
                    GPS += "J";
                }
                cui.jTextArea1.append("\nGPS med låda:\n" + GPS + "\n\n");
                System.out.println("GPS.längd " + GPS.length() + "\n");
                cui.jTextArea1.setCaretPosition(cui.jTextArea1.getDocument().getLength());

                //Om ingen längd på GPS-koordinaterna ges behöver ingenting skickas till roboten
                if (GPS.length() > 0) {
                    if (meddelande_ut.equalsIgnoreCase("start")) {
                        for (int o = GPS.length(); o < 12; o++) {
                            GPS += "z";
                        }
                        meddelande_ut = GPS;
                        //meddelande_ut = "x" + meddelande_ut + "z";
                        System.out.println("under meddelande_ut " + meddelande_ut);
                        bluetooth_ut.print(meddelande_ut);
                    }

                    ds.robotX = ds.nodeX[start - 1];
                    ds.robotY = ds.nodeY[start - 1];
                    cui.repaint();
                    meddelande_ut = this.bluetoothkom(bluetooth_in);

                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException ex) {
                        System.out.println("Fel");
                    }
                }
                Arrays.fill(ds.arcColor, 0);
                ds.robotX = ds.nodeX[stop - 1];
                ds.robotY = ds.nodeY[stop - 1];
                cui.repaint();
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
