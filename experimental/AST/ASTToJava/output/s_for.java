/*
 * Automatically generated file. Do not modify directly.
 * Created 2014-02-17 22:31:02.896000 by Gaurav Manek
 */

package edu.brown.cs.cutlass.pyret.ast;

import java.util.List;

/**
 * This class corresponds to the s_for variant of type Expr
 * in the Pyret AST.
 *
 * data s_for:
 *   s_for (l :: Loc, iterator :: Expr, bindings :: List<ForBind>, ann :: Ann, body :: Expr)
 *   ...
 * end
 * 
 * @author Edward Teach
 */
public final class s_for implements Expr {
	// Variables:
	public final Loc l;
	public final Expr iterator;
	public final List<ForBind> bindings;
	public final Ann ann;
	public final Expr body;

	/**
	 * Constructor, initializes all fields.
	 */
	public s_for(Loc l, Expr iterator, List<ForBind> bindings, Ann ann, Expr body) {
		this.l = l;
		this.iterator = iterator;
		this.bindings = bindings;
		this.ann = ann;
		this.body = body;
    }
	
}
