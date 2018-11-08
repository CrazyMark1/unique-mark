package com.mark.serviceimp.convertor;

import com.mark.configuration.beans.Id;
import com.mark.serviceimp.beans.IdMeta;
import com.mark.serviceimp.beans.IdMetaFactory;
import com.mark.configuration.beans.TimeType;

/**
 * @Author: 帅气的Mark
 * @Description: Mark行行好，给点注释吧！
 * @Date: Create in 2018/9/4 13:49
 * @QQ: 85104982
 */
public class IdDefaultConvertor implements IdConvertor{
    private IdMeta idMata;

    public IdDefaultConvertor(IdMeta idMata) {
        this.idMata = idMata;
    }
    public IdDefaultConvertor(TimeType timeType) {
        this.idMata = IdMetaFactory.getIdMata(timeType);
    }
    public IdDefaultConvertor() {
        this.idMata = IdMetaFactory.getIdMata(TimeType.SECOND);
    }

    @Override
    public long convert(Id id) {
        long result=0;
        long offSet=0;
        result |= id.getTimeType()<<offSet;
        offSet += idMata.getTimeTypeBits();

        result |= id.getMachineID()<<offSet;
        offSet += idMata.getMachineIdBits();

        result |= id.getGenType()<<offSet;
        offSet += idMata.getGenTypeBits();

        result |= id.getSequence() << offSet;
        offSet += idMata.getSequenceBits();

        result |= id.getTime() << offSet;
        offSet += idMata.getTimeBits();

        result |= id.getVersion() <<offSet;
        return result;
    }

    @Override
    public Id convert(long id) {
        Id result=new Id();
        long offSet=0;

        result.setTimeType(id & 1);
        offSet += idMata.getTimeTypeBits();

        result.setMachineID(id >> offSet & (-1L^-1L<<idMata.getMachineIdBits()));
        offSet += idMata.getMachineIdBits();

        result.setGenType(id >> offSet & (-1L^-1L<<idMata.getGenTypeBits()));
        offSet += idMata.getGenTypeBits();

        result.setSequence(id >> offSet & (-1L^-1L<<idMata.getSequenceBits()));
        offSet += idMata.getSequenceBits();

        result.setTime(id >> offSet & (-1L^-1L<<idMata.getTimeBits()));
        offSet += idMata.getTimeBits();

        result.setVersion(id >> offSet & (-1L^-1L<<idMata.getVersionBits()));

        return result;
    }
}
