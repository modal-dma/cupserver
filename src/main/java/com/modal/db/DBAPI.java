package com.modal.db;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.geonames.Toponym;
import org.geonames.ToponymSearchCriteria;
import org.geonames.ToponymSearchResult;
import org.geonames.WebService;

import com.modal.db.BaseModel3D.Point3D;

import io.swagger.model.CodDesc;
import io.swagger.model.HeatmapItem;

public class DBAPI {

	private static final String url = "jdbc:postgresql://192.168.1.20/cup";
	private static final String user = "postgres";
	private static final String password = "modal@1618!";

	private static DBAPI instance;
	
	private Connection con;
	
	public static DBAPI getInstance()
	{
		if(instance == null)
			instance = new DBAPI();
		
		return instance;
	}
	
	private DBAPI()
	{
		try 
		{
			// Carichiamo un driver di tipo 1 (bridge jdbc-odbc)
		    String driver = "org.postgresql.Driver";
		    Class.forName(driver);
		    
		    // Creiamo la stringa di connessione
		    // Otteniamo una connessione con username e password
		    con = DriverManager.getConnection (url, user, password);
		}
		catch(ClassNotFoundException ex)
		{
			ex.printStackTrace();
		} 
		catch (SQLException e) 
		{			
			e.printStackTrace();
		}
	}
	
	public ArrayList<String> branche(String startData, String endData)
	{		
		try 
		{
	        Statement cmd = con.createStatement ();
	 
	        String qry = "SELECT descrizione, COUNT(sa_data_ins) as val FROM dwh_mis_cup, branche WHERE sa_branca_id = id_branca";
	        		
	        if(startData != null)
	        	qry += " AND sa_data_pren >= '" + startData + "'";
	        
	        if(endData != null)
	        	qry += " AND sa_data_pren <= '" + endData  + "'";
	        	        
	        qry += " GROUP BY descrizione ORDER BY val DESC";
	        
//	        select * from dwh_mis_cup where sa_data_pren >= '01/01/2017' and sa_data_pren <= '31/12/2017'
//	        		
	        System.out.println("query " + qry);
	        
	        ResultSet res = cmd.executeQuery(qry);
	 	        
	        ArrayList<String> values = new ArrayList<String>();
	        // Stampiamone i risultati riga per riga
	        while (res.next()) 
	        {
	        	long count = res.getLong("val");
	        	if(count > 0)
	        	{
	        		values.add(res.getString("descrizione"));
	        	}	        		        
	        }
		    	        
	        res.close();
		    cmd.close();
		    
		    return values;
	    } 
		catch (SQLException e) 
		{
	         e.printStackTrace();
	    } 
				
		return null;
	}
	
	public ArrayList<String> comuni(String startData, String endData)
	{		
		try 
		{
	        Statement cmd = con.createStatement ();
	 
	        String qry = "SELECT descrizione, COUNT(sa_data_ins) as val FROM dwh_mis_cup, quartieri WHERE sa_comune_id = codcomune AND quartieri.quartiere = '0'";
	        		
	        if(startData != null)
	        	qry += " AND sa_data_pren >= '" + startData + "'";
	        
	        if(endData != null)
	        	qry += " AND sa_data_pren <= '" + endData  + "'";
	        	        
	        qry += " GROUP BY descrizione ORDER BY descrizione ASC";
	        
//	        select * from dwh_mis_cup where sa_data_pren >= '01/01/2017' and sa_data_pren <= '31/12/2017'
//	        		
	        System.out.println("query " + qry);
	        
	        ResultSet res = cmd.executeQuery(qry);
	 	        
	        ArrayList<String> values = new ArrayList<String>();
	        // Stampiamone i risultati riga per riga
	        while (res.next()) 
	        {
	        	long count = res.getLong("val");
	        	if(count > 0)
	        	{
	        		values.add(res.getString("descrizione"));
	        	}	        		        
	        }
		    	        
	        res.close();
		    cmd.close();
		    
		    return values;
	    } 
		catch (SQLException e) 
		{
	         e.printStackTrace();
	    } 
				
		return null;
	}
	
