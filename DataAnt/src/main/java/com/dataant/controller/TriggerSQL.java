/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataant.controller;


import com.dataant.model.LoadTableProperity;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author sheriff
 */
public class TriggerSQL {
    private final StringBuilder sbTriggerSQL=new StringBuilder();
    private Logger log=Logger.getLogger(TriggerSQL.class);
    
    public void delTrigger(){
        sbTriggerSQL.append("CREATE TRIGGER tg_").append(LoadTableProperity.getST().getSDB())
                .append("_").append(LoadTableProperity.getST().getSTable()).append("_del AFTER DELETE ON ")
                .append(LoadTableProperity.getST().getSDB()).append(".").append(LoadTableProperity.getST().getSTable())
                .append(" FOR EACH ROW DELETE IGNORE FROM ").append(LoadTableProperity.getDT().getDDB()).append(".").append(LoadTableProperity.getDT().getDTable())
                .append(" WHERE ").append(LoadTableProperity.getDT().getDDB()).append(".").append(LoadTableProperity.getDT().getDTable()).append(".").append(LoadTableProperity.getPK())
                .append(" <=> OLD.").append(LoadTableProperity.getPK()).append(";");
        //System.out.println(sbTriggerSQL.toString());
        log.info(sbTriggerSQL.toString());
        sbTriggerSQL.delete(0, sbTriggerSQL.length());
    }

    public void updTrigger(List<String> collist){
        sbTriggerSQL.append("CREATE TRIGGER tg_").append(LoadTableProperity.getST().getSDB())
                .append("_").append(LoadTableProperity.getST().getSTable()).append("_upd AFTER UPDATE ON ")
                .append(LoadTableProperity.getST().getSDB()).append(".").append(LoadTableProperity.getST().getSTable())
                .append(" FOR EACH ROW REPLACE INTO ").append(LoadTableProperity.getDT().getDDB()).append(".").append(LoadTableProperity.getDT().getDTable()).append("(")
                .append(LoadTableProperity.getDT().gerDCols()).append(") VALUES (");
        StringBuilder sb=new StringBuilder();
        String str=(LoadTableProperity.getST().getSCols());
        //int colStrlen=str.length();
        sb.append(str);
        //匹配values的后半段
        for(String col:collist){
            int beginidx=0;
            while(sb.indexOf(col,beginidx)>=0){
                /*
                if(col.equals("date_flag")){
                    System.out.println(sb.indexOf(col,beginidx));
                    System.out.println(sb.length());
                    System.out.println("xxx");
                }*/
                beginidx=sb.indexOf(col,beginidx);
                //System.out.println(str.indexOf(",", beginidx));
                if(sb.indexOf(",", beginidx)==beginidx+col.length()||
                        sb.indexOf(")", beginidx)==beginidx+col.length()||
                        sb.indexOf(" ", beginidx)==beginidx+col.length()||
                        sb.length()==beginidx+col.length()||//判断是否匹配的为末尾字符串
                        sb.indexOf("/", beginidx)==beginidx+col.length()||
                        sb.indexOf("*", beginidx)==beginidx+col.length()||
                        sb.indexOf("+", beginidx)==beginidx+col.length()||
                        sb.indexOf("-", beginidx)==beginidx+col.length()){
                    //str=str.replaceFirst(col, "NEW."+col);
                    sb.replace(beginidx,beginidx+col.length(), "NEW."+col);
                    //sbTmp.append(sb.substring(beginidx, beginidx+col.length()).replace(col, "NEW."+col));
                    //System.out.println(sb.toString());
                    beginidx=beginidx+col.length()+4;
                }else if(sb.indexOf(",", beginidx)>0){
                    beginidx=sb.indexOf(",", beginidx);
                }else if(sb.indexOf(" ", beginidx)>0){
                    beginidx=sb.indexOf(" ", beginidx);
                }else if(sb.indexOf("/", beginidx)>0){
                    beginidx=sb.indexOf("/", beginidx);
                }else if(sb.indexOf("*", beginidx)>0){
                    beginidx=sb.indexOf("*", beginidx);
                }else if(sb.indexOf("+", beginidx)>0){
                    beginidx=sb.indexOf("+", beginidx);
                }else if(sb.indexOf("-", beginidx)>0){
                    beginidx=sb.indexOf("-", beginidx);
                }else if(sb.indexOf(")", beginidx)>0){
                    beginidx=sb.indexOf(")", beginidx);
                }else
                    break;
            }
        }
        sbTriggerSQL.append(sb.toString()).append(");");
        //System.out.println(sbTriggerSQL.toString());
        log.info(sbTriggerSQL.toString());
        sbTriggerSQL.delete(0, sbTriggerSQL.length());
        sb.delete(0, sb.length());
    }

