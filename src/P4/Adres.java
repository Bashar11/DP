package P4;



public class Adres {
    private int id ;
    private String postCode;
    private String straat;
    private String woonplaats;
    private String huisNr;
    private Reiziger reiziger;



    public Adres(int id, String postCode,String straat, String huisNr, String woonplaats,Reiziger reiziger){
        this.id = id;
        this.postCode = postCode;
        this.straat = straat;
        this.huisNr = huisNr;
        this.woonplaats = woonplaats;
        this.reiziger = reiziger;
    }

    public Adres(int id, String postcode, String huisnummer, String straat, String woonplaats) {
        this.id = id;
        this.postCode = postcode;
        this.straat = straat;
        this.huisNr = huisnummer;
        this.woonplaats = woonplaats;
    }

    public String getPostCode(){
        return postCode;
    }
    public int getId(){return id;}

    public void setPostCode(String postCode){
        this.postCode = postCode;
    }
    public void setId(int id){this.id = id;
    }
    public String getStraat() {
        return straat;
    }

    public void setStraat(String straat) {
        this.straat = straat;
    }

    public String getWoonplaats() {
        return woonplaats;
    }

    public void setWoonplaats(String woonplaats) {
        this.woonplaats = woonplaats;
    }

    public String getHuisNr() {
        return huisNr;
    }

    public void setHuisNr(String huisNr) {
        this.huisNr = huisNr;
    }

    public Reiziger getReiziger() {
        return reiziger;
    }

    public void setReiziger(Reiziger reiziger) {
        this.reiziger = reiziger;
    }


    public String toString(){
        String s = id + "- Het adres is  " +straat +", " +huisNr + ", " + postCode +" ," + woonplaats;
        return s;
    }

    public String String(){
        String s = id + "- Het adres is  " +straat +", " +huisNr + ", " + postCode +" ," + woonplaats + reiziger.String() ;
        return s;
    }
}


