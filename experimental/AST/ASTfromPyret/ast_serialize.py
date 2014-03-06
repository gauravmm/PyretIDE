import os
import datetime

from ast_python import pyret_ast
# Assumes that ast_python.py contains the output of astexport.arr called on the latest version of the pyret AST.
# The parts of the Pyret AST, as a Python list-structure.
# It is a list of data types, where each data type is (name, list of variants) and each variant is (name, argument) and each argument is (name, annotation).

#
# Global constants
#

output = "serialize.txt"
header = "AST Serialize Code\nGenerated " + str(datetime.datetime.now()) + " on " + os.environ['LOGNAME'] + "\n\n"

#
# Output functions
#

def createNewType(typeName, variants=[], isRootType=False):
	print("" + typeName + "");
			
def createNewTypeVariant(typeName, variantName, arguments):
	print("     |- " + variantName + " (" + getTypeVariantArgumentsString(typeName, variantName, arguments) + ")");
	
	return """
	# For {0[data]}.{0[typestr]}:
	serialize(self):
		"{{ \\\"_type\\\" : \\\"{0[variant]}\\\""
		{0[serializestr]}.append("}}")
	end
""".format({
	'data': typeName,
	'variant': variantName,
	'typestr': variantName + " (" + getTypeVariantArgumentsString(typeName, variantName, arguments) + ")",
	'serializestr': "\n\t\t".join(map(lambda (name, type): serializingString(name, type), arguments))
	})


def key(name):
	return ".append(\", \\\"" + str(name) + "\\\" : \")"

def qn(name, fn):
	return key(name) + ".append(" +  str(name) + "." + fn + ")"
	
def serializingString(name, type):
	if type == "String" or type == "Number":
		return  qn(name, "tostring()")
	elif type.startswith("List"):
		lfunc = ""
		if type.find("String") >= 0 or type.find("Number") >= 0:
			lfunc = "fun(x): x.tostring();"
		else:
			lfunc = "fun(x): x.serialize();"
		
		# Call the list serializer:
		return key(name) + ".append(pyret_list_serializer(" + name + ", " + lfunc + "))"
	else :
		return qn(name, "serialize()") 
			
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
	f = open(output, 'w')
	f.write(header)
	
	for (data_type_name, variants) in pyret_ast:
		createNewType(data_type_name, variants)
		for (variant_name, args) in variants:
			f.write(createNewTypeVariant(data_type_name, variant_name, args))
		print("");
	f.close()
	
main()		
