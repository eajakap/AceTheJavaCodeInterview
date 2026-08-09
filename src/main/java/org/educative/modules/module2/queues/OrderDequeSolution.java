package org.educative.modules.module2.queues;

import java.util.ArrayDeque;
import java.util.Deque;

class OrderDequeSolution {
    private Deque<Integer> orderQueue;

    public OrderDequeSolution() {
        // TODO: Initialize the order queue using ArrayDeque
        orderQueue = new ArrayDeque<>();
    }

    public void addOrder(int orderId) {
        // TODO: Add an order to the queue
        orderQueue.addLast(orderId);
    }

    public int serveOrder() {
        // TODO: Serve (remove) the first order in the queue; raise an exception if there are no orders
        if (orderQueue.isEmpty()) {
            throw new IllegalStateException("No orders to serve");
        }
        return orderQueue.removeFirst();
    }

    public static void main(String[] args) {
        // TODO: Initialize an instance of Solution
        OrderDequeSolution solution = new OrderDequeSolution();

        // TODO: Add at least three orders by their ID
        solution.addOrder(101);
        solution.addOrder(102);
        solution.addOrder(103);

        // TODO: Serve an order and print the ID of the order served
        System.out.println("Served order ID: " + solution.serveOrder());
    }
}