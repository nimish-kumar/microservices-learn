package com.microservices.ecommerce.payment;

import com.microservices.ecommerce.notification.NotificationProducer;
import com.microservices.ecommerce.notification.PaymentNotificationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository repository;
    private final PaymentMapper mapper;
    private final NotificationProducer notificationProducer;

    public Long createPayment(PaymentRequest request){
        var payment = repository.save(mapper.toPayment(request));

        // Send notification for payment
        notificationProducer.sendNotification(
                new PaymentNotificationRequest(
                        request.orderReference(),
                        request.amount(),
                        request.paymentMethod(),
                        request.customer().firstName(),
                        request.customer().lastName(),
                        request.customer().email()
                )
        );


        return payment.getId();
    }
}
