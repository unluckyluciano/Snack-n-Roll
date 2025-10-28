package snacknroll.dao;

import snacknroll.dao.ProductDAO;
import snacknroll.model.Product;
import snacknroll.util.DataSourceProvider;

import java.sql.*;
import java.util.*;

public class JdbcProductDAO implements ProductDAO {

  private Product map(ResultSet rs) throws SQLException {
    Product p = new Product();
    p.setId(rs.getLong("id"));
    p.setNome(rs.getString("nome"));
    p.setDescrizione(rs.getString("descrizione"));
    p.setCategoria(rs.getString("categoria"));
    p.setPrezzo(rs.getBigDecimal("prezzo"));
    p.setImageUrl(rs.getString("image_url"));
    p.setQta(rs.getInt("qta"));
    return p;
  }

  @Override public List<Product> findAll() {
    String sql = "SELECT * FROM prodotto WHERE qta >= 1 ORDER BY created_at DESC";
    try (Connection c = DataSourceProvider.get().getConnection();
         PreparedStatement ps = c.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
      List<Product> out = new ArrayList<>();
      while (rs.next()) out.add(map(rs));
      return out;
    } catch (SQLException e) { throw new RuntimeException(e); }
  }

  @Override public List<Product> findByCategory(String categoria) {
    String sql = "SELECT * FROM prodotto WHERE qta >= 1 AND categoria = ? ORDER BY created_at DESC";
    try (Connection c = DataSourceProvider.get().getConnection();
         PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setString(1, categoria);
      try (ResultSet rs = ps.executeQuery()) {
        List<Product> out = new ArrayList<>();
        while (rs.next()) out.add(map(rs));
        return out;
      }
    } catch (SQLException e) { throw new RuntimeException(e); }
  }

  @Override public List<Product> searchByQuery(String q) {
    String sql = """
      SELECT * FROM prodotto
      WHERE qta >= 1
        AND (LOWER(nome) LIKE ? OR LOWER(descrizione) LIKE ? OR LOWER(categoria) LIKE ?)
      ORDER BY created_at DESC
    """;
    String like = "%" + q.toLowerCase() + "%";
    try (Connection c = DataSourceProvider.get().getConnection();
         PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setString(1, like); ps.setString(2, like); ps.setString(3, like);
      try (ResultSet rs = ps.executeQuery()) {
        List<Product> out = new ArrayList<>();
        while (rs.next()) out.add(map(rs));
        return out;
      }
    } catch (SQLException e) { throw new RuntimeException(e); }
  }

  @Override public Optional<Product> findById(long id) {
    String sql = "SELECT * FROM prodotto WHERE id = ?";
    try (Connection c = DataSourceProvider.get().getConnection();
         PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setLong(1, id);
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) return Optional.of(map(rs));
        return Optional.empty();
      }
    } catch (SQLException e) { throw new RuntimeException(e); }
  }
}
