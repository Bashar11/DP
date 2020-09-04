package P2;

import java.sql.Date;


public class Reiziger {
    private int id ;
    private String voorletters ;
    private String tussenvoegsel;
    private String achternaam;
    private Date geboortedatum;
    private Adres adres;

    public Reiziger(int id, String voorletters, String tussenvoegsel, String achternaam, Date geboortedatum, Adres adres) {
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

    public String toString(){
        String s = voorletters+ " " + tussenvoegsel +" " + achternaam+ " heeft een id " + id + " en is geboren op " + geboortedatum ;

        return s;
    }
}
