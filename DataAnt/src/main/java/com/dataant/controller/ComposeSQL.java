/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataant.controller;

import com.dataant.model.DTableObject;
import com.dataant.model.STableObject;
import java.util.List;

/**
 *
 * @author sheriff
 */
public class ComposeSQL {
    private String sqlmain="";
    private String sqlType1="insert ignore into";
    private String sqlType2="replace into";
    
    private int maxid=1;
    private int minid=1;
    
    private List baseList=null;
    private StringBuilder sbInsert=null;
    
    public ComposeSQL(int maxid,int minid){
        this.maxid=maxid;
        this.minid=minid;
    }
    
    public void construct(int type){
        if(type==1)
            sqlmain=sqlType1;
        else
            sqlmain=sqlType2;
        DTableObject dt=LoadTableProperity.getDT();
        STableObject st=LoadTableProperity.getST();
        
        sqlmain=sqlmain.concat(" ").concat(dt.getDDB()).concat(".").concat(dt.getDTable()).concat("(").concat(dt.gerDCols()).concat(") ")
                .concat("select ").concat(st.getSCols()).concat(" from ").concat(st.getSDB()).concat(".").concat(st.getSTable());
        //System.out.println(sqlmain);
        int limit=LoadTableProperity.getPerLimit();
        StringBuilder sbSQLChanger=new StringBuilder();
        for(int i=minid;i-maxid<=0;i=i+limit){
            sbSQLChanger.append(sqlmain).append(" where ").append(LoadTableProperity.getPK()).append(" between ").append(i).append(" and ").append(i+limit-1);
            System.out.println(sbSQLChanger.toString());
            sbSQLChanger.delete(0, sbSQLChanger.length());
        }
    }
}
