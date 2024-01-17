package it.Pigiatasti.SeVedemo.controller;

import it.Pigiatasti.SeVedemo.entity.Preferiti;
import it.Pigiatasti.SeVedemo.payload.request.PreferitiRequest;
import it.Pigiatasti.SeVedemo.service.PreferitiServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/preferiti")
public class PreferitiController {

    private PreferitiServiceImpl preferitiServiceImpl;

    @Autowired
    PreferitiController(PreferitiServiceImpl preferitiServiceImpl) {
        this.preferitiServiceImpl = preferitiServiceImpl;
    }

    @PostMapping("/add")
    public void aggiungiPreferito(@RequestBody PreferitiRequest preferito) {
        preferitiServiceImpl.aggiungiPreferito(preferito);
    }

    @GetMapping("/utente/{id}")
    public List<Preferiti> getPreferiti(@PathVariable("id") int id) {
        return preferitiServiceImpl.getPreferiti(id);
    }

    @DeleteMapping("/delete/{idFollowing}")
    public boolean deleteFollowing(@PathVariable("idFollowing") int idFollowing) {
        return preferitiServiceImpl.deleteFollowing(idFollowing);
    }

    @PostMapping("/isFollowing")
    public Preferiti isFollowing(@RequestBody PreferitiRequest followingRequest) {
        return preferitiServiceImpl.isFollowing(followingRequest);
    }
}
