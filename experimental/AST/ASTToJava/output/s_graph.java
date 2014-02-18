/*
 * Automatically generated file. Do not modify directly.
 * Created 2014-02-17 22:31:02.896000 by Gaurav Manek
 */

package edu.brown.cs.cutlass.pyret.ast;

import java.util.List;

/**
 * This class corresponds to the s_graph variant of type Expr
 * in the Pyret AST.
 *
 * data s_graph:
 *   s_graph (l :: Loc, bindings :: List<is-s_let>)
 *   ...
 * end
 * 
 * @author Edward Teach
 */
public final class s_graph implements Expr {
	// Variables:
	public final Loc l;
	public final List<is-s_let> bindings;

	/**
	 * Constructor, initializes all fields.
	 */
	public s_graph(Loc l, List<is-s_let> bindings) {
		this.l = l;
		this.bindings = bindings;
    }
	
}
