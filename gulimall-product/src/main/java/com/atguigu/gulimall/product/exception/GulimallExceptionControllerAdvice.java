package com.atguigu.gulimall.product.exception;

import com.atguigu.common.exception.BizCodeEnum;
import com.atguigu.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 描述: Controller统一异常处理
 *
 * @author haoyunzheng
 * @date 2022-05-05 21:38
 */
@RestControllerAdvice
@Slf4j
public class GulimallExceptionControllerAdvice {

    @ExceptionHandler(value = Throwable.class)
    public R handleException(Throwable throwable) {
        log.error("未知异常{},异常类型{}", throwable.getMessage(), throwable.getClass(), throwable);
        return R.error(BizCodeEnum.UNKNOWN_EXCEPTION.getCode(), BizCodeEnum.UNKNOWN_EXCEPTION.getMsg());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public R handleValidException(MethodArgumentNotValidException e) {
        Map<String, String> data = e.getBindingResult().getFieldErrors().stream().collect(
                Collectors.toMap(FieldError::getField, x -> Optional.ofNullable(x.getDefaultMessage()).orElse("")));
        return R.error(BizCodeEnum.VALID_EXCEPTION.getCode(), BizCodeEnum.VALID_EXCEPTION.getMsg()).put("data", data);
    }

}
