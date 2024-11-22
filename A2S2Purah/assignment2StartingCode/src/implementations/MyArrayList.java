package implementations;

import java.util.NoSuchElementException;

import utilities.Iterator;
import utilities.ListADT;

/**
 * MyArrayList.java
 * 
 * @author Team Purah
 * @version 1.0
 * 
 * Class Description: Implementation of an Array List. It uses the
 * ListADT and Iterator interfaces and an array as the underlying data
 * structure.
 */
public class MyArrayList<E> implements ListADT<E>
{
	// Constants
	private static final long serialVersionUID = 9218649871642684839L;

	// Attributes
	private E[] array;
	private int size;

	// Constructors
	/**
	 * Constructs an empty MyArrayList.
	 */
	@SuppressWarnings( "unchecked" )
	public MyArrayList()
	{
		array = (E[]) new Object[10];
		size = 0;
	}

	// Methods
	/**
	 * Returns the current size of the list.
	 */
	@Override
	public int size()
	{
		return size;
	}

	/**
	 * Removes all elements from the list.
	 */
	@Override
	public void clear()
	{
		for ( int i = 0; i < size; i++ )
		{
			array[i] = null;
		}
		size = 0;
	}

	/**
	 * Increases the size of the list by one.
	 */
	public void resize( int newSize )
	{
		array = java.util.Arrays.copyOf( array, newSize );
	}

	/**
	 * Adds a specified element to a specified position of the list.
	 */
	@Override
	public boolean add( int index, E toAdd ) throws NullPointerException, IndexOutOfBoundsException
	{
		if ( toAdd == null )
		{
			throw new NullPointerException( "Cannot add null element" );
		}

		if ( index < 0 || index > size )
		{
			throw new IndexOutOfBoundsException( "Index: '" + index + "' out of bounds" );
		}

		if ( size == array.length )
		{
			resize( array.length + 1 );
		}

		for ( int i = size; i > index; i-- )
		{
			array[i] = array[i - 1];
		}

		array[index] = toAdd;

		size++;

		return true;
	}

	/**
	 * Adds a specified element to the end of the list.
	 */
	@Override
	public boolean add( E toAdd ) throws NullPointerException
	{
		if ( toAdd == null )
		{
			throw new NullPointerException( "Cannot add null element" );
		}

		if ( size == array.length )
		{
			resize( array.length + 1 );
		}

		array[size++] = toAdd;

		return true;
	}

	/**
	 * Adds all elements of a specified collection to the end of the list.
	 */
	@Override
	public boolean addAll( ListADT<? extends E> toAdd ) throws NullPointerException
	{
		if ( toAdd == null )
		{
			throw new NullPointerException( "Cannot add null collection" );
		}

		int toAddSize = toAdd.size();

		if ( size + toAddSize > array.length )
		{
			resize( size + toAddSize );
		}

		for ( int i = 0; i < toAddSize; i++ )
		{
			array[size++] = toAdd.get( i );
		}

		return true;
	}

	/**
	 * Returns the element at a specified position in the list.
	 */
	@Override
	public E get( int index ) throws IndexOutOfBoundsException
	{
		if ( index < 0 || index >= size )
		{
			throw new IndexOutOfBoundsException( "Index: '" + index + "' out of bounds" );
		}

		return array[index];
	}

	/**
	 * Removes an element at a specified position in the list.
	 */
	@Override
	public E remove( int index ) throws IndexOutOfBoundsException
	{
		if ( index < 0 || index > size )
		{
			throw new IndexOutOfBoundsException( "Index: '" + index + "' out of bounds" );
		}

		E removedElement = array[index];

		for ( int i = index; i < size - 1; i++ )
		{
			array[i] = array[i + 1];
		}

		array[size - 1] = null;

		size--;

		return removedElement;
	}

	/**
	 * Removes a specified element from the list if it exists.
	 */
	@Override
	public E remove( E toRemove ) throws NullPointerException
	{
		if ( toRemove == null )
		{
			throw new NullPointerException( "Cannot remove null element" );
		}

		int index = -1;
		for ( int i = 0; i < size; i++ )
		{
			if ( array[i].equals( toRemove ) )
			{
				index = i;
				break;
			}
		}

		if ( index == -1 )
		{
			return null;
		}

		E removedElement = array[index];

		for ( int i = index; i < size - 1; i++ )
		{
			array[i] = array[i + 1];
		}

		array[size - 1] = null;

		size--;

		return removedElement;
	}

	/**
	 * Replaces the element at a specified position in the list with the specified
	 * element.
	 */
	@Override
	public E set( int index, E toChange ) throws NullPointerException, IndexOutOfBoundsException
	{
		if ( toChange == null )
		{
			throw new NullPointerException( "Cannot set to null element" );
		}

		if ( index < 0 || index >= size )
		{
			throw new IndexOutOfBoundsException( "Index: '" + index + "' out of bounds" );
		}

		E oldElement = array[index];

		array[index] = toChange;

		return oldElement;
	}

	/**
	 * Returns true if the list is empty, false otherwise.
	 */
	@Override
	public boolean isEmpty()
	{
		return size == 0;
	}

	/**
	 * Returns true if the specified element is in the list, false otherwise.
	 */
	@Override
	public boolean contains( E toFind ) throws NullPointerException
	{
		if ( toFind == null )
		{
			throw new NullPointerException( "Cannot search for null element" );
		}

		for ( int i = 0; i < size; i++ )
		{
			if ( array[i].equals( toFind ) )
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns an array containing all the elements of the list in proper sequence.
	 */
	@SuppressWarnings( "unchecked" )
	@Override
	public E[] toArray( E[] toHold ) throws NullPointerException
	{
		if ( toHold == null )
		{
			throw new NullPointerException( "Cannot store to null array" );
		}

		if ( toHold.length >= size )
		{
			System.arraycopy( array, 0, toHold, 0, size );
			return toHold;
		}

		else
		{
			E[] newArray = (E[]) new Object[size];
			System.arraycopy( array, 0, newArray, 0, size );
			return newArray;
		}
	}

	/**
	 * Returns an array containing all the elements of the list in proper sequence.
	 */
	@Override
	public Object[] toArray()
	{
		Object[] newArray = new Object[size];
		System.arraycopy( array, 0, newArray, 0, size );
		return newArray;
	}

	/**
	 * Returns an iterator over the elements in the list.
	 */
	@Override
	public Iterator<E> iterator()
	{
		return new MyListIterator();
	}

	// Inner classes
	private class MyListIterator implements Iterator<E>
	{
		private int currentIndex = 0;

		@Override
		public boolean hasNext()
		{
			return currentIndex < size;
		}

		@Override
		public E next() throws NoSuchElementException
		{
			if ( !hasNext() )
			{
				throw new NoSuchElementException( "No more elements to iterate" );
			}
			return array[currentIndex++];
		}
	}

}