	public ArrayList<String> prestazioni(String startData, String endData)
	{		
		try 
		{
	        Statement cmd = con.createStatement ();
	 
	        String qry = "SELECT descrizione, COUNT(sa_data_ins) as val FROM dwh_mis_cup, prestazioni WHERE sa_pre_id = codice";
	        		
	        if(startData != null)
	        	qry += " AND sa_data_pren >= '" + startData + "'";
	        
	        if(endData != null)
	        	qry += " AND sa_data_pren <= '" + endData  + "'";
	        	        
	        qry += " GROUP BY descrizione ORDER BY val DESC";
	        
	        System.out.println("query " + qry);
	        
	        ResultSet res = cmd.executeQuery(qry);
	 	        
	        ArrayList<String> values = new ArrayList<String>();
	        // Stampiamone i risultati riga per riga
	        while (res.next()) 
	        {
	        	long count = res.getLong("val");
	        	if(count > 0)
	        	{
	        		values.add(res.getString("descrizione"));
	        	}	        		        
	        }
		    	        
	        res.close();
		    cmd.close();
		    
		    return values;
	    } 
		catch (SQLException e) 
		{
	         e.printStackTrace();
	    } 
				
		return null;
	}
	
	public BaseModel prestazioniPerBranca(String comune, String startData, String endData)
	{
		
		try 
		{
		    // Creiamo un oggetto Statement per poter interrogare il db
	        Statement cmd = con.createStatement ();
	 
	        // Eseguiamo una query e immagazziniamone i risultati
	        // in un oggetto ResultSet
	        String qry = "SELECT branche.descrizione, COUNT(sa_data_ins) as val FROM dwh_mis_cup, branche" + (comune != null && !comune.equals("") ? ", quartieri " : " ") + "WHERE sa_branca_id = id_branca";
	        		
	        if(comune != null && !comune.equals(""))	        	        	
	        	qry += " AND sa_comune_id = quartieri.codcomune AND quartieri.quartiere = '0' AND quartieri.descrizione = '" + comune + "'";
	        
	        if(startData != null)
	        	qry += " AND sa_data_pren >= '" + startData + "'";
	        
	        if(endData != null)
	        	qry += " AND sa_data_pren <= '" + endData  + "'";
	        	        
	        qry += " GROUP BY branche.descrizione ORDER BY val DESC";
	        
//	        select * from dwh_mis_cup where sa_data_pren >= '01/01/2017' and sa_data_pren <= '31/12/2017'
//	        		
	        System.out.println("query " + qry);
	        
	        ResultSet res = cmd.executeQuery(qry);
	 	        
	        BaseModel model = new BaseModel();
	        ArrayList<BigDecimal> values = new ArrayList<BigDecimal>();
	        // Stampiamone i risultati riga per riga
	        while (res.next()) 
	        {
	        	System.out.println(res.getString("descrizione"));
	        	System.out.println(res.getString("val"));
	        	
	        	model.labels.add(res.getString("descrizione"));
	        	values.add(new BigDecimal(res.getLong("val")));	        		        
	        }
		    
	        model.dataset.add(values);
	        
	        res.close();
		    cmd.close();
		    
		    return model;
	    } 
		catch (SQLException e) 
		{
	         e.printStackTrace();
	    } 
				
		return null;
	}
	
	public BaseModel3D prestazioniPerBrancaPerComune(String startData, String endData)
	{
		
		try 
		{
		    // Creiamo un oggetto Statement per poter interrogare il db
	        Statement cmd = con.createStatement ();
	 
	        // Eseguiamo una query e immagazziniamone i risultati
	        // in un oggetto ResultSet
	        String qry = "SELECT branche.descrizione as branca, quartieri.descrizione as comune, COUNT(sa_data_ins) as val FROM dwh_mis_cup, branche, quartieri WHERE sa_branca_id = id_branca AND sa_comune_id = codcomune AND quartieri.quartiere = '0'";
	        		
	        		
	        if(startData != null)
	        	qry += " AND sa_data_pren >= '" + startData + "'";
	        
	        if(endData != null)
	        	qry += " AND sa_data_pren <= '" + endData + "'";
	        		
	        qry += " GROUP BY branche.descrizione, quartieri.descrizione ORDER BY val DESC";
	        
	        ResultSet res = cmd.executeQuery(qry);
	 
	        BaseModel3D model = new BaseModel3D();
	        ArrayList<BigDecimal> values = new ArrayList<BigDecimal>();
	        // Stampiamone i risultati riga per riga
	        while (res.next()) 
	        {
	        	System.out.println(res.getString("branca"));
	        	System.out.println(res.getString("comune"));
	        	System.out.println(res.getString("val"));
	        	
	        	Point3D point = new Point3D();
	        	
	        	point.xLabel = res.getString("branca");
	        	point.yLabel = res.getString("comune");
	        	point.val = new BigDecimal(res.getLong("val"));
	        	
	        	model.points.add(point);
	        }
		    	        
	        res.close();
		    cmd.close();
		    
		    return model;
	    } 
		catch (SQLException e) 
		{
	         e.printStackTrace();
	    } 
				
		return null;
	}
	
	
	
