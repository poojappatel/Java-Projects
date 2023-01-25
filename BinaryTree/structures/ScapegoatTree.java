package structures;

public class ScapegoatTree<T extends Comparable<T>> extends
		BinarySearchTree<T> {
	private int upperBound;


	@Override
  public void add(T t) {
    // TODO: Implement the add() method
    if (t == null) {
      throw new NullPointerException();
    }
    upperBound++;
    BSTNode<T> node = new BSTNode<T>(t, null, null);
    root = addToSubtree(root, node);
    if (height() > Math.log(upperBound) / Math.log(3.0/2.0)) {
      BSTNode<T> spW = node.parent;
      while ((double)subtreeSize(spW) / subtreeSize(spW.parent) <= 2.0/3.0) {
        spW = spW.parent;
        ScapegoatTree<T> newSub = new ScapegoatTree<T>();
        newSub.root = spW;
        BSTNode<T> firstParent = spW.parent;
        newSub.balance();
        if(firstParent.getLeft() == spW) {
          firstParent.setLeft(newSub.root);
        } else {
          firstParent.setRight(newSub.root);
        }
      }
    }
  } 
	
	@Override
  public boolean remove(T element) {
    // TODO: Implement the remove() method
    if (element == null)
      throw new NullPointerException();
    boolean removeNode = super.remove(element);
    if (upperBound > 2 * size()) {
      balance();
      upperBound = size();
    }
    return removeNode;
  }


  public static void main(String[] args) {
    BSTInterface<String> tree = new ScapegoatTree<String>();
    /*\
    //You can test your Scapegoat tree here.
    for (String r : new String[] {"0", "1", "2", "3", "4"}) {
      tree.add(r);
      System.out.println(toDotFormat(tree.getRoot()));
    }
    */
  }
}
