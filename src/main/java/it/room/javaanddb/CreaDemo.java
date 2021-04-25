package it.room.javaanddb;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CreaDemo {

    // JDBC driver name and database URL 
    public static final String NOME_TABELLA = "TEST";

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
        String sql = "CREATE TABLE " + NOME_TABELLA
                + " (id INTEGER not NULL, "
                + " nome VARCHAR(255), "
                + " cognome VARCHAR(255), "
                + " eta INTEGER, "
                + " PRIMARY KEY ( id ))";
        stmt.executeUpdate(sql);
        System.out.println("Tabella creata...");
        DbConnect.closeConnection();
    }

    public void cancellaTabella(String nomeTabella) throws SQLException {

        //Passo 1: Aprire connessione
        System.out.println("Connessione al database...");
        Statement stmt = DbConnect.getConnectionStatement();
        if (stmt == null) {
            System.out.println("Connessione rifiutata, esco...");
            System.exit(-1);
        }
        stmt.execute("drop table if exists " + nomeTabella);
        stmt.closeOnCompletion();
        DbConnect.closeConnection();
    }

    public void inserisciRecordsPerTest() throws SQLException {
        Statement stmt = DbConnect.getConnectionStatement();
        if (stmt == null) {
            System.out.println("Connessione rifiutata, esco...");
            System.exit(-1);
        }
        stmt.execute("insert into " + NOME_TABELLA + " values(1, 'Mario','Rossi',32)");
        stmt.execute("insert into " + NOME_TABELLA + " values(2, 'Luigi','Bianchi',45)");
        stmt.execute("insert into " + NOME_TABELLA + " values(3, 'Giulia','Azzurra',18)");
        stmt.execute("insert into " + NOME_TABELLA + " values(4, 'Serena','Verdi',29)");
        stmt.closeOnCompletion();
        DbConnect.closeConnection();
    }

    public boolean updateRecord(String cognome, int nuovaEta) {
        Statement stmt = DbConnect.getConnectionStatement();
        if (stmt == null) {
            System.out.println("Connessione rifiutata, esco...");
            System.exit(-1);
        }
        try {
            stmt.executeUpdate("UPDATE " + NOME_TABELLA + " SET eta=" + nuovaEta + " WHERE cognome='" + cognome + "'");
        } catch (SQLException ex) {
            Logger.getLogger(CreaDemo.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    public ArrayList<String> execSQL(String command)  {
        ArrayList<String> arrayList = new ArrayList<>();
        Statement stmt = DbConnect.getConnectionStatement();
        if (stmt == null) {
            System.out.println("Connessione rifiutata, esco...");
            System.exit(-1);
        }
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery(command);
        } catch (SQLException ex) {
            Logger.getLogger(CreaDemo.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            while (rs.next()) {
                int id;
                try {
                    id = rs.getInt("id");
                } catch (SQLException ex) {
                    Logger.getLogger(CreaDemo.class.getName()).log(Level.SEVERE, null, ex);
                    id=-1;
                }
                String nome;
                try {
                    nome = rs.getString("nome");
                } catch (SQLException ex) {
                    Logger.getLogger(CreaDemo.class.getName()).log(Level.SEVERE, null, ex);
                    nome="n---";
                }
                String cognome;
                try {
                    cognome = rs.getString("cognome");
                } catch (SQLException ex) {
                    Logger.getLogger(CreaDemo.class.getName()).log(Level.SEVERE, null, ex);
                    cognome="c---";
                }
                String eta;
                try {
                    eta = rs.getString("eta");
                } catch (SQLException ex) {
                    Logger.getLogger(CreaDemo.class.getName()).log(Level.SEVERE, null, ex);
                    eta="e--";
                }
                
                String st = String.format(
                        "id:%d, Nome:%s, Cognome:%s, Eta':%s",id,nome,cognome,eta);
                arrayList.add(st);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CreaDemo.class.getName()).log(Level.SEVERE, "la lettura del record è finita in malo modo...", ex);
        }
        return arrayList;
    }

    public void leggiRecords() throws SQLException {
        Statement stmt = DbConnect.getConnectionStatement();
        if (stmt == null) {
            System.out.println("Connessione rifiutata, esco...");
            System.exit(-1);
        }
        ResultSet rs = stmt.executeQuery("select * from " + NOME_TABELLA);

        while (rs.next()) {
            System.out.println("id " + rs.getInt("id")
                    + " nome " + rs.getString("nome")
                    + " cognome " + rs.getString("cognome")
                    + " eta " + rs.getString("eta")
            );
        }
        System.out.println("-------------------------------------------------");
    }

    public static void main(String[] args) throws SQLException {
        CreaDemo creaDemo = new CreaDemo();
        creaDemo.cancellaTabella(NOME_TABELLA);
        creaDemo.creaTabella();
        creaDemo.inserisciRecordsPerTest();
        creaDemo.leggiRecords();
        boolean ret = creaDemo.updateRecord("Azzurra", 28);
        if (!ret) {
            System.out.println("Update record non eseguito correttamente");
        } else {
            System.out.println("Update record eseguito");
        }
        creaDemo.leggiRecords();
    }
}
