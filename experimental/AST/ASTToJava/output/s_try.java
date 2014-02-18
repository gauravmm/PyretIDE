/*
 * Automatically generated file. Do not modify directly.
 * Created 2014-02-17 22:31:02.896000 by Gaurav Manek
 */

package edu.brown.cs.cutlass.pyret.ast;

import java.util.List;

/**
 * This class corresponds to the s_try variant of type Expr
 * in the Pyret AST.
 *
 * data s_try:
 *   s_try (l :: Loc, body :: Expr, id :: Bind, _except :: Expr)
 *   ...
 * end
 * 
 * @author Edward Teach
 */
public final class s_try implements Expr {
	// Variables:
	public final Loc l;
	public final Expr body;
	public final Bind id;
	public final Expr _except;

	/**
	 * Constructor, initializes all fields.
	 */
	public s_try(Loc l, Expr body, Bind id, Expr _except) {
		this.l = l;
		this.body = body;
		this.id = id;
		this._except = _except;
    }
	
}
