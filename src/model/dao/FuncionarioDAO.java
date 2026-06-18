/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.dao;
import model.bean.Funcionario;
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
public class FuncionarioDAO 
{
    public void create(Funcionario f)
    {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        
        try 
        {
            stmt = con.prepareStatement("insert into funcionario(nome, cargo, salario) values(?, ?, ?)");
            stmt.setString(1, f.getNome());
            stmt.setString(2, f.getCargo());
            stmt.setDouble(3, f.getSalario());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Salvo com sucesso", ":]", JOptionPane.INFORMATION_MESSAGE);
        } 
        catch (SQLException ex) 
        {
            JOptionPane.showMessageDialog(null, "Erro ao salvar!", "Erro",JOptionPane.ERROR_MESSAGE);
        }
        finally
        {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
    
    public void update(Funcionario f)
    {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        
        try {
            stmt = con.prepareStatement("update funcionario set nome = ?, cargo = ?, salario = ? where id = ?");
            stmt.setString(1, f.getNome());
            stmt.setString(2, f.getCargo());
            stmt.setDouble(3, f.getSalario());
            stmt.setInt(4, f.getId());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Atualizado com sucesso!", ":]", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar!", "Erro", JOptionPane.ERROR_MESSAGE);
            
        }
    }
    
    public void delete(Funcionario f)
    {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        
        try {
            stmt = con.prepareStatement("delete from funcionario where id = ?");
            stmt.setInt(1, f.getId());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Deletado com sucesso!", ":]", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao deletar!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public List<Funcionario> read()
    {
        Connection con = ConnectionFactory.getConnection();
        @SuppressWarnings("UnusedAssignment")
        PreparedStatement stmt = null;
        @SuppressWarnings("UnusedAssignment")
        ResultSet rs = null;
        List <Funcionario> funcs = new ArrayList<>();
        
        try 
        {
            stmt = con.prepareStatement("select * from funcionario");
            rs = stmt.executeQuery();
            
            while (rs.next()) 
            {
                Funcionario func = new Funcionario();
                func.setId(rs.getInt("id"));
                func.setNome(rs.getString("nome"));
                func.setCargo(rs.getString("cargo"));
                func.setSalario(rs.getDouble("salario"));
                funcs.add(func);
            }  
        } 
        catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro na consulta!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
        finally
        {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        
        return funcs;
    }
}
