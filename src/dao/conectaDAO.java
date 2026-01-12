package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class conectaDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/atividade_produtos";
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    
    public Connection conectaBD() throws SQLException {
        try {
        Class.forName("com.mysql.cj.jdbc.Driver");
    } catch (ClassNotFoundException e) {
        throw new SQLException("Driver JDBC do MySQL não encontrado!", e);
    }
    return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}