package it.Pigiatasti.SeVedemo.execption;

public class UtenteException extends RuntimeException {
    private Integer idUtente;
    private String messaggio;

    public UtenteException(Integer idUtente, String messaggio) {
        this.idUtente = idUtente;
        this.messaggio = messaggio;
    }

    public Integer getIdUtente() {
        return idUtente;
    }

    @Override
    public String getMessage() {
        return "Errore per l'utente con ID " + idUtente + ": " + messaggio;
    }
}
