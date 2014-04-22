/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.editor;

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
    private static final Color COL_FUNCTION = Color.WHITE;
    private static final Color COL_DATA = new Color(192, 224, 255);
    private static final Color COL_DATAOFNAME = Color.WHITE;

    @Override
    public Component getListCellRendererComponent(JList<? extends CallGraphEntry> list, final CallGraphEntry value, int index, boolean isSelected, boolean cellHasFocus) {
        JPanel lbl = new JPanel();
        lbl.setLayout(new BorderLayout());
        lbl.add(new JLabel(value.name), BorderLayout.CENTER);
        if (value.dataOf.hasData()) {
            JLabel lblDataOf = new JLabel(value.dataOf.getData() + " ");
            lblDataOf.setHorizontalAlignment(SwingConstants.RIGHT);
            lblDataOf.setForeground(COL_DATAOFNAME);
            lbl.add(lblDataOf, BorderLayout.EAST);
        }
        lbl.add(new CallGraphIcon(value.isCurrent, value.callsCurrent, value.isCalledByCurrent), BorderLayout.WEST);
        lbl.setOpaque(true);
        lbl.setBackground(value.dataOf.hasData() ? COL_DATA : COL_FUNCTION);
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
                    super.setText("\u22C5");
                }
            } else {
                super.setText(String.format("%s\u22C5%s", calledByCurrent ? "\u27A7" : "  ", callsCurrent ? "\u27A7" : "  "));
            }
            
            super.setHorizontalAlignment(CENTER);

            sz = new Dimension(30, super.getPreferredSize().height);

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
