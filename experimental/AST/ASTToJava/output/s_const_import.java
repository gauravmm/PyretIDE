/*
 * Automatically generated file. Do not modify directly.
 * Created 2014-02-17 22:31:02.896000 by Gaurav Manek
 */

package edu.brown.cs.cutlass.pyret.ast;

import java.util.List;

/**
 * This class corresponds to the s_const_import variant of type ImportType
 * in the Pyret AST.
 *
 * data s_const_import:
 *   s_const_import (module :: String)
 *   ...
 * end
 * 
 * @author Edward Teach
 */
public final class s_const_import implements ImportType {
	// Variables:
	public final String module;

	/**
	 * Constructor, initializes all fields.
	 */
	public s_const_import(String module) {
		this.module = module;
    }
	
}
