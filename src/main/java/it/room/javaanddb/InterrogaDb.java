package it.room.javaanddb;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ma
 */
public class InterrogaDb {

    static void getInterroga1() {

        CreaDemo cd = new CreaDemo();
        try {
            cd.leggiRecords();
        } catch (SQLException ex) {
            Logger.getLogger(InterrogaDb.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public static void main(String[] args) {
        InterrogaDb.getInterroga1();
    }
}
