/*
 * Automatically generated file. Do not modify directly.
 * Created 2014-02-17 22:31:02.896000 by Gaurav Manek
 */

package edu.brown.cs.cutlass.pyret.ast;

/**
 * This interface corresponds to the Expr data type in the Pyret AST.
 *
 * data Expr:
 *   | s_hint_exp (l :: Loc, hints :: List<Hint>, e :: Expr)
 *   | s_block (l :: Loc, stmts :: List<Expr>)
 *   | s_user_block (l :: Loc, body :: Expr)
 *   | s_fun (l :: Loc, name :: String, params :: List<String>, args :: List<Bind>, ann :: Ann, doc :: String, body :: Expr, check :: Expr)
 *   | s_var (l :: Loc, name :: Bind, value :: Expr)
 *   | s_let (l :: Loc, name :: Bind, value :: Expr)
 *   | s_graph (l :: Loc, bindings :: List<is-s_let>)
 *   | s_when (l :: Loc, test :: Expr, block :: Expr)
 *   | s_assign (l :: Loc, id :: String, value :: Expr)
 *   | s_if (l :: Loc, branches :: List<IfBranch>)
 *   | s_if_else (l :: Loc, branches :: List<IfBranch>, _else :: Expr)
 *   | s_cases (l :: Loc, type :: Ann, val :: Expr, branches :: List<CasesBranch>)
 *   | s_cases_else (l :: Loc, type :: Ann, val :: Expr, branches :: List<CasesBranch>, _else :: Expr)
 *   | s_try (l :: Loc, body :: Expr, id :: Bind, _except :: Expr)
 *   | s_op (l :: Loc, op :: String, left :: Expr, right :: Expr)
 *   | s_check_test (l :: Loc, op :: String, left :: Expr, right :: Expr)
 *   | s_not (l :: Loc, expr :: Expr)
 *   | s_paren (l :: Loc, expr :: Expr)
 *   | s_lam (l :: Loc, params :: List<String>, args :: List<Bind>, ann :: Ann, doc :: String, body :: Expr, check :: Expr)
 *   | s_method (l :: Loc, args :: List<Bind>, ann :: Ann, doc :: String, body :: Expr, check :: Expr)
 *   | s_extend (l :: Loc, super :: Expr, fields :: List<Member>)
 *   | s_update (l :: Loc, super :: Expr, fields :: List<Member>)
 *   | s_obj (l :: Loc, fields :: List<Member>)
 *   | s_list (l :: Loc, values :: List<Expr>)
 *   | s_app (l :: Loc, _fun :: Expr, args :: List<Expr>)
 *   | s_left_app (l :: Loc, obj :: Expr, _fun :: Expr, args :: List<Expr>)
 *   | s_id (l :: Loc, id :: String)
 *   | s_num (l :: Loc, n :: Number)
 *   | s_bool (l :: Loc, b :: Bool)
 *   | s_str (l :: Loc, s :: String)
 *   | s_dot (l :: Loc, obj :: Expr, field :: String)
 *   | s_get_bang (l :: Loc, obj :: Expr, field :: String)
 *   | s_bracket (l :: Loc, obj :: Expr, field :: Expr)
 *   | s_colon (l :: Loc, obj :: Expr, field :: String)
 *   | s_colon_bracket (l :: Loc, obj :: Expr, field :: Expr)
 *   | s_data (l :: Loc, name :: String, params :: List<String>, mixins :: List<Expr>, variants :: List<Variant>, shared_members :: List<Member>, check :: Expr)
 *   | s_datatype (l :: Loc, name :: String, params :: List<String>, variants :: List<Variant>, check :: Expr)
 *   | s_for (l :: Loc, iterator :: Expr, bindings :: List<ForBind>, ann :: Ann, body :: Expr)
 *   | s_check (l :: Loc, body :: Expr)
 * end
 * 
 * @author Edward Teach
 */
public interface Expr extends PyretASTType {
	// Nothing. This is used for type safety.
}
