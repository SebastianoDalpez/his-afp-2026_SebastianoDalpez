package com.sio.backend.resources;

import com.sio.backend.generics.ApiResponse;
import com.sio.backend.resources.pathology.PathologiesDTO;
import com.sio.backend.resources.pathology.PathologiesService;
import com.sio.backend.resources.arrivalMode.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;





/**
 * Controller REST per la gestione delle risorse cliniche e di sistema del progetto SIO[cite: 9].
 * Espone gli endpoint per le operazioni CRUD sulle patologie[cite: 9].
 */
@RestController
@RequestMapping("/resources")
@Tag(name="Gestione risorse SIO", description = "Endpoint per la gestione delle risorse SIO")
class ResourcesController {


    private final PathologiesService pathologiesService;
    private final arrivalModeService arrivalModeService;

    ResourcesController(PathologiesService pathologiesService, arrivalModeService arrivalModeService ) {
        this.pathologiesService = pathologiesService;
        this.arrivalModeService = arrivalModeService;
    }

    /**
     * Recupera un elenco paginato di tutte le patologie registrate[cite: 9].
     *
     * @param pageable parametri di paginazione e ordinamento[cite: 9]
     * @return un'istanza di {@link ApiResponse} contenente la pagina di {@link PathologiesDTO}[cite: 9]
     */
    @Operation(summary = "Recupera tutte le patologie", description = "Recupero di tutte le patologie presenti a DB con supporto alla paginazione")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Lista di patologie recuperata con successo"
            )
    })
    @GetMapping("/pathologies")
    public ApiResponse<Page<PathologiesDTO>> getAllPathologies(
//            @PageableDefault(size = 10, sort = "code", direction = Sort.Direction.ASC)
            Pageable pageable
    ) {
        return ApiResponse.success(
                pathologiesService.getAllPathologiesPaginated(pageable)
        );
    }


    /**
     * Recupera una singola patologia tramite il codice identificativo univoco[cite: 9].
     *
     * @param code il codice identificativo della patologia[cite: 9]
     * @return un'istanza di {@link ApiResponse} contenente il {@link PathologiesDTO} corrispondente[cite: 9]
     */
    @Operation(
            summary = "Recupera una patologia dato il suo codice",
            description = "Recupero di una patologia specifica tramite il relativo codice"
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Patologia trovata"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Patologia non trovata")
    })
    @GetMapping("/pathologies/{code}")
    public ApiResponse<PathologiesDTO> getPathologiesByCode(
            @Parameter(description = "Codice identificativo della patologia", example = "C02")
            @Valid
            @PathVariable
            String code
    ){
        PathologiesDTO dto = pathologiesService.getPathologyById(code);

        return ApiResponse.success(dto);
    }

    /**
     * Crea una nuova patologia nel sistema[cite: 9].
     *
     * @param dto il DTO contenente i dati della patologia da creare[cite: 9]
     * @return il {@link PathologiesDTO} della patologia appena creata[cite: 9]
     */
    @Operation(summary = "Crea una nuova patologia", description = "Inserisce una nuova patologia nel database")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Patologia creata con successo"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Dati non validi o codice già esistente")
    })
    @PostMapping("/pathologies")
    public PathologiesDTO createPathology(@Valid @RequestBody PathologiesDTO dto){
        return pathologiesService.createNewPathology(dto);
    }



