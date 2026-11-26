package com.sio.backend.resources.triagecolor;
import com.sio.backend.resources.triagecolor.triagecolorDTO;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
@Slf4j
public class triageColorService {
    
    private final triagecolorRepository repo;
    private final triagecolorMapper mapper;
    
    public triageColorService(triagecolorRepository repo, triagecolorMapper mapper){
        this.repo = repo;
        this.mapper = mapper;
    }
        
    
    /**
     * @return una List di tutti i colori del triage presenti nel DB, sottoforma di triageColorDTO
     */
    public List<triagecolorDTO> getAllTriagecolor(){
        return repo.findAll().stream()
            .map(mapper::toDTO)   
            .toList();            
    }
    

    /**
     * @param pageableObj --> un oggetto di tipo pageable che determina i parametri di paginazione
     * @return una page di arrivalModeDTO
     */
    public Page<triagecolorDTO> getAllTriageColorPaginated(Pageable pageableObj){
        Page<triagecolor> triageColorPage = repo.findAll(pageableObj);
        return triageColorPage.map(mapper::toDTO);
    }
    
    
    /**
     * @param code --> il codice del colore del triage da prelevare
     * @return un singolo oggetto DTO contenente il colore trovato in base al codice dato in input
     */
    public triagecolorDTO getTriageColorByCode(String code){
       return mapper.toDTO(repo.findByCode(code));
    }
    
        
    
    /**
     * @param dtoFromUser --> l'oggetto in formato JSON che arriva dal frontend
     * @return il DTO della nuova entità creata 
     * 
     * Prima di creare l'Entity viene controllato se esiste già; se l'Entity è già esistente, viene lanciata un'exception, altrimenti viene lanciata
     */
    public triagecolorDTO createNewTriageColor(triagecolorDTO dtoFromUser){
        log.info("Creazione nuovo colore del triage con codice: {}", dtoFromUser.code());
        log.debug("Entità in crezione con codice: {}, e nome visualizzato {}", dtoFromUser.code(), dtoFromUser.displayName());

        
        if (repo.existsById(dtoFromUser.code())) {
            log.warn("Tentativo di creazione di un colore del triage con codice già esistente: {}", dtoFromUser.code());
            throw new IllegalArgumentException(
                "Colore del triage con codice: " + dtoFromUser.code() + " già esistente"
            );
        }

        triagecolor newTriageColor = mapper.toEntity(dtoFromUser);
        triagecolor savedTriageColor = repo.save(newTriageColor);
        log.info("Entità patology con codice: {} creata con successo", savedTriageColor.getCode());

        
        return mapper.toDTO(savedTriageColor);
    }
    
    /**
     * @param code --> una stringa contenente il codice identificativo del colore del triage da modificare
     */
    public void deleteTriageColor(String code){
        repo.deleteById(code);
    }
    
    /**
     * @param dto --> l'oggetto in formato JSON che arriva dal frontend
     * @return il dto della "nuova versione" dell'Entity
     * Prima di modificare l'Entity viene controllato se effettivamente esiste; se non viene trovata viene lanciata un exception, altrimenti viene modificata
     */
    public triagecolorDTO editTriageColor(triagecolorDTO dto){
        triagecolor triageColorToEdit = repo.findById(dto.code())
            .orElseThrow(() -> new EntityNotFoundException(
                "Colore del triage con codice: " + dto.code() + " non trovato"
            ));

        triageColorToEdit.setDisplayName(dto.displayName());
        triageColorToEdit.setPriority(dto.priority());
        triageColorToEdit.setHexValue(dto.hexValue());

        triagecolor savedTriageColor = repo.save(triageColorToEdit);
        return mapper.toDTO(savedTriageColor);
    }
}
