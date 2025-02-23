package org.shop.productSetupService.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ProductMessageListener {

    private static final Logger logger = LoggerFactory.getLogger(ProductMessageListener.class);

    // Listener for the PRODUCT_ADD queue
    @RabbitListener(queues = "MESSAGE_QUEUE_PRODUCT_ADD")
    public void handleProductAdd(String productId) {
        try {
            Long id = Long.parseLong(productId);
            // Log the successful product add event
            logger.info("Received product add message with ID: {}", id);
            // Process the product add event here
        } catch (NumberFormatException e) {
            // Log the error in case of invalid productId
            logger.error("Invalid product ID received for add: {}", productId, e);
        }
    }

    // Listener for the PRODUCT_UPDATE queue
    @RabbitListener(queues = "MESSAGE_QUEUE_PRODUCT_UPDATE")
    public void handleProductUpdate(String productId) {
        try {
            Long id = Long.parseLong(productId);
            // Log the successful product update event
            logger.info("Received product update message with ID: {}", id);
            // Process the product update event here
        } catch (NumberFormatException e) {
            // Log the error in case of invalid productId
            logger.error("Invalid product ID received for update: {}", productId, e);
        }
    }

    // Listener for the PRODUCT_DELETE queue
    @RabbitListener(queues = "MESSAGE_QUEUE_PRODUCT_DELETE")
    public void handleProductDelete(String productId) {
        try {
            Long id = Long.parseLong(productId);
            // Log the successful product delete event
            logger.info("Received product delete message with ID: {}", id);
            // Process the product delete event here
        } catch (NumberFormatException e) {
            // Log the error in case of invalid productId
            logger.error("Invalid product ID received for delete: {}", productId, e);
        }
    }
}
