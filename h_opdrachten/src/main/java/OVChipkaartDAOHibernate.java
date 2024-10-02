import org.hibernate.Session;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OVChipkaartDAOHibernate implements OVChipkaartDAO{

    @Override
    public boolean save(OVChipkaart ovChipkaart) {
        try{
            Session session = Main.getSession();
            session.beginTransaction();
            session.persist(ovChipkaart);
            session.flush();
            session.close();
        } catch(Exception e){
            System.err.println("[SQLException] Something went wrong with the SQL at save: " + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean update(OVChipkaart ovChipkaart) {
        try {
            Session session = Main.getSession();
            session.beginTransaction();
            session.merge(ovChipkaart);
            session.flush();
            session.close();
        } catch(Exception e){
            System.err.println("[SQLException] Something went wrong with the SQL at update: " + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(OVChipkaart ovChipkaart) {
        try {
            Session session = Main.getSession();
            session.beginTransaction();
            session.remove(ovChipkaart);
            session.flush();
            session.close();
        } catch(Exception e){
            System.err.println("[SQLException] Something went wrong with the SQL at delete: " + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public List<OVChipkaart> findByReiziger(int reizigerId) {
        try {
            Session session = Main.getSession();
            List<OVChipkaart> foundOVChipkaartList = (List<OVChipkaart>) session.createQuery("from OVChipkaart where reiziger_id = ?").setParameter(0, reizigerId).list();
            session.close();
            return foundOVChipkaartList;
        } catch(Exception e){
            System.err.println("[SQLException] Something went wrong with the SQL at findByReiziger: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<OVChipkaart> findAll() {
        try {
            Session session = Main.getSession();
            List<OVChipkaart> foundOVChipkaartList = (List<OVChipkaart>) session.createQuery("from OVChipkaart").list();
            session.close();
            return foundOVChipkaartList;
        } catch(Exception e){
            System.err.println("[SQLException] Something went wrong with the SQL at findAll: " + e.getMessage());
            return null;
        }
    }
}