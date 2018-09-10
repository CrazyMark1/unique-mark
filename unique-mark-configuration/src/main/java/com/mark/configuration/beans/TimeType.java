package com.mark.serviceimp.beans;

/**
 * @Author: 帅气的Mark
 * @Description: Mark行行好，给点注释吧！
 * @Date: Create in 2018/9/4 11:08
 * @QQ: 85104982
 */
public enum TimeType {
    SECOND, MILLISECOND;

    public long value() {
        switch (this) {
            case SECOND:
                return 0;
            case MILLISECOND:
                return 1;
            default:
                return 0;
        }
    }

    public static TimeType parse(String name) {
        if ("second".equalsIgnoreCase(name)) {
            return SECOND;
        }
        if ("millisencond".equalsIgnoreCase(name)) {
            return MILLISECOND;
        }
        return SECOND;
    }

    public static TimeType parse(long type) {
        if (0 == type)
            return SECOND;
        if (1 == type)
            return MILLISECOND;
        return SECOND;
    }
}
