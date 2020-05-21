/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataant.controller;

import com.dataant.model.LoadTableProperity;
import com.dataant.model.DTableObject;
import com.dataant.model.STableObject;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author sheriff
 */
public class ComposeSQL {
    private String sqlmain="";
    
    private int maxid=1;
    private int minid=1;
    
    private Queue<String> sqlQueue = new LinkedList<>();
    
    public ComposeSQL(int maxid,int minid){
        this.maxid=maxid;
        this.minid=minid;
    }
    
    public void construct(int type){
        if(type==1)
            sqlmain="INSERT LOW_PRIORITY IGNORE INTO";
        else
            sqlmain="REPLACE LOW_PRIORITY INTO";
        
        DTableObject dt=LoadTableProperity.getDT();
        STableObject st=LoadTableProperity.getST();
        
        sqlmain=sqlmain.concat(" ").concat(dt.getDDB()).concat(".").concat(dt.getDTable()).concat("(").concat(dt.gerDCols()).concat(") ")
                .concat("SELECT ").concat(st.getSCols()).concat(" FROM ").concat(st.getSDB()).concat(".").concat(st.getSTable());
        //System.out.println(sqlmain);
        int limit=LoadTableProperity.getPerLimit();
        StringBuilder sbSQLChanger=new StringBuilder();
        for(int i=minid;i-maxid<=0;i=i+limit){
            sbSQLChanger.append(sqlmain).append(" WHERE ").append(LoadTableProperity.getPK()).append(" BETWEEN ").append(i).append(" AND ").append(i+limit-1).append(" LOCK IN SHARE MODE");
            //System.out.println(sbSQLChanger.toString());
            sqlQueue.offer(sbSQLChanger.toString());
            sbSQLChanger.delete(0, sbSQLChanger.length());
        }
    }
    
    public Queue<String> getSQLQueue(){
        return this.sqlQueue;
    }
}
