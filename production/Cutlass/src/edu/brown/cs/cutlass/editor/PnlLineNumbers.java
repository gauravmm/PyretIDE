/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.editor;

import edu.brown.cs.cutlass.util.Lumberjack;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Collections;
import java.util.List;
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
        this.setLayout(null);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        Rectangle visibleRect = editorPane.getVisibleRect();

        List<Integer> lineStartOffsets = editorPane.getLineStartOffsets();
        //System.out.println(lineStartOffsets);

        // Bounds checks
        if (lineStartOffsets.isEmpty()) {
            throw new IllegalStateException("lineStartOffsets is empty");
        } else if (lineStartOffsets.get(0) != 0) {
            throw new IllegalStateException("lineStartOffsets does not start from 0.");
        }

        Integer startPos = editorPane.viewToModel(visibleRect.getLocation());
        Integer endPos = editorPane.viewToModel(new Point(visibleRect.x + visibleRect.width, visibleRect.y + visibleRect.height));

        int start = processBinarySearch(Collections.binarySearch(lineStartOffsets, startPos));
        int end = processBinarySearch(Collections.binarySearch(lineStartOffsets, endPos));

        this.removeAll();
        if (start == end) {
            return;
        } else if (start > end) {
            throw new IllegalStateException("Start is after End in line numbering request.");
        } else if (start < 0) {
            throw new IllegalStateException("Start is negative");
        }

        int yBias = -1 * visibleRect.getLocation().y + 3;
        int lineNum = start;
        while (lineNum <= end) {
            try {
                JLabel lineLbl;
                lineLbl = new LabelLineNumber(lineNum);
                this.add(lineLbl);
                lineLbl.setSize(new Dimension(this.getWidth(), 14));
                lineLbl.setLocation(0, editorPane.modelToView(lineStartOffsets.get(lineNum)).y + yBias);
                lineNum++;
            } catch (BadLocationException ex) {
                Lumberjack.log(Lumberjack.Level.ERROR, ex);
            }
        }

        this.revalidate();
    }

    private int processBinarySearch(int v) {
        if (v >= 0) {
            return v;
        } else {
            return -(v + 1) - 1;
        }
    }

    private static class LabelLineNumber extends JLabel {

        // TODO: Monospace
        public LabelLineNumber(Integer number) {
            super(String.format("<html><font face=\"verdana\" size=\"2\">%d ", number + 1));
            number = number + 1;
            this.setHorizontalAlignment(SwingConstants.RIGHT);
            this.setForeground(Color.GRAY);
            if (number % 10 == 0) {
                this.setForeground(Color.DARK_GRAY);
            }
        }

    }

}
