package com.example.traditionalmarket.exception;

import com.example.traditionalmarket.exception.errorcode.ErrorCode;

public class ValidationException extends CustomException {
    public ValidationException(ErrorCode errorCode) {
        super(errorCode);
    }
    public ValidationException(ErrorCode errorCode, String detail) {
        super(errorCode, detail);
    }
}
