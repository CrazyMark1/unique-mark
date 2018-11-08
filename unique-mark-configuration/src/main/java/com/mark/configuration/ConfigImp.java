package com.mark.configuration;


import com.mark.configuration.beans.ConvertorType;
import com.mark.configuration.beans.PopulaterType;
import com.mark.configuration.beans.TimeType;
import com.mark.configuration.machineidprovider.MachineDefaultProvider;
import com.mark.configuration.machineidprovider.zk.ZkIdProvider;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

/**
 * @Author: 帅气的Mark
 * @Description: Mark行行好，给点注释吧！
 * @Date: Create in 2018/9/5 16:44
 * @QQ: 85104982
 */
public class ConfigImp {
    private TimeType timeType;
    private long version;
    private long machineId;
    //保留字段  中心服务器方式获取  暂未实现
    private long tripartiteMachineId;
    //保留字段  生成方式
    private long genType;
    private ConvertorType convertorType;
    private PopulaterType populaterType;
    private String zk;

    public ConfigImp() {
        init();
        verify();
    }

    private void init() {
        Properties properties=loadProp();
        if(properties==null){
            timeType=TimeType.SECOND;
            version=0L;
            machineId=1022L;
            tripartiteMachineId=-1;
            genType=0L;
            convertorType=ConvertorType.DEFAULT;
            populaterType=PopulaterType.DEFAULT;
            zk="N/A";
        }
        else
            inject(properties);
    }

    private void verify(){
        if (zk.equalsIgnoreCase("N/A"))
            machineId=new MachineDefaultProvider(this).getMachineId();
        else
            machineId=new ZkIdProvider(this).getMachineId();
    }

    private void inject(Properties properties) {
        timeType=Optional.ofNullable(properties.getProperty("timetype"))
                .map(TimeType::parse)
                .orElse(TimeType.SECOND);
        version=Optional.ofNullable(properties.getProperty("version"))
                .map(v->v=(v==null||v.equals(""))?"0":v)
                .map(Long::parseLong)
                .filter(version -> version==0 || version==1)
                .orElse(0L);
        machineId=Optional.ofNullable(properties.getProperty("machineid"))
                .map(id->id=(id==null||id.equals(""))?"1022":id)
                .map(Long::parseLong)
                .filter(id -> id>923 && id<1024)
                .orElse(1022L);
        tripartiteMachineId=Optional.ofNullable(properties.getProperty("tripartitemachineid"))
                .map(id->id=(id==null||id.equals(""))?"-1":id)
                .map(Long::parseLong)
                .filter(id -> id>=0 && id<=923)
                .orElse(-1L);
        genType=Optional.ofNullable(properties.getProperty("gentype"))
                .map(type->type=(type==null||type.equals(""))?"0":type)
                .map(Long::parseLong)
                .filter(type -> type>=0 && type <=3)
                .orElse(0L);
        convertorType=Optional.ofNullable(properties.getProperty("convertor"))
                .map(ConvertorType::parse)
                .orElse(ConvertorType.DEFAULT);
        populaterType=Optional.ofNullable(properties.getProperty("populater"))
                .map(PopulaterType::parse)
                .orElse(PopulaterType.DEFAULT);
        zk=Optional.ofNullable(properties.getProperty("zk"))
                .filter(zk->!zk.equals(""))
                .orElse("N/A");

    }

    private Properties loadProp() {
//        String path=this.getClass().getResource("/").getPath()+"application.properties";
//        Properties properties=new Properties();
//        try ( FileInputStream inputFile = new FileInputStream(path)){
//            properties.load(inputFile);
//        } catch (FileNotFoundException e) {
//            return null;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        Properties p=new Properties();
        InputStream is=this.getClass().getResourceAsStream("/application.properties");
        if (is !=null){
            try {
                p.load(is);
                return p;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
            finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        else
            return null;
    }

    public TimeType getTimeType() {
        return timeType;
    }

    public void setTimeType(TimeType timeType) {
        this.timeType = timeType;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public long getMachineId() {
        return machineId;
    }

    public void setMachineId(long machineId) {
        this.machineId = machineId;
    }

    public long getTripartiteMachineId() {
        return tripartiteMachineId;
    }

    public void setTripartiteMachineId(long tripartiteMachineId) {
        this.tripartiteMachineId = tripartiteMachineId;
    }

    public long getGenType() {
        return genType;
    }

    public void setGenType(long genType) {
        this.genType = genType;
    }

    public ConvertorType getConvertorType() {
        return convertorType;
    }

    public void setConvertorType(ConvertorType convertorType) {
        this.convertorType = convertorType;
    }

    public PopulaterType getPopulaterType() {
        return populaterType;
    }

    public void setPopulaterType(PopulaterType populaterType) {
        this.populaterType = populaterType;
    }

    public String getZk() {
        return zk;
    }

    public void setZk(String zk) {
        this.zk = zk;
    }
}
