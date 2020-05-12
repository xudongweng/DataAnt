/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataant.controller;

import com.dataant.helper.MySQLHelper;
import com.dataant.model.LoadMySQLProperity;
import com.dataant.model.LoadTableProperity;
import com.dataant.model.MySQLObject;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;

/**
 *
 * @author sheriff
 */
public class ExecuteSQLController {
    private Logger log=null;
    private MySQLObject mysqlobj=null;
    public ExecuteSQLController(){
        this.log=Logger.getLogger(ExecuteSQLController.class);
        mysqlobj=LoadMySQLProperity.getMySQLObj();
    }
    public void multipleTreadExecute(List<String> sqlList){
        MySQLHelper mysqlhelper=new MySQLHelper();
        mysqlhelper.setURL(mysqlobj.getHost(), mysqlobj.getPort(), mysqlobj.getUser(), mysqlobj.getPassword(),mysqlobj.getCharacterEncoding());
        ExecutorService poolbk = Executors.newFixedThreadPool(LoadTableProperity.getThreads());
        LoadMySQLProperity.setMaxLine(sqlList.size());
        LoadMySQLProperity.setMinLine(sqlList.size());
        int i=sqlList.size();

        for(int j=0;j<i;j++){
            InsertThread it=new InsertThread(mysqlhelper,sqlList.get(j));
            poolbk.execute(it);
        }
        poolbk.shutdown();
        try{
            poolbk.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);//等待线程池结束，注销将不等待
        }catch(InterruptedException e){
            log.error(e.toString());
        }
    }
}
