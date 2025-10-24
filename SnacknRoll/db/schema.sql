CREATE DATABASE IF NOT EXISTS snacknroll CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE snacknroll;

CREATE TABLE IF NOT EXISTS utente (
  id_utente INT AUTO_INCREMENT PRIMARY KEY,
  nome VARCHAR(60) NOT NULL,
  cognome VARCHAR(60) NOT NULL,
  email VARCHAR(120) NOT NULL UNIQUE,
  password_hash VARCHAR(255) NOT NULL,
  ruolo ENUM('CLIENTE','ADMIN') NOT NULL DEFAULT 'CLIENTE',
  indirizzo_spedizione VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS prodotto (
  id_prodotto INT AUTO_INCREMENT PRIMARY KEY,
  nome VARCHAR(120) NOT NULL,
  descrizione TEXT,
  prezzo DECIMAL(10,2) NOT NULL,
  categoria VARCHAR(60),
  immagine_url VARCHAR(255),
  quantita_disponibile INT NOT NULL DEFAULT 0
);

CREATE TABLE IF NOT EXISTS ordine (
  id_ordine INT AUTO_INCREMENT PRIMARY KEY,
  id_utente INT NOT NULL,
  data_ordine DATETIME NOT NULL,
  stato ENUM('IN_ELABORAZIONE','SPEDITO','CONSEGNATO','ANNULLATO') NOT NULL,
  totale DECIMAL(10,2) NOT NULL,
  CONSTRAINT fk_ordine_utente FOREIGN KEY (id_utente) REFERENCES utente(id_utente)
);

CREATE TABLE IF NOT EXISTS dettaglio_ordine (
  id_dettaglio INT AUTO_INCREMENT PRIMARY KEY,
  id_ordine INT NOT NULL,
  id_prodotto INT NULL,
  nome_prodotto_snapshot VARCHAR(120) NOT NULL,
  prezzo_unitario DECIMAL(10,2) NOT NULL,
  quantita INT NOT NULL,
  CONSTRAINT fk_det_ord_ordine FOREIGN KEY (id_ordine) REFERENCES ordine(id_ordine) ON DELETE CASCADE,
  CONSTRAINT fk_det_ord_prod FOREIGN KEY (id_prodotto) REFERENCES prodotto(id_prodotto) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS pagamento (
  id_pagamento INT AUTO_INCREMENT PRIMARY KEY,
  id_ordine INT NOT NULL,
  metodo ENUM('CARTA','PAYPAL','BONIFICO') NOT NULL,
  stato ENUM('IN_ATTESA','COMPLETATO','FALLITO') NOT NULL,
  importo DECIMAL(10,2) NOT NULL,
  data_pagamento DATETIME NOT NULL,
  transazione_id VARCHAR(120),
  CONSTRAINT fk_pag_ordine FOREIGN KEY (id_ordine) REFERENCES ordine(id_ordine) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS recensione (
  id_recensione INT AUTO_INCREMENT PRIMARY KEY,
  id_utente INT NOT NULL,
  id_prodotto INT NOT NULL,
  valutazione INT NOT NULL,
  commento TEXT,
  data_recensione DATETIME NOT NULL,
  approvata BOOLEAN NOT NULL DEFAULT FALSE,
  CONSTRAINT fk_rec_utente FOREIGN KEY (id_utente) REFERENCES utente(id_utente),
  CONSTRAINT fk_rec_prodotto FOREIGN KEY (id_prodotto) REFERENCES prodotto(id_prodotto)
);

CREATE TABLE IF NOT EXISTS wishlist (
  id_wishlist INT AUTO_INCREMENT PRIMARY KEY,
  id_utente INT NOT NULL,
  id_prodotto INT NOT NULL,
  data_aggiunta DATETIME NOT NULL,
  CONSTRAINT fk_wl_utente FOREIGN KEY (id_utente) REFERENCES utente(id_utente),
  CONSTRAINT fk_wl_prodotto FOREIGN KEY (id_prodotto) REFERENCES prodotto(id_prodotto)
);

INSERT INTO prodotto (nome, descrizione, prezzo, categoria, immagine_url, quantita_disponibile) VALUES
('Twinkies', 'Iconico dolce americano', 3.50, 'Dolci', 'images/twinkies.jpg', 100),
('Mountain Dew', 'Bibita frizzante agrumata', 2.20, 'Bevande', 'images/mountaindew.jpg', 200);
