#lang pyret

provide *
import pprint as PP

Loc = error.Location
loc = error.location

INDENT = 2

break-one = PP.break(1)
str-any = PP.str("Any")
str-arrow = PP.str("->")
str-arrowspace = PP.str("-> ")
str-as = PP.str("as")
str-blank = PP.str("")
str-block = PP.str("block:")
str-brackets = PP.str("[]")
str-cases = PP.str("cases")
str-check = PP.str("check:")
str-colon = PP.str(":")
str-coloncolon = PP.str("::")
str-colonspace = PP.str(": ")
str-comment = PP.str("# ")
str-constructor = PP.str("with constructor")
str-data = PP.str("data ")
str-datatype = PP.str("datatype ")
str-deriving = PP.str("deriving ")
str-doc = PP.str("doc: ")
str-elsebranch = PP.str("| else =>")
str-elsecolon = PP.str("else:")
str-elsespace = PP.str("else ")
str-end = PP.str("end")
str-except = PP.str("except")
str-for = PP.str("for ")
str-from = PP.str("from")
str-fun = PP.str("fun")
str-if = PP.str("if ")
str-import = PP.str("import")
str-method = PP.str("method")
str-mutable = PP.str("mutable")
str-not = PP.str("not")
str-period = PP.str(".")
str-pipespace = PP.str("| ")
str-provide = PP.str("provide")
str-provide-star = PP.str("provide *")
str-sharing = PP.str("sharing:")
str-space = PP.str(" ")
str-spacecolonequal = PP.str(" :=")
str-spaceequal = PP.str(" =")
str-thickarrow = PP.str("=>")
str-try = PP.str("try:")
str-use-loc = PP.str("UseLoc")
str-var = PP.str("var ")
str-when = PP.str("when")
str-where = PP.str("where:")
str-with = PP.str("with:")

# Serializer assist:
fun <A> pyret_list_serializer(alod :: List<A>, conv :: (A -> String)) -> String:
	for fold(rv from "", elt from alod):
		rv.append(", ").append(conv(elt))
	end
end

fun funlam_tosource(funtype, name, params, args :: List<is-s_bind>,
    ann :: Ann, doc :: String, body :: Expr, _check :: Expr) -> PP.PPrintDoc:
  typarams =
    if is-nothing(params): PP.mt-doc
    else: PP.surround-separate(INDENT, 0, PP.mt-doc, PP.langle, PP.commabreak, PP.rangle,
        params.map(PP.str))
    end
  arg-list = PP.nest(INDENT,
    PP.surround-separate(INDENT, 0, PP.lparen + PP.rparen, PP.lparen, PP.commabreak, PP.rparen,
      args.map(fun(a): a.tosource() end)))
  ftype = funtype + typarams
  fname =
    if is-nothing(name): ftype
    else if PP.is-mt-doc(ftype): PP.str(name)
    else: ftype + PP.str(" " + name)
    end
  fann =
    if is-a_blank(ann) or is-nothing(ann): PP.mt-doc
    else: break-one + str-arrowspace + ann.tosource()
    end
  header = PP.group(fname + arg-list + fann + str-colon)
  checker = _check.tosource()
  footer =
    if PP.is-mt-doc(checker): str-end
    else: PP.surround(INDENT, 1, str-where, _check.tosource(), str-end)
    end
  docstr =
    if is-nothing(doc) or (doc == ""): PP.mt-doc
    else: str-doc + PP.dquote(PP.str(doc)) + PP.hardline
    end
  PP.surround(INDENT, 1, header, docstr + body.tosource(), footer)
end

data Program:
  | s_program(l :: Loc, imports :: List<Header>, block :: Expr) with:
	serialize(self): # For Program.s_program:
		"{ \"_type\" : \"s_program\""
		.append(", \"l\" : ").append(self.l.serialize())
		.append(", \"imports\" : ").append(pyret_list_serializer(self.imports, fun(x): x.serialize();))
		.append(", \"block\" : ").append(self.block.serialize()).append("}")
	end,
    tosource(self):
      PP.group(
        PP.flow_map(PP.hardline, fun(i): i.tosource() end, self.imports)
          + PP.hardline
          + self.block.tosource()
        )
    end
end

data Header:
  | s_import(l :: Loc, file :: ImportType, name :: String) with:
	serialize(self): # For Header.s_import:
		"{ \"_type\" : \"s_import\""
		.append(", \"l\" : ").append(self.l.serialize())
		.append(", \"file\" : ").append(self.file.serialize())
		.append(", \"name\" : ").append(self.name.tostring()).append("}")
	end,
    tosource(self):
      PP.flow([str-import, PP.dquote(self.file.tosource()),
          str-as, PP.str(self.name)])
    end
  | s_provide(l :: Loc, block :: Expr) with:
	serialize(self): # For Header.s_provide:
		"{ \"_type\" : \"s_provide\""
		.append(", \"l\" : ").append(self.l.serialize())
		.append(", \"block\" : ").append(self.block.serialize()).append("}")
	end,
    tosource(self):
      PP.soft-surround(INDENT, 1, str-provide,
        self.block.tosource(), str-end)
    end
  | s_provide_all(l :: Loc) with:
	serialize(self): # For Header.s_provide_all:
		"{ \"_type\" : \"s_provide_all\""
		.append(", \"l\" : ").append(self.l.serialize()).append("}")
	end,
    tosource(self): str-provide-star end
end

data ImportType:
  | s_file_import(file :: String) with:
	serialize(self): # For ImportType.s_file_import:
		"{ \"_type\" : \"s_file_import\""
		.append(", \"file\" : ").append(self.file.tostring()).append("}")
	end,
    tosource(self): str-import + break-one + PP.dquote(PP.str(self.file)) end
  | s_const_import(module :: String) with:
	serialize(self): # For ImportType.s_const_import:
		"{ \"_type\" : \"s_const_import\""
		.append(", \"module\" : ").append(self.module.tostring()).append("}")
	end,
    tosource(self): str-import + break-one + PP.str(self.module) end
