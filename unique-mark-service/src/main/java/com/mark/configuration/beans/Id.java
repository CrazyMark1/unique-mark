package com.mark.beans;

import java.io.Serializable;

/**
 * @Author: 帅气的Mark
 * @Description: Mark行行好，给点注释吧！
 * @Date: Create in 2018/9/4 10:47
 * @QQ: 85104982
 */
public class Id implements Serializable{
    private static final long serialVersionUID = -866501910956688525L;

    private long version;
    private long time;
    private long sequence;
    private long genType;
    private long machineID;
    private long timeType;

    public Id(long version, long time, long sequence, long genType, long machineID, long timeType) {
        this.version = version;
        this.time = time;
        this.sequence = sequence;
        this.genType = genType;
        this.machineID = machineID;
        this.timeType = timeType;
    }

    public Id() {
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getSequence() {
        return sequence;
    }

    public void setSequence(long sequence) {
        this.sequence = sequence;
    }

    public long getGenType() {
        return genType;
    }

    public void setGenType(long genType) {
        this.genType = genType;
    }

    public long getMachineID() {
        return machineID;
    }

    public void setMachineID(long machineID) {
        this.machineID = machineID;
    }

    public long getTimeType() {
        return timeType;
    }

    public void setTimeType(long timeType) {
        this.timeType = timeType;
    }

    @Override
    public String toString() {
        return "Id{" +
                "version=" + version +
                ", time=" + time +
                ", sequence=" + sequence +
                ", genType=" + genType +
                ", machineID=" + machineID +
                ", timeType=" + timeType +
                '}';
    }
}
