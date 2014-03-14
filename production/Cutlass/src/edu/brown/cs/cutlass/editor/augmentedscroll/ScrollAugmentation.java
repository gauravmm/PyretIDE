/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.editor.augmentedscroll;

import edu.brown.cs.cutlass.editor.EditorJumpTo;
import edu.brown.cs.cutlass.util.Option;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;

/**
 *
 * @author Gaurav Manek
 */
public abstract class ScrollAugmentation {

    private final EditorJumpTo jumpOnClick;
    private final Option<String> tooltip;

    /**
     * Create a ScrollAugmentation with a jumpTo instruction.
     *
     * @param jumpOnClick The EditorJumpTo to use when clicked.
     */
    public ScrollAugmentation(EditorJumpTo jumpOnClick) {
        this.jumpOnClick = jumpOnClick;
        this.tooltip = new Option<>();
    }

    /**
     * Create a ScrollAugmentation with a jumpTo instruction and some text to
     * display in the tooltip dialog.
     *
     * @param jumpOnClick The EditorJumpTo to use when clicked.
     * @param tooltip The content of the tooltip dialog.
     */
    public ScrollAugmentation(EditorJumpTo jumpOnClick, String tooltip) {
        this.jumpOnClick = jumpOnClick;
        this.tooltip = new Option<>(tooltip);
    }

    /**
     * Get the String to show when the mouse hovers over this augmentation for
     * some time.
     *
     * @return The help text as a String, if one is defined.
     */
    public Option<String> getToolTipText() {
        return tooltip;
    }

    /**
     * Handle a click using the EditorJumpTo to move the cursor to the
     * corresponding position in the document.
     */
    public void handleClick() {
        jumpOnClick.jump();
    }

    /**
     * Get the color to render the highlighted bar segment on the scrollbar.
     *
     * @return An Option that has a Color if one should be drawn, or an empty
     * Option otherwise.
     */
    public abstract Option<Color> getBarColor();

    /**
     * Advanced custom rendering - directly draw any graphical representation of
     * the code. For most applications, this need not be overridden.
     *
     * @param g2d The Graphics2D object to draw onto.
     * @param topLeft The coordinates of the top-left area that this object is
     * allowed to draw in.
     * @param size The size of the area that this object is allowed to draw in.
     */
    public void getBarColor(Graphics2D g2d, Point topLeft, Dimension size) {

    }

}
