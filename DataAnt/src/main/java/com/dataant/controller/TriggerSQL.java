/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataant.controller;

import com.dataant.model.LoadTableProperity;

/**
 *
 * @author sheriff
 */
public class TriggerSQL {
    private final StringBuilder sbTriggerSQL=new StringBuilder();
    
    public void delTrigger(){
        sbTriggerSQL.append("CREATE TRIGGER tg_").append(LoadTableProperity.getST().getSDB())
                .append("_").append(LoadTableProperity.getST().getSTable()).append("_del AFTER DELETE ON ")
                .append(LoadTableProperity.getST().getSDB()).append(".").append(LoadTableProperity.getST().getSTable())
                .append(" FOR EACH ROW DELETE IGNORE FROM ").append(LoadTableProperity.getDT().getDDB()).append(".").append(LoadTableProperity.getDT().getDTable())
                .append(" WHERE ").append(LoadTableProperity.getDT().getDDB()).append(".").append(LoadTableProperity.getDT().getDTable()).append(".").append(LoadTableProperity.getPK())
                .append(" <=> OLD.").append(LoadTableProperity.getPK());
        System.out.println(sbTriggerSQL.toString());
        sbTriggerSQL.delete(0, sbTriggerSQL.length());
    }
    
    public void updTrigger(){
        sbTriggerSQL.append("CREATE TRIGGER tg_").append(LoadTableProperity.getST().getSDB())
                .append("_").append(LoadTableProperity.getST().getSTable()).append("_upd AFTER UPDATE ON ")
                .append(LoadTableProperity.getST().getSDB()).append(".").append(LoadTableProperity.getST().getSTable())
                .append(" FOR EACH ROW REPLACE INTO ").append(LoadTableProperity.getDT().getDDB()).append(".").append(LoadTableProperity.getDT().getDTable()).append("(")
                .append(LoadTableProperity.getDT().gerDCols()).append(") VALUES (NEW.").append(LoadTableProperity.getDT().gerDCols().replace(",", ",NEW.")).append(")");
        System.out.println(sbTriggerSQL.toString());
        sbTriggerSQL.delete(0, sbTriggerSQL.length());
    }
    
    public void insTrigger(){
        sbTriggerSQL.append("CREATE TRIGGER tg_").append(LoadTableProperity.getST().getSDB())
                .append("_").append(LoadTableProperity.getST().getSTable()).append("_ins AFTER INSERT ON ")
                .append(LoadTableProperity.getST().getSDB()).append(".").append(LoadTableProperity.getST().getSTable())
                .append(" FOR EACH ROW REPLACE INTO ").append(LoadTableProperity.getDT().getDDB()).append(".").append(LoadTableProperity.getDT().getDTable()).append("(")
                .append(LoadTableProperity.getDT().gerDCols()).append(") VALUES (NEW.").append(LoadTableProperity.getDT().gerDCols().replace(",", ",NEW.")).append(")");
        System.out.println(sbTriggerSQL.toString());
        sbTriggerSQL.delete(0, sbTriggerSQL.length());
    }
}
