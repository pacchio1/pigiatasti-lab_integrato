package it.Pigiatasti.SeVedemo.controller;

import it.Pigiatasti.SeVedemo.entity.Evento;
import it.Pigiatasti.SeVedemo.entity.Recensione;
import it.Pigiatasti.SeVedemo.entity.Utente;
import it.Pigiatasti.SeVedemo.execption.UtenteNotFoundException;
import it.Pigiatasti.SeVedemo.payload.response.EventiResponseFuturi;
import it.Pigiatasti.SeVedemo.payload.response.EventiResponsePassati;
import it.Pigiatasti.SeVedemo.payload.response.EventiUtenteResponse;
import it.Pigiatasti.SeVedemo.payload.response.RecensioniUtenteResponse;
import it.Pigiatasti.SeVedemo.service.UtenteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.CrossOrigin;


@RestController
@RequestMapping("/users")
public class UtenteController {
    private UtenteServiceImpl utenteServiceImpl;

    @Autowired
    public UtenteController(UtenteServiceImpl utenteServiceImpl) {
        this.utenteServiceImpl = utenteServiceImpl;
    }

    @GetMapping
    public List<Utente> getUtenti() {
        return utenteServiceImpl.selezionaTuttiUtenti();
    }

    @DeleteMapping("/{id}") // Delete di un utente per id
    public void cancellaUtente(@PathVariable int id) {
        utenteServiceImpl.cancellaUtentePerId(id);
    }

    @PostMapping("/user/{id}")
    public Optional<Utente> ottieniUtente(@PathVariable int id) {
        return utenteServiceImpl.ottieniUtentePerId(id);
    }

    @PostMapping("/update/{id}") // Update
    public ResponseEntity<?> aggiornaUtente(@PathVariable int id, @RequestBody Utente utente) {
        try {
            Utente utenteAggiornato = utenteServiceImpl.aggiornaUtente(utente, id);
            return ResponseEntity.ok(utenteAggiornato);
        } catch (UtenteNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @GetMapping("/reviews/{id}") // Get recensioni per un utente
    public List<RecensioniUtenteResponse> getRecensioniUtente(@PathVariable int id) {
        return utenteServiceImpl.recensioniUtente(id);
    }

    @GetMapping("/events/{id}") // Get lista eventi che ha creato un utente
    public List<EventiUtenteResponse> getEventiUtente(@PathVariable int id) {
        return utenteServiceImpl.eventiUtente(id);
    }

    @DeleteMapping("event/{id}")
    public boolean cancellaEvento(@PathVariable int id) { // Delete dell'evento in base all'id, restituisce true se
                                                          // cancellato false se non cancellato
        return utenteServiceImpl.cancellaEvento(id);
    }

    @PostMapping("/reviews/create")
    public void creaRecensione(@RequestBody Recensione recensione) {
        utenteServiceImpl.creaRecensione(recensione);
    }

    @PostMapping("/getid/{email}") // Dall email restituisco l utente
    public Optional<Utente> getIdUtenteByEmail(@PathVariable String email) {
        return utenteServiceImpl.utentePerEmail(email);
    }

    @PostMapping("/events/partecipations/{id}")
    public List<EventiResponsePassati> getEventiPartecipatiUtente(@PathVariable int id) {
        return utenteServiceImpl.eventiPartecipatiUtente(id);
    }

    @GetMapping("events/passati/{id}")
    public List<EventiResponsePassati> getEventiPassatiUtente(@PathVariable int id) {
        return utenteServiceImpl.eventiOrganizzatiPassati(id);
    }

    @GetMapping("events/futuri/{id}")
    public List<EventiResponseFuturi> getEventiFuturiUtente(@PathVariable int id) {
        return utenteServiceImpl.eventiOrganizzatiFuturi(id);
    }
}
