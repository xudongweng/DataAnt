/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataant.controller;

import com.dataant.helper.MySQLHelper;
import com.dataant.model.ExecuteObject;
import com.dataant.model.LoadMySQLProperity;
import com.dataant.model.MySQLObject;
import java.util.Queue;
import org.apache.log4j.Logger;

/**
 *
 * @author sheriff
 */
public class ExecuteSQLController {
    private Logger log=null;
    private MySQLObject mysqlobj=null;
    private ExecuteObject execobj=null;
    public ExecuteSQLController(){
        this.log=Logger.getLogger(ExecuteSQLController.class);
        mysqlobj=LoadMySQLProperity.getMySQLObj();
        this.execobj=new ExecuteObject();
    }
    public void execute( Queue<String> sqlQueue){
        MySQLHelper mysqlhelper=new MySQLHelper();
        mysqlhelper.setURL(mysqlobj.getHost(), mysqlobj.getPort(), mysqlobj.getUser(), mysqlobj.getPassword(),mysqlobj.getCharacterEncoding());
        LoadMySQLProperity.setMaxLine(sqlQueue.size());
        LoadMySQLProperity.setMinLine(sqlQueue.size());

        
        while(sqlQueue.size()>0){
            String sql=sqlQueue.poll();//每次取出第一条SQL，并删除取出的第一条SQL.
            java.sql.Date currentDate1 = new java.sql.Date(System.currentTimeMillis()); //计算执行时间
            long longtime1=currentDate1.getTime();
            log.info(" execute startup -- "+sql);
            boolean bool=mysqlhelper.execSQL(sql);
            log.info(" execute finished -- "+sql);
            java.sql.Date currentDate2 = new java.sql.Date(System.currentTimeMillis()); 
            long longtime2=currentDate2.getTime();
            System.out.println("remain -- "+String.valueOf((longtime2-longtime1)*(sqlQueue.size()-1)/1000) + "s");//显示预计执行所需要的时间
            if(bool){
                LoadMySQLProperity.setMinLine(LoadMySQLProperity.getMinLine()-1);
                this.execobj.setNumber(LoadMySQLProperity.getMaxLine(), LoadMySQLProperity.getMinLine());
                log.info(" finished -- "+this.execobj.execRate());
            }else{
                log.error(" execute failed -- "+sql);
                sqlQueue.offer(sql);
            }
        }
       
    }
}
