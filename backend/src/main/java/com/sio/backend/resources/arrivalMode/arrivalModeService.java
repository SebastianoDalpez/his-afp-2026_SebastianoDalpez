package com.sio.backend.resources.arrivalMode;

import java.util.List;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service 
public class arrivalModeService {
    private final arrivalModeMapper mapper; 
    private final arrivalModeRepository repo;

    /**
     * Inizializza il service iniettando il repository e il mapper degli arrival mode
     * @param repo repository per l'accesso ai dati degli arrivale mode
     * @param mapper componente di conversione tra Entity e DTO
     */
    public arrivalModeService(arrivalModeRepository repo, arrivalModeMapper mapper){
        this.repo = repo;
        this.mapper = mapper;
    }

    /**
     * Creazione di una nuova arrivalMode e controlla che non sia già presente quella modalità d'arrivo
     * @param dto --> un DTO passato dal frontend
     * @return il DTO della nuova Entity appena creata
     */
    public arrivalModeDTO createNewArrivalMode(arrivalModeDTO dto){
        //controllo se la modalità d'arrivo che l'utente prova a creare esiste già
        if(repo.existsById(dto.code())){
            throw new IllegalArgumentException("Modalità di arrivo con codice: " + dto.code() + "già esistente");
        } else {
            arrivalMode entity = mapper.toEntity(dto);
            repo.save(entity);
            return mapper.toDTO(entity);
        }
    }
    
    // /**
    //  * Recupero di tutte le modalità d'arrivo registrate (elenco completo)
    //  * @return un vettore (List) di arrivalModeDTO
    //  */
    // @Transactional(readOnly = true)
    // public List<arrivalModeDTO> getAllArrivalMode(){
    //     return repo.findAll().stream()     //.stream() scrorre tutto il vettore restituito dal contoller
    //         .map(mapper::toDTO)     //.map() prende ogni singola Entity restiuita dal DB e la trasforma in un DTO 
    //         .toList();              //.toList() mette i nuovi DTO creati e li mette in un vettore (List)
    // } 

    
    /**
     * @param pageable oggetto contenente i parametri per la paginazione e l'ordinamento
     * @return una page di arrivalModeDTO
     */
    public Page<arrivalModeDTO> getAllArrivalModePaginated(Pageable pageable){
        Page<arrivalMode> arrivalModePage = repo.findAll(pageable);
        return arrivalModePage.map(mapper::toDTO);
    }


    /**
     * Cerca, tra tutti i record del DB di quella tabella, il record che nel campo "code" contiene il valore fornito dall'utente
     * 
     * @param code --> il codice della modalità d'arrivo fornito dall'utente
     * @return il DTO del record trovato nella tabella del DB (se presente)
     */
    @Transactional(readOnly = true)
    public arrivalModeDTO getArrivalModeById(String code){
        arrivalMode entity =  repo.findById(code).orElseThrow(); //se non esiste un record nel DB con quel codice viene lanciata un'eccezione
        return mapper.toDTO(entity);
    }
    

    /**
     * Cerca una modalità d'arrivo nella relativa tabella nel DB ed elimina il record che contiene il codice fornito dall'utente come input nell'URL
     * @param code --> il codice della modalità d'arrivo da cercare nel DB ed eliminare; fornito dall'utente
     * @return dto --> il DTO dell'Entity appena eliminata
     */
    public void deleteArrivalMode(String code){
        repo.deleteById(code); 
    }
    

    /**
     * Modifica una modalità d'arrivo già esistente: filtra per code e modifica la descrizione
     * @param dto inviato dall'utente, che contiene una modalità d'arrivo già esistente ma con una descrizione diversa
     * @return il DTO della nuova versione del Entity 
     */
    public arrivalModeDTO modifyArrivalModeDescription(arrivalModeDTO dto){
        arrivalMode EntityToModify = repo.findById(dto.code()) //cerco il record del DB che corrisponde al DTO fornito dall'utente
            .orElseThrow(() -> new EntityNotFoundException("Modalità d'arrivo con codice: " + dto.code() + " non trovata")); //repo.findById restituisce un <optional> quindi non è sicuro che restituisca un'oggetto arrivalMode (se non trova il record nel DB restituisce null): 
                                                                                                                             // qui viene gestito il caso in cui questo metodo nel repo restituisca null

        EntityToModify.setDescription(dto.description());   //l'entità interessata viene modificata accedendo al suo campo description e sostituendolo con il campo description contenuto nel DTO. 

        repo.delete(EntityToModify); //elimino la versione precedente

        repo.save(EntityToModify); //salvo la nuova versione dell'Entity nel DB
 
        return mapper.toDTO(EntityToModify); //restituisce all'utente il DTO ricavato dall'Entity appena modificata
    }
}
