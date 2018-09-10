package com.mark.serviceimp.beans;

/**
 * @Author: 帅气的Mark
 * @Description: Mark行行好，给点注释吧！
 * @Date: Create in 2018/9/4 11:37
 * @QQ: 85104982
 */
public class IdMataFactory {
    public static IdMata getIdMata(TimeType timeType){
        if (timeType==TimeType.SECOND){
            return new IdMata(1,30,20,2,10,1);
        }
        if (timeType==TimeType.MILLISECOND){
            return new IdMata(1,40,10,2,10,1);
        }
        return new IdMata( 1,30,20,2,10,1);
    }
}
