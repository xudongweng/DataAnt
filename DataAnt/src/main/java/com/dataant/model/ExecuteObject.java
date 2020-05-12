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
    private long summary=0;
    private long noexecute=0;
    
    public void setNumber(long summary,long noexecute){
        this.summary=summary;
        this.noexecute=noexecute;
    }
    
    public void setSummary(long summary){
        this.summary=summary;
    }
    
    public void setNoExecute(long noexecute){
        this.noexecute=noexecute;
    }
    
    public String execRate(){
        NumberFormat nt = NumberFormat.getPercentInstance();
        nt.setMinimumFractionDigits(2);
        return nt.format(1-(float)noexecute/summary);
    } 
}
