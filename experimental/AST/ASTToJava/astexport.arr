#lang pyret

import ast as A
import file as F

filename = "ast.arr"
f = F.input-file(filename)
ast-str = f.read-file()

a = A.parse(ast-str, filename, {check : true})

#_ = print(a.pre-desugar.tosource().pretty(80).join-str("\n"))

data JavaDef:
  | interface()
  | abstract-class()
end

fun defs-to-java(p :: A.Program):
  cases(A.Program) p:
    | s_program(l, imports, block) =>
      data-defs = block.stmts.filter(A.is-s_data)
      for each(d from data-defs):
        print(d.name)
        for each(v from d.variants):
          print("\t" + v.name)
		  if (A.is-s_datatype_variant(v) or A.is-s_variant(v)):
			# Raw source code:
			# map(fun(x): print("\t |-\t" + x.bind.tosource().pretty(80).join-str("\n"));, v.members)
			# Each element:
			map(fun(x): print("\t |-\t" + x.bind.id + " :: " + x.bind.ann.tosource().pretty(100).get(0));, v.members)
		  else:
		  end
		  
		  ### Print methods associated with each object:
		  #if (A.is-s_singleton_variant(v) or A.is-s_variant(v)):
		  #	map(fun(x): print("\t |--\t" + x.name.tosource().pretty(80).join-str("\n"));, v.with_members)
		  #else:
		  #end
        end
      end
  end
end

fun defs-to-python(p :: A.Program):
  doc: "Creates a representation as a python list of data types, where each data type is (name, list of variants) and each variant is (name, list of types) and each types is (name, annotation)"
  cases(A.Program) p:
    | s_program(l, imports, block) =>
      data-defs = block.stmts.filter(A.is-s_data)
      print("[")
	  for each(d from data-defs):
        var variant_str = "(\"" + d.name + "\", ["
		#print(d.name)
        for each(v from d.variants):
		  #print("\t" + v.name)
		  if (A.is-s_datatype_variant(v) or A.is-s_variant(v)):
			variant_str := variant_str + "(\"" + v.name + "\", [" + fold(fun(pv, x): pv + "(\"" + x.bind.id + "\", \"" + x.bind.ann.tosource().pretty(100).get(0) + "\"), ";, "", v.members) + "]), "
		  else:
		  end
		end
		print(variant_str + "]),\n")
      end
	  print("]")
	  ""
  end
end

#defs-to-java(a.pre-desugar)
defs-to-python(a.pre-desugar)