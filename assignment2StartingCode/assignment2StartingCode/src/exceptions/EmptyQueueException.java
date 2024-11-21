package exceptions;

/**
 * EmptyQueueException.java
 * 
 * @author Team Purah
 * @version 1.0
 * 
 * Class Description: An exception class for handling empty queue errors.
 */
public class EmptyQueueException extends Exception
{
	public EmptyQueueException( String message )
	{
		super( message );
	}
}
