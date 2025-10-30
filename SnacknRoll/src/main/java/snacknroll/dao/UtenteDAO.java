package snacknroll.dao;

import snacknroll.model.Utente;

public interface UtenteDAO {
  Utente findByEmail(String email);
  Utente findById(int id_utente);
  boolean existsByEmail(String email);
  int create(Utente u);
}