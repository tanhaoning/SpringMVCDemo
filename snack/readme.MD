/**
 * snack构建平台使用说明
 */
1、安装node
2、选择grunt或者gulp
3、进入对应的路径
4、执行npm install安装插件

/**
 * snack配置说明
 */
{
    /*** 要构建的工程，基于本工程的相对路径，如果不配置，会默认成snack的同级目录 ***/
    //注意，这里的相对路径，是相对与构建脚本所在路径，不是snack.json的所在路径
	"basePath" : "../../" ,
	//生产环境标识，主要差别在于带min的文件处理，true时真正做压缩供生产使用，false时相当于只做了合并
	"onLine" : true,
	/*** 要处理的工程，必须是一个数组，数组元素必须是一个对象 ***/
	"projects" : [
		{
		    //工程名
		    "name" : "vita" ,
		    //需要处理的文件的基础路径，寻径将为：basePath + name + base
		    "base" : "src/main/java/vita" ,
		    //需要处理的less任务，如果不需要则无需这个节点
		    "less" : {
		    	//是否监听，这里设置false，path里的watch设置将无效
		    	"watch" : true,
		        //less文件路径集
				"paths" : [
				    //paths里的元素可以有两种，A对象，B字符串
				    //A：对象
					{
					    //资源路径，必须有，扩展名为less。单个可以使用字符串，多个源输出，可以赋值为数组，如["path1","path2"]
						"source" : "vita/themes/base",
						//指定文件，扩展名为less。单个可以使用字符串，多个文件使用数组，如果不需要则无需这个节点
						"filter" : "theme.core",
						//排除文件，扩展名为less，单个可以使用字符串，多个文件使用数组，如果不需要则无需这个节点
						"exclude" : "",
						//生成文件的路径，如果需要同时输出到多个目录可以使用数组，单目录可以使用字符串。注意：是生成的所有文件同时在所有目录里保存；如果没有该节点，默认同source
						"target" : ["vita/app/theme/base"],
						//指定文件名，扩展名为css，如果设置了这个属性，则所有less都会合并且生成指定文件名的css文件，如果不指定，则不合并less文件，css文件名为对应less的文件名
						"fileName" : "core",
						//是否压缩，压缩的是合并后的文件，文件名加.min
                        "minify" : true,
                        //上层watch设置为true时，该设置有效，默认为true
                        "watch" : true,
					},
					//B：字符串，如果source和target都在同一个路径，且没有上述的复杂条件，可以直接用字符串标识
					"vita/themes/base"
				]
		    } ,
		    //需要处理的css任务，如果不需要则无需这个节点
		    "css" : {
		    	//是否监听，这里设置false，path里的watch设置将无效
			  	"watch" : true,
		    	//css文件路径集
		    	"paths" : [
		    		//paths里的元素必须是对象
					{
					    //资源路径，必须有，扩展名为css，单个可以使用字符串，多个源输出，可以赋值为数组，如["path1","path2"]
						"source" : "vita/themes/base",
						//指定文件，扩展名为css，单个可以使用字符串，多个文件可以使用数组，如果不需要则无需这个节点
						"filter" : "theme.core",
						//排除文件，扩展名为css，单个可以使用字符串，多个文件可以使用数组，如果不需要则无需这个节点
						"exclude" : "",
						//生成文件的路径，如果需要同时输出到多个目录可以使用数组，单个可以使用字符串，注意：是生成的所有文件同时在所有目录里保存；如果没有该节点，默认同source
						"target" : ["vita/app/theme/base"],
						//文件名，如果指定文件名，则将source里指定的文件合并并生成文件；否则按源文件复制；扩展名为css。
						"fileName" : "core"
						//是否压缩，压缩的是合并后的文件，文件名加.min
                        "minify" : true,
                        //上层watch设置为true时，该设置有效，默认为true
                        "watch" : true,
					}
		    	]
		    } ,
		    "js" : {
		    	//是否监听，这里设置false，path里的watch设置将无效
			  	"watch" : true,
		    	//js文件路径集
		    	"paths" : [
		    		//paths里的元素必须是对象
					{
					    //资源路径，必须有，扩展名为js，单个可以使用字符串，多个源输出，可以赋值为数组，如["path1","path2"]
						"source" : "vita/themes/base",
						//指定文件，扩展名为js，单个可以使用字符串，多个文件可以使用数组，如果不需要则无需这个节点
						"filter" : "theme.core",
						//排除文件，扩展名为js，单个可以使用字符串，多个文件可以使用数组，如果不需要则无需这个节点
						"exclude" : "",
						//生成文件的路径，如果需要同时输出到多个目录可以使用数组，单个可以使用字符串，注意：是生成的所有文件同时在所有目录里保存；如果没有该节点，默认同source
						"target" : ["vita/app/theme/base"],
						//文件名，如果指定文件名，则将source里指定的文件合并并生成文件；否则按源文件复制；扩展名为js。
						"fileName" : "core",
						//是否压缩，压缩的是合并后的文件，文件名加.min
						"minify" : true,
						//上层watch设置为true时，该设置有效，默认为true
						"watch" : true,
					}
		    	]
		    } ,
		    "html" : {
		    	"watch" : true,
		    	"path" : [
		    		{
                    	"source" : "vita/themes/base",
                    	"filter" : "theme.core",
                    	"exclude" : "",
                    	"target" : ["vita/app/theme/base"],
                    	"fileName" : "core",
                    	"minify" : true,
                    	"watch" : true,
		    		}
		    	]
		    },
		    "copy" : {
		    	//是否监听，这里设置false，path里的watch设置将无效
		    	"watch" : true,
		    	//需要copy的文件路径集合
		    	"paths" : [
		    		//paths里的元素必须是对象
					{
					    //资源路径，必须有，单个可以使用字符串，如果多个源同时输出，可以赋值为数组，如["path1","path2"]
						"source" : "vita/themes/base",
						//指定文件，多个文件可以使用数组，单个可以直接使用字符串，如果不需要则无需这个节点
						"filter" : "theme.core",
						//排除的文件，多个文件可以使用数组，单个可以直接使用字符串，如果不需要则无需这个节点
						"exclude" : "",
						//生成文件的保存路径，如果需要同时输出到多个目录可以使用数组，单个可以使用字符串
						//注意：所有文件将同时在所有目录里保存；如果没有该节点，默认同source
						"target" : ["vita/app/theme/base"],
						//上层watch设置为true时，该设置有效，默认为true
						"watch" : true,
					}
		    	]
		    },
		  	"imagemin" : {
		    	//是否监听，这里设置false，path里的watch设置将无效
		    	"watch" : true,
		    	//需要imagemin的文件路径集合
		    	"paths" : [
		    		//paths里的元素必须是对象
					{
					    //资源路径，必须有，单个可以使用字符串，如果多个源同时输出，可以赋值为数组，如["path1","path2"]
						"source" : "vita/themes/base",
						//指定文件，多个文件可以使用数组，单个可以直接使用字符串，如果不需要则无需这个节点
						"filter" : "theme.core",
						//排除的文件，多个文件可以使用数组，单个可以直接使用字符串，如果不需要则无需这个节点
						"exclude" : "",
						//生成文件的保存路径，如果需要同时输出到多个目录可以使用数组，单个可以使用字符串
						//注意：所有文件将同时在所有目录里保存；如果没有该节点，默认同source
						"target" : ["vita/app/theme/base"],
						//上层watch设置为true时，该设置有效，默认为true
						"watch" : true,
					}
		    	]
			}
		}
	]
}