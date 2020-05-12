/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataant.model;


/**
 *
 * @author sheriff
 */
public class LoadMySQLProperity {
    private static MySQLObject mysqlobj=null;
    private static SQLArrObject sqlobj=null;
    private static long maxLine=0;
    private static long minLine=0;
    public static MySQLObject getMySQLObj(){
        return mysqlobj;
    }
    public static void setMySQLObj(MySQLObject mo){
        mysqlobj=mo;
    }
    
    public static SQLArrObject getSQLArrObj(){
        return sqlobj;
    }
    
    public static void setSQLArrObj(SQLArrObject sao){
        sqlobj=sao;
    }
    
    public static void setMaxLine(long maxLine){
        LoadMySQLProperity.maxLine=maxLine;
    }
    
    public static long getMaxLine(){
        return LoadMySQLProperity.maxLine;
    }
    
    public static void setMinLine(long minLine){
        LoadMySQLProperity.minLine=minLine;
    }
    
    public static long getMinLine(){
        return LoadMySQLProperity.minLine;
    }
}
