import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

public class Product {
    @Id @GeneratedValue
    @Column(name = "product_nummer")
    private int productNummer;
    private String naam;
    private String beschrijving;
    private double prijs;
    @ManyToMany
    @JoinTable(name = "ov_chipkaart_product", joinColumns = @JoinColumn(name = "kaart_nummer"), inverseJoinColumns = @JoinColumn(name = "product_nummer"))
    private List<OVChipkaart> chipkaartenMetProduct = new ArrayList<>();

    public Product(int productNummer, String naam, String beschrijving, double prijs) {
        this.productNummer = productNummer;
        this.naam = naam;
        this.beschrijving = beschrijving;
        this.prijs = prijs;
    }

    public int getProductNummer() {
        return productNummer;
    }

    public void setProductNummer(int productNummer) {
        this.productNummer = productNummer;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }

    public double getPrijs() {
        return prijs;
    }

    public void setPrijs(double prijs) {
        this.prijs = prijs;
    }

    public void addChipkaart(OVChipkaart ovck){
        chipkaartenMetProduct.add(ovck);
    }

    public void deleteChipkaart(OVChipkaart ovck){
        chipkaartenMetProduct.remove(ovck);
    }

    public List<OVChipkaart> getChipkaartenMetProduct(){
        return chipkaartenMetProduct;
    }

    @Override
    public String toString(){
        return String.format("#%s; %s, %s €%s", productNummer, naam, beschrijving, prijs);
    }
}