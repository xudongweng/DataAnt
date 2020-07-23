/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataant.controller;


import com.dataant.model.LoadTableProperity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author sheriff
 */
public class TriggerSQL {
    private final StringBuilder sbTriggerSQL=new StringBuilder();
    private final Logger log=Logger.getLogger(TriggerSQL.class);
    
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

     public void delTrigger(List<String> collist){
        sbTriggerSQL.append("CREATE TRIGGER tg_").append(LoadTableProperity.getST().getSDB())
                .append("_").append(LoadTableProperity.getST().getSTable()).append("_del AFTER DELETE ON ")
                .append(LoadTableProperity.getST().getSDB()).append(".").append(LoadTableProperity.getST().getSTable())
                .append(" FOR EACH ROW DELETE IGNORE FROM ").append(LoadTableProperity.getDT().getDDB()).append(".").append(LoadTableProperity.getDT().getDTable())
                .append(" WHERE ");//.append(LoadTableProperity.getDT().getDDB()).append(".").append(LoadTableProperity.getDT().getDTable()).append(".").append(LoadTableProperity.getPK())
                //.append(" <=> OLD.").append(LoadTableProperity.getPK()).append(";");
        
        //对where部分类进行拆分再组合
        String strWhere=this.setNewCols(LoadTableProperity.getST().getSCustomedKey(), collist,false);
        List<String> dcusColsList=Arrays.asList(LoadTableProperity.getDT().getDCustomedKey().split(","));
        List<String> scusColsList=this.getColList(strWhere);
        
        for(int idxlist=0;idxlist<dcusColsList.size();idxlist++){
            sbTriggerSQL.append(LoadTableProperity.getDT().getDDB()).append(".").
                    append(LoadTableProperity.getDT().getDTable()).append(".").append(dcusColsList.get(idxlist)).append("<=>").append(scusColsList.get(idxlist));
            if(idxlist!=dcusColsList.size()-1) sbTriggerSQL.append(" AND ");
        }
        //System.out.println(sbTriggerSQL.toString());
        log.info(sbTriggerSQL.toString());
        sbTriggerSQL.delete(0, sbTriggerSQL.length());
    }
     
    public void updTriggerIns(List<String> collist){
        sbTriggerSQL.append("CREATE TRIGGER tg_").append(LoadTableProperity.getST().getSDB())
                .append("_").append(LoadTableProperity.getST().getSTable()).append("_upd AFTER UPDATE ON ")
                .append(LoadTableProperity.getST().getSDB()).append(".").append(LoadTableProperity.getST().getSTable())
                .append(" FOR EACH ROW REPLACE INTO ").append(LoadTableProperity.getDT().getDDB()).append(".").append(LoadTableProperity.getDT().getDTable()).append("(")
                .append(LoadTableProperity.getDT().getDCols()).append(") VALUES (");
        
        sbTriggerSQL.append(this.setNewCols(LoadTableProperity.getST().getSCols(), collist,true)).append(");");
        //System.out.println(sbTriggerSQL.toString());
        log.info(sbTriggerSQL.toString());
        sbTriggerSQL.delete(0, sbTriggerSQL.length());
    }

