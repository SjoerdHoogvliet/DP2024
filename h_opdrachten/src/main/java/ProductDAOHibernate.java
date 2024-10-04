import org.hibernate.Session;

import java.util.List;

public class ProductDAOHibernate implements ProductDAO{
    private OVChipkaartDAO ovckdao;
    @Override
    public boolean save(Product product) {
        try{
            Session session = Main.getSession();
            session.beginTransaction();
            session.persist(product);
            session.flush();
            session.close();
        } catch(Exception e){
            System.err.println("[SQLException] Something went wrong with the SQL at save: " + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean update(Product product) {
        try {
            Session session = Main.getSession();
            session.beginTransaction();
            session.merge(product);
            session.flush();
            session.close();
        } catch(Exception e){
            System.err.println("[SQLException] Something went wrong with the SQL at update: " + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(Product product) {
        try {
            Session session = Main.getSession();
            session.beginTransaction();
            session.remove(product);
            session.flush();
            session.close();
        } catch(Exception e){
            System.err.println("[SQLException] Something went wrong with the SQL at delete: " + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public List<Product> findByOVChipkaart(OVChipkaart ovck) {
        try {
            Session session = Main.getSession();
            List<Product> foundProductList = (List<Product>) session.createQuery("from Product where kaart_nummer = ?").setParameter(0, ovck.getKaartNummer()).list();
            session.close();
            return foundProductList;
        } catch(Exception e){
            System.err.println("[SQLException] Something went wrong with the SQL at findByOVChipkaart: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Product> findAll() {
        try {
            Session session = Main.getSession();
            List<Product> foundProductList = (List<Product>) session.createQuery("from Product").list();
            session.close();
            return foundProductList;
        } catch(Exception e){
            System.err.println("[SQLException] Something went wrong with the SQL at findAll: " + e.getMessage());
            return null;
        }
    }
    @Override
    public void setOvckdao(OVChipkaartDAO ovckdao) {
        this.ovckdao = ovckdao;
    }
}
