package P4;

import java.sql.Date;

public class OVChipkaart {
    private int kaartnummer;
    private Date geldigheid ;
    private int klasse ;
    private double saldo;
    private Reiziger reiziger;

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

    public String toString(){
        String s = "Deze Ovkaart heeft het nummer " + kaartnummer + ", en is geldig tot " + geldigheid +". Klasse is " + klasse +
                ". Saldo is: " + saldo ;
        if (reiziger != null) {
            s+= ".\n" + "De eigenaar van deze kaart is "+reiziger.String();
        }else{
            s+= " en de eigenaar is onbekend";
        }


        return s;
    }

}
