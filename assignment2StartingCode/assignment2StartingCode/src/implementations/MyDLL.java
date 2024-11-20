package implementations;

import java.util.NoSuchElementException;

import utilities.Iterator;
import utilities.ListADT;

/**
 * MyDLL.java
 * 
 * @author Team Purah
 * @version 1.1
 * 
 * Class Description: Implementation of a doubly linked list. It uses
 * the ListADT and Iterator interfaces and a linked list as the
 * underlying data structure.
 */
public class MyDLL<E> implements ListADT<E>
{
	// Constants
	private static final long serialVersionUID = -1080782129422688520L;

	// Attributes
	private MyDLLNode<E> head;
	private MyDLLNode<E> tail;
	private int size;

	// Constructors
	/**
	 * Constructs an empty MyDLL
	 */
	public MyDLL()
	{
		this.head = null;
		this.tail = null;
		this.size = 0;
	}

	// Methods
	/**
	 * Returns the current number of elements in the list.
	 */
	@Override
	public int size()
	{
		return size;
	}

	/**
	 * Clears the list.
	 */
	@Override
	public void clear()
	{
		head = tail = null;
		size = 0;
	}

	/**
	 * Adds an element to a specific index in the list.
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

		MyDLLNode<E> newNode = new MyDLLNode<>( toAdd );
		if ( index == 0 )
		{
			newNode.setNext( head );
			if ( head != null )
			{
				head.setPrev( newNode );
			}
			head = newNode;
			if ( size == 0 )
			{
				tail = newNode;
			}
		}
		else if ( index == size )
		{
			newNode.setPrev( tail );
			if ( tail != null )
			{
				tail.setNext( newNode );
			}
			tail = newNode;
			if ( size == 0 )
			{
				head = newNode;
			}
		}
		else
		{
			MyDLLNode<E> current = head;
			for ( int i = 0; i < index; i++ )
			{
				current = current.getNext();
			}
			newNode.setNext( current );
			newNode.setPrev( current.getPrev() );
			if ( current.getPrev() != null )
			{
				current.getPrev().setNext( newNode );
			}
			current.setPrev( newNode );
		}
		size++;
		return true;
	}

	/**
	 * Adds an element to the end of the list.
	 */
	@Override
	public boolean add( E toAdd ) throws NullPointerException
	{
		if ( toAdd == null )
		{
			throw new NullPointerException( "Cannot add null element" );
		}
		return add( size, toAdd );
	}

	/**
	 * Adds all elements of a specified list to the end of this list.
	 */
	@Override
	public boolean addAll( ListADT<? extends E> toAdd ) throws NullPointerException
	{
		if ( toAdd == null )
		{
			throw new NullPointerException( "Cannot add null collection" );
		}

		for ( int i = 0; i < toAdd.size(); i++ )
		{
			this.add( toAdd.get( i ) );
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
		MyDLLNode<E> current = head;
		for ( int i = 0; i < index; i++ )
		{
			current = current.getNext();
		}
		return current.getElement();
	}

	/**
	 * Removes the element at a specified position in the list.
	 */
	@Override
	public E remove( int index ) throws IndexOutOfBoundsException
	{
		if ( index < 0 || index >= size )
		{
			throw new IndexOutOfBoundsException( "Index: '" + index + "' out of bounds" );
		}

		if ( index == 0 )
		{
			MyDLLNode<E> removed = head;
			head = head.getNext();
			size--;
			return removed.getElement();
		}
		else
		{
			MyDLLNode<E> removed = head.getNext();
			MyDLLNode<E> prev = head;
			for ( int i = 1; i < index; i++ )
			{
				removed = removed.getNext();
				prev = prev.getNext();
			}
			E deleted = removed.getElement();
			prev.setNext( removed.getNext() );
			size--;
			return deleted;
		}
	}

	/**
	 * Removes a specified element from the list.
	 */
	@Override
	public E remove( E toRemove ) throws NullPointerException
	{
		if ( toRemove == null )
		{
			throw new NullPointerException( "Cannot remove null element" );
		}

		MyDLLNode<E> current = head;
		while ( current != null )
		{
			if ( current.getElement().equals( toRemove ) )
			{
				if ( current == head )
				{
					head = current.getNext();
					if ( head != null )
					{
						head.setPrev( null );
					}
				}
				else
				{
					current.getPrev().setNext( current.getNext() );
				}
				if ( current == tail )
				{
					tail = current.getPrev();
					if ( tail != null )
					{
						tail.setNext( null );
					}
				}
				else
				{
					current.getNext().setPrev( current.getPrev() );
				}
				size--;
				return current.getElement();
			}
			current = current.getNext();
		}
		return null;
	}

	/**
	 * Replaces the element at a specified index in the list with the specified
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

		MyDLLNode<E> current = head;
		for ( int i = 0; i < index; i++ )
		{
			current = current.getNext();
		}
		E oldData = current.getElement();
		current.setElement( toChange );
		return oldData;
	}

	/**
	 * Returns true if there are no elements in the list, false otherwise.
	 */
	@Override
	public boolean isEmpty()
	{
		return size == 0;
	}

	/**
	 * Returns true if a specified element is in the list, false otherwise.
	 */
	@Override
	public boolean contains( E toFind ) throws NullPointerException
	{
		if ( toFind == null )
		{
			throw new NullPointerException( "Cannot search for null element" );
		}
		MyDLLNode<E> current = head;
		while ( current != null )
		{
			if ( current.getElement().equals( toFind ) )
			{
				return true;
			}
			current = current.getNext();
		}
		return false;
	}

	/**
	 * Returns an array containing all the elements of the list in proper sequence.
	 */
	@Override
	public E[] toArray( E[] toHold ) throws NullPointerException
	{
		if ( toHold == null )
		{
			throw new NullPointerException( "Cannot store to null array" );
		}
		if ( toHold.length < size )
		{
			toHold = (E[]) new Object[size];
		}
		MyDLLNode<E> current = head;
		for ( int i = 0; i < size; i++ )
		{
			toHold[i] = current.getElement();
			current = current.getNext();
		}
		return toHold;
	}

	/**
	 * Returns an array containing all the elements of the list in proper sequence.
	 */
	@Override
	public Object[] toArray()
	{
		Object[] array = new Object[size];
		MyDLLNode<E> current = head;
		for ( int i = 0; i < size; i++ )
		{
			array[i] = current.getElement();
			current = current.getNext();
		}
		return array;
	}

	/**
	 * Returns a new instance of an iterator for this list.
	 */
	@Override
	public Iterator<E> iterator()
	{
		return new MyDLLIterator();
	}

	// Inner Classes
	private class MyDLLIterator implements Iterator<E>
	{
		private MyDLLNode<E> current = head;

		@Override
		public boolean hasNext()
		{
			return current != null;
		}

		@Override
		public E next() throws NoSuchElementException
		{
			if ( !hasNext() )
			{
				throw new NoSuchElementException();
			}
			E data = current.getElement();
			current = current.getNext();
			return data;
		}
	}

}
