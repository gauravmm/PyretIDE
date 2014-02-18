/*
 * Automatically generated file. Do not modify directly.
 * Created 2014-02-17 22:31:02.896000 by Gaurav Manek
 */

package edu.brown.cs.cutlass.pyret.ast;

/**
 * This interface corresponds to the Member data type in the Pyret AST.
 *
 * data Member:
 *   | s_data_field (l :: Loc, name :: Expr, value :: Expr)
 *   | s_mutable_field (l :: Loc, name :: Expr, ann :: Ann, value :: Expr)
 *   | s_once_field (l :: Loc, name :: Expr, ann :: Ann, value :: Expr)
 *   | s_method_field (l :: Loc, name :: Expr, args :: List<Bind>, ann :: Ann, doc :: String, body :: Expr, check :: Expr)
 * end
 * 
 * @author Edward Teach
 */
public interface Member extends PyretASTType {
	// Nothing. This is used for type safety.
}
