/*
 * Automatically generated file. Do not modify directly.
 * Created 2014-02-17 22:31:02.896000 by Gaurav Manek
 */

package edu.brown.cs.cutlass.pyret.ast;

import java.util.List;

/**
 * This class corresponds to the a_pred variant of type Ann
 * in the Pyret AST.
 *
 * data a_pred:
 *   a_pred (l :: Loc, ann :: Ann, exp :: Expr)
 *   ...
 * end
 * 
 * @author Edward Teach
 */
public final class a_pred implements Ann {
	// Variables:
	public final Loc l;
	public final Ann ann;
	public final Expr exp;

	/**
	 * Constructor, initializes all fields.
	 */
	public a_pred(Loc l, Ann ann, Expr exp) {
		this.l = l;
		this.ann = ann;
		this.exp = exp;
    }
	
}
