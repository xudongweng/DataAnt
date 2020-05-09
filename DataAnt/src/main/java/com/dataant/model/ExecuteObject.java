/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataant.model;

import java.text.NumberFormat;

/**
 *
 * @author sheriff
 */
public class ExecuteObject {
    private int summary=0;
    private int noexecute=0;
    
    public void setNumber(int summary,int noexecute){
        this.summary=summary;
        this.noexecute=noexecute;
    }
    
    public void setSummary(int summary){
        this.summary=summary;
    }
    
    public void setNoExecute(int noexecute){
        this.noexecute=noexecute;
    }
    
    public String execRate(){
        NumberFormat nt = NumberFormat.getPercentInstance();
        nt.setMinimumFractionDigits(2);
        return nt.format(1-(float)noexecute/summary);
    } 
}
