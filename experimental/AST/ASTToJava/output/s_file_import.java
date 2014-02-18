/*
 * Automatically generated file. Do not modify directly.
 * Created 2014-02-17 22:31:02.896000 by Gaurav Manek
 */

package edu.brown.cs.cutlass.pyret.ast;

import java.util.List;

/**
 * This class corresponds to the s_file_import variant of type ImportType
 * in the Pyret AST.
 *
 * data s_file_import:
 *   s_file_import (file :: String)
 *   ...
 * end
 * 
 * @author Edward Teach
 */
public final class s_file_import implements ImportType {
	// Variables:
	public final String file;

	/**
	 * Constructor, initializes all fields.
	 */
	public s_file_import(String file) {
		this.file = file;
    }
	
}
