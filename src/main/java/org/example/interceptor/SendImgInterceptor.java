package org.example.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.example.util.CheckLogin;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Enumeration;
import java.io.PrintWriter;
@Slf4j
@Component
public class SendImgInterceptor implements HandlerInterceptor {

    private static final String TOKEN_PARAM = "token";

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {


        // 1、获取请求url
        String url = req.getRequestURI().toString();
        log.info("开始拦截: url="+url);
        String token = req.getHeader("token");
        System.out.println("token: "+token);
//        Enumeration<String> headers_name= req.getHeaderNames();
//        while (headers_name.hasMoreElements()) {
//            String headerName = headers_name.nextElement();
//            String headerValue = req.getHeader(headerName);
//            System.out.println(headerName + ": " + headerValue);
//        }
//        // 将响应改为 "555666"
//        resp.setContentType("text/plain;charset=UTF-8");  // 设置响应的内容类型和字符集
//        PrintWriter out = resp.getWriter();  // 获取输出流
//        out.print("555666");
        return true;

//        // 获取查询参数中的令牌
//        String tokenFromQuery = req.getParameter(TOKEN_PARAM);
//
//        // 如果查询参数中的token存在，则使用它进行验证
//        if (tokenFromQuery != null && checkToken(tokenFromQuery)) {
//            log.info("查询参数中的Jwt：{}",tokenFromQuery);
//            log.info("Token验证成功！");
//            return true;
//        }
//
//        // 如果查询参数中的token不存在，则尝试从JSON请求体中获取token
//        String tokenFromJsonBody = getTokenFromJsonBody(req);
//        if (tokenFromJsonBody != null && checkToken(tokenFromJsonBody)) {
//            log.info("尝试从JSON请求体中获取token：{}",tokenFromJsonBody);
//            log.info("Token验证成功！");
//            return true;
//        }
//
//        // 如果两个地方的token验证都失败，则返回false
//        return false;
    }

    private String getTokenFromJsonBody(HttpServletRequest request) throws IOException {
        // 检查是否已经读取过请求体
        if (request.getAttribute("jsonBody") != null) {
            return (String) request.getAttribute("jsonBody");
        }

        StringBuilder jsonBody = new StringBuilder();
        String line;
        try (BufferedReader reader = request.getReader()) {
            while ((line = reader.readLine()) != null) {
                jsonBody.append(line);
            }
        }

        // 将读取到的JSON请求体存储在request属性中，以便后续使用
        request.setAttribute("jsonBody", jsonBody.toString());
        return jsonBody.toString();
    }

    //验证逻辑
    private boolean checkToken(String token) {
        return CheckLogin.checkLogin(token);
    }
}