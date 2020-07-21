/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataant;

import com.dataant.controller.LoadConfigController;
import com.dataant.controller.TriggerSQL;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author sheriff
 */
public class GenerateTriggerInfo {
    public static void main(String[] args){
        Logger log=Logger.getLogger(GenerateTriggerInfo.class);
        try{
            LoadConfigController lcf=new LoadConfigController();
            if(lcf.loadFile()==0)return;
            if(lcf.setMySQLData()==0)return;
            if(lcf.setPK()==0)return;
        
            List<String> collist=lcf.getDBTriggerCol();
            TriggerSQL tgSQL=new TriggerSQL();
            tgSQL.delTrigger();
            tgSQL.updTrigger(collist);
            tgSQL.insTrigger(collist);    
        }catch(Exception e){
            log.error(e.toString());
        }
    }
}
