package com.mark.configuration.beans;

/**
 * @Author: 帅气的Mark
 * @Description: Mark行行好，给点注释吧！
 * @Date: Create in 2018/9/5 16:38
 * @QQ: 85104982
 */
public enum PopulaterType {
    DEFAULT,ATOMIC,NOLOCK,REENTRANTLOCK;

    public static PopulaterType parse(String type){
        if("default".equalsIgnoreCase(type))
            return DEFAULT;
        if("atomic".equalsIgnoreCase(type))
            return ATOMIC;
        if("nolock".equalsIgnoreCase(type))
            return NOLOCK;
        if("reentrantlock".equalsIgnoreCase(type))
            return REENTRANTLOCK;
        return DEFAULT;

    }
}
