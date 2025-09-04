package com.smartcabinet.common.result;

/**
 * 响应状态码枚举
 * 
 * @author SmartCabinet Team
 */
public enum ResultCode {
    
    // 成功
    SUCCESS(200, "操作成功"),
    
    // 客户端错误 4xx
    BAD_REQUEST(400, "请求参数错误"),
    UNAUTHORIZED(401, "未授权"),
    FORBIDDEN(403, "权限不足"),
    NOT_FOUND(404, "资源不存在"),
    METHOD_NOT_ALLOWED(405, "请求方法不允许"),
    CONFLICT(409, "数据冲突"),
    VALIDATION_ERROR(422, "参数验证失败"),
    
    // 服务器错误 5xx
    INTERNAL_SERVER_ERROR(500, "服务器内部错误"),
    BAD_GATEWAY(502, "网关错误"),
    SERVICE_UNAVAILABLE(503, "服务不可用"),
    
    // 业务错误 6xxx
    USER_NOT_FOUND(6001, "用户不存在"),
    USER_ALREADY_EXISTS(6002, "用户已存在"),
    INVALID_PASSWORD(6003, "密码错误"),
    ACCOUNT_LOCKED(6004, "账户已被锁定"),
    INVALID_TOKEN(6005, "无效的令牌"),
    TOKEN_EXPIRED(6006, "令牌已过期"),
    
    PRODUCT_NOT_FOUND(6101, "商品不存在"),
    INSUFFICIENT_STOCK(6102, "库存不足"),
    PRODUCT_OFF_SHELF(6103, "商品已下架"),
    
    ORDER_NOT_FOUND(6201, "订单不存在"),
    ORDER_PAID(6202, "订单已支付"),
    ORDER_EXPIRED(6203, "订单已过期"),
    PAYMENT_FAILED(6204, "支付失败"),
    
    DEVICE_NOT_FOUND(6301, "设备不存在"),
    DEVICE_OFFLINE(6302, "设备离线"),
    DEVICE_BUSY(6303, "设备忙碌"),
    DEVICE_FAULT(6304, "设备故障"),
    DOOR_OPEN_FAILED(6305, "开门失败"),
    
    WECHAT_AUTH_FAILED(6401, "微信授权失败"),
    SMS_SEND_FAILED(6402, "短信发送失败"),
    FILE_UPLOAD_FAILED(6403, "文件上传失败");
    
    private final Integer code;
    private final String message;
    
    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
    
    public Integer getCode() {
        return code;
    }
    
    public String getMessage() {
        return message;
    }
}
