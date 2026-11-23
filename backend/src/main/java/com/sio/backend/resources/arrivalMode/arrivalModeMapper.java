package com.sio.backend.resources.arrivalMode;

import org.springframework.stereotype.Component;

/**
 * Componente Mapper per la conversione bidirezionale tra l'entità JPA {@link ArrivalMode}
 * e il relativo Data Transfer Object {@link ArrivalModeDTO}.
 */

@Component 
public class arrivalModeMapper {

    /**
     * Converte un'entità {@link ArrivalMode} nel rispettivo {@link ArrivalModeDTO}.
     *
     * @param entity l'entità di persistenza da convertire in DTO
     * @return il DTO popolato con i dati dell'entità, oppure null se l'input è null
     */    
    public arrivalModeDTO toDTO (arrivalMode entity){
        if(entity == null) return null;
        
        return new arrivalModeDTO(
            entity.getCode(),
            entity.getDescription()
        );
    }

    
    /**
     * Converte un DTO dato come parametro in un'Entity
     * @param dto --> il DTO inviato dal frontend
     * @return entity --> un entity da salvare nel DB
     */
    public arrivalMode toEntity (arrivalModeDTO dto){
        if(dto == null) return null;

        arrivalMode entity = new arrivalMode();
        
        entity.setCode(dto.code());
        entity.setDescription(dto.description());
        
        return entity;
    }
}
