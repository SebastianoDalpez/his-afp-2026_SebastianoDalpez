package com.sio.backend.resources.arrivalMode;

import org.springframework.stereotype.Service;

@Service 
public class arrivalModeService {
    private final arrivalModeMapper mapper; 
    private final arrivalModeRepository repo;

    /**
     * Inizializza il service iniettando il repository e il mapper degli arrival mode
     *
     * @param repo repository per l'accesso ai dati degli arrivale mode
     * @param mapper componente di conversione tra Entity e DTO
     */
    public arrivalModeService(arrivalModeRepository repo, arrivalModeMapper mapper){
        this.repo = repo;
        this.mapper = mapper;
    }


    //Creazione di una nuova arrivalMode e controlla che non sia già presente quella modalità d'arrivo
    public void createNewArrivalMode(arrivalModeDTO dto){
        //controllo se la modalità d'arrivo che l'utente prova a creare esiste già
        if(repo.existsById(dto.code())){
            throw new IllegalArgumentException("Modalità di arrivo con codice: " + dto.code() + "già esistente");
        } else {
            arrivalMode entity = mapper.toEntity(dto);
            repo.save(entity);
        }

    //Recupero di tutte le modalità d'arrivo registrate (elenco completo)

    //Recupero di tutte le modalità d'arrivo registrate (elenco paginato)

    //Recupera una singola modalità d'arrivo tramite il suo codice identificativo

    //Elimina una modalità d'arrivo in base al suo codice identificativo

    //Modifica una modalità d'arrivo già esistente: filtra per code e modifica la descrizione
     
    }
}
