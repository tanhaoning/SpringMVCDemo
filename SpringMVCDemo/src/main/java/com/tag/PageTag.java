package com.tag;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;


/**
 * 分页标签
 *
 * @author tangs
 */
public class PageTag extends BodyTagSupport {

    private static final long serialVersionUID = -2537649580045296305L;

    protected Logger log = LoggerFactory.getLogger(PageTag.class);

    /**
     * 当前页数
     */
    private String pageNo;
    /**
     * 总页数
     */
    private String total;
    /**
     * 样式
     */
    private String styleClass;
    /**
     * 主题
     */
    private String theme;
    /**
     * url参数
     */
    private String includes;
    /**
     * url
     */
    private String url;

    public void setUrl(String url) {
        this.url = url;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }

    public void setPageNo(String pageNo) {
        this.pageNo = pageNo;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getIncludes() {
        return includes;
    }

    public void setIncludes(String includes) {
        this.includes = includes;
    }

    @Override
    public int doAfterBody() throws JspException {
        return super.doAfterBody();
    }

    @Override
    public int doStartTag() throws JspException {
        HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
        Object pageNo = request.getParameter("pageNo");
        pageNo= pageNo == null?"1":(String)pageNo;
        Object total = request.getParameter("total");
        total = total == null?1:String.valueOf(total);
        StringBuilder builder = new StringBuilder();
        builder.append("<div><span>页数:"+pageNo+",总数:"+total+"</span><br/>");
        builder.append("<input type='text' value='"+total+"' name='text'/><br/>");
        builder.append("<input type='checkbox' value='checkbox1' name='checkbox' id='checkbox1' checked='checked'>check1</check>");
        builder.append("<input type='checkbox' value='checkbox2' name='checkbox' id='checkbox2'>check2</check>");
        builder.append("<br/>");
        builder.append("<input type='radio' name ='radio' value='radio1' checked='checked'>radio1</check>");
        builder.append("<input type='radio' name ='radio' value='radio2'>radio2</check>");
        builder.append("<input type='radio' name ='radio' value='radio3'>radio3</check>");
        builder.append("</div>");
        try {
            this.pageContext.getOut().print(builder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return SKIP_BODY;
    }

    @Override
    public int doEndTag() throws JspException {
        return super.doEndTag();
    }
}