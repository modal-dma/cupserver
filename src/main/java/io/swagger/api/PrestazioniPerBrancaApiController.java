package io.swagger.api;

import io.swagger.model.Dataset;
import io.swagger.model.Dataset3D;
import io.swagger.model.HeatmapItem;
import io.swagger.model.PathNode;
import io.swagger.model.TreemapDataset;
import io.swagger.model.Value3D;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.modal.db.BaseModel;
import com.modal.db.BaseModel3D;
import com.modal.db.BaseModel3D.Point3D;
import com.modal.db.DBAPI;
import com.modal.db.DBAPI.Item;

import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-06-12T09:36:36.471Z")

@Controller
public class PrestazioniPerBrancaApiController implements PrestazioniPerBrancaApi {

    private static final Logger log = LoggerFactory.getLogger(PrestazioniPerBrancaApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public PrestazioniPerBrancaApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<Dataset> prestazioniPerBranca(@ApiParam(value = "comune (opzionale)") @Valid @RequestParam(value = "comune", required = false) String comune, @ApiParam(value = "data inizio(opzionale)") @Valid @RequestParam(value = "startdate", required = false) String startDate, @ApiParam(value = "datafine (opzionale)") @Valid @RequestParam(value = "enddate", required = false) String endDate)
    {
            try {
            	
            	DBAPI dbapi = DBAPI.getInstance();
        		
        		BaseModel model = dbapi.prestazioniPerBranca(comune, startDate, endDate);
        		
        		Dataset dataset = new Dataset();
        		
        		dataset.labels(model.labels);
        		dataset.data(model.dataset);
        		
        		HttpHeaders headers = new HttpHeaders();
            	headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
            	headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "GET, POST");
            	
                return new ResponseEntity<Dataset>(dataset, headers, HttpStatus.OK);
            } catch (Exception e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<Dataset>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
    }

    public ResponseEntity<Dataset> prestazioniBrancaNelTempo(@ApiParam(value = "branca") @Valid @RequestParam(value = "branca", required = false) String branca)
    {
    	 try {
         	
         	DBAPI dbapi = DBAPI.getInstance();
     		
     		BaseModel model = dbapi.prestazioniBrancaNelTempo(branca);
     		
     		Dataset dataset = new Dataset();
     		
     		dataset.labels(model.labels);
     		dataset.data(model.dataset);
     		
     		HttpHeaders headers = new HttpHeaders();
         	headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
         	headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "GET, POST");
         	
             return new ResponseEntity<Dataset>(dataset, headers, HttpStatus.OK);
         } catch (Exception e) {
             log.error("Couldn't serialize response for content type application/json", e);
             return new ResponseEntity<Dataset>(HttpStatus.INTERNAL_SERVER_ERROR);
         }
    }

