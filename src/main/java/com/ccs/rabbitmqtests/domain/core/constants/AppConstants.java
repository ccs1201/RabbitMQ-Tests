package com.ccs.rabbitmqtests.domain.core.constants;

public class AppConstants {

    private AppConstants() {
    }

    public static class RabbitMQConstants {
        public static final String EXCHANGE_NAME = "payment-service-exchange";
        public static final String QUEUE_TEST = "test_queue";
        public static final String ROUTING_KEY_TEST = "test_routing_key";
        public static final String QUEUE_FOOD = "food-queue";
        public static final String ROUTING_KEY_FOOD = "food-routing-key";
        public static final String QUEUE_MEAL = "meal-queue";
        public static final String ROUTING_KEY_MEAL = "meal-routing-key";
        public static final String QUEUE_CASH = "cash-queue";
        public static final String ROUTING_KEY_CASH = "cash-routing-key";
        public static final String PAYMENT_SERVICE_QUEUE = "payment-service-queue";

        private RabbitMQConstants() {
        }
    }

}

