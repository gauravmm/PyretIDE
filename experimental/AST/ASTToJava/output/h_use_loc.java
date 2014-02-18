/*
 * Automatically generated file. Do not modify directly.
 * Created 2014-02-17 22:31:02.896000 by Gaurav Manek
 */

package edu.brown.cs.cutlass.pyret.ast;

import java.util.List;

/**
 * This class corresponds to the h_use_loc variant of type Hint
 * in the Pyret AST.
 *
 * data h_use_loc:
 *   h_use_loc (l :: Loc)
 *   ...
 * end
 * 
 * @author Edward Teach
 */
public final class h_use_loc implements Hint {
	// Variables:
	public final Loc l;

	/**
	 * Constructor, initializes all fields.
	 */
	public h_use_loc(Loc l) {
		this.l = l;
    }
	
}
