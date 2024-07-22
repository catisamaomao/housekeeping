package org.example.houseKeeping.exception;

import org.example.houseKeeping.pojo.Result;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.logging.Logger;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = Logger.getLogger(GlobalExceptionHandler.class.getName());

    @ExceptionHandler(TokenExpiredException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public Result handleTokenExpiredException(HttpServletRequest request, TokenExpiredException ex) {
        logger.warning("Token expired: " + ex.getMessage());
        return Result.error("token 已过期");
    }

    @ExceptionHandler(TokenNotProvidedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public Result handleTokenNotProvidedException(HttpServletRequest request, TokenNotProvidedException ex) {
        logger.warning("Token not provided: " + ex.getMessage());
        return Result.error("token 没有提供");
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Result handleGeneralException(HttpServletRequest request, Exception ex) {
        logger.severe("An error occurred: " + ex.getMessage());
        return Result.error("内部服务器错误");
    }
}
