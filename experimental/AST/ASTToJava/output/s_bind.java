/*
 * Automatically generated file. Do not modify directly.
 * Created 2014-02-17 22:31:02.896000 by Gaurav Manek
 */

package edu.brown.cs.cutlass.pyret.ast;

import java.util.List;

/**
 * This class corresponds to the s_bind variant of type Bind
 * in the Pyret AST.
 *
 * data s_bind:
 *   s_bind (l :: Loc, id :: String, ann :: Ann)
 *   ...
 * end
 * 
 * @author Edward Teach
 */
public final class s_bind implements Bind {
	// Variables:
	public final Loc l;
	public final String id;
	public final Ann ann;

	/**
	 * Constructor, initializes all fields.
	 */
	public s_bind(Loc l, String id, Ann ann) {
		this.l = l;
		this.id = id;
		this.ann = ann;
    }
	
}
