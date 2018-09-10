package com.mark.serviceimp.provider;

import com.mark.configuration.machineidprovider.MachineIdProvider;

/**
 * @Author: 帅气的Mark
 * @Description: Mark行行好，给点注释吧！
 * @Date: Create in 2018/9/4 16:22
 * @QQ: 85104982
 */
public class MachineDefaultProvider implements MachineIdProvider {
    @Override
    public long getMachineId() {
        return 1022;
    }
}