end

data Hint:
  | h_use_loc(l :: Loc) with:
	serialize(self): # For Hint.h_use_loc:
		"{ \"_type\" : \"h_use_loc\""
		.append(", \"l\" : ").append(self.l.serialize()).append("}")
	end,
    tosource(self): str-use-loc + PP.parens(PP.str(self.l.tostring())) end
end

data Expr:
  | s_hint_exp(l :: Loc, hints :: List<Hint>, e :: Expr) with:
	serialize(self): # For Expr.s_hint_exp:
		"{ \"_type\" : \"s_hint_exp\""
		.append(", \"l\" : ").append(self.l.serialize())
		.append(", \"hints\" : ").append(pyret_list_serializer(self.hints, fun(x): x.serialize();))
		.append(", \"e\" : ").append(self.e.serialize()).append("}")
	end,
    tosource(self):
      PP.flow_map(PP.hardline, fun(h): str-comment + h.tosource() end, self.hints) + PP.hardline
        + self.e.tosource()
    end
  | s_block(l :: Loc, stmts :: List<Expr>) with:
	serialize(self): # For Expr.s_block:
		"{ \"_type\" : \"s_block\""
		.append(", \"l\" : ").append(self.l.serialize())
		.append(", \"stmts\" : ").append(pyret_list_serializer(self.stmts, fun(x): x.serialize();)).append("}")
	end,
    tosource(self):
      PP.flow_map(PP.hardline, _.tosource(), self.stmts) end
  | s_user_block(l :: Loc, body :: Expr) with:
	serialize(self): # For Expr.s_user_block:
		"{ \"_type\" : \"s_user_block\""
		.append(", \"l\" : ").append(self.l.serialize())
		.append(", \"body\" : ").append(self.body.serialize()).append("}")
	end,
    tosource(self):
      PP.surround(INDENT, 1, str-block, self.body.tosource(), str-end)
    end
  | s_fun(
      l :: Loc,
      name :: String,
      params :: List<String>, # Type parameters
      args :: List<Bind>, # Value parameters
      ann :: Ann, # return type
      doc :: String,
      body :: Expr,
      check :: Expr
    ) with:
	serialize(self): # For Expr.s_fun:
		"{ \"_type\" : \"s_fun\""
		.append(", \"l\" : ").append(self.l.serialize())
		.append(", \"name\" : ").append(self.name.tostring())
		.append(", \"params\" : ").append(pyret_list_serializer(self.params, fun(x): x.tostring();))
		.append(", \"args\" : ").append(pyret_list_serializer(self.args, fun(x): x.serialize();))
		.append(", \"ann\" : ").append(self.ann.serialize())
		.append(", \"doc\" : ").append(self.doc.tostring())
		.append(", \"body\" : ").append(self.body.serialize())
		.append(", \"check\" : ").append(self.check.serialize()).append("}")
	end,
    tosource(self):
      funlam_tosource(str-fun,
        self.name, self.params, self.args, self.ann, self.doc, self.body, self.check)
    end
  | s_var(l :: Loc, name :: Bind, value :: Expr) with:
	serialize(self): # For Expr.s_var:
		"{ \"_type\" : \"s_var\""
		.append(", \"l\" : ").append(self.l.serialize())
		.append(", \"name\" : ").append(self.name.serialize())
		.append(", \"value\" : ").append(self.value.serialize()).append("}")
	end,
    tosource(self):
      str-var
        + PP.group(PP.nest(INDENT, self.name.tosource()
            + str-spaceequal + break-one + self.value.tosource()))
    end
  | s_let(l :: Loc, name :: Bind, value :: Expr) with:
	serialize(self): # For Expr.s_let:
		"{ \"_type\" : \"s_let\""
		.append(", \"l\" : ").append(self.l.serialize())
		.append(", \"name\" : ").append(self.name.serialize())
		.append(", \"value\" : ").append(self.value.serialize()).append("}")
	end,
    tosource(self):
      PP.group(PP.nest(INDENT, self.name.tosource() + str-spaceequal + break-one + self.value.tosource()))
    end
  | s_graph(l :: Loc, bindings :: List<is-s_let>)
  | s_when(l :: Loc, test :: Expr, block :: Expr) with:
	serialize(self): # For Expr.s_when:
		"{ \"_type\" : \"s_when\""
		.append(", \"l\" : ").append(self.l.serialize())
		.append(", \"test\" : ").append(self.test.serialize())
		.append(", \"block\" : ").append(self.block.serialize()).append("}")
	end,
	serialize(self): # For Expr.s_graph:
		"{ \"_type\" : \"s_graph\""
		.append(", \"l\" : ").append(self.l.serialize())
		.append(", \"bindings\" : ").append(pyret_list_serializer(self.bindings, fun(x): x.serialize();)).append("}")
	end,
    tosource(self):
      PP.soft-surround(INDENT, 1,
        str-when + PP.parens(self.test.tosource()) + str-colon,
        self.block.tosource(),
        str-end)
    end
  | s_assign(l :: Loc, id :: String, value :: Expr) with:
	serialize(self): # For Expr.s_assign:
		"{ \"_type\" : \"s_assign\""
		.append(", \"l\" : ").append(self.l.serialize())
		.append(", \"id\" : ").append(self.id.tostring())
		.append(", \"value\" : ").append(self.value.serialize()).append("}")
	end,
    tosource(self):
      PP.nest(INDENT, PP.str(self.id) + str-spacecolonequal + break-one + self.value.tosource())
    end
  | s_if(l :: Loc, branches :: List<IfBranch>) with:
	serialize(self): # For Expr.s_if:
		"{ \"_type\" : \"s_if\""
		.append(", \"l\" : ").append(self.l.serialize())
		.append(", \"branches\" : ").append(pyret_list_serializer(self.branches, fun(x): x.serialize();)).append("}")
	end,
    tosource(self):
      branches = PP.separate(break-one + str-elsespace,
        self.branches.map(fun(b): b.tosource() end))
      PP.group(branches + break-one + str-end)
    end
  | s_if_else(l :: Loc, branches :: List<IfBranch>, _else :: Expr) with:
	serialize(self): # For Expr.s_if_else:
		"{ \"_type\" : \"s_if_else\""
		.append(", \"l\" : ").append(self.l.serialize())
		.append(", \"branches\" : ").append(pyret_list_serializer(self.branches, fun(x): x.serialize();))
		.append(", \"_else\" : ").append(self._else.serialize()).append("}")
	end,
    tosource(self):
      branches = PP.separate(break-one + str-elsespace,
        self.branches.map(fun(b): b.tosource() end))
      _else = str-elsecolon + PP.nest(INDENT, break-one + self._else.tosource())
      PP.group(branches + break-one + _else + break-one + str-end)
    end
  | s_cases(l :: Loc, type :: Ann, val :: Expr, branches :: List<CasesBranch>) with:
	serialize(self): # For Expr.s_cases:
		"{ \"_type\" : \"s_cases\""
		.append(", \"l\" : ").append(self.l.serialize())
		.append(", \"type\" : ").append(self.type.serialize())
		.append(", \"val\" : ").append(self.val.serialize())
		.append(", \"branches\" : ").append(pyret_list_serializer(self.branches, fun(x): x.serialize();)).append("}")
	end,
    tosource(self):
      header = str-cases + PP.parens(self.type.tosource()) + break-one
        + self.val.tosource() + str-colon
      PP.surround-separate(INDENT, 1, header + str-space + str-end,
        PP.group(header), break-one, str-end,
        self.branches.map(fun(b): PP.group(b.tosource()) end))
    end
  | s_cases_else(l :: Loc, type :: Ann, val :: Expr, branches :: List<CasesBranch>, _else :: Expr) with:
	serialize(self): # For Expr.s_cases_else:
		"{ \"_type\" : \"s_cases_else\""
		.append(", \"l\" : ").append(self.l.serialize())
		.append(", \"type\" : ").append(self.type.serialize())
		.append(", \"val\" : ").append(self.val.serialize())
		.append(", \"branches\" : ").append(pyret_list_serializer(self.branches, fun(x): x.serialize();))
		.append(", \"_else\" : ").append(self._else.serialize()).append("}")
	end,
    tosource(self):
      header = str-cases + PP.parens(self.type.tosource()) + break-one
        + self.val.tosource() + str-colon
      body = PP.separate(break-one, self.branches.map(fun(b): PP.group(b.tosource()) end))
        + break-one + PP.group(str-elsebranch + break-one + self._else.tosource())
      PP.surround(INDENT, 1, PP.group(header), body, str-end)
    end
  | s_try(l :: Loc, body :: Expr, id :: Bind, _except :: Expr) with:
	serialize(self): # For Expr.s_try:
		"{ \"_type\" : \"s_try\""
		.append(", \"l\" : ").append(self.l.serialize())
		.append(", \"body\" : ").append(self.body.serialize())
		.append(", \"id\" : ").append(self.id.serialize())
		.append(", \"_except\" : ").append(self._except.serialize()).append("}")
	end,
    tosource(self):
      _try = str-try + break-one
        + PP.nest(INDENT, self.body.tosource()) + break-one
      _except = str-except + PP.parens(self.id.tosource()) + str-colon + break-one
        + PP.nest(INDENT, self._except.tosource()) + break-one
      PP.group(_try + _except + str-end)
    end
  | s_op(l :: Loc, op :: String, left :: Expr, right :: Expr) with:
	serialize(self): # For Expr.s_op:
		"{ \"_type\" : \"s_op\""
		.append(", \"l\" : ").append(self.l.serialize())
		.append(", \"op\" : ").append(self.op.tostring())
		.append(", \"left\" : ").append(self.left.serialize())
		.append(", \"right\" : ").append(self.right.serialize()).append("}")
	end,
    tosource(self): PP.infix(INDENT, 1, PP.str(self.op.substring(2, self.op.length())), self.left.tosource(), self.right.tosource()) end
  | s_check_test(l :: Loc, op :: String, left :: Expr, right :: Expr) with:
	serialize(self): # For Expr.s_check:
		"{ \"_type\" : \"s_check\""
		.append(", \"l\" : ").append(self.l.serialize())
		.append(", \"body\" : ").append(self.body.serialize()).append("}")
	end,
	serialize(self): # For Expr.s_check_test:
		"{ \"_type\" : \"s_check_test\""
		.append(", \"l\" : ").append(self.l.serialize())
		.append(", \"op\" : ").append(self.op.tostring())
		.append(", \"left\" : ").append(self.left.serialize())
		.append(", \"right\" : ").append(self.right.serialize()).append("}")
	end,
    tosource(self): PP.infix(INDENT, 1, PP.str(self.op.substring(2, self.op.length())), self.left.tosource(), self.right.tosource()) end
  | s_not(l :: Loc, expr :: Expr) with:
	serialize(self): # For Expr.s_not:
		"{ \"_type\" : \"s_not\""
		.append(", \"l\" : ").append(self.l.serialize())
		.append(", \"expr\" : ").append(self.expr.serialize()).append("}")
	end,
    tosource(self): PP.nest(INDENT, PP.flow([str-not, self.expr.tosource()])) end
  | s_paren(l :: Loc, expr :: Expr) with:
	serialize(self): # For Expr.s_paren:
		"{ \"_type\" : \"s_paren\""
		.append(", \"l\" : ").append(self.l.serialize())
		.append(", \"expr\" : ").append(self.expr.serialize()).append("}")
	end,
    tosource(self): PP.parens(self.expr.tosource()) end
  | s_lam(
      l :: Loc,
      params :: List<String>, # Type parameters
      args :: List<Bind>, # Value parameters
      ann :: Ann, # return type
      doc :: String,
      body :: Expr,
      check :: Expr
    ) with:
	serialize(self): # For Expr.s_lam:
		"{ \"_type\" : \"s_lam\""
		.append(", \"l\" : ").append(self.l.serialize())
		.append(", \"params\" : ").append(pyret_list_serializer(self.params, fun(x): x.tostring();))
		.append(", \"args\" : ").append(pyret_list_serializer(self.args, fun(x): x.serialize();))
		.append(", \"ann\" : ").append(self.ann.serialize())
		.append(", \"doc\" : ").append(self.doc.tostring())
		.append(", \"body\" : ").append(self.body.serialize())
		.append(", \"check\" : ").append(self.check.serialize()).append("}")
	end,
    tosource(self):
      funlam_tosource(str-fun,
        nothing, self.params, self.args, self.ann, self.doc, self.body, self.check)
    end
  | s_method(
      l :: Loc,
      args :: List<Bind>, # Value parameters
      ann :: Ann, # return type
      doc :: String,
      body :: Expr,
      check :: Expr
    ) with:
	serialize(self): # For Expr.s_method:
		"{ \"_type\" : \"s_method\""
		.append(", \"l\" : ").append(self.l.serialize())
		.append(", \"args\" : ").append(pyret_list_serializer(self.args, fun(x): x.serialize();))
		.append(", \"ann\" : ").append(self.ann.serialize())
		.append(", \"doc\" : ").append(self.doc.tostring())
		.append(", \"body\" : ").append(self.body.serialize())
		.append(", \"check\" : ").append(self.check.serialize()).append("}")
	end,
    tosource(self):
      funlam_tosource(str-method,
        nothing, nothing, self.args, self.ann, self.doc, self.body, self.check)
    end
  | s_extend(l :: Loc, super :: Expr, fields :: List<Member>) with:
	serialize(self): # For Expr.s_extend:
		"{ \"_type\" : \"s_extend\""
		.append(", \"l\" : ").append(self.l.serialize())
		.append(", \"super\" : ").append(self.super.serialize())
		.append(", \"fields\" : ").append(pyret_list_serializer(self.fields, fun(x): x.serialize();)).append("}")
	end,
    tosource(self):
      PP.group(self.super.tosource() + str-period
          + PP.surround-separate(INDENT, 1, PP.lbrace + PP.rbrace,
          PP.lbrace, PP.commabreak, PP.rbrace, self.fields.map(fun(f): f.tosource() end)))
    end
  | s_update(l :: Loc, super :: Expr, fields :: List<Member>)
  | s_obj(l :: Loc, fields :: List<Member>) with:
	serialize(self): # For Expr.s_obj:
		"{ \"_type\" : \"s_obj\""
		.append(", \"l\" : ").append(self.l.serialize())
		.append(", \"fields\" : ").append(pyret_list_serializer(self.fields, fun(x): x.serialize();)).append("}")
	end,
	serialize(self): # For Expr.s_update:
		"{ \"_type\" : \"s_update\""
		.append(", \"l\" : ").append(self.l.serialize())
		.append(", \"super\" : ").append(self.super.serialize())
		.append(", \"fields\" : ").append(pyret_list_serializer(self.fields, fun(x): x.serialize();)).append("}")
	end,
    tosource(self):
      PP.surround-separate(INDENT, 1, PP.lbrace + PP.rbrace,
        PP.lbrace, PP.commabreak, PP.rbrace, self.fields.map(fun(f): f.tosource() end))
    end
  | s_list(l :: Loc, values :: List<Expr>) with:
	serialize(self): # For Expr.s_list:
		"{ \"_type\" : \"s_list\""
		.append(", \"l\" : ").append(self.l.serialize())
		.append(", \"values\" : ").append(pyret_list_serializer(self.values, fun(x): x.serialize();)).append("}")
	end,
    tosource(self):
      PP.surround-separate(INDENT, 0, str-brackets, PP.lbrack, PP.commabreak, PP.rbrack,
        self.values.map(fun(v): v.tosource() end))
    end
  | s_app(l :: Loc, _fun :: Expr, args :: List<Expr>) with:
	serialize(self): # For Expr.s_app:
		"{ \"_type\" : \"s_app\""
		.append(", \"l\" : ").append(self.l.serialize())
		.append(", \"_fun\" : ").append(self._fun.serialize())
		.append(", \"args\" : ").append(pyret_list_serializer(self.args, fun(x): x.serialize();)).append("}")
	end,
    tosource(self):
      PP.group(self._fun.tosource()
          + PP.parens(PP.nest(INDENT,
            PP.separate(PP.commabreak, self.args.map(fun(f): f.tosource() end)))))
    end
  | s_left_app(l :: Loc, obj :: Expr, _fun :: Expr, args :: List<Expr>) with:
	serialize(self): # For Expr.s_left_app:
		"{ \"_type\" : \"s_left_app\""
		.append(", \"l\" : ").append(self.l.serialize())
		.append(", \"obj\" : ").append(self.obj.serialize())
		.append(", \"_fun\" : ").append(self._fun.serialize())
		.append(", \"args\" : ").append(pyret_list_serializer(self.args, fun(x): x.serialize();)).append("}")
	end,
    tosource(self):
      PP.group(self.obj.tosource() + PP.nest(INDENT, PP.break(0) + str-period + self._fun.tosource())
          + PP.parens(PP.separate(PP.commabreak, self.args.map(fun(f): f.tosource() end))))
    end
  | s_id(l :: Loc, id :: String) with:
	serialize(self): # For Expr.s_id:
		"{ \"_type\" : \"s_id\""
		.append(", \"l\" : ").append(self.l.serialize())
		.append(", \"id\" : ").append(self.id.tostring()).append("}")
	end,
    tosource(self): PP.str(self.id) end
  | s_num(l :: Loc, n :: Number) with:
	serialize(self): # For Expr.s_num:
		"{ \"_type\" : \"s_num\""
		.append(", \"l\" : ").append(self.l.serialize())
		.append(", \"n\" : ").append(self.n.tostring()).append("}")
	end,
    tosource(self): PP.number(self.n) end
  | s_bool(l :: Loc, b :: Bool) with:
	serialize(self): # For Expr.s_bool:
		"{ \"_type\" : \"s_bool\""
		.append(", \"l\" : ").append(self.l.serialize())
		.append(", \"b\" : ").append(self.b.serialize()).append("}")
	end,
    tosource(self): PP.str(self.b.tostring()) end
  | s_str(l :: Loc, s :: String) with:
	serialize(self): # For Expr.s_str:
		"{ \"_type\" : \"s_str\""
		.append(", \"l\" : ").append(self.l.serialize())
		.append(", \"s\" : ").append(self.s.tostring()).append("}")
	end,
    tosource(self):
      PP.str(torepr(self.s))
    end
  | s_dot(l :: Loc, obj :: Expr, field :: String) with:
	serialize(self): # For Expr.s_dot:
		"{ \"_type\" : \"s_dot\""
		.append(", \"l\" : ").append(self.l.serialize())
		.append(", \"obj\" : ").append(self.obj.serialize())
		.append(", \"field\" : ").append(self.field.tostring()).append("}")
	end,
    tosource(self): PP.infix(INDENT, 0, str-period, self.obj.tosource(), PP.str(self.field)) end
  | s_get_bang(l :: Loc, obj :: Expr, field :: String)
  | s_bracket(l :: Loc, obj :: Expr, field :: Expr) with:
	serialize(self): # For Expr.s_bracket:
		"{ \"_type\" : \"s_bracket\""
		.append(", \"l\" : ").append(self.l.serialize())
		.append(", \"obj\" : ").append(self.obj.serialize())
		.append(", \"field\" : ").append(self.field.serialize()).append("}")
	end,
	serialize(self): # For Expr.s_get_bang:
		"{ \"_type\" : \"s_get_bang\""
		.append(", \"l\" : ").append(self.l.serialize())
		.append(", \"obj\" : ").append(self.obj.serialize())
		.append(", \"field\" : ").append(self.field.tostring()).append("}")
	end,
    tosource(self): PP.infix(INDENT, 0, str-period, self.obj.tosource(),
        PP.surround(INDENT, 0, PP.lbrack, self.field.tosource(), PP.rbrack))
    end
  | s_colon(l :: Loc, obj :: Expr, field :: String) with:
	serialize(self): # For Expr.s_colon:
		"{ \"_type\" : \"s_colon\""
		.append(", \"l\" : ").append(self.l.serialize())
		.append(", \"obj\" : ").append(self.obj.serialize())
		.append(", \"field\" : ").append(self.field.tostring()).append("}")
	end,
    tosource(self): PP.infix(INDENT, 0, str-colon, self.obj.tosource(), PP.str(self.field)) end
  | s_colon_bracket(l :: Loc, obj :: Expr, field :: Expr) with:
	serialize(self): # For Expr.s_colon_bracket:
		"{ \"_type\" : \"s_colon_bracket\""
		.append(", \"l\" : ").append(self.l.serialize())
		.append(", \"obj\" : ").append(self.obj.serialize())
		.append(", \"field\" : ").append(self.field.serialize()).append("}")
	end,
    tosource(self): PP.infix(INDENT, 0, str-colon, self.obj.tosource(),
        PP.surround(INDENT, 0, PP.lbrack, self.field.tosource(), PP.rbrack))
    end
  | s_data(
      l :: Loc,
      name :: String,
      params :: List<String>, # type params
      mixins :: List<Expr>,
      variants :: List<Variant>,
      shared_members :: List<Member>,
      check :: Expr
    ) with:
	serialize(self): # For Expr.s_data:
		"{ \"_type\" : \"s_data\""
		.append(", \"l\" : ").append(self.l.serialize())
		.append(", \"name\" : ").append(self.name.tostring())
		.append(", \"params\" : ").append(pyret_list_serializer(self.params, fun(x): x.tostring();))
		.append(", \"mixins\" : ").append(pyret_list_serializer(self.mixins, fun(x): x.serialize();))
		.append(", \"variants\" : ").append(pyret_list_serializer(self.variants, fun(x): x.serialize();))
		.append(", \"shared_members\" : ").append(pyret_list_serializer(self.shared_members, fun(x): x.serialize();))
		.append(", \"check\" : ").append(self.check.serialize()).append("}")
	end,
    tosource(self):
      fun optional_section(lbl, section):
        if PP.is-mt-doc(section): PP.mt-doc
        else: break-one + PP.group(PP.nest(INDENT, lbl + break-one + section))
        end
      end
      tys = PP.surround-separate(2*INDENT, 0, PP.mt-doc, PP.langle, PP.commabreak, PP.rangle,
        self.params.map(fun(f): f.tosource() end))
      header = str-data + PP.str(self.name) + tys + str-colon
      _deriving =
        PP.surround-separate(INDENT, 0, PP.mt-doc, break-one + str-deriving, PP.commabreak, PP.mt-doc, self.mixins.map(fun(m): m.tosource() end))
      variants = PP.separate(break-one + str-pipespace,
        str-blank^list.link(self.variants.map(fun(v): PP.nest(INDENT, v.tosource()) end)))
      shared = optional_section(str-sharing,
        PP.separate(PP.commabreak, self.shared_members.map(fun(s): s.tosource() end)))
      _check = optional_section(str-where, self.check.tosource())
      footer = break-one + str-end
      header + _deriving + PP.group(PP.nest(INDENT, variants) + shared + _check + footer)
    end
  | s_datatype(
      l :: Loc,
      name :: String,
      params :: List<String>, # type params
      variants :: List<Variant>,
      check :: Expr
    ) with:
	serialize(self): # For Expr.s_datatype:
		"{ \"_type\" : \"s_datatype\""
		.append(", \"l\" : ").append(self.l.serialize())
		.append(", \"name\" : ").append(self.name.tostring())
		.append(", \"params\" : ").append(pyret_list_serializer(self.params, fun(x): x.tostring();))
		.append(", \"variants\" : ").append(pyret_list_serializer(self.variants, fun(x): x.serialize();))
		.append(", \"check\" : ").append(self.check.serialize()).append("}")
	end,
      label(self): "s_datatype" end,
    tosource(self):
      fun optional_section(lbl, section):
        if PP.is-empty(section): PP.empty
        else: break-one + PP.group(PP.nest(INDENT, lbl + break-one + section))
        end
      end
      tys = PP.surround-separate(2*INDENT, 0, PP.empty, PP.langle, PP.commabreak, PP.rangle,
        self.params.map(fun(f): f.tosource() end))
      header = str-data + PP.str(self.name) + tys + str-colon
      variants = PP.separate(break-one + str-pipespace,
        str-blank^list.link(self.variants.map(fun(v): PP.nest(INDENT, v.tosource()) end)))
      _check = optional_section(str-where, self.check.tosource())
      footer = break-one + str-end
      header + PP.group(PP.nest(INDENT, variants) + _check + footer)
    end
  | s_for(
      l :: Loc,
      iterator :: Expr,
      bindings :: List<ForBind>,
      ann :: Ann,
      body :: Expr
    ) with:
	serialize(self): # For Expr.s_for:
		"{ \"_type\" : \"s_for\""
		.append(", \"l\" : ").append(self.l.serialize())
		.append(", \"iterator\" : ").append(self.iterator.serialize())
		.append(", \"bindings\" : ").append(pyret_list_serializer(self.bindings, fun(x): x.serialize();))
		.append(", \"ann\" : ").append(self.ann.serialize())
		.append(", \"body\" : ").append(self.body.serialize()).append("}")
	end,
    tosource(self):
      header = PP.group(str-for
          + self.iterator.tosource()
          + PP.surround-separate(2*INDENT, 0, PP.lparen + PP.rparen, PP.lparen, PP.commabreak, PP.rparen,
          self.bindings.map(fun(b): b.tosource() end))
          + PP.group(PP.nest(2*INDENT,
            break-one + str-arrow + break-one + self.ann.tosource() + str-colon)))
      PP.surround(INDENT, 1, header, self.body.tosource(), str-end)
    end
  | s_check(
      l :: Loc,
      body :: Expr
    ) with:
    tosource(self):
      PP.surround(INDENT, 1, str-check, self.body.tosource(), str-end)
    end
