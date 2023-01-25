package priorityqueue;

import java.util.Comparator;

public class Heap<T> implements PriorityQueueADT<T> {

  private int numElements;
  private T[] heap;
  private boolean isMaxHeap;
  private Comparator<T> comparator;
  private final static int INIT_SIZE = 5;

  /**
   * Constructor for the heap.
   * @param comparator comparator object to define a sorting order for the heap elements.
   * @param isMaxHeap Flag to set if the heap should be a max heap or a min heap.
   */
  public Heap(Comparator<T> comparator, boolean isMaxHeap) {
      //TODO: Implement this method.
      this.comparator = comparator;
      this.isMaxHeap = isMaxHeap;
      this.heap = (T[]) new Object[INIT_SIZE];
      numElements = 0;
  }

  /**
   * This results in the entry at the specified index "bubbling up" to a location
   * such that the property of the heap are maintained. This method should run in
   * O(log(size)) time.
   * Note: When enqueue is called, an entry is placed at the next available index in 
   * the array and then this method is called on that index. 
   *
   * @param index the index to bubble up
   */
  public void bubbleUp(int index) {
    //TODO: Implement this method.
    if (compare(heap[index], heap[getParentOf(index)]) <= 0) {
      return;
    }
    else if (index > 0) {
      swap(index, getParentOf(index));
      bubbleUp(getParentOf(index));
    }
}

  /**
   * This method results in the entry at the specified index "bubbling down" to a
   * location such that the property of the heap are maintained. This method
   * should run in O(log(size)) time.
   * Note: When remove is called, if there are elements remaining in this
   *  the bottom most element of the heap is placed at
   * the 0th index and bubbleDown(0) is called.
   * 
   * @param index
   */
  public void bubbleDown(int index) {
    //TODO: Implement this method.
    T max = heap[index];
    int maxIndex = -1;
    for (int i = 0; i < 2 && i + getLeftChildOf(index) < size(); i++) {
      if (compare(heap[i + getLeftChildOf(index)], max) > 0) {
        max = heap[i + getLeftChildOf(index)];
        maxIndex = i + getLeftChildOf(index);
      }
    }
    if (max == heap[index]) {
      return;
    }
    if (getLeftChildOf(index) < size()) {
      swap(index, maxIndex);
      bubbleDown(maxIndex);
      getLeftChildOf(index);
    }
  }

  

  /**
   * Test for if the queue is empty.
   * @return true if queue is empty, false otherwise.
   */
  public boolean isEmpty() {
    //TODO: Implement this method.
    return numElements == 0;
  }

  /**
   * Number of data elements in the queue.
   * @return the size
   */
  public int size(){
    //TODO: Implement this method.
    return numElements;
  }

  /**
   * Compare method to implement max/min heap behavior. It changes the value of a variable, compareSign, 
   * beased on the state of the boolean variable isMaxHeap. It then calls the compare method from the 
   * comparator object and multiplies its output by compareSign.
   * @param element1 first element to be compared
   * @param element2 second element to be compared
   * @return positive int if {@code element1 > element2}, 0 if {@code element1 == element2}, 
   * negative int otherwise (if isMaxHeap),
   * return negative int if {@code element1 > element2}, 0 if {@code element1 == element2}, 
   * positive int otherwise (if ! isMinHeap).
   */
  public int compare(T element1 , T element2) {
    int result = 0;
    int compareSign =  -1;
    if (isMaxHeap) {
      compareSign = 1;
    }
    result = compareSign * comparator.compare(element1, element2);
    return result;
  }

  /**
   * Return the element with highest (or lowest if min heap) priority in the heap 
   * without removing the element.
   * @return T, the top element
   * @throws QueueUnderflowException if empty
   */
  public T peek() throws QueueUnderflowException {
    //TODO: Implement this method.
    if (isEmpty()) {
      throw new QueueUnderflowException();
    }
    T data = heap[0];
    return data;
  }  

  /**
   * Removes and returns the element with highest (or lowest if min heap) priority in the heap.
   * @return T, the top element
   * @throws QueueUnderflowException if empty
   */
  public T dequeue() throws QueueUnderflowException{
      //TODO: Implement this method.
    if (isEmpty()) {
      throw new QueueUnderflowException();
    } 
    T data = heap[0];
    numElements--;
    heap[0] = heap[numElements];
    heap[numElements] = null;
    bubbleDown(0);
    return data;
  }

  /**
   * Enqueue the element.
   * @param the new element
   */
  public void enqueue(T newElement) {
      //TODO: Implement this method.
    if (numElements == heap.length) {
      expandCapacity();
    }
    heap[numElements] = newElement;
    bubbleUp(numElements);
    numElements++;
  }

  private int getLeftChildOf(int index) {
    return (index * 2) + 1;
  }

  private int getRightChildOf(int index) {
    return (index * 2) + 2;
  }

  private int getParentOf(int index) {
    return (index - 1) / 2;
  }

  private void swap(int index1, int index2) {
    T temp = heap[index1];
    heap[index1] = heap[index2];
    heap[index2] = temp;
  }
 
  private void expandCapacity() {
    T[] arrayHeap = (T[])new Object[heap.length * 2];
    for (int i = 0; i < heap.length; i++) {
      arrayHeap[i] = heap[i];
    }
    heap = arrayHeap;
  }

}