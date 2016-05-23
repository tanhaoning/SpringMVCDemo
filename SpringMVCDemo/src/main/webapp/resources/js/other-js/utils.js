//是否是IE判断
var agt = navigator.userAgent.toLowerCase();
var bOpera = (agt.indexOf("opera") != -1);
var bIE = !bOpera && (agt.indexOf("msie") > -1);

//判断浏览器版本
function validateBrowser() {
    var allowflag = false;
    var version = $.browser.version;
    if ($.browser.msie) {
        if (version >= 7)
            allowflag = true;
    } else if ($.browser.mozilla) {
        if (version >= 12)
            allowflag = true;
    } else if ($.browser.safari) {
        var agent = navigator.userAgent.toLowerCase();
        var regStr_chrome = /(?:chrome|crios|crmo)\/([0-9.]+)/gi;
        //var regStr_chrome = /chrome\/[\d.]+/gi ;
        //var regStr_saf = /safari\/[\d.]+/gi ;
        var chromeVer = agent.match(regStr_chrome);
        if (chromeVer) {
            var verArr = chromeVer[0].split("/");
            var chrome_version = verArr[1].split(".");
            if (chrome_version[0] >= 21) {
                allowflag = true;
            }
        }
    }
    return allowflag;
}
//Check if the string is empty
//判断是否为空
function isEmpty(value) {
    var bEmpty = false;

    if (trimString(value) == "")
        bEmpty = true;

    return bEmpty;
}

//去掉字符前后的空格
function trimString(str) {
    // str = this != window? this : str;
    return str.replace(/^\s+/g, '').replace(/\s+$/g, '');
}
//去掉字符前后的空格
function trimString22(str) {
    // str = this != window? this : str;
    return str.replace(/^\s+/g, '').replace(/\s+$/g, '');
}

//替换字符串中的某个字符串
function replaceString(str, oldStr, newStr) {
    var sRetVal = "";
    var index = str.indexOf(oldStr);

    while (index != -1) {
        sRetVal = sRetVal + str.substring(0, index) + newStr;
        str = str.substring(index + oldStr.length);
        index = str.indexOf(oldStr);
    }

    sRetVal = sRetVal + str;

    return sRetVal;
}

// Convert int to a fix length string
// Parameters:	vInt		-- The int value
//				vLen		-- The length of result string
//				vFillChar	-- The char that will be filled into string
// 例:intToStr(12,5,"0") = 00012
function intToStr(vInt, vLen, vFillChar) {
    var vRetVal = "" + vInt;

    if (vLen > 0) {
        while (vRetVal.length < vLen) {
            vRetVal = vFillChar + vRetVal;
        }
    }

    return vRetVal;
}

// Convert string to an int
function strToInt(vStr) {
    if (vStr == "")
        return 0;
    else
        return parseInt(vStr, 10);
}

// Convert string to a float
function strToFloat(vStr) {
    if (vStr == "")
        return 0;
    else
        return parseFloat(vStr);
}

//字符串转换为数组
function strToArray(vStr, vDelimiter) {
    var aRet = new Array();
    var nPos = vStr.indexOf(vDelimiter);

    while (nPos != -1) {
        if (nPos != 0)
            aRet[aRet.length] = vStr.substring(0, nPos);
        else
            aRet[aRet.length] = "";

        if (vStr.length > nPos + 1) {
            vStr = vStr.substring(nPos + 1);
            nPos = vStr.indexOf(vDelimiter);
        }
        else {
            vStr = "";
            nPos = -1;
        }

    }

    if (vStr.length > 0)
        aRet[aRet.length] = vStr;

    return aRet;
}

//是否是数字，包括整数 小数 负数
function isValidNumber(s) {
    var i;
    var hasDecimalPoint = false;

    if (isEmpty(s) || s == "." || s == "-") {
        return false;
    }

    for (i = 0; i < s.length; i++) {
        var c = s.charAt(i);

        if (!isDigit(c) && !(c == "." && !hasDecimalPoint) && !(i == 0 && c == "-"))
            return false;

        if (c == ".")
            hasDecimalPoint = true;
    }

    return true;
}

//是否是整数
function isValidInteger(s) {
    var i;

    if (isEmpty(s) || s.indexOf(".") != -1 || s == "-") {
        return false;
    }

    for (i = 0; i < s.length; i++) {
        var c = s.charAt(i);

        if (!isDigit(c) && !(i == 0 && c == "-"))
            return false;

    }

    return true;
}

