/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataant;

import com.dataant.controller.ComposeSQL;
import com.dataant.controller.ExecuteSQLController;
import com.dataant.controller.LoadConfigController;
import com.dataant.model.LoadTableProperity;
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

        LoadConfigController lcf=new LoadConfigController();
        if(lcf.loadFile()==0)return;
        if(lcf.setMySQLData()==0)return;
        lcf.setSQLData();
        if(lcf.setPK()==0)return;
        List range=lcf.setPKRange();
        
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
