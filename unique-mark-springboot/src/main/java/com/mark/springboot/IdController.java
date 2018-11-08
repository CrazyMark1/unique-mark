package com.mark.springboot;

import com.mark.service.IdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: 帅气的Mark
 * @Description: Mark行行好，给点注释吧！
 * @Date: Create in 2018/9/7 17:53
 * @QQ: 85104982
 */
@RestController
public class IdController {
    IdService idService;

    public IdController() {
        ApplicationContext context=new ClassPathXmlApplicationContext("META-INF/spring/provider.xml");
        idService= (IdService) context.getBean("idService");
    }

    @GetMapping("/getid")
    public long getId(){
        return idService.getId();
    }

    @GetMapping("/parse")
    public String parse(@RequestParam long id){
        return idService.parseId(id).toString();
    }
}