// Validate if the given parameter is positive number
// Param: c		the input parameter to be evaluated
// 是否正数
function isPositiveNumber(c) {
    if (isEmpty(c)) {
        return true;
    }
    else {
        var num = parseFloat(c);

        if (num >= 0)
            return true;
        else
            return false;
    }
}

// Validate if the given parameter is a positive integer
// Param: s		the input parameter to be evaluated
// 是否正整数数
function isPositiveInteger(s) {
    var i;

    if (!isPositiveNumber(s)) {
        return false;
    }

    for (i = 0; i < s.length; i++) {
        var c = s.charAt(i);

        if (!isDigit(c))
            return false;
    }

    return true;
}

//Validate if input is valid or not. Valid input has 0-9 only.
//判断输入的是否只是数字
function validateDigitFormat(s) {
    if (isEmpty(s)) {
        return false;
    }

    var i;
    for (i = 0; i < s.length; i++) {
        var c = s.charAt(i);

        if (!isDigit(c))
            return false;
    }
    return true;
}


// Returns true if character c is an English letter (A .. Z, a..z).
function isLetter(c) {
    return ( ((c >= "a") && (c <= "z")) || ((c >= "A") && (c <= "Z")) );
}

// Returns true if character c is a digit between 0 to 9.
function isDigit(c) {
    return ((c >= "0") && (c <= "9"));
}


//全选所有的复选框
//obj FORM对象
function selectAllCheck(obj) {
    var formName = obj;
    for (var i = 0; i < formName.length; i++) {
        if (formName.item(i).type == "checkbox") {
            formName.item(i).checked = "checked";
        }
    }
}

//打开一个模态窗口
//例如 doModal("/app/aa.htm","mywin",100,200,300,400)
// popup window
var newWindow = null;
var bOpening = false;
function doModal(url, MyWindow, mwidth, mheight, mLeft, mTop) {
    if (!bOpening && ((newWindow == null) || newWindow.closed)) {
        bOpening = true;
        if (window.showModelessDialog) {
            newWindow = window.showModelessDialog(url, MyWindow, "help:0;resizable:0;status:0;scroll:1;dialogWidth:" + mwidth + "px;dialogHeight:" + mheight + "px;dialogLeft:" + mLeft + "px;dialogTop:" + mTop + "px");
        } else {
            if (mLeft == null)
                mLeft = 120;
            if (mTop == null)
                mTop = 80;
            newWindow = window.open(url, "", "width=" + mwidth + "px,height=" + mheight + "px,resizable=0,scrollbars=1,statusbar=0,menubar=0,left=" + mLeft + "px,top=" + mTop + "px");
        }

        newWindow.name = "NewWindow";
        window.setTimeout("bOpening = false;", 1000, "JAVASCRIPT");
        return newWindow;
    }
}

//Validate if ID is valid or not. Valid Id has A-Z, a-z, and 0-9 only.
//判断是否为正确的ID,正确的ID可包括  A-Z, a-z, 和0-9
function validateIdFormat(s) {
    var i;
    for (i = 0; i < s.length; i++) {
        var c = s.charAt(i);

        if (!isLetter(c) && !isDigit(c))
            return false;
    }
    return true;
}


//截取某个字符串的某个长度
function textLength(s, maxlimit) {
    var result = true;
    if (s.value.length > maxlimit) {
        s.value = s.value.substring(0, maxlimit);
        result = false;
    }

    if (window.event)
        window.event.returnValue = result;

    return result;
}

//是否是合法的Email
function isValidEmail(str) {
    // are regular expressions supported?
    var supported = 0;
    if (window.RegExp) {
        var tempStr = "a";
        var tempReg = new RegExp(tempStr);
        if (tempReg.test(tempStr))
            supported = 1;
    }
    if (!supported)
        return (str.indexOf(".") > 2) && (str.indexOf("@") > 0);

    var r1 = new RegExp("(@.*@)|(\\.\\.)|(@\\.)|(^\\.)");
    //var r2 = new RegExp("^.+\\@(\\[?)[a-zA-Z0-9\\-\\.]+\\.([a-zA-Z]{2,3}|[0-9]{1,3})(\\]?)$");
    var r3 = new RegExp("^.+\\@(\\[?)[a-zA-Z0-9\\-\\.]+\\.([a-zA-Z]{2,20}|[0-9]{1,3})(\\]?)$"); // for aaa@bbb.museum formats

    //return (!r1.test.js(str) && r2.test.js(str));
    return (!r1.test(str) && r3.test(str));
}

