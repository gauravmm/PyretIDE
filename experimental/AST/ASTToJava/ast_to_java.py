import os
import datetime

from ast_python import pyret_ast
# Assumes that ast_python.py contains the output of astexport.arr called on the latest version of the pyret AST.
# The parts of the Pyret AST, as a Python list-structure.
# It is a list of data types, where each data type is (name, list of variants) and each variant is (name, argument) and each argument is (name, annotation).

#
# Global constants
#

dir = "output/"
package = "edu.brown.cs.cutlass.pyret.ast" # Quite a mouthful...
author = "Edward Teach"
created = "Created " + str(datetime.datetime.now()) + " by " + os.environ['LOGNAME']
rootPyretType = "PyretASTType"

#
# Output functions
#

def writeToFile(fn, data):
	# Make the output folder if it does not exist
	if not os.path.isdir(dir):
		os.mkdir(dir)
	
	f = open(dir + fn, 'w')
	f.write(data)
	f.close()

def getFilenameFromName(name):
	return name + ".java"
	
def createNewType(typeName, variants=[], isRootType=False):
	print("[Type] " + typeName + "");
	
	writeToFile(getFilenameFromName(typeName), """/*
 * Automatically generated file. Do not modify directly.
 * {0[topcomment]}
 */

package {0[package]};

/**
 * This interface corresponds to the {0[type]} data type in the Pyret AST.
 *
 * data {0[type]}:
{0[typestr]}
 * end
 * 
 * @author {0[author]}
 */
public interface {0[type]} {0[extends]}{{
	// Nothing. This is used for type safety.
}}
""".format({
	'package': package,
	'author': author,
	'type': typeName,
	'extends': ("" if isRootType else ("extends " + rootPyretType + " ")),
	'typestr': "\n".join(getTypeVariantsString(typeName, variants, " *   | ")),
	'topcomment': created}))
			
def createNewTypeVariant(typeName, variantName, arguments):
	print("     |- " + variantName + " (" + getTypeVariantArgumentsString(typeName, variantName, arguments) + ")");
	
	writeToFile(getFilenameFromName(variantName), """/*
 * Automatically generated file. Do not modify directly.
 * {0[topcomment]}
 */

package {0[package]};
{0[importline]}
/**
 * This class corresponds to the {0[variant]} variant of type {0[data]}
 * in the Pyret AST.
 *
 * data {0[variant]}:
 *   {0[typestr]}
 *   ...
 * end
 * 
 * @author {0[author]}
 */
public final class {0[variant]} implements {0[data]} {{
	// Variables:
{0[vars]}

	/**
	 * Constructor, initializes all fields.
	 */
	public {0[variant]}({0[constructorargs]}) {{
{0[assignment]}
    }}
	
}}
""".format({
	'package': package,
	'author': author,
	'variant': variantName,
	'data': typeName,
	'typestr': variantName + " (" + getTypeVariantArgumentsString(typeName, variantName, arguments) + ")",
	'vars': "\n".join(map(lambda (name, type): "\tpublic final " + type + " " + name + ";", arguments)),
	'constructorargs': ", ".join(map(lambda (name, type): type + " " + name, arguments)),
	'assignment': "\n".join(map(lambda (name, type): "\t\tthis." + name + " = " + name + ";", arguments)),
	'importline': "\nimport java.util.List;\n",
	'topcomment': created}))
	
#
# Pretty output:
#
def getTypeVariantArgumentsString(typeName, variantName, arguments):
	arg_str = []
	for (a_name, a_type) in arguments:
		arg_str.append(a_name + " :: " + a_type)
	return ", ".join(arg_str)
	
def getTypeVariantsString(typeName, variants, prefix=""):
	arg_str = []
	for (variant_name, args) in variants:
		arg_str.append(prefix + variant_name + " (" + getTypeVariantArgumentsString(typeName, variant_name, args) + ")")
	return arg_str

#
# Main method:
#
def main():
	createNewType(rootPyretType, [], True)
	for (data_type_name, variants) in pyret_ast:
		createNewType(data_type_name, variants)
		for (variant_name, args) in variants:
			createNewTypeVariant(data_type_name, variant_name, args)
		print("");

main()		
