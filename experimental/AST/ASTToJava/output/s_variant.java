/*
 * Automatically generated file. Do not modify directly.
 * Created 2014-02-17 22:31:02.896000 by Gaurav Manek
 */

package edu.brown.cs.cutlass.pyret.ast;

import java.util.List;

/**
 * This class corresponds to the s_variant variant of type Variant
 * in the Pyret AST.
 *
 * data s_variant:
 *   s_variant (l :: Loc, name :: String, members :: List<VariantMember>, with_members :: List<Member>)
 *   ...
 * end
 * 
 * @author Edward Teach
 */
public final class s_variant implements Variant {
	// Variables:
	public final Loc l;
	public final String name;
	public final List<VariantMember> members;
	public final List<Member> with_members;

	/**
	 * Constructor, initializes all fields.
	 */
	public s_variant(Loc l, String name, List<VariantMember> members, List<Member> with_members) {
		this.l = l;
		this.name = name;
		this.members = members;
		this.with_members = with_members;
    }
	
}
