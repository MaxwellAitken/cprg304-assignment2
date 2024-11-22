package utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.EmptyStackException;

import exceptions.EmptyQueueException;
import implementations.MyQueue;
import implementations.MyStack;

/**
 * XMLParser.java
 * 
 * @author Team Purah
 * @version 1.0
 * 
 * Class Description: This XML parser will read XML documents, parse
 * for errors in the XML construction, and print all lines that are not
 * properly constructed when parsing is complete.
 */
public class XMLParser
{
	// Attributes
	private static MyQueue<MyError> errorQueue = new MyQueue<>();
	private static MyStack<MyTag> tagStack = new MyStack<>();
	private static MyQueue<MyError> extrasQueue = new MyQueue<>();
	private static int lineCount = 1;

	public static void main( String[] args )
	{
		String filePath = args[0];
		try
		{
			readAndParseFile( filePath );
			printErrors();
		} catch ( IOException e )
		{
			System.out.println( "Error reading file: " + e.getMessage() );
		}
	}

	/**
	 * Reads and parses data from an XML file.
	 * 
	 * @param filePath the path of the XML file to be read and parsed.
	 * @throws IOException if the file could not be found or accessed
	 */
	private static void readAndParseFile( String filePath ) throws IOException
	{
		FileInputStream fis = null;
		StringBuilder lineBuilder = new StringBuilder();

		try
		{
			fis = new FileInputStream( filePath );
			int byteRead;
			while ( ( byteRead = fis.read() ) != -1 )
			{
				char character = (char) byteRead;
				if ( character == '\n' )
				{
					processLine( lineBuilder.toString() );
					lineBuilder.setLength( 0 );
					lineCount++;
				}
				else
				{
					lineBuilder.append( character );
				}
			}

			if ( lineBuilder.length() > 0 )
			{
				processLine( lineBuilder.toString() );
			}
		} finally
		{
			if ( fis != null )
			{
				fis.close();
			}
		}
	}

	/**
	 * Prints all necessary errors from the error queue and extra queue.
	 */
	public static void printErrors()
	{
		boolean noErrors = true;
		System.out.println( "==================ERROR LOG==================\n" );

		while ( !tagStack.isEmpty() )
		{
			errorQueue.enqueue(
				new MyError( "<" + tagStack.peek().tagContent + ">", "Unclosed tag", tagStack.pop().lineNumber ) 
			);
		}

		try
		{
			while ( !errorQueue.isEmpty() || !extrasQueue.isEmpty() )
			{
				noErrors = false;
				if ( errorQueue.isEmpty() )
				{
					while ( !extrasQueue.isEmpty() )
					{
						System.out.println( extrasQueue.dequeue() );
					}
				}
				else if ( extrasQueue.isEmpty() )
				{
					while ( !errorQueue.isEmpty() )
					{
						System.out.println( errorQueue.dequeue() );
					}
				}
				else
				{
					String errorTag = errorQueue.peek().tag;
					String extraTag = extrasQueue.peek().tag;

					if ( !errorTag.equals( extraTag ) )
					{
						System.out.println( errorQueue.dequeue() );
					}
					else
					{
						errorQueue.dequeue();
						extrasQueue.dequeue();
					}
				}
			}
		} catch ( EmptyQueueException e )
		{
			e.printStackTrace();
		}
		if ( noErrors ) System.out.println( "No errors found." );
	}

