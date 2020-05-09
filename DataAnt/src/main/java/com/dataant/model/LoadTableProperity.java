/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataant.model;

import com.dataant.model.DTableObject;
import com.dataant.model.STableObject;

/**
 *
 * @author sheriff
 */
public class LoadTableProperity {
    private static DTableObject dtableobj=null;
    private static STableObject stableobj=null;
    
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
    
    public static int setTable(DTableObject dobj,STableObject sobj){
        if(dobj.getDDB().trim().equals("")) return -1;
        if(dobj.getDTable().trim().equals("")) return -2;
        if(dobj.gerDCols().trim().equals("")) return -3;
        if(sobj.getSDB().trim().equals("")) return -4;
        if(sobj.getSTable().trim().equals("")) return -5;
        if(sobj.getSCols().trim().equals("")) return -6;
        //判断dcol与scol列的数量是否一致
        String[] dcols=dobj.gerDCols().split(",");
        String[] scols=sobj.getSCols().split(",");
        if(dcols.length!=scols.length) return -7;

        dtableobj=dobj;
        stableobj=sobj;
        
        return 1;
    }
    
    public static DTableObject getDT(){
        return dtableobj;
    }
    public static STableObject getST(){
        return stableobj;
    }
}
