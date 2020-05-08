/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataant.controller;

import java.util.List;

/**
 *
 * @author sheriff
 */
public class ComposeSQL {
    private String sqlType1="insert ignore into";
    private String sqlType2="replace into";
    
    private int maxid=1;
    private int minid=1;
    
    private List baseList=null;
    private StringBuilder sbInsert=null;
    
    public ComposeSQL(int maxid,int minid){
    
    }
}
