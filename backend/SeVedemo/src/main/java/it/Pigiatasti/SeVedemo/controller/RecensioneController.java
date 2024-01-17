package it.Pigiatasti.SeVedemo.controller;


import it.Pigiatasti.SeVedemo.entity.Recensione;
import it.Pigiatasti.SeVedemo.payload.request.RecensioniRequest;
import it.Pigiatasti.SeVedemo.payload.request.RecensioniRequestIsPresent;
import it.Pigiatasti.SeVedemo.service.RecensioneServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/reviews")
public class RecensioneController {

    private RecensioneServiceImpl recensioneService;

    @Autowired
    public RecensioneController(RecensioneServiceImpl recensioneService) {
        this.recensioneService = recensioneService;
    }


    @PostMapping("/create")
    public boolean creaRecensione(@RequestBody RecensioniRequest recensione) {
       return recensioneService.creaRecensione(recensione.getIdUtente(), recensione.getIdEvento(), recensione.getVoto(), recensione.getDescrizione());
    }

    @GetMapping("/{id}")
    public Optional<Recensione> getRecensioneById(@PathVariable int id) {
        return recensioneService.getRecensioneById(id);
    }

    @DeleteMapping("/delete/{id}")
    public Recensione deleteRecensione(@PathVariable("id") int idRecensione) {
        return recensioneService.cancellaRecensione(idRecensione);
    }

    @PostMapping("/isCreated")
    public Recensione isRecensioneCreated(@RequestBody RecensioniRequestIsPresent recensione){
        return recensioneService.isPresent(recensione);
    }
}
