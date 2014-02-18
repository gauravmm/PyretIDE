#
# The parts of the Pyret AST, as a Python list-structure.
# It is a list of data types, where each data type is (name, list of variants) and each variant is (name, list of types) and each type is (name, annotation).
# Note that annotations with type-variables are represented in test form (i.e. List<All>). We can ignore them for now.
#
pyret_ast = [
("Loc", [("location", [("file", "String"), ("line", "Any"), ("column", "Any"), ]), ]),

("Program", [("s_program", [("l", "Loc"), ("imports", "List<Header>"), ("block", "Expr"), ]), ]),

("Header", [("s_import", [("l", "Loc"), ("file", "ImportType"), ("name", "String"), ]), ("s_provide", [("l", "Loc"), ("block", "Expr"), ]), ("s_provide_all", [("l", "Loc"), ]), ]),

("ImportType", [("s_file_import", [("file", "String"), ]), ("s_const_import", [("module", "String"), ]), ]),

("Hint", [("h_use_loc", [("l", "Loc"), ]), ]),

("Expr", [("s_hint_exp", [("l", "Loc"), ("hints", "List<Hint>"), ("e", "Expr"), ]), ("s_block", [("l", "Loc"), ("stmts", "List<Expr>"), ]), ("s_user_block", [("l", "Loc"), ("body", "Expr"), ]), ("s_fun", [("l", "Loc"), ("name", "String"), ("params", "List<String>"), ("args", "List<Bind>"), ("ann", "Ann"), ("doc", "String"), ("body", "Expr"), ("check", "Expr"), ]), ("s_var", [("l", "Loc"), ("name", "Bind"), ("value", "Expr"), ]), ("s_let", [("l", "Loc"), ("name", "Bind"), ("value", "Expr"), ]), ("s_graph", [("l", "Loc"), ("bindings", "List<is-s_let>"), ]), ("s_when", [("l", "Loc"), ("test", "Expr"), ("block", "Expr"), ]), ("s_assign", [("l", "Loc"), ("id", "String"), ("value", "Expr"), ]), ("s_if", [("l", "Loc"), ("branches", "List<IfBranch>"), ]), ("s_if_else", [("l", "Loc"), ("branches", "List<IfBranch>"), ("_else", "Expr"), ]), ("s_cases", [("l", "Loc"), ("type", "Ann"), ("val", "Expr"), ("branches", "List<CasesBranch>"), ]), ("s_cases_else", [("l", "Loc"), ("type", "Ann"), ("val", "Expr"), ("branches", "List<CasesBranch>"), ("_else", "Expr"), ]), ("s_try", [("l", "Loc"), ("body", "Expr"), ("id", "Bind"), ("_except", "Expr"), ]), ("s_op", [("l", "Loc"), ("op", "String"), ("left", "Expr"), ("right", "Expr"), ]), ("s_check_test", [("l", "Loc"), ("op", "String"), ("left", "Expr"), ("right", "Expr"), ]), ("s_not", [("l", "Loc"), ("expr", "Expr"), ]), ("s_paren", [("l", "Loc"), ("expr", "Expr"), ]), ("s_lam", [("l", "Loc"), ("params", "List<String>"), ("args", "List<Bind>"), ("ann", "Ann"), ("doc", "String"), ("body", "Expr"), ("check", "Expr"), ]), ("s_method", [("l", "Loc"), ("args", "List<Bind>"), ("ann", "Ann"), ("doc", "String"), ("body", "Expr"), ("check", "Expr"), ]), ("s_extend", [("l", "Loc"), ("super", "Expr"), ("fields", "List<Member>"), ]), ("s_update", [("l", "Loc"), ("super", "Expr"), ("fields", "List<Member>"), ]), ("s_obj", [("l", "Loc"), ("fields", "List<Member>"), ]), ("s_list", [("l", "Loc"), ("values", "List<Expr>"), ]), ("s_app", [("l", "Loc"), ("_fun", "Expr"), ("args", "List<Expr>"), ]), ("s_left_app", [("l", "Loc"), ("obj", "Expr"), ("_fun", "Expr"), ("args", "List<Expr>"), ]), ("s_id", [("l", "Loc"), ("id", "String"), ]), ("s_num", [("l", "Loc"), ("n", "Number"), ]), ("s_bool", [("l", "Loc"), ("b", "Bool"), ]), ("s_str", [("l", "Loc"), ("s", "String"), ]), ("s_dot", [("l", "Loc"), ("obj", "Expr"), ("field", "String"), ]), ("s_get_bang", [("l", "Loc"), ("obj", "Expr"), ("field", "String"), ]), ("s_bracket", [("l", "Loc"), ("obj", "Expr"), ("field", "Expr"), ]), ("s_colon", [("l", "Loc"), ("obj", "Expr"), ("field", "String"), ]), ("s_colon_bracket", [("l", "Loc"), ("obj", "Expr"), ("field", "Expr"), ]), ("s_data", [("l", "Loc"), ("name", "String"), ("params", "List<String>"), ("mixins", "List<Expr>"), ("variants", "List<Variant>"), ("shared_members", "List<Member>"), ("check", "Expr"), ]), ("s_datatype", [("l", "Loc"), ("name", "String"), ("params", "List<String>"), ("variants", "List<Variant>"), ("check", "Expr"), ]), ("s_for", [("l", "Loc"), ("iterator", "Expr"), ("bindings", "List<ForBind>"), ("ann", "Ann"), ("body", "Expr"), ]), ("s_check", [("l", "Loc"), ("body", "Expr"), ]), ]),

("Bind", [("s_bind", [("l", "Loc"), ("id", "String"), ("ann", "Ann"), ]), ]),

("Member", [("s_data_field", [("l", "Loc"), ("name", "Expr"), ("value", "Expr"), ]), ("s_mutable_field", [("l", "Loc"), ("name", "Expr"), ("ann", "Ann"), ("value", "Expr"), ]), ("s_once_field", [("l", "Loc"), ("name", "Expr"), ("ann", "Ann"), ("value", "Expr"), ]), ("s_method_field", [("l", "Loc"), ("name", "Expr"), ("args", "List<Bind>"), ("ann", "Ann"), ("doc", "String"), ("body", "Expr"), ("check", "Expr"), ]), ]),

("ForBind", [("s_for_bind", [("l", "Loc"), ("bind", "Bind"), ("value", "Expr"), ]), ]),

("VariantMember", [("s_variant_member", [("l", "Loc"), ("member_type", "String"), ("bind", "Bind"), ]), ]),

("Variant", [("s_variant", [("l", "Loc"), ("name", "String"), ("members", "List<VariantMember>"), ("with_members", "List<Member>"), ]), ("s_singleton_variant", [("l", "Loc"), ("name", "String"), ("with_members", "List<Member>"), ]), ("s_datatype_variant", [("l", "Loc"), ("name", "String"), ("members", "List<VariantMember>"), ("constructor", "Constructor"), ]), ("s_datatype_singleton_variant", [("l", "Loc"), ("name", "String"), ("constructor", "Constructor"), ]), ]),

("Constructor", [("s_datatype_constructor", [("l", "Loc"), ("self", "String"), ("body", "Expr"), ]), ]),

("IfBranch", [("s_if_branch", [("l", "Loc"), ("test", "Expr"), ("body", "Expr"), ]), ]),

("CasesBranch", [("s_cases_branch", [("l", "Loc"), ("name", "String"), ("args", "List<Bind>"), ("body", "Expr"), ]), ]),

("Ann", [("a_name", [("l", "Loc"), ("id", "String"), ]), ("a_arrow", [("l", "Loc"), ("args", "List<Ann>"), ("ret", "Ann"), ]), ("a_method", [("l", "Loc"), ("args", "List<Ann>"), ("ret", "Ann"), ]), ("a_record", [("l", "Loc"), ("fields", "List<AField>"), ]), ("a_app", [("l", "Loc"), ("ann", "Ann"), ("args", "List<Ann>"), ]), ("a_pred", [("l", "Loc"), ("ann", "Ann"), ("exp", "Expr"), ]), ("a_dot", [("l", "Loc"), ("obj", "String"), ("field", "String"), ]), ]),

("AField", [("a_field", [("l", "Loc"), ("name", "String"), ("ann", "Ann"), ]), ]),

]
