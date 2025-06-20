package com.example.traditionalmarket.exception;

import com.example.traditionalmarket.exception.errorcode.ErrorCode;

public class ConflictException extends CustomException {
    public ConflictException(ErrorCode errorCode) {
        super(errorCode);
    }
    public ConflictException(ErrorCode errorCode, String detail) {
        super(errorCode, detail);
    }
}