// -------------- Arrival Mode ----------


   
    /**
    * Creazione di una nuova modalità d'arrivo (arrivalMode)
    * @param dto --> il DTO inviato dal frontend (JSON)
    * @return il dto dell'Entity appena creata 
    */
    @Operation(summary = "Crea una nuova metodologia di arrivo", description = "Inserisce una nuova modalità di arrivo nel database")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Modalità di arrivo creata con successo"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Dati non validi o codice identificativo (sigla) già esistente"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Errore interno al server")

    })
    @PostMapping("/arrivalMode")
    public ApiResponse<arrivalModeDTO> createArrivalMode(@Valid @RequestBody arrivalModeDTO dto){
        return ApiResponse.success(arrivalModeService.createNewArrivalMode(dto));
    }


    /**
     * Recuperare tutte le modalità d'arrivo
     * @return un oggetto ApiResponse contenente come payload (campo "data") una List di ArrivalModeDTO
    */
    @GetMapping("/arrivalModes")
    public ApiResponse<List<arrivalModeDTO>> getAllArrivalModes(){ 
        return ApiResponse.success(arrivalModeService.getAllArrivalMode());  //restuisco la List di arrivalModeDTO insieme ad altri dati aggiunti dal metodo .success() di ApiResponse
    } 


    /**
     * Recuperare una singola modalità d'arrivo tramite il codice identificativo di quella modalità d'arrivo fornita dall'utente
     * @param code --> il codice di una singola modalità d'arrivo
     * @return --> il DTO della modalità d'arrivo trovata wrappato da (o contenuto in) un istanza della classe ApiResponse
     */
    @GetMapping("/arrivalMode/{code}")
    public ApiResponse<arrivalModeDTO> getArrivalModeByCode(@PathVariable String code){ //PathVariable permette di estrarre il codice fornito dall'utente nell URL ed inserirlo in una variabile Java
        return ApiResponse.success(arrivalModeService.getArrivalModeById(code));
    } 
    
    
    // /**
    //  * Modificare una modalità d'arrivo 
    //  * @param 
    //  * @return
    //  */
    // @PostMapping("/arrivalMode") // ha lo stesso endpoint di createArrivalMode ??????????
    // public arrivalModeDTO modifyExistingArrivalMode(@RequestBody arrivalModeDTO dto) {
    //     return arrivalModeService.modifyArrivalModeDescription(dto);        
    // }
    

    
    /**
     * 
     * @return
     *
     */
    @PostMapping("/arrivalMode/{code}")
    public ApiResponse<arrivalModeDTO> deleteExistingArrivalMode(@PathVariable String code){
        return ApiResponse.success(arrivalModeService.deleteArrivalMode(code)); 
    }






















    /**
     * Elimina una patologia esistente in base al codice[cite: 9].
     *
     * @param code il codice identificativo della patologia da rimuovere[cite: 9]
     * @return un'istanza di {@link ApiResponse} vuota con esito positivo[cite: 9]
     */
    @Operation(summary = "Elimina una patologia", description = "Rimuove una patologia dal database tramite il codice")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Patologia eliminata con successo")
    })
    @DeleteMapping("/pathologies")
    public ApiResponse<Void> deletePathology(
            @Parameter(description = "Codice identificativo della patologia da eliminare", example = "C02")
            @RequestParam String code
    ){
        pathologiesService.deletePathology(code);

        return ApiResponse.success(null);
    }

    /**
     * Modifica i dati di una patologia esistente[cite: 9].
     *
     * @param dto il DTO contenente i campi aggiornati[cite: 9]
     * @return un'istanza di {@link ApiResponse} con il {@link PathologiesDTO} aggiornato[cite: 9]
     */
    @Operation(summary = "Modifica una patologia", description = "Aggiorna la descrizione di una patologia esistente")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Patologia aggiornata con successo"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Patologia non trovata")
    })
    @PutMapping("/pathologies")
    public ApiResponse<PathologiesDTO> editPathology(@RequestBody PathologiesDTO dto){
        PathologiesDTO newDto = pathologiesService.editPathology(dto);
        return ApiResponse.success(newDto);
    }

    /**
     * Endpoint di utilità per simulare un errore del server a fini di test e debug[cite: 9].
     *
     * @return non restituisce valori, solleva un'eccezione di test[cite: 9]
     * @throws Exception eccezione generica simulata[cite: 9]
     */
    @Operation(summary = "Simula un errore", description = "Endpoint di test per verificare la gestione globale delle eccezioni")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Errore interno simulato del server")
    })
    @GetMapping("/sim-error")
    public ApiResponse<PathologiesDTO> getSimError() throws Exception {
        throw new Exception("è scoppiato tutto!");
//        throw new EntityNotFoundException("Patologia non trovata!");
//        throw new IllegalArgumentException("Dati non validi");
//        return ApiResponse.error(
//                "Messaggio di errore simulato",
//                HttpStatus.INTERNAL_SERVER_ERROR.value()
//                );
    }
}