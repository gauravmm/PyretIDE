/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.sys.io.laskura.ui;

import edu.brown.cs.cutlass.util.Option;
import edu.brown.cs.cutlass.util.Pair;
import java.net.URL;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.getRootFrame;

/**
 *
 * @author Gaurav Manek
 */
public class PnlLaskuraLogin extends javax.swing.JPanel {

    private static final long serialVersionUID = 1993464977048125519L;

    public static Option<Pair<String, String>> getLogin(URL server) {
        PnlLaskuraLogin pnlLogin = new PnlLaskuraLogin(server);
        JOptionPane pane = new JOptionPane(pnlLogin, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION, null, new Object[]{"Login", "Cancel"}, (Object) "Login");
        pane.setComponentOrientation((getRootFrame()).getComponentOrientation());
        pane.selectInitialValue();
        final JDialog dialog = pane.createDialog(null, "Login");
        dialog.setVisible(true);
        dialog.dispose();

        Object value = pane.getValue();
        if (value.equals("Login")) {
            return new Option<>(pnlLogin.getLoginData());
        } else {
            return new Option<>();
        }
    }

    /**
     * Creates new form LaskuraLogin
     */
    public PnlLaskuraLogin(URL server) {
        initComponents();
        this.lblServer.setText(server.toString());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtUsername = new javax.swing.JTextField();
        txtPassword = new javax.swing.JPasswordField();
        jLabel1 = new javax.swing.JLabel();
        lblServer = new javax.swing.JLabel();

        txtUsername.setText("captain");

        txtPassword.setText("edward");

        jLabel1.setBackground(new java.awt.Color(255, 0, 0));
        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("<html><center>REMOVE<br />BEFORE<br />FLIGHT</center></html>");
        jLabel1.setOpaque(true);

        lblServer.setFont(lblServer.getFont().deriveFont((float)10));
        lblServer.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblServer.setText("Server");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtUsername)
                    .addComponent(txtPassword)
                    .addComponent(lblServer, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(lblServer)
                .addGap(10, 10, 10)
                .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lblServer;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables

    private Pair<String, String> getLoginData() {
        return new Pair<>(this.txtUsername.getText(), new String(this.txtPassword.getPassword()));
    }
}
