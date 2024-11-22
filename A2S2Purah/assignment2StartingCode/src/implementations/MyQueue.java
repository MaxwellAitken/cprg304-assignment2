package implementations;

import exceptions.EmptyQueueException;
import utilities.Iterator;
import utilities.QueueADT;

/**
 * MyQueue.java
 * 
 * @author Team Purah
 * @version 1.0
 * 
 * Class Description: Implementation of a queue. It uses the QueueADT
 * and Iterator interfaces and MyDLL as the underlying data structure.
 */
public class MyQueue<E> implements QueueADT<E>
{

	// Constants
	private static final long serialVersionUID = 6264354142650671522L;

	// Attributes
	private MyDLL<E> list;

	// Constructors
	/**
	 * Constructs an empty queue
	 */
	public MyQueue()
	{
		list = new MyDLL<E>();
	}

	// Methods
	/**
	 * Adds an element to the last position in the queue.
	 */
	@Override
	public void enqueue( E toAdd ) throws NullPointerException
	{
		if ( toAdd == null )
		{
			throw new NullPointerException( "Cannot queue null element" );
		}
		list.add( list.size(), toAdd );
	}

	/**
	 * Removes the element at the first position in the queue.
	 */
	@Override
	public E dequeue() throws EmptyQueueException
	{
		if ( isEmpty() )
		{
			throw new EmptyQueueException( "Cannot dequeue from empty queue" );
		}
		return list.remove( 0 );
	}

	/**
	 * Returns a reference to the first element in the queue.
	 */
	@Override
	public E peek() throws EmptyQueueException
	{
		if ( isEmpty() )
		{
			throw new EmptyQueueException( "Cannot peek empty queue" );
		}
		return list.get( 0 );
	}

	/**
	 * Removes all elements in the queue.
	 */
	@Override
	public void dequeueAll()
	{
		list.clear();
	}

	/**
	 * Returns true if there are no elements in the queue, false otherwise.
	 */
	@Override
	public boolean isEmpty()
	{
		return list.isEmpty();
	}

	/**
	 * Returns true if the specified element is in the queue, false otherwise.
	 */
	@Override
	public boolean contains( E toFind ) throws NullPointerException
	{
		if ( toFind == null )
		{
			throw new NullPointerException( "Cannot search for null element" );
		}
		return list.contains( toFind );
	}

	/**
	 * Returns the 1-based index of element in this queue.
	 */
	@Override
	public int search( E toFind )
	{
		MyDLLNode<E> current = list.getHead();
		int index = 0;

		while ( current != null )
		{
			if ( current.getElement().equals( toFind ) )
			{
				return index;
			}
			current = current.getNext();
			index++;
		}

		return -1;
	}

	/**
	 * Returns an iterator over the element in this queue.
	 */
	@Override
	public Iterator<E> iterator()
	{
		return list.iterator();
	}

	/**
	 * Compares to queues for equality.
	 */
	@Override
	public boolean equals( QueueADT<E> that )
	{
		if ( this == that )
		{
			return true;
		}
		if ( that == null || this.size() != that.size() )
		{
			return false;
		}
		Iterator<E> thisIterator = this.iterator();
		Iterator<E> thatIterator = that.iterator();

		while ( thisIterator.hasNext() && thatIterator.hasNext() )
		{
			if ( !thisIterator.next().equals( thatIterator.next() ) )
			{
				return false;
			}
		}
		return true;
	}

	/**
	 * Converts the queue to an array
	 */
	@Override
	public Object[] toArray()
	{
		return list.toArray();
	}

	/**
	 * Converts the queue to an array
	 */
	@Override
	public E[] toArray( E[] holder ) throws NullPointerException
	{
		return list.toArray( holder );
	}

	/**
	 * Returns true if the number of elements in the queue equals the length. This
	 * will always be false in this implementation since a linked list can never be
	 * full.
	 */
	@Override
	public boolean isFull()
	{
		return false;
	}

	/**
	 * Returns the current number of elements in the queue.
	 */
	@Override
	public int size()
	{
		return list.size();
	}

}