end

data Bind:
  | s_bind(l :: Loc, id :: String, ann :: Ann) with:
	serialize(self): # For Bind.s_bind:
		"{ \"_type\" : \"s_bind\""
		.append(", \"l\" : ").append(self.l.serialize())
		.append(", \"id\" : ").append(self.id.tostring())
		.append(", \"ann\" : ").append(self.ann.serialize()).append("}")
	end,
    tosource(self):
      if is-a_blank(self.ann): PP.str(self.id)
      else: PP.infix(INDENT, 1, str-coloncolon, PP.str(self.id), self.ann.tosource())
      end
    end
end

data Member:
  | s_data_field(l :: Loc, name :: Expr, value :: Expr) with:
	serialize(self): # For Member.s_data_field:
		"{ \"_type\" : \"s_data_field\""
		.append(", \"l\" : ").append(self.l.serialize())
		.append(", \"name\" : ").append(self.name.serialize())
		.append(", \"value\" : ").append(self.value.serialize()).append("}")
	end,
    tosource(self): PP.nest(INDENT, self.name.tosource() + str-colonspace + self.value.tosource()) end,
  | s_mutable_field(l :: Loc, name :: Expr, ann :: Ann, value :: Expr) with:
	serialize(self): # For Member.s_mutable_field:
		"{ \"_type\" : \"s_mutable_field\""
		.append(", \"l\" : ").append(self.l.serialize())
		.append(", \"name\" : ").append(self.name.serialize())
		.append(", \"ann\" : ").append(self.ann.serialize())
		.append(", \"value\" : ").append(self.value.serialize()).append("}")
	end,
    tosource(self): PP.nest(INDENT, str-mutable + self.name.tosource() + str-coloncolon + self.ann.tosource() + str-colonspace + self.value.tosource()) end,
  | s_once_field(l :: Loc, name :: Expr, ann :: Ann, value :: Expr)
  | s_method_field(
      l :: Loc,
      name :: Expr,
      args :: List<Bind>, # Value parameters
      ann :: Ann, # return type
      doc :: String,
      body :: Expr,
      check :: Expr
    ) with:
	serialize(self): # For Member.s_method_field:
		"{ \"_type\" : \"s_method_field\""
		.append(", \"l\" : ").append(self.l.serialize())
		.append(", \"name\" : ").append(self.name.serialize())
		.append(", \"args\" : ").append(pyret_list_serializer(self.args, fun(x): x.serialize();))
		.append(", \"ann\" : ").append(self.ann.serialize())
		.append(", \"doc\" : ").append(self.doc.tostring())
		.append(", \"body\" : ").append(self.body.serialize())
		.append(", \"check\" : ").append(self.check.serialize()).append("}")
	end,
	serialize(self): # For Member.s_once_field:
		"{ \"_type\" : \"s_once_field\""
		.append(", \"l\" : ").append(self.l.serialize())
		.append(", \"name\" : ").append(self.name.serialize())
		.append(", \"ann\" : ").append(self.ann.serialize())
		.append(", \"value\" : ").append(self.value.serialize()).append("}")
	end,
    tosource(self):
      name-part = cases(Expr) self.name:
        | s_str(l, s) => PP.str(s)
        | else => self.name.tosource()
      end
      funlam_tosource(name-part,
        nothing, nothing, self.args, self.ann, self.doc, self.body, self.check)
    end
