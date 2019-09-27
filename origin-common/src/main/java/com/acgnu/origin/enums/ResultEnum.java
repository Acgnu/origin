package com.acgnu.origin.enums;

import com.acgnu.origin.util.MessageHolder;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 系统错误码
 */
@Getter
@AllArgsConstructor
public enum ResultEnum {
    SUCCESS(1000, "eurm.result.success"),
    E_BUSSINESS(2000, "eurm.result.error-biz"),
    E_LOGIC(2100, "eurm.result.error-logic"),
    E_REQ_ARG(3000, "eurm.result.error-request-arg"),
    E_REQ_UNAUTH(3200, "eurm.result.error-unauth"),
    E_REQ(3100, "eurm.result.error-request"),
    E_UNKNOW(0, "eurm.result.error-unknow");

    private int code;
    private String value;

    public String getLocalValue(MessageHolder messageHolder) {
        return messageHolder.lGet(this.value);
    }
}
