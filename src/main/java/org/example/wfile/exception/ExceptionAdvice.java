package org.example.wfile.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.example.wfile.common.ApiResponse;
import org.example.wfile.core.constant.HttpStatus;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

@RestControllerAdvice
@Slf4j
public class ExceptionAdvice {
    /**
     * 全局异常
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ApiResponse handleException(Exception e,
                                      HttpServletRequest request){
        String requestURI = request.getRequestURI();
        log.error("请求地址{},发生系统异常",requestURI,e);
        return ApiResponse.error(e.getMessage());
    }

    @ExceptionHandler(CoustomException.class)
    public ApiResponse handleCoustomException(CoustomException e, HttpServletRequest request){
        String requestURI = request.getRequestURI();
        log.error("请求地址{},发生自定义异常->code:{}|message:{}",requestURI,e.getCode(),e.getMessage(),e);
        return ApiResponse.error(e.getCode(),e.getMessage());
    }

    /**
     * 参数验证失败异常
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e,
                                                            HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        String message = e.getBindingResult().getFieldError().getDefaultMessage();
        log.error("请求地址{},参数验证失败{}", requestURI, e.getObjectName(),e);
        return ApiResponse.error(message);
    }

    /**
     * 权限校验异常
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ApiResponse handleAccessDeniedException(AccessDeniedException e,
                                                  HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址{},权限校验失败{}", requestURI, e.getMessage());
        return ApiResponse.error(HttpStatus.FORBIDDEN, "没有权限，请联系管理员授权");
    }

    /**
     * 请求方式不支持
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ApiResponse handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e,
                                                          HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址{},不支持{}请求", requestURI, e.getMethod());
        return ApiResponse.error(e.getMessage());
    }
    /**
     * 拦截错误SQL异常
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(BadSqlGrammarException.class)
    public ApiResponse handleBadSqlGrammarException(BadSqlGrammarException e,
                                                   HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',发生数据库异常.", requestURI, e);
        return ApiResponse.error(HttpStatus.ERROR, "数据库异常！");
    }

    /**
     * 可以拦截表示违反数据库的完整性约束导致的异常。
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ApiResponse handleDataIntegrityViolationException(DataIntegrityViolationException e,
                                                             HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',发生数据库异常.", requestURI, e);
        return ApiResponse.error(HttpStatus.ERROR, "数据库异常！");
    }


    /**
     * 可以拦截违反数据库的非完整性约束导致的异常，可能也会拦截一些也包括 SQL 语句错误、连接问题、权限问题等各种数据库异常。
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(UncategorizedSQLException.class)
    public ApiResponse handleUncategorizedSqlException(UncategorizedSQLException e,
                                                      HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',发生数据库异常.", requestURI, e);
        return ApiResponse.error(HttpStatus.ERROR, "数据库异常！");
    }

    /**
     * 拦截未知的运行时异常
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(RuntimeException.class)
    public ApiResponse handleRuntimeException(RuntimeException e,
                                             HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址{},发生未知运行异常", requestURI, e);
        return ApiResponse.error(e.getMessage());
    }

}