    public void insTrigger(List<String> collist){
        sbTriggerSQL.append("CREATE TRIGGER tg_").append(LoadTableProperity.getST().getSDB())
                .append("_").append(LoadTableProperity.getST().getSTable()).append("_ins AFTER INSERT ON ")
                .append(LoadTableProperity.getST().getSDB()).append(".").append(LoadTableProperity.getST().getSTable())
                .append(" FOR EACH ROW REPLACE INTO ").append(LoadTableProperity.getDT().getDDB()).append(".").append(LoadTableProperity.getDT().getDTable()).append("(")
                .append(LoadTableProperity.getDT().gerDCols()).append(") VALUES (");
        //匹配values的后半段
        StringBuilder sb=new StringBuilder();
        String str=(LoadTableProperity.getST().getSCols());
        //int colStrlen=str.length();
        sb.append(str);
        
        for(String col:collist){
            int beginidx=0;
            while(sb.indexOf(col,beginidx)>=0){
                /*
                if(col.equals("date_flag")){
                    System.out.println(sb.indexOf(col,beginidx));
                    System.out.println(sb.length());
                    System.out.println("xxx");
                }*/
                beginidx=sb.indexOf(col,beginidx);
                //System.out.println(str.indexOf(",", beginidx));
                if(sb.indexOf(",", beginidx)==beginidx+col.length()||
                        sb.indexOf(")", beginidx)==beginidx+col.length()||
                        sb.indexOf(" ", beginidx)==beginidx+col.length()||
                        sb.length()==beginidx+col.length()||//判断是否匹配的为末尾字符串
                        sb.indexOf("/", beginidx)==beginidx+col.length()||
                        sb.indexOf("*", beginidx)==beginidx+col.length()||
                        sb.indexOf("+", beginidx)==beginidx+col.length()||
                        sb.indexOf("-", beginidx)==beginidx+col.length()){
                    //str=str.replaceFirst(col, "NEW."+col);
                    sb.replace(beginidx,beginidx+col.length(), "NEW."+col);
                    //sbTmp.append(sb.substring(beginidx, beginidx+col.length()).replace(col, "NEW."+col));
                    //System.out.println(sb.toString());
                    beginidx=beginidx+col.length()+4;
                }else if(sb.indexOf(",", beginidx)>0){
                    beginidx=sb.indexOf(",", beginidx);
                }else if(sb.indexOf(" ", beginidx)>0){
                    beginidx=sb.indexOf(" ", beginidx);
                }else if(sb.indexOf("/", beginidx)>0){
                    beginidx=sb.indexOf("/", beginidx);
                }else if(sb.indexOf("*", beginidx)>0){
                    beginidx=sb.indexOf("*", beginidx);
                }else if(sb.indexOf("+", beginidx)>0){
                    beginidx=sb.indexOf("+", beginidx);
                }else if(sb.indexOf("-", beginidx)>0){
                    beginidx=sb.indexOf("-", beginidx);
                }else if(sb.indexOf(")", beginidx)>0){
                    beginidx=sb.indexOf(")", beginidx);
                }else
                    break;
            }
        }
        sbTriggerSQL.append(sb.toString()).append(");");
        //System.out.println(sbTriggerSQL.toString());
        log.info(sbTriggerSQL.toString());
        sbTriggerSQL.delete(0, sbTriggerSQL.length());
        sb.delete(0, sb.length());
    }
}
