package com.sio.backend.resources.triagecolor;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

/**
 * Data Transfer Object (DTO) in formato record per il trasferimento
 * dei dati relativi al colore del Triage nel sistema SIO.
 */
@Schema(description = "DTO per il trasferimento dei dati riguardanti il colore del Triage")
public record triagecolorDTO(
    @Schema(
        description = "Codice univoco del colore del Triage",
        example = "C02",
        requiredMode = Schema.RequiredMode.REQUIRED,
        pattern = "[A - Z]+"
    )
    @NotBlank(message = "Il codice è obbligatorio")
    String code,
     
    @Schema(
        description = "Codice univoco del colore del Triage",
        example = "C02",
        requiredMode = Schema.RequiredMode.REQUIRED,
        pattern = "[A - Z]+"
    )
    @NotBlank(message = "Il nome da visualizzare è obbligatorio")
    String displayName,
    
    
    @Schema(
        description = "Numero intero per la priorità dell'accettazione del paziente",
        example = "1",
        requiredMode = Schema.RequiredMode.REQUIRED,
        pattern = "[1 - 9]"
    )
    @NotBlank(message = "La priorità è obbligatoria")
    Integer priority,
    
    
    @Schema(
        description = "Codice colore (esadecimale) del colore del Triage",
        example = "...",
        requiredMode = Schema.RequiredMode.REQUIRED,
        pattern = "[A-Fa-f0-9]{6}"
    )
    @NotBlank(message = "Il codice colore esadecimale è obbligatorio")
    String hexValue
    

) {}
