/*
 * Automatically generated file. Do not modify directly.
 * Created 2014-02-17 22:31:02.896000 by Gaurav Manek
 */

package edu.brown.cs.cutlass.pyret.ast;

import java.util.List;

/**
 * This class corresponds to the a_app variant of type Ann
 * in the Pyret AST.
 *
 * data a_app:
 *   a_app (l :: Loc, ann :: Ann, args :: List<Ann>)
 *   ...
 * end
 * 
 * @author Edward Teach
 */
public final class a_app implements Ann {
	// Variables:
	public final Loc l;
	public final Ann ann;
	public final List<Ann> args;

	/**
	 * Constructor, initializes all fields.
	 */
	public a_app(Loc l, Ann ann, List<Ann> args) {
		this.l = l;
		this.ann = ann;
		this.args = args;
    }
	
}
