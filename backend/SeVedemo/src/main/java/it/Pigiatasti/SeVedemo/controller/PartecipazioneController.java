package it.Pigiatasti.SeVedemo.controller;

import it.Pigiatasti.SeVedemo.entity.Partecipazione;
import it.Pigiatasti.SeVedemo.payload.request.PartecipazioniRequest;
import it.Pigiatasti.SeVedemo.service.PartecipazioneServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/partecipation")
public class PartecipazioneController {

    private PartecipazioneServiceImpl partecipazioneService;

    @Autowired
    public PartecipazioneController(PartecipazioneServiceImpl partecipazioneService){
        this.partecipazioneService = partecipazioneService;
    }

    @PostMapping("/passate/{id}")
    public List<Partecipazione> getPartecipazioniPassate(@PathVariable int id){
        return partecipazioneService.getPartecipazioniPassate(id);
    }
    @PostMapping("/future/{id}")
    public List<Partecipazione> getpartecipazioniFuture(@PathVariable int id){
        return partecipazioneService.getPartecipazioniFuture(id);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createPartecipazione(@RequestBody PartecipazioniRequest requestDTO) {
        try {
            partecipazioneService.creaPartecipazione(requestDTO.getIdUtente(), requestDTO.getIdEvento());
            return ResponseEntity.ok("Partecipazione creata con successo");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/getId")
    public Partecipazione getPartecipazione(@RequestBody PartecipazioniRequest requestDTO){
        return partecipazioneService.PartecipazionePerId(requestDTO.getIdUtente(), requestDTO.getIdEvento());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePartecipazione(@PathVariable int id) {
        try {
            partecipazioneService.eliminaPartecipazione(id);
            return ResponseEntity.ok("Partecipazione eliminata con successo");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
