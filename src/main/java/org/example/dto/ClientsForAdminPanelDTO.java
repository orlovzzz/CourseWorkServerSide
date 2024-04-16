package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ClientsForAdminPanelDTO {
    private int id;
    private String name;
    private String surname;
}
