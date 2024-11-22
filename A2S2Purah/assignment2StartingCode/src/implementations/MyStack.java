package implementations;

import java.util.EmptyStackException;
import java.util.NoSuchElementException;

import utilities.Iterator;
import utilities.StackADT;

/**
 * MyStack.java
 * 
 * @author Team Purah
 * @version 1.0
 * 
 * Class Description: Implementation of a stack. It uses the StackADT
 * and Iterator interfaces and MyArrayList as the underlying data
 * structure.
 */
public class MyStack<E> implements StackADT<E>
{

	// Constants
	private static final long serialVersionUID = 159071005598406005L;

	// Attributes
	private MyArrayList<E> list;

	// Constructors
	/**
	 * Constructs an empty stack.
	 */
	public MyStack()
	{
		list = new MyArrayList<E>();
	}

	// Methods
	/**
	 * Adds an element to the top of the stack.
	 */
	@Override
	public void push( E toAdd ) throws NullPointerException
	{
		if ( toAdd == null )
		{
			throw new NullPointerException( "Cannot push null element" );
		}
		list.add( toAdd );
	}

	/**
	 * Removes and returns the top element of the stack.
	 */
	@Override
	public E pop() throws EmptyStackException
	{
		if ( isEmpty() )
		{
			throw new EmptyStackException();
		}
		return list.remove( list.size() - 1 );
	}

	/**
	 * Returns the top element of the stack.
	 */
	@Override
	public E peek() throws EmptyStackException
	{
		if ( isEmpty() )
		{
			throw new EmptyStackException();
		}
		return list.get( list.size() - 1 );
	}

	/**
	 * Removes all elements from the stack.
	 */
	@Override
	public void clear()
	{
		list.clear();
	}

	/**
	 * AdReturns true if there are no elements in the stack, false otherwise.
	 */
	@Override
	public boolean isEmpty()
	{
		return list.isEmpty();
	}

	/**
	 * Concerts the stack to an array.
	 */
	@Override
	public Object[] toArray()
	{
		Object[] array = list.toArray();

		for ( int i = 0, j = array.length - 1; i < j; i++, j-- )
		{
			Object temp = array[i];
			array[i] = array[j];
			array[j] = temp;
		}
		return array;
	}

	/**
	 * Concerts the stack to an array.
	 */
	@SuppressWarnings( "unchecked" )
	@Override
	public E[] toArray( E[] holder ) throws NullPointerException
	{
		if ( holder == null )
		{
			throw new NullPointerException( "Cannot store in empty collection" );
		}

		int size = list.size();

		if ( holder.length < size )
		{
			holder = (E[]) new Object[size];
		}

		for ( int i = 0; i < size; i++ )
		{
			holder[i] = list.get( size - 1 - i );
		}

		if ( holder.length > size )
		{
			System.arraycopy( new Object[holder.length - size], 0, holder, size, holder.length - size );
		}

		return holder;
//		return list.toArray( holder );
	}

	/**
	 * Returns true if the stack contains the specified element, false otherwise.
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
	 * Returns the 1-based position of a specified element in the stack, if it
	 * exists.
	 */
	@Override
	public int search( E toFind )
	{
		for ( int i = list.size() - 1; i >= 0; i-- )
		{
			if ( list.get( i ).equals( toFind ) )
			{
				return list.size() - i;
			}
		}
		return -1;
	}

	/**
	 * Returns an iterator for the stack.
	 */
	@Override
	public Iterator<E> iterator()
	{
		return new MyStackIterator();
	}

	/**
	 * Compares two stacks for equality.
	 */
	@Override
	public boolean equals( StackADT<E> that )
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
	 * Returns the current number of elements in the stack
	 */
	@Override
	public int size()
	{
		return list.size();
	}

	/**
	 * Returns true if the number of elements in the stack equals the length. This
	 * will always return false in this implementation since MyArrayList is dynamic.
	 */
	@Override
	public boolean stackOverflow()
	{
		return false;
	}

	// Inner classes
	private class MyStackIterator implements Iterator<E>
	{
		private int index = list.size() - 1;

		@Override
		public boolean hasNext()
		{
			return index >= 0;
		}

		@Override
		public E next() throws NoSuchElementException
		{
			if ( !hasNext() )
			{
				throw new NoSuchElementException( "No more elements in the stack." );
			}
			return list.get( index-- );
		}
	};
}
