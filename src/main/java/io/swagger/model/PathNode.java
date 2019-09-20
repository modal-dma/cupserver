package io.swagger.model;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-06-12T09:36:36.471Z")
public class PathNode {

	  @JsonProperty("name")
	  @Valid
	  public String name;
	  
	  @JsonProperty("count")
	  @Valid
	  public int count;
	  
	  @JsonProperty("max")
	  @Valid
	  public int max = Integer.MAX_VALUE;
	  
	  @JsonProperty("min")
	  @Valid
	  public int min = Integer.MIN_VALUE;
	  
	  @JsonProperty("average")
	  @Valid
	  public int average;
	  
	  @JsonProperty("childrenCount")
	  @Valid
	  public int childrenCount;
	  
	  @JsonProperty("children")
	  @Valid
	  public List<PathNode> children = new ArrayList<PathNode>();
}
