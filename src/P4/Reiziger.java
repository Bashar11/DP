package P4;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


public class Reiziger {
    private int id ;
    private String voorletters ;
    private String tussenvoegsel;
    private String achternaam;
    private Date geboortedatum;
    private Adres adres;

    List<OVChipkaart> kaarten= new ArrayList<>();

    public Reiziger(int id, String voorletters, String tussenvoegsel, String achternaam, Date geboortedatum, Adres adres, OVChipkaart ovKaart) {
        this.id = id;
        this.voorletters = voorletters;
        this.tussenvoegsel = tussenvoegsel;
        this.achternaam = achternaam;
        this.geboortedatum = geboortedatum;
        this.adres = adres;




    }
    public Reiziger(int id, String voorletters, String tussenvoegsel, String achternaam, Date geboortedatum) {
        this.id = id;
        this.voorletters = voorletters;
        this.tussenvoegsel = tussenvoegsel;
        this.achternaam = achternaam;
        this.geboortedatum = geboortedatum;


    }


    public int getId(){return id;}
    public void setId(int id){this.id = id;}

    public String getVoorletters(){return voorletters;}
    public void setVoorletters(String voorletters){this.voorletters = voorletters;}

    public String getTussenvoegsel(){return tussenvoegsel;}
    public void setTussenvoegsel(String tussenvoegsel){this.tussenvoegsel = tussenvoegsel;}


    public String getNaam(){return achternaam;}
    public void setNaam(String achternaam){this.achternaam = achternaam;}

    public Date getGeboortedatum(){return geboortedatum;}
    public void setGeboortedatum(Date geboortedatum){this.geboortedatum = geboortedatum;}

    public Adres getAdres(){return adres;}
    public void setAdres(Adres adres ){this.adres = adres ;}


    public String toStringss(){
        String res = voorletters +(tussenvoegsel == null ? "" : " " + tussenvoegsel) + achternaam;
        return res;
    }
    public List<OVChipkaart> getKaarten(){return kaarten;}
    public void addKaart(OVChipkaart newKaart){
        kaarten.add(newKaart);
    }
    public void deleteKaart(OVChipkaart oldKaart){
        kaarten.remove(oldKaart);
    }

    public String String(){
        String s = voorletters +(tussenvoegsel == null ? "" : " " + tussenvoegsel) +" " + achternaam+ " heeft een id " + id + " en is geboren op " + geboortedatum ;
        return s;

    }

    public String toString(){
        String s = voorletters +(tussenvoegsel == null ? "" : " " + tussenvoegsel) +" " + achternaam+ " heeft een id " + id + " en is geboren op " + geboortedatum + buildOv();
        if (adres ==null ){
            s += ". Het adres is onbekend. ";
        }else{
             s+=adres;

    }return s;
}

    public String buildOv(){
        ArrayList<OVChipkaart> lijst = new ArrayList<>();
        if(kaarten != null){
            if(kaarten.size() >0){
                for(OVChipkaart kaart: kaarten){
                    lijst.add(kaart);
                }
            }
        }
        return String.valueOf(lijst);
    }
}
