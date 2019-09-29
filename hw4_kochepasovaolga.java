/*  Name: Olga Kochepasova
 *  COSC 311  FA19
 *  hw0926a
 *  URL: github.com/okochepasova/COSC311/blob/master/hw4_kochepasovaolga.java
 */

public class Homework4{

    public static boolean isPalindrome(String[] arr) {
        int l = arr.length - 1;

        for(int i = 0; 2*i <= l; i++)
            if(arr[i] != arr[l-i])
                return false;

        return true;
    }

    public static void main(String []args){
        String[] a1 = {"a", "b", "c", "d"}, 
a2 = {"a", "boy", "boy", "a"}, 
a3 = {"a", "b", "caliph", "b", "a"};
        String[][] list = {a1, a2, a3};

        for(String[] a : list) {
            for(String x : a)
                System.out.print(x + ", ");
            System.out.println("\t-->  "+isPalindrome(a));
        }
    }
}