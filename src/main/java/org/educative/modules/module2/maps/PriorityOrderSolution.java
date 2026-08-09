package org.educative.modules.module2.maps;
import java.util.TreeMap;
import java.util.Map;


public class PriorityOrderSolution {

        private TreeMap<Integer, String> map;

        public PriorityOrderSolution() {
            map = new TreeMap<>();
        }

        // TODO: This method maps the customer to its priority (priority stored as key)
        public void addOrder(String customer, int priority) {
            map.put(priority, customer) ;
        }

        // TODO: Process the order with the highest priority (the order is removed after processing)
        public String processOrder() {
            Map.Entry<Integer, String> entry = map.pollFirstEntry();
            if (entry == null) return "No orders";
            return entry.getKey() + ", " + entry.getValue();
        }

        public static void main(String[] args) {
            // Initializing the order manager
            PriorityOrderSolution manager = new PriorityOrderSolution();

            // Adding a couple of orders with a unique priority for each
            manager.addOrder("John", 2);  // John has a lower priority
            manager.addOrder("Alice", 1);  // Alice has a higher priority

            // Retrieving and printing the next order to process - the one with the highest priority
            System.out.println(manager.processOrder());  // Expected output: "1, Alice"
        }
    }
