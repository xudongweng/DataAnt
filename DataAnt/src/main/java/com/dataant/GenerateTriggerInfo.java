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
            LoadConfigController lcc=new LoadConfigController();
            if(lcc.loadFile()==0)return;
            if(lcc.setMySQLData()==0)return;
            if(lcc.setPK()==0)return;
            
            List<String> collist=lcc.getDBTriggerCol();//获取数据源所有列名
            TriggerSQL tgSQL=new TriggerSQL();
            tgSQL.insTrigger(collist);
            if(lcc.setCustomedKey()==0){
                tgSQL.updTriggerIns(collist);
                tgSQL.delTrigger();
            }else{
                tgSQL.updTriggerUpd(collist);
                tgSQL.delTrigger(collist);
            }
            
        }catch(Exception e){
            log.error(e.toString());
        }
    }
}