//是否是合法的多个以;或,隔开的Email
function isValidMultiEmails(str) {
    var aEmail = new Array();
    var bValidEmail = true;
    var sEmail = replaceString((str), ";", ",");
    aEmail = sEmail.split(",");
    for (var i = 0; i < aEmail.length; i++) {
        if (!isEmpty(aEmail[i])) {
            if (!isValidEmail(aEmail[i])) {
                bValidEmail = false;
                break;
            }
        }
    }
    return bValidEmail;
}

//判断是否是合法的日前字符串
function validateDate(vTime) {
    var dtTime = new Date(vTime);
    if ((((vTime.indexOf(',') == -1) && (vTime.length != 12 || vTime.length != 11)) || (dtTime == "NaN")) && (!isEmpty(vTime))) {
        return false;
    }

    return true;
}

// Use this function instead of "location.reload()"
//刷新本页面
function reloadPage() {
    window.top.Working.location.href = window.top.Working.location;
}

//获取元素对象
//function $(d){return document.getElementById(d);}

//获取元素样式
function f(d) {
    var t = $(d);
    if (t) {
        return t.style;
    } else {
        return null;
    }
}

//隐藏所有ＵＬ中的所有ＬＩ
function Hi() {
    var items = document.getElementsByTagName("ul");
    for (var i = 0; i < items.length; i++) {
        items[i].style.display = 'none';
    }
}

//设置所有Ｈ2的fontweight为normal
function Hl() {
    var iteml = document.getElementsByTagName("h2");
    for (var j = 0; j < iteml.length; j++) {
        iteml[j].style.fontWeight = 'normal';
    }
}

//判断对象是否隐藏
function h(d) {
    var s = f(d);
    var b = s.display;
    if (b == 'none') {
        return true;
    } else {
        return false;
    }
}

//先判断对象是否隐藏，如果隐藏，则让它显示出来，并把fontWeith设为normal，如果是显示的，则隐藏，并把fontWeith设为normal
function ShHi(ii, jj) {
    if (h(jj)) {
        f(jj).display = 'block';
        f(ii).fontWeight = 'normal';
    } else {
        f(jj).display = 'none';
        f(ii).fontWeight = 'normal';
    }
}


function getPostfix(busiType) {
    var postfic = "";
    if (busiType == "AD") {
        postfic = "@163.gd";
    } else if (busiType == "AD-IPTV") {
        postfic = "@iptv.gd";
    } else if (busiType == "AD-GREEN") {
        postfic = "@green";
    } else if (busiType == "AD-FREE") {
        postfic = "@free.gd";
    } else if (busiType == "AD-WLAN") {
        postfic = "@wlan.gd";
    } else if (busiType == "ZD") {// 窄带无后缀
        postfic = "";
    } else if (busiType == "AD-LAN") {// lan
        postfic = "163.gd";
    } else if (busiType == "CARD") {// 上网卡
        postfic = "@card";
    } else if (busiType == "DH" || busiType == "PHS" || busiType == "SHT" || busiType == "CDMA") {
        postfic = "";
    } else {
        postfic = "@163.gd";
    }
    return postfic;
}

/**
 是否是合法的手机
 */
function isValidsPhone(s) {
    if (!isPositiveInteger(s)) {
        return false;
    }
    if (s.length != 11) {
        return false;
    }
    var pm = s.substring(0, 3);
    if (pm != "189" && pm != "133" && pm != "180" && pm != "153" && pm != "188" && pm != "181" && pm != "177" && pm != "170" && pm != "171" && pm != "134" && pm != "135" && pm != "136" && pm != "137" && pm != "138" && pm != "139" && pm != "158" && pm != "159") {//
        return false;
    }
    return true;
}

/**
 * 是否有效的电信手机号码
 */
function isValidCDMA(num) {
    var reg = /^1(33|53|80|81|89|70|77)\d{8}$/;
    if (reg.test(num)) {
        return true;
    } else {
        return false;
    }
}