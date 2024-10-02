import org.hibernate.Session;

import java.util.List;

public class AdresDAOHibernate implements AdresDAO{

    @Override
    public boolean save(Adres adres) {
        try{
            Session session = Main.getSession();
            session.beginTransaction();
            session.persist(adres);
            session.flush();
            session.close();
        } catch(Exception e){
            System.err.println("[SQLException] Something went wrong with the SQL at save: " + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean update(Adres adres) {
        try {
            Session session = Main.getSession();
            session.beginTransaction();
            session.merge(adres);
            session.flush();
            session.close();
        } catch(Exception e){
            System.err.println("[SQLException] Something went wrong with the SQL at merge: " + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(Adres adres) {
        try {
            Session session = Main.getSession();
            session.beginTransaction();
            session.remove(adres);
            session.flush();
            session.close();
        } catch(Exception e){
            System.err.println("[SQLException] Something went wrong with the SQL at delete: " + e.getMessage());
            return false;
        }
        return true;
    }

    @Override public Adres findById(int id) {
        try {
            Session session = Main.getSession();
            Adres foundAdres = (Adres) session.get(Adres.class, id);
            session.close();
            return foundAdres;
        } catch(Exception e){
            System.err.println("[SQLException] Something went wrong with the SQL at findById: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Adres findByReiziger(Reiziger reiziger) {
        try {
            Session session = Main.getSession();
            Adres foundAdres = (Adres) session.createQuery("from Adres where reiziger_id = ?").setParameter(0, reiziger.getId()).getSingleResult();
            session.close();
            return foundAdres;
        } catch(Exception e){
            System.err.println("[SQLException] Something went wrong with the SQL at findByReiziger: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Adres> findAll() {
        try {
            Session session = Main.getSession();
            List<Adres> foundAdresList = (List<Adres>) session.createQuery("from Adres").list();
            session.close();
            return foundAdresList;
        } catch(Exception e){
            System.err.println("[SQLException] Something went wrong with the SQL at findAll: " + e.getMessage());
            return null;
        }
    }
}
