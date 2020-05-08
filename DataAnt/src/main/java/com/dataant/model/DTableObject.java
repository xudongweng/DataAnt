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
public class DTableObject {
    private String dDB;
    private String dTable;
    private String dCols;
    
    public DTableObject(){}
    public DTableObject(String dDB,String dTable,String dCols){
        this.dDB=dDB;
        this.dTable=dTable;
        this.dCols=dCols;
    }
    
    public void setDDB(String dDB){
        this.dDB=dDB;
    }
    public void setDCols(String dCols){
        this.dCols=dCols;
    }
    public void setDTable(String dTable){
        this.dTable=dTable;
    }
    public String getDDB(){
        return this.dDB;
    }
    public String gerDCols(){
        return this.dCols;
    }
    public String getDTable(){
        return this.dTable;
    }
}
