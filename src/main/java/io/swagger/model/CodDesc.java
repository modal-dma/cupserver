package io.swagger.model;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-06-12T09:36:36.471Z")
public class CodDesc {
	@JsonProperty("id")
	@Valid
	public String id;
	
	@JsonProperty("desc")
	@Valid
	public String desc;
	
	
}
