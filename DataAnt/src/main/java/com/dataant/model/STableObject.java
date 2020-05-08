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
public class STableObject {
    private String sDB;
    private String sTable;
    private String sCols;
    
    public STableObject(){}
    public STableObject(String sDB,String sTable,String sCols){
        this.sDB=sDB;
        this.sTable=sTable;
        this.sCols=sCols;
    }
    
    public void setSDB(String sDB){
        this.sDB=sDB;
    }
    public void setSCols(String sCols){
        this.sCols=sCols;
    }
    public void setSTable(String sTable){
        this.sTable=sTable;
    }
    public String getSDB(){
        return this.sDB;
    }
    public String getSCols(){
        return this.sCols;
    }
    public String getSTable(){
        return this.sTable;
    }
}
