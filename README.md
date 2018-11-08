# unique-mark

unique-mark是一个高性能高可用的发号器



#### unique-mark使用方式:

##### 单机发布模式：

1. netty发布方式，在终端直接运行 java -jar unique-mark-netty.jar可以启动netty服务
2. springboot发布模式，在终端运行javaJava -jar unique-mark-springboot.jar可以启动springboot服务.

##### zk发布模式

	zk发布模式是把服务注册到zk上，在zk的基础之上获取本机的机器号，通过zk的协调，让每台机器都拥有一个唯一标识来保证生成的ID不会重复。

	在zk发布模式下，机器每3秒钟会向zk发送一次本地的时间信息，如果机器出现时间回调，那么则会抛出异常，终止ID的生成。

	使用方式，修改apllication.properties：

	

```properties
zk=192.168.1.1，192.168.1.2，192.168.1.3
```

		

##### dubbo发布模式式

	dubbo发布模式是把服务依赖dubbo发布，通过dubbo，以集群的方式提供服务

	servier端xml设置：

	

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:dubbo="http://code.alibabatech.com/schema/dubbo"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://code.alibabatech.com/schema/dubbo
        http://code.alibabatech.com/schema/dubbo/dubbo.xsd" default-autowire="byName">

    <import resource="classpath*:spring/serviceimp.xml" />

    <dubbo:application name="IdService" owner="Mark"/>

    <dubbo:registry protocol="zookeeper"
                    address="192.168.80.128:2181,192.168.80.129:2181,192.168.80.130:2181"
                    group="idservice-dev"/>


    <dubbo:protocol host="192.168.80.1"
                    name="dubbo"
                    port="20881"/>

    <dubbo:service interface="com.mark.service.IdService" ref="idService"/>

</beans>
```



application.properties

```properties
zk=192.168.80.128:2181,192.168.80.129:2181,192.168.80.130:2181
```



client端xml设置

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:dubbo="http://code.alibabatech.com/schema/dubbo"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context-3.0.xsd
        http://code.alibabatech.com/schema/dubbo
        http://code.alibabatech.com/schema/dubbo/dubbo.xsd" default-autowire="byName">

    <dubbo:application name="IdService" owner="Mark"/>

    <dubbo:reference id="idService" interface="com.mark.service.IdService"/>

    <dubbo:registry protocol="zookeeper" group="idservice-dev" address="192.168.80.128:2181,192.168.80.129:2181,192.168.80.130:2181"/>

</beans>
```



#### unique-mark API

restful接口   http://地址/getid()

			http://地址/parse?name=id   (id为一个long，该方法是解析一个long类型的id，把他转化为可读的		 形式)



			long getid()

			String parse(long id)



#### unique-mark的高可用

	unique-mark在设置了zk的情况下，会把自己注册到zk上，并会在zk上持久化存储，然后会通过zk拿到一个属于自己的唯一机器ID，再拿到机器ID之后，会把这个机器ID持久化到本地。

	如果下次启动的时候，连接不上zk，机器就会去读取本地持久化的ID,unique-mark对zk是弱依赖的，即使是zk挂掉了，依然不会影响使用。



#### unique-mark application.properties参数说明

```properties
timetype=       值可为 second ，millisencond，默认为seond
version=		默认为 0 
machineid=		默认为1022，如果设置了zk，则从zk获取
gentype=		默认为0
convertor=		默认为defualt
populater=		值可为 atimic defualt nolock reentrantlock,默认为default
zk=				默认为 N/A ，当职位N/A的时候，机器ID则以machineid设置的为准
```





##### timetype

	**Id说明 ：**

	**seond模式（可用34年，每秒可生成1048576个ID，最多支持1024台机器）**

| 字段 | 版本 | 秒级时间 | 序列号 | 生成方式 | 机器ID | 类型 |
| ---- | ---- | -------- | ------ | -------- | ------ | ---- |
| 位数 | 63   | 33~62    | 13~32  | 11，12   | 1~10   | 0    |

 

	**milliseond模式（可用34年，每毫秒可用产生1024个ID）**

| 字段 | 版本 | 豪秒级时间 | 序列号 | 生成方式 | 机器ID | 类型 |
| ---- | ---- | ---------- | ------ | -------- | ------ | ---- |
| 位数 | 63   | 23~62      | 13~22  | 11，12   | 1~10   | 0    |

 	当为秒级模式时，ID在秒级有序

	当为毫秒级模式时，ID在毫秒级有序



##### populater

popilater是ID生成的核心算法，unique-mark默认提供了4种

1. defult 使用了synchronized，在JDK1.8中，这种是默认提供的性能最好的一种
2. nolock使用了ThreadLocal,unique-mark属于CPU密集型的应用，因此在cpu核数=线程数的时候效率应该是最高的，于是用一个线程池去处理请求，记录每个线程的ID，为每一个线程设置一个步长，比如说线程1就生成1，8，15，22这些ID，通过这种方式实现无锁化。但是实际测试的时候，这种方式的性能并不高（不推荐使用）
3. atomic使用了自旋锁，性能不错，但是对CPU的消耗比较大
4. reentrantlock使用了重入锁



