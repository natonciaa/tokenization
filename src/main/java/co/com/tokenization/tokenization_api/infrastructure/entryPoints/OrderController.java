package co.com.tokenization.tokenization_api.infrastructure.entryPoints;

import co.com.tokenization.tokenization_api.application.usecase.PaymentUseCase;
import co.com.tokenization.tokenization_api.application.usecase.RegisterOrderUseCase;
import co.com.tokenization.tokenization_api.domain.model.Client;
import co.com.tokenization.tokenization_api.domain.model.Order;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final RegisterOrderUseCase registerOrder;
    private final PaymentUseCase paymentUseCase;

    public OrderController(RegisterOrderUseCase registerOrder, PaymentUseCase paymentUseCase) {
        this.registerOrder = registerOrder;
        this.paymentUseCase = paymentUseCase;
    }

    @PostMapping("/{cartId}")
    public Order createOrder(@PathVariable Long cartId,
                             @RequestBody OrderRequest request) {
        return registerOrder.register(cartId, request.client, request.cardToken, request.deliveryAddress);
    }

    @PostMapping("/{orderId}/pay")
    public Order payOrder(@PathVariable Long orderId, @RequestBody OrderRequest request) {
        return paymentUseCase.processPayment(orderId, request.client, request.cardToken, request.deliveryAddress);
    }

    record OrderRequest(Client client, String cardToken, String deliveryAddress) {}
}

