/* Olga Kochepasova
 * COSC 311 - FA19
 * Homework 8 - hw1114
 * URL: https://github.com/okochepasova/COSC311/blob/master/hw8/HW8.java
 */

import java.util.Random;
import java.util.ArrayList;

import java.io.IOException;
import java.io.RandomAccessFile;


public class HW8 {
  private static int SIZE = 11;
  private Random r;
  private RandomAccessFile fp;


  public HW8() {
    r = new Random(68);
    try {
      // Initial population
      fp = new RandomAccessFile("data.raf", "rw");
      for (int i=0; i<SIZE; i++) fp.writeInt(-1);
      fp.close();
    }
    catch (IOException err) { System.out.println(err); }
  }


  public int newRand() {
    return r.nextInt(100);
  }

  private int h1(int key) {
    return key % SIZE;
  }

  int store(int key) {
    int indx = h1(key);
    try {
      fp = new RandomAccessFile("data.raf", "rw");
      fp.seek(indx*4);

      if (fp.readInt() < 0) {
        fp.seek(indx*4);
        fp.writeInt(key);
      }
      else indx = -1;

      fp.close();
    }
    catch (IOException err) { System.out.println(err); }
    return indx;
  }

  public void run() {
    int key, indx;
    for (int i=0; i<8; i++) {
      key = newRand();
      indx = store(key);
    }
    System.out.println(this);
  }

  public String toString() {
    ArrayList<String> list = new ArrayList();
    String lineBreak = "+---------------+---------------+---------------+";
    list.add(lineBreak);
    list.add("|  byte offset\t|   int index\t|   int value\t|");
    list.add(lineBreak);

    try {
      fp = new RandomAccessFile("data.raf", "r");
      int randValue;
      String newEntry;

      for (int i=0; i<SIZE; i++) {
        randValue = fp.readInt();
        newEntry = "|\t"+(i*4)+"\t|\t"+i+"\t|\t";

        if (randValue < 0) list.add( newEntry+"-\t|" );
        else list.add( newEntry+randValue+"\t|" );
      }

      list.add(lineBreak);
      fp.close();
    }
    catch (IOException err) { System.out.println(err); }

    return String.join("\n", list);
  }


  public static void main(String[] args)   {
    HW8 obj = new HW8();
    obj.run();
  }
}
