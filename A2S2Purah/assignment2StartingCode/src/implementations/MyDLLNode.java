package implementations;

/**
 * MyDLLNode.java
 * 
 * @author Team Purah
 * @version 1.1
 * 
 * Class Description: Implementation of a node in a doubly linked list.
 * It uses the ListADT and Iterator interfaces and a linked list as the
 * underlying data structure.
 */
public class MyDLLNode<E>
{
	// Attributes
	private E element;
	private MyDLLNode<E> next;
	private MyDLLNode<E> prev;

	// Constructors
	/**
	 * Constructs a DLL node with an element, predecessor, and successor.
	 */
	public MyDLLNode( E element )
	{
		this.element = element;
		this.next = null;
		this.prev = null;
	}

	/**
	 * Constructs a DLL node with an element, a successor, and a predecessor.
	 */
	public MyDLLNode( E element, MyDLLNode<E> next, MyDLLNode<E> prev )
	{
		this.element = element;
		this.next = next;
		this.prev = prev;
	}

	// Getters and Setters
	public E getElement()
	{
		return element;
	}

	public void setElement( E element )
	{
		this.element = element;
	}

	public MyDLLNode<E> getNext()
	{
		return next;
	}

	public void setNext( MyDLLNode<E> next )
	{
		this.next = next;
	}

	public MyDLLNode<E> getPrev()
	{
		return prev;
	}

	public void setPrev( MyDLLNode<E> prev )
	{
		this.prev = prev;
	}

}
