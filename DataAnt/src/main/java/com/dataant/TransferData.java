/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataant;

import com.dataant.controller.LoadMySQLProperity;
import com.dataant.controller.LoadTableProperity;
import com.dataant.helper.MySQLHelper;
import com.dataant.model.DTableObj;
import com.dataant.model.MySQLObj;
import com.dataant.model.STableObj;
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
public class TransferData {
    public static void main(String[] args){
        Logger log=Logger.getLogger(TransferData.class);
        try{
            //加载配置
            Properties prop = new Properties();
            prop.load(new FileInputStream("D:\\config.properties"));

            MySQLObj mysqlobj=new MySQLObj(prop.getProperty("host"),prop.getProperty("user"),
                    prop.getProperty("password"),prop.getProperty("port"),prop.getProperty("characterEncoding"));
            LoadMySQLProperity.setMySQLObj(mysqlobj);
            
            DTableObj dobj=new DTableObj(prop.getProperty("dDB"),prop.getProperty("dTable"),prop.getProperty("dCol"));
            STableObj sobj=new STableObj(prop.getProperty("sDB"),prop.getProperty("sTable"),prop.getProperty("sCol"));
            switch(LoadTableProperity.setTable(dobj, sobj)){
                case -1:log.error("dDB is empty."); return;
                case -2:log.error("dTable is empty."); return;
                case -3:log.error("dCol is empty."); return;
                case -4:log.error("sDB is empty."); return;
                case -5:log.error("sTable is empty."); return;
                case -6:log.error("sCol is empty."); return;
                case -7:log.error("dCol is inconformity with sCol."); return;
            }
            LoadTableProperity.setSqlType(Integer.parseInt(prop.getProperty("sqlType")));
            LoadTableProperity.setThreads(Integer.parseInt(prop.getProperty("threads")));
            LoadTableProperity.setPerLimit(Integer.parseInt(prop.getProperty("limit")));
            
            MySQLHelper mysqlhelper=new MySQLHelper();
            mysqlhelper.setURL(mysqlobj.getHost(), mysqlobj.getPort(), mysqlobj.getUser(), mysqlobj.getPassword(),mysqlobj.getCharacterEncoding());
            List cols=mysqlhelper.queryAll("show columns from "+sobj.getSDB()+"."+sobj.getSTable());
            if (cols==null) return;
            
            for(int i=0;i<cols.size();i++){
                //System.out.println(cols.get(i));
                Map<String,Object> tabledesc=(Map<String,Object>) cols.get(i);
                System.out.println(tabledesc.get("Key"));
                if(tabledesc.get("Key").equals("PRI")){
                    if(tabledesc.get("Type").toString().contains("int")){
                        LoadTableProperity.setPK(tabledesc.get("Field").toString());
                        //System.out.println(LoadTableProperity.getPK());
                        break;
                    }else{
                        log.error("Table "+sobj.getSTable()+" doesn't exist primary key of Integer.");
                        return;
                    }
                }
            }
            if(LoadTableProperity.getPK().equals("")){
                log.error("Table "+sobj.getSTable()+" doesn't exist primary key.");
                return;
            }
            
        }catch(IOException e){
            log.error(e.toString());
        }
    }
}
