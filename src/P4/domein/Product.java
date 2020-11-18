package P4.domein;

import P4.DuplicateException;


import java.util.ArrayList;
import java.util.List;

public class Product {
    private int productNr;
    private String naam ;
    private String beschrijving;
    private double prijs;
    List<OVChipkaart> kaarten = new ArrayList<>();
    

    public Product(int productNr, String naam, String beschrijving, double prijs) {
        this.productNr = productNr;
        this.naam = naam;
        this.beschrijving = beschrijving;
        this.prijs = prijs;
    }


    public int getProductNr() {
        return productNr;
    }
    public void setProductNr(int productNr) {
        this.productNr = productNr;
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


    public boolean equals(Object object){
        boolean gelijkeProduct = false ;

        if(object instanceof Product){
            Product andereProduct = (Product) object;

            if(this.productNr == andereProduct.productNr &&
                    this.naam.equals(andereProduct.naam) &&
                    this.beschrijving.equals(andereProduct.beschrijving)&&
                    this.prijs == andereProduct.prijs){
                 gelijkeProduct = true;
            }
        }
        return gelijkeProduct;
    }

    public List<OVChipkaart> getOv() {
        return kaarten;
    }

    public void addKaart(OVChipkaart ov){
        for(OVChipkaart kaart : kaarten){
            if(kaart.getKaartnummer() == ov.getKaartnummer()){
                throw new DuplicateException("Ov-kaart", ov.getKaartnummer(),"Kaart nummer.");
            }
        }

        kaarten.add(ov);
    }
    public void deleteKaart(OVChipkaart ov){
        OVChipkaart ovChipkaart = kaarten.stream().filter(e->e.getKaartnummer()==ov.getKaartnummer()).findFirst().orElse(null);
        if(ovChipkaart != null){
            kaarten.remove(ov);
        }
    }

    public String toString(){
        String s = productNr + " " + naam + " " + beschrijving + prijs ;
        return s;
    }


}