end

data ForBind:
  | s_for_bind(l :: Loc, bind :: Bind, value :: Expr) with:
	serialize(self): # For ForBind.s_for_bind:
		"{ \"_type\" : \"s_for_bind\""
		.append(", \"l\" : ").append(self.l.serialize())
		.append(", \"bind\" : ").append(self.bind.serialize())
		.append(", \"value\" : ").append(self.value.serialize()).append("}")
	end,
    tosource(self):
      PP.group(self.bind.tosource() + break-one + str-from + break-one + self.value.tosource())
    end
end

data VariantMember:
  | s_variant_member(l :: Loc, member_type :: String, bind :: Bind) with:
	serialize(self): # For VariantMember.s_variant_member:
		"{ \"_type\" : \"s_variant_member\""
		.append(", \"l\" : ").append(self.l.serialize())
		.append(", \"member_type\" : ").append(self.member_type.tostring())
		.append(", \"bind\" : ").append(self.bind.serialize()).append("}")
	end,
    tosource(self):
      if self.member_type <> "normal":
        PP.str(self.member_type) + str-space + self.bind.tosource()
      else:
        self.bind.tosource()
      end
    end
end

data Variant:
  | s_variant(
      l :: Loc,
      name :: String,
      members :: List<VariantMember>,
      with_members :: List<Member>
    ) with:
	serialize(self): # For Variant.s_variant:
		"{ \"_type\" : \"s_variant\""
		.append(", \"l\" : ").append(self.l.serialize())
		.append(", \"name\" : ").append(self.name.tostring())
		.append(", \"members\" : ").append(pyret_list_serializer(self.members, fun(x): x.serialize();))
		.append(", \"with_members\" : ").append(pyret_list_serializer(self.with_members, fun(x): x.serialize();)).append("}")
	end,
    tosource(self):
      header-nowith =
        PP.str(self.name)
        + PP.surround-separate(INDENT, 0, PP.mt-doc, PP.lparen, PP.commabreak, PP.rparen,
        self.members.map(fun(b): b.tosource() end))
      header = PP.group(header-nowith + break-one + str-with)
      withs = self.with_members.map(fun(m): m.tosource() end)
      if list.is-empty(withs): header-nowith
      else: header + PP.group(PP.nest(INDENT, break-one + PP.separate(PP.commabreak, withs)))
      end
    end
  | s_singleton_variant(
      l :: Loc,
      name :: String,
      with_members :: List<Member>
    ) with:
	serialize(self): # For Variant.s_singleton_variant:
		"{ \"_type\" : \"s_singleton_variant\""
		.append(", \"l\" : ").append(self.l.serialize())
		.append(", \"name\" : ").append(self.name.tostring())
		.append(", \"with_members\" : ").append(pyret_list_serializer(self.with_members, fun(x): x.serialize();)).append("}")
	end,
    tosource(self):
      header-nowith = PP.str(self.name)
      header = PP.group(header-nowith + break-one + str-with)
      withs = self.with_members.map(fun(m): m.tosource() end)
      if list.is-empty(withs): header-nowith
      else: header + PP.group(PP.nest(INDENT, break-one + PP.separate(PP.commabreak, withs)))
      end
    end
  | s_datatype_variant(
      l :: Loc,
      name :: String,
      members :: List<VariantMember>,
      constructor :: Constructor
    ) with:
	serialize(self): # For Variant.s_datatype_variant:
		"{ \"_type\" : \"s_datatype_variant\""
		.append(", \"l\" : ").append(self.l.serialize())
		.append(", \"name\" : ").append(self.name.tostring())
		.append(", \"members\" : ").append(pyret_list_serializer(self.members, fun(x): x.serialize();))
		.append(", \"constructor\" : ").append(self.constructor.serialize()).append("}")
	end,
    label(self): "s_datatype_variant" end,
    tosource(self):
      PP.str("FIXME 10/24/2013: dbp doesn't understand this pp stuff")
    end
  | s_datatype_singleton_variant(
      l :: Loc,
      name :: String,
      constructor :: Constructor
    ) with:
	serialize(self): # For Variant.s_datatype_singleton_variant:
		"{ \"_type\" : \"s_datatype_singleton_variant\""
		.append(", \"l\" : ").append(self.l.serialize())
		.append(", \"name\" : ").append(self.name.tostring())
		.append(", \"constructor\" : ").append(self.constructor.serialize()).append("}")
	end,
    label(self): "s_datatype_singleton_variant" end,
    tosource(self):
      PP.str("FIXME 10/24/2013: dbp doesn't understand this pp stuff")
    end
