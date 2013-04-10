/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package overordnatsystem;

import java.util.Random;

/**
 *
 * 
 */
public class GuiUpdate implements Runnable {

    private int sleepTime;
    private static Random generator = new Random();
    private ControlUI cui;
    private DataStore ds;

    public GuiUpdate(DataStore ds, ControlUI cui) {
        this.cui = cui;
        this.ds = ds;
        sleepTime = generator.nextInt(20000);
    }

    @Override
    public void run() {
        try {
            while(!ds.updateUIflag){
             Thread.sleep(100);
            }
            cui.appendStatus("GuiUpdate startar och kommer att köra i "
                    + sleepTime + " millisekunder.");
            int i = 1;
            while (i <= 20) {
                Thread.sleep(sleepTime / 20);
                cui.appendStatus("Jag är tråd GuiUpdate! För " + i + ":te gången.");
                ds.robotX = ds.robotX + 10; //blir ds.robotX = ds.nodeX[slutnoden när roboten har sagt att den är klar] - 1  nodeX[GPS[GPS.length-1]]
                cui.repaint();
                i++;
            }
        } catch (InterruptedException exception) {
        }
        cui.appendStatus("GuiUpdate är nu klar!");
    }
}
