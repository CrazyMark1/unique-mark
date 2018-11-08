package com.mark.serviceimp.beans;

import com.mark.configuration.beans.TimeType;
/**
 * @Author: 帅气的Mark
 * @Description: Mark行行好，给点注释吧！
 * @Date: Create in 2018/9/4 11:37
 * @QQ: 85104982
 */
public class IdMetaFactory {
    public static IdMeta getIdMata(TimeType timeType){
        if (timeType==TimeType.SECOND){
            return new IdMeta(1,30,20,2,10,1);
        }
        if (timeType==TimeType.MILLISECOND){
            return new IdMeta(1,40,10,2,10,1);
        }
        return new IdMeta( 1,30,20,2,10,1);
    }
}
