// JavaScript Document
/*-----------------------------------------------------------------------------------------------------
 Map object (just like java.util.HashMap)
 -----------------------------------------------------------------------------------------------------*/
function HashMap() {
    var mapArray = new Array();
    this.put = function (key, val) {
        var temp = new Array(key, val);
        mapArray.push(temp);
    };
    this.get = function (key) {
        for (var i = 0; i < mapArray.length; i++) {
            if (key == mapArray[i][0]) {
                return mapArray[i][1];
            }
        }
    };
    this.getArray = function () {
        return mapArray;
    };
}

/*-----------------------------------------------------------------------------------------------------
 ajax object
 -----------------------------------------------------------------------------------------------------*/
function Ajax() {
    //init
    var requestType = "text";
    var serverPageUrl = null;
    var submitMethod = "POST";
    var val = null;

    //set return context format : xml,text
    this.setRequestType = function (type) {
        requestType = type.toLowerCase();
    };

    //set the url path
    this.setUrl = function (url) {
        serverPageUrl = url;
    };

    //set submit mothod
    this.setSubmitMethod = function (method) {
        submitMethod = method.toUpperCase();
    };

    //set the params
    this.setVal = function (valArg) {
        val = valArg;
    };

    //decode
    this.decode = function (src) {
        var map = new HashMap();
        if (src != "") {
            var srcAry = src.split(",");
            for (var i = 0; i < srcAry.length; i++) {
                var line = srcAry[i].split(":");
                map.put(line[0], line[1]);
            }
        }
        return map;
    };

    //encode
    this.encode = function (map) {
        var temp = "";
        for (var i = 0; i < map.length; i++) {
            temp += "&" + map[i][0] + "=" + map[i][1];
        }
        return temp.substring(1, temp.length);
    };

    //crate a XMLHttpRequest object
    function creatXMLHttpRequestObj() {
        var xmlObject = null;
        try {
            xmlObject = new ActiveXObject("Msxml2.XMLHTTP");
        } catch (e) {
            try {
                xmlObject = new ActiveXObject("Microsoft.XMLHTTP");
            } catch (oc) {
                xmlObject = null;
            }
        }
        if (!xmlObject && typeof XMLHttpRequest != "undefined") {
            xmlObject = new XMLHttpRequest();
        }
        return xmlObject;
    }

    var xmlhttp = creatXMLHttpRequestObj();

    this.getRequest = function () {
        if (val == null)
            val = "";
        var request = null;
        if (submitMethod == "GET") {
            xmlhttp.open(submitMethod, serverPageUrl + "?" + val, false);
        } else if (submitMethod == "POST") {
            xmlhttp.open(submitMethod, serverPageUrl, false);
            xmlhttp.setRequestHeader("content-length", val.length);
        }
        xmlhttp.setRequestHeader("content-type", "application/x-www-form-urlencoded");
        xmlhttp.onreadystatechange = function () {
            switch (xmlhttp.readyState) {
                case 0:
                    request = "对象已建立，但是尚未初始化（尚未调用open方法）。";
                    break;
                case 1:
                    request = "对象已建立，尚未调用send方法。";
                    break;
                case 2:
                    request = "send方法已调用，但是当前的状态及http头未知。";
                    break;
                case 3:
                    request = "已接收部分数据，因为响应及http头不全，这时通过responseBody和responseText获取部分数据会出现错误，";
                    break;
                case 4:
                    if (xmlhttp.status == 200) {
                        if (requestType == "xml") {
                            request = xmlhttp.responseXml;
                        } else if (requestType == "text") {
                            request = xmlhttp.responseText;
                        }
                    } else {
                        request = "错误：错误代码：" + xmlhttp.status;
                    }
                    break;
                default :
                    request = "请等待，正在向服务器提交数据！";
                    break;
            }
        };

        if (submitMethod == "GET") {
            xmlhttp.send(null);
        } else if (submitMethod == "POST") {
            xmlhttp.send(val);
        }
        return request;
    };
}

var ajax = new Ajax();