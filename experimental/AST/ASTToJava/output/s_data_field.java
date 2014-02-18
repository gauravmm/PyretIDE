/*
 * Automatically generated file. Do not modify directly.
 * Created 2014-02-17 22:31:02.896000 by Gaurav Manek
 */

package edu.brown.cs.cutlass.pyret.ast;

import java.util.List;

/**
 * This class corresponds to the s_data_field variant of type Member
 * in the Pyret AST.
 *
 * data s_data_field:
 *   s_data_field (l :: Loc, name :: Expr, value :: Expr)
 *   ...
 * end
 * 
 * @author Edward Teach
 */
public final class s_data_field implements Member {
	// Variables:
	public final Loc l;
	public final Expr name;
	public final Expr value;

	/**
	 * Constructor, initializes all fields.
	 */
	public s_data_field(Loc l, Expr name, Expr value) {
		this.l = l;
		this.name = name;
		this.value = value;
    }
	
}
