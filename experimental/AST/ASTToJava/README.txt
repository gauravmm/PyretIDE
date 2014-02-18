README - Create Pyret AST Elements

How to use:
  1) Copy latest version of Pyret AST in Pyret from the pyret-lang project ( pyret-lang/src/lang/racket-ffi/ast.arr)
  2) Manually substitute all imports from moorings.arr. Here are SOME of the imports necessary:
	# Loc = error.Location, from moorings.rkt
  3) Run "raco pyret astexport.arr > temp.txt" to dump the data in python-compatible format.
  4) Copy and paste the data structure from temp.txt to ast_python.py
  5) Run ast_to_java.py
  6) Manually repair the following classes/interfaces:
	# Any <create this>
		| Fixing this will automatically fix: location.java
	# Bool <create this>
		| Fixing this will automatically fix: s_bool.java
	# s_extend <rename super to _super>
	# s_graph <change "<is-s_let>" to "<s_let>">
	# s_update <rename super to _super>
	
Note: This is a hack. The fact that it is working is mildly miraculous. It can/will break for no reason at any time, so treasure it while it lasts.

TODO:
  1) Only include java.util.List when necessary.
  2) Check if List<> types are checked by Pyret.