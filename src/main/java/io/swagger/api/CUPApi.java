/**
 * NOTE: This class is auto generated by the swagger code generator program (2.4.5).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package io.swagger.api;

import java.util.ArrayList;
import java.util.HashMap;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.model.Dataset;
import io.swagger.model.Dataset3D;
import io.swagger.model.HeatmapItem;
import io.swagger.model.PathNode;
import io.swagger.model.TreemapDataset;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-06-12T09:36:36.471Z")

@Api(value = "prestazioniPerBranca", description = "the prestazioniPerBranca API")
public interface CUPApi {

    @ApiOperation(value = "Prestazioni Per Branca nel tempo", nickname = "prestazioniBrancaNelTempo", notes = "prestazioniBrancaNelTempo ", response = Dataset.class, responseContainer = "List", tags={ "developers", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "related dataset", response = Dataset.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "bad input parameter") })
    @RequestMapping(value = "/prestazioniBrancaNelTempo",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<Dataset> prestazioniBrancaNelTempo(@ApiParam(value = "branca") @Valid @RequestParam(value = "branca", required = false) String branca);

    @ApiOperation(value = "Prestazioni Per Branca", nickname = "prestazioniPerBranca", notes = "prestazioniPerBranca ", response = Dataset.class, responseContainer = "List", tags={ "developers", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "related dataset", response = Dataset.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "bad input parameter") })
    @RequestMapping(value = "/prestazioniPerBranca",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<Dataset> prestazioniPerBranca(@ApiParam(value = "comune (opzionale)") @Valid @RequestParam(value = "comune", required = false) String comune, @ApiParam(value = "data inizio(opzionale)") @Valid @RequestParam(value = "startdate", required = false) String startDate, @ApiParam(value = "datafine (opzionale)") @Valid @RequestParam(value = "enddate", required = false) String endDate);

    @ApiOperation(value = "Prestazioni Altre Branche", nickname = "prestazioniAltreBranche", notes = "prestazioniAltreBranche ", response = Dataset.class, responseContainer = "List", tags={ "developers", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "related dataset", response = Dataset.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "bad input parameter") })
    @RequestMapping(value = "/prestazioniAltreBranche",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<Dataset> prestazioniAltreBranche(@ApiParam(value = "data inizio(opzionale)") @Valid @RequestParam(value = "startdate", required = false) String startDate, @ApiParam(value = "datafine (opzionale)") @Valid @RequestParam(value = "enddate", required = false) String endDate);

    
    @ApiOperation(value = "Prestazioni Per Branca per Comune", nickname = "prestazioniPerBrancaPerComune", notes = "prestazioniPerBrancaPerComune", response = Dataset3D.class, responseContainer = "List", tags={ "developers", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "related dataset", response = Dataset.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "bad input parameter") })
    @RequestMapping(value = "/prestazioniPerBrancaPerComune",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<Dataset3D> prestazioniPerBrancaPerComune(@ApiParam(value = "data inizio(opzionale)") @Valid @RequestParam(value = "startdate", required = false) String startDate, @ApiParam(value = "datafine (opzionale)") @Valid @RequestParam(value = "enddate", required = false) String endDate);

    
    @ApiOperation(value = "Attesa Per Branca", nickname = "attesaPerBranca", notes = "attesaPerBranca ", response = Dataset.class, responseContainer = "List", tags={ "developers", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "related dataset", response = Dataset.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "bad input parameter") })
    @RequestMapping(value = "/attesaPerBranca",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<Dataset> attesaPerBranca(@ApiParam(value = "id comune (opzionale)") @Valid @RequestParam(value = "comuneId", required = false) String comuneId, @ApiParam(value = "data inizio(opzionale)") @Valid @RequestParam(value = "startdate", required = false) String startDate, @ApiParam(value = "datafine (opzionale)") @Valid @RequestParam(value = "enddate", required = false) String endDate);

    @ApiOperation(value = "Attesa Disponibilità Per Branca", nickname = "attesaDisponibilitaPerBranca", notes = "attesaDisponibilitaPerBranca ", response = Dataset.class, responseContainer = "List", tags={ "developers", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "related dataset", response = Dataset.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "bad input parameter") })
    @RequestMapping(value = "/attesaDisponibilitaPerBranca",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<Dataset> attesaDisponibilitaPerBranca(@ApiParam(value = "id comune (opzionale)") @Valid @RequestParam(value = "comuneId", required = false) String comuneId, @ApiParam(value = "data inizio(opzionale)") @Valid @RequestParam(value = "startdate", required = false) String startDate, @ApiParam(value = "datafine (opzionale)") @Valid @RequestParam(value = "enddate", required = false) String endDate);
    
    @ApiOperation(value = "Branche", nickname = "branche", notes = "branche", response = Dataset.class, responseContainer = "List", tags={ "developers", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "related dataset", response = Dataset.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "bad input parameter") })
    @RequestMapping(value = "/branche",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<ArrayList<String>> branche(@ApiParam(value = "data inizio(opzionale)") @Valid @RequestParam(value = "startdate", required = false) String startDate, @ApiParam(value = "datafine (opzionale)") @Valid @RequestParam(value = "enddate", required = false) String endDate);
    
    @ApiOperation(value = "Comuni", nickname = "comuni", notes = "comuni", response = Dataset.class, responseContainer = "List", tags={ "developers", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "related dataset", response = Dataset.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "bad input parameter") })
    @RequestMapping(value = "/comuni",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<ArrayList<String>> comuni(@ApiParam(value = "data inizio(opzionale)") @Valid @RequestParam(value = "startdate", required = false) String startDate, @ApiParam(value = "datafine (opzionale)") @Valid @RequestParam(value = "enddate", required = false) String endDate);

    @ApiOperation(value = "Tipo Prestazioni", nickname = "tipoPrestazioni", notes = "tipoPrestazioni", response = Dataset.class, responseContainer = "List", tags={ "developers", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "related dataset", response = Dataset.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "bad input parameter") })
    @RequestMapping(value = "/tipoPrestazioni",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<Dataset> tipoPrestazione(@ApiParam(value = "comune (opzionale)") @Valid @RequestParam(value = "comune", required = false) String comune, @ApiParam(value = "data inizio(opzionale)") @Valid @RequestParam(value = "startdate", required = false) String startDate, @ApiParam(value = "datafine (opzionale)") @Valid @RequestParam(value = "enddate", required = false) String endDate, @ApiParam(value = "limit (opzionale)") @Valid @RequestParam(value = "limit", required = false) Integer limit);

    @ApiOperation(value = "Heatmap Prestazioni", nickname = "heatmapPrestazioni", notes = "heatmapPrestazioni", response = ArrayList.class, responseContainer = "List", tags={ "developers", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "related dataset", response = Dataset.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "bad input parameter") })
    @RequestMapping(value = "/heatmapPrestazioni",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<ArrayList<HeatmapItem>> heatmapPrestazioni(@ApiParam(value = "prestazione") @Valid @RequestParam(value = "prestazione", required = true) String prestazione, @ApiParam(value = "data inizio(opzionale)") @Valid @RequestParam(value = "startdate", required = false) String startDate, @ApiParam(value = "datafine (opzionale)") @Valid @RequestParam(value = "enddate", required = false) String endDate, @ApiParam(value = "limit (opzionale)") @Valid @RequestParam(value = "limit", required = false) Integer limit);

    @ApiOperation(value = "Prestazioni", nickname = "prestazioni", notes = "prestazioni", response = Dataset.class, responseContainer = "List", tags={ "developers", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "related dataset", response = ArrayList.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "bad input parameter") })
    @RequestMapping(value = "/prestazioni",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<ArrayList<String>> prestazioni(@ApiParam(value = "data inizio(opzionale)") @Valid @RequestParam(value = "startdate", required = false) String startDate, @ApiParam(value = "datafine (opzionale)") @Valid @RequestParam(value = "enddate", required = false) String endDate);

    @ApiOperation(value = "Heatmap Prestazioni", nickname = "heatmapBranche", notes = "heatmapBranche", response = ArrayList.class, responseContainer = "List", tags={ "developers", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "related dataset", response = Dataset.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "bad input parameter") })
    @RequestMapping(value = "/heatmapBranche",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<ArrayList<HeatmapItem>> heatmapBranche(@ApiParam(value = "branca") @Valid @RequestParam(value = "branca", required = true) String branca, @ApiParam(value = "data inizio(opzionale)") @Valid @RequestParam(value = "startdate", required = false) String startDate, @ApiParam(value = "datafine (opzionale)") @Valid @RequestParam(value = "enddate", required = false) String endDate, @ApiParam(value = "limit (opzionale)") @Valid @RequestParam(value = "limit", required = false) Integer limit);

    @ApiOperation(value = "Attesa Disponibilità Per Branca", nickname = "prenotazioniPerPrestazioneDopoPrestazione", notes = "prenotazioniPerBrancaDopoBranca ", response = Dataset.class, responseContainer = "List", tags={ "developers", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "related dataset", response = Dataset.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "bad input parameter") })
    @RequestMapping(value = "/prenotazioniPerPrestazioneDopoPrestazione",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<Dataset> prenotazioniPerPrestazioneDopoPrestazione(@ApiParam(value = "prestazione") @Valid @RequestParam(value = "prestazione", required = true) String prestazione, @ApiParam(value = "id comune (opzionale)") @Valid @RequestParam(value = "comuneId", required = false) String comuneId, @ApiParam(value = "data inizio(opzionale)") @Valid @RequestParam(value = "startdate", required = false) String startDate, @ApiParam(value = "datafine (opzionale)") @Valid @RequestParam(value = "enddate", required = false) String endDate);

    @ApiOperation(value = "Attesa Disponibilità Per Branca", nickname = "prenotazioniPerBrancaDopoBranca", notes = "prenotazioniPerBrancaDopoBranca ", response = Dataset.class, responseContainer = "List", tags={ "developers", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "related dataset", response = Dataset.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "bad input parameter") })
    @RequestMapping(value = "/prenotazioniPerBrancaDopoBranca",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<Dataset> prenotazioniPerBrancaDopoBranca(@ApiParam(value = "branca") @Valid @RequestParam(value = "branca", required = true) String branca, @ApiParam(value = "id comune (opzionale)") @Valid @RequestParam(value = "comuneId", required = false) String comuneId, @ApiParam(value = "data inizio(opzionale)") @Valid @RequestParam(value = "startdate", required = false) String startDate, @ApiParam(value = "datafine (opzionale)") @Valid @RequestParam(value = "enddate", required = false) String endDate);

    @ApiOperation(value = "Prestazioni Per Branca", nickname = "branchePrestazioni", notes = "branchePrestazioni", response = Dataset.class, responseContainer = "List", tags={ "developers", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "related dataset", response = HashMap.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "bad input parameter") })
    @RequestMapping(value = "/branchePrestazioni",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<HashMap<String, ArrayList<TreemapDataset>>> branchePrestazioni(@ApiParam(value = "id comune (opzionale)") @Valid @RequestParam(value = "comuneId", required = false) String comuneId, @ApiParam(value = "data inizio(opzionale)") @Valid @RequestParam(value = "startdate", required = false) String startDate, @ApiParam(value = "datafine (opzionale)") @Valid @RequestParam(value = "enddate", required = false) String endDate);

    @ApiOperation(value = "Prestazioni Per Eta", nickname = "branchePrestazioni", notes = "branchePrestazioni", response = Dataset.class, responseContainer = "List", tags={ "developers", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "related dataset", response = HashMap.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "bad input parameter") })
    @RequestMapping(value = "/etaPrestazioni",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<HashMap<String, ArrayList<TreemapDataset>>> etaPrestazioni(@ApiParam(value = "id comune (opzionale)") @Valid @RequestParam(value = "comuneId", required = false) String comuneId, @ApiParam(value = "data inizio(opzionale)") @Valid @RequestParam(value = "startdate", required = false) String startDate, @ApiParam(value = "datafine (opzionale)") @Valid @RequestParam(value = "enddate", required = false) String endDate);

    @ApiOperation(value = "Prestazioni nel tempo", nickname = "branchePrestazioni", notes = "branchePrestazioni", response = Dataset.class, responseContainer = "List", tags={ "developers", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "related dataset", response = HashMap.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "bad input parameter") })
    @RequestMapping(value = "/pathPrestazioniNelTempo",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<PathNode> pathPrestazioniNelTempo(@ApiParam(value = "id comune (opzionale)") @Valid @RequestParam(value = "comuneId", required = false) String comuneId, @ApiParam(value = "data inizio(opzionale)") @Valid @RequestParam(value = "startdate", required = false) String startDate, @ApiParam(value = "datafine (opzionale)") @Valid @RequestParam(value = "enddate", required = false) String endDate, @ApiParam(value = "gender (opzionale)") @Valid @RequestParam(value = "gender", required = false) String gender, @ApiParam(value = "limitUser (opzionale)") @Valid @RequestParam(value = "limitUser", required = false) Integer limitUser, @ApiParam(value = "anni (opzionale)") @Valid @RequestParam(value = "anni", required = false) Integer anni, @ApiParam(value = "eta (opzionale)") @Valid @RequestParam(value = "eta", required = false) String eta);

    @ApiOperation(value = "etaPerPrestazione", nickname = "etaPerPrestazione", notes = "etaPerPrestazione", response = Dataset.class, responseContainer = "List", tags={ "developers", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "related dataset", response = Dataset.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "bad input parameter") })
    @RequestMapping(value = "/etaPerPrestazione",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<Dataset> etaPerPrestazione(@ApiParam(value = "prestazione(opzionale)") @Valid @RequestParam(value = "prestazione", required = false) String prestazione, @ApiParam(value = "data inizio(opzionale)") @Valid @RequestParam(value = "startdate", required = false) String startDate, @ApiParam(value = "datafine (opzionale)") @Valid @RequestParam(value = "enddate", required = false) String endDate);
    
    @ApiOperation(value = "prestazioniPerEta", nickname = "prestazioniPerEta", notes = "prestazioniPerEta", response = Dataset.class, responseContainer = "List", tags={ "developers", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "related dataset", response = Dataset.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "bad input parameter") })
    @RequestMapping(value = "/prestazioniPerEta",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<Dataset> prestazioniPerEta(@ApiParam(value = "prestazione(opzionale)") @Valid @RequestParam(value = "prestazione", required = false) String prestazione, @ApiParam(value = "data inizio(opzionale)") @Valid @RequestParam(value = "startdate", required = false) String startDate, @ApiParam(value = "datafine (opzionale)") @Valid @RequestParam(value = "enddate", required = false) String endDate);
    
    @ApiOperation(value = "eta", nickname = "eta", notes = "eta", response = Dataset.class, responseContainer = "List", tags={ "developers", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "related dataset", response = ArrayList.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "bad input parameter") })
    @RequestMapping(value = "/eta",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<ArrayList<String>> eta();
    
    
}
