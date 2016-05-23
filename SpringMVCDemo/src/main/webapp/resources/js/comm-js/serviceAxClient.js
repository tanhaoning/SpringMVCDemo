/**
 * 请使用jquery-1.7.2.min.js及以上版本
 * servName,服务名 params.入参 options回调函数数组和timeout 请求超时时间设置，默认10秒
 * 参数要求:json对象格式{},不能字符串,或url入参  aa=c&bb=d参数
 * <p>
 * sample: var params={map:{"key":"param","keye":"parame"}}; var
 * $.callServiceAsJson("/cust/logn/login",params,{"done":function(response){
 *  //todo
 * });
 * options参数回调为
 * {
 * 	 before:null,// 执行前回调函数
 * 	 done:null,// 成功回调函数
 * 	 fail:null,//异常回调函数
 * 	 always:null,// 执行后回调函数
 *   timeout:10000默认10秒超时
 *   dataType:json　回返数据类型
 *   type:post 请求方法
 * }}
 * 格式如 {code:"编码",msg:"提示信息",result:"返回的结果数据",isjson:"请求是否以json返回"};
 * <P>
 * 有回调函数则为异步请求，若没有回调函数，则为同步，直接把结果返回
 * <p>
 * @author tang zheng yu
 */
(function ($) {
    var default_options = {
        dataType: "json",
        type: "POST",
        contentType: "application/json;charset=UTF-8",
        timeout: 10000// 默认10秒超时
    };
    var defalut_param_options = {
        before: null,// 执行前回调函数
        done: null,// 成功回调函数
        fail: null,//异常回调函数
        always: null,// 执行后回调函数
        trim: true,//默认对参数所有值去掉半角和全角空格
        cache: false,
        timeout: 180000// 请求超时时限
    };
    try {
        var token = getToken();
    } catch (e) {
    }
    if (!token)
        token = "";
    var headers = {
        "_al_ec_token": token
    };
    var _callService = function (servName, params, datatype, param_options) {
        if (!(!!servName)) {
            alert("请求路径必须填写！");
            return;
        }
        var paramOptions = $.extend({}, defalut_param_options, param_options || {});
        var asyncs = true; // 默认为异步请求
        if (!$.isFunction(paramOptions.done)) {
            asyncs = false; // 同步请求
        }
        var isForm = false;
        var isJsonObjet = false;
        var tempparams = params;
        var trimLeft = /^\s+/, trimRight = /\s+$/, trimCLeft = /(^　*)/g, trimCRight = /(　*$)/g;
        try {
            if (params && typeof params == "object") {
                if (paramOptions.trim) {
                    params = JSON.stringify(params, function (key, value) {
                        if (typeof value === "string") {
                            value = value.replace(trimLeft, "").replace(trimRight, "").replace(trimCLeft, "").replace(trimCRight, "");
                        }
                        return value;
                    });
                } else {
                    params = JSON.stringify(params);
                }

            }
            if (params && /^\{(.+:.+,*){1,}\}$/.test(params)) {
                isJsonObjet = true;
            }
        } catch (e) {
            isJsonObjet = false;
        }

        if (params) {
            if (paramOptions.type === "GET") {
                isForm = true;
            } else if (/^(.+=.+&?){1,}$/.test(params)) {
                isForm = true;
            }
        }
        if (isForm && isJsonObjet && tempparams) {
            if (paramOptions.trim) {
                params = JSON.parse(JSON.stringify(tempparams, function (key, value) {
                    if (typeof value === "string") {
                        value = value.replace(trimLeft, "").replace(trimRight, "").replace(trimCLeft, "").replace(trimCRight, "");
                    }
                    return value;
                }));
            } else {
                params = tempparams;
            }
            isForm = false;
        }
        var response = {code: "", errorsList: "", data: "", isjson: true};
        var reqparm = {};
        if (params) {
            reqparm = {
                url: servName,
                data: params,
                dataType: datatype,
                type: paramOptions.type,
                cache: paramOptions.cache,
                timeout: paramOptions.timeout,
                async: asyncs
            };

        } else {
            reqparm = {
                url: servName,
                dataType: datatype,
                type: paramOptions.type,
                cache: paramOptions.cache,
                timeout: paramOptions.timeout,
                async: asyncs
            };
        }
        //默认表请求类型
        if (isForm) {
            reqparm.contentType = "application/x-www-form-urlencoded;charset=UTF-8";
        }
        var req = $.extend({}, default_options, reqparm, {
            beforeSend: function (xhr) {
                $.each(headers, function (key, value) {
                    xhr.setRequestHeader(key, value);
                });
                if ($.isFunction(paramOptions.before)) {
                    paramOptions.before.apply(this);
                }
            }
        });
        $.ajax(req).done(function (data, status, xhr) {
            //获取sessionCode
            //判断是否为空，如果不为空则当前工号被强制下线，有其他人正在登录使用
            var sessionCode = xhr.getResponseHeader("sessionCode");
            if (sessionCode != null) {
                login.windowpub.alertLoginWindow("*当前工号已经在其它地方登录，请重新登录");
                return;
            }
            if (!!data && ( typeof data.data != "undefined" || typeof data.code != "undefined")) {
                response.code = data.code;
                response.data = data.data;
                response.errorsList = data.errorsList;
                response.isjson = true;
                //未登录
                if (response.code && ( response.code == 1101 || response.code == 1100)) {
//						 			alert("用户未登录或会话过期，请重新登录！");
//						 			CommonUtils.getRootWin().location.href=data.data;
                    login.windowpub.alertLoginWindow();
                    return;
                }
                if (response.code && ( response.code == 1103 || response.code == 1104)) {
                    alert("无权访问页面！");
                    return;
                }

                //非jsonresponse返回,直接返回html或text
            } else {
                try {
                    if (typeof data == "string" && /^\{(.+:.+,*){1,}\}$/.test(data)) {
                        var tempdata = eval('(' + data + ')');

                        if (tempdata && typeof tempdata.data != "undefined" && typeof tempdata.code != "undefined") {
                            //异常信息
                            if (tempdata.code == -2) {
                                $.alertM(tempdata.data);
                                $.unecOverlay();
                                response.code = -2;
                                response.data = data;
                                response.isjson = false;
                                return;
                                //未登录
                            } else if (tempdata.code == 1101 || tempdata.code == 1100) {
                                //alert("用户未登录或会话过期，请重新登录！");
                                login.windowpub.alertLoginWindow();
                                //CommonUtils.getRootWin().location.href=tempdata.data;
                                return;
                            }
                        }
                    }
                } catch (e) {
                    alert(e.name + ": " + e.message);
                }
                response.code = xhr.getResponseHeader("respCode");
                response.errorsList = decodeURIComponent(xhr.getResponseHeader("respMsg"));
                if (typeof response.code == "undefined" || !/^[-]?\d+$/.test(response.code)) {
                    response.code = 0;
                }
                //未登录
                if (response.code == 1101 || response.code == 1100) {
//						 			alert("用户未登录或会话过期，请重新登录！");
//						 			CommonUtils.getRootWin().location.href=response.errorsList;
                    login.windowpub.alertLoginWindow();
                    return;
                }
                if (response.code == 1103 || response.code == 1104) {
                    alert("无权访问页面！");
                }
                response.data = data;
                response.isjson = false;
            }

            if (asyncs) {
                paramOptions.done.apply(this, [response]);
            }
            //异常,非200返回处理
        }).fail(function (xhr, textStatus) {
            //获取sessionCode
            //判断是否为空，如果不为空则当前工号被强制下线，有其他人正在登录使用
            var sessionCode = xhr.getResponseHeader("sessionCode");
            if (sessionCode != null) {
                login.windowpub.alertLoginWindow("*当前工号已经在其它地方登录，请重新登录");
                return;
            }
            var respCode = xhr.getResponseHeader("respCode");
            var respMsg = decodeURIComponent(xhr.getResponseHeader("respMsg"));
            if (!!respCode && typeof respCode != "undefined") {
                //未登录
                if (respCode == 1101 || respCode == 1100) {
//						 			alert("用户未登录或会话过期，请重新登录！");
//						 			CommonUtils.getRootWin().location.href=respMsg;
                    login.windowpub.alertLoginWindow();
                    return;
                }
                if (response.code == 1103 || response.code == 1104) {
                    alert("无权访问页面！");
                    return;
                }
            }
            if (xhr.status == 0) {//未初始化
                return;
            } else if (xhr.status == 404) {
                response.code = 1;
                response.errorsList = [{code: xhr.status, msg: '请求路径不存在.'}];
            } else if (xhr.status == 500) {
                response.code = 1;
                //BUG FIX please don't show the responseText directly, it's ugly
//								  response.errorsList=[{code:xhr.status,msg: xhr.responseText}];
                response.errorsList = [{code: xhr.status, msg: '系统异常,请刷新页面重新操作或退出重登录!'}];
            } else if (xhr.status == 403) {
                response.code = 1;
                response.errorsList = [{code: xhr.status, msg: '请刷新页面重新操作或退出重登录!'}];
            } else if (xhr.status == 400) {
                response.code = 1;
                response.errorsList = [{code: xhr.status, msg: '请求数据格式错误!'}];
            } else if (xhr.status == 12029) {
                response.code = 1;
                response.errorsList = [{code: xhr.status, msg: '网络问题,请检查网络是否连接正常!'}];
            } else {
                response.code = 1;
                response.errorsList = [{code: xhr.status, msg: '网络问题,请稍后重试!'}];
            }
            if (asyncs) {
                if ($.isFunction(paramOptions.fail)) {
                    paramOptions.fail.apply(this, [response]);
                } else {
                    $.Zebra_Dialog('<strong>' + response.errorsList[0].msg + '(' + response.errorsList[0].code + ')' + '</strong>',
                        {
                            'type': 'error',
                            'title': '失败'
                        });
                }
            }
        }).always(function () {
            if ($.isFunction(paramOptions.always) && asyncs) {
                paramOptions.always.apply(this);
            }
        });
        // 同步请求
        if (!asyncs) {
            return response;
        }
    };
    $.extend($, {
        callServiceAsJson: function (servName, params, options) {
            //判断第二个参数是否为param_options，若是，则无参数提交
            if (params && params.done && !options) {
                options = params;
                params = null;
            }
            return _callService(servName, params, "json", options);
        },
        callServiceAsJsonGet: function (servName, params, options) {
            if (params && params.done && !options) {
                options = params;
                params = null;
            }
            return _callService(servName, params, "json", $.extend(options || {}, {type: "GET"}));
        },
        callServiceAsXml: function (servName, params, options) {
            if (params && params.done && !options) {
                options = params;
                params = null;
            }
            return _callService(servName, params, "xml", options);
        },
        callServiceAsHtml: function (servName, params, options) {
            if (params && params.done && !options) {
                options = params;
                params = null;
            }
            return _callService(servName, params, "html", options);
        },
        callServiceAsHtmlGet: function (servName, params, options) {
            //判断第二个参数是否为param_options，若是，则无参数提交
            if (params && params.done && !options) {
                options = params;
                params = null;
            }
            return _callService(servName, params, "html", $.extend(options || {}, {type: "GET"}));
        },
        callServiceAsText: function (servName, params, options) {
            if (params && params.done && !options) {
                options = params;
                params = null;
            }
            return _callService(servName, params, "text", options);
        },
        callServiceAsTextGet: function (servName, params, options) {
            if (params && params.done && !options) {
                options = params;
                params = null;
            }
            return _callService(servName, params, "text", $.extend(options || {}, {type: "GET"}));
        }
    });
})(jQuery, this);
