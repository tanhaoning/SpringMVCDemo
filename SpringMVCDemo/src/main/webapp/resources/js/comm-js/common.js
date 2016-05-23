/**
 * 前台工具类
 *
 * @class CommonUtils
 * @static
 * @author luoxh
 * @modefiy tangzhengyu
 */
CommonUtils = {
    /**
     * 注册命名空间
     * @param 参数1为包路径，可以多级: order.cust
     * @param 参数2为类名，只有一级，sample:cust
     * @example 合法例子如下
     *          CommonUtils.regNamespace("order","cust");
     *        CommonUtils.regNamespace("crm.order","cust");
     * @return 名称空间对象
     */
    regNamespace: function () {
        var args = arguments, o = null, nameSpaces;
        o = window;
        for (var i = 0; i < args.length; i = i + 1) {
            nameSpaces = args[i].split(".");
            for (var j = 0; j < nameSpaces.length; j = j + 1) {
                o[nameSpaces[j]] = o[nameSpaces[j]] || {};
                o = o[nameSpaces[j]];
                if (i == 1) {
                    break;
                }
            }
        }
        return o;
    },
    /**
     * json {} 序列化转为 a=b&c=d
     * 建议用jquery param方法
     */
    serializeJson: function (obj) {
        if (!obj) {
            return "";
        }
        try {
            var key = "", arrayObj = [];
            for (key in obj) {
                arrayObj.push(key + "=" + obj[key]);
            }
        } catch (e) {
            return "";
        }
        return arrayObj.join("&");
    },
    /**
     * 获取最顶级窗口window
     * @returns
     */
    getRootWin: function () {
        var win = window;
        while (win != win.parent) {
            win = win.parent;
        }
        return win;
    },
    /**
     * 判断当前窗口不是顶级窗口,自动将当前窗口换成顶级窗口
     */
    autoTopWinOnload: function () {
        if (window != window.top) {
            this.getRootWin().top.location.href = window.location.href;
        }
    },
    /** arg1除以arg2的精确结果*/
    mathDiv: function (arg1, arg2) {
        var t1 = 0, t2 = 0, r1, r2;
        try {
            t1 = arg1.toString().split(".")[1].length;
        } catch (e) {
        }
        try {
            t2 = arg2.toString().split(".")[1].length;
        } catch (e) {
        }
        with (Math) {
            r1 = Number(arg1.toString().replace(".", ""));
            r2 = Number(arg2.toString().replace(".", ""));
            return (r1 / r2) * pow(10, t2 - t1);
        }
    },
    /** arg1乘以arg2的精确结果 */
    mathMul: function (arg1, arg2) {
        var m = 0, s1 = arg1.toString(), s2 = arg2.toString();
        try {
            m += s1.split(".")[1].length;
        } catch (e) {
        }
        try {
            m += s2.split(".")[1].length;
        } catch (e) {
        }
        return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m);
    },
    /** arg1加上arg2的精确结果 */
    mathAdd: function (arg1, arg2) {
        var r1, r2, m;
        try {
            r1 = arg1.toString().split(".")[1].length;
        } catch (e) {
            r1 = 0;
        }
        try {
            r2 = arg2.toString().split(".")[1].length;
        } catch (e) {
            r2 = 0;
        }
        m = Math.pow(10, Math.max(r1, r2));
        return (arg1 * m + arg2 * m) / m;
    },
    /** 减法函数，用来得到精确的减法结果 */
    mathSub: function (arg1, arg2) {
        var r1, r2, m, n;
        try {
            r1 = arg1.toString().split(".")[1].length;
        } catch (e) {
            r1 = 0;
        }
        try {
            r2 = arg2.toString().split(".")[1].length;
        } catch (e) {
            r2 = 0;
        }
        m = Math.pow(10, Math.max(r1, r2));
        //动态控制精度长度
        n = (r1 >= r2) ? r1 : r2;
        return ((arg1 * m - arg2 * m) / m).toFixed(n);
    },
    /**
     * 替换字符串
     * @param param 原字符串
     * @param s1 被替换的字符
     * @param s2 替换的字符
     * @returns 返回替换后字符串
     */
    replaceAll: function (param, s1, s2) {
        return param.replace(new RegExp(s1, "gm"), s2);
    }
};