	public BaseModel attesaPerBranca(String comuneId, String startData, String endData)
	{		
		try 
		{
		    // Creiamo un oggetto Statement per poter interrogare il db
	        Statement cmd = con.createStatement ();
	 
	        // Eseguiamo una query e immagazziniamone i risultati
	        // in un oggetto ResultSet
	        String qry = "SELECT descrizione, MIN(sa_gg_attesa) as min, MAX(sa_gg_attesa) as max, AVG(sa_gg_attesa) as avg FROM dwh_mis_cup, branche WHERE sa_branca_id = id_branca AND sa_gg_attesa > 0 AND sa_gg_attesa < 400";
	        
	        if(comuneId != null && !comuneId.equals(""))	        
	        	qry += " AND sa_comune_id = '" + comuneId + "'";
	        
	        if(startData != null)
	        	qry += " AND sa_data_pren >= '" + startData + "'";
	        
	        if(endData != null)
	        	qry += " AND sa_data_pren <= '" + endData + "'";
	        
	        qry += " GROUP BY descrizione ORDER BY avg DESC";
	        
	        ResultSet res = cmd.executeQuery(qry);
	 
	        BaseModel model = new BaseModel();
	        ArrayList<BigDecimal> minValues = new ArrayList<BigDecimal>();
	        ArrayList<BigDecimal> maxValues = new ArrayList<BigDecimal>();
	        ArrayList<BigDecimal> avgValues = new ArrayList<BigDecimal>();
	        // Stampiamone i risultati riga per riga
	        while (res.next()) 
	        {
	        	System.out.println(res.getString("descrizione"));
	        	System.out.println(res.getString("min"));
	        	System.out.println(res.getString("max"));
	        	System.out.println(res.getString("avg"));
	        	
	        	model.labels.add(res.getString("descrizione"));
	        	long min = res.getLong("min");
	        	minValues.add(new BigDecimal(min > 0 ? min : 0));
	        	maxValues.add(new BigDecimal(res.getLong("max")));
	        	avgValues.add(new BigDecimal(res.getLong("avg")));
	        }
		    
	        model.dataset.add(minValues);
	        model.dataset.add(maxValues);
	        model.dataset.add(avgValues);
	        
	        res.close();
		    cmd.close();
		    
		    return model;
	    } 
		catch (SQLException e) 
		{
	         e.printStackTrace();
	    } 
				
		return null;
	}
	
	public BaseModel attesaDisponibiitaPerBranca(String comuneId, String startData, String endData)
	{		
		try 
		{
		    // Creiamo un oggetto Statement per poter interrogare il db
	        Statement cmd = con.createStatement ();
	 
	        // Eseguiamo una query e immagazziniamone i risultati
	        // in un oggetto ResultSet
	        String qry = "SELECT descrizione, MIN(sa_gg_attesa_pdisp) as min, MAX(sa_gg_attesa_pdisp) as max, AVG(sa_gg_attesa_pdisp) as avg FROM dwh_mis_cup, branche WHERE sa_branca_id = id_branca AND sa_gg_attesa_pdisp > 0 AND sa_gg_attesa_pdisp < 400";
	        
	        if(comuneId != null && !comuneId.equals(""))	        
	        	qry += " AND sa_comune_id = '" + comuneId + "'";
	        
	        if(startData != null)
	        	qry += " AND sa_data_pren >= '" + startData + "'";
	        
	        if(endData != null)
	        	qry += " AND sa_data_pren <= '" + endData + "'";
	        
	        qry += " GROUP BY descrizione ORDER BY avg DESC";
	        
	        ResultSet res = cmd.executeQuery(qry);
	 
	        BaseModel model = new BaseModel();
	        ArrayList<BigDecimal> minValues = new ArrayList<BigDecimal>();
	        ArrayList<BigDecimal> maxValues = new ArrayList<BigDecimal>();
	        ArrayList<BigDecimal> avgValues = new ArrayList<BigDecimal>();
	        
	        // Stampiamone i risultati riga per riga
	        while (res.next()) 
	        {
	        	System.out.println(res.getString("descrizione"));
	        	System.out.println(res.getString("min"));
	        	System.out.println(res.getString("max"));
	        	System.out.println(res.getString("avg"));
	        	
	        	model.labels.add(res.getString("descrizione"));
	        	long min = res.getLong("min");
	        	minValues.add(new BigDecimal(min > 0 ? min : 0));
	        	maxValues.add(new BigDecimal(res.getLong("max")));
	        	avgValues.add(new BigDecimal(res.getLong("avg")));
	        }
		    
	        model.dataset.add(minValues);
	        model.dataset.add(maxValues);
	        model.dataset.add(avgValues);
	        
	        res.close();
		    cmd.close();
		    
		    return model;
	    } 
		catch (SQLException e) 
		{
	         e.printStackTrace();
	    } 
				
		return null;
	}
	
