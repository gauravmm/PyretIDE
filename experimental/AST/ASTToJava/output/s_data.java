/*
 * Automatically generated file. Do not modify directly.
 * Created 2014-02-17 22:31:02.896000 by Gaurav Manek
 */

package edu.brown.cs.cutlass.pyret.ast;

import java.util.List;

/**
 * This class corresponds to the s_data variant of type Expr
 * in the Pyret AST.
 *
 * data s_data:
 *   s_data (l :: Loc, name :: String, params :: List<String>, mixins :: List<Expr>, variants :: List<Variant>, shared_members :: List<Member>, check :: Expr)
 *   ...
 * end
 * 
 * @author Edward Teach
 */
public final class s_data implements Expr {
	// Variables:
	public final Loc l;
	public final String name;
	public final List<String> params;
	public final List<Expr> mixins;
	public final List<Variant> variants;
	public final List<Member> shared_members;
	public final Expr check;

	/**
	 * Constructor, initializes all fields.
	 */
	public s_data(Loc l, String name, List<String> params, List<Expr> mixins, List<Variant> variants, List<Member> shared_members, Expr check) {
		this.l = l;
		this.name = name;
		this.params = params;
		this.mixins = mixins;
		this.variants = variants;
		this.shared_members = shared_members;
		this.check = check;
    }
	
}
