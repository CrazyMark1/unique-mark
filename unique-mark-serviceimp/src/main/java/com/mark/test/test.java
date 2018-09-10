package com.mark;

import com.mark.beans.Id;
import com.mark.service.IdService;
import com.mark.serviceimp.IdServiceImp;
import com.mark.serviceimp.beans.IdMeta;
import com.mark.serviceimp.beans.IdMetaFactory;
import com.mark.serviceimp.beans.TimeType;
import com.mark.serviceimp.convertor.IdConvertor;
import com.mark.serviceimp.convertor.IdDefaultConvertor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author: 帅气的Mark
 * @Description: Mark行行好，给点注释吧！
 * @Date: Create in 2018/9/4 13:57
 * @QQ: 85104982
 */
public class test {
    public static void main(String[] args) {
//        IdMeta idMata= IdMetaFactory.getIdMata(TimeType.SECOND);
//        IdConvertor convertor=new IdDefaultConvertor(idMata);
//        //
//        Id id=new Id(0,74790400,589824,3,1022,0);
//        convertor.convert(id);
//
////        convertor.convert(642444653236330492L);
//        convertor.convert(642444648941363196L);
//        System.out.println(System.currentTimeMillis());
        ApplicationContext context=new ClassPathXmlApplicationContext("spring/serviceimp.xml");
        IdService idService= (IdService) context.getBean("idService");
        long id=idService.getId();
        System.out.println(id);
        Id ids=idService.parseId(id);
        System.out.println(ids);

    }
}
