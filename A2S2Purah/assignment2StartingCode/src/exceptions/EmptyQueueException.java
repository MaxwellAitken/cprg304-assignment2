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
	// Constants
	private static final long serialVersionUID = 4841203106897644399L;

	// Constructors
	public EmptyQueueException( String message )
	{
		super( message );
	}
}
