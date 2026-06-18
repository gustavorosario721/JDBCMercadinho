/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.dao;
import model.bean.Produto;
import connection.ConnectionFactory;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author ovatsug
 */
public class ProdutoDAO 
{
    public void create(Produto p)
    {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        
        try 
        {
            stmt = con.prepareStatement("insert into produto(descricao, quantidade, preco) values(?, ?, ?)");
            stmt.setString(1, p.getDescricao());
            stmt.setInt(2, p.getQtd());
            stmt.setDouble(3, p.getPreco());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Salvo com sucesso", ":]", JOptionPane.INFORMATION_MESSAGE);
        } 
        catch (SQLException ex) 
        {
            JOptionPane.showMessageDialog(null, "Erro ao salvar!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
        finally
        {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
    
    public void update(Produto p)
    {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        
        try {
            stmt = con.prepareStatement("update produto set descricao = ?, quantidade = ?, preco = ? where id = ?");
            stmt.setString(1, p.getDescricao());
            stmt.setInt(2, p.getQtd());
            stmt.setDouble(3, p.getPreco());
            stmt.setInt(4, p.getId());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Atualizado com sucesso!", ":]", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void delete(Produto p)
    {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        
        try {
            stmt = con.prepareStatement("delete from produto where id = ?");
            stmt.setInt(1, p.getId());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Deletado com sucesso!", ":]", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao deletar!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public List<Produto> read()
    {
        Connection con = ConnectionFactory.getConnection();
        @SuppressWarnings("UnusedAssignment")
        PreparedStatement stmt = null;
        @SuppressWarnings("UnusedAssignment")
        ResultSet rs = null;
        List <Produto> prods = new ArrayList<>();
        
        try 
        {
            stmt = con.prepareStatement("select * from produto");
            rs = stmt.executeQuery();
            
            while (rs.next()) 
            {
                Produto prod = new Produto();
                prod.setId(rs.getInt("id"));
                prod.setDescricao(rs.getString("descricao"));
                prod.setQtd(rs.getInt("quantidade"));
                prod.setPreco(rs.getDouble("preco"));
                prods.add(prod);
            }  
        } 
        catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro na consulta!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
        finally
        {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        
        return prods;
    }
}
