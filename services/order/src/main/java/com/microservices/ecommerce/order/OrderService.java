package com.microservices.ecommerce.order;

import com.microservices.ecommerce.customer.CustomerClient;
import com.microservices.ecommerce.exception.BusinessException;
import com.microservices.ecommerce.kafka.OrderConfirmation;
import com.microservices.ecommerce.kafka.OrderProducer;
import com.microservices.ecommerce.payment.PaymentClient;
import com.microservices.ecommerce.payment.PaymentRequest;
import com.microservices.ecommerce.product.ProductClient;
import com.microservices.ecommerce.product.PurchaseRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderRepository repository;
    private final OrderMapper mapper;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;
    private final PaymentClient paymentClient;

    public Long createOrder(OrderRequest request) {
        // Check customer --> OpenFeign
        var customer = this.customerClient.findCustomerById(request.customerId())
                .orElseThrow(() -> new BusinessException("Cannot create order:: No customer exists with ID: " + request.customerId()));


        // purchase the products --> product-ms(RestTemplate)
        var  purchasedProducts = this.productClient.purchaseProducts(request.products());


        // persist order
        var order = this.repository.save(mapper.toOrder(request));

        // persist order lines
        for (PurchaseRequest purchaseRequest: request.products()){
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    )
            );

        }

        // start payment process
        paymentClient.requestOrderPayment(
                new PaymentRequest(
                        request.amount(),
                        request.paymentMethod(),
                        order.getId(),
                        order.getReference(),
                        customer
                )
        );

        // send the order confirmation --> notification-ms (kafka)
        orderProducer.sendOrderConfirmation(new OrderConfirmation(
                request.reference(),
                request.amount(),
                request.paymentMethod(),
                customer,
                purchasedProducts));
        return order.getId();
    }

    public List<OrderResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::fromOrder)
                .collect(Collectors.toList());

    }

    public OrderResponse findById(Long orderId) {
        return repository.findById(orderId).map(mapper::fromOrder)
                .orElseThrow(() -> new EntityNotFoundException("No order found with provided id: " + orderId));
    }
}
