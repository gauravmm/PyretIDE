/*
 * Automatically generated file. Do not modify directly.
 * Created 2014-02-17 22:31:02.896000 by Gaurav Manek
 */

package edu.brown.cs.cutlass.pyret.ast;

/**
 * This interface corresponds to the Header data type in the Pyret AST.
 *
 * data Header:
 *   | s_import (l :: Loc, file :: ImportType, name :: String)
 *   | s_provide (l :: Loc, block :: Expr)
 *   | s_provide_all (l :: Loc)
 * end
 * 
 * @author Edward Teach
 */
public interface Header extends PyretASTType {
	// Nothing. This is used for type safety.
}
