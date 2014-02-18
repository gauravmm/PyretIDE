/*
 * Automatically generated file. Do not modify directly.
 * Created 2014-02-17 22:31:02.896000 by Gaurav Manek
 */

package edu.brown.cs.cutlass.pyret.ast;

import java.util.List;

/**
 * This class corresponds to the s_if variant of type Expr
 * in the Pyret AST.
 *
 * data s_if:
 *   s_if (l :: Loc, branches :: List<IfBranch>)
 *   ...
 * end
 * 
 * @author Edward Teach
 */
public final class s_if implements Expr {
	// Variables:
	public final Loc l;
	public final List<IfBranch> branches;

	/**
	 * Constructor, initializes all fields.
	 */
	public s_if(Loc l, List<IfBranch> branches) {
		this.l = l;
		this.branches = branches;
    }
	
}