	/**
	 * Parses a line of XML and checks it for a variety of errors.
	 * If errors are found they are queued to the error or extra queue.
	 * 
	 * @param line the specified line to be parsed and checked for errors
	 */
	private static void processLine( String line )
	{
		line = line.trim();

		// Ignore empty lines and comments
		if ( line.isEmpty() || line.startsWith( "<?xml" ) || line.startsWith( "<!--" ) )
		{
			return;
		}

		int currentIndex = 0;

		while ( currentIndex < line.length() )
		{
			int openTagStart = line.indexOf( '<', currentIndex );
			int openTagEnd = line.indexOf( '>', openTagStart );

			// Malformed tag: tag>
			if ( openTagStart == -1 && openTagEnd != -1 )
			{
				errorQueue.enqueue( new MyError( line, "Malformed tag, missing '<'", lineCount ) );
			}

			// Malformed tag: tag>
			int malformedEnd = line.indexOf( '>', currentIndex );
			if ( openTagStart > malformedEnd && openTagStart != currentIndex )
			{
				errorQueue.enqueue( new MyError( line, "Malformed tag, missing '<'", lineCount ) );
			}

			// No more tags in the line
			if ( openTagStart == -1 )
				break;

			// Malformed tag: <tag
			if ( openTagEnd == -1 )
			{
				errorQueue.enqueue( new MyError( line, "Malformed tag, missing '>'", lineCount ) );
				return;
			}

			String tagContent = line.substring( openTagStart + 1, openTagEnd ).trim();
			String fullTag = line.substring( openTagStart, openTagEnd + 1 ).trim();

			// Remove Variables
			if ( tagContent.contains( "=" ) )
			{
				if ( tagContent.endsWith( "/" ) )
				{
					tagContent = tagContent.split( " " )[0] + "/";
					fullTag = fullTag.split( " " )[1] + "/>";
				}
				else
				{
					tagContent = tagContent.split( " " )[0];
					fullTag = fullTag.split( " " )[1] + ">";
				}
			}
			
			// Self-closing tag: <tag/>
			if ( tagContent.endsWith( "/" ) )
			{
				// Ignore
			}

			// End tag: </tag>
			else if ( tagContent.startsWith( "/" ) )
			{
				String endTag = tagContent.substring( 1 );

				if ( !tagStack.isEmpty() && tagStack.peek().tagContent.equals( endTag ) )
				{
					// Matches top of stack: pop it
					tagStack.pop();
				}
				
				else
					try
					{
						if ( !errorQueue.isEmpty() && errorQueue.peek().tag.equals( fullTag ) )
						{
							// Matches head of errorQueue: dequeue
							errorQueue.dequeue();
						}
						
						else if ( tagStack.isEmpty() )
						{
							// Stack is empty: add to errorQueue
							errorQueue.enqueue( new MyError( fullTag, "Unmatched closing tag", lineCount ) );
						}
						
						else
						{
							// Search stack for matching start tag
							boolean found = false;
							MyStack<MyTag> tempStack = new MyStack<>();

							while ( !tagStack.isEmpty() )
							{
								MyTag topTag = tagStack.pop();
								if ( topTag.tagContent.equals( endTag ) )
								{
									found = true;
									break;
								}
								// Save unmatched tags in tempStack
								tempStack.push( topTag );
							}

							// If match was found, report all popped tags as errors
							if ( found )
							{
								while ( !tempStack.isEmpty() )
								{
									errorQueue.enqueue( 
										new MyError( "<" + tempStack.peek().tagContent + ">", "Unclosed tag", tempStack.pop().lineNumber ) 
									);
								}
							}
							else
							{
								// If no match found, add end tag to extrasQueue
								extrasQueue.enqueue( new MyError( fullTag, "Extra tag", lineCount ) );
								// Restore the stack
								while ( !tempStack.isEmpty() )
								{
									tagStack.push( tempStack.pop() );
								}
							}
						}
					} catch ( NullPointerException e )
					{
						e.printStackTrace();
					} catch ( EmptyStackException e )
					{
						e.printStackTrace();
					} catch ( EmptyQueueException e )
					{
						e.printStackTrace();
					}
			}

			// Opening tag: <tag>
			else
			{
				// Push to stack
				tagStack.push( new MyTag( tagContent, lineCount ) );
			}

			currentIndex = openTagEnd + 1;
		}
	}

	// Inner classes
	/**
	 * This class stores error info for easier access and message formating.
	 */
	private static class MyError
	{
		//Attributes
		String tag;
		String errorMessage;
		int lineNumber;

		// Constructors
		MyError( String tag, String errorMessage, int lineNumber )
		{
			this.tag = tag;
			this.errorMessage = errorMessage;
			this.lineNumber = lineNumber;
		}

		/**
		 * Returns a formatted error message as a string.
		 * 
		 * @return A formatted error message including 
		 * 		   the error, line number and XML tag.
		 */
		public String toString()
		{
			return String.format( "%s at line %d\n\t%s\n", errorMessage, lineNumber, tag );
		}
	}

	/**
	 * This class stores tag info to 
	 * allow for easier manipulation of tags.
	 */
	private static class MyTag
	{
		//Attributes
		String tagContent;
		int lineNumber;

		// Constructors
		MyTag( String tagContent, int lineNumber )
		{
			this.tagContent = tagContent;
			this.lineNumber = lineNumber;
		}
	}

}
