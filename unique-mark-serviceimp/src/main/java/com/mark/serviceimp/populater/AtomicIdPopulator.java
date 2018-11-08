package com.mark.serviceimp.populater;

import com.mark.configuration.beans.Id;
import com.mark.serviceimp.beans.IdMeta;
import com.mark.configuration.beans.TimeType;
import com.mark.utils.TimeUtil;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @Author: 帅气的Mark
 * @Description: Mark行行好，给点注释吧！
 * @Date: Create in 2018/9/5 11:18
 * @QQ: 85104982
 */
public class AtomicIdPopulator implements IdPopulator {
    class Variant {
        private long sequence = 0;
        private long lastTimestamp = -1;

    }

    private AtomicReference<Variant> variant = new AtomicReference<Variant>(new Variant());


    public void populateId(Id id, IdMeta idMeta) {
        Variant varOld, varNew;
        long timestamp, sequence;

        while (true) {
            varOld = variant.get();
            timestamp = TimeUtil.getTimestamp(TimeType.parse(id.getTimeType()));
            TimeUtil.validateTimestamp(varOld.lastTimestamp, timestamp);
            sequence = varOld.sequence;
            if (timestamp == varOld.lastTimestamp) {
                sequence++;
                sequence &= (-1L ^ -1L << idMeta.getSequenceBits());
                if (sequence == 0) {
                    timestamp = TimeUtil.waitNextTimestamp(varOld.lastTimestamp, TimeType.parse(id.getTimeType()));
                }
            } else {
                sequence = 0;
            }

            varNew = new Variant();
            varNew.sequence = sequence;
            varNew.lastTimestamp = timestamp;

            if (variant.compareAndSet(varOld, varNew)) {
                id.setSequence(sequence);
                id.setTime(timestamp);
                break;
            }
        }

    }
}
