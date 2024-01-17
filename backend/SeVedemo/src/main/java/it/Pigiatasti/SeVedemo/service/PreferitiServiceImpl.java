package it.Pigiatasti.SeVedemo.service;

import it.Pigiatasti.SeVedemo.entity.Preferiti;
import it.Pigiatasti.SeVedemo.payload.request.PreferitiRequest;
import it.Pigiatasti.SeVedemo.repository.PreferitiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PreferitiServiceImpl implements PreferitiService {

    private PreferitiRepository preferitiRepository;

    @Autowired
    PreferitiServiceImpl(PreferitiRepository preferitiRepository) {
        this.preferitiRepository = preferitiRepository;
    }

    @Override
    public void aggiungiPreferito(PreferitiRequest preferito) {

        preferitiRepository.salvaPreferito(preferito.getId_utente(), preferito.getUtente_preferito());
    }
    @Override
    public List<Preferiti> getPreferiti(int id) {
        return preferitiRepository.getTuttiPreferiti(id);
    }
    @Override
    public boolean deleteFollowing(int idFollowing) {
        try {
            preferitiRepository.deleteById(idFollowing);
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    @Override
    public Preferiti isFollowing(PreferitiRequest followingRequest) {

        return preferitiRepository.isFollowing(followingRequest.getId_utente(), followingRequest.getUtente_preferito());
    }
}
