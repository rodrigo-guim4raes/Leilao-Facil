package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;


public class ProdutosDAO {

    public boolean cadastrarProduto(ProdutosDTO produto) {
        String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";

        try (Connection conn = new conectaDAO().conectaBD();
             PreparedStatement prep = conn.prepareStatement(sql)) {

            prep.setString(1, produto.getNome());
            prep.setInt(2, (int) produto.getValor());
            prep.setString(3, produto.getStatus());

            int linhasAfetadas = prep.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {           
            JOptionPane.showMessageDialog(null, "Erro no cadastro: " + e.getMessage());
            return false;
        }
    }

    public ArrayList<ProdutosDTO> listarProdutos() {
        String sql = "SELECT id, nome, valor, status FROM produtos ORDER BY id DESC";
        ArrayList<ProdutosDTO> listagem = new ArrayList<>();

        try (Connection conn = new conectaDAO().conectaBD();
             PreparedStatement prep = conn.prepareStatement(sql);
             ResultSet rs = prep.executeQuery()) {

            while (rs.next()) {
                ProdutosDTO p = new ProdutosDTO();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setValor(rs.getInt("valor"));
                p.setStatus(rs.getString("status"));
                listagem.add(p);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar produtos: " + e.getMessage());
        }

        return listagem;
    }
    
    public boolean venderProduto(int id) {
        String sql = "UPDATE produtos SET status = ? WHERE id = ?";                

        try (Connection conn = new conectaDAO().conectaBD();
            PreparedStatement prep = conn.prepareStatement(sql);){                
                
            prep.setString(1, "Vendido");
            prep.setInt(2, id);
                
            int linhasAfetadas = prep.executeUpdate();
            return linhasAfetadas > 0;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao vender produto: " + e.getMessage());
            return false;
        }
    }
    
    public List<ProdutosDTO> listarProdutosVendidos() {
        List<ProdutosDTO> vendidos = new ArrayList<>();
        String sql = "SELECT * FROM produtos WHERE status = 'Vendido'";        

        try (Connection conn = new conectaDAO().conectaBD();
            PreparedStatement prep = conn.prepareStatement(sql);) {            
            ResultSet rs = prep.executeQuery();

            while (rs.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setValor(rs.getDouble("valor"));
                produto.setStatus(rs.getString("status"));
                vendidos.add(produto);
            }
        
            System.out.println("Produtos vendidos encontrados: " + vendidos.size());

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar produtos vendidos: " + e.getMessage());
        }
        return vendidos;
    }
}


