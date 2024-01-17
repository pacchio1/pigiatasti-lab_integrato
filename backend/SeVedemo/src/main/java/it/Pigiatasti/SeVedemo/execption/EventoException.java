package it.Pigiatasti.SeVedemo.execption;


public class EventoException extends RuntimeException {
    private Integer idEvento;
    private String messaggio;
// non lasciare il pc acceso incustodito lol
    public EventoException(Integer idEvento, String messaggio) {
        this.idEvento = idEvento;
        this.messaggio = messaggio;
    }

    public Integer getIdEvento() {
        return idEvento;
    }

    @Override
    public String getMessage() {
        return "Errore per l'evento con ID " + idEvento + ": " + messaggio;
    }
}
