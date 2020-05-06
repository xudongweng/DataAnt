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
public class DTableObj {
    private String dDB;
    private String dTable;
    private String dCol;
    
    public DTableObj(){}
    public DTableObj(String dDB,String dTable,String dCol){
        this.dDB=dDB;
        this.dTable=dTable;
        this.dCol=dCol;
    }
    
    public void setDDB(String dDB){
        this.dDB=dDB;
    }
    public void setDCol(String dCol){
        this.dCol=dCol;
    }
    public void setDTable(String dTable){
        this.dTable=dTable;
    }
    public String getDDB(){
        return this.dDB;
    }
    public String gerDCol(){
        return this.dCol;
    }
    public String getDTable(){
        return this.dTable;
    }
}
