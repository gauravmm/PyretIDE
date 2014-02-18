/*
 * Automatically generated file. Do not modify directly.
 * Created 2014-02-17 22:31:02.896000 by Gaurav Manek
 */

package edu.brown.cs.cutlass.pyret.ast;

import java.util.List;

/**
 * This class corresponds to the s_datatype_constructor variant of type Constructor
 * in the Pyret AST.
 *
 * data s_datatype_constructor:
 *   s_datatype_constructor (l :: Loc, self :: String, body :: Expr)
 *   ...
 * end
 * 
 * @author Edward Teach
 */
public final class s_datatype_constructor implements Constructor {
	// Variables:
	public final Loc l;
	public final String self;
	public final Expr body;

	/**
	 * Constructor, initializes all fields.
	 */
	public s_datatype_constructor(Loc l, String self, Expr body) {
		this.l = l;
		this.self = self;
		this.body = body;
    }
	
}
