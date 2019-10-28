package com.modal.db;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.DoubleSummaryStatistics;
import java.util.HashMap;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.LongSummaryStatistics;

import org.geonames.Toponym;
import org.geonames.ToponymSearchCriteria;
import org.geonames.ToponymSearchResult;
import org.geonames.WebService;

import com.modal.db.BaseModel3D.Point3D;

import io.swagger.model.CodDesc;
import io.swagger.model.HeatmapItem;

public class DBAPI {

	private static final String url = "jdbc:postgresql://192.168.1.20/cup";
	//private static final String url = "jdbc:postgresql://192.167.11.69:4532/cup";
	private static final String user = "postgres";
	private static final String password = "modal@1618!";

	private static DBAPI instance;
	
	private Connection con;
	
	private static HashMap<String, Toponym> geoplaceMap = new HashMap<>();

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
		    
		    WebService.setUserName("ugos"); // add your username here
	        WebService.setGeoNamesServer("http://api.geonames.org");
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
	
	public BaseModel eta()
	{		
		try 
		{
	        Statement cmd = con.createStatement ();
	 
	        String qry = "SELECT * FROM distribuzione_eta";
	        			        		
	        System.out.println("query " + qry);
	        
	        ResultSet res = cmd.executeQuery(qry);	      
	        
	        BaseModel model = new BaseModel();
	        ArrayList<BigDecimal> values = new ArrayList<BigDecimal>();
	        // Stampiamone i risultati riga per riga
	        while (res.next()) 
	        {
        		model.labels.add(res.getString("eta"));
        		values.add(new BigDecimal(res.getLong("count")));
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
	
	public BaseModel prestazioniAltreBranche(String startData, String endData)
	{
		
		try 
		{
		    // Creiamo un oggetto Statement per poter interrogare il db
	        Statement cmd = con.createStatement ();
	 
	        String qry = "SELECT prestazioni.descrizione, count(prestazioni.descrizione) AS count FROM altre_branche, prestazioni WHERE altre_branche.sa_pre_id::text = prestazioni.codice::text and (altre_branche.sa_stato_pren::text = 'N'::text OR altre_branche.sa_stato_pren::text IS NULL)";
	        
	        if(startData != null)
	        	qry += " AND sa_data_pren >= '" + startData + "'";
	        
	        if(endData != null)
	        	qry += " AND sa_data_pren <= '" + endData  + "'";
	        		
	        qry += "GROUP BY prestazioni.descrizione ORDER BY (count(prestazioni.descrizione)) DESC";
	        	        
	        System.out.println("query " + qry);
	        
	        ResultSet res = cmd.executeQuery(qry);
	 	        
	        BaseModel model = new BaseModel();
	        ArrayList<BigDecimal> values = new ArrayList<BigDecimal>();
	        // Stampiamone i risultati riga per riga
	        while (res.next()) 
	        {
	        	System.out.println(res.getString("descrizione"));
	        	System.out.println(res.getString("count"));
	        	
//	        	if(res.getLong("count") > 120)
//	        	{
	        		model.labels.add(res.getString("descrizione"));
	        		values.add(new BigDecimal(res.getLong("count")));
//	        	}
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
	
	public BaseModel prestazioniNelTempo()
	{
		
		try 
		{
		    // Creiamo un oggetto Statement per poter interrogare il db
	        Statement cmd = con.createStatement ();
	 
	        String qry = "SELECT * FROM prestazioni_nel_tempo";
	        	        
	        System.out.println("query " + qry);
	        
	        ResultSet res = cmd.executeQuery(qry);
	 	        
	        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	        
	        BaseModel model = new BaseModel();
	        ArrayList<BigDecimal> values = new ArrayList<BigDecimal>();
	        // Stampiamone i risultati riga per riga
	        while (res.next()) 
	        {
	        	System.out.println(res.getString("data_appuntamento"));
	        	System.out.println(res.getString("count"));
	        	
	        	Date d = res.getDate("data_appuntamento");
	        	
	        	
        		model.labels.add(sdf.format(d));
        		values.add(new BigDecimal(res.getLong("count")));
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
	
	public BaseModel prestazioniConteggio(String startData, String endData)
	{
		
		try 
		{
		    // Creiamo un oggetto Statement per poter interrogare il db
	        Statement cmd = con.createStatement ();
	 
	        String qry = "SELECT descrizione, COUNT(sa_data_ins) as val FROM dwh_mis_cup, prestazioni WHERE sa_pre_id = codice AND (dwh_mis_cup.sa_stato_pren::text = 'N'::text or dwh_mis_cup.sa_stato_pren::text is null)";
	        	       
	        if(startData != null)
	        	qry += " AND sa_data_pren >= '" + startData + "'";
	        
	        if(endData != null)
	        	qry += " AND sa_data_pren <= '" + endData  + "'";
	        		
	        qry += "GROUP BY descrizione ORDER BY val DESC";
	        	        
	        System.out.println("query " + qry);
	        
	        ResultSet res = cmd.executeQuery(qry);
	 	        
	        BaseModel model = new BaseModel();
	        ArrayList<BigDecimal> values = new ArrayList<BigDecimal>();
	        // Stampiamone i risultati riga per riga
	        while (res.next()) 
	        {
	        	System.out.println(res.getString("descrizione"));
	        	System.out.println(res.getString("val"));
	        	
//	        	if(res.getLong("count") > 120)
//	        	{
	        		model.labels.add(res.getString("descrizione"));
	        		values.add(new BigDecimal(res.getLong("val")));
//	        	}
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
	
	public BaseModel prestazioniPerBranca(String comune, String startData, String endData, Integer minValue)
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
	        	
	        	long val = res.getLong("val");
	        	if(minValue == null || val >= minValue)
	        	{
	        		model.labels.add(res.getString("descrizione"));
	        		values.add(new BigDecimal(val));
	        	}
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
	
	public BaseModel3D prestazioniPerUOPPerComune(String prestazione, String comune, int minCount)
	{		
		try 
		{
			String wherePrestazioni = "( prestazioni.descrizione = '" + prestazione.replace("'",  "''") + "')";
			
		    // Creiamo un oggetto Statement per poter interrogare il db
	        Statement cmd = con.createStatement ();
	 
	        // Eseguiamo una query e immagazziniamone i risultati
	        // in un oggetto ResultSet
	        //AND (NOT dec_unita_eroganti.struttura_comune = sa_comune_id) 
	        //select * from dwh_mis_cup, dec_unita_eroganti, quartieri where dwh_mis_cup.sa_uop_codice_id = dec_unita_eroganti.sa_uop_codice_id and dec_unita_eroganti.struttura_comune = codcomune
	        String qry = "SELECT quartieri.descrizione as comune_asl, sa_comune_id as id_residenza, COUNT(sa_data_ins) as val FROM dwh_mis_cup, quartieri, prestazioni, dec_unita_eroganti WHERE "  
	        		 + wherePrestazioni + " AND quartieri.quartiere = '0' AND dwh_mis_cup.sa_uop_codice_id = dec_unita_eroganti.sa_uop_codice_id AND dec_unita_eroganti.struttura_comune = codcomune AND (dwh_mis_cup.sa_stato_pren::text = 'N'::text or dwh_mis_cup.sa_stato_pren::text is null) AND sa_pre_id = codice  GROUP BY comune_asl, sa_comune_id ORDER BY val desc";
	        //+ wherePrestazioni + " AND quartieri.quartiere = '0' AND dwh_mis_cup.sa_uop_codice_id = dec_unita_eroganti.sa_uop_codice_id AND dec_unita_eroganti.struttura_comune = codcomune AND (NOT dec_unita_eroganti.struttura_comune = sa_comune_id) AND (dwh_mis_cup.sa_stato_pren::text = 'N'::text or dwh_mis_cup.sa_stato_pren::text is null) AND sa_pre_id = codice  GROUP BY comune_asl, sa_comune_id ORDER BY val desc";
	        			        
	        
//	        if(startData != null)
//	        	qry += " AND sa_data_pren >= '" + startData + "'";
//	        
//	        if(endData != null)
//	        	qry += " AND sa_data_pren <= '" + endData + "'";
//	        			        
	        qry = "SELECT base.comune_asl as comune_asl, quartieri.descrizione as residenza, val from (" + qry + ") as base, quartieri where id_residenza = quartieri.codcomune AND quartieri.quartiere = '0'" + ((comune != null && comune != "" && !comune.equals(" ")) ? " AND quartieri.descrizione = '" + comune + "'": "");

	        System.out.println(qry);
	        
	        ResultSet res = cmd.executeQuery(qry);
	        
	        WebService.setUserName("ugos"); // add your username here
	        WebService.setGeoNamesServer("http://api.geonames.org");
	        
	        BaseModel3D model = new BaseModel3D();
	        ArrayList<BigDecimal> values = new ArrayList<BigDecimal>();
	        // Stampiamone i risultati riga per riga
	        while (res.next()) 
	        {	        		        	
	        	long val = res.getLong("val");
	        	//if(val > 10)
	        	//{
		        	Point3D point = new Point3D();
		        	
		        	point.xLabel = res.getString("comune_asl");
		        	point.yLabel = res.getString("residenza");
		        	point.val = new BigDecimal(val);
		        	
		        	Toponym residenzaToponym = searchForToponym(point.yLabel);
		        	Toponym comuneAslToponym = searchForToponym(point.xLabel);
		        			        	        		      		        
		        	double distance = -1;
		        	
		        	if(comuneAslToponym != null && residenzaToponym != null)
		        		distance = distance(comuneAslToponym.getLatitude(), comuneAslToponym.getLongitude(), 0, residenzaToponym.getLatitude(), residenzaToponym.getLongitude(), 0);
		        	
		        	point.weight = new BigDecimal(distance);
		        			        
		        	model.points.add(point);
		        	
		        	System.out.println(res.getString("comune_asl"));
		        	System.out.println(res.getString("residenza"));
		        	System.out.println(res.getString("val"));
		        			       
	        }
	        
	        res.close();
		    cmd.close();
		    
		    return model;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
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
	
	
	public BaseModel attesaPerBranca(String comune, String startData, String endData)
	{		
		try 
		{
		    // Creiamo un oggetto Statement per poter interrogare il db
	        Statement cmd = con.createStatement ();
	 
	        // Eseguiamo una query e immagazziniamone i risultati
	        // in un oggetto ResultSet
	        // AND sa_gg_attesa < 400
	        String qry = "SELECT descrizione, MIN(sa_gg_attesa) as min, MAX(sa_gg_attesa) as max, AVG(sa_gg_attesa) as avg, count(descrizione) as count FROM dwh_mis_cup, branche WHERE sa_branca_id = id_branca AND sa_gg_attesa > 0 AND (dwh_mis_cup.sa_stato_pren::text = 'N'::text or dwh_mis_cup.sa_stato_pren::text is null)";
	        
//	        if(comuneId != null && !comuneId.equals(""))	        
//	        	qry += " AND sa_comune = '" + comune + "'";
//	        
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
	        ArrayList<BigDecimal> countValues = new ArrayList<BigDecimal>();
	        
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
	        	countValues.add(new BigDecimal(res.getLong("count")));
	        }
		    
	        model.dataset.add(minValues);
	        model.dataset.add(maxValues);
	        model.dataset.add(avgValues);
	        model.dataset.add(countValues);
	        
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
	        String qry = "SELECT descrizione, MIN(sa_gg_attesa_pdisp) as min, MAX(sa_gg_attesa_pdisp) as max, AVG(sa_gg_attesa_pdisp) as avg, count(descrizione) as count FROM dwh_mis_cup, branche WHERE sa_branca_id = id_branca AND sa_gg_attesa_pdisp > 0 AND (dwh_mis_cup.sa_stato_pren::text = 'N'::text or dwh_mis_cup.sa_stato_pren::text is null)";
	        
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
	        ArrayList<BigDecimal> countValues = new ArrayList<BigDecimal>();
	        
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
	        	countValues.add(new BigDecimal(res.getLong("count")));
	        }
		    
	        model.dataset.add(minValues);
	        model.dataset.add(maxValues);
	        model.dataset.add(avgValues);
	        model.dataset.add(countValues);
	        
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
	        
	        int i = (limit == 0 ? Integer.MAX_VALUE : limit);
	        while (i > 0 && res.next()) 
	        {
	        	System.out.println(res.getString("descrizione"));
	        	System.out.println(res.getString("val"));
	        	
	        	String descrizione = res.getString("descrizione");
	        	int count = res.getInt("val");
	        	System.out.println("count " + count);
	        	try
	        	{
	        		Toponym toponym = searchForToponym(descrizione);
		        	if(toponym != null)
		      	  	{	
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
	
	public ArrayList<HeatmapItem> heatmapPrestazioniUOP(String prestazioni, String startData, String endData, int limit)
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
	        //select * from dwh_mis_cup, dec_unita_eroganti, quartieri where dwh_mis_cup.sa_uop_codice_id = dec_unita_eroganti.sa_uop_codice_id and dec_unita_eroganti.struttura_comune = codcomune
	        String qry = "SELECT quartieri.descrizione as descrizione, COUNT(sa_data_ins) as val FROM dwh_mis_cup, quartieri, prestazioni, dec_unita_eroganti WHERE quartieri.quartiere = '0' AND dwh_mis_cup.sa_uop_codice_id = dec_unita_eroganti.sa_uop_codice_id AND dec_unita_eroganti.struttura_comune = codcomune AND (dwh_mis_cup.sa_stato_pren::text = 'N'::text or dwh_mis_cup.sa_stato_pren::text is null) AND sa_pre_id = codice AND " + wherePrestazioni;
	        			        		
	        if(startData != null)
	        	qry += " AND sa_data_pren >= '" + startData + "'";
	        
	        if(endData != null)
	        	qry += " AND sa_data_pren <= '" + endData + "'";
	        		
	        qry += " GROUP BY quartieri.descrizione ORDER BY val DESC";
	        
	        System.out.println(qry);
	        
	        ResultSet res = cmd.executeQuery(qry);
	 
	        ArrayList<HeatmapItem> values = new ArrayList<HeatmapItem>();
	        
	        // Stampiamone i risultati riga per riga
	        
	        int i = limit == 0 ? Integer.MAX_VALUE : limit;
	        while (i > 0 && res.next()) 
	        {
	        	//System.out.println(res.getString("descrizione"));
	        	//System.out.println(res.getString("val"));
	        	
	        	String descrizione = res.getString("descrizione");
	        	int count = res.getInt("val");
	        	//System.out.println("count " + count);
	        	try
	        	{
	        		Toponym toponym = searchForToponym(descrizione);
		        	if(toponym != null)
		      	  	{		
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
	
	public ArrayList<HeatmapItem> heatmapPrestazioniUOPEx(String prestazioni, String startData, String endData, int limit)
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
	        //AND (NOT dec_unita_eroganti.struttura_comune = sa_comune_id) 
	        //select * from dwh_mis_cup, dec_unita_eroganti, quartieri where dwh_mis_cup.sa_uop_codice_id = dec_unita_eroganti.sa_uop_codice_id and dec_unita_eroganti.struttura_comune = codcomune
	        String qry = "SELECT quartieri.descrizione as comune_asl, sa_comune_id as id_residenza, COUNT(sa_data_ins) as val FROM dwh_mis_cup, quartieri, prestazioni, dec_unita_eroganti WHERE "  + wherePrestazioni + " AND quartieri.quartiere = '0' AND dwh_mis_cup.sa_uop_codice_id = dec_unita_eroganti.sa_uop_codice_id AND dec_unita_eroganti.struttura_comune = codcomune AND (NOT dec_unita_eroganti.struttura_comune = sa_comune_id) AND (dwh_mis_cup.sa_stato_pren::text = 'N'::text or dwh_mis_cup.sa_stato_pren::text is null) AND sa_pre_id = codice  GROUP BY comune_asl, sa_comune_id ORDER BY val desc";
	        			        		
	        if(startData != null)
	        	qry += " AND sa_data_pren >= '" + startData + "'";
	        
	        if(endData != null)
	        	qry += " AND sa_data_pren <= '" + endData + "'";
	        			        
	        qry = "SELECT base.comune_asl as comune_asl, quartieri.descrizione as residenza, val from (" + qry + ") as base, quartieri where id_residenza = quartieri.codcomune";

	        System.out.println(qry);
	        
	        ResultSet res = cmd.executeQuery(qry);
	 	        
	        HashMap<String, HeatmapItem> heatmapItemMap = new HashMap<>();
	        
	        // Stampiamone i risultati riga per riga
	       	        
	        WebService.setUserName("ugos"); // add your username here
	        WebService.setGeoNamesServer("http://api.geonames.org");
	        int i = limit == 0 ? Integer.MAX_VALUE : limit;
	        while (i > 0 && res.next()) 
	        {
	        	//System.out.println(res.getString("descrizione"));
	        	//System.out.println(res.getString("val"));
	        	
	        	try
	        	{
		        	String residenza = res.getString("residenza");
		        	String comune_asl = res.getString("comune_asl");
		        	
		        	Toponym residenzaToponym = searchForToponym(residenza);
		        	Toponym comuneAslToponym = searchForToponym(comune_asl);
		        			      	  		        		        		      		        
		        	if(comuneAslToponym != null && residenzaToponym != null)
		        	{
		        		  int count = res.getInt("val");
		        		  HeatmapItem item;
		        		  if(heatmapItemMap.containsKey(comune_asl))
		        		  {
		        			  item = heatmapItemMap.get(comune_asl);
		        			  item.weight += count;
		        		  }
		        		  else
		        		  {
		        			  item = new HeatmapItem();
		        		  
				      		  item.lat = comuneAslToponym.getLatitude();
				      		  item.lon = comuneAslToponym.getLongitude();
				      		  item.weight = count;
				      		  item.name = comune_asl;
				      		  item.distanceArray = new ArrayList<>();  
				      		  heatmapItemMap.put(comune_asl, item);
		        		  }
		        		  
		        		  double distance = distance(item.lat, item.lon, 0, residenzaToponym.getLatitude(), residenzaToponym.getLongitude(), 0);
		        		  
		        		  if(distance > item.maxDistance)
		        		  {
		        			  item.maxDistance = distance;
		        			  item.maxComune = residenza;
		        		  }
		        		  
			      		  item.distanceArray.add(distance);			      		  
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
		    
		    for(HeatmapItem item : heatmapItemMap.values())
		    {
		    	DoubleSummaryStatistics stats = item.distanceArray.stream()
                        .mapToDouble((x) -> x)
                        .summaryStatistics();
        	    
		    	item.maxDistance = (int)stats.getMax();	 			
	 			item.minDistance = (int) stats.getMin();
	 			item.averageDistance = (int)stats.getAverage();	 			
		    }
		    
		    return new ArrayList<HeatmapItem>(heatmapItemMap.values());
	    } 
		catch (SQLException e) 
		{
	         e.printStackTrace();
	    } 
				
		return null;
	}
	
	/**
	 * Calculate distance between two points in latitude and longitude taking
	 * into account height difference. If you are not interested in height
	 * difference pass 0.0. Uses Haversine method as its base.
	 * 
	 * lat1, lon1 Start point lat2, lon2 End point el1 Start altitude in meters
	 * el2 End altitude in meters
	 * @returns Distance in Meters
	 */
	public static double distance(double lat1, double lon1, double el1, double lat2, 
	        double lon2, double el2) {

	    final int R = 6371; // Radius of the earth

	    double latDistance = Math.toRadians(lat2 - lat1);
	    double lonDistance = Math.toRadians(lon2 - lon1);
	    double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
	            + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
	            * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
	    double distance = R * c * 1000; // convert to meters

	    double height = el1 - el2;

	    distance = Math.pow(distance, 2) + Math.pow(height, 2);

	    return Math.sqrt(distance) / 1000;
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
	        		Toponym toponym = searchForToponym(descrizione);
	        			        			      	  	
		      	  	if(toponym != null)
		      	  	{			      	  	
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
		public java.util.Date start;
		public int count;
		public HashMap<String, Item> children;
		public ArrayList<Long> days;
	}
	
	public HashMap<String, Item> pathPrestazioniNelTempo(String primaPrestazione, String startData, String endData, int gender, int limitUser, int anni, String eta)
	{		
		try 
		{
		    // Creiamo un oggetto Statement per poter interrogare il db
	        Statement cmd = con.createStatement ();
	 
	        // Eseguiamo una query e immagazziniamone i risultati
	        // in un oggetto ResultSet
	        String qry = "SELECT assistito, prestazione, data_inserimento, data_appuntamento FROM branche_prestazioni_data_paziente";
	        			        		
	        if(gender != 0)
	        {
	        	qry +=  " where sesso = '" + gender + "' AND " ;
	        }
	        else
	        {
	        	qry +=  " where ";
	        }
	        
	        if(startData != null)
	        {
	        	int year = Integer.parseInt(startData);
	        	qry += " data_appuntamento >= '01/01/" + startData + "' AND data_appuntamento <= '01/01/" + (year + anni) + "'";
	        }
	        
	        if(eta != null)
	        {
	        	qry += " AND eta_range = '" + eta + "'";
	        }
	        
//	        if(endData != null)
//	        	qry += " AND data_appuntamento <= '" + endData + "'";
//	        			        
	        //qry += " limit 800000";
	        
	        System.out.println(qry);
	        System.out.println("limit user " + limitUser);
	        
	        ResultSet res = cmd.executeQuery(qry);
	 
	        HashMap<String, Item> root = new HashMap<>();
	        HashMap<String, Item> currentNode = root;
	        
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        
	        Date startDate = null;
   		 	     
	        if(limitUser == 0)
	        	limitUser = Integer.MAX_VALUE;
	        	
	        int userCount = 0;
	        int currentUser = Integer.MIN_VALUE;
	        Item parentItem = null;
	        Item item = null;
	        //System.out.println("anni " + anni);
	        while (res.next() && userCount < limitUser)
	        {
	        	int user = res.getInt("assistito");
        		String prestazione = res.getString("prestazione");
        		
	        	if(user != currentUser)
	        	{
	        		startDate = null;
	        				
	        		while(!res.isAfterLast() && !primaPrestazione.equals(prestazione))
	        		{
	        			int user1 = user;
	        			while (user1 == user && res.next()) 
	        	        {
	        	        	user1 = res.getInt("assistito");	        	        	
	        	        }
	        		
	        			user = user1;
	        			if(!res.isAfterLast())
	        				prestazione = res.getString("prestazione");
	        		}
	        		
	        		if(res.isAfterLast())
	        			continue;
	        	
	        		userCount++;
	        		
	        		//System.out.println(userCount);	        			        	
	        		
	        			        		
	        		currentUser = user;
	        		currentNode = root;
	        		
	        		if(anni > 0)
	        		{
		        		try {
							startDate = dateFormat.parse(res.getString("data_appuntamento"));							
							System.out.println("data appuntamento " + startDate.toString());
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	        		}
	        		
	        	}
	        	
	        	if(startDate != null)
        		{	        	
	        		 try {
		        		 Date currentDate = dateFormat.parse(res.getString("data_appuntamento"));
		        		 
		        		 long difference = currentDate.getTime() - startDate.getTime();
		        		 
		        		 long years = (difference / 1000) / 86400 / 365;
		        		 
		        		 System.out.println("years " + years);
		        		 
		        		 if(years > anni)
		        			 continue;
		        		 
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
        		}
        		if(currentNode.containsKey(prestazione))
        		{
        			item = currentNode.get(prestazione);	     
        			item.count++;
        		}
        		else
        		{
        			parentItem = item;
        			//System.out.println("parentItem " + parentItem);
        			
        			item = new Item();
        			try {
						item.start = dateFormat.parse(res.getString("data_appuntamento"));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
        			item.count = 1;
        			item.days = new ArrayList<>();
        			item.children = new HashMap<>();
        			currentNode.put(prestazione, item);
        		}	        			        		
        		
        		java.util.Date appuntamento;
				try {
					appuntamento = dateFormat.parse(res.getString("data_appuntamento"));
					
					long difference = parentItem != null ? (Math.abs(appuntamento.getTime() - parentItem.start.getTime())) : 0;
	        	    long differenceDates = difference / (24 * 60 * 60 * 1000);
	        	    
	        	    item.days.add(new Long(differenceDates));
	        	    	        	    	        	   
//	        	    if(item.maxDay < differenceDates)
//	        	    	item.maxDay = differenceDates;
//	        	    
//	        	    if(item.minDay > differenceDates)
//	        	    	item.minDay = differenceDates;	        	    	       	        	    
	        	    
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
	
	private Toponym searchForToponym(String name)
	{
		Toponym toponym = null;
	
		if(geoplaceMap.containsKey(name))
		{
			toponym = geoplaceMap.get(name);	        		
		}
		else
		{
			
			try {
				// Creiamo un oggetto Statement per poter interrogare il db
				Statement cmd = con.createStatement ();
 
				// Eseguiamo una query e immagazziniamone i risultati
				// in un oggetto ResultSet
				String qry = "SELECT * FROM geoname where LOWER(name) = LOWER('" + name.replace("'",  "''") + "') AND (fcode = 'ADM3')";
							        		
				System.out.println(qry);
 
				ResultSet res = cmd.executeQuery(qry);
 
				if (res.next())
				{
					toponym = new Toponym();
				
					System.out.println(res.getString("name"));
					
					toponym.setName(res.getString("name"));
					toponym.setLatitude(res.getDouble("latitude"));
					toponym.setLongitude(res.getDouble("longitude"));
				}
				
				res.close();
				cmd.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
//			ToponymSearchCriteria searchCriteria = new ToponymSearchCriteria();
//  	  		searchCriteria.setNameStartsWith(name);// + ", campania");
//  	  		searchCriteria.setLanguage("it");
//  	  		searchCriteria.setFeatureCode("ADM3");
//  	  		ToponymSearchResult searchResult;
//			try {
//				searchResult = WebService.search(searchCriteria);
//				List<Toponym> toponyms = searchResult.getToponyms();
//		  	  	
//		  	  	if(toponyms.size() > 0)
//		  	  	{
//		  	  		toponym = toponyms.get(0);
//		  	  		geoplaceMap.put(name, toponym);
//		  	  	}
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//  	  	
//	  	  	
//  	  	}	        		
		
		return toponym;
	}
	
//	ToponymSearchCriteria searchCriteria = new ToponymSearchCriteria();
//	  searchCriteria.setQ("zurich");
//	  ToponymSearchResult searchResult = WebService.search(searchCriteria);
//	  for (Toponym toponym : searchResult.toponyms) {
//	     System.out.println(toponym.getName()+" "+ toponym.getCountryName());
//	  }
}
