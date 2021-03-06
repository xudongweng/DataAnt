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
public class LoadTableProperity {
    private static DTableObject dtableobj=null;
    private static STableObject stableobj=null;
    
    private static int sqlType=1;
    private static String pk;
    private static int perlimit=100;
    
    public static void setPerLimit(int perlimit){
        LoadTableProperity.perlimit=perlimit;
    }
    
    public static int getPerLimit(){
        return perlimit;
    }
    
    public static void setSqlType(int sqlType){
        LoadTableProperity.sqlType=sqlType;//sqlType 1=insert into  2=replace into
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
    
    public static int setTableObj(DTableObject dobj,STableObject sobj){
        if(dobj.getDDB().trim().equals("")) return -1;
        if(dobj.getDTable().trim().equals("")) return -2;
        if(dobj.getDCols().trim().equals("")) return -3;
        if(sobj.getSDB().trim().equals("")) return -4;
        if(sobj.getSTable().trim().equals("")) return -5;
        if(sobj.getSCols().trim().equals("")) return -6;
        //用逗号分隔列判断dcol与scol列的数量是否一致
        String[] dcols=dobj.getDCols().split(",");
        String[] scols=sobj.getSCols().split(",");
        int d=dcols.length;
        int s=scols.length;
        
        
        if(s!=d) {
            if(dobj.getDCols().indexOf("(")>0 || dobj.getDCols().indexOf(")")>0||
                    dobj.getDCols().indexOf("+")>0|| dobj.getDCols().indexOf("-")>0|| 
                    dobj.getDCols().indexOf("*")>0|| dobj.getDCols().indexOf("/")>0) return -7;

            int schars=sobj.getSCols().length();
            int bracket=0;
            int i=sobj.getSCols().indexOf("(");
            for(;i<schars&&i>-1;i++){
                if(sobj.getSCols().charAt(i)=='('){
                    bracket++;
                }else if(sobj.getSCols().charAt(i)==')'){
                    bracket--;
                }else if(sobj.getSCols().charAt(i)==','&&bracket>0){
                    s--;
                }
            }
            if(s!=d)
                return -8;
        }

        dtableobj=dobj;
        stableobj=sobj;
        
        return 1;
    }
    
    public static int checkCustomedKey(DTableObject dobj,STableObject sobj){
        if(dobj.getDCustomedKey().trim().equals("")) return -1;
        if(sobj.getSCustomedKey().trim().equals("")) return -2;
        //用逗号分隔列判断dcol与scol列的数量是否一致
        String[] dcusKey=dobj.getDCustomedKey().split(",");
        String[] scusKey=sobj.getSCustomedKey().split(",");
        
        int d=dcusKey.length;
        int s=scusKey.length;
        if(s!=d) {
            if(dobj.getDCustomedKey().indexOf("(")>0 || dobj.getDCustomedKey().indexOf(")")>0||
                    dobj.getDCustomedKey().indexOf("+")>0|| dobj.getDCustomedKey().indexOf("-")>0|| 
                    dobj.getDCustomedKey().indexOf("*")>0|| dobj.getDCustomedKey().indexOf("/")>0) return -7;

            int schars=sobj.getSCustomedKey().length();
            int bracket=0;
            int i=sobj.getSCustomedKey().indexOf("(");
            for(;i<schars&&i>-1;i++){
                if(sobj.getSCustomedKey().charAt(i)=='('){
                    bracket++;
                }else if(sobj.getSCustomedKey().charAt(i)==')'){
                    bracket--;
                }else if(sobj.getSCustomedKey().charAt(i)==','&&bracket>0){
                    s--;
                }
            }
            if(s!=d)
                return -8;
        
        }
        
        return 1;
    }
    public static DTableObject getDT(){
        return dtableobj;
    }
    public static STableObject getST(){
        return stableobj;
    }
}
