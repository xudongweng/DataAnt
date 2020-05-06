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
public class STableObj {
    private String sDB;
    private String sTable;
    private String sCol;
    
    public STableObj(){}
    public STableObj(String sDB,String sTable,String sCol){
        this.sDB=sDB;
        this.sTable=sTable;
        this.sCol=sCol;
    }
    
    public void setSDB(String sDB){
        this.sDB=sDB;
    }
    public void setSCol(String sCol){
        this.sCol=sCol;
    }
    public void setSTable(String sTable){
        this.sTable=sTable;
    }
    public String getSDB(){
        return this.sDB;
    }
    public String getSCol(){
        return this.sCol;
    }
    public String getSTable(){
        return this.sTable;
    }
}
