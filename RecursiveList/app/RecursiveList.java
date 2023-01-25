package app;
 
import java.util.Iterator;
 
public class RecursiveList<T> implements ListInterface<T> {
 
 private int size;
 private Node<T> head = null;
 
 public RecursiveList() {
   this.head = null;
   this.size = 0;
 }
 
 public RecursiveList(Node<T> first) {
   this.head = first;
   this.size = 1;
 }
 
 @Override
 public int size() {
   return size;
 }
 
 @Override
 public void insertFirst(T elem) {
    //TODO: Implement this method.
   if (elem == null)
    throw new NullPointerException();
 
   Node<T> nodeToInsert = new Node<T>(elem, head);
   head = nodeToInsert;
   this.size++;
 }
 
 @Override
 public void insertLast(T elem) {
     //TODO: Implement this method.
   if (elem == null) {
     throw new NullPointerException();
   } 
   else if(head == null){
     insertFirst(elem);
   }
   else{
    this.head = insertLastHelper(head, elem);
    this.size++;
   }
 }
 
 
 //helper method that inserts element to the end of the linked list
 private Node<T> insertLastHelper(Node<T> node, T elem) {

   if (node == null) {
     Node<T> insertedNode = new Node<T>(elem, null);
     return insertedNode;
   } 
   node.setNext(insertLastHelper(node.getNext(), elem));
   return node;
 }
 
 @Override
 public void insertAt(int index, T elem) {
     //TODO: Implement this method.
   if (elem == null) {
     throw new NullPointerException();
   } else if (index < 0 || index > this.size) {
     throw new IndexOutOfBoundsException();
   }
     head = insertAtHelper(head, index, elem);
     this.size++;
 }
 
 //helper method that inserts elem at index position
 private Node<T> insertAtHelper(Node<T> head, int index, T elem) {
   if (index == 0) {
     Node<T> nodeToInsert = new Node<T>(elem, head);
     return nodeToInsert;
   }
   Node<T> insertedNode = insertAtHelper(head.getNext(), index - 1, elem);
   head.setNext(insertedNode);
   return head;
 }
 
 @Override
 public T removeFirst() {
  
     //TODO: Implement this method.
     if (this.isEmpty()) {
       throw new IllegalStateException();
     }
     T removedItem = null;
     if (head != null) {
       removedItem = head.getData();
       head = head.getNext();
       this.size--;
     }
 
   return removedItem;
 }
 
 @Override
 public T removeLast() {
     //TODO: Implement this method.
     if (isEmpty()) {
       throw new IllegalStateException();
     }
     T removedItem = null;
     if (this.size() == 0) {
       removedItem = head.getData();
       head = head.getNext();
     }
 
     Node<T> previousTail = removeLastHelper(head.getNext(), head);
     if (previousTail != null) {
       removedItem = previousTail.getNext().getData();
       previousTail.setNext(null);
     }
     this.size--;
   return removedItem;
 }
 
 private Node<T> removeLastHelper(Node<T> currNode, Node<T> previous) {
   if (currNode != null) {
     if (currNode.getNext() == null)
       return previous;
     else {
       previous = removeLastHelper(currNode.getNext(), currNode);
     }
   }
   return previous;
 }
 
 @Override
 public T removeAt(int i) {
   //TODO: Implement this method.
   if (i < 0 || i >= this.size()) {
     throw new IndexOutOfBoundsException();
     }
   T removedItem = null;
   Node<T> removedNode = removeAtHelper(head, i);
   if (removedNode != null) {
     return removedNode.getData();
   }
   return removedItem;
 }
 
 private Node<T> removeAtHelper(Node<T> node, int i) {
   if (head == null)
     return null;
   if (i == 0)
     return head.getNext();
   Node<T> prevNode = getNode(head, i - 1);
   Node<T> returnItem = prevNode.getNext(); 
   if (prevNode != null && prevNode.getNext() != null) {
    prevNode.setNext(prevNode.getNext().getNext());
   } else if (prevNode != null) {
    prevNode = prevNode.getNext();
   }
   this.size--;
   return returnItem;
 }
 
 private Node<T> getNode(Node<T> head, int i) {
   Node<T> nodeN = null;
   if (i == 0) {
     return head;
   }
   nodeN = getNode(head.getNext(), i - 1);
   return nodeN;
 }
 
 @Override
 public T getFirst() {
     //TODO: Implement this method.
     if (isEmpty())
       throw new IllegalStateException();
   T item = null; 
   item = head.getData();
 
   return item;
 }
 
 @Override
 public T getLast() {
     //TODO: Implement this method.
   if (isEmpty())
     throw new IllegalStateException();
   T item = null;
   Node<T> tail = getLastHelper(head);
   item = tail.getData();
   return item;
 }
 
 private Node<T> getLastHelper(Node<T> head) {
   if (head != null && head.getNext() == null)
     return head;
   head = getLastHelper(head.getNext());
   return head;
 }
 
 @Override
 public T get(int i) {
   //TODO: Implement this method.
   if (i < 0 || i >= this.size)
     throw new IndexOutOfBoundsException();
 
   T item = null;
   item = getHelper(head, i);
   return item;
 }
 
 private T getHelper(Node<T> head, int i) {
   T item = null;
   if (i == 0) {
     item = head.getData();
     return item;
   }
   item = getHelper(head.getNext(), i - 1);
   return item;
 }
 
 @Override
 public void remove(T elem) {
   //TODO: Implement this method.
   if (elem == null)
     throw new NullPointerException();
   removeHelper(head, elem);
 
 }
 
 private void removeHelper(Node<T> head, T elem) {
   if (head == null)
     throw new ItemNotFoundException();
   if (head.getData() == elem) {
     head = head.getNext();
     this.size--;
   } else {
     removeHelper(head.getNext(), elem);
   }
 }
 
 @Override
 public int indexOf(T elem) {
   //TODO: Implement this method.
   if (elem == null)
     throw new NullPointerException();
   int index = indexOfHelper(elem, head, 0);
   return index;
 }
 
 private int indexOfHelper( T elem, Node<T> head, int index) {
   if (index>size|| index < 0 || head == null)
     return -1;
   else if (elem.equals(head.getData()))
     return index;
    else{
      return indexOfHelper(elem, head.getNext(), index++);
    }
   
 }
 
 @Override
 public boolean isEmpty() {
   boolean empty = false;
     //TODO: Implement this method.
   if (head == null)
     empty = true;
 
   return empty;
 }
 
 
 public Iterator<T> iterator() {
   Iterator<T> iter = null;
     //TODO: Implement this method.
   iter = new LinkedNodeIterator<T>(head);
 
  return iter;
 }
}
 
