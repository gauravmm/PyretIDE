/*
 * Automatically generated file. Do not modify directly.
 * Created 2014-02-17 22:31:02.896000 by Gaurav Manek
 */

package edu.brown.cs.cutlass.pyret.ast;

import java.util.List;

/**
 * This class corresponds to the s_cases variant of type Expr
 * in the Pyret AST.
 *
 * data s_cases:
 *   s_cases (l :: Loc, type :: Ann, val :: Expr, branches :: List<CasesBranch>)
 *   ...
 * end
 * 
 * @author Edward Teach
 */
public final class s_cases implements Expr {
	// Variables:
	public final Loc l;
	public final Ann type;
	public final Expr val;
	public final List<CasesBranch> branches;

	/**
	 * Constructor, initializes all fields.
	 */
	public s_cases(Loc l, Ann type, Expr val, List<CasesBranch> branches) {
		this.l = l;
		this.type = type;
		this.val = val;
		this.branches = branches;
    }
	
}
