package com.mark;


import com.mark.service.IdService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("META-INFO/spring/client.xml");
        context.start();
        IdService idService= (IdService) context.getBean("idService");
        System.out.println(idService.getId());
    }
}
