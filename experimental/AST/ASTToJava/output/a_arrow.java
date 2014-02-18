/*
 * Automatically generated file. Do not modify directly.
 * Created 2014-02-17 22:31:02.896000 by Gaurav Manek
 */

package edu.brown.cs.cutlass.pyret.ast;

import java.util.List;

/**
 * This class corresponds to the a_arrow variant of type Ann
 * in the Pyret AST.
 *
 * data a_arrow:
 *   a_arrow (l :: Loc, args :: List<Ann>, ret :: Ann)
 *   ...
 * end
 * 
 * @author Edward Teach
 */
public final class a_arrow implements Ann {
	// Variables:
	public final Loc l;
	public final List<Ann> args;
	public final Ann ret;

	/**
	 * Constructor, initializes all fields.
	 */
	public a_arrow(Loc l, List<Ann> args, Ann ret) {
		this.l = l;
		this.args = args;
		this.ret = ret;
    }
	
}
