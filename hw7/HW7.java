/* Olga Kochepasova
 * COSC 311 - FA19
 * Homework 7 - hw1105
 * URL: https://github.com/okochepasova/COSC311/blob/master/hw7/HW7.java
 */

import java.util.Random;
import java.util.ArrayList;


public class HW7 {
  private static int SIZE = 11;
  private Random r;
  private int[] hash;


  public HW7() {
    r = new Random(68);
    hash = new int[SIZE];
    for (int i=0; i<SIZE; i++) hash[i] = -1;
  }


  public int newRand() {
    return r.nextInt(100);
  }

  private int h1(int key) {
    return key % SIZE;
  }

  private int h2(int key) {
    return 7 - (key % 7);
  }

  int store(int key) {
    int k1 = h1(key);
    int k2 = h2(key);
    int indx = k1;

    // Double hashing for collision
    for (int i=1; 0 <= hash[indx] && i<SIZE; i++) {
        indx = (k1 + i*k2) % SIZE;
    }
    hash[indx] = key;

    return indx;
  }

  public void run() {
    int key, indx;
    for (int i=0; i<8; i++) {
      key = newRand();
      indx = store(key);
      System.out.println("key: "+key+"      i: "+indx);
    }
    System.out.println("\n"+this);
  }

  public String toString() {
    ArrayList<String> list = new ArrayList();

    for (int i=0; i<SIZE; i++) {
      if (hash[i] < 0) list.add("- ");
      else list.add( ""+hash[i] );
    }

    return "hash table: ["+String.join(", ", list)+']';
  }


  public static void main(String[] args)   {
    HW7 obj = new HW7();
    obj.run();
  }
}
