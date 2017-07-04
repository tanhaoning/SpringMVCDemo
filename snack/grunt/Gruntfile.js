module.exports = function(grunt){
	"use strict";
	
	/****************** 声明：变量、函数 ******************/
	var
	//工具类
	util = require("../util"),
	//配置检查类
	check = require("../check"),
	//读取snack.json
	snack = grunt.file.readJSON("../snack.json"),
	//grunt初始化用的options
	initOption = {
		//读取package.json
		//pkg : grunt.file.readJSON("package.json"),
		//less
		less : {},
		//文件合并
		concat : {},
		//js压缩，minify
		uglify : {},
		//css压缩，minify
		cssmin : {},
		//文件复制
		copy : {},
		//自定编译侦听
		watch : {}
	},
	//基础路径
	basePath = check.getBasePath(snack),
	//自定义任务
	mytasks = [],
	//添加到自定义任务
	addToMytasks = function(task){
		if(util.inArray(task,mytasks) == -1){
			mytasks.push(task);
		}
	},
	//扫描并排除磁盘里的资源，返回需要的资源，用于*的时候比较好
	getRealSources = function(filters, excludes){
		var tmpExcludes = grunt.file.expand(excludes);
		return grunt.file.expand(filters).filter(function(f){
			if(tmpExcludes.length == 0){
				return true;
			}else{
				return util.inArray(f,tmpExcludes) == -1;
			}
		});
	},
	//处理less
	buildLess = function(project){
		var files = {} , projectPath , paths , path , index;
		//构造less所在工程的基本路径
		projectPath = basePath + check.getProjectBasePath(project);
		//开始遍历
		paths = project.less.paths;
		for(index in paths){
			path = paths[index];
			var data = check.getLessPath(projectPath , path);
			//排除需要排除的路径
			var filters = getRealSources(data.filter,data.exclude);
			//增加watch任务
			if(project.less.watch){
				if(typeof path.watch == "undefined" || path.watch){
					addToMytasks("watch");
					initOption.watch["build_" + project.name + index] = {
						files: filters,
						tasks: "less"
					};
				}
			}
			//构造任务资源
			if(data.fileName != ""){
				for(var ta in data.target){
					var files = {}; files[data.target[ta]] = filters;
					initOption.less["build_" + project.name + "_" + index] = {
						files: files
					};
					if(path.minify){
						var minName = check.getFileName(data.target[ta],"css")  + ".min.css";
						//生成压缩minify任务
						initOption.cssmin["build_" + project.name + "_less_min_" + index] = {
							files: {
								minName : data.target[ta]
							}
						};
					}
				}
			}else{
				//没有指定输入文件名的，按输出文件名处理
				for(var ta in data.target){
					for(var f in filters){
						var fileName = check.getFileName(filters[f],"less")  + ".css";
						var files = {};
						files[data.target[ta] + fileName] = filters[f];
						initOption.less["build_" + project.name + "_" + index] = {
							files: files
						};
						if(path.minify){
							var minName = check.getFileName(filters[f],"less")  + ".min.css";
							//生成压缩minify任务
							initOption.cssmin["build_" + project.name + "_less_min_" + index] = {
								files: {
									minName : data.target[ta] + fileName
								}
							};
						}
					}
				}
			}
		}
		//增加到任务列表
		addToMytasks("less");
		//构建less任务
		initOption.less["build_" + project.name] = {
			files: files
		};

	},
	//处理css
	buildCss = function(project){
		var mergeFiles = {}, minifyFiles = {}, projectPath , paths , path , index;
		//构造less所在工程的基本路径
		projectPath = basePath + check.getProjectBasePath(project);
		//开始遍历
		paths = project.css.paths;
		for(index in paths){
			path = paths[index];
			var data = check.getCssPath(projectPath , path);
			//排除需要排除的路径
			var filters = data.filter.concat(check.reversePaths(data.exclude));
			if(data.isAll){
				filters = getRealSources(data.filter,data.exclude);
			}
			//增加watch任务
			if(project.css.watch){
				if(typeof path.watch == "undefined" || path.watch){
					addToMytasks("watch");
					initOption.watch["build_" + project.name + index] = {
						files: filters,
						tasks: "css"
					};
				}
			}
			//构造任务
			if(data.fileName != ""){
				for(var ta in data.target){
					var files = {}; files[data.target[ta]] = filters;
					initOption.concat["build_" + project.name + "_" + index] = {
						files : files
					};
					if(path.minify){
						var minName = check.getFileName(data.target[ta],"css")  + ".min.css";
						//生成压缩minify任务
						initOption.cssmin["build_" + project.name + "_css_min_" + index] = {
							files: {
								minName : data.target[ta]
							}
						};
					}
				}
			}else{
				for(var ta in data.target){
					for(var f in filters){
						var fileName = check.getFileName(filters[f],"css")  + ".css";
						mergeFiles[data.target[ta] + fileName] = filters[f];

						var files = {}; files[data.target[ta]] = filters;
						initOption.concat["build_" + project.name + "_" + index] = {
							files : files
						};

						if(path.minify){

						}
					}
				}
			}

			switch(path.action){
				case "merge" :
					if(data.fileName != ""){

					}else{

					}
					break;
				case "minify" :
					if(data.fileName != ""){
						for(var ta in data.target){
							minifyFiles[data.target[ta].replace(".css",".min.css")] = data.filter.concat(check.reversePaths(data.exclude));
						}
					}else{
						//没有设置文件名，按文件
						var filters = data.filter.concat(check.reversePaths(data.exclude));
						if(data.isAll){
							filters = getRealSources(data.filter,data.exclude);
						}
						for(var ta in data.target){
							for(var f in filters){
								var fileName = check.getFileName(filters[f],"css")  + ".min.css";
								minifyFiles[data.target[ta] + fileName] = filters[f];
							}
						}
					}
					break;
				case "merge&minify" :
					if(data.fileName != ""){
						for(var ta in data.target){
							mergeFiles[data.target[ta]] = data.filter.concat(check.reversePaths(data.exclude));
							minifyFiles[data.target[ta].replace(".css",".min.css")] = data.filter.concat(check.reversePaths(data.exclude));
						}
					}else{
						//没有设置文件名，按文件
						var filters = data.filter.concat(check.reversePaths(data.exclude));
						if(data.isAll){
							filters = getRealSources(data.filter,data.exclude);
						}
						for(var ta in data.target){
							for(var f in filters){
								var fileName = check.getFileName(filters[f],"css");
								mergeFiles[data.target[ta] + fileName + ".css"] = filters[f];
								minifyFiles[data.target[ta] + fileName + ".min.css"] = filters[f];
							}
						}
					}
					break;
			}
		}
		//生成合并任务
		if(!util.isEmptyObject(mergeFiles)){
			initOption.concat["build_" + project.name + "_css"] = {
				files : mergeFiles
			};
			//增加到任务列表
			addToMytasks("concat");
		}
		if(!util.isEmptyObject(minifyFiles)){
			//生成压缩minify任务
			initOption.cssmin["build_" + project.name] = {
				files: minifyFiles
			};
			//增加到任务列表
			addToMytasks("cssmin");
		}
	},
	//处理js
	buildJs = function(project){
		var mergeFiles = {}, minifyFiles = {}, projectPath , jsPaths , jsPath , jsIndex;
		//构造less所在工程的基本路径
		projectPath = basePath + check.getProjectBasePath(project);
		//开始遍历
		jsPaths = project.js.paths;
		for(jsIndex in jsPaths){
			jsPath = jsPaths[jsIndex];
			var data = check.getJsPath(projectPath , jsPath);
			//
			//需要排除的文件
			switch(jsPath.action){
				case "merge" :
					if(data.fileName != ""){
						for(var ta in data.target){
							mergeFiles[data.target[ta]] = data.filter.concat(check.reversePaths(data.exclude));
						}
					}else{
						//没有设置文件名，按文件合并 -。-！
						var filters = data.filter.concat(check.reversePaths(data.exclude));
						if(data.isAll){
							filters = getRealSources(data.filter,data.exclude);
						}
						for(var ta in data.target){
							for(var f in filters){
								var fileName = check.getFileName(filters[f],"js")  + ".js";
								mergeFiles[data.target[ta] + fileName] = filters[f];
							}
						}
					}
					break;
				case "minify" :
					if(data.fileName != ""){
						for(var ta in data.target){
							minifyFiles[data.target[ta].replace(".js",".min.js")] = data.filter.concat(check.reversePaths(data.exclude));
						}
					}else{
						//没有设置文件名，按文件
						var filters = data.filter.concat(check.reversePaths(data.exclude));
						if(data.isAll){
							filters = getRealSources(data.filter,data.exclude);
						}
						for(var ta in data.target){
							for(var f in filters){
								var fileName = check.getFileName(filters[f],"js")  + ".min.js";
								minifyFiles[data.target[ta] + fileName] = filters[f];
							}
						}
					}
					break;
				case "merge&minify" :
					if(data.fileName != ""){
						for(var ta in data.target){
							mergeFiles[data.target[ta]] = data.filter.concat(check.reversePaths(data.exclude));
							minifyFiles[data.target[ta].replace(".js",".min.js")] = data.filter.concat(check.reversePaths(data.exclude));
						}
					}else{
						//没有设置文件名，按文件
						var filters = data.filter.concat(check.reversePaths(data.exclude));
						if(data.isAll){
							filters = getRealSources(data.filter,data.exclude);
						}
						for(var ta in data.target){
							for(var f in filters){
								var fileName = check.getFileName(filters[f],"js");
								mergeFiles[data.target[ta] + fileName + ".js"] = filters[f];
								minifyFiles[data.target[ta] + fileName + ".min.js"] = filters[f];
							}
						}
					}
					break;
			}
		}
		//生成合并任务
		if(!util.isEmptyObject(mergeFiles)){
			initOption.concat["build_" + project.name + "_js"] = {
				files : mergeFiles
			};
			//增加到任务列表
			addToMytasks("concat");
		}
		if(!util.isEmptyObject(minifyFiles)){
			//生成压缩minify任务
			initOption.uglify["build_" + project.name] = {
				files: minifyFiles
			};
			//增加到任务列表
			addToMytasks("uglify");
		}
	},
	//处理copy
	buildCopy = function(project){
		var copyFiles = [], projectPath , copyPaths , copyPath , copyIndex;
		//构造less所在工程的基本路径
		projectPath = basePath + check.getProjectBasePath(project);
		//开始遍历
		copyPaths = project.copy.paths;
		for(copyIndex in copyPaths){
			copyPath = copyPaths[copyIndex];
			var data = check.getCopyPath(projectPath , copyPath);
			//
			var filters = data.filter.concat(check.reversePaths(data.exclude));
			if(data.isAll){
				filters = getRealSources(data.filter,data.exclude);
			}
			for(var ta in data.target){
				copyFiles.push({
					expand : true,
					flatten : true,
					src : filters,
					dest : data.target[ta]
				});
			}
		}
		//生成复制任务
		if(!util.isEmptyObject(copyFiles)){
			initOption.copy["build_" + project.name] = {
				files : copyFiles
			};
			//增加到任务列表
			addToMytasks("copy");
		}
	}

	;
	
	/****************** grunt初始化 ******************/
	//构造grunt初始化options
	(function(){
		var project , projectIndex;
		//配置projects才能继续
		if(check.hasProjects(snack)){
			//遍历工程
			for(projectIndex in snack.projects){
				project = snack.projects[projectIndex];
				//less
				if(check.doLess(project)){
					buildLess(project);
				}
				//css
				if(check.doCss(project)){
					buildCss(project);
				}
				//js
				if(check.doJs(project)){
					buildJs(project);
				}
				//copy
				if(check.doCopy(project)){
					buildCopy(project);
				}
				//watch
			}
			console.log("当前任务：" + mytasks);
			console.log("当前任务配置：" + JSON.stringify(initOption));
		}
	})();
	grunt.initConfig(initOption);
	
	/****************** 任务加载 ******************/
	grunt.loadNpmTasks("grunt-contrib-less");
	grunt.loadNpmTasks("grunt-contrib-concat");
	grunt.loadNpmTasks("grunt-contrib-uglify");
	grunt.loadNpmTasks("grunt-contrib-cssmin");
	grunt.loadNpmTasks("grunt-contrib-copy");
	grunt.loadNpmTasks("grunt-contrib-watch");

	/****************** 自定义任务 ******************/
	//默认任务
	grunt.registerTask("default",mytasks);

};