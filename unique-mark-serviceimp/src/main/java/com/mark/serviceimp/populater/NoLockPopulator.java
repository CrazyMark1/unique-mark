package com.mark.serviceimp.populater;

import com.mark.configuration.beans.Id;
import com.mark.serviceimp.beans.IdMeta;
import com.mark.configuration.beans.TimeType;
import com.mark.utils.TimeUtil;

import java.util.concurrent.*;

/**
 * @Author: 帅气的Mark
 * @Description: Mark行行好，给点注释吧！
 * @Date: Create in 2018/9/5 13:59
 * @QQ: 85104982
 */
public class NoLockPopulator implements AsynIdPopulator {
    private static final int THREADCOUNT=Runtime.getRuntime().availableProcessors();
    private static final ExecutorService executorService= Executors.newFixedThreadPool(THREADCOUNT);
    final ThreadLocal<Long> threadId=new ThreadLocal<Long>(){
        @Override
        protected Long initialValue() {
            return Thread.currentThread().getId()%THREADCOUNT;
        }
    };
    ThreadLocal<Long> sequence=new ThreadLocal<Long>(){
        @Override
        protected Long initialValue() {
            return threadId.get();
        }
    };
    ThreadLocal<Long> lastTimestamp=new ThreadLocal<Long>(){
        @Override
        protected Long initialValue() {
            return -1L;
        }
    };
    @Override
    public Future asynPopulateId(Id id, IdMeta idMeta) {
        return  executorService.submit(()->{
            populateId1(id,idMeta);
            return;
        });
    }

    @Override
    public void populateId(Id id, IdMeta idMeta){
        Future future=asynPopulateId(id,idMeta);
        try {
            future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void populateId1(Id id, IdMeta idMeta) {
        long timestamp= TimeUtil.getTimestamp(TimeType.parse(id.getTimeType()));
        TimeUtil.validateTimestamp(lastTimestamp.get(),timestamp);

        if (timestamp==lastTimestamp.get()){
            long temp=sequence.get()+THREADCOUNT;
            temp &= (-1L ^-1L <<idMeta.getSequenceBits());
            if (sequence.get()>temp){
                timestamp=TimeUtil.waitNextTimestamp(lastTimestamp.get(),TimeType.parse(id.getTimeType()));
                lastTimestamp.set(timestamp);
                sequence.set(temp);
            }
            else {
                sequence.set(temp);
            }
        }
        else {
            lastTimestamp.set(timestamp);
            sequence.set(threadId.get());
        }
        id.setTime(timestamp);
        id.setSequence(sequence.get());
    }

}

