/*
 * Automatically generated file. Do not modify directly.
 * Created 2014-02-17 22:31:02.896000 by Gaurav Manek
 */

package edu.brown.cs.cutlass.pyret.ast;

import java.util.List;

/**
 * This class corresponds to the s_variant_member variant of type VariantMember
 * in the Pyret AST.
 *
 * data s_variant_member:
 *   s_variant_member (l :: Loc, member_type :: String, bind :: Bind)
 *   ...
 * end
 * 
 * @author Edward Teach
 */
public final class s_variant_member implements VariantMember {
	// Variables:
	public final Loc l;
	public final String member_type;
	public final Bind bind;

	/**
	 * Constructor, initializes all fields.
	 */
	public s_variant_member(Loc l, String member_type, Bind bind) {
		this.l = l;
		this.member_type = member_type;
		this.bind = bind;
    }
	
}
