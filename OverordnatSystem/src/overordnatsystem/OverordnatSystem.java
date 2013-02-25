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

    OverordnatSystem() {
        ds = new DataStore();
        ds.setFileName("C:/Users/oskst764/Desktop/hej/OverordnatSystem/Lagernatverk_20130213.csv");
        
        ds.readNet();
        
        OptPlan op = new OptPlan(ds);
        op.createPlan();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        OverordnatSystem x = new OverordnatSystem();
    }
}
