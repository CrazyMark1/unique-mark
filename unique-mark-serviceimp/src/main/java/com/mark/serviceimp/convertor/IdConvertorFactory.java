package com.mark.serviceimp.convertor;

import com.mark.configuration.beans.ConvertorType;

/**
 * @Author: 帅气的Mark
 * @Description: Mark行行好，给点注释吧！
 * @Date: Create in 2018/9/6 17:58
 * @QQ: 85104982
 */
public class IdConvertorFactory {
    public static IdConvertor getIdConvertor(ConvertorType type) {
        switch (type) {
            case DEFAULT:
                return new IdDefaultConvertor();
            default:
                return new IdDefaultConvertor();
        }

    }
}
