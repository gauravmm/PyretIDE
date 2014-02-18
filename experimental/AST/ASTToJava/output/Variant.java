/*
 * Automatically generated file. Do not modify directly.
 * Created 2014-02-17 22:31:02.896000 by Gaurav Manek
 */

package edu.brown.cs.cutlass.pyret.ast;

/**
 * This interface corresponds to the Variant data type in the Pyret AST.
 *
 * data Variant:
 *   | s_variant (l :: Loc, name :: String, members :: List<VariantMember>, with_members :: List<Member>)
 *   | s_singleton_variant (l :: Loc, name :: String, with_members :: List<Member>)
 *   | s_datatype_variant (l :: Loc, name :: String, members :: List<VariantMember>, constructor :: Constructor)
 *   | s_datatype_singleton_variant (l :: Loc, name :: String, constructor :: Constructor)
 * end
 * 
 * @author Edward Teach
 */
public interface Variant extends PyretASTType {
	// Nothing. This is used for type safety.
}
