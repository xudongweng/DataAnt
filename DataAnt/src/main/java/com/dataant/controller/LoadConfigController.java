/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataant.controller;

import com.dataant.helper.MySQLHelper;
import com.dataant.model.DTableObject;
import com.dataant.model.LoadMySQLProperity;
import com.dataant.model.LoadTableProperity;
import com.dataant.model.MySQLObject;
import com.dataant.model.STableObject;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.apache.log4j.Logger;

/**
 *
 * @author sheriff
 */
public class LoadConfigController {
    private final Logger log=Logger.getLogger(LoadConfigController.class);
    private final Properties prop = new Properties();
    private MySQLHelper mysqlhelper=null;
    public int loadFile(){
        File file = new File("config.properties");
        if(!file.exists()){
            log.error("File "+file.getAbsolutePath()+"config.properties is not exist.");
            return 0;
        }
        //加载配置
        try{
            prop.load(new FileInputStream("config.properties"));
        }catch(IOException e){
            log.error(e.toString());
            return 0;
        }
        return 1;
    }
    
    public int setMySQLData(){
        try{
            MySQLObject mysqlobj=new MySQLObject(prop.getProperty("host"),prop.getProperty("user"),
                    prop.getProperty("password"),prop.getProperty("port"),prop.getProperty("characterEncoding"));
            LoadMySQLProperity.setMySQLObj(mysqlobj);
        }catch(NumberFormatException e){
            log.error(e.toString()+ " host or user or password or port or characterEncoding is empty.");
            return 0;
        }
        DTableObject dobj;
        try{
            dobj=new DTableObject(prop.getProperty("dDB"),prop.getProperty("dTable"),prop.getProperty("dCol"));
        }catch(NumberFormatException e){
            log.error(e.toString()+ " dDB or dTable or dCol is empty.");
            return 0;
        }
        STableObject sobj;
        try{
            sobj=new STableObject(prop.getProperty("sDB"),prop.getProperty("sTable"),prop.getProperty("sCol"));
        }catch(NumberFormatException e){
            log.error(e.toString()+ " sDB or sTable or sCol is empty.");
            return 0;
        }
        switch(LoadTableProperity.setTable(dobj, sobj)){
            case -1:log.error("dDB is empty."); return 0;
            case -2:log.error("dTable is empty."); return 0;
            case -3:log.error("dCol is empty."); return 0;
            case -4:log.error("sDB is empty."); return 0;
            case -5:log.error("sTable is empty."); return 0;
            case -6:log.error("sCol is empty."); return 0;
            case -7:log.error("dCol can't contain '(' or ')'."); return 0;
            case -8:log.error("dCol is inconformity with sCol."); return 0;
        }
        return 1;
    }
    
    public void setSQLData(){
        try{
            LoadTableProperity.setSqlType(Integer.parseInt(prop.getProperty("sqlType")));
        }catch(NumberFormatException e){
            log.error(e.toString()+ " sqlType is empty.");
        }
        try{
            LoadTableProperity.setPerLimit(Integer.parseInt(prop.getProperty("limit")));
        }catch(NumberFormatException e){
            log.error(e.toString()+ " limit is empty.");
        }
    }
    
    public int setPK(){
        this.mysqlhelper=new MySQLHelper();
        this.mysqlhelper.setURL(LoadMySQLProperity.getMySQLObj().getHost(), LoadMySQLProperity.getMySQLObj().getPort(), LoadMySQLProperity.getMySQLObj().getUser()
                , LoadMySQLProperity.getMySQLObj().getPassword(),LoadMySQLProperity.getMySQLObj().getCharacterEncoding());
        //List cols=this.mysqlhelper.queryAll("SHOW COLUMNS FROM "+LoadTableProperity.getST().getSDB()+"."+LoadTableProperity.getST().getSTable());
        String valProperTable=this.mysqlhelper.querySingleleCell("SHOW CREATE TABLE "+LoadTableProperity.getST().getSDB()+"."+LoadTableProperity.getST().getSTable(), 0, 2);
        if (valProperTable==null) return 0;
        int beginidx=valProperTable.indexOf("PRIMARY KEY (`");//判断表是否包含主键
        int endidx=-1;
        if(beginidx<=0){
            log.error("Table "+LoadTableProperity.getST().getSTable()+" doesn't exist primary key.");
            return 0;
        }else
            endidx=valProperTable.indexOf("`),",beginidx);
        String pk=valProperTable.substring(beginidx+14, endidx);
        if(valProperTable.substring(valProperTable.indexOf("`"+pk+"` ")+pk.length()+3,valProperTable.indexOf("`"+pk+"` ")+pk.length()+13).contains("int")){
            LoadTableProperity.setPK(pk);//设置迁移主键
            return 1;
        }
        log.error("Table "+LoadTableProperity.getST().getSTable()+" isn't Integer.");

        return 0;
    }
    
    public List setPKRange(){
        return mysqlhelper.queryAll("SELECT MAX("+LoadTableProperity.getPK() +") AS max,MIN("+LoadTableProperity.getPK()+") AS min FROM "+LoadTableProperity.getST().getSDB()+"."+LoadTableProperity.getST().getSTable());
    }
}
