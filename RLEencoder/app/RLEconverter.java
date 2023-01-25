package app;

import java.util.Scanner;
import java.io.*;

public class RLEconverter {
   private final static int DEFAULT_LEN = 100; // used to create arrays.

   /*
    *  This method reads in an uncompressed ascii image file that contains 
    *  2 characters. It stores each line of the file in an array.
    *  It then calls compressLines to get an array that stores the compressed
    *  version of each uncompressed line from the file. The compressed array
    *  is then passed to the getCompressedFileStr method which returns a String
    *  of all compressed lines (the two charcaters are written in the first line)
    *  in CSV format. This String is written to a text file with the prefix "RLE_"
    *  added to the original, uncompressed file name.
    *  Note that dataSize keeps track of the number of lines in the file. The array 
    *  that holds the lines of the file is initialized to the DEFAULT_LEN, which 
    *  is assumed to be << the number of lines in the file.
    */   
  public void compressFile(String fileName) throws IOException{
    Scanner scan = new Scanner(new FileReader(fileName));
    String line = null;
    String[] decompressed = new String [DEFAULT_LEN];
    int dataSize = 0;
    while(scan.hasNext()){
      line = scan.next();
      if(line != null && line.length()>0)
        decompressed[dataSize]=line;
        dataSize++;
    }
    scan.close();
    char[] fileChars = discoverAllChars(decompressed, dataSize); 
    String[] compressed = compressLines(decompressed, dataSize, fileChars);
    writeFile(getCompressedFileStr(compressed, fileChars), "RLE_"+fileName);
  }
  
   
/*
 * This method implements the RLE compression algorithm. It takes a line of uncompressed data
 * from an ascii file and returns the RLE encoding of that line in CSV format.
 * The two characters that make up the image file are passed in as a char array, where
 * the first cell contains the first character that occurred in the file.
*/
public String compressLine(String line, char[] fileChars){
   String compFile = "";
   int count = 0;
   int index = 0;
   for (int i = 0; i < line.length(); i++) {
     if (fileChars[index] == line.charAt(i)) {
       count++;
     } else {
       compFile += "" + count;
     if (i <= (line.length() - 1)) {
       compFile += ",";
     }

     if (index == 0) {
       index = 1;
     } else {
       index = 0;
      }
      count = 1;
     }
   }
   compFile += "" + count;
      return compFile;
}

  /*
   *  This method discovers the two ascii characters that make up the image. 
   *  It iterates through all of the lines and writes each compressed line
   *  to a String array which is returned. The method compressLine is called on 
   *  each line.
   *  The dataSize is the number of lines in the file, which is likely to be << the length of lines.
   */
  public String[] compressLines(String[] lines, int dataSize, char[] fileChars){
       String[] compressed = new String[dataSize];
       for (int i = 0; i < dataSize; i++) {
         compressed[i] = this.compressLine(lines[i], fileChars);
       }
      return compressed;
}

/*
 *  This method assembles the lines of compressed data for
 *  writing to a file. The first line must be the 2 ascii characters
 *  in comma-separated format. 
 */
public String getCompressedFileStr(String[] compressed, char[] fileChars) {
    String newLines = "";
    newLines += fileChars[0] + "," + fileChars[1];
    for (int i = 0; i < compressed.length; i++) {
      newLines += "\n" + compressed[i];
    }
      return newLines;
}
   /*
    *  This method reads in an RLE compressed ascii image file that contains 
    *  2 characters. It stores each line of the file in an array.
    *  It then calls decompressLines to get an array that stores the decompressed
    *  version of each compressed line from the file. The first row contains the two 
    *  ascii charcaters used in the original image file. The decompressed array
    *  is then passed to the getDecompressedFileStr method which returns a String
    *  of all decompressed lines, thus restoring the original, uncompressed image.
    *  This String is written to a text file with the prefix "DECOMP_"
    *  added to the original, compressed file name.
    *  Note that dataSize keeps track of the number of lines in the file. The array 
    *  that holds the lines of the file is initialized to the DEFAULT_LEN, which 
    *  is assumed to be << the number of lines in the file.
    */   
  public void decompressFile(String fileName) throws IOException{
    Scanner scan = new Scanner(new FileReader(fileName));
    String line = null;
    String[] compressed = new String [DEFAULT_LEN];
    int dataSize =0;
    while(scan.hasNext()){
      line = scan.next();
      if(line != null && line.length()>0)
        compressed[dataSize]=line;
        dataSize++;
    }
    scan.close();
    String[] decompressed = decompressLines(compressed, dataSize);
    writeFile(getDecompressedFileStr(decompressed), "DECOMP_"+fileName);
  }
 
   /*
   * This method decodes lines that were encoded by the RLE compression algorithm. 
   * It takes a line of compressed data and returns the decompressed, or original version
   * of that line. The two characters that make up the image file are passed in as a char array, 
   * where the first cell contains the first character that occurred in the file.
   */
   public String decompressLine(String line, char[] fileChars){
      String decompressed = "";
      int index = 0;
      String [] numbers = line.split(",");
      for (int i = 0; i < numbers.length; i++) {
        int count = Integer.parseInt(numbers[i]);
        String fileChar = Character.toString(fileChars[index]);
        decompressed += fileChar.repeat(count);
        if (index == 0) {
          index = 1;
        } else {
          index = 0;
        }
      }
        return decompressed;
   }
    /*
   *  This method iterates through all of the compressed lines and writes 
   *  each decompressed line to a String array which is returned. 
   *  The method decompressLine is called on each line. The first line in
   *  the compressed array passed in are the 2 ascii characters used to make
   *  up the image. 
   *  The dataSize is the number of lines in the file, which is likely to be << the length of lines.
   *  The array returned contains only the decompressed lines to be written to the decompressed file.
   */
  public String[] decompressLines(String[] lines, int dataSize){
     String[] decompressed = new String[dataSize];
     char[] fileChars = new char[2];
     fileChars[0] = lines[0].charAt(0);
     fileChars[1] = lines[0].charAt(2);

     for (int i = 1; i < dataSize; i++) {
       decompressed[i - 1] = this.decompressLine(lines[i], fileChars);
     }
      return decompressed;
  }
  
  /*
   *  This method assembles the lines of decompressed data for
   *  writing to a file. 
   */
  public String getDecompressedFileStr(String[] decompressed){ 
   String data = "";
   for (int i = 1; i < decompressed.length; i++) {
     data += decompressed[i] + "\n";
   }
      return data;
  }

  // assume the file contains only 2 different ascii characters.
  public char[] discoverAllChars(String[] decompressed, int dataSize){
    char[] discover = new char[2];
    discover[0] = decompressed[0].charAt(0);
    for (int i = 0; i < dataSize; i++) {
      for (int j = 0; j < decompressed[i].length(); j++) {
        if (decompressed[i].charAt(j) != discover[0]) {
          discover[1] = decompressed[i].charAt(j);
          break;
        }
      }
    }
  return discover;
}



   
   public void writeFile(String data, String fileName) throws IOException{
		PrintWriter pw = new PrintWriter(fileName);
      pw.print(data);
      pw.close();
   }
}