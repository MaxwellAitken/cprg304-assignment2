package utilities;

import java.util.EmptyStackException;

/**
 * This StackADT interface defines the abstract 
 * data type for a Stack, a collection that follows a 
 * Last-In-First-Out (LIFO) sequence. The stack only allows 
 * adding and removing elements from the "top" of the stack.
 * 
 * @param <E> The type of elements stored in this stack.
 */
public interface StackADT<E>
{
	/**
	 * Adds an element to the top of this stack.
	 * 
	 * @param toPush The element to be pushed to the top of this stack.
	 * @throws NullPointerException	If the specified element is
	 *                              <code>null</code> and the stack
	 *                              implementation does not support having
	 *                              <code>null</code> elements.
	 */
	public void push( E toPush ) throws NullPointerException;

	/**
	 * Removes the element at the top of this stack.
	 * 
	 * @return The element that was popped from this stack.
	 * @throws EmptyStackException if the stack is empty.
	 */
	public E pop() throws EmptyStackException;

	/**
	 * Returns the element at the top of this stack.
	 * 
	 * @return The element at the top of this stack.
	 * @throws EmptyStackException if the stack is empty.
	 */
	public E peek() throws EmptyStackException;
	
	/**
	 * Returns the current element count in this stack.
	 * 
	 * @return The current element count.
	 */
	public int size();
	
	/**
	 * Returns <code>true</code> if this stack contains no elements.
	 * 
	 * @return <code>true</code> if this stack contains no elements.
	 */
	public boolean isEmpty();
	
	/**
	 * Removes all the elements from this stack.
	 */
	public void clear();
	
	/**
	 * Searches for the given element in this stack. 
	 * Returns the 1-based index of the element if 
	 * found (the top of the stack is at position 1).
	 * 
	 * @param toFind The element to search for in this stack.
	 * @return The 1-based index of the element if found, or -1 if the 
	 * 		   element is not in this stack, or stack is empty.
	 * @throws NullPointerException	If the specified element is
	 *                              <code>null</code> and the stack
	 *                              implementation does not support having
	 *                              <code>null</code> elements.
	 */
	public int search( E toFind ) throws NullPointerException;

	/**
	 * Returns true if this stack contains the specified element.
	 * 
	 * @param toFind The element whose presence in this stack is to be tested.
	 * @return <code>true</code> if this stack contains the specified element.
	 * @throws NullPointerException	If the specified element is
	 *                              <code>null</code> and the stack
	 *                              implementation does not support having
	 *                              <code>null</code> elements.
	 */
	public boolean contains( E toFind ) throws NullPointerException;

	/**
	 * Returns an array containing all of the elements in 
	 * this stack in proper sequence. The top of the stack 
	 * corresponds to the first element in the array.
	 * If the stack is empty, an empty array will be returned.
	 * 
	 * @return An array containing all of the elements
	 * 		   in this stack in proper sequence.
	 */
	public Object[] toArray();
	
	/**
	 * Returns an array containing all of the elements in 
	 * this stack in proper sequence. The top of the stack 
	 * corresponds to the first element in the array.
	 * If the stack is empty, an empty array will be returned.
	 * 
	 * @param copy The array into which the elements of this stack are to be
	 *             stored, if it is big enough; otherwise, a new array of the same
	 *             runtime type is allocated for this purpose.
	 * @return An array containing the elements of this stack.
	 * @throws NullPointerException If the specified array is <code>null</code>.
	 */
	public E[] toArray( E[] copy ) throws NullPointerException;
	
	/**
	 * Compares this stack with another stack for equality. Two stacks are 
	 * considered equal if they contain the same elements in the same order.
	 *
	 * @param that The stack to be compared with this stack.
	 * @return <code>true</code> if the stacks contain equal elements 
     * 		   in the same order, false otherwise.
	 */
	public boolean equals( StackADT<E> that );
	
	/**
	 * Returns an iterator over the elements in this stack, in proper sequence.
	 * 
	 * @return An iterator over the elements in this stack, in proper sequence.
	 */
	public Iterator<E> iterator();
}
