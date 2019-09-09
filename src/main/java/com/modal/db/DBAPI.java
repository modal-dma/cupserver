package com.modal.db;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
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
	
	public ArrayList<String> eta()
	{		
		try 
		{
	        Statement cmd = con.createStatement ();
	 
	        String qry = "SELECT eta FROM distribuzione_eta";
	        			        		
	        System.out.println("query " + qry);
	        
	        ResultSet res = cmd.executeQuery(qry);
	 	        
	        ArrayList<String> values = new ArrayList<String>();
	        // Stampiamone i risultati riga per riga
	        while (res.next()) 
	        {
	        	values.add(res.getString("eta"));
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
	 
	        String qry = "SELECT descrizione, COUNT(sa_data_ins) as val FROM dwh_mis_cup, prestazioni WHERE sa_pre_id = codice AND (dwh_mis_cup.sa_stato_pren::text = 'N'::text or dwh_mis_cup.sa_stato_pren::text is null)";
	        		
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
	        String qry = "SELECT branche.descrizione, COUNT(sa_data_ins) as val FROM dwh_mis_cup, branche" + (comune != null && !comune.equals("") ? ", quartieri " : " ") + "WHERE sa_branca_id = id_branca AND (dwh_mis_cup.sa_stato_pren::text = 'N'::text or dwh_mis_cup.sa_stato_pren::text is null)";
	        		
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
	        String qry = "SELECT branche.descrizione as branca, quartieri.descrizione as comune, COUNT(sa_data_ins) as val FROM dwh_mis_cup, branche, quartieri WHERE sa_branca_id = id_branca AND sa_comune_id = codcomune AND quartieri.quartiere = '0' AND (dwh_mis_cup.sa_stato_pren::text = 'N'::text or dwh_mis_cup.sa_stato_pren::text is null)";
	        		
	        		
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
	
	public BaseModel prestazioniBrancaNelTempo(String branca)
	{
		
		try 
		{
		    // Creiamo un oggetto Statement per poter interrogare il db
	        Statement cmd = con.createStatement ();
	 
	        // Eseguiamo una query e immagazziniamone i risultati
	        // in un oggetto ResultSet
	        //String qry = "SELECT sa_data_ins, COUNT(sa_data_ins) as val FROM dwh_mis_cup, branche WHERE sa_branca_id = id_branca AND branche.descrizione = '" + branca + "' GROUP BY sa_data_ins ORDER BY sa_data_ins ASC";
	        
	        String qry = "SELECT date_trunc('month', sa_data_ins) as month, COUNT(sa_data_ins) as val FROM dwh_mis_cup, branche WHERE sa_branca_id = id_branca AND (dwh_mis_cup.sa_stato_pren::text = 'N'::text or dwh_mis_cup.sa_stato_pren::text is null) AND branche.descrizione = '" + branca + "' GROUP BY date_trunc('month', sa_data_ins) ORDER BY month ASC";
	        
	        System.out.println("query " + qry);
	        
	        ResultSet res = cmd.executeQuery(qry);
	 	        
	        BaseModel model = new BaseModel();
	        ArrayList<BigDecimal> values = new ArrayList<BigDecimal>();
	        // Stampiamone i risultati riga per riga
	        while (res.next()) 
	        {
	        	System.out.println(res.getString("month"));
	        	System.out.println(res.getString("val"));
	        	
	        	model.labels.add(res.getString("month"));
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
	
	// query pazienti per branche
	//select branche.descrizione, sa_ass_cf, count(sa_ass_cf) from dwh_mis_cup, branche where sa_branca_id = id_branca and sa_ass_cf in (select sa_ass_cf from dwh_mis_cup, branche where sa_branca_id = id_branca and branche.descrizione = 'CARDIOLOGIA') group by sa_ass_cf, branche.descrizione order by sa_ass_cf
	//select branche.descrizione, sa_ass_cf, count(sa_ass_cf) from dwh_mis_cup, branche where sa_branca_id = id_branca and sa_ass_cf in (select sa_ass_cf from dwh_mis_cup, branche where sa_branca_id = id_branca) group by sa_ass_cf, branche.descrizione order by sa_ass_cf
	
	
	//select descrizione, sum(count) as totale from branche_paziente where sa_ass_cf IN (select sa_ass_cf from branche_paziente where descrizione = 'CARDIOLOGIA') and not descrizione = 'CARDIOLOGIA' group by descrizione order by  totale DESC
	public BaseModel prenotazioniPerBrancaDopoBranca(String branca, String comuneId, String startData, String endData)
	{		
		try 
		{
		    // Creiamo un oggetto Statement per poter iterrogare il db
	        Statement cmd = con.createStatement ();
	 
	        // Eseguiamo una query e immagazziniamone i risultati
	        // in un oggetto ResultSet
	        String qry = "select descrizione, sum(count) as totale from branche_paziente where sa_ass_cf IN (select sa_ass_cf from branche_paziente where descrizione = '" + branca + "') and not descrizione = '" + branca + "'";
	        
	        if(comuneId != null && !comuneId.equals(""))	        
	        	qry += " AND sa_comune_id = '" + comuneId + "'";
	        
	        if(startData != null)
	        	qry += " AND sa_data_pren >= '" + startData + "'";
	        
	        if(endData != null)
	        	qry += " AND sa_data_pren <= '" + endData + "'";
	        
	        qry += " GROUP BY descrizione ORDER BY totale DESC";
	        
	        ResultSet res = cmd.executeQuery(qry);
	 
	        BaseModel model = new BaseModel();
	        ArrayList<BigDecimal> values = new ArrayList<BigDecimal>();
	        // Stampiamone i risultati riga per riga
	        while (res.next()) 
	        {
	        	System.out.println(res.getString("descrizione"));
	        	System.out.println(res.getString("totale"));
	        	
	        	model.labels.add(res.getString("descrizione"));
	        	values.add(new BigDecimal(res.getLong("totale")));
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
	
	public BaseModel prenotazioniPerPrestazioneDopoPrestazione(String prestazione, String comuneId, String startData, String endData)
	{		
		try 
		{
		    // Creiamo un oggetto Statement per poter iterrogare il db
	        Statement cmd = con.createStatement ();
	 
	        // Eseguiamo una query e immagazziniamone i risultati
	        // in un oggetto ResultSet
	        String qry = "select descrizione, sum(count) as totale from prestazioni_paziente where sa_ass_cf IN (select sa_ass_cf from prestazioni_paziente where descrizione = '" + prestazione + "') and not descrizione = '" + prestazione + "'";
	        
	        if(comuneId != null && !comuneId.equals(""))	        
	        	qry += " AND sa_comune_id = '" + comuneId + "'";
	        
	        if(startData != null)
	        	qry += " AND sa_data_pren >= '" + startData + "'";
	        
	        if(endData != null)
	        	qry += " AND sa_data_pren <= '" + endData + "'";
	        
	        qry += " GROUP BY descrizione ORDER BY totale DESC";
	        
	        ResultSet res = cmd.executeQuery(qry);
	 
	        BaseModel model = new BaseModel();
	        ArrayList<BigDecimal> values = new ArrayList<BigDecimal>();
	        // Stampiamone i risultati riga per riga
	        while (res.next()) 
	        {
	        	System.out.println(res.getString("descrizione"));
	        	System.out.println(res.getString("totale"));
	        	
	        	model.labels.add(res.getString("descrizione"));
	        	values.add(new BigDecimal(res.getLong("totale")));
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
	
	public BaseModel3D branchePrestazioni(String comuneId, String startData, String endData)
	{		
		try 
		{
		    // Creiamo un oggetto Statement per poter iterrogare il db
	        Statement cmd = con.createStatement ();
	 
	        // Eseguiamo una query e immagazziniamone i risultati
	        // in un oggetto ResultSet
	        String qry = "select * from branche_prestazioni where true";
	        
	        if(comuneId != null && !comuneId.equals(""))	        
	        	qry += " AND sa_comune_id = '" + comuneId + "'";
	        
	        if(startData != null)
	        	qry += " AND sa_data_pren >= '" + startData + "'";
	        
	        if(endData != null)
	        	qry += " AND sa_data_pren <= '" + endData + "'";
	        
	        //qry += " GROUP BY descrizione ORDER BY totale DESC";
	        
	        ResultSet res = cmd.executeQuery(qry);
	 
	        BaseModel3D model = new BaseModel3D();
	        ArrayList<BigDecimal> values = new ArrayList<BigDecimal>();
	        // Stampiamone i risultati riga per riga
	        while (res.next()) 
	        {
	        	System.out.println(res.getString("branca"));
	        	System.out.println(res.getString("prestazione"));
	        	System.out.println(res.getString("count"));
	        	
	        	Point3D point = new Point3D();
	        	
	        	point.xLabel = res.getString("branca");
	        	point.yLabel = res.getString("prestazione");
	        	point.val = new BigDecimal(res.getLong("count"));
	        	
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
	
	public BaseModel3D etaPrestazioni(String comuneId, String startData, String endData)
	{		
		try 
		{
		    // Creiamo un oggetto Statement per poter iterrogare il db
	        Statement cmd = con.createStatement ();
	 
	        // Eseguiamo una query e immagazziniamone i risultati
	        // in un oggetto ResultSet
	        String qry = "select * from prestazioni_eta ";
	        
	        if(comuneId != null && !comuneId.equals(""))	        
	        	qry += " where	 sa_comune_id = '" + comuneId + "'";
	        
	        if(startData != null)
	        	qry += " AND sa_data_pren >= '" + startData + "'";
	        
	        if(endData != null)
	        	qry += " AND sa_data_pren <= '" + endData + "'";
	        
	        //qry += " GROUP BY descrizione ORDER BY totale DESC";
	        
	        ResultSet res = cmd.executeQuery(qry);
	 
	        BaseModel3D model = new BaseModel3D();
	        ArrayList<BigDecimal> values = new ArrayList<BigDecimal>();
	        // Stampiamone i risultati riga per riga
	        while (res.next()) 
	        {
	        	System.out.println(res.getString("eta"));
	        	System.out.println(res.getString("prestazione"));
	        	System.out.println(res.getString("count"));
	        	
	        	Point3D point = new Point3D();
	        	
	        	point.xLabel = res.getString("eta");
	        	point.yLabel = res.getString("prestazione");
	        	point.val = new BigDecimal(res.getLong("count"));
	        	
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
	
	public BaseModel etaPerPrestazioni(String prestazione, String startData, String endData)
	{		
		try 
		{
		    // Creiamo un oggetto Statement per poter iterrogare il db
	        Statement cmd = con.createStatement ();
	 
	        // Eseguiamo una query e immagazziniamone i risultati
	        // in un oggetto ResultSet
	        String qry = "select eta, count as totale from prestazioni_eta";
	        
        	qry += " where prestazione = '" + prestazione + "'";
	        
	        if(startData != null)
	        	qry += " AND sa_data_pren >= '" + startData + "'";
	        
	        if(endData != null)
	        	qry += " AND sa_data_pren <= '" + endData + "'";
	        
	        qry += " ORDER BY count DESC";
	        
	        ResultSet res = cmd.executeQuery(qry);
	 
	        BaseModel model = new BaseModel();
	        ArrayList<BigDecimal> values = new ArrayList<BigDecimal>();
	        // Stampiamone i risultati riga per riga
	        while (res.next()) 
	        {
	        	System.out.println(res.getString("eta"));
	        	System.out.println(res.getString("totale"));
	        	
	        	model.labels.add(res.getString("eta"));
	        	values.add(new BigDecimal(res.getLong("totale")));
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
	
	public BaseModel prestazioniPerEta(String eta, String startData, String endData)
	{		
		try 
		{
		    // Creiamo un oggetto Statement per poter iterrogare il db
	        Statement cmd = con.createStatement ();
	 
	        // Eseguiamo una query e immagazziniamone i risultati
	        // in un oggetto ResultSet
	        String qry = "select prestazione, count as totale from prestazioni_eta";
	        
	        qry += " where eta = '" + eta + "' AND count > 500";
	        
	        if(startData != null)
	        	qry += " AND sa_data_pren >= '" + startData + "'";
	        
	        if(endData != null)
	        	qry += " AND sa_data_pren <= '" + endData + "'";
	        
	        qry += " ORDER BY totale DESC";
	        
	        ResultSet res = cmd.executeQuery(qry);
	 
	        BaseModel model = new BaseModel();
	        ArrayList<BigDecimal> values = new ArrayList<BigDecimal>();
	        // Stampiamone i risultati riga per riga
	        while (res.next()) 
	        {
	        	System.out.println(res.getString("prestazione"));
	        	System.out.println(res.getString("totale"));
	        	
	        	model.labels.add(res.getString("prestazione"));
	        	values.add(new BigDecimal(res.getLong("totale")));
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
	
	
	public BaseModel attesaPerBranca(String comuneId, String startData, String endData)
	{		
		try 
		{
		    // Creiamo un oggetto Statement per poter interrogare il db
	        Statement cmd = con.createStatement ();
	 
	        // Eseguiamo una query e immagazziniamone i risultati
	        // in un oggetto ResultSet
	        // AND sa_gg_attesa < 400
	        String qry = "SELECT descrizione, MIN(sa_gg_attesa) as min, MAX(sa_gg_attesa) as max, AVG(sa_gg_attesa) as avg FROM dwh_mis_cup, branche WHERE sa_branca_id = id_branca AND sa_gg_attesa > 0 AND (dwh_mis_cup.sa_stato_pren::text = 'N'::text or dwh_mis_cup.sa_stato_pren::text is null)";
	        
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
	        String qry = "SELECT descrizione, MIN(sa_gg_attesa_pdisp) as min, MAX(sa_gg_attesa_pdisp) as max, AVG(sa_gg_attesa_pdisp) as avg FROM dwh_mis_cup, branche WHERE sa_branca_id = id_branca AND sa_gg_attesa_pdisp > 0 AND (dwh_mis_cup.sa_stato_pren::text = 'N'::text or dwh_mis_cup.sa_stato_pren::text is null)";
	        
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
	        String qry = "SELECT prestazioni.descrizione as prestazione, COUNT(sa_data_ins) as val FROM dwh_mis_cup, prestazioni" + (comune != null && !comune.equals("") ? ", quartieri " : " ") + "WHERE sa_pre_id = codice AND (dwh_mis_cup.sa_stato_pren::text = 'N'::text or dwh_mis_cup.sa_stato_pren::text is null)";
	        		
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
	        String qry = "SELECT quartieri.descrizione as descrizione, COUNT(sa_data_ins) as val FROM dwh_mis_cup, quartieri, prestazioni WHERE sa_pre_id = codice AND (dwh_mis_cup.sa_stato_pren::text = 'N'::text or dwh_mis_cup.sa_stato_pren::text is null) AND quartieri.quartiere = '0' AND sa_comune_id = quartieri.codcomune AND " + wherePrestazioni;
	        			        		
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
	        String qry = "SELECT quartieri.descrizione as descrizione, COUNT(sa_data_ins) as val FROM dwh_mis_cup, quartieri, branche WHERE sa_branca_id = id_branca AND (dwh_mis_cup.sa_stato_pren::text = 'N'::text or dwh_mis_cup.sa_stato_pren::text is null) AND quartieri.quartiere = '0' AND sa_comune_id = codcomune AND branche.descrizione = '" + branca + "'";
	        			        		
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
	
	public static class Item
	{
		public int count;
		public HashMap<String, Item> children;
	}
	
	public HashMap<String, Item> pathPrestazioniNelTempo(String primaPrestazione, String startData, String endData)
	{		
		try 
		{
		    // Creiamo un oggetto Statement per poter interrogare il db
	        Statement cmd = con.createStatement ();
	 
	        // Eseguiamo una query e immagazziniamone i risultati
	        // in un oggetto ResultSet
	        String qry = "SELECT assistito, prestazione, data_inserimento, data_appuntamento FROM branche_prestazioni_data_paziente";
	        			        		
	        if(startData != null)
	        	qry += " AND data_appuntamento >= '" + startData + "'";
	        
	        if(endData != null)
	        	qry += " AND data_appuntamento <= '" + endData + "'";
	        			        
	        qry += " limit 100000";
	        
	        ResultSet res = cmd.executeQuery(qry);
	 
	        HashMap<String, Item> root = new HashMap<>();
	        HashMap<String, Item> currentNode = root;
	        
	        int currentUser = 0;
	        
	        while (res.next()) 
	        {
	        	int user = res.getInt("assistito");
        		String prestazione = res.getString("prestazione");
        		
	        	if(user != currentUser)
	        	{
	        		if(!primaPrestazione.equals(prestazione))
	        			continue;
	        		
	        		currentUser = user;
	        		currentNode = root;
	        	}
	        		        		
        		Item item;
        		if(currentNode.containsKey(prestazione))
        		{
        			item = currentNode.get(prestazione);	     
        			item.count++;
        		}
        		else
        		{
        			item = new Item();
        			item.count = 1;
        			item.children = new HashMap<>();
        			currentNode.put(prestazione, item);
        		}	        			        		
        		
        		currentNode = item.children;
	        }
		    	        		    	        
	        res.close();
		    cmd.close();
		    
		    return root;
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
