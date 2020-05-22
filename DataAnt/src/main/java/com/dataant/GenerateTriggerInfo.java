/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataant;

import com.dataant.controller.LoadConfigController;
import com.dataant.controller.TriggerSQL;
import org.apache.log4j.Logger;

/**
 *
 * @author sheriff
 */
public class GenerateTriggerInfo {
    public static void main(String[] args){
        Logger log=Logger.getLogger(GenerateTriggerInfo.class);

        LoadConfigController lcf=new LoadConfigController();
        if(lcf.loadFile()==0)return;
        if(lcf.setMySQLData()==0)return;
        if(lcf.setPK()==0)return;
        
        TriggerSQL tgSQL=new TriggerSQL();
        tgSQL.delTrigger();
        tgSQL.updTrigger();
        tgSQL.insTrigger();    
    
    }
}
