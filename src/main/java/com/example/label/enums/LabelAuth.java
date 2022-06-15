package com.example.label.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @date 2022-06-13
 * @author jack
 */
public enum LabelAuth {

    /**
     * 私有标签。只有创建者自己可用
     */
    PRIVATE(0, "私有"),

    /**
     * 授权标签。只有创建者和被授权的用户可以使用
     */
    PROTECT(1, "授权"),

    /**
     * 共享标签。所有人均可使用
     */
    PUBLIC(2, "共享");

    private int code;

    private String desc;

    private static Map<Integer, LabelAuth> map = new HashMap<>();

    private static Map<String, LabelAuth> desc2Map = new HashMap<>();

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    LabelAuth(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    static {
        for (LabelAuth labelAuth : values()){
            map.put(labelAuth.getCode(), labelAuth);
            desc2Map.put(labelAuth.getDesc(), labelAuth);
        }
    }

    public static LabelAuth of(int code){
        for (LabelAuth e : values()){
            if (e.getCode() == code){
                return e;
            }
        }
        return null;
    }

    public static LabelAuth getInstance(int code){
        return map.get(code);
    }

    public static LabelAuth getByDesc(String desc){
        return desc2Map.get(desc);
    }
}
