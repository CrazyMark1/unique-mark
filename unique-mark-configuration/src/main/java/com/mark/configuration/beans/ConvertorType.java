package com.mark.beans;

/**
 * @Author: 帅气的Mark
 * @Description: Mark行行好，给点注释吧！
 * @Date: Create in 2018/9/5 16:28
 * @QQ: 85104982
 */
public enum ConvertorType {
    DEFAULT;

    public static ConvertorType parse(String type){
        if ("default".equalsIgnoreCase(type))
            return DEFAULT;
        return DEFAULT;
    }
}
