/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.editor.callgraph;

import edu.brown.cs.cutlass.editor.callgraph.CallGraphEntry;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;

/**
 *
 * @author Gaurav Manek
 */
public class CallGraphEntryRenderer implements ListCellRenderer<CallGraphEntry> {

    private static final Color COL_CURRENT = new Color(27, 161, 226);
    private static final Color COL_DEFAULT = Color.WHITE;

    @Override
    public Component getListCellRendererComponent(JList<? extends CallGraphEntry> list, final CallGraphEntry value, int index, boolean isSelected, boolean cellHasFocus) {
        JPanel lbl = new JPanel();
        lbl.setLayout(new BorderLayout());
        lbl.add(new JLabel(value.name), BorderLayout.CENTER);
        lbl.setOpaque(true);
        
        if (value.dataOf.hasData()) {
            JLabel lblDataOf = new JLabel(value.dataOf.getData() + " ");
            lblDataOf.setHorizontalAlignment(SwingConstants.RIGHT);
            lblDataOf.setOpaque(true);
            lblDataOf.setForeground(value.dataColor.hasData() ? value.dataColor.getData() : COL_DEFAULT);
            //lblDataOf.setForeground(COL_DATAOFNAME);
            //lbl.setBackground(COL_DATA);
            lbl.add(lblDataOf, BorderLayout.EAST);
        } else {
            lbl.setBackground(Color.WHITE);
        }
        
        lbl.add(new CallGraphIcon(value.isCurrent, value.callsCurrent, value.isCalledByCurrent), BorderLayout.WEST);
        
        if (value.isCurrent) {
            lbl.setBackground(COL_CURRENT);
        }
        return lbl;
    }

    private static class CallGraphIcon extends JLabel {

        private final Dimension sz;
        private final boolean current;
        private final boolean callsCurrent;
        private final boolean calledByCurrent;

        private CallGraphIcon(boolean current, boolean callsCurrent, boolean calledByCurrent) {
            super();
            if (current) {
                if (callsCurrent) {
                    super.setText("\u21BB");
                } else {
                    super.setText("\u25C6");
                }
            } else {
                super.setText(String.format("%s\u25C6%s", calledByCurrent ? "\u25BA" : "   ", callsCurrent ? "\u25BA" : "   "));
            }

            super.setHorizontalAlignment(CENTER);

            sz = new Dimension(48, super.getPreferredSize().height);

            this.current = current;
            this.callsCurrent = callsCurrent;
            this.calledByCurrent = calledByCurrent;
        }

        @Override
        public Dimension getMinimumSize() {
            return sz;
        }

        @Override
        public Dimension getPreferredSize() {
            return sz;
        }

    }

}
