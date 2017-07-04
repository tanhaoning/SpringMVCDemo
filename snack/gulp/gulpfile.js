"use strict";

var snackPath = "../snack.json";
/****************** 声明：变量、函数 ******************/
var
	//gulp核心
	gulp = require("gulp"),
	//文件操作
	fs = require("fs"),
	//重命名
	rename = require("gulp-rename"),
	//less
	less = require("gulp-less"),
	//concat
	concat = require("gulp-concat"),
	//minifyCss
	minifyCss = require("gulp-minify-css"),
	//uglify
	uglify = require("gulp-uglify"),
	//gulp-minify-html
	htmlmin = require("gulp-html-minifier2"),
    htmlminOptions = {
        removeComments : true,//清除HTML注释
        collapseWhitespace : true,//压缩HTML
        removeScriptTypeAttributes : true,//删除<script>的type="text/javascript"
        removeStyleLinkTypeAttributes : true,//删除<style>和<link>的type="text/css"
        minifyJS : true,//压缩页面JS
        minifyCSS : true//压缩页面CSS
	},
	//imagemin
	imagemin = require('gulp-imagemin')
	;

var
	//工具类
	util = require("../util"),
	//配置检查类
	check = require("../check"),
	//snack.json
	snack,
	//基础路径
	basePath,
	//tasks
	mytasks = [],
	//less任务
	lessTasks = [],
	//css任务
	cssTasks = [],
	//js任务
	jsTasks = [],
	//html任务
	htmlTasks = [],
	//copy任务
	copyTasks = [],
	//imagemin任务
	imageminTasks = [],
	//watch任务
	watchTasks = [],
	//是否watch中
	watched = false,
	//添加到自定义任务
	addToMytasks = function(task){
		if(util.inArray(task,mytasks) == -1){
			mytasks.push(task);
		}
	}
	;

