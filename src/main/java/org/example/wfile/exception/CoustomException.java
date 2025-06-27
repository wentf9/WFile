package org.example.wfile.exception;

import lombok.Getter;
import org.example.wfile.common.ResultCodeEnum;

@Getter
public class CoustomException extends RuntimeException{
    private Integer code;
    private String message;
    public CoustomException(String message) {
        super(message);
    }
    public CoustomException(String message, Throwable cause) {
        super(message, cause);
    }
    public CoustomException(Integer code, String message) {
        super(message);
        this.code = code;
    }
    public CoustomException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }
    public CoustomException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
    }
}
