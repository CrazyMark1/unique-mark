package com.mark.serviceimp;

import com.mark.configuration.ConfigImp;
import com.mark.configuration.beans.Id;
import com.mark.service.IdService;
import com.mark.serviceimp.beans.IdMeta;
import com.mark.serviceimp.beans.IdMetaFactory;
import com.mark.serviceimp.convertor.IdConvertor;
import com.mark.serviceimp.convertor.IdConvertorFactory;

/**
 * @Author: 帅气的Mark
 * @Description: Mark行行好，给点注释吧！
 * @Date: Create in 2018/9/4 10:59
 * @QQ: 85104982
 */
public abstract class AbstractIdServiceImp implements IdService{
    protected ConfigImp configImp;
    private IdMeta idMeta;

    private IdConvertor idConvertor;

    public AbstractIdServiceImp(ConfigImp configImp) {
        this.configImp=configImp;
        init();
    }

    public void init(){
        idMeta=IdMetaFactory.getIdMata(configImp.getTimeType());
        idConvertor = IdConvertorFactory.getIdConvertor(configImp.getConvertorType());
    }

    @Override
    public long getId() {
        Id id=new Id();
        id.setVersion(configImp.getVersion());
        id.setGenType(configImp.getGenType());
        id.setMachineID(configImp.getMachineId());
        id.setTimeType(configImp.getTimeType().value());

        populateId(id,idMeta);

        long result=idConvertor.convert(id);
        return result;
    }

    protected abstract void populateId(Id id,IdMeta idMeta);

    @Override
    public Id parseId(long id) {
        return idConvertor.convert(id);
    }
}
