import java.io.Serializable;

public class SemAutorizacaoException extends Exception implements Serializable{
    public SemAutorizacaoException(String message)
    {
       super(message); 
    }
}