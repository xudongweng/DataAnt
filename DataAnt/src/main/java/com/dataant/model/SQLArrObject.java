/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataant.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sheriff
 */
public class SQLArrObject {
    private List<String> arrSQL=new ArrayList<>();//SQL字符串集合
    
    public void addSQL(String sql){
        this.arrSQL.add(sql);
    }
    
    public List<String> getSQL(){
        return this.arrSQL;
    }
}
