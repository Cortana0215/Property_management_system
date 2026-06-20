package com.example.propertymanagement.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        
        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            
            model.addAttribute("statusCode", statusCode);
            
            switch (statusCode) {
                case 400:
                    model.addAttribute("errorMessage", "请求参数错误");
                    model.addAttribute("errorDescription", "请检查您的请求参数是否正确");
                    break;
                case 401:
                    model.addAttribute("errorMessage", "未授权访问");
                    model.addAttribute("errorDescription", "请先登录系统");
                    break;
                case 403:
                    model.addAttribute("errorMessage", "访问被拒绝");
                    model.addAttribute("errorDescription", "您没有足够的权限访问此页面");
                    break;
                case 404:
                    model.addAttribute("errorMessage", "页面未找到");
                    model.addAttribute("errorDescription", "您访问的页面不存在，请检查URL地址");
                    break;
                case 500:
                    model.addAttribute("errorMessage", "服务器内部错误");
                    model.addAttribute("errorDescription", "服务器遇到了问题，请稍后重试");
                    break;
                default:
                    model.addAttribute("errorMessage", "未知错误");
                    model.addAttribute("errorDescription", "发生了未知错误");
            }
        }
        
        return "error";
    }
}
