package com.mark.configuration.machineidprovider;

import com.mark.configuration.ConfigImp;

/**
 * @Author: 帅气的Mark
 * @Description: Mark行行好，给点注释吧！
 * @Date: Create in 2018/9/4 16:22
 * @QQ: 85104982
 */
public class MachineDefaultProvider implements MachineIdProvider {
    private ConfigImp configImp;

    public MachineDefaultProvider(ConfigImp configImp) {
        this.configImp = configImp;
    }

    @Override
    public long getMachineId() {
        return configImp.getMachineId();
    }
}
