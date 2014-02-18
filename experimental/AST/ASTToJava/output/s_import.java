/*
 * Automatically generated file. Do not modify directly.
 * Created 2014-02-17 22:31:02.896000 by Gaurav Manek
 */

package edu.brown.cs.cutlass.pyret.ast;

import java.util.List;

/**
 * This class corresponds to the s_import variant of type Header
 * in the Pyret AST.
 *
 * data s_import:
 *   s_import (l :: Loc, file :: ImportType, name :: String)
 *   ...
 * end
 * 
 * @author Edward Teach
 */
public final class s_import implements Header {
	// Variables:
	public final Loc l;
	public final ImportType file;
	public final String name;

	/**
	 * Constructor, initializes all fields.
	 */
	public s_import(Loc l, ImportType file, String name) {
		this.l = l;
		this.file = file;
		this.name = name;
    }
	
}
