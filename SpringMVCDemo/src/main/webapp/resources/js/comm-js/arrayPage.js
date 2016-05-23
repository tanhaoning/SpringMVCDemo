CommonUtils.regNamespace("common", "arraypage");

/**
 * 前台静态分页组件
 *
 * @requre jquery.js
 * @requre jquery.jevent.js
 * @auth tanzp
 * @desc 防淘宝分页,用于对array对象进行分页
 */
common.arraypage = (function () {

    /**
     * pageSize : 10
     */
    var page = {};
    var defaultPageSize = 10;
    var defaultListenerName = "pageChangeListener";
    var objs = {};
    var name = "";
    var divId = "";

    var _init = function (pageDivId, pageSize, pageId, listenerName, objList) {
        if (!pageDivId) {
            $.alert("提示", "分页DivId不能为空!");
            return;
        }
        divId = pageDivId;
        // 初始化赋值
        if (!pageSize || pageSize < 0) {
            page.pageSize = defaultPageSize;
        } else {
            page.pageSize = pageSize;
        }
        page.totalSize = objList.length;
        page.curPage = 1;
        if (!listenerName) {
            listenerName = defaultListenerName;
        }
        name = listenerName;
        objs = objList;
        var totalPage = Math.ceil(page.totalSize / page.pageSize);
        page.totalPage = totalPage;

        common.arraypage.turnToPage(page.curPage);
    };

    // 获取当前页信息列表
    var _getObjsByPage = function (curPage) {
        var objList = [];
        for (var i = (curPage - 1) * page.pageSize + 1; i < curPage
        * page.pageSize + 1; i++) {
            if (i > page.totalSize) {
                break;
            }
            objList.push(objs[i - 1]);
        }
        return objList;
    };

    // 重置分页处理
    var _resetPageDiv = function (pageNum) {
        var str_prv = '', str_next = '';
        if (pageNum == 1) {
            str_prv = '<span class="disabled">上一页</span>';
        } else {
            str_prv = '<a href="javascript:void(0);" onclick="common.arraypage.turnPrePage()">上一页</a>';
        }

        var str = "";
        var dot = '<span class="spanDot">...</span>';
        if (page.totalPage <= 5) {
            for (var i = 1; i <= page.totalPage; i++) {
                if (page.curPage == i) {
                    str += '<span class="curr">' + i + '</span>';
                } else {
                    str += '<a href="javascript:void(0);" onclick="common.arraypage.turnToPage(parseInt($.trim($(this).text())))">'
                    + i + '</a>';
                }
            }
        } else {
            if (page.curPage <= 5) {
                for (var i = 1; i <= 7; i++) {
                    if (page.curPage == i) {
                        str += '<span class="curr">' + i + '</span>';
                    } else {
                        str += '<a href="javascript:void(0);" onclick="common.arraypage.turnToPage(parseInt($.trim($(this).text())))">'
                        + i + '</a>';
                    }
                }
                str += dot;
            } else {
                str += '<a href="javascript:void(0);" onclick="common.arraypage.turnToPage(parseInt($.trim($(this).text())))">1</a>';
                str += '<a href="javascript:void(0);" onclick="common.arraypage.turnToPage(parseInt($.trim($(this).text())))">2</a>';
                str += dot;

                var begin = page.curPage - 2;
                var end = page.curPage + 2;
                if (end > page.totalPage) {
                    end = page.totalPage;
                    begin = end - 4;
                    if (page.curPage - begin < 2) {
                        begin = begin - 1;
                    }
                } else if (end + 1 == page.totalPage) {
                    end = page.totalPage;
                }
                for (var i = begin; i <= end; i++) {
                    if (page.curPage == i) {
                        str += '<span class="curr">' + i + '</span>';
                    } else {
                        str += '<a href="javascript:void(0);" onclick="common.arraypage.turnToPage(parseInt($.trim($(this).text())))">'
                        + i + '</a>';
                    }
                }
                if (end != page.totalPage) {
                    str += dot;
                }
            }
        }

        if (page.curPage == page.totalPage) {
            str_next = '<span class="disabled">下一页</span>';
        } else {
            str_next = '<a href="javascript:void(0);" onclick="common.arraypage.turnNextPage()">下一页</a>';
        }

        var total_info = '<span class="totalText">当前第<span class="currPageNum">'
            + page.curPage
            + '</span>页<span class="totalInfoSplitStr">/</span>共<span class="totalPageNum">'
            + page.totalPage + '</span>页</span>';

        var gopage_info = '';
        gopage_info = '<span class="goPageBox">&nbsp;转到<span class="arraypage_gopage_wrap">'
        + '<input type="text" onkeydown="if(event.keyCode==13){common.arraypage.turnToPage(parseInt($.trim($(this).val())))}" class="arraypage_btn_go_input"/>页</span></span>';

        var pagerHtml = '<div class="arraypage_cls">';
        pagerHtml += str_prv
        + str
        + str_next
        + '<span class="infoTextAndGoPageBtnWrap">'
        + total_info
        + gopage_info
        + '</span><a href="javascript:void(0);"onclick="common.arraypage.gopage()" class="arraypage_btn_go">确定</span>';
        $("#" + divId).empty();
        $("#" + divId).html(pagerHtml);
    };

    // 前往前一页
    var _turnPrePage = function () {
        $(this).dispatchJEvent(name, _getObjsByPage(--page.curPage));
        _resetPageDiv(page.curPage);
    };

    // 前往下一页
    var _turnNextPage = function () {
        $(this).dispatchJEvent(name, _getObjsByPage(++page.curPage));
        _resetPageDiv(page.curPage);
    };

    var _gopage = function () {
        var pageNum = parseInt($.trim($("#" + divId).find("input").val()));
        _turnToPage(pageNum);
    };

    // 前往输入的页
    var _turnToPage = function (pageNum) {
        if (pageNum < 0 || pageNum > page.totalPage || pageNum == undefined
            || isNaN(pageNum)) {
            return;
        }
        $(this).dispatchJEvent(name, _getObjsByPage(pageNum));
        page.curPage = pageNum;
        _resetPageDiv(page.curPage);
    };

    return {
        init: _init,
        turnPrePage: _turnPrePage,
        turnNextPage: _turnNextPage,
        gopage: _gopage,
        turnToPage: _turnToPage
    };
})();