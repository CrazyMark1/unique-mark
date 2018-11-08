package com.mark.serviceimp.populater;

import com.mark.configuration.beans.Id;
import com.mark.serviceimp.beans.IdMeta;

import java.util.concurrent.ExecutionException;

/**
 * @Author: 帅气的Mark
 * @Description: Mark行行好，给点注释吧！
 * @Date: Create in 2018/9/4 16:48
 * @QQ: 85104982
 */
public interface IdPopulator {
    void populateId(Id id, IdMeta idMeta) throws ExecutionException, InterruptedException;
}
