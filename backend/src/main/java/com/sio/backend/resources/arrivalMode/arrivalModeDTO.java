package com.sio.backend.resources.arrivalMode;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record arrivalModeDTO(
    @Schema (description = "Indica il codice/sigla di una modalità di arrivo", example = "AMB", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank 
    String code,

    @Schema (description = "La descrizione testuale della modalità di arrivo", example = "Ambulanza 118", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank 
    String description 
) 
{}
