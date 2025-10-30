package snacknroll.model;

public class Utente {
  private int id_utente;
  private String nome;
  private String cognome;
  private String email;
  private String password_hash;
  private String ruolo;
  private String indirizzo_spedizione;

  public int getIdUtente() { return id_utente; }
  public void setIdUtente(int idUtente) { this.id_utente = id_utente; }

  public String getNome() { return nome; }
  public void setNome(String nome) { this.nome = nome; }

  public String getCognome() { return cognome; }
  public void setCognome(String cognome) { this.cognome = cognome; }

  public String getEmail() { return email; }
  public void setEmail(String email) { this.email = email; }

  public String getPasswordHash() { return password_hash; }
  public void setPasswordHash(String passwordHash) { this.password_hash = password_hash; }

  public String getRuolo() { return ruolo; }
  public void setRuolo(String ruolo) { this.ruolo = ruolo; }

  public String getIndirizzoSpedizione() { return indirizzo_spedizione; }
  public void setIndirizzoSpedizione(String indirizzoSpedizione) { this.indirizzo_spedizione = indirizzo_spedizione; }
}