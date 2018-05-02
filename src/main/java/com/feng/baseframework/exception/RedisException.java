package com.feng.baseframework.exception;

import com.feng.baseframework.constant.ResultEnum;

/**
 * @ProjectName: svc-search-biz
 * @description: redis异常类
 * @author: lanhaifeng
 * @create: 2018-05-02 15:44
 * @UpdateUser:
 * @UpdateDate: 2018/5/2 15:44
 * @UpdateRemark:
 **/
public class RedisException extends BusinessException {

    public RedisException(ResultEnum resultEnum, Exception sourceException) {
        super(resultEnum.getMessage());
        super.setCode(resultEnum.getCode());
        super.setSourceException(sourceException);
    }


}