    public ResponseEntity<Dataset3D> prestazioniPerBrancaPerComune(@ApiParam(value = "data inizio(opzionale)") @Valid @RequestParam(value = "startdate", required = false) String startDate, @ApiParam(value = "datafine (opzionale)") @Valid @RequestParam(value = "enddate", required = false) String endDate)
    {
    	 try {
         	
         	DBAPI dbapi = DBAPI.getInstance();
     		
     		BaseModel3D model = dbapi.prestazioniPerBrancaPerComune(startDate, endDate);
     		
     		Dataset3D dataset = new Dataset3D();
     		
     		for(Point3D p : model.points)
     		{
     			if(p.val.longValue() > 200)
     			{
	     			Value3D value = new Value3D();
	     			value.x = p.xLabel;
	     			value.y = p.yLabel;
	     			value.value = p.val;
	     			
	     			dataset.values.add(value);
     			}
     		}
     		
     		HttpHeaders headers = new HttpHeaders();
         	headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
         	headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "GET, POST");
         	
             return new ResponseEntity<Dataset3D>(dataset, headers, HttpStatus.OK);
         } catch (Exception e) {
             log.error("Couldn't serialize response for content type application/json", e);
             return new ResponseEntity<Dataset3D>(HttpStatus.INTERNAL_SERVER_ERROR);
         }
    }
    
    public ResponseEntity<Dataset> attesaPerBranca(@ApiParam(value = "comune (opzionale)") @Valid @RequestParam(value = "comune", required = false) String comune, @ApiParam(value = "data inizio(opzionale)") @Valid @RequestParam(value = "startdate", required = false) String startDate, @ApiParam(value = "datafine (opzionale)") @Valid @RequestParam(value = "enddate", required = false) String endDate) 
    {
          try {
          	
          	DBAPI dbapi = DBAPI.getInstance();
      		
      		BaseModel model = dbapi.attesaPerBranca(comune, startDate, endDate);
      		
      		Dataset dataset = new Dataset();
      		
      		dataset.labels(model.labels);
      		dataset.data(model.dataset);
      		
      		HttpHeaders headers = new HttpHeaders();
          	headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
          	headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "GET, POST");
          	
              return new ResponseEntity<Dataset>(dataset, headers, HttpStatus.OK);
          } catch (Exception e) {
              log.error("Couldn't serialize response for content type application/json", e);
              return new ResponseEntity<Dataset>(HttpStatus.INTERNAL_SERVER_ERROR);
          }
  }
    
	public ResponseEntity<Dataset> attesaDisponibilitaPerBranca(@ApiParam(value = "comune (opzionale)") @Valid @RequestParam(value = "comune", required = false) String comune, @ApiParam(value = "data inizio(opzionale)") @Valid @RequestParam(value = "startdate", required = false) String startDate, @ApiParam(value = "datafine (opzionale)") @Valid @RequestParam(value = "enddate", required = false) String endDate) 
	{
	      try {
	      	
	      	DBAPI dbapi = DBAPI.getInstance();
	  		
	  		BaseModel model = dbapi.attesaDisponibiitaPerBranca(comune, startDate, endDate);
	  		
	  		Dataset dataset = new Dataset();
	  		
	  		dataset.labels(model.labels);
	  		dataset.data(model.dataset);
	  		
	  		HttpHeaders headers = new HttpHeaders();
	      	headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
	      	headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "GET, POST");
	      	
	          return new ResponseEntity<Dataset>(dataset, headers, HttpStatus.OK);
	      } catch (Exception e) {
	          log.error("Couldn't serialize response for content type application/json", e);
	          return new ResponseEntity<Dataset>(HttpStatus.INTERNAL_SERVER_ERROR);
	      }
	}
	
	public ResponseEntity<Dataset> prenotazioniPerBrancaDopoBranca(@ApiParam(value = "branca") @Valid @RequestParam(value = "branca", required = true) String branca, @ApiParam(value = "id comune (opzionale)") @Valid @RequestParam(value = "comuneId", required = false) String comune, @ApiParam(value = "data inizio(opzionale)") @Valid @RequestParam(value = "startdate", required = false) String startDate, @ApiParam(value = "datafine (opzionale)") @Valid @RequestParam(value = "enddate", required = false) String endDate)
	{
		try {
	      	
	      	DBAPI dbapi = DBAPI.getInstance();
	  		
	  		BaseModel model = dbapi.prenotazioniPerBrancaDopoBranca(branca, comune, startDate, endDate);
	  		
	  		Dataset dataset = new Dataset();
	  		
	  		dataset.labels(model.labels);
	  		dataset.data(model.dataset);
	  		
	  		HttpHeaders headers = new HttpHeaders();
	      	headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
	      	headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "GET, POST");
	      	
	          return new ResponseEntity<Dataset>(dataset, headers, HttpStatus.OK);
	      } catch (Exception e) {
	          log.error("Couldn't serialize response for content type application/json", e);
	          return new ResponseEntity<Dataset>(HttpStatus.INTERNAL_SERVER_ERROR);
	      }
	}
	
	public ResponseEntity<Dataset> prenotazioniPerPrestazioneDopoPrestazione(@ApiParam(value = "prestazione") @Valid @RequestParam(value = "prestazione", required = true) String prestazione, @ApiParam(value = "id comune (opzionale)") @Valid @RequestParam(value = "comuneId", required = false) String comune, @ApiParam(value = "data inizio(opzionale)") @Valid @RequestParam(value = "startdate", required = false) String startDate, @ApiParam(value = "datafine (opzionale)") @Valid @RequestParam(value = "enddate", required = false) String endDate)
	{
		try {
	      	
	      	DBAPI dbapi = DBAPI.getInstance();
	  		
	  		BaseModel model = dbapi.prenotazioniPerPrestazioneDopoPrestazione(prestazione, comune, startDate, endDate);
	  		
	  		Dataset dataset = new Dataset();
	  		
	  		dataset.labels(model.labels);
	  		dataset.data(model.dataset);
	  		
	  		HttpHeaders headers = new HttpHeaders();
	      	headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
	      	headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "GET, POST");
	      	
	          return new ResponseEntity<Dataset>(dataset, headers, HttpStatus.OK);
	      } catch (Exception e) {
	          log.error("Couldn't serialize response for content type application/json", e);
	          return new ResponseEntity<Dataset>(HttpStatus.INTERNAL_SERVER_ERROR);
	      }
	}
	
	public ResponseEntity<ArrayList<String>> branche(@ApiParam(value = "data inizio(opzionale)") @Valid @RequestParam(value = "startdate", required = false) String startDate, @ApiParam(value = "datafine (opzionale)") @Valid @RequestParam(value = "enddate", required = false) String endDate) 
	{
	      try {
	      	
	      	DBAPI dbapi = DBAPI.getInstance();
	  		
	      	ArrayList<String> brancheList = dbapi.branche(startDate, endDate);
	  		
	  		
	  		HttpHeaders headers = new HttpHeaders();
	      	headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
	      	headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "GET, POST");
	      	
	          return new ResponseEntity<ArrayList<String>>(brancheList, headers, HttpStatus.OK);
	      } catch (Exception e) {
	          log.error("Couldn't serialize response for content type application/json", e);
	          return new ResponseEntity<ArrayList<String>>(HttpStatus.INTERNAL_SERVER_ERROR);
	      }
	}
	
	public ResponseEntity<ArrayList<String>> comuni(@ApiParam(value = "data inizio(opzionale)") @Valid @RequestParam(value = "startdate", required = false) String startDate, @ApiParam(value = "datafine (opzionale)") @Valid @RequestParam(value = "enddate", required = false) String endDate) 
	{
	      try {
	      	
	      	DBAPI dbapi = DBAPI.getInstance();
	  		
	      	ArrayList<String> comuniList = dbapi.comuni(startDate, endDate);
	  		
	  		
	  		HttpHeaders headers = new HttpHeaders();
	      	headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
	      	headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "GET, POST");
	      	
	          return new ResponseEntity<ArrayList<String>>(comuniList, headers, HttpStatus.OK);
	      } catch (Exception e) {
	          log.error("Couldn't serialize response for content type application/json", e);
	          return new ResponseEntity<ArrayList<String>>(HttpStatus.INTERNAL_SERVER_ERROR);
	      }
	}
	
	 public ResponseEntity<Dataset> tipoPrestazione(@ApiParam(value = "comune (opzionale)") @Valid @RequestParam(value = "comune", required = false) String comune, @ApiParam(value = "data inizio(opzionale)") @Valid @RequestParam(value = "startdate", required = false) String startDate, @ApiParam(value = "datafine (opzionale)") @Valid @RequestParam(value = "enddate", required = false) String endDate, @ApiParam(value = "limit (opzionale)") @Valid @RequestParam(value = "limit", required = false) Integer limit)
	    {
	            try {
	            	
	            	DBAPI dbapi = DBAPI.getInstance();
	        		
	        		BaseModel model = dbapi.tipoPrestazione(comune, startDate, endDate, limit != null ? limit.intValue() : 0);
	        		
	        		Dataset dataset = new Dataset();
	        		
	        		dataset.labels(model.labels);
	        		dataset.data(model.dataset);
	        		
	        		HttpHeaders headers = new HttpHeaders();
	            	headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
	            	headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "GET, POST");
	            	
	                return new ResponseEntity<Dataset>(dataset, headers, HttpStatus.OK);
	            } catch (Exception e) {
	                log.error("Couldn't serialize response for content type application/json", e);
	                return new ResponseEntity<Dataset>(HttpStatus.INTERNAL_SERVER_ERROR);
	            }
	    }
	 
	 	public ResponseEntity<ArrayList<HeatmapItem>> heatmapPrestazioni(@ApiParam(value = "prestazione") @Valid @RequestParam(value = "prestazione", required = true) String prestazione, @ApiParam(value = "data inizio(opzionale)") @Valid @RequestParam(value = "startdate", required = false) String startDate, @ApiParam(value = "datafine (opzionale)") @Valid @RequestParam(value = "enddate", required = false) String endDate, @ApiParam(value = "limit (opzionale)") @Valid @RequestParam(value = "limit", required = false) Integer limit)
	    {
	            try {
	            	
	            	DBAPI dbapi = DBAPI.getInstance();
	        		
	            	ArrayList<HeatmapItem> heatmap = dbapi.heatmapPrestazioni(prestazione, startDate, endDate, limit != null ? limit.intValue() : 0);
	        		
	        		
	        		HttpHeaders headers = new HttpHeaders();
	            	headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
	            	headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "GET, POST");
	            	
	                return new ResponseEntity<ArrayList<HeatmapItem>>(heatmap, headers, HttpStatus.OK);
	            } catch (Exception e) {
	                log.error("Couldn't serialize response for content type application/json", e);
	                return new ResponseEntity<ArrayList<HeatmapItem>>(HttpStatus.INTERNAL_SERVER_ERROR);
	            }
	    }
	 	
	 	public ResponseEntity<ArrayList<String>> prestazioni(@ApiParam(value = "data inizio(opzionale)") @Valid @RequestParam(value = "startdate", required = false) String startDate, @ApiParam(value = "datafine (opzionale)") @Valid @RequestParam(value = "enddate", required = false) String endDate)
	 	{
	 		try {
		      	
		      	DBAPI dbapi = DBAPI.getInstance();
		  		
		      	ArrayList<String> brancheList = dbapi.prestazioni(startDate, endDate);		  		
		  		
		  		HttpHeaders headers = new HttpHeaders();
		      	headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
		      	headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "GET, POST");
		      	
		          return new ResponseEntity<ArrayList<String>>(brancheList, headers, HttpStatus.OK);
		      } catch (Exception e) {
		          log.error("Couldn't serialize response for content type application/json", e);
		          return new ResponseEntity<ArrayList<String>>(HttpStatus.INTERNAL_SERVER_ERROR);
		      }
	 	}
	 	
	 	public ResponseEntity<ArrayList<HeatmapItem>> heatmapBranche(@ApiParam(value = "branca") @Valid @RequestParam(value = "branca", required = true) String branca, @ApiParam(value = "data inizio(opzionale)") @Valid @RequestParam(value = "startdate", required = false) String startDate, @ApiParam(value = "datafine (opzionale)") @Valid @RequestParam(value = "enddate", required = false) String endDate, @ApiParam(value = "limit (opzionale)") @Valid @RequestParam(value = "limit", required = false) Integer limit)
	 	{
	 		try {
            	
            	DBAPI dbapi = DBAPI.getInstance();
        		
            	ArrayList<HeatmapItem> heatmap = dbapi.heatmapBranche(branca, startDate, endDate, limit != null ? limit.intValue() : 0);
        		
        		
        		HttpHeaders headers = new HttpHeaders();
            	headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
            	headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "GET, POST");
            	
                return new ResponseEntity<ArrayList<HeatmapItem>>(heatmap, headers, HttpStatus.OK);
            } catch (Exception e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<ArrayList<HeatmapItem>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
	 	}

	 	public ResponseEntity<HashMap<String, ArrayList<TreemapDataset>>> branchePrestazioni(@ApiParam(value = "id comune (opzionale)") @Valid @RequestParam(value = "comuneId", required = false) String comuneId, @ApiParam(value = "data inizio(opzionale)") @Valid @RequestParam(value = "startdate", required = false) String startDate, @ApiParam(value = "datafine (opzionale)") @Valid @RequestParam(value = "enddate", required = false) String endDate)
	 	{
	 		try {
		      	
		      	DBAPI dbapi = DBAPI.getInstance();
		  		
		  		BaseModel3D model = dbapi.branchePrestazioni(comuneId, startDate, endDate);
		  		
		  				  		
		  		HashMap<String, ArrayList<TreemapDataset>> dataset = new HashMap<>();
		  		
		  		for(Point3D p : model.points)
	     		{
		  			TreemapDataset data = new TreemapDataset();
		  			
		  			data.title = p.yLabel;
		  			data.value = p.val;
		  			
		  			ArrayList<TreemapDataset> item;
		  			
		  			if(dataset.containsKey(p.xLabel))
		  			{
		  				item = dataset.get(p.xLabel);
		  			}
		  			else
		  			{
		  				item = new ArrayList<>();
		  				dataset.put(p.xLabel, item);
		  			}
		  				
		  			item.add(data);		  				     			
	     		}
		  				  		
		  		HttpHeaders headers = new HttpHeaders();
		      	headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
		      	headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "GET, POST");
		      	
		          return new ResponseEntity<HashMap<String, ArrayList<TreemapDataset>>>(dataset, headers, HttpStatus.OK);
		      } catch (Exception e) {
		          log.error("Couldn't serialize response for content type application/json", e);
		          return new ResponseEntity<HashMap<String, ArrayList<TreemapDataset>>>(HttpStatus.INTERNAL_SERVER_ERROR);
		      }
	 	}
	 	
	 	public ResponseEntity<HashMap<String, ArrayList<TreemapDataset>>> etaPrestazioni(@ApiParam(value = "id comune (opzionale)") @Valid @RequestParam(value = "comuneId", required = false) String comuneId, @ApiParam(value = "data inizio(opzionale)") @Valid @RequestParam(value = "startdate", required = false) String startDate, @ApiParam(value = "datafine (opzionale)") @Valid @RequestParam(value = "enddate", required = false) String endDate)
	 	{
	 		try {
		      	
		      	DBAPI dbapi = DBAPI.getInstance();
		  		
		  		BaseModel3D model = dbapi.etaPrestazioni(comuneId, startDate, endDate);
		  		
		  				  		
		  		HashMap<String, ArrayList<TreemapDataset>> dataset = new HashMap<>();
		  		
		  		for(Point3D p : model.points)
	     		{
		  			TreemapDataset data = new TreemapDataset();
		  			
		  			data.title = p.yLabel;
		  			data.value = p.val;
		  			
		  			ArrayList<TreemapDataset> item;
		  			
		  			if(dataset.containsKey(p.xLabel))
		  			{
		  				item = dataset.get(p.xLabel);
		  			}
		  			else
		  			{
		  				item = new ArrayList<>();
		  				dataset.put(p.xLabel, item);
		  			}
		  				
		  			item.add(data);		  				     			
	     		}
		  				  		
		  		HttpHeaders headers = new HttpHeaders();
		      	headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
		      	headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "GET, POST");
		      	
		          return new ResponseEntity<HashMap<String, ArrayList<TreemapDataset>>>(dataset, headers, HttpStatus.OK);
		      } catch (Exception e) {
		          log.error("Couldn't serialize response for content type application/json", e);
		          return new ResponseEntity<HashMap<String, ArrayList<TreemapDataset>>>(HttpStatus.INTERNAL_SERVER_ERROR);
		      }
	 	}
	 	
	 	public ResponseEntity<PathNode> pathPrestazioniNelTempo(@ApiParam(value = "prestazione") @Valid @RequestParam(value = "prestazione", required = true) String prestazione, @ApiParam(value = "data inizio(opzionale)") @Valid @RequestParam(value = "startdate", required = false) String startDate, @ApiParam(value = "datafine (opzionale)") @Valid @RequestParam(value = "enddate", required = false) String endDate)
	 	{
	 		try {
		      	
		      	DBAPI dbapi = DBAPI.getInstance();
		  		
		      	HashMap<String, Item> root = dbapi.pathPrestazioniNelTempo(prestazione, startDate, endDate);
		      	Item rootItem = ((Item)(root.get(prestazione)));
		  		PathNode rootNode = new PathNode();
		  		rootNode.name = prestazione;
	  			rootNode.count = rootItem.count;
	  			
	  			populatePathNode(rootNode, rootItem.children);
	  			
		  				  	
		  		HttpHeaders headers = new HttpHeaders();
		      	headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
		      	headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "GET, POST");
		      	
		          return new ResponseEntity<PathNode>(rootNode, headers, HttpStatus.OK);
		      } catch (Exception e) {
		          log.error("Couldn't serialize response for content type application/json", e);
		          return new ResponseEntity<PathNode>(HttpStatus.INTERNAL_SERVER_ERROR);
		      }
	 	}
	 	
	 	private void populatePathNode(PathNode node, HashMap<String, Item> children)
	 	{
	 		for(String nodeName : children.keySet())
     		{	 			
	 			Item child = children.get(nodeName);
	 			
	 			PathNode childNode = new PathNode();
	 			childNode.name = nodeName;
	 			childNode.count = child.count;
	 			
	 			node.children.add(childNode);
	 			
	 			populatePathNode(childNode, child.children);
     		}
	 	}
}
