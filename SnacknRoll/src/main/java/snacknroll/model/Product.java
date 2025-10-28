package snacknroll.model;

public class Product {
  private long id;
  private String nome;
  private String descrizione;
  private String categoria;
  private java.math.BigDecimal prezzo;
  private String imageUrl;
  private int qta;

  public long getId(){ return id; }
  public void setId(long id){ this.id = id; }
  public String getNome(){ return nome; }
  public void setNome(String nome){ this.nome = nome; }
  public String getDescrizione(){ return descrizione; }
  public void setDescrizione(String descrizione){ this.descrizione = descrizione; }
  public String getCategoria(){ return categoria; }
  public void setCategoria(String categoria){ this.categoria = categoria; }
  public java.math.BigDecimal getPrezzo(){ return prezzo; }
  public void setPrezzo(java.math.BigDecimal prezzo){ this.prezzo = prezzo; }
  public String getImageUrl(){ return imageUrl; }
  public void setImageUrl(String imageUrl){ this.imageUrl = imageUrl; }
  public int isDisponibile(){ return qta; }
  public void setDisponibile(int qta){ this.qta = qta; }
}
