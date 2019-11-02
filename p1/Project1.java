/*  Name: Olga Kochepasova
 *  COSC 311 - FA19
 *  pp1008
 *  URL: https://github.com/okochepasova/COSC311/blob/master/p1/Project1.java
 */
import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;


public class Project1 {
    /* Customers’ wait time <= 5 minutes each
      The extent of time of interest is 20 minutes
      Each time tick represents 1 minute
      tick time = 100 milliseconds

      At each time tick:
    	- Each server that has completed service of a customer “releases” the 
    	customer.
    	- Every customer currently in line has their wait time incremented.
    	- A number of new customers enter the system.
    	- Each idle server removes one customer from the queue.

      Run the experiment under the following environmental conditions:
        - with 1, 2, 4, 8 servers.
          Serv. Req.      Cust. Arr.
        - 1-12 minutes    2/minutes    heavy demand
        - 1-3 minutes    0.25/minute    light demand
    */


    // Inner Classes
    class Customer {
        int waitTime, serviceTime;

        public Customer(int time) {
            waitTime = 0;
            serviceTime = time;
        }
    }
    class Server {
        private Customer c;

        public Server() {
            c = null;
        }

        public void takeCustomer(Customer c) {
            if (this.isIdle()) this.c = c;
        }
        public void work() {
            if (!this.isIdle()) {
                c.serviceTime--;
                if (c.serviceTime < 1) c = null;
            }
        }
        public boolean isIdle() {
            return c == null;
        }
    }
    class Node {
        public Customer value;
        public Node next;

        public Node(Customer obj) {
            value = obj;
            next = null;
        }
    }
    class Queue {
        
        public Node head, tail;
        public int length;

        public Queue() {
            head = tail = null;
            length = 0;
        }

        public void add(Customer obj) {
            Node n = new Node(obj);
            this.add(n);
        }
        public void add(Node n) {
            if (head == null) head = tail = n;
            else {
                tail.next = n;
                tail = n;
            }
            length++;
        }
        public Node pop() {
            if (length < 1) return null;
            //Body
            Node n = head;
            head = n.next;
            if (head == null) tail = null;
            else n.next = null;
            // Closing
            length--;
            return n;
        }
        public boolean isEmpty() {
            return length<1;
        }
    }


    // Class Variables
    private int[] serviceTime;
    private double arrivalRate;
    private Queue line;
    ArrayList<Integer> output;
    Server[] frontDesk;


    // Methods
    private void populate(int minTime, int maxTime, double rate, int servNum) {
        arrivalRate = rate;
        line = new Queue();
        output = new ArrayList<Integer>();

        serviceTime = new int[2];
        serviceTime[0] = minTime;
        serviceTime[1] = maxTime;

        frontDesk = new Server[servNum];
        for (int i=0; i<servNum; i++) {
            frontDesk[i] = new Server();
        }
    }

    private int getCustomerTime() {
        if (serviceTime == null) return -1;
        return getRandomNumberInRange(serviceTime[0],serviceTime[1]);
    }

    public void run() throws InterruptedException {
        long end = System.currentTimeMillis() + 2000; //20 ticks
        int tick = 0;
        Node n; //Customer line Node
        while(System.currentTimeMillis() < end) {
            // customers come in
            int x = getPoissonRandom(arrivalRate);
            for(int i=0; i<x; i++) {
                Customer c = new Customer( getCustomerTime() );
                line.add(c);
            }

            // idle servers take customers
            for (int i = 0; i < frontDesk.length && !line.isEmpty(); i++) {
                if (frontDesk[i].isIdle()) {
                    n = line.pop();
                    output.add(n.value.waitTime);
                    frontDesk[i].takeCustomer(n.value);
                }
            }

            // servers process customers
            for (Server desk:frontDesk) {
                if (!desk.isIdle()) desk.work();
            }

            // customers' waitTime increments
            for (n = line.head; n != null; n = n.next) {
                n.value.waitTime++;
            }

            // Tick Output
	        System.out.println("Tick#: "+tick+"\n"+this);
            tick++;
            Thread.sleep(100); //1 tick
        }
    }

    private int buisyServers() {
        int num = 0;
        for (Server r:frontDesk) {
            if (!r.isIdle()) num++;
        }
        return num;
    }
    private int totalWaitTime() {
        int sum = 0;
        for (int i:output) sum += i;
        for (int i:queueWaitTime()) sum += i;
        return sum;
    }
    private ArrayList<Integer> queueWaitTime() {
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (Node n=line.head;n!=null;n=n.next) list.add(n.value.waitTime);
        return list;
    }

    public String toString() {
        ArrayList<String> l = new ArrayList<String>();
        String s = "\t%s: %d\n";

        int i = buisyServers();
        l.add(String.format(s,"# Customers in service",i));
        l.add(String.format(s,"# Customers with completed service",
output.size()-i));
        l.add(String.format(s,"# Customers in queue",line.length));

        i = totalWaitTime();
        l.add(String.format(s,"Total wait time", i));

        ArrayList<Integer> list = queueWaitTime();
        list.addAll(output);
        double ave = Double.valueOf(i)/(list.size());
        l.add(String.format("\tWait time: %d, %f, %d\n",Collections.min(list),
ave,Collections.max(list)));

        return String.join("", l);
    }


    // Static Methods
    private static int getPoissonRandom(double mean) {
        Random r = new Random();
        double L = Math.exp(-mean);
        int k = 0;
        double p = 1.0;
        do {
            p = p * r.nextDouble();
            k++;
        } while (p > L);
        return k - 1;
    }

    /* START CODE BLOCK
      Got the code from:
      https://www.mkyong.com/java/java-generate-random-integers-in-a-range/
    */
    private static int getRandomNumberInRange(int min, int max) {
		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}
	// END CODE BLOCK


    /* 
      Provide output to confirm your code is correct in the case where service 
      requirement is 1–3 minutes, with 2 arrivals/minute, and 4 servers.
    */
	public static void main(String[] args) {
	    Project1 shop = new Project1();
	    try {
	        shop.populate(1,3,2.0,4);
    	    shop.run();
	    } catch(InterruptedException e) {
	        System.out.println("Something went wrong.");
	    }
	}
}
