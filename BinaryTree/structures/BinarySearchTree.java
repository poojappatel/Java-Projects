package structures;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class BinarySearchTree<T extends Comparable<T>> implements BSTInterface<T> {
  protected BSTNode<T> root;

  public boolean isEmpty() {
		return root == null;
	}

	public int size() {
		return subtreeSize(root);
	}

	protected int subtreeSize(BSTNode<T> node) {
    if (node == null) {
      return 0;
    } else {
      return 1 + subtreeSize(node.getLeft()) + subtreeSize(node.getRight());
    }
  }

	public boolean contains(T t) {
    // TODO: Implement the contains() method
    if (t == null)
      throw new NullPointerException();
    return containsHelper(t, root);
  }

  public boolean containsHelper(T t, BSTNode<T> node) {
    if (node == null)
     return false;
    else if (t.compareTo(node.getData()) < 0)
      return containsHelper(t, node.getLeft());
    else if (t.compareTo(node.getData()) > 0)
      return containsHelper(t, node.getRight());
    else
      return true;
  }

	public boolean remove(T t) {
		if (t == null) {
			throw new NullPointerException();
		}
		boolean result = contains(t);
		if (result) {
			root = removeFromSubtree(root, t);
		}
		return result;
	}

	protected BSTNode<T> removeFromSubtree(BSTNode<T> node, T t) {
		// node must not be null
		int result = t.compareTo(node.getData());
		if (result < 0) {
			node.setLeft(removeFromSubtree(node.getLeft(), t));
			return node;
		} else if (result > 0) {
			node.setRight(removeFromSubtree(node.getRight(), t));
			return node;
		} else { // result == 0
			if (node.getLeft() == null) {
				return node.getRight();
			} else if (node.getRight() == null) {
				return node.getLeft();
			} else { // neither child is null
				T predecessorValue = getHighestValue(node.getLeft());
				node.setLeft(removeRightmost(node.getLeft()));
				node.setData(predecessorValue);
				return node;
			}
		}
	}

	private T getHighestValue(BSTNode<T> node) {
		// node must not be null
		if (node.getRight() == null) {
			return node.getData();
		} else {
			return getHighestValue(node.getRight());
		}
	}

	private BSTNode<T> removeRightmost(BSTNode<T> node) {
		// node must not be null
		if (node.getRight() == null) {
			return node.getLeft();
		} else {
			node.setRight(removeRightmost(node.getRight()));
			return node;
		}
	}

	public T get(T t) {
    // TODO: Implement the get() method
    if (t == null) {
      throw new NullPointerException();
    }
    return getHelper(t, root);
  }

  private T getHelper(T t, BSTNode<T> curNode) {
    if (curNode == null)
      return null;
    if (t.compareTo(curNode.getData()) == 0)
      return curNode.getData();
    else if (t.compareTo(curNode.getData()) < 0)
      return getHelper(t, curNode.getLeft());
    else 
      return getHelper(t, curNode.getRight());
  }


	public void add(T t) {
		if (t == null) {
			throw new NullPointerException();
		}
		root = addToSubtree(root, new BSTNode<T>(t, null, null));
	}

	protected BSTNode<T> addToSubtree(BSTNode<T> node, BSTNode<T> toAdd) {
		if (node == null) {
			return toAdd;
		}
		int result = toAdd.getData().compareTo(node.getData());
		if (result <= 0) {
			node.setLeft(addToSubtree(node.getLeft(), toAdd));
		} else {
			node.setRight(addToSubtree(node.getRight(), toAdd));
		}
		return node;
	}

	@Override
	public T getMinimum() {
    // TODO: Implement the getMinimum() method
    if (root == null)
      return null;
    return getMinimumHelper(root);
  }

  private T getMinimumHelper(BSTNode<T> curNode) {
    if (curNode.getLeft() != null) {
      return getMinimumHelper(curNode.getLeft());
    }
    return curNode.getData();
  }


	@Override
	public T getMaximum() {
    // TODO: Implement the getMaximum() method
    if (root == null)
      return null;
    return getMaximumHelper(root);
  }

  private T getMaximumHelper(BSTNode<T> curNode) {
    if (curNode.getRight() != null) {
      return getMaximumHelper(curNode.getRight());
    }
    return curNode.getData();
  }


  @Override
  public int height() {
    // TODO: Implement the height() method
    return heightHelper(root);
	}

  private int heightHelper(BSTNode<T> curNode) {
		if (curNode == null) 
      return -1;
    int leftHeight = heightHelper(curNode.getLeft());
    int rightHeight = heightHelper(curNode.getRight());
		return Math.max(leftHeight, rightHeight) + 1;
  }


	public Iterator<T> preorderIterator() {
    // TODO: Implement the preorderIterator() method
    Queue<T> queue = new LinkedList<T>();
    preorderTraverse(queue, root);
    return queue.iterator();
  }

  private void preorderTraverse(Queue<T> queue, BSTNode<T> node) {
    if (node != null) {
      queue.add(node.getData());
      preorderTraverse(queue, node.getLeft());
      preorderTraverse(queue, node.getRight());
    }
  }


  public Iterator<T> inorderIterator() {
    Queue<T> queue = new LinkedList<T>();
    inorderTraverse(queue, root);
    return queue.iterator();
  }


  private void inorderTraverse(Queue<T> queue, BSTNode<T> node) {
    if (node != null) {
      inorderTraverse(queue, node.getLeft());
      queue.add(node.getData());
      inorderTraverse(queue, node.getRight());
    }
  }

  public Iterator<T> postorderIterator() {
    // TODO: Implement the postorderIterator() method
    Queue<T> queue = new LinkedList<T>();
    postorderTraverse(queue, root);
    return queue.iterator();
  }

  private void postorderTraverse(Queue<T> queue, BSTNode<T> node) {
    if (node != null) {
      postorderTraverse(queue, node.getLeft());
      postorderTraverse(queue, node.getRight());
      queue.add(node.getData());
    }
  }

	@Override
	public boolean equals(BSTInterface<T> other) {
		// TODO
		return equals(root, other.getRoot());
	}
	
	private boolean equals(BSTNode<T> treeOne, BSTNode<T> treeTwo) {
		if (treeOne == null && treeTwo == null) 
      return true;
		else if (treeOne == null || treeTwo == null) 
      return false;
		else {
			if (!(treeOne.getData().compareTo(treeTwo.getData()) == 0)) 
        return false;
			return equals(treeOne.getLeft(), treeTwo.getLeft()) && equals(treeOne.getRight(), treeTwo.getRight());
		}
	}


	@Override
	public boolean sameValues(BSTInterface<T> other) {
    // TODO: Implement the sameValues() method
    Iterator<T> treeOne = this.inorderIterator();
		Iterator<T> treeTwo = other.inorderIterator();
		while (treeOne.hasNext() && treeTwo.hasNext()) {
			if (treeOne.next().compareTo(treeTwo.next()) != 0)
				return false;
    }
		if (!treeOne.hasNext() && !treeTwo.hasNext())
      return true;
    return false;
  }

	@Override
	public boolean isBalanced() {
    // TODO: Implement the isBalanced() method
    return (isBalancedHelper(root) == -1) ? false : true;
  }
    
  private int isBalancedHelper(BSTNode<T> node) {
    if (node == null)
      return 0;   
    int left  = isBalancedHelper(node.getLeft());
    int right = isBalancedHelper(node.getRight());
    if (left == -1 || right == -1)
      return -1;
    if (Math.abs(left - right) > 1) 
      return -1;     
    return 1 + Math.max(left, right);
  }

	@Override
  @SuppressWarnings("unchecked")
  public void balance() {
    // TODO: Implement the balanceHelper() method
    //inorderIter be an inorder iterator
    //size = size of tree
    //values be an array to store the data elements
    Iterator<T> inorderIter = this.inorderIterator();
    int size = size();
    T[] values = (T[]) new Comparable[size()];
    for (int i = 0; i < size; i++) {
      values[i] = inorderIter.next();
    } 
    root = null;
    balanceHelper(values, 0, size - 1);
    }

  protected void balanceHelper(T[] values, int low, int high) {
    if (low == high) 
      add(values[low]);
    else if ((low + 1) == high) {
      add(values[low]);
      add(values[high]);
    } else {
    int mid = (low + high) / 2;
    add(values[mid]);
    balanceHelper(values, low, mid - 1);
    balanceHelper(values, mid + 1, high);
    } 
  }


  @Override
  public BSTNode<T> getRoot() {
    // DO NOT MODIFY
    return root;
  }

  public static <T extends Comparable<T>> String toDotFormat(BSTNode<T> root) {
    // header
    int count = 0;
    String dot = "digraph G { \n";
    dot += "graph [ordering=\"out\"]; \n";
    // iterative traversal
    Queue<BSTNode<T>> queue = new LinkedList<BSTNode<T>>();
    queue.add(root);
    BSTNode<T> cursor;
    while (!queue.isEmpty()) {
      cursor = queue.remove();
      if (cursor.getLeft() != null) {
        // add edge from cursor to left child
        dot += cursor.getData().toString() + " -> " + cursor.getLeft().getData().toString() + ";\n";
        queue.add(cursor.getLeft());
      } else {
        // add dummy node
        dot += "node" + count + " [shape=point];\n";
        dot += cursor.getData().toString() + " -> " + "node" + count + ";\n";
        count++;
      }
      if (cursor.getRight() != null) {
        // add edge from cursor to right child
        dot +=
            cursor.getData().toString() + " -> " + cursor.getRight().getData().toString() + ";\n";
        queue.add(cursor.getRight());
      } else {
        // add dummy node
        dot += "node" + count + " [shape=point];\n";
        dot += cursor.getData().toString() + " -> " + "node" + count + ";\n";
        count++;
      }
    }
    dot += "};";
    return dot;
  }

  public static void main(String[] args) {
    for (String r : new String[] {"a", "b", "c", "d", "e", "f", "g"}) {
      BSTInterface<String> tree = new BinarySearchTree<String>();
      for (String s : new String[] {"d", "b", "a", "c", "f", "e", "g"}) {
        tree.add(s);
      }
      Iterator<String> iterator = tree.inorderIterator();
      while (iterator.hasNext()) {
        System.out.print(iterator.next());
      }
      System.out.println();
      iterator = tree.preorderIterator();
      while (iterator.hasNext()) {
        System.out.print(iterator.next());
      }
      System.out.println();
      iterator = tree.postorderIterator();
      while (iterator.hasNext()) {
        System.out.print(iterator.next());
      }
      System.out.println();

      System.out.println(tree.remove(r));

      iterator = tree.inorderIterator();
      while (iterator.hasNext()) {
        System.out.print(iterator.next());
      }
      System.out.println();
    }

    BSTInterface<String> tree = new BinarySearchTree<String>();
    for (String r : new String[] {"a", "b", "c", "d", "e", "f", "g"}) {
      tree.add(r);
    }
    System.out.println(toDotFormat(tree.getRoot()));
    System.out.println(tree.size());
    System.out.println(tree.height());
    System.out.println(tree.isBalanced());
    tree.balance();
    System.out.println(tree.size());
    System.out.println(tree.height());
    System.out.println(tree.isBalanced());
  }
}
