package P4;

public class DuplicateException extends RuntimeException{
    public DuplicateException(String entiteit, int waarde, String veld){
        super("Entiteit " + entiteit + " heeft al een waarde " + waarde + " in het veld " + veld);

    }
}
