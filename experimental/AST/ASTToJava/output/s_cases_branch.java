/*
 * Automatically generated file. Do not modify directly.
 * Created 2014-02-17 22:31:02.896000 by Gaurav Manek
 */

package edu.brown.cs.cutlass.pyret.ast;

import java.util.List;

/**
 * This class corresponds to the s_cases_branch variant of type CasesBranch
 * in the Pyret AST.
 *
 * data s_cases_branch:
 *   s_cases_branch (l :: Loc, name :: String, args :: List<Bind>, body :: Expr)
 *   ...
 * end
 * 
 * @author Edward Teach
 */
public final class s_cases_branch implements CasesBranch {
	// Variables:
	public final Loc l;
	public final String name;
	public final List<Bind> args;
	public final Expr body;

	/**
	 * Constructor, initializes all fields.
	 */
	public s_cases_branch(Loc l, String name, List<Bind> args, Expr body) {
		this.l = l;
		this.name = name;
		this.args = args;
		this.body = body;
    }
	
}
