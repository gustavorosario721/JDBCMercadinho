/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.dao;
import model.bean.Venda;
import connection.ConnectionFactory;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.sql.Date;
import java.time.LocalDate;

/**
 *
 * @author ovatsug
 */
public class VendaDAO 
{
    public void create(Venda v)
    {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        PreparedStatement stmtProd = null;

        
        try 
        {  
            con.setAutoCommit(false);
            stmt = con.prepareStatement("insert into venda(id_func, id_prod, quantidade_vendida, data_venda) values(?, ?, ?, ?)");
            stmt.setInt(1, v.getIdFunc());
            stmt.setInt(2, v.getIdProd());
            stmt.setInt(3, v.getQuantidadeVendida());
            stmt.setDate(4, Date.valueOf(v.getDataVenda()));
            stmt.executeUpdate();
            
            stmtProd = con.prepareStatement("update produto set quantidade = quantidade - ? where id = ? and quantidade >= ?");
            stmtProd.setInt(1, v.getQuantidadeVendida());
            stmtProd.setInt(2, v.getIdProd());
            stmtProd.setInt(3, v.getQuantidadeVendida());
            int estoqueSuficiente = stmtProd.executeUpdate();
            
            if (estoqueSuficiente == 0)
            {
                throw new Exception("Estoque insuficiente!");
            }
            
            con.commit();
            JOptionPane.showMessageDialog(null, "Salvo com sucesso", ":]", JOptionPane.INFORMATION_MESSAGE);

        } 
        catch (SQLIntegrityConstraintViolationException ex)
        {
            JOptionPane.showMessageDialog(null, "Erro. Funcionário e ou produto devem ter id válido!", "Erro", JOptionPane.ERROR_MESSAGE);
            try 
            {
                if (con != null)
                    con.rollback();
            } 
            catch (SQLException ex1) 
            {
                JOptionPane.showMessageDialog(null, "Erro inesperado!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
        catch (SQLException ex)
        {
            JOptionPane.showMessageDialog(null, "Erro ao salvar!", "Erro", JOptionPane.ERROR_MESSAGE);
            try 
            {
                if (con != null)
                    con.rollback();
            } 
            catch (SQLException ex1) 
            {
                JOptionPane.showMessageDialog(null, "Erro inesperado!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            try 
            {
                if (con != null)
                    con.rollback();
            } 
            catch (SQLException ex1) 
            {
                JOptionPane.showMessageDialog(null, "Erro inesperado!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
        finally
        {
            ConnectionFactory.closeConnection(con, stmt);
            try 
            {
                if (stmtProd != null)
                    stmtProd.close();
            } 
            catch (SQLException ex) 
            {
                JOptionPane.showMessageDialog(null, "Erro inesperado!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    public List<Venda> read()
    {
        Connection con = ConnectionFactory.getConnection();
        @SuppressWarnings("UnusedAssignment")
        PreparedStatement stmt = null;
        @SuppressWarnings("UnusedAssignment")
        ResultSet rs = null;
        List <Venda> vendas = new ArrayList<>();
        
        try 
        {
            stmt = con.prepareStatement("select * from venda");
            rs = stmt.executeQuery();
            
            while (rs.next()) 
            {
                Venda venda = new Venda();
                venda.setCodVenda(rs.getInt("cod_venda"));
                venda.setIdFunc(rs.getInt("id_func"));
                venda.setIdProd(rs.getInt("id_prod"));
                venda.setQuantidadeVendida(rs.getInt("quantidade_vendida"));
                venda.setDataVenda(rs.getObject("data_venda", LocalDate.class));
                vendas.add(venda);
            }  
        } 
        catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro na consulta!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
        finally
        {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        
        return vendas;
    }
}
