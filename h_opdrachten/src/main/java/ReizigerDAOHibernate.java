import jakarta.persistence.EntityManager;
import org.hibernate.Session;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReizigerDAOHibernate implements ReizigerDAO{
    private AdresDAO adao;
    private OVChipkaartDAO ovckdao;

    @Override
    public boolean save(Reiziger reiziger) {
        try {
            Session session = Main.getSession();
            EntityManager em = session.getEntityManagerFactory().createEntityManager();
            em.getTransaction().begin();
            em.persist(reiziger);
            em.getTransaction().commit();
            session.flush();
            session.close();
        } catch (Exception e) {
            System.err.println("[SQLException] Something went wrong with the SQL at save: " + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean update(Reiziger reiziger) {
        try {
            Session session = Main.getSession();
            session.merge(reiziger);
            session.flush();
            session.close();
        } catch (Exception e) {
            System.err.println("[SQLException] Something went wrong with the SQL at update: " + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(Reiziger reiziger) {
        try {
            Session session = Main.getSession();
            session.remove(reiziger);
            session.flush();
            session.close();
        } catch (Exception e) {
            System.err.println("[SQLException] Something went wrong with the SQL at delete: " + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public Reiziger findById(int id) {
        try {
            Session session = Main.getSession();
            EntityManager em = session.getEntityManagerFactory().createEntityManager();
            Reiziger foundReiziger = (Reiziger) em.getReference(Reiziger.class, id);
            session.close();
            return foundReiziger;
        } catch (Exception e) {
            System.err.println("[SQLException] Something went wrong with the SQL at findById: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Reiziger> findByGbdatum(String datum) {
        try {
            Session session = Main.getSession();
            EntityManager em = session.getEntityManagerFactory().createEntityManager();
            List<Reiziger> foundReizigerList = (List<Reiziger>) em.createQuery("from Reiziger where geboortedatum = :date").setParameter("date", Date.valueOf(datum)).getResultList();
            session.close();
            return foundReizigerList;
        } catch (Exception e) {
            System.err.println("[SQLException] Something went wrong with the SQL at findByGbDatum: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Reiziger> findAll() {
        try {
            Session session = Main.getSession();
            List<Reiziger> foundReizigerList = (List<Reiziger>) session.createQuery("from Reiziger").list();
            session.close();
            return foundReizigerList;
        } catch (Exception e) {
            System.err.println("[SQLException] Something went wrong with the SQL at findAll: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void setAdao(AdresDAO adao){
        this.adao = adao;
    }
    @Override
    public void setOvckdao(OVChipkaartDAO ovckdao){this.ovckdao = ovckdao;}
}
