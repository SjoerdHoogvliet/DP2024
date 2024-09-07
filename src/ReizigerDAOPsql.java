import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReizigerDAOPsql implements ReizigerDAO{
    private Connection conn;

    public ReizigerDAOPsql(Connection conn) {
        this.conn = conn;
    }
    
    @Override
    public boolean save(Reiziger reiziger) {
        try{
            String query = "INSERT INTO reiziger(reiziger_id, voorletters, tussenvoegsel, achternaam, geboortedatum) VALUES (?, ?, ?, ?, ?);";

            PreparedStatement pst = this.conn.prepareStatement(query);
            pst.setInt(1,reiziger.getId());
            pst.setString(2, reiziger.getVoorletters());
            pst.setString(3, reiziger.getTussenvoegsel());
            pst.setString(4, reiziger.getAchternaam());
            pst.setDate(5, reiziger.getGeboortedatum());
            pst.execute();
            pst.close();
            return true;
        }catch(SQLException sqle){
            System.err.println("[SQLException] Something went wrong with the SQL at save: " + sqle.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(Reiziger reiziger) {
        try{
            String query = "UPDATE reiziger SET voorletters=?, tussenvoegsel=?, achternaam=?, geboortedatum=? WHERE reiziger_id=?";

            PreparedStatement pst = this.conn.prepareStatement(query);
            pst.setString(1, reiziger.getVoorletters());
            pst.setString(2, reiziger.getTussenvoegsel());
            pst.setString(3, reiziger.getAchternaam());
            pst.setDate(4, reiziger.getGeboortedatum());
            pst.setInt(5,reiziger.getId());
            pst.execute();
            pst.close();
            return true;
        }catch(SQLException sqle){
            System.err.println("[SQLException] Something went wrong with the SQL: " + sqle.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(Reiziger reiziger) {
        try{
            String query = "DELETE FROM reiziger WHERE reiziger_id=?";

            PreparedStatement pst = this.conn.prepareStatement(query);
            pst.setInt(1,reiziger.getId());
            pst.execute();
            pst.close();
            return true;
        }catch(SQLException sqle){
            System.err.println("[SQLException] Something went wrong with the SQL: " + sqle.getMessage());
            return false;
        }
    }

    @Override
    public Reiziger findById(int id) {
        try{
            String query = "SELECT * FROM reiziger WHERE reiziger_id=?";

            PreparedStatement pst = this.conn.prepareStatement(query);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            

            String voorletters = null;
            String tussenvoegsel = null;
            String achternaam = null;
            Date geboortedatum = null;
            while(rs.next()){
                voorletters = rs.getString("voorletters");
                tussenvoegsel = rs.getString("tussenvoegsel");
                achternaam = rs.getString("achternaam");
                geboortedatum = rs.getDate("geboortedatum");
            }
            pst.close();
            rs.close();

            Reiziger r = new Reiziger(id, voorletters, tussenvoegsel, achternaam, geboortedatum);
            return r;
        }catch(SQLException sqle){
            System.err.println("[SQLException] Something went wrong with the SQL: " + sqle.getMessage());
            return null;
        }
    }

    @Override
    public List<Reiziger> findByGbdatum(String datum) {
        List<Reiziger> reizigerList = new ArrayList<>();
        try{
            String query = "SELECT * FROM reiziger WHERE geboortedatum=?";

            PreparedStatement pst = this.conn.prepareStatement(query);
            pst.setDate(1, Date.valueOf(datum));
            ResultSet rs = pst.executeQuery();
           

            while(rs.next()){
                int id = rs.getInt("reiziger_id");
                String voorletters = rs.getString("voorletters");
                String tussenvoegsel = rs.getString("tussenvoegsel");
                String achternaam = rs.getString("achternaam");
                Reiziger r = new Reiziger(id, voorletters, tussenvoegsel, achternaam, Date.valueOf(datum));
                reizigerList.add(r);
            }
            pst.close();
            rs.close();

            return reizigerList;
        }catch(SQLException sqle){
            System.err.println("[SQLException] Something went wrong with the SQL: " + sqle.getMessage());
            return reizigerList;
        }
    }

    @Override
    public List<Reiziger> findAll() {
        List<Reiziger> reizigerList = new ArrayList<>();
        try{
            String query = "SELECT * FROM reiziger";

            PreparedStatement pst = this.conn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            

            while(rs.next()){
                int id = rs.getInt("reiziger_id");
                String voorletters = rs.getString("voorletters");
                String tussenvoegsel = rs.getString("tussenvoegsel");
                String achternaam = rs.getString("achternaam");
                Date geboortedatum = rs.getDate("geboortedatum");

                Reiziger r = new Reiziger(id, voorletters, tussenvoegsel, achternaam, geboortedatum);
                reizigerList.add(r);
            }
            pst.close();
            rs.close();
            return reizigerList;
        }catch(SQLException sqle){
            System.err.println("[SQLException] Something went wrong with the SQL at findAll(): " + sqle.getMessage());
            return reizigerList;
        }
    }
}
