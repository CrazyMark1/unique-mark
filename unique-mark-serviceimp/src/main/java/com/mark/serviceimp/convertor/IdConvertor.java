package com.mark.serviceimp.convertor;

import com.mark.configuration.beans.Id;

/**
 * @Author: 帅气的Mark
 * @Description: Mark行行好，给点注释吧！
 * @Date: Create in 2018/9/4 11:44
 * @QQ: 85104982
 */
public interface IdConvertor {
    long convert(Id id);
    Id convert(long id);
}
