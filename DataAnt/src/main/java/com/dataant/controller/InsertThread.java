/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataant.controller;

import com.dataant.helper.MySQLHelper;
import com.dataant.model.ExecuteObject;
import com.dataant.model.LoadMySQLProperity;
import org.apache.log4j.Logger;

/**
 *
 * @author sheriff
 */
public class InsertThread extends Thread{
    private Logger log=Logger.getLogger(InsertThread.class);
    private MySQLHelper mysqlhelper=null;
    private String sql;
    private ExecuteObject execobj=null;
    public InsertThread(MySQLHelper mysqlhelper,String sql){
        this.sql=sql;
        this.mysqlhelper=mysqlhelper;
        this.execobj=new ExecuteObject();
        //this.execobj.setNumber(summary, noexec);
    }
    
    @Override
    public void run() {
        log.info(" execute startup -- "+this.sql);
        mysqlhelper.execSQL(sql);
        log.info(" execute finished -- "+this.sql);
        LoadMySQLProperity.setMinLine(LoadMySQLProperity.getMinLine()-1);
        this.execobj.setNumber(LoadMySQLProperity.getMaxLine(), LoadMySQLProperity.getMinLine());
        log.info(" finished -- "+this.execobj.execRate());
    }
}
