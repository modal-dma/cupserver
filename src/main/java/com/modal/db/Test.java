package com.modal.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import io.swagger.model.HeatmapItem;

public class Test {

	private static final String url = "jdbc:postgresql://192.168.1.20/sanita2";
	private static final String user = "postgres";
	private static final String password = "modal@1618!";
	
	
	public static void main(String args[])
	{
		DBAPI dbapi = DBAPI.getInstance();
		
		List<String> prestazioni = dbapi.prestazioni(null, null);
		List<HeatmapItem> items = dbapi.heatmapPrestazioni(prestazioni.get(100), null, null, 50);
		
		System.out.println(items);
		
		//BaseModel3D model = dbapi.prestazioniPerBrancaPerComune("01/01/2017", "31/12/2017");		
		//System.out.println(model.points);
		
	}
	
	public static void main2(String args[])
	{
		DBAPI dbapi = DBAPI.getInstance();
		
		BaseModel model = dbapi.prestazioniPerBranca(null, "01/01/2017", "31/12/2017");
		
		System.out.println(model.labels);
		
		System.out.println(model.dataset);
		
	}
	
	public static void main1(String args[])
	{
		try 
		{
			// Carichiamo un driver di tipo 1 (bridge jdbc-odbc)
		    String driver = "org.postgresql.Driver";
		    Class.forName(driver);
		    
		    // Creiamo la stringa di connessione
		    // Otteniamo una connessione con username e password
		    Connection con =
		        DriverManager.getConnection (url, user, password);
		 
		    // Creiamo un oggetto Statement per poter interrogare il db
	        Statement cmd = con.createStatement ();
	 
	        // Eseguiamo una query e immagazziniamone i risultati
	 
	        // in un oggetto ResultSet
	        String qry = "SELECT descrizione, COUNT(sa_data_ins) as val FROM dwh_mis_cup, branche WHERE sa_branca_id = id_branca GROUP BY descrizione";
	        //String qry = "SELECT * FROM dwh_mis_cup LIMIT 100";
	        ResultSet res = cmd.executeQuery(qry);
	 
	        // Stampiamone i risultati riga per riga
	        while (res.next()) 
	        {
	        	System.out.println(res.getString("descrizione"));
	        	System.out.println(res.getLong("val"));
	        }
		    
	        res.close();
		    cmd.close();
		    con.close();
	    } 
		catch (SQLException e) 
		{
	         e.printStackTrace();
	    } 
		catch (ClassNotFoundException e) 
		{
		     e.printStackTrace();
		}
	}
}
