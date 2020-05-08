/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataant.controller;

import com.dataant.model.DTableObj;
import com.dataant.model.STableObj;

/**
 *
 * @author sheriff
 */
public class LoadTableProperity {
    private static DTableObj dtableobj=null;
    private static STableObj stableobj=null;
    
    private static int threads=1;
    private static int sqlType=1;
    private static String pk;
    private static int perlimit=100;
    
    public static void setPerLimit(int perlimit){
        LoadTableProperity.perlimit=perlimit;
    }
    
    public static int getPerLimit(){
        return perlimit;
    }
    
    public static void setThreads(int threads){
        LoadTableProperity.threads=threads;
    }
    
    public static int getThreads(){
        return threads;
    }
    
    public static void setSqlType(int sqlType){
        LoadTableProperity.sqlType=sqlType;
    }
    
    public static int getSqlType(){
        return sqlType;
    }
    
    public static void setPK(String pk){
        LoadTableProperity.pk=pk;
    }
    
    public static String getPK(){
        return pk;
    }
    
    public static int setTable(DTableObj dobj,STableObj sobj){
        if(dobj.getDDB().trim().equals("")) return -1;
        if(dobj.getDTable().trim().equals("")) return -2;
        if(dobj.gerDCol().trim().equals("")) return -3;
        if(sobj.getSDB().trim().equals("")) return -4;
        if(sobj.getSTable().trim().equals("")) return -5;
        if(sobj.getSCol().trim().equals("")) return -6;
        //判断dcol与scol列的数量是否一致
        String[] dcol=dobj.gerDCol().split(",");
        String[] scol=sobj.getSCol().split(",");
        if(dcol.length!=scol.length) return -7;

        dtableobj=dobj;
        stableobj=sobj;
        
        return 1;
    }
    
    public static DTableObj getDT(){
        return dtableobj;
    }
    public static STableObj getST(){
        return stableobj;
    }
}
