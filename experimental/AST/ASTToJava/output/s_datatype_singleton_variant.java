/*
 * Automatically generated file. Do not modify directly.
 * Created 2014-02-17 22:31:02.896000 by Gaurav Manek
 */

package edu.brown.cs.cutlass.pyret.ast;

import java.util.List;

/**
 * This class corresponds to the s_datatype_singleton_variant variant of type Variant
 * in the Pyret AST.
 *
 * data s_datatype_singleton_variant:
 *   s_datatype_singleton_variant (l :: Loc, name :: String, constructor :: Constructor)
 *   ...
 * end
 * 
 * @author Edward Teach
 */
public final class s_datatype_singleton_variant implements Variant {
	// Variables:
	public final Loc l;
	public final String name;
	public final Constructor constructor;

	/**
	 * Constructor, initializes all fields.
	 */
	public s_datatype_singleton_variant(Loc l, String name, Constructor constructor) {
		this.l = l;
		this.name = name;
		this.constructor = constructor;
    }
	
}
