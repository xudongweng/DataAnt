/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataant;

import com.dataant.controller.LoadMySQLProperity;
import com.dataant.controller.LoadTableProperity;
import com.dataant.model.DTableObj;
import com.dataant.model.MySQLObj;
import com.dataant.model.STableObj;
import java.io.FileInputStream;
import java.io.IOException;
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
            Properties prop = new Properties();
            prop.load(new FileInputStream("D:\\config.properties"));

            MySQLObj mysqlobj=new MySQLObj(prop.getProperty("host"),prop.getProperty("user"),
                    prop.getProperty("password"),prop.getProperty("port"),prop.getProperty("unicode"));
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
            LoadTableProperity.setSqlType(Integer.parseInt(prop.getProperty("threads")));
            LoadTableProperity.setThreads(Integer.parseInt(prop.getProperty("sqlType")));
        
        }catch(IOException e){
            log.error(e.toString());
        }
    }
}
