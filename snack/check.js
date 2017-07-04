var util = require("./util");

/*********************公共方法********************************/

//判断是否配置了扩展名，完善文件名
var supplyFileName = function(fileName,extension){
	var e = "." + extension, elength = e.length, flength = fileName.length;
	return flength > elength && fileName.lastIndexOf(e) == flength - elength ? fileName : fileName + e;
};

//完善简化的路径/过滤/排除的配置
var supplyPaths = function(taskType,tarType,projectPath,path){
	var source = [], filter = [], exclude = [], target = [], fileName = "", tmpFilters = [], tmpExclude = [], isAll = false, isSameDir = false;
	if(util.type(path) == "string"){
		source = [path];
		target = [path];
	}else{
		if(path.source){
			source = path.source;
			if(util.type(source) == "string"){
				source = [source];
			}
			filter = path.filter ? path.filter : [];
			if(util.type(filter) == "string"){
				filter = [filter];
			}
			exclude = path.exclude ? path.exclude : [];
			if(util.type(exclude) == "string"){
				exclude = [exclude];
			}
			if(path.target){
				target = path.target;
			}else{
				target = source;
				isSameDir = true
			}
			if(util.type(target) == "string"){
				target = [target];
			}
			fileName = path.fileName ? path.fileName : "";
		}else{
			console.log(taskType +"任务，没有提供源文件位置！");
		}
	}
	//构造需要取出的文件路径
	for(var s in source){
		var p = util.supplyLastSymbol(projectPath + source[s],"/");
		//过滤出的文件
		if(filter.length > 0){
			for(var f in filter){
				tmpFilters.push(p + supplyFileName(filter[f],taskType));
			}
		}else{
			tmpFilters.push(p + "*." + taskType);
			isAll = true;
		}
		//需要排除的文件路径
		if(exclude.length > 0){
			for(var e in exclude){
				tmpExclude.push(p + supplyFileName(exclude[e],taskType));
			}
		}
	}
	//构造目标文件名
	if(fileName != ""){
		fileName = supplyFileName(fileName,tarType);
	}
	//构造目标目录路径
	for(var t in target){
		target[t] = util.supplyLastSymbol(projectPath + target[t],"/") + fileName;
	}
	return {
		filter : tmpFilters,
		exclude : tmpExclude,
		target : target,
		fileName : fileName,
		isAll : isAll,
		isSameDir : isSameDir
	};
};

//反转paths，加非表示
exports.reversePaths = function(paths){
	var re = [], index = 0;
	if(util.isArray(paths)){
		for(;index < paths.length;index++){
			re.push("!" + paths[index]);
		}
	}
	return re;
};

exports.getFileName = function(path,extension){
	return path.substring(path.lastIndexOf("/") + 1 , path.indexOf("." + extension));
};

/***************************配置检查*********************************/

//构造基本路径
exports.getBasePath = function(snack){
	var basePath = snack.basePath;
	if(basePath){
		basePath = util.supplyLastSymbol(basePath,"/");
	}else{
		basePath = "./";
	}
	return basePath;
};

//是否配置了工程
exports.hasProjects = function(snack){
	if(snack.projects && util.isArray(snack.projects)){
		return true;
	}else{
		console.log("没有设置需要处理的工程 或者 配置错误！");
	}
	return false;
};

//构造工程的基本路径
exports.getProjectBasePath = function(project){
	var path = "";
	if(project.name){
		path = project.name;
	}
	path = util.supplyLastSymbol(path,"/");
	if(project.base){
		path += project.base;
	}
	path = util.supplyLastSymbol(path,"/");
	return path;
};

/**************************less任务**********************/

//是否需要处理less
exports.doLess = function(project){
	var less = project.less;
	if(less && !util.isEmptyObject(less) && less.paths && util.isArray(less.paths)){
		return true;
	}
	return false;
};

//构造less各种条件
exports.getLessPath = function(projectPath , path){
	var taskType = "less", tarType = "css";
	return supplyPaths(taskType,tarType,projectPath,path);
};

/*******************************css任务***************************/

//是否需要处理css
exports.doCss = function(project){
	var css = project.css;
	if(css && !util.isEmptyObject(css) && css.paths && util.isArray(css.paths)){
		return true;
	}
	return false;	
};

//构造css各种条件
exports.getCssPath = function(projectPath , path){
	var taskType = "css", tarType = "css";
	return supplyPaths(taskType,tarType,projectPath,path);
};

/****************************js任务**********************************/

//是否需要处理js
exports.doJs = function(project){
	var js = project.js;
	if(js && !util.isEmptyObject(js) && js.paths && util.isArray(js.paths)){
		return true;
	}
	return false;
};

//构造js各种条件
exports.getJsPath = function(projectPath , path){
	var taskType = "js", tarType = "js";
	return supplyPaths(taskType,tarType,projectPath,path);
};

/****************************html任务**********************************/

//是否需要处理html
exports.doHtml = function(project){
	var html = project.html;
	if(html && !util.isEmptyObject(html) && html.paths && util.isArray(html.paths)){
		return true;
	}
	return false;
};

//构造html各种条件
exports.getHtmlPath = function(projectPath , path){
	var taskType = "html", tarType = "html";
	return supplyPaths(taskType,tarType,projectPath,path);
};

/******************************* copy任务 ***************************/
//是否需要处理copy
exports.doCopy = function(project){
	var copy = project.copy;
	if(copy && !util.isEmptyObject(copy) && copy.paths && util.isArray(copy.paths)){
		return true;
	}
	return false;
};

//构造copy各种条件
exports.getCopyPath = function(projectPath , path){
	var taskType = "*", tarType = "*";
	return supplyPaths(taskType,tarType,projectPath,path);
};

/******************************* imagemin任务 ***************************/
//是否需要处理imagemin
exports.doImagemin = function(project){
	var imagemin = project.imagemin;
	if(imagemin && !util.isEmptyObject(imagemin) && imagemin.paths && util.isArray(imagemin.paths)){
		return true;
	}
	return false;
};

//构造imagemin各种条件
exports.getImageminPath = function(projectPath , path){
	var taskType = "*", tarType = "*";
	return supplyPaths(taskType,tarType,projectPath,path);
};