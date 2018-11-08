package com.mark.serviceimp.beans;

/**
 * @Author: 帅气的Mark
 * @Description: Mark行行好，给点注释吧！
 * @Date: Create in 2018/9/4 11:31
 * @QQ: 85104982
 */
public class IdMeta {
    private int versionBits;
    private int timeBits;
    private int sequenceBits;
    private int genTypeBits;
    private int machineIdBits;
    private int timeTypeBits;

    public IdMeta(int versionBits, int timeBits, int sequenceBits, int genTypeBits, int machineIdBits, int timeTypeBits) {
        this.versionBits = versionBits;
        this.timeBits = timeBits;
        this.sequenceBits = sequenceBits;
        this.genTypeBits = genTypeBits;
        this.machineIdBits = machineIdBits;
        this.timeTypeBits = timeTypeBits;
    }

    public int getVersionBits() {
        return versionBits;
    }

    public void setVersionBits(int versionBits) {
        this.versionBits = versionBits;
    }

    public int getTimeBits() {
        return timeBits;
    }

    public void setTimeBits(int timeBits) {
        this.timeBits = timeBits;
    }

    public int getSequenceBits() {
        return sequenceBits;
    }

    public void setSequenceBits(int sequenceBits) {
        this.sequenceBits = sequenceBits;
    }

    public int getGenTypeBits() {
        return genTypeBits;
    }

    public void setGenTypeBits(int genTypeBits) {
        this.genTypeBits = genTypeBits;
    }

    public int getMachineIdBits() {
        return machineIdBits;
    }

    public void setMachineIdBits(int machineIdBits) {
        this.machineIdBits = machineIdBits;
    }

    public int getTimeTypeBits() {
        return timeTypeBits;
    }

    public void setTimeTypeBits(int timeTypeBits) {
        this.timeTypeBits = timeTypeBits;
    }
}
