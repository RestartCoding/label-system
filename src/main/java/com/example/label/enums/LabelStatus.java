package com.example.label.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 标签状态
 * <p>
 * 待审核 -> 已审核 -> 启用 <-> 停用
 * @author jack
 */
public enum LabelStatus {
    /**
     * 待审核
     */
    UNAUDITED(0, "未审核"),

    /**
     * 已审核
     */
    AUDITED(1, "已审核"),
    /**
     * 启用
     */
    ENABLE(2, "启用"),

    /**
     * 停用
     */
    DISABLE(3, "停用");

    private int code;

    private String desc;

    private static Map<Integer, LabelStatus> map = new HashMap<>();

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    LabelStatus(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    static {
        for (LabelStatus labelStatus : values()){
            map.put(labelStatus.getCode(), labelStatus);
        }
    }

    public static LabelStatus of(int code){
        for (LabelStatus e : values()){
            if (e.getCode() == code){
                return e;
            }
        }
        return null;
    }

    public static LabelStatus getInstance(int code){
        return map.get(code);
    }
}
