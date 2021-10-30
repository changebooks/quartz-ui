package com.github.changebooks.quartz.ui.main;

import com.github.changebooks.quartz.ui.util.Constants;
import com.github.changebooks.quartz.ui.util.Result;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * 统一处理异常
 *
 * @author changebooks
 */
@ControllerAdvice
public class WideExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(WideExceptionHandler.class);

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public Result<?> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException tr, HttpServletRequest request) {
        LOGGER.error("handleHttpRequestMethodNotSupportedException, request: {}, throwable: ", request, tr);

        return Result.fromMessage(Constants.METHOD_NOT_ALLOWED, tr.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public Result<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException tr, HttpServletRequest request) {
        LOGGER.error("handleMethodArgumentNotValidException, request: {}, throwable: ", request, tr);

        FieldError error = tr.getBindingResult().getFieldError();
        String message = Objects.nonNull(error) ? error.getDefaultMessage() : Constants.MSG_ARGS_ERR;

        return Result.fromMessage(Constants.ARGS_ERR, message);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public Result<?> handleIllegalArgumentException(IllegalArgumentException tr, HttpServletRequest request) {
        LOGGER.error("handleIllegalArgumentException, request: {}, throwable: ", request, tr);

        return Result.fromMessage(Constants.ARGS_ERR, tr.getMessage());
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseBody
    public Result<?> handleNullPointerException(NullPointerException tr, HttpServletRequest request) {
        LOGGER.error("handleNullPointerException, request: {}, throwable: ", request, tr);

        return Result.fromMessage(Constants.ARGS_ERR, tr.getMessage());
    }

    @ExceptionHandler(SchedulerException.class)
    @ResponseBody
    public Result<?> handleSchedulerException(SchedulerException tr, HttpServletRequest request) {
        LOGGER.error("handleSchedulerException, request: {}, throwable: ", request, tr);

        return Result.fromThrowable(tr);
    }

    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public Result<?> handleThrowable(Throwable tr, HttpServletRequest request) {
        LOGGER.error("handleThrowable, request: {}, throwable: ", request, tr);

        return Result.fromThrowable(tr);
    }

}
