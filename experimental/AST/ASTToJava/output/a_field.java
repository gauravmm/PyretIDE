/*
 * Automatically generated file. Do not modify directly.
 * Created 2014-02-17 22:31:02.896000 by Gaurav Manek
 */

package edu.brown.cs.cutlass.pyret.ast;

import java.util.List;

/**
 * This class corresponds to the a_field variant of type AField
 * in the Pyret AST.
 *
 * data a_field:
 *   a_field (l :: Loc, name :: String, ann :: Ann)
 *   ...
 * end
 * 
 * @author Edward Teach
 */
public final class a_field implements AField {
	// Variables:
	public final Loc l;
	public final String name;
	public final Ann ann;

	/**
	 * Constructor, initializes all fields.
	 */
	public a_field(Loc l, String name, Ann ann) {
		this.l = l;
		this.name = name;
		this.ann = ann;
    }
	
}
