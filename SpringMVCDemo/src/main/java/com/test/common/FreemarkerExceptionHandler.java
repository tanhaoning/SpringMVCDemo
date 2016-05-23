package com.test.common;

import freemarker.core.Environment;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Writer;

/**
 * Created by tanzepeng on 2015/6/12.
 */
public class FreemarkerExceptionHandler implements TemplateExceptionHandler {
    private static Logger log = LoggerFactory.getLogger(FreemarkerExceptionHandler.class);

    public void handleTemplateException(TemplateException te, Environment env, Writer out) throws TemplateException {
        try {
            StringBuffer sb = new StringBuffer(256);
            sb.append("<script language=javascript>//\"></script>");
            sb.append("<script language=javascript>//\'></script>");
            sb.append("<script language=javascript>//\"></script>");
            sb.append("<script language=javascript>//\'></script>");
            sb.append("</title></xmp></script></noscript></style></object>");
            sb.append("</head></pre></table>");
            sb.append("</form></table></table></table></a></u></i></b>");
            sb.append("<div style='text-transform: none'>");

            sb.append("<script type='text/javascript'>");
            sb.append("var webLoc = window.location.toString();");
            sb.append("var arrWebLoc = webLoc.split('/');");
            sb.append("webLoc = arrWebLoc[0]+'//'+arrWebLoc[1]+'/'+arrWebLoc[2]+'/'+arrWebLoc[3];");
            sb.append("window.location.href=webLoc+'/error/error-freemarker.jsp';");
            sb.append("</script>");

            sb.append("</div></html>");

            this.log.debug("--location--{}", sb);
            out.write(sb.toString());
            this.log.error("Freemarker Error: ", te);
        } catch (IOException e) {
            this.log.error("异常捕捉失败：", e);
            //throw new TemplateException("Failed to print error message. Cause: " + e, env);
        } finally {
            if (null != out) {
                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                }
            }
        }
    }
}
