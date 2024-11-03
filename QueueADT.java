package utilities;

/**
 * This QueueADT interface defines the abstract data type for a Queue,
 * a linear First-In-First-Out (FIFO) sequence of elements.
 * Elements can only be added at one end (the tail) and removed 
 * at the other end (the head). This Queue uses a 1-based index.
 * 
 * In this documentation, "head" refers to the start 
 * of the queue (index = 1), and "tail" refers to the 
 * end of the queue (index = length of queue, if length is not 0).
 * 
 * @param <E> The type of elements stored in this queue.
 */
public interface QueueADT<E>
{
	/**
	 * Adds an element to the tail of this queue.
	 * 
	 * @param toEnqueue The element to be added to the end of this queue.
	 * @throws NullPointerException	If the specified element is
	 *                              <code>null</code> and the queue
	 *                              implementation does not support having
	 *                              <code>null</code> elements.
	 */
	public void enqueue( E toEnqueue ) throws NullPointerException;

	/**
	 * Removes the head element of this queue.
	 * 
	 * @return The element that was removed from this queue.
	 * @throws EmptyQueueException if the queue is empty.
	 */
	public E dequeue() throws EmptyQueueException;

	/**
	 * Returns the head element in this queue.
	 * 
	 * @return The head element in this queue.
	 * @throws EmptyQueueException if the queue is empty.
	 */
	public E peek() throws EmptyQueueException;
	
	/**
	 * Returns the current element count in this queue.
	 * 
	 * @return The current element count.
	 */
	public int size();

	/**
	 * Returns true if this queue contains no elements.
	 * 
	 * @return true if this queue contains no elements.
	 */
	public boolean isEmpty();

	/**
	 * Returns true if the number of elements in this queue 
	 * equals the length. This method is only implemented 
	 * when a queue with a fixed length is required.
	 * 
	 * @return <code>true</code> if this queue contains no elements.
	 */
	public boolean isFull();
	
	/**
	 * Removes all the elements from this queue.
	 */
	public void dequeueAll();
	
	/**
	 * Searches for the given element in this queue. 
	 * Returns the 1-based index of the element if 
	 * found (the head of the queue is at position 1).
	 * 
	 * @param toFind The element to search for in this queue.
	 * @return The 1-based index of the element if found, or -1 if the 
	 * 		   element is not in this queue, or queue is empty.
	 * @throws NullPointerException	If the specified element is
	 *                              <code>null</code> and the queue
	 *                              implementation does not support having
	 *                              <code>null</code> elements.
	 */
	public int search( E toFind ) throws NullPointerException;

	/**
	 * Returns true if this queue contains the specified element.
	 * Will return false if the queue is empty.
	 * 
	 * @param toFind The element whose presence in this queue is to be tested.
	 * @return <code>true</code> if this queue contains the specified element.
	 * @throws NullPointerException	If the specified element is
	 *                              <code>null</code> and the queue
	 *                              implementation does not support having
	 *                              <code>null</code> elements.
	 */
	public boolean contains( E toFind ) throws NullPointerException;

	/**
	 * Returns an array containing all of the elements in 
	 * this queue in proper sequence. The head of the queue 
	 * corresponds to the first element in the array.
	 * If the queue is empty, an empty array will be returned.
	 * 
	 * @return An array containing all of the elements
	 * 		   in this queue in proper sequence or an
	 * 		   empty array if this queue is empty.
	 */
	public Object[] toArray();
	
	/**
	 * Returns an array containing all of the elements in 
	 * this queue in proper sequence. The head of the queue 
	 * corresponds to the first element in the array.
	 * If the queue is empty, an empty array will be returned.
	 * 
	 * @param copy The array into which the elements of this queue are to be
	 *             stored, if it is big enough; otherwise, a new array of the same
	 *             runtime type is allocated for this purpose.
	 * @return An array containing the elements of this queue or an
	 * 		   empty array if this queue is empty.
	 * @throws NullPointerException If the specified array is <code>null</code>.
	 */
	public E[] toArray( E[] copy ) throws NullPointerException;
	
	/**
	 * Compares this queue with another queue for equality. Two queues are 
	 * considered equal if they contain the same elements in the same order.
	 *
	 * @param that The queue to be compared with this queue.
	 * @return <code>true</code> if the queues contain equal elements 
     * 		   in the same order, false otherwise.
	 */
	public boolean equals( QueueADT<E> that );
	
	/**
	 * Returns an iterator over the elements in this queue, in proper sequence.
	 * 
	 * @return An iterator over the elements in this queue, in proper sequence.
	 */
	public Iterator<E> iterator();
}
