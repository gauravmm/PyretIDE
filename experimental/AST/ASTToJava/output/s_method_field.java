/*
 * Automatically generated file. Do not modify directly.
 * Created 2014-02-17 22:31:02.896000 by Gaurav Manek
 */

package edu.brown.cs.cutlass.pyret.ast;

import java.util.List;

/**
 * This class corresponds to the s_method_field variant of type Member
 * in the Pyret AST.
 *
 * data s_method_field:
 *   s_method_field (l :: Loc, name :: Expr, args :: List<Bind>, ann :: Ann, doc :: String, body :: Expr, check :: Expr)
 *   ...
 * end
 * 
 * @author Edward Teach
 */
public final class s_method_field implements Member {
	// Variables:
	public final Loc l;
	public final Expr name;
	public final List<Bind> args;
	public final Ann ann;
	public final String doc;
	public final Expr body;
	public final Expr check;

	/**
	 * Constructor, initializes all fields.
	 */
	public s_method_field(Loc l, Expr name, List<Bind> args, Ann ann, String doc, Expr body, Expr check) {
		this.l = l;
		this.name = name;
		this.args = args;
		this.ann = ann;
		this.doc = doc;
		this.body = body;
		this.check = check;
    }
	
}