var
	//处理less
	buildLess = function(){
		var findex = 0, fObj, projectPath, paths, path, index;
		for(;findex < lessTasks.length;findex++){
			fObj = lessTasks[findex];
			projectPath = fObj.projectPath;
			paths = fObj.paths;
			for(index in paths){
				path = paths[index];
				var data = check.getLessPath(projectPath , path);
				//排除需要排除的路径
				var filters = data.filter.concat(check.reversePaths(data.exclude));
				//增加watch任务
				if(fObj.watch && !watched){
					if(typeof path.watch == "undefined" || path.watch){
						addToMytasks("watch");
						watchTasks.push({
							path : filters,
							event : "less"
						});
					}
				}
				//构造任务资源
				if(data.fileName != ""){
					for(var ta in data.target){
						var fileName = check.getFileName(data.target[ta],"css")  + ".css";
						var pipe = gulp.src(filters)
							.pipe(less())
							.pipe(concat(fileName))
							.pipe(gulp.dest(data.target[ta].replace(fileName,"")));
						if(path.minify){
							//压缩
							if(snack.onLine){
								//如果是生产用，则启用压缩
								pipe.pipe(minifyCss());
							}
							pipe.pipe(rename({suffix:".min"}))
								.pipe(gulp.dest(data.target[ta].replace(fileName,"")));
						}
					}
				}else{
					//没有指定输入文件名的，按输出文件名处理
					for(var ta in data.target){
						var pipe = gulp.src(filters)
							.pipe(less())
							.pipe(gulp.dest(data.target[ta]));
						if(path.minify){
							//压缩
							if(snack.onLine){
								//如果是生产用，则启用压缩
								pipe.pipe(minifyCss());
							}
							pipe.pipe(rename({suffix:".min"})).pipe(gulp.dest(data.target[ta]));
						}
					}
				}
			}
		}
	},
	//处理css
	buildCss = function(){
		var findex = 0, fObj, projectPath, paths, path, index;
		for(;findex < cssTasks.length;findex++){
			fObj = cssTasks[findex];
			projectPath = fObj.projectPath;
			paths = fObj.paths;
			for(index in paths){
				path = paths[index];
				var data = check.getCssPath(projectPath , path);
				//排除需要排除的路径
				var filters = data.filter.concat(check.reversePaths(data.exclude));
				//增加watch任务
				if(fObj.watch && !watched){
					if(typeof path.watch == "undefined" || path.watch){
						addToMytasks("watch");
						watchTasks.push({
							path : filters,
							event : "css"
						});
					}
				}
				//构造任务资源
				if(data.fileName != ""){
					for(var ta in data.target){
						var fileName = check.getFileName(data.target[ta],"css")  + ".css";
						var pipe = gulp.src(filters)
							.pipe(concat(fileName))
							.pipe(gulp.dest(data.target[ta].replace(fileName,"")));
						if(path.minify){
							//压缩
							if(snack.onLine){
								pipe.pipe(minifyCss());
							}
							pipe.pipe(rename({suffix:".min"})).pipe(gulp.dest(data.target[ta].replace(fileName,"")));
						}
					}
				}else{
					//没有指定输入文件名的，按输出文件名处理
					for(var ta in data.target){
						if(!data.isSameDir){
							//如果源和目标是不同目录，才需要输出源文件
                            var pipe = gulp.src(filters);
							pipe.pipe(gulp.dest(data.target[ta]));
						}
						if(path.minify){
							//压缩
                            var pipe = gulp.src(filters);
							if(snack.onLine){
								//如果是生产用，则启用压缩
								pipe.pipe(minifyCss());
							}
							pipe.pipe(rename({suffix:".min"})).pipe(gulp.dest(data.target[ta]));
						}
					}
				}
			}
		}
	},
	//处理js
	buildJs = function(){
		var findex = 0, fObj, projectPath, paths, path, index;
		for(;findex < jsTasks.length;findex++){
			fObj = jsTasks[findex];
			projectPath = fObj.projectPath;
			paths = fObj.paths;
			for(index in paths){
				path = paths[index];
				var data = check.getJsPath(projectPath , path);
				//排除需要排除的路径
				var filters = data.filter.concat(check.reversePaths(data.exclude));
				//增加watch任务
				if(fObj.watch && !watched){
					if(typeof path.watch == "undefined" || path.watch){
						addToMytasks("watch");
						watchTasks.push({
							path : filters,
							event : "js"
						});
					}
				}
				//构造任务资源
				if(data.fileName != ""){
					for(var ta in data.target){
						var fileName = check.getFileName(data.target[ta],"js")  + ".js";
						var pipe = gulp.src(filters)
							.pipe(concat(fileName))
							.pipe(gulp.dest(data.target[ta].replace(fileName,"")));
						if(path.minify){
							//压缩
							if(snack.onLine){
								//如果是生产用，则启用压缩
								pipe.pipe(uglify());
							}
							pipe.pipe(rename({suffix:".min"})).pipe(gulp.dest(data.target[ta].replace(fileName,"")));
						}
					}
				}else{
					//没有指定输入文件名的，按输出文件名处理
					for(var ta in data.target){
						if(!data.isSameDir){
							//如果源和目标是不同目录，才需要输出源文件
							var pipe = gulp.src(filters);
							pipe.pipe(gulp.dest(data.target[ta]));
						}
						if(path.minify){
							//压缩
                            var pipe = gulp.src(filters);
							if(snack.onLine){
								//如果是生产用，则启用压缩
								pipe.pipe(uglify());
							}
							pipe.pipe(rename({suffix:".min"})).pipe(gulp.dest(data.target[ta]));
						}
					}
				}
			}
		}
	},
	//处理html
	buildHtml = function(){
		var findex = 0, fObj, projectPath, paths, path, index;
		for(;findex < htmlTasks.length;findex++){
			fObj = htmlTasks[findex];
			projectPath = fObj.projectPath;
			paths = fObj.paths;
			for(index in paths){
				path = paths[index];
				var data = check.getHtmlPath(projectPath , path);
				//排除需要排除的路径
				var filters = data.filter.concat(check.reversePaths(data.exclude));
				//增加watch任务
				if(fObj.watch && !watched){
					if(typeof path.watch == "undefined" || path.watch){
						addToMytasks("watch");
						watchTasks.push({
							path : filters,
							event : "html"
						});
					}
				}
				//构造任务资源
				if(data.fileName != ""){
					for(var ta in data.target){
						var fileName = check.getFileName(data.target[ta],"html")  + ".html";
						var pipe = gulp.src(filters)
							.pipe(concat(fileName))
							.pipe(gulp.dest(data.target[ta].replace(fileName,"")));
						if(path.minify){
							//压缩
							if(snack.onLine){
								//如果是生产用，则启用压缩
								pipe.pipe(htmlmin(htmlminOptions));
							}
							pipe.pipe(rename({suffix:".min"})).pipe(gulp.dest(data.target[ta].replace(fileName,"")));
						}
					}
				}else{
					//没有指定输入文件名的，按输出文件名处理
					for(var ta in data.target){
						if(!data.isSameDir){
							//如果源和目标是不同目录，才需要输出源文件
                            var pipe = gulp.src(filters);
							pipe.pipe(gulp.dest(data.target[ta]));
						}
						if(path.minify){
							//压缩
                            var pipe = gulp.src(filters);
							if(snack.onLine){
								//如果是生产用，则启用压缩
								pipe.pipe(htmlmin(htmlminOptions));
							}
							pipe.pipe(rename({suffix:".min"})).pipe(gulp.dest(data.target[ta]));
						}
					}
				}
			}
		}
	},
	//处理copy
	buildCopy = function(){
		var findex = 0, fObj, projectPath, paths, path, index;
		for(;findex < copyTasks.length;findex++){
			fObj = copyTasks[findex];
			projectPath = fObj.projectPath;
			paths = fObj.paths;
			for(index in paths){
				path = paths[index];
				var data = check.getCopyPath(projectPath , path);
				//排除需要排除的路径
				var filters = data.filter.concat(check.reversePaths(data.exclude));
				//增加watch任务
				if(fObj.watch && !watched){
					if(typeof path.watch == "undefined" || path.watch){
						addToMytasks("watch");
						watchTasks.push({
							path : filters,
							event : "copy"
						});
					}
				}
				//构造任务资源
				//没有指定输入文件名的，按输出文件名处理
				for(var ta in data.target){
					gulp.src(filters).pipe(gulp.dest(data.target[ta]));
				}
			}
		}
	},
	//处理imagemin
	buildImagemin = function(){
		var findex = 0, fObj, projectPath, paths, path, index;
		for(;findex < imageminTasks.length;findex++){
			fObj = imageminTasks[findex];
			projectPath = fObj.projectPath;
			paths = fObj.paths;
			for(index in paths){
				path = paths[index];
				var data = check.getImageminPath(projectPath , path);
				//排除需要排除的路径
				var filters = data.filter.concat(check.reversePaths(data.exclude));
				//增加watch任务
				if(fObj.watch && !watched){
					if(typeof path.watch == "undefined" || path.watch){
						addToMytasks("watch");
						watchTasks.push({
							path : filters,
							event : "imagemin"
						});
					}
				}
				//构造任务资源
				//没有指定输入文件名的，按输出文件名处理
				for(var ta in data.target){
					gulp.src(filters).pipe(imagemin()).pipe(gulp.dest(data.target[ta]));
				}
			}
		}
	}

	;

