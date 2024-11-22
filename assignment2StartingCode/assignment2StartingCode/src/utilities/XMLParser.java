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
 *          Class Description: This XML parser will read XML documents, parse
 *          for errors in the XML construction, and print all lines that are not
 *          properly constructed when parsing is complete.
 */
public class XMLParser
{
	// Attributes
	private static MyQueue<MyError> errorQueue = new MyQueue<>();
	private static MyStack<String> tagStack = new MyStack<>();
	private static MyQueue<MyError> extrasQueue = new MyQueue<>();
	private static int lineCount = 1;

	
	public static void main( String[] args )
	{
		if ( args.length != 1 )
		{
			System.out.println( "Usage: java XMLParser <file path>" );
			return;
		}

		String filePath = args[0];
		try
		{
			readAndParseFile( filePath );
		} catch ( IOException e )
		{
			System.out.println( "Error reading file: " + e.getMessage() );
		}
	}

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
			while ( !tagStack.isEmpty() )
			{
				errorQueue.enqueue( new MyError("<" + tagStack.pop() + ">", "", -1) );
			}

			try
			{
				while ( !errorQueue.isEmpty() || !extrasQueue.isEmpty() )
				{
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

		} finally
		{
			if ( fis != null )
			{
				fis.close();
			}
		}
	}

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

			// No more tags in the line
			if ( openTagStart == -1 ) break;

			int openTagEnd = line.indexOf( '>', openTagStart );
			
			if ( openTagEnd == -1 )
			{
				errorQueue.enqueue( new MyError("", "Malformed tag, missing '>'", lineCount) );
				return;
			}

			String tagContent = line.substring( openTagStart + 1, openTagEnd ).trim();
			String fullTag = line.substring( openTagStart, openTagEnd + 1 ).trim();

			
			
			System.out.println("count:  " + lineCount +  "  tag:  " +  fullTag);
			
			
			
			// Self-closing tag: <tag/>
			if ( tagContent.endsWith( "/" ) )
			{
				// Ignore
			}

			// End tag: </tag>
			else if ( tagContent.startsWith( "/" ) )
			{
				String endTag = tagContent.substring( 1 );
				
				if ( !tagStack.isEmpty() && tagStack.peek().equals( endTag ) )
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
							// Stack is empty: enqueue to errorQueue
							errorQueue.enqueue( 
								new MyError( fullTag, "Unmatched closing tag", lineCount ) 
							);
						}
						else
						{
							// Search stack for matching start tag
							boolean found = false;
							MyStack<String> tempStack = new MyStack<>();

							while ( !tagStack.isEmpty() )
							{
								String topTag = tagStack.pop();
								if ( topTag.equals( endTag ) )
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
										new MyError( "<" + tempStack.pop() + ">", "Unclosed tag", lineCount )
									);
								}
							}
							else
							{
								// If no match found, add end tag to extrasQueue
								extrasQueue.enqueue(
									new MyError( fullTag, "Error", lineCount )
								);
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
				tagStack.push( tagContent );
			}

			currentIndex = openTagEnd + 1;
		}
	}
	
	
	// Inner class to and format errors
	private static class MyError
	{
		String tag;
		String errorMessage;
		int lineNumber;
		
		MyError(String tagName, String errorMessage, int lineNumber)
		{
			this.tag = tagName;
			this.errorMessage = errorMessage;
			this.lineNumber = lineNumber;
		}
		
		public String toString()
		{
			if (lineNumber == -1)
			{
				return String.format("%s  \n\t%s\n", errorMessage, tag );
			}
			return String.format("%s at line %d\n\t%s\n", errorMessage, lineNumber, tag );
		}
	}
	
}
