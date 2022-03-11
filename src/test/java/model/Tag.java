package model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Tag {

    @JsonProperty("id")
    Integer id;
    @JsonProperty("name")
    String name;
}
