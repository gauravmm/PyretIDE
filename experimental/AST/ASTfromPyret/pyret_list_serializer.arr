#lang pyret

fun <A> pyret_list_serializer(alod :: List<A>, conv :: (A -> String)) -> String:
	for fold(rv from "", elt from alod):
		rv.append(", ").append(conv(elt))
	end
end

pyret_list_serializer(["Hello", "Boo!", "Goodbye"], fun(x): "{ \"String\" : \"".append(x).append("\"}");)