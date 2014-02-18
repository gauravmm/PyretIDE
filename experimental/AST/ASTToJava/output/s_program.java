/*
 * Automatically generated file. Do not modify directly.
 * Created 2014-02-17 22:31:02.896000 by Gaurav Manek
 */

package edu.brown.cs.cutlass.pyret.ast;

import java.util.List;

/**
 * This class corresponds to the s_program variant of type Program
 * in the Pyret AST.
 *
 * data s_program:
 *   s_program (l :: Loc, imports :: List<Header>, block :: Expr)
 *   ...
 * end
 * 
 * @author Edward Teach
 */
public final class s_program implements Program {
	// Variables:
	public final Loc l;
	public final List<Header> imports;
	public final Expr block;

	/**
	 * Constructor, initializes all fields.
	 */
	public s_program(Loc l, List<Header> imports, Expr block) {
		this.l = l;
		this.imports = imports;
		this.block = block;
    }
	
}
