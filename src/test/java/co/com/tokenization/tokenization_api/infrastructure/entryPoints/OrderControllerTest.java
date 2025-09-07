package co.com.tokenization.tokenization_api.infrastructure.entryPoints;

import co.com.tokenization.tokenization_api.application.usecase.PaymentUseCase;
import co.com.tokenization.tokenization_api.domain.model.Client;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @Mock
    RegisterOrderUseCase registerOrder;

    @Mock
    PaymentUseCase paymentUseCase;

    @InjectMocks
    OrderController controller;

    @Test
    void shouldCreateOrder() {
        Client client = new Client(1L, "Bob", "bob@test.com", "111", "Street 1");
        OrderController.OrderRequest request = new OrderController.OrderRequest(client, "token123", "Address");

        Order order = new Order();
        order.setId(1L);

        when(registerOrder.register(1L, client, "token123", "Address")).thenReturn(order);

        Order result = controller.createOrder(String.valueOf(1L), request);

        assertEquals(1L, result.getId());
    }

    @Test
    void shouldPayOrder() {
        Order order = new Order();
        order.setId(1L);

        when(paymentUseCase.processPayment(order)).thenReturn(order);

        Order result = controller.payOrder(1L, order);

        assertEquals(1L, result.getId());
    }
}
