package com.acira.gofarm.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import java.util.Set;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Preference {
    private Set<UUID> preferredLocation;
    private Set<UUID> crops;
}
