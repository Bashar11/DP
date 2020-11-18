package P4.domein;

import P4.DuplicateException;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class OVChipkaart {
    private int kaartnummer;
    private Date geldigheid ;
    private int klasse ;
    private double saldo;
    private Reiziger reiziger;
    List<Product> producten = new ArrayList<>();

    public OVChipkaart(int kaartnummer, Date geldigheid, int klasse, double saldo){
        this.kaartnummer = kaartnummer;
        this.geldigheid = geldigheid;
        this.klasse = klasse;
        this.saldo = saldo;

    }

    public OVChipkaart(int kaartnummer, Date geldigheid, int klasse, double saldo, Reiziger reiziger){
        this.kaartnummer = kaartnummer;
        this.geldigheid = geldigheid;
        this.klasse = klasse;
        this.saldo = saldo;
        this.reiziger = reiziger;
    }

    public int getKaartnummer() {
        return kaartnummer;
    }

    public void setKaartnummer(int kaartnummer) {
        this.kaartnummer = kaartnummer;
    }

    public Date getGeldigheid() {
        return geldigheid;
    }

    public void setGeldigheid(Date geldigheid) {
        this.geldigheid = geldigheid;
    }

    public int getKlasse() {
        return klasse;
    }

    public void setKlasse(int klasse) {
        this.klasse = klasse;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public Reiziger getReiziger() {
        return reiziger;
    }

    public void setReiziger(Reiziger reiziger) {
        this.reiziger = reiziger;
    }

    public boolean equlas(Object obj){
        boolean gelijkeOv = false;

        if(obj instanceof OVChipkaart){
            OVChipkaart andereOv = (OVChipkaart) obj;
            if(this.kaartnummer == andereOv.kaartnummer &&
               this.geldigheid.equals(andereOv.geldigheid)&&
               this.klasse == andereOv.klasse &&
               this.saldo == andereOv.saldo){
                gelijkeOv = true;
            }
        }
        return gelijkeOv;
    }

    public List<Product> getProducten(){
        return producten;
    }

    public void addProduct(Product product){
        for(Product product1: producten){
            if(product1.getProductNr() == product.getProductNr()){
                throw new DuplicateException("product ", product.getProductNr() , "Product nummer");

            }

        }
        producten.add(product);


    }

    public void deleteProduct(Product product){

        Product product1 = producten.stream().filter(e->e.getProductNr()==product.getProductNr()).findFirst().orElse(null);
        if(product != null){
            producten.remove(product1);
        }



    }

    public String toString(){
        String s = " Ov-nummer: " + kaartnummer + ".\n Geldigheid: " + geldigheid +".\n Klasse: " + klasse +
                ".\n Saldo: " + saldo + ". \n Reiziger: " + reiziger +". \n Producten: " + producten + "\n`" ;
        return s;
    }



}
