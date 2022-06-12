package com.example.label.enums;

public enum LabelAuth {

    PRIVATE(0),

    PROTECT(1),

    PUBLIC(2);

    private int code;

    public int getCode() {
        return code;
    }

    LabelAuth(int code) {
        this.code = code;
    }

    public static LabelAuth of(int code){
        for (LabelAuth e : values()){
            if (e.getCode() == code){
                return e;
            }
        }
        return null;
    }
}
