/*
 * Automatically generated file. Do not modify directly.
 * Created 2014-02-17 22:31:02.896000 by Gaurav Manek
 */

package edu.brown.cs.cutlass.pyret.ast;

import java.util.List;

/**
 * This class corresponds to the s_id variant of type Expr
 * in the Pyret AST.
 *
 * data s_id:
 *   s_id (l :: Loc, id :: String)
 *   ...
 * end
 * 
 * @author Edward Teach
 */
public final class s_id implements Expr {
	// Variables:
	public final Loc l;
	public final String id;

	/**
	 * Constructor, initializes all fields.
	 */
	public s_id(Loc l, String id) {
		this.l = l;
		this.id = id;
    }
	
}
