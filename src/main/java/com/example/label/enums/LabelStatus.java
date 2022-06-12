package com.example.label.enums;

/**
 * 标签状态
 * <p>
 * 待审核 -> 已审核 -> 启用 <-> 停用
 */
public enum LabelStatus {
    /**
     * 待审核
     */
    UNAUDITED(0),

    /**
     * 已审核
     */
    AUDITED(1),
    /**
     * 启用
     */
    ENABLE(2),

    /**
     * 停用
     */
    DISABLE(3);

    private int code;

    public int getCode() {
        return code;
    }

    LabelStatus(int code) {
        this.code = code;
    }

    public static LabelStatus of(int code){
        for (LabelStatus e : values()){
            if (e.getCode() == code){
                return e;
            }
        }
        return null;
    }
}
