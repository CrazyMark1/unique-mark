package com.mark.serviceimp;

import com.mark.configuration.ConfigImp;
import com.mark.configuration.beans.Id;
import com.mark.serviceimp.beans.IdMeta;
import com.mark.serviceimp.populater.IdPopulator;
import com.mark.serviceimp.populater.PopulaterFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.ExecutionException;

/**
 * @Author: 帅气的Mark
 * @Description: Mark行行好，给点注释吧！
 * @Date: Create in 2018/9/4 16:46
 * @QQ: 85104982
 */
public class IdServiceImp extends AbstractIdServiceImp{
    private IdPopulator populater= PopulaterFactory.getIdPopulate(configImp.getPopulaterType());

    public IdServiceImp(ConfigImp configImp) {
        super(configImp);
    }


    @Override
    protected void populateId(Id id, IdMeta idMeta) {
        try {
            populater.populateId(id,idMeta);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public IdPopulator getPopulater() {
        return populater;
    }

    public void setPopulater(IdPopulator populater) {
        this.populater = populater;
    }
}
