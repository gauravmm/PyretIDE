/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.editor;

import edu.brown.cs.cutlass.editor.callgraph.CallGraphEntry;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 *
 * @author Gaurav Manek
 */
public class CallGraphEntryRenderer implements ListCellRenderer<CallGraphEntry> {

    private static final Color COL_CURRENT = new Color(27, 161, 226);
    private static final Color COL_FUNCTION = Color.WHITE;
    private static final Color COL_DATA = new Color(162, 193, 57);
    

    @Override
    public Component getListCellRendererComponent(JList<? extends CallGraphEntry> list, final CallGraphEntry value, int index, boolean isSelected, boolean cellHasFocus) {
        JLabel lbl = new JLabel(value.name);
        lbl.setOpaque(true);
        lbl.setBackground(value.dataOf.hasData() ? COL_DATA : COL_FUNCTION);
        if (value.isCurrent) {
            lbl.setBackground(COL_CURRENT);
        }
        return lbl;
    }

}
