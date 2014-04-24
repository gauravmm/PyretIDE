/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.editor;

import edu.brown.cs.cutlass.util.Lumberjack;
import edu.brown.cs.cutlass.util.Lumberjack;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.BadLocationException;

/**
 *
 * @author Gaurav Manek
 */
public class PnlLineNumbers extends JPanel implements ChangeListener {

    private final StyledUndoPane editorPane;

    PnlLineNumbers(StyledUndoPane editorPane) {
        this.editorPane = editorPane;
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        Rectangle visibleRect = editorPane.getVisibleRect();
        
        List<Integer> lineStartOffsets = editorPane.getLineStartOffsets();
        System.out.println(lineStartOffsets);
        
        int start = editorPane.viewToModel(visibleRect.getLocation());
        int end = editorPane.viewToModel(new Point(visibleRect.x + visibleRect.width, visibleRect.y + visibleRect.height));
        
        this.removeAll();
        if (start == end) {
            return;
        } else if (start > end) {
            throw new IllegalStateException("Start is after End in line numbering request.");
        } else if (start < 0) {
            throw new IllegalStateException("Start is negative");
        }

        // Bounds checks
        if (lineStartOffsets.isEmpty()) {
            throw new IllegalStateException("lineStartOffsets is empty");
        } else if (lineStartOffsets.get(0) != 0) {
            throw new IllegalStateException("lineStartOffsets does not start from 0.");
        }

        int lineNum = start;
        while (lineNum <= end) {
            try {
                JLabel lineLbl;
                lineLbl = new LabelLineNumber(lineNum);
                this.add(lineLbl);
                lineLbl.setPreferredSize(new Dimension(this.getWidth(), 16));
                lineLbl.setLocation(0, editorPane.modelToView(lineStartOffsets.get(lineNum)).y);
                lineNum++;
            } catch (BadLocationException ex) {
                Lumberjack.log(Lumberjack.Level.ERROR, ex);
            }
        }
    }

    private static class LabelLineNumber extends JLabel {

        public LabelLineNumber(Integer number) {
            super(Integer.toString(number + 1));
            number = number + 1;
            this.setHorizontalAlignment(SwingConstants.RIGHT);
            if (number % 10 == 0) {
                this.setFont(this.getFont().deriveFont(Font.BOLD));
            }
        }
    }

}
