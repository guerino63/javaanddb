package it.room.javaanddb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Connect to H2 database
 *
 * @author ma
 */
public class DbConnect {

    private static final String JDBC_DRIVER = "org.h2.Driver";
    private static final String DB_URL = "jdbc:h2:~/test";

    //  Credenziali Database 
    private static final String USER = "sa";
    private static final String PASS = "";

    private static Connection connection = null;
    private static Statement stmt;

    /**
     * Non c'e' bisogno di creare piu' istanze di questa classe
     * Posso quindi creare questo metodo e(ovviamente) la variabile di classe che usa static
     * 
     * @return istanza di Connection , altrimenti null, se connessione rifiutata.
     */
    static Statement getConnectionStatement() {
        
        if (connection == null) {   //Se la connessione e' null provo a connettermi al DB
            try {
                Class.forName(JDBC_DRIVER); 
                connection = DriverManager.getConnection(DB_URL, USER, PASS);
                stmt = connection.createStatement();
            } catch (SQLException ex) { //se la connessione non viene accettata ritorno null
                Logger.getLogger(DbConnect.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(DbConnect.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
        }
        return stmt;  //Connessione accettata, ritorno la istanza della classe Connection
    }
    
    /**
     * Una volta usata la connessione deve essere chiusa
     */
    static void closeConnection(){
        if(stmt == null){ //se stmt e' null, non se ne fa nulla
            return;
        }
        try {
            stmt.closeOnCompletion();     //Provo a chiudere la connessione
        } catch (SQLException ex) { //Se qualcosa va storto visualizzo messaggio di errore su console
            Logger.getLogger(DbConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
        stmt = null;
        
        if(connection == null){ //se la connessione e' null, non se ne fa nulla
            return;
        }
        try {
            connection.close();     //Provo a chiudere la connessione
        } catch (SQLException ex) { //Se qualcosa va storto visualizzo messaggio di errore su console
            Logger.getLogger(DbConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
        connection = null;        
        
    }

}