/************************** 配置数据解析 start ***********************************/

//readProp
gulp.task("readProp",function(){
	//同步读取配置
	snack = fs.readFileSync(snackPath, "utf-8");
	snack = JSON.parse(snack);
	//基础路径
	basePath = check.getBasePath(snack);
});

//analysisProp
gulp.task("analysisProp",function(){
	var project, projectIndex, projectPath;
	//配置projects才能继续
	if(check.hasProjects(snack)){
		//遍历工程
		for(projectIndex in snack.projects){
			project = snack.projects[projectIndex];
			projectPath = basePath + check.getProjectBasePath(project);
			/******* 构造任务 *******/
			//less
			if(check.doLess(project)){
				addToMytasks("less");
				lessTasks.push({
					projectPath : projectPath,
					paths : project.less.paths,
					watch : project.less.watch
				});
			}
			//css
			if(check.doCss(project)){
				addToMytasks("css");
				cssTasks.push({
					projectPath : projectPath,
					paths : project.css.paths,
					watch : project.css.watch
				});
			}
			//js
			if(check.doJs(project)){
				addToMytasks("js");
				jsTasks.push({
					projectPath : projectPath,
					paths : project.js.paths,
					watch : project.js.watch
				});
			}
			//html
			if(check.doHtml(project)){
				addToMytasks("html");
				htmlTasks.push({
					projectPath : projectPath,
					paths : project.html.paths,
					watch : project.html.watch
				});
			}
			//copy
			if(check.doCopy(project)){
				addToMytasks("copy");
				copyTasks.push({
					projectPath : projectPath,
					paths : project.copy.paths,
					watch : project.copy.watch
				});
			}
			//imagemin
			if(check.doImagemin(project)){
				addToMytasks("imagemin");
				imageminTasks.push({
					projectPath : projectPath,
					paths : project.imagemin.paths,
					watch : project.imagemin.watch
				});
			}
		}
	}
});

/************************** 配置数据解析 end ***********************************/

/************************** 任务 start ***********************************/

//less任务
gulp.task("less",function(){
	buildLess();
});

//css任务
gulp.task("css",function(){
	buildCss();
});

//js任务
gulp.task("js",function(){
	buildJs();
});

//html任务
gulp.task("html",function(){
	buildHtml();
});

//imagemin任务
gulp.task("imagemin",function(){
	buildImagemin();
});

//copy任务
gulp.task("copy",function(){
	buildCopy();
});

//watch任务
gulp.task("watch",function(){
	var index = 0, task;
	var tp = function(event){
		return function(){
			gulp.run(event);
		}
	};
	for(;index < watchTasks.length;index++){
		task = watchTasks[index];
		gulp.watch(task.path,tp(task.event));
	}
	watched = true;
});

//默认任务
gulp.task("default", ["readProp", "analysisProp"], function(){
	var taskIndex = 0;
	for(;taskIndex < mytasks.length;taskIndex++){
		gulp.run(mytasks[taskIndex]);
	}
});

/************************** 任务 end ***********************************/