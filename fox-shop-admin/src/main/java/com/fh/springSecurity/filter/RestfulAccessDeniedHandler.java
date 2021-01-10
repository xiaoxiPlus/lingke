package com.fh.springSecurity.filter;

import cn.hutool.json.JSONUtil;
import com.fh.codeGather.CommonReturn;
import com.fh.codeGather.ReturnCode;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*没有权限拦截返回的结果*/
@Component
public class RestfulAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        //设置response返回的字符类型
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().println(JSONUtil.parse(CommonReturn.error(ReturnCode.NO_PERMISSION)));
        response.getWriter().flush();
    }
}
