package com.example.traditionalmarket.exception;

import com.example.traditionalmarket.exception.errorcode.ErrorCode;

public class BadRequestException extends CustomException {
  public BadRequestException(ErrorCode errorCode) {
    super(errorCode);
  }
  public BadRequestException(ErrorCode errorCode, String detail) {
    super(errorCode, detail);
  }
}
