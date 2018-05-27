import java.io.Serializable;

public class ContribuinteExistenteException extends Exception implements Serializable{
    public ContribuinteExistenteException(String message)
    {
        super(message);
    }
}
