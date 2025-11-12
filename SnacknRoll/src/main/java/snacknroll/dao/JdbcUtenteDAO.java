package snacknroll.dao;

import snacknroll.model.Utente;

import javax.sql.DataSource;
import java.sql.*;
import javax.naming.InitialContext;

public class JdbcUtenteDAO implements UtenteDAO {
  private final DataSource ds;

  public JdbcUtenteDAO() {
    try {
      this.ds = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/snacknroll");
    } catch (Exception e) {
      throw new RuntimeException("DataSource non trovato", e);
    }
  }

  public JdbcUtenteDAO(DataSource ds) { this.ds = ds; }

  @Override
  public Utente findByEmail(String email) {
    String sql = "SELECT id_utente,nome,cognome,email,password_hash,ruolo,indirizzo_spedizione FROM utente WHERE email = ?";
    try (Connection c = ds.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setString(1, email);
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) return map(rs);
        return null;
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Utente findById(int idUtente) {
    String sql = "SELECT id_utente,nome,cognome,email,password_hash,ruolo,indirizzo_spedizione FROM utente WHERE id_utente = ?";
    try (Connection c = ds.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setInt(1, idUtente);
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) return map(rs);
        return null;
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public boolean existsByEmail(String email) {
    String sql = "SELECT 1 FROM utente WHERE email = ?";
    try (Connection c = ds.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setString(1, email);
      try (ResultSet rs = ps.executeQuery()) {
        return rs.next();
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public int create(Utente u) {
    String sql = "INSERT INTO utente(nome,cognome,email,password_hash,ruolo,indirizzo_spedizione) VALUES (?,?,?,?,?,?)";
    try (Connection c = ds.getConnection();
         PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
      ps.setString(1, u.getNome());
      ps.setString(2, u.getCognome());
      ps.setString(3, u.getEmail());
      ps.setString(4, u.getPasswordHash());
      ps.setString(5, u.getRuolo());
      ps.setString(6, u.getIndirizzoSpedizione());
      ps.executeUpdate();
      try (ResultSet keys = ps.getGeneratedKeys()) {
        if (keys.next()) return keys.getInt(1);
      }
      throw new RuntimeException("ID generato non trovato");
    } catch (SQLIntegrityConstraintViolationException e) {
        int code = e.getErrorCode();
        // 1062 = duplicate entry (unique/email)
        if (code == 1062) throw new RuntimeException("Email già registrata", e);
        // 1048 = column cannot be null
        if (code == 1048) throw new RuntimeException("Campo obbligatorio mancante: " + e.getMessage(), e);
        throw new RuntimeException("Violazione vincoli: " + e.getMessage(), e);
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
    }

  private Utente map(ResultSet rs) throws SQLException {
    Utente u = new Utente();
    u.setIdUtente(rs.getInt("id_utente"));
    u.setNome(rs.getString("nome"));
    u.setCognome(rs.getString("cognome"));
    u.setEmail(rs.getString("email"));
    u.setPasswordHash(rs.getString("password_hash"));
    u.setRuolo(rs.getString("ruolo"));
    u.setIndirizzoSpedizione(rs.getString("indirizzo_spedizione"));
    return u;
  }
}