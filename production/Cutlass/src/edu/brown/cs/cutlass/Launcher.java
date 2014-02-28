/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.brown.cs.cutlass;

import edu.brown.cs.cutlass.util.Lumberjack;
import javax.swing.UIManager;

/**
 *
 * @author Gaurav Manek
 */
public class Launcher {

    /**
     * @param args the command line arguments
     */
    public static void main(final String[] args) {
        
        // From NetBeans:
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            // Use system default:
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            /*
             for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
             if ("Nimbus".equals(info.getName())) {
             javax.swing.UIManager.setLookAndFeel(info.getClassName());
             break;
             }
             }*/
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            Lumberjack.log(Lumberjack.Level.WARN, ex);
        }
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Launcher(args);
            }
        });
    }

    private Launcher(String[] args) {
        // Need to process the commandline input
        // Set up logger (including CLI for that)
        // Write logs to disk at the end, if the CLI calls for that
        // Provide CLI usage
        
        // Hand off control to FrmMain
    }

}