    public void updTriggerUpd(List<String> collist){
        sbTriggerSQL.append("CREATE TRIGGER tg_").append(LoadTableProperity.getST().getSDB())
                .append("_").append(LoadTableProperity.getST().getSTable()).append("_upd AFTER UPDATE ON ")
                .append(LoadTableProperity.getST().getSDB()).append(".").append(LoadTableProperity.getST().getSTable())
                .append(" FOR EACH ROW UPDATE ").append(LoadTableProperity.getDT().getDDB()).append(".").append(LoadTableProperity.getDT().getDTable()).append(" SET ");
        //对set部分类进行拆分再组合
        String strSet=this.setNewCols(LoadTableProperity.getST().getSCols(), collist,true);
        List<String> dcolslist=Arrays.asList(LoadTableProperity.getDT().getDCols().split(","));
        List<String> scolslist=this.getColList(strSet);
        //System.out.println(dcolslist);
        //System.out.println(scolslist);
        //System.out.println(sbTriggerSQL.toString());
       
        //SET部分拼接
        for(int idxlist=0;idxlist<dcolslist.size();idxlist++){
            sbTriggerSQL.append(LoadTableProperity.getDT().getDDB()).append(".").
                    append(LoadTableProperity.getDT().getDTable()).append(".").append(dcolslist.get(idxlist)).append("=").append(scolslist.get(idxlist));
            if(idxlist!=dcolslist.size()-1)
                sbTriggerSQL.append(",");
            else
                sbTriggerSQL.append(" WHERE ");
        }
        //对where部分类进行拆分再组合
        String strWhere=this.setNewCols(LoadTableProperity.getST().getSCustomedKey(), collist,false);
        List<String> dcusColsList=Arrays.asList(LoadTableProperity.getDT().getDCustomedKey().split(","));
        List<String> scusColsList=this.getColList(strWhere);
        
        //System.out.println(dcusColsList);
        //System.out.println(scusColsList);
        for(int idxlist=0;idxlist<dcusColsList.size();idxlist++){
            sbTriggerSQL.append(LoadTableProperity.getDT().getDDB()).append(".").
                    append(LoadTableProperity.getDT().getDTable()).append(".").append(dcusColsList.get(idxlist)).append("<=>").append(scusColsList.get(idxlist));
            if(idxlist!=dcusColsList.size()-1) sbTriggerSQL.append(" AND ");
        }
        
        log.info(sbTriggerSQL.toString());
        sbTriggerSQL.delete(0, sbTriggerSQL.length());
    }
    
    private List<String> getColList(String cols){
        List<String> scolslist=null;
        if(!cols.contains("(")){
            scolslist = Arrays.asList(cols.split(","));
        }else{
            //StringBuilder sbSet=new StringBuilder();
            //sbSet.append(sbSet);
            scolslist=new ArrayList();
            int idx=0;
            int i=cols.indexOf(",");
            int j=cols.indexOf("(");
            while(true){
                while((i<j||j==-1)&&i>-1){
                    //System.out.println(strSet.substring(idx,i));
                    scolslist.add(cols.substring(idx,i));
                    idx=i+1;
                    i=cols.indexOf(",",idx);
                }
                if(i==-1){
                    scolslist.add(cols.substring(idx,cols.length()));
                    break;
                }
                int bracket=0;
                if(j>0){
                    bracket=1;
                }
                while(bracket>0){
                    j++;
                    //System.out.println(strSet);
                    //System.out.println(strSet.charAt(j));
                    if(cols.charAt(j)==')'){
                        bracket--;
                    }else if(cols.charAt(j)=='('){
                        bracket++;
                    }
                }
                i=cols.indexOf(",",j);
                j=cols.indexOf("(",j);
            }
        }
        return scolslist;
    }
    
    //匹配更新部分列字段更新为NEW.的列字段
    private String setNewCols(String strCols,List<String> collist,boolean flag){
        StringBuilder sb=new StringBuilder();
        sb.append(strCols);
        //匹配SET部分列字段更新为NEW.的列字段
        for(String col:collist){
            int beginidx=0;
            while(sb.indexOf(col,beginidx)>=0){

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
                    if(flag)
                        sb.replace(beginidx,beginidx+col.length(), "NEW."+col);
                    else
                        sb.replace(beginidx,beginidx+col.length(), "OLD."+col);
                    //System.out.println(sb.toString());
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
        return sb.toString();
    }
    
    
    public void insTrigger(List<String> collist){
        sbTriggerSQL.append("CREATE TRIGGER tg_").append(LoadTableProperity.getST().getSDB())
                .append("_").append(LoadTableProperity.getST().getSTable()).append("_ins AFTER INSERT ON ")
                .append(LoadTableProperity.getST().getSDB()).append(".").append(LoadTableProperity.getST().getSTable())
                .append(" FOR EACH ROW REPLACE INTO ").append(LoadTableProperity.getDT().getDDB()).append(".").append(LoadTableProperity.getDT().getDTable()).append("(")
                .append(LoadTableProperity.getDT().getDCols()).append(") VALUES (");
        sbTriggerSQL.append(this.setNewCols(LoadTableProperity.getST().getSCols(), collist,true)).append(");");
        //System.out.println(sbTriggerSQL.toString());
        log.info(sbTriggerSQL.toString());
        sbTriggerSQL.delete(0, sbTriggerSQL.length());
    }
}
