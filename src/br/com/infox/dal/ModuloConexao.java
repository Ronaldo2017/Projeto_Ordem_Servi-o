package br.com.infox.dal;

import java.sql.*;

public class ModuloConexao {

    // conexao com o bd
    public static Connection conector() {
        java.sql.Connection conexao = null;
        // driver para conexao do bd
        String driver = "com.mysql.jdbc.Driver";
        // armazena informações referente ao banco

        String url = "jdbc:mysql://localhost:3306/infox?autoReconnect=true&useSSL=false";//caminho do bd
        String user = "root";//usuario
        String password = "1234";//senha

        //estabelece a conexao com o bd
        try {
           //Class.forName(driver);// executa o caminho do driver
            conexao = DriverManager.getConnection(url,user,password);
            return conexao;
        } catch (Exception e) {
            //mostrando o erro da conexao
            System.out.println(e);
            return null;
        }
    }
}
