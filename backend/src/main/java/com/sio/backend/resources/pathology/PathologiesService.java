package com.sio.backend.resources.pathology;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Service che gestisce la logica di business per l'entità {@link Pathologies} nel sistema SIO[cite: 8].
 * Gestisce transazionalità, operazioni CRUD e log di auditing delle patologie cliniche[cite: 8].
 */
@Service
@Slf4j
@Transactional
public class PathologiesService {
    private final PathologiesRepository repo;
    private final PathologiesMapper mapper;

    /**
     * Inizializza il service iniettando il repository e il mapper delle patologie[cite: 8].
     *
     * @param repo repository per l'accesso ai dati delle patologie[cite: 8]
     * @param mapper componente di conversione tra Entity e DTO[cite: 8]
     */
    public PathologiesService(PathologiesRepository repo, PathologiesMapper mapper){
        this.repo = repo;
        this.mapper = mapper;
    }

    /**
     * Recupera l'elenco completo di tutte le patologie registrate[cite: 8].
     *
     * @return una lista contenente i {@link PathologiesDTO} di tutte le patologie[cite: 8]
     */
    @Transactional(readOnly = true)
    public List<PathologiesDTO> getAllPathologies(){
        log.debug("Recupero di tutte le patologie");
        List<Pathologies> list = repo.findAll();
        List<PathologiesDTO> dtos = new ArrayList<>();

        for (Pathologies e : list){
            dtos.add(mapper.toDTO(e));
        }

//        return repo.findAll().stream()
//                .map(mapper::toDTO)
//                .toList();

        return dtos;
    }

    /**
     * Recupera un elenco paginato delle patologie[cite: 8].
     *
     * @param pageable oggetto contenente i parametri di paginazione e ordinamento[cite: 8]
     * @return una pagina di {@link PathologiesDTO}[cite: 8]
     */
    @Transactional(readOnly = true)
    public Page<PathologiesDTO> getAllPathologiesPaginated(Pageable pageable){
        Page<Pathologies> entityPage = repo.findAll(pageable);
        return entityPage.map(mapper::toDTO);
    }

    /**
     * Recupera una singola patologia tramite il suo codice identificativo[cite: 8].
     *
     * @param code il codice identificativo della patologia[cite: 8]
     * @return il {@link PathologiesDTO} corrispondente[cite: 8]
     * @throws java.util.NoSuchElementException se la patologia non viene trovata[cite: 8]
     */
    @Transactional(readOnly = true)
    public PathologiesDTO getPathologyById(String code){
        Pathologies e = repo.findById(code).orElseThrow();

        return mapper.toDTO(e);
    }

    /**
     * Crea una nuova patologia nel database[cite: 8].
     * Verifica preventivamente l'esistenza di un codice duplicato[cite: 8].
     *
     * @param dto il DTO contenente i dati della patologia da creare[cite: 8]
     * @return il {@link PathologiesDTO} della patologia creata[cite: 8]
     * @throws IllegalArgumentException se il codice della patologia esiste già[cite: 8]
     */
    public PathologiesDTO createNewPathology(PathologiesDTO dto){
        log.info("Creazione nuova entità patology con codice: {}", dto.code());
        log.debug("Entità in crezione con codice: {}, e descrzione {}", dto.code(), dto.description());

        if (repo.existsById(dto.code())){
            log.warn("Tentativo di creazione di una patologia con codice già esistente: {}", dto.code());
            throw new IllegalArgumentException("Patologia con codice: " + dto.code() + " già esistente");
        }

        Pathologies entity = mapper.toEntity(dto);
        entity = repo.save(entity);

        log.info("Entità patology con codice: {} creata con successo", entity.getCode());
        return mapper.toDTO(entity);
    }

    /**
     * Elimina una patologia in base al codice identificativo[cite: 8].
     *
     * @param code il codice della patologia da eliminare[cite: 8]
     */
    public void deletePathology(String code){
        repo.deleteById(code);
    }

    /**
     * Modifica la descrizione di una patologia esistente[cite: 8].
     *
     * @param dto il DTO contenente i dati aggiornati[cite: 8]
     * @return il {@link PathologiesDTO} aggiornato[cite: 8]
     * @throws EntityNotFoundException se la patologia non viene trovata nel database[cite: 8]
     */
    public PathologiesDTO editPathology(PathologiesDTO dto){
        Pathologies e = repo.findById(dto.code())
                .orElseThrow(() -> new EntityNotFoundException("Patologia con codice: " + dto.code() + " non trovata"));

        e.setDescription(dto.description());

        Pathologies savedEntity = repo.save(e);

        return mapper.toDTO(savedEntity);
    }
}