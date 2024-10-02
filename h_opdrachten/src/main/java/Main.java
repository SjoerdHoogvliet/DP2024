
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.Metamodel;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import java.sql.Date;

import java.sql.SQLException;
import java.util.List;

/**
 * Testklasse - deze klasse test alle andere klassen in deze package.
 *
 * System.out.println() is alleen in deze klasse toegestaan (behalve voor exceptions).
 *
 * @author tijmen.muller@hu.nl
 */
public class Main {
    // Creëer een factory voor Hibernate sessions.
    private static final SessionFactory factory;

    static {
        try {
            // Create a Hibernate session factory
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * Retouneer een Hibernate session.
     *
     * @return Hibernate session
     * @throws HibernateException
     */
    public static Session getSession() throws HibernateException {
        return factory.openSession();
    }

    public static void main(String[] args) throws SQLException {
//        testFetchAll();
        ReizigerDAO rdao = new ReizigerDAOHibernate();
        AdresDAO adao = new AdresDAOHibernate();
        OVChipkaartDAO ovckdao = new OVChipkaartDAOHibernate();
        rdao.setAdao(adao);
        rdao.setOvckdao(ovckdao);

        testReizigerDAO(rdao);
    }

    private static void testReizigerDAO(ReizigerDAO rdao) throws SQLException{
        System.out.println("\n---------- Test ReizigerDAO -------------");

        // Haal alle reizigers op uit de database
        List<Reiziger> reizigers = rdao.findAll();
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println();

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        String gbdatum = "1981-03-14";
        Reiziger sietske = new Reiziger(77, "S", "", "Boers", Date.valueOf(gbdatum));
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.save(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers");

        //Vind alle reizigers met dezelfde geboortedatum als S. Boers
        List<Reiziger> foundByGb = rdao.findByGbdatum(gbdatum);
        System.out.println("[TEST] De volgende reizigers zijn gevonden met ReizigerDAO.findByGbdatum():");
        for(Reiziger r : foundByGb){
            System.out.println(r.toString());
        }
//
        //Vind de nieuwe reiziger in de database met zijn id
        int id = 77;
        Reiziger foundById = rdao.findById(id);
        System.out.println(String.format("[TEST] Gevonden door het zoeken naar id %s: %s", id, foundById));

        //Wijzig de zojuist met id gevonden reiziger in de database
        System.out.println("[TEST] Eerst: " + foundById.toString());
        Reiziger exampleReiziger = new Reiziger(77, "A", "", "Broers", Date.valueOf("1971-12-03"));
        rdao.update(exampleReiziger);
        Reiziger updatedReiziger = rdao.findById(id);
        System.out.println("na ReizigerDAO.update(): " + updatedReiziger.toString());

        //Verwijder de nieuwe reiziger
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.delete() ");
        rdao.delete(exampleReiziger);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");
    }

    /**
     * P6. Haal alle (geannoteerde) entiteiten uit de database.
     */
    private static void testFetchAll() {
        Session session = getSession();
        try {
            Metamodel metamodel = session.getSessionFactory().getMetamodel();
            for (EntityType<?> entityType : metamodel.getEntities()) {
                Query query = session.createQuery("from " + entityType.getName());

                System.out.println("[Test] Alle objecten van type " + entityType.getName() + " uit database:");
                for (Object o : query.list()) {
                    System.out.println("  " + o);
                }
                System.out.println();
            }
        } finally {
            session.close();
        }
    }
}