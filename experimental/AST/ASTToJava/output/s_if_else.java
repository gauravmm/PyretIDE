/*
 * Automatically generated file. Do not modify directly.
 * Created 2014-02-17 22:31:02.896000 by Gaurav Manek
 */

package edu.brown.cs.cutlass.pyret.ast;

import java.util.List;

/**
 * This class corresponds to the s_if_else variant of type Expr
 * in the Pyret AST.
 *
 * data s_if_else:
 *   s_if_else (l :: Loc, branches :: List<IfBranch>, _else :: Expr)
 *   ...
 * end
 * 
 * @author Edward Teach
 */
public final class s_if_else implements Expr {
	// Variables:
	public final Loc l;
	public final List<IfBranch> branches;
	public final Expr _else;

	/**
	 * Constructor, initializes all fields.
	 */
	public s_if_else(Loc l, List<IfBranch> branches, Expr _else) {
		this.l = l;
		this.branches = branches;
		this._else = _else;
    }
	
}
