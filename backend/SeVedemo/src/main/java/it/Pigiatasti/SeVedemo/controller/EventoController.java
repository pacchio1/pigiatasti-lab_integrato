package it.Pigiatasti.SeVedemo.controller;

import it.Pigiatasti.SeVedemo.entity.Evento;
import it.Pigiatasti.SeVedemo.entity.Utente;
import it.Pigiatasti.SeVedemo.payload.request.CreaEventoRequest;
import it.Pigiatasti.SeVedemo.payload.request.EventiFiltriRequest;
import it.Pigiatasti.SeVedemo.payload.response.EventiResponse;
import it.Pigiatasti.SeVedemo.payload.response.EventiResponseFuturi;
import it.Pigiatasti.SeVedemo.payload.response.EventiResponsePassati;
import it.Pigiatasti.SeVedemo.payload.response.UtentePartecipantiResponse;
import it.Pigiatasti.SeVedemo.service.EventoServiceImpl;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/event")
public class EventoController {

    private EventoServiceImpl eventoServiceImpl;

    @Autowired
    public EventoController(EventoServiceImpl eventoServiceImpl) {
        this.eventoServiceImpl = eventoServiceImpl;
    }

    @GetMapping("/events")
    public List<EventiResponse> getTuttiEventiAttivi() {
        return eventoServiceImpl.GetTuttiEventiAttivi();
    }

    @GetMapping("/events/past")
    public List<EventiResponsePassati> getTuttiEventi() {
        return eventoServiceImpl.GetTuttiEventi();
    }

    @PostMapping("/events/categoria/{categoria}")
    public List<EventiResponse> getTuttiEventiCategori(@PathVariable String categoria) {
        return eventoServiceImpl.GetTuttiEventiCategoria(categoria);
    }

    @PostMapping("/events/host/{host}")
    public List<EventiResponse> getTuttiEventiHost(@PathVariable String host) {
        return eventoServiceImpl.GetTuttiEventiHost(host);
    }

    @PostMapping("/events/citta/{citta}")
    public List<EventiResponse> getTuttiEventiCitta(@PathVariable String citta) {
        return eventoServiceImpl.GetTuttiEventiCittà(citta);
    }

    @PostMapping("/events/data/{data}")
    public List<EventiResponse> getTuttiEventiData(@PathVariable("data") String data) {
        return eventoServiceImpl.GetTuttiEventiData(data);
    }

    @PostMapping("/{id}")
    public List<EventiResponse> getEventoPerId(@PathVariable("id") int id) {
        return eventoServiceImpl.GetTuttiEventiPerId(id);
    }

    @PostMapping("/create")
    public Evento creaEvento(@RequestBody CreaEventoRequest evento) {
        return eventoServiceImpl.createEvento(evento);
    }

    @DeleteMapping("/delete/{id}")
    public Evento cancellaEvento(@PathVariable("id") int id) throws MessagingException {
        return eventoServiceImpl.cancellaEvento(id);
    }

    @PostMapping("/partecipanti/{id}")
    public List<UtentePartecipantiResponse> getPartecipantiEvento(@PathVariable("id") int id) {
        return eventoServiceImpl.getPartecipantiEvento(id);
    }

    @PostMapping("/events/filtri")
    public List<EventiResponse> getTuttiEventiFiltri(@RequestBody EventiFiltriRequest filtriRequest) {
        return eventoServiceImpl.GetTuttiEventiPiuFiltri(
                filtriRequest.getCitta(),
                filtriRequest.getCategoria(),
                filtriRequest.getHost(),
                filtriRequest.getData());
    }

}
