package com.mark.serviceimp.populater;

import com.mark.configuration.beans.Id;
import com.mark.serviceimp.beans.IdMeta;

import java.util.concurrent.Future;

/**
 * @Author: 帅气的Mark
 * @Description: Mark行行好，给点注释吧！
 * @Date: Create in 2018/9/5 15:23
 * @QQ: 85104982
 */
public interface AsynIdPopulator extends IdPopulator {
    Future asynPopulateId(Id id, IdMeta idMeta);
}
