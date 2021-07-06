package com.simplicite.commons.ElasticSearchIndexer;

import java.util.*;
import com.simplicite.util.*;
import com.simplicite.util.tools.*;
import kong.unirest.Unirest;
import org.json.JSONObject;

/**
 * Shared code EsiHelper
 */
public class EsiHelper implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	 
	 Grant g;
	 String esInstance;
	 String esIndex;
	 String esUser;
	 String esPassword;
	 
	 public EsiHelper(Grant g){
	 	this.g=g;
	 	JSONObject p = new JSONObject(g.getParameter("ESI_CONFIG"));
	 	this.esInstance=p.getString("instance");
	 	this.esIndex=p.optString("index", "simplicite");
	 	String esCredentials=p.optString("credentials", null);
	 	if(esCredentials!=null){
	 		esUser = esCredentials.split(":")[0];
	 		esPassword = esCredentials.split(":")[0];
	 	}
	 }
	 
	 public void indexAllModules(){
	 	for(String mdl : getModules())
	 		if(!ModuleDB.isSystemModule(mdl))
	 			indexModule(mdl);
	 }
	 
	 public void indexModule(String mdl){
 		for(String obj : getObjects(mdl))
 			indexObject(obj);
	 }
	 
	 private void indexObject(String objectName){
	 	ObjectDB o = g.getTmpObject(objectName);
	 	synchronized(o){
	 		if(o.isIndexable()){
	 			o.resetFilters();
	 			for(String[] rslt : o.search()){
	 				JSONObject doc = new JSONObject();
	 				for(ObjectField f : o.getFields())
	 					if(f.isIndexable())
	 						doc.put(f.getName(), rslt[o.getFieldIndex(f.getName())]);
	 				indexEsDoc(o.getName()+":"+rslt[0], doc);
	 			}
	 		}
	 	}
	 }
	 
	 private void indexEsDoc(String id, JSONObject doc){
	 	String url = esInstance+"/simplicite/_doc/"+id;
	 	AppLog.info("Indexing at "+url+" : "+doc.toString(), Grant.getSystemAdmin());
	 	try{
	 		String result = RESTTool.post(doc, "application/json", url, esUser, esPassword);
	 		//AppLog.info("Result : "+result, g);
	 	}
	 	catch(Exception e){
	 		
	 	}
	 	
		/*String rslt = Unirest
			.put(url)
			.header("Content-Type", "application/json")
			.body(doc)
			.asString()
			.getBody();
		AppLog.info("Result : "+rslt, Grant.getSystemAdmin());*/
	 }
	 
	 private String[] getModules(){
	 	ObjectDB m = g.getTmpObject("Module");
	 	synchronized(m){
	 		m.resetFilters();
	 		return Tool.getColumnOfMatrixAsArray(m.search(), m.getFieldIndex("mdl_name"));
	 	}
	 }
	 private String[] getObjects(String mdl){
	 	ObjectDB o = g.getTmpObject("ObjectInternal");
	 	synchronized(o){
	 		o.resetFilters();
	 		o.setFieldFilter("mdl_name", mdl);
	 		return Tool.getColumnOfMatrixAsArray(o.search(), o.getFieldIndex("obo_name"));
	 	}
	 }
	 
}
