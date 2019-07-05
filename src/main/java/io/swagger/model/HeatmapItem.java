package io.swagger.model;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-06-12T09:36:36.471Z")
public class HeatmapItem {

	@JsonProperty("lat")
	@Valid
	public double lat;
	
	@JsonProperty("lon")
	@Valid
	public double lon;
	
	@JsonProperty("weight")
	@Valid	
	public int weight;
	
	@JsonProperty("name")
	@Valid		
	public String name;
}
