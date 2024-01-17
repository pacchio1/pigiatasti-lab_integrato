package it.Pigiatasti.SeVedemo.service;

import it.Pigiatasti.SeVedemo.entity.Preferiti;
import it.Pigiatasti.SeVedemo.payload.request.PreferitiRequest;

import java.util.List;

public interface PreferitiService {
    void aggiungiPreferito(PreferitiRequest preferito);
    List<Preferiti> getPreferiti(int id);
    boolean deleteFollowing(int idFollowing);

    Preferiti isFollowing(PreferitiRequest followingRequest);

}
