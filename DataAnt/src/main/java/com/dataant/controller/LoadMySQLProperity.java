/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataant.controller;

import com.dataant.model.MySQLObj;

/**
 *
 * @author sheriff
 */
public class LoadMySQLProperity {
    private static MySQLObj mysqlobj=null;
    
    public static MySQLObj getMySQLObj(){
        return mysqlobj;
    }
    public static void setMySQLObj(MySQLObj obj){
        mysqlobj=obj;
    }
}
