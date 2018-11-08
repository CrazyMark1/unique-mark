package com.mark.serviceimp.populater;

import com.mark.configuration.beans.PopulaterType;

/**
 * @Author: 帅气的Mark
 * @Description: Mark行行好，给点注释吧！
 * @Date: Create in 2018/9/6 18:02
 * @QQ: 85104982
 */
public class PopulaterFactory {
    public static IdPopulator getIdPopulate(PopulaterType type) {
        switch (type) {
            case DEFAULT:
                return new DefaultIdPopulator();
            case ATOMIC:
                return new AtomicIdPopulator();
            case NOLOCK:
                return new NoLockPopulator();
            case REENTRANTLOCK:
                return new ReentrantLockIdPopulator();
            default:
                return new DefaultIdPopulator();
        }
    }
}
