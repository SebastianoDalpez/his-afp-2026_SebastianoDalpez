package com.sio.backend.resources.triagecolor;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

@Entity 
@Table(name = "triage_colors")
@Getter 
@Setter
public class triagecolor {
    @Id 
    @Column (name = "code")
    @Size(min = 1, max = 20)
    @Schema(description = "Codice identificativo univoco della colore del triage", example = "ROS", maxLength = 20)
    private String code;

    @Column (name = "display_name")
    @NotNull
    @Size(min = 1, max = 50)
    @Schema(description = "Nome mostrato per il colore del triage", example = "ROSSO", maxLength = 50)
    private String displayName;

    @Column (name = "priority")
    @NotNull
    @Schema(description = "Numero intero che rappresenta la priorità di quel paziente", example = "2")
    private Integer priority;

    @Column (name = "hex_value")
    @NotNull
    @Size(min = 1, max = 7)
    @Schema(description = "Codice identificativo univoco della patologia", example = "PAT-01", maxLength = 10)
    private String hexValue;

    @Column (name = "created_at")
    @ColumnDefault("CURRENT_TIMESTAMP")
    @CreationTimestamp
    private Instant created_at;

    protected triagecolor() {
    }

    public triagecolor(String code, String displayName, Integer priority, String hexValue) {
        this.code = code;
        this.displayName = displayName;
        this.priority = priority;
        this.hexValue = hexValue;
    }
}
