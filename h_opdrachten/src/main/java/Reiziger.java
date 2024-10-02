import jakarta.persistence.*;

import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "reiziger")
public class Reiziger {
    @Id @GeneratedValue
    @Column(name = "reiziger_id")
    private int id;
    private String voorletters;
    private String tussenvoegsel;
    private String achternaam;
    private Date geboortedatum;
    @OneToOne(cascade = CascadeType.MERGE) @Transient
    private Adres adres;
//    @OneToMany
//    private List<OVChipkaart> ovChipKaarten;

    public Reiziger(){}

    public Reiziger(int id, String voorletters, String tussenvoegsel, String achternaam, Date geboortedatum) {
        this.id = id;
        this.voorletters = voorletters;
        this.tussenvoegsel = tussenvoegsel;
        this.achternaam = achternaam;
        this.geboortedatum = geboortedatum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getVoorletters() {
        return voorletters;
    }

    public void setVoorletters(String voorletters) {
        this.voorletters = voorletters;
    }

    public String getTussenvoegsel() {
        return tussenvoegsel;
    }

    public void setTussenvoegsel(String tussenvoegsel) {
        this.tussenvoegsel = tussenvoegsel;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public Date getGeboortedatum() {
        return geboortedatum;
    }

    public void setGeboortedatum(Date geboortedatum) {
        this.geboortedatum = geboortedatum;
    }

    public String getNaam(){
        if(tussenvoegsel == null){
            return voorletters + ". " + achternaam;
        }
        else{
            return voorletters + ". "  + tussenvoegsel + " " + achternaam;
        }
    }

    public void setAdres(Adres adres){
        this.adres = adres;
    }

    public Adres getAdres(){
        return adres;
    }
//    public void addOVChipkaart(OVChipkaart ovChipkaart){
//        ovChipKaarten.add(ovChipkaart);
//    }
//
//    public List<OVChipkaart> getOvChipKaarten(){
//        return ovChipKaarten;
//    }

    @Override
    public String toString(){
        if(tussenvoegsel == null){
         return String.format("Reiziger id: %d; %s. %s is geboren op: %s adres: %s", id, voorletters,
                 achternaam, geboortedatum, adres);
        }
        return String.format("Reiziger id: %d; %s. %s %s geboren op: %s adres: %s", id,
            voorletters, tussenvoegsel, achternaam, geboortedatum, adres);
    }

}
