package org.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SuccessMessageEmployeeDTO extends SuccessMessageDTO {
    private String position;
    @JsonProperty("masterId")
    private String id;

    public SuccessMessageEmployeeDTO(boolean success, String message) {
        super(success, message);
    }

    public SuccessMessageEmployeeDTO(boolean success, String message, String position, String id) {
        super(success, message);
        this.position = position;
        this.id = id;
    }
}
