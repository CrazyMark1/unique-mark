package com.mark.service;

import com.mark.configuration.beans.Id;

/**
 * @Author: 帅气的Mark
 * @Description: Mark行行好，给点注释吧！
 * @Date: Create in 2018/9/4 10:43
 * @QQ: 85104982
 */
public interface IdService {
    long getId();
    Id parseId(long id);
}
