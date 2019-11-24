package exceptions;


@SuppressWarnings("serial")
public class InvalidQuotaException extends Exception
{
	public InvalidQuotaException()
	{
	}

    public InvalidQuotaException(String message)
    {
       super(message);
    }
}