end

data Constructor:
  | s_datatype_constructor(
      l :: Loc,
      self :: String,
      body :: Expr
      ) with:
	serialize(self): # For Constructor.s_datatype_constructor:
		"{ \"_type\" : \"s_datatype_constructor\""
		.append(", \"l\" : ").append(self.l.serialize())
		.append(", \"self\" : ").append(self.self.tostring())
		.append(", \"body\" : ").append(self.body.serialize()).append("}")
	end,
    label(self): "s_datatype_constructor" end,
    tosource(self):
      PP.str("FIXME 10/24/2013: dbp doesn't understand this pp stuff")
    end
end

data IfBranch:
  | s_if_branch(l :: Loc, test :: Expr, body :: Expr) with:
	serialize(self): # For IfBranch.s_if_branch:
		"{ \"_type\" : \"s_if_branch\""
		.append(", \"l\" : ").append(self.l.serialize())
		.append(", \"test\" : ").append(self.test.serialize())
		.append(", \"body\" : ").append(self.body.serialize()).append("}")
	end,
    tosource(self):
      str-if
        + PP.nest(2*INDENT, self.test.tosource()+ str-colon)
        + PP.nest(INDENT, break-one + self.body.tosource())
    end
end

data CasesBranch:
  | s_cases_branch(l :: Loc, name :: String, args :: List<Bind>, body :: Expr) with:
	serialize(self): # For CasesBranch.s_cases_branch:
		"{ \"_type\" : \"s_cases_branch\""
		.append(", \"l\" : ").append(self.l.serialize())
		.append(", \"name\" : ").append(self.name.tostring())
		.append(", \"args\" : ").append(pyret_list_serializer(self.args, fun(x): x.serialize();))
		.append(", \"body\" : ").append(self.body.serialize()).append("}")
	end,
    tosource(self):
      PP.group(PP.str("| " + self.name)
          + PP.surround-separate(INDENT, 0, PP.mt-doc, PP.lparen, PP.commabreak, PP.rparen,
          self.args.map(fun(a): a.tosource() end)) + break-one + str-thickarrow) + break-one +
      self.body.tosource()
    end
end

data Ann:
  | a_blank with:
    tosource(self): str-any end
  | a_any with:
    tosource(self): str-any end
  | a_name(l :: Loc, id :: String) with:
	serialize(self): # For Ann.a_name:
		"{ \"_type\" : \"a_name\""
		.append(", \"l\" : ").append(self.l.serialize())
		.append(", \"id\" : ").append(self.id.tostring()).append("}")
	end,
    tosource(self): PP.str(self.id) end
  | a_arrow(l :: Loc, args :: List<Ann>, ret :: Ann) with:
	serialize(self): # For Ann.a_arrow:
		"{ \"_type\" : \"a_arrow\""
		.append(", \"l\" : ").append(self.l.serialize())
		.append(", \"args\" : ").append(pyret_list_serializer(self.args, fun(x): x.serialize();))
		.append(", \"ret\" : ").append(self.ret.serialize()).append("}")
	end,
    tosource(self):
      PP.surround(INDENT, 1, PP.lparen,
        PP.separate(str-space,
          [PP.separate(PP.commabreak,
            self.args.map(fun(f): f.tosource() end))] + [str-arrow, self.ret.tosource()]), PP.rparen)
    end
  | a_method(l :: Loc, args :: List<Ann>, ret :: Ann) with:
	serialize(self): # For Ann.a_method:
		"{ \"_type\" : \"a_method\""
		.append(", \"l\" : ").append(self.l.serialize())
		.append(", \"args\" : ").append(pyret_list_serializer(self.args, fun(x): x.serialize();))
		.append(", \"ret\" : ").append(self.ret.serialize()).append("}")
	end,
    tosource(self): PP.str("NYI: A_method") end
  | a_record(l :: Loc, fields :: List<AField>) with:
	serialize(self): # For Ann.a_record:
		"{ \"_type\" : \"a_record\""
		.append(", \"l\" : ").append(self.l.serialize())
		.append(", \"fields\" : ").append(pyret_list_serializer(self.fields, fun(x): x.serialize();)).append("}")
	end,
    tosource(self):
      PP.surround-separate(INDENT, 1, PP.lbrace + PP.rbrace, PP.lbrace, PP.commabreak, PP.rbrace,
        self.fields.map(fun(f): f.tosource() end))
    end
  | a_app(l :: Loc, ann :: Ann, args :: List<Ann>) with:
	serialize(self): # For Ann.a_app:
		"{ \"_type\" : \"a_app\""
		.append(", \"l\" : ").append(self.l.serialize())
		.append(", \"ann\" : ").append(self.ann.serialize())
		.append(", \"args\" : ").append(pyret_list_serializer(self.args, fun(x): x.serialize();)).append("}")
	end,
    tosource(self):
      PP.group(self.ann.tosource()
          + PP.group(PP.langle + PP.nest(INDENT,
            PP.separate(PP.commabreak, self.args.map(fun(f): f.tosource() end))) + PP.rangle))
    end
  | a_pred(l :: Loc, ann :: Ann, exp :: Expr) with:
	serialize(self): # For Ann.a_pred:
		"{ \"_type\" : \"a_pred\""
		.append(", \"l\" : ").append(self.l.serialize())
		.append(", \"ann\" : ").append(self.ann.serialize())
		.append(", \"exp\" : ").append(self.exp.serialize()).append("}")
	end,
    tosource(self): self.ann.tosource() + PP.parens(self.exp.tosource()) end
  | a_dot(l :: Loc, obj :: String, field :: String) with:
	serialize(self): # For Ann.a_dot:
		"{ \"_type\" : \"a_dot\""
		.append(", \"l\" : ").append(self.l.serialize())
		.append(", \"obj\" : ").append(self.obj.tostring())
		.append(", \"field\" : ").append(self.field.tostring()).append("}")
	end,
    tosource(self): PP.str(self.obj + "." + self.field) end
end

data AField:
  | a_field(l :: Loc, name :: String, ann :: Ann) with:
	serialize(self): # For AField.a_field:
		"{ \"_type\" : \"a_field\""
		.append(", \"l\" : ").append(self.l.serialize())
		.append(", \"name\" : ").append(self.name.tostring())
		.append(", \"ann\" : ").append(self.ann.serialize()).append("}")
	end,
    tosource(self):
      if is-a_blank(self.ann): PP.str(self.name)
      else: PP.infix(INDENT, 1, str-coloncolon, PP.str(self.name), self.ann.tosource())
      end
    end
end
