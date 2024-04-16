package org.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ClientOrderDTO {

    @JsonProperty("clientId")
    private String id;
    private String orderDescription;

}
