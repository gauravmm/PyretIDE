/*
 * Automatically generated file. Do not modify directly.
 * Created 2014-02-17 22:31:02.896000 by Gaurav Manek
 */

package edu.brown.cs.cutlass.pyret.ast;

import java.util.List;

/**
 * This class corresponds to the s_datatype variant of type Expr
 * in the Pyret AST.
 *
 * data s_datatype:
 *   s_datatype (l :: Loc, name :: String, params :: List<String>, variants :: List<Variant>, check :: Expr)
 *   ...
 * end
 * 
 * @author Edward Teach
 */
public final class s_datatype implements Expr {
	// Variables:
	public final Loc l;
	public final String name;
	public final List<String> params;
	public final List<Variant> variants;
	public final Expr check;

	/**
	 * Constructor, initializes all fields.
	 */
	public s_datatype(Loc l, String name, List<String> params, List<Variant> variants, Expr check) {
		this.l = l;
		this.name = name;
		this.params = params;
		this.variants = variants;
		this.check = check;
    }
	
}
