/***************************通用工具*********************************/

//是否空对象
exports.isEmptyObject = function(obj){
	var name;
	for ( name in obj ) {
		return false;
	}
	return true;
};

//是否数组
exports.isArray = function(obj){
	return Array.isArray(obj);
};

//是否在数组中
exports.inArray = function( elem, arr, i ) {
	return arr == null ? -1 : [].indexOf.call( arr, elem, i );
};

//类型判断
var class2type = {} , index = 0 , classes = "Boolean Number String Function Array Date RegExp Object".split(" ");
for( ; index < classes.length ; index++){
	class2type[ "[object " + classes[index] + "]" ] = classes[index].toLowerCase();
}
exports.type = function(obj){
	return obj == null ? String( obj ) : class2type[ Object.prototype.toString.call(obj) ] || "object";
};

//补充最后一个符号
exports.supplyLastSymbol = function(str,symbol){
	if(str != "" && str.charAt(str.length - 1) != symbol){
		str += symbol;
	}
	return str;
};