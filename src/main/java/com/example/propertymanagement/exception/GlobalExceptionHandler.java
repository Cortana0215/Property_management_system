package com.example.propertymanagement.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ModelAndView handleResourceNotFoundException(ResourceNotFoundException ex, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("statusCode", 404);
        modelAndView.addObject("errorMessage", "资源未找到");
        modelAndView.addObject("errorDescription", ex.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(BusinessException.class)
    public ModelAndView handleBusinessException(BusinessException ex, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("statusCode", ex.getStatus().value());
        modelAndView.addObject("errorMessage", "业务错误");
        modelAndView.addObject("errorDescription", ex.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleGenericException(Exception ex, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("statusCode", 500);
        modelAndView.addObject("errorMessage", "服务器内部错误");
        modelAndView.addObject("errorDescription", "服务器遇到了问题，请稍后重试");
        return modelAndView;
    }
}
