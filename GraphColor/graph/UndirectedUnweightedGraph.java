package graph;
import java.util.ArrayList;
/**
 * This class implements general operations on a graph as specified by UndirectedGraphADT.
 * It implements a graph where data is contained in Vertex class instances.
 * Edges between verticies are unweighted and undirected.
 * A graph coloring algorithm determines the chromatic number. 
 * Colors are represented by integers. 
 * The maximum number of vertices and colors must be specified when the graph is instantiated.
 * You may implement the graph in the manner you choose. See instructions and course material for background.
 */
 
 public class UndirectedUnweightedGraph<T> implements UndirectedGraphADT<T> {
   // private class variables here.
   
   private int MAX_VERTICES;
   private int MAX_COLORS;
   // TODO: Declare class variables here.
   private ArrayList<ArrayList<Vertex<T>>> vertexList;
   
   /**
    * Initialize all class variables and data structures. 
   */   
   public UndirectedUnweightedGraph (int maxVertices, int maxColors){
    MAX_VERTICES = maxVertices;
    MAX_COLORS = maxColors; 
    // TODO: Implement the rest of this method.
    vertexList = new ArrayList<ArrayList<Vertex<T>>>();
   }

   /**
    * Add a vertex containing this data to the graph.
    * Throws Exception if trying to add more than the max number of vertices.
   */
   public void addVertex(T data) throws Exception {
    // TODO: Implement this method.
    if (vertexList.size() == MAX_VERTICES) 
      throw new Exception("Maximum number of vertices reached");
     ArrayList<Vertex<T>> list = new ArrayList<Vertex<T>>();
     list.add(new Vertex<T>(data));
     vertexList.add(list);
   }
   
   /**
    * Return true if the graph contains a vertex with this data, false otherwise.
   */   
   public boolean hasVertex(T data) {
    // TODO: Implement this method.
    for (ArrayList<Vertex<T>> currV : vertexList) 
      if (currV.get(0).getData().equals(data)) 
        return true;
      return false;
   } 

   /**
    * Add an edge between the vertices that contain these data.
    * Throws Exception if one or both vertices do not exist.
   */   
   public void addEdge(T data1, T data2) throws Exception {
    // TODO: Implement this method.
    if (!hasVertex(data1) || !hasVertex(data2)) 
      throw new Exception("Vertex does not exist");
    getVertexList(data1).add(getVertexList(data2).get(0));
    getVertexList(data2).add(getVertexList(data1).get(0));
   }

   /**
    * Get an ArrayList of the data contained in all vertices adjacent to the vertex that
    * contains the data passed in. Returns an ArrayList of zero length if no adjacencies exist in the graph.
    * Throws Exception if a vertex containing the data passed in does not exist.
   */   
   public ArrayList<T> getAdjacentData(T data) throws Exception{
    // TODO: Implement this method.
    if (!hasVertex(data)) 
      throw new Exception("Vertex does not exist");
    ArrayList<T> list = new ArrayList<>();
    for (Vertex<T> currV : getVertexList(data)) {
      list.add(currV.getData());
    }
    list.remove(0);
    return list;
    }
   
   /**
    * Returns the total number of vertices in the graph.
   */   
   public int getNumVertices(){
    // TODO: Implement this method.
      return vertexList.size();
   }

   /**
    * Returns the total number of edges in the graph.
   */   
   public int getNumEdges(){
    // TODO: Implement this method.
    int count = 0;
    for (ArrayList<Vertex<T>> currV : vertexList)  
      count += currV.size() - 1;
    return count / 2;
   }

   /**
    * Returns the minimum number of colors required for this graph as 
    * determined by a graph coloring algorithm.
    * Throws an Exception if more than the maximum number of colors are required
    * to color this graph.
   */   
   public int getChromaticNumber() throws Exception{
    // TODO: Implement this method.
    int highestColorUsed = -1;
    int colorToUse = -1;
    for (ArrayList<Vertex<T>> currV : vertexList) {
      if (currV.get(0).getColor() == -1) {
        colorToUse = getColorToUse(currV);
        currV.get(0).setColor(colorToUse);
        if (highestColorUsed > colorToUse)
          return highestColorUsed;
        else
          highestColorUsed = colorToUse; 
      }
    }  
    return highestColorUsed + 1;
   }
   
   private int getColorToUse(ArrayList<Vertex<T>> curVertex) throws Exception{
    int colorToUse = -1;
    for (Vertex<T> curAdjVert : curVertex) {
      if (curAdjVert.getColor() > colorToUse) {
        colorToUse = curAdjVert.getColor();
      }
    }
    colorToUse++;
    if (colorToUse > MAX_COLORS -1) 
      throw new Exception("All colors have been used, the Max number of colors has been exceeded.");
    return colorToUse;
  }

  private ArrayList<Vertex<T>> getVertexList(T data) {
    for (ArrayList<Vertex<T>> currV : vertexList) 
      if (currV.get(0).getData().equals(data)) 
        return currV;
    return null;
  }

   
}