package it.room.javaanddb;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreaDemo {
    // JDBC driver name and database URL 

    public void creaTabella() throws SQLException {
        Connection conn = null;
        Statement stmt = null;

        //Passo 1: Aprire connessione
        System.out.println("Connessione al database...");
        conn = DbConnect.getConnection();
        if (conn == null) {
            System.out.println("Connessione rifiutata, esco...");
            System.exit(-1);
        }
        //STEP 2: Eseguire un' interrogazione(query)
        System.out.println("Crea una tabella nel database...");
        stmt = conn.createStatement();
        String sql = "CREATE TABLE   REGISTRATION "
                + "(id INTEGER not NULL, "
                + " first VARCHAR(255), "
                + " last VARCHAR(255), "
                + " age INTEGER, "
                + " PRIMARY KEY ( id ))";
        stmt.executeUpdate(sql);
        System.out.println("Tabella creata...");
        stmt.closeOnCompletion();
        DbConnect.closeConnection();
    }

    
    
    
    public static void main(String[] args) throws SQLException {
        CreaDemo creaDemo = new CreaDemo();
        creaDemo.creaTabella();
        
    }
}
