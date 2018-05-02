package com.feng.baseframework.constant;

/**     
  *
  * @ProjectName:    svc-search-biz
  * @Description:    定义响应结果
  * @Author:         lanhaifeng
  * @CreateDate:     2018/5/2 16:20
  * @UpdateUser:     
  * @UpdateDate:     2018/5/2 16:20
  * @UpdateRemark:   
 */
public enum ResultEnum {
    UNKONW_ERROR(-1, "未知错误"),
    SUCCESS(0, "成功"),
    REDIS_SET_ERROR(1,"redis缓存数据失败"),
    REDIS_GET_ERROR(2,"redis获取缓存数据失败"),
    REDIS_REMOVE_ERROR(3,"redis清理键数据失败"),
    REDIS_KEY_NULL_ERROR(4,"redis键为空")


    ;

    private Integer code;
    private String message;

    ResultEnum(Integer code, String message){
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
