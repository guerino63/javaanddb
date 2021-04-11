package it.room.javaanddb;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CreaDemo {
    // JDBC driver name and database URL 

    public void creaTabella() throws SQLException {

        //Passo 1: Aprire connessione
        System.out.println("Connessione al database...");
        Statement stmt = DbConnect.getConnectionStatement();
        if (stmt == null) {
            System.out.println("Connessione rifiutata, esco...");
            System.exit(-1);
        }
        //STEP 2: Eseguire un' interrogazione(query)
        System.out.println("Crea una tabella nel database...");
        String sql = "CREATE TABLE   REGISTRATION "
                + "(id INTEGER not NULL, "
                + " nome VARCHAR(255), "
                + " cognome VARCHAR(255), "
                + " eta INTEGER, "
                + " PRIMARY KEY ( id ))";
        stmt.executeUpdate(sql);
        System.out.println("Tabella creata...");
        DbConnect.closeConnection();
    }

    public void cancellaTabella(String nomeTabella) throws SQLException{

        //Passo 1: Aprire connessione
        System.out.println("Connessione al database...");
        Statement stmt = DbConnect.getConnectionStatement();
        if (stmt == null) {
            System.out.println("Connessione rifiutata, esco...");
            System.exit(-1);
        }
        stmt.execute("drop table "+nomeTabella);
        stmt.closeOnCompletion();
        DbConnect.closeConnection();
    }
    
    public void inserisciRecordsPerTest() throws SQLException{
        Statement stmt = DbConnect.getConnectionStatement();
        if (stmt == null) {
            System.out.println("Connessione rifiutata, esco...");
            System.exit(-1);
        }
        stmt.execute("insert into REGISTRATION values(1, 'Mario','Rossi',32)");
        stmt.execute("insert into REGISTRATION values(2, 'Luigi','Bianchi',45)");
        stmt.execute("insert into REGISTRATION values(3, 'Giulia','Azzurra',18)");
        stmt.execute("insert into REGISTRATION values(4, 'Serena','Verdi',29)");
        stmt.closeOnCompletion();
        DbConnect.closeConnection();
    }
    
    
    public void leggiRecords() throws SQLException{
        Statement stmt = DbConnect.getConnectionStatement();
        if (stmt == null) {
            System.out.println("Connessione rifiutata, esco...");
            System.exit(-1);
        }
        ResultSet rs = stmt.executeQuery("select * from REGISTRATION");
 
        while (rs.next()) {
            System.out.println("id " + rs.getInt("id") + 
                    " nome " + rs.getString("nome") +
                    " cognome " + rs.getString("cognome") +
                    " eta " + rs.getString("eta") 
                    );
        }
    }
    
    public static void main(String[] args) throws SQLException {
        CreaDemo creaDemo = new CreaDemo();
        creaDemo.cancellaTabella("REGISTRATION");
        creaDemo.creaTabella();
        creaDemo.inserisciRecordsPerTest();
    }
}