	public BaseModel tipoPrestazione(String comune, String startData, String endData, int limit)
	{
		
		try 
		{
		    // Creiamo un oggetto Statement per poter interrogare il db
	        Statement cmd = con.createStatement ();
	 
	        // Eseguiamo una query e immagazziniamone i risultati
	        // in un oggetto ResultSet
	        String qry = "SELECT prestazioni.descrizione as prestazione, COUNT(sa_data_ins) as val FROM dwh_mis_cup, prestazioni" + (comune != null && !comune.equals("") ? ", quartieri " : " ") + "WHERE sa_pre_id = codice";
	        		
	        if(comune != null && !comune.equals(""))	        	        	
	        	qry += " AND sa_comune_id = quartieri.codcomune AND quartieri.quartiere = '0' AND quartieri.descrizione = '" + comune + "'";
	        		
	        if(startData != null)
	        	qry += " AND sa_data_pren >= '" + startData + "'";
	        
	        if(endData != null)
	        	qry += " AND sa_data_pren <= '" + endData + "'";
	        		
	        qry += " GROUP BY prestazioni.descrizione ORDER BY val DESC" + (limit > 0 ? " LIMIT " + limit : "");
	        
	        ResultSet res = cmd.executeQuery(qry);
	 
	        BaseModel model = new BaseModel();
	        ArrayList<BigDecimal> values = new ArrayList<BigDecimal>();
	        // Stampiamone i risultati riga per riga
	        while (res.next()) 
	        {
	        	System.out.println(res.getString("prestazione"));
	        	System.out.println(res.getString("val"));
	        	
	        	model.labels.add(res.getString("prestazione"));
	        	values.add(new BigDecimal(res.getLong("val")));	        		        
	        }
		    
	        model.dataset.add(values);		    	        
	        res.close();
		    cmd.close();
		    
		    return model;
	    } 
		catch (SQLException e) 
		{
	         e.printStackTrace();
	    } 
				
		return null;
	}
	
	public ArrayList<HeatmapItem> heatmapPrestazioni(String prestazioni, String startData, String endData, int limit)
	{		
		try 
		{
			String prestazione[] = prestazioni.split(";");
			
			String wherePrestazioni = "( prestazioni.descrizione = '" + prestazione[0].replace("'",  "''") + "'";
			for(int i = 1; i < prestazione.length; i++)
			{
				wherePrestazioni += " OR prestazioni.descrizione = '" + prestazione[i].replace("'",  "''") + "'";
			}
			
			wherePrestazioni += " )";
			
		    // Creiamo un oggetto Statement per poter interrogare il db
	        Statement cmd = con.createStatement ();
	 
	        // Eseguiamo una query e immagazziniamone i risultati
	        // in un oggetto ResultSet
	        String qry = "SELECT quartieri.descrizione as descrizione, COUNT(sa_data_ins) as val FROM dwh_mis_cup, quartieri, prestazioni WHERE sa_pre_id = codice AND quartieri.quartiere = '0' AND sa_comune_id = quartieri.codcomune AND " + wherePrestazioni;
	        			        		
	        if(startData != null)
	        	qry += " AND sa_data_pren >= '" + startData + "'";
	        
	        if(endData != null)
	        	qry += " AND sa_data_pren <= '" + endData + "'";
	        		
	        qry += " GROUP BY quartieri.descrizione ORDER BY val DESC";
	        
	        System.out.println(qry);
	        
	        ResultSet res = cmd.executeQuery(qry);
	 
	        ArrayList<HeatmapItem> values = new ArrayList<HeatmapItem>();
	        
	        // Stampiamone i risultati riga per riga
	        
	        WebService.setUserName("ugos"); // add your username here
	        WebService.setGeoNamesServer("http://api.geonames.org");
	        int i = limit;
	        while (i > 0 && res.next()) 
	        {
	        	System.out.println(res.getString("descrizione"));
	        	System.out.println(res.getString("val"));
	        	
	        	String descrizione = res.getString("descrizione");
	        	int count = res.getInt("val");
	        	System.out.println("count " + count);
	        	try
	        	{
		        	ToponymSearchCriteria searchCriteria = new ToponymSearchCriteria();
		      	  	searchCriteria.setQ(descrizione);
		      	  	ToponymSearchResult searchResult = WebService.search(searchCriteria);
		      	  	
		      	  	List<Toponym> toponyms = searchResult.getToponyms();
		      	  	
		      	  	if(toponyms.size() > 0)
		      	  	{

			      	  	Toponym toponym = toponyms.get(0);
			      	  	
			      		  System.out.println(toponym.getName() + " " + toponym.getCountryName() + " - " + count);
		
			      		  HeatmapItem item = new HeatmapItem();
			      		  
			      		  item.lat = toponym.getLatitude();
			      		  item.lon = toponym.getLongitude();
			      		  item.weight = count;
			      		  item.name = descrizione;
			      		  
			      		  values.add(item);
			      	}	        		        		        		     
	        	}
	        	catch(Exception ex)
	        	{
	        		ex.printStackTrace();
	        	}
	        	
	        	i--;
	        }
		    	        		    	        
	        res.close();
		    cmd.close();
		    
		    return values;
	    } 
		catch (SQLException e) 
		{
	         e.printStackTrace();
	    } 
				
		return null;
	}
	
