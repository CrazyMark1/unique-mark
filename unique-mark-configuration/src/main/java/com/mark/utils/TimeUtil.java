package com.mark.utils;

import com.mark.configuration.beans.TimeType;

/**
 * @Author: 帅气的Mark
 * @Description: Mark行行好，给点注释吧！
 * @Date: Create in 2018/9/4 16:55
 * @QQ: 85104982
 */
public class TimeUtil {
    public static final long START_TIME=1536051500000L;

    public static long getTimestamp(TimeType timeType){
        if (timeType==TimeType.SECOND)
            return (System.currentTimeMillis()-START_TIME)/1000;
        if (timeType==TimeType.MILLISECOND)
            return System.currentTimeMillis()-START_TIME;
        return (System.currentTimeMillis()-START_TIME)/1000;
    }

    public static void validateTimestamp(long lastTimestamp,long timestamp){
        if (lastTimestamp>timestamp)
            throw new RuntimeException("时间错误");
    }

    public static void validateZkTimestamp(long timestamp,long zktimestamp){
        if (zktimestamp>timestamp)
            throw new RuntimeException("与Zk时间不符，请调整时间");
    }

    public static long waitNextTimestamp(long lastTimestamp,TimeType timeType){
        long timestamp=getTimestamp(timeType);
        while (timestamp<=lastTimestamp){
            timestamp=getTimestamp(timeType);
        }
        return timestamp;
    }
}
