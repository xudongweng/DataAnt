/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataant;

import com.dataant.controller.ComposeSQL;
import com.dataant.controller.ExecuteSQLController;
import com.dataant.controller.LoadConfigFile;
import com.dataant.model.LoadMySQLProperity;
import com.dataant.model.LoadTableProperity;
import com.dataant.helper.MySQLHelper;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import org.apache.log4j.Logger;

/**
 *
 * @author sheriff
 */
public class TransferData {
    public static void main(String[] args){
        Logger log=Logger.getLogger(TransferData.class);

        LoadConfigFile lcf=new LoadConfigFile();
        if(lcf.loadFile()==0)return;
        if(lcf.setMySQLData()==0)return;
        lcf.setSQLData();


        MySQLHelper mysqlhelper=new MySQLHelper();
        mysqlhelper.setURL(LoadMySQLProperity.getMySQLObj().getHost(), LoadMySQLProperity.getMySQLObj().getPort(), LoadMySQLProperity.getMySQLObj().getUser()
                , LoadMySQLProperity.getMySQLObj().getPassword(),LoadMySQLProperity.getMySQLObj().getCharacterEncoding());
        List cols=mysqlhelper.queryAll("SHOW COLUMNS FROM "+LoadTableProperity.getST().getSDB()+"."+LoadTableProperity.getST().getSTable());
        if (cols==null) return;

        for(int i=0;i<cols.size();i++){
            //System.out.println(cols.get(i));
            Map<String,Object> tabledesc=(Map<String,Object>) cols.get(i);
            //System.out.println(tabledesc.get("Key"));
            if(tabledesc.get("Key").equals("PRI")){
                if(tabledesc.get("Type").toString().contains("int")){
                    LoadTableProperity.setPK(tabledesc.get("Field").toString());
                    //System.out.println(LoadTableProperity.getPK());
                    break;
                }else{
                    log.error("Table "+LoadTableProperity.getST().getSTable()+" doesn't exist primary key of Integer.");
                    return;
                }
            }
        }
        if(LoadTableProperity.getPK().equals("")){
            log.error("Table "+LoadTableProperity.getST().getSTable()+" doesn't exist primary key.");
            return;
        }
        List range=mysqlhelper.queryAll("SELECT MAX("+LoadTableProperity.getPK() +") AS max,MIN("+LoadTableProperity.getPK()+") AS min FROM "+LoadTableProperity.getST().getSDB()+"."+LoadTableProperity.getST().getSTable());
        if(range==null) return;
        Map<String,Object> idrange=(Map<String,Object>) range.get(0);
        //idrange.get("max")
        ComposeSQL csql=new ComposeSQL(Integer.parseInt(idrange.get("max").toString()),Integer.parseInt(idrange.get("min").toString()));
        csql.construct(LoadTableProperity.getSqlType());
        Queue<String> sqlQueue=csql.getSQLQueue();
        ExecuteSQLController exSQL=new ExecuteSQLController();
        exSQL.execute(sqlQueue);
    }
}
