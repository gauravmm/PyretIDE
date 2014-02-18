/*
 * Automatically generated file. Do not modify directly.
 * Created 2014-02-17 22:31:02.896000 by Gaurav Manek
 */

package edu.brown.cs.cutlass.pyret.ast;

import java.util.List;

/**
 * This class corresponds to the a_record variant of type Ann
 * in the Pyret AST.
 *
 * data a_record:
 *   a_record (l :: Loc, fields :: List<AField>)
 *   ...
 * end
 * 
 * @author Edward Teach
 */
public final class a_record implements Ann {
	// Variables:
	public final Loc l;
	public final List<AField> fields;

	/**
	 * Constructor, initializes all fields.
	 */
	public a_record(Loc l, List<AField> fields) {
		this.l = l;
		this.fields = fields;
    }
	
}
