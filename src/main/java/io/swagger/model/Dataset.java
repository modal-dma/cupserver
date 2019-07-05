package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Dataset
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-06-12T09:36:36.471Z")

public class Dataset   {
  @JsonProperty("labels")
  @Valid
  private List<String> labels = new ArrayList<String>();

  @JsonProperty("data")
  @Valid
  private List<ArrayList<BigDecimal>> data = new ArrayList<ArrayList<BigDecimal>>();

  public Dataset labels(List<String> labels) {
    this.labels = labels;
    return this;
  }

  public Dataset addLabelsItem(String labelsItem) {
    this.labels.add(labelsItem);
    return this;
  }

  /**
   * Get labels
   * @return labels
  **/
  @ApiModelProperty(example = "[\"Red\",\"Blue\",\"Yellow\",\"Green\",\"Purple\",\"Orange\"]", required = true, value = "")
  @NotNull


  public List<String> getLabels() {
    return labels;
  }

  public void setLabels(List<String> labels) {
    this.labels = labels;
  }

  public Dataset data(List<ArrayList<BigDecimal>> data) {
    this.data = data;
    return this;
  }

//  public Dataset addDataItem(BigDecimal dataItem) {
//    this.data.add(dataItem);
//    return this;
//  }

  /**
   * Get data
   * @return data
  **/
  @ApiModelProperty(example = "[12,19,3,5,2,3]", required = true, value = "")
  @NotNull

  @Valid

  public List<ArrayList<BigDecimal>> getData() {
    return data;
  }

  public void setData(List<ArrayList<BigDecimal>> data) {
    this.data = data;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Dataset dataset = (Dataset) o;
    return Objects.equals(this.labels, dataset.labels) &&
        Objects.equals(this.data, dataset.data);
  }

  @Override
  public int hashCode() {
    return Objects.hash(labels, data);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Dataset {\n");
    
    sb.append("    labels: ").append(toIndentedString(labels)).append("\n");
    sb.append("    data: ").append(toIndentedString(data)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

