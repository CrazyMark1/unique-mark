package com.mark.serviceimp.populater;

import com.mark.configuration.beans.Id;
import com.mark.serviceimp.beans.IdMeta;
import com.mark.configuration.beans.TimeType;
import com.mark.utils.TimeUtil;

/**
 * @Author: 帅气的Mark
 * @Description: Mark行行好，给点注释吧！
 * @Date: Create in 2018/9/4 16:49
 * @QQ: 85104982
 */
public class DefaultIdPopulator implements IdPopulator {
    private long sequence=0;
    private long lastTimestamp=-1;
    @Override
    public synchronized void  populateId(Id id, IdMeta idMeta) {
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
