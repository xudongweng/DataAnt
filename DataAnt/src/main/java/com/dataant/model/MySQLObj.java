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
public class MySQLObj {
    private String host="localhost";
    private String user="";
    private String password="";
    private String unicode="utf8";
    private String port="3306";
    
    public MySQLObj(){}
    
    public MySQLObj(String host,String user,String password,String port,String unicode){
        this.host=host;
        this.user=user;
        this.password=password;
        this.port=port;
        this.unicode=unicode;
    }
    
    public MySQLObj(String host,String user,String password,String port){
        this.host=host;
        this.user=user;
        this.password=password;
        this.port=port;
    }
    
    public void setObject(String host,String user,String password,String unicode,String port){
        this.host=host;
        this.user=user;
        this.password=password;
        this.port=port;
        this.unicode=unicode;
    }
    
    public void setObject(String host,String user,String password,String port){
        this.host=host;
        this.user=user;
        this.password=password;
        this.port=port;
    }
    
    public String getHost(){
        return this.host;
    }
    public String getUser(){
        return this.user;
    }
    public String getPassword(){
        return this.password;
    }
    public String getPort(){
        return this.port;
    }
    public String getUnicode(){
        return this.unicode;
    }
    
    public void setHost(String host){
        this.host=host;
        
    }
    public void setUser(String user){
        this.user=user;
        
    }
    public void setPassword(String password){
        this.password=password;
        
    }
    public void setPort(String port){
        this.port=port;
        
    }
    public void setUnicode(String unicode){
        this.unicode=unicode;
    }
}
