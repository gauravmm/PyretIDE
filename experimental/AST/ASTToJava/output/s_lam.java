/*
 * Automatically generated file. Do not modify directly.
 * Created 2014-02-17 22:31:02.896000 by Gaurav Manek
 */

package edu.brown.cs.cutlass.pyret.ast;

import java.util.List;

/**
 * This class corresponds to the s_lam variant of type Expr
 * in the Pyret AST.
 *
 * data s_lam:
 *   s_lam (l :: Loc, params :: List<String>, args :: List<Bind>, ann :: Ann, doc :: String, body :: Expr, check :: Expr)
 *   ...
 * end
 * 
 * @author Edward Teach
 */
public final class s_lam implements Expr {
	// Variables:
	public final Loc l;
	public final List<String> params;
	public final List<Bind> args;
	public final Ann ann;
	public final String doc;
	public final Expr body;
	public final Expr check;

	/**
	 * Constructor, initializes all fields.
	 */
	public s_lam(Loc l, List<String> params, List<Bind> args, Ann ann, String doc, Expr body, Expr check) {
		this.l = l;
		this.params = params;
		this.args = args;
		this.ann = ann;
		this.doc = doc;
		this.body = body;
		this.check = check;
    }
	
}
