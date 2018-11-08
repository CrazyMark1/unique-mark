package com.mark.serviceimp.populater;

import com.mark.configuration.beans.Id;
import com.mark.serviceimp.beans.IdMeta;
import com.mark.configuration.beans.TimeType;
import com.mark.utils.TimeUtil;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: 帅气的Mark
 * @Description: Mark行行好，给点注释吧！
 * @Date: Create in 2018/9/5 13:53
 * @QQ: 85104982
 */
public class ReentrantLockIdPopulator implements IdPopulator {
    private long sequence=0;
    private long lastTimestamp=-1;
    private ReentrantLock lock=new ReentrantLock();
    @Override
    public  void  populateId(Id id, IdMeta idMeta) {
        lock.lock();
        try {
            populateId1(id,idMeta);
        }finally {
            lock.unlock();
        }
    }
    private   void  populateId1(Id id, IdMeta idMeta) {
        long timestamp= TimeUtil.getTimestamp(TimeType.parse(id.getTimeType()));
        TimeUtil.validateTimestamp(lastTimestamp,timestamp);

        if (timestamp==lastTimestamp){
            sequence++;
            sequence &= (-1L ^-1L <<idMeta.getSequenceBits());
            if (sequence==0){
                timestamp=TimeUtil.waitNextTimestamp(lastTimestamp,TimeType.parse(id.getTimeType()));
                lastTimestamp=timestamp;
            }
        }
        else {
            lastTimestamp=timestamp;
            sequence=0;
        }
        id.setTime(timestamp);
        id.setSequence(sequence);
    }

}
