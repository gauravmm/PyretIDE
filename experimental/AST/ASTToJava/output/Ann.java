/*
 * Automatically generated file. Do not modify directly.
 * Created 2014-02-17 22:31:02.896000 by Gaurav Manek
 */

package edu.brown.cs.cutlass.pyret.ast;

/**
 * This interface corresponds to the Ann data type in the Pyret AST.
 *
 * data Ann:
 *   | a_name (l :: Loc, id :: String)
 *   | a_arrow (l :: Loc, args :: List<Ann>, ret :: Ann)
 *   | a_method (l :: Loc, args :: List<Ann>, ret :: Ann)
 *   | a_record (l :: Loc, fields :: List<AField>)
 *   | a_app (l :: Loc, ann :: Ann, args :: List<Ann>)
 *   | a_pred (l :: Loc, ann :: Ann, exp :: Expr)
 *   | a_dot (l :: Loc, obj :: String, field :: String)
 * end
 * 
 * @author Edward Teach
 */
public interface Ann extends PyretASTType {
	// Nothing. This is used for type safety.
}