	public ArrayList<HeatmapItem> heatmapBranche(String branca, String startData, String endData, int limit)
	{		
		try 
		{
		    // Creiamo un oggetto Statement per poter interrogare il db
	        Statement cmd = con.createStatement ();
	 
	        // Eseguiamo una query e immagazziniamone i risultati
	        // in un oggetto ResultSet
	        String qry = "SELECT quartieri.descrizione as descrizione, COUNT(sa_data_ins) as val FROM dwh_mis_cup, quartieri, branche WHERE sa_branca_id = id_branca AND quartieri.quartiere = '0' AND sa_comune_id = codcomune AND branche.descrizione = '" + branca + "'";
	        			        		
	        if(startData != null)
	        	qry += " AND sa_data_pren >= '" + startData + "'";
	        
	        if(endData != null)
	        	qry += " AND sa_data_pren <= '" + endData + "'";
	        		
	        qry += " GROUP BY quartieri.descrizione ORDER BY val DESC LIMIT " + limit;
	        
	        ResultSet res = cmd.executeQuery(qry);
	 
	        ArrayList<HeatmapItem> values = new ArrayList<HeatmapItem>();
	        
	        // Stampiamone i risultati riga per riga
	        
	        WebService.setUserName("ugos"); // add your username here
	        WebService.setGeoNamesServer("http://api.geonames.org");
	        int i = limit;
	        while (i > 0 && res.next()) 
	        {
	        	System.out.println(res.getString("descrizione"));
	        	System.out.println(res.getString("val"));
	        	
	        	String descrizione = res.getString("descrizione");
	        	int count = res.getInt("val");
	        	System.out.println("count " + count);
	        	try
	        	{
		        	ToponymSearchCriteria searchCriteria = new ToponymSearchCriteria();
		      	  	searchCriteria.setQ(descrizione);
		      	  	ToponymSearchResult searchResult = WebService.search(searchCriteria);
		      	  	
		      	  	List<Toponym> toponyms = searchResult.getToponyms();
		      	  	
		      	  	if(toponyms.size() > 0)
		      	  	{

			      	  	Toponym toponym = toponyms.get(0);
			      	  	
			      		  System.out.println(toponym.getName() + " " + toponym.getCountryName() + " - " + count);
		
			      		  HeatmapItem item = new HeatmapItem();
			      		  
			      		  item.lat = toponym.getLatitude();
			      		  item.lon = toponym.getLongitude();
			      		  item.weight = count;
			      		  item.name = descrizione;
			      		  
			      		  values.add(item);
			      	}	        		        		        		     
	        	}
	        	catch(Exception ex)
	        	{
	        		ex.printStackTrace();
	        	}
	        	
	        	i--;
	        }
		    	        		    	        
	        res.close();
		    cmd.close();
		    
		    return values;
	    } 
		catch (SQLException e) 
		{
	         e.printStackTrace();
	    } 
				
		return null;
	}
	
//	ToponymSearchCriteria searchCriteria = new ToponymSearchCriteria();
//	  searchCriteria.setQ("zurich");
//	  ToponymSearchResult searchResult = WebService.search(searchCriteria);
//	  for (Toponym toponym : searchResult.toponyms) {
//	     System.out.println(toponym.getName()+" "+ toponym.getCountryName());
//	  }
}
