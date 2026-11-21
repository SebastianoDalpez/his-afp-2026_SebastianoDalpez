package com.sio.backend.resources.arrivalMode;

import java.time.Instant;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity 
@Table (name = "arrival_modes")
@Getter 
@Setter 
public class arrivalMode {

    //Codice identificativo della patologia
    @Column (name = "code")
    @Id 
    @Size(min = 1, max = 10)
    private String code;

    //Descrizione della patologia
    @Column (name = "description")
    @Size (min = 0, max = 50)
    @NotBlank
    private String description;

    // Timestamp di quando è stata creato quel record
    @Column (name = "created_at")
    @CreationTimestamp
    private Instant createdAt;
}
