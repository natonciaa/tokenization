package co.com.tokenization.tokenization_api.application.usecase;

import co.com.tokenization.tokenization_api.domain.model.*;
import co.com.tokenization.tokenization_api.domain.model.gateway.CartRepository;
import co.com.tokenization.tokenization_api.domain.model.gateway.ClientRepository;
import co.com.tokenization.tokenization_api.domain.model.gateway.OrderRepository;
import co.com.tokenization.tokenization_api.domain.model.gateway.TokenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class PaymentUseCase {
    private final OrderRepository orderRepo;
    private final ClientRepository clientRepo;

    private final TokenRepository tokenRepo;
    private final CartRepository cartRepo;
    private final EmailService mailSender;
    private final Random random = new Random();

    @Value("${app.payment.success-probability:0.8}")
    private double successProbability;

    @Value("${app.payment.max-retries:3}")
    private int maxRetries;

    public PaymentUseCase(OrderRepository orderRepo, ClientRepository clientRepo,TokenRepository tokenRepo, CartRepository cartRepo, EmailService mailSender) {
        this.orderRepo = orderRepo;
        this.clientRepo = clientRepo;
        this.tokenRepo = tokenRepo;
        this.cartRepo = cartRepo;
        this.mailSender = mailSender;
    }

    public Order processPayment(Long idOrder, Client client, String creditCardToken, String deliveryAddress) {
        Order order = orderRepo.findById(idOrder);
        if(order == null )
           throw new IllegalArgumentException("Order not found");

        TokenRecord tokenRecord = tokenRepo.findByToken(creditCardToken);
        if (tokenRecord == null) {
            mailSender.sendSimpleMessage(order.getClient().getEmail(), "Payment failed", "Invalid token");
            throw new IllegalArgumentException("Invalid token");
        }

        Cart cart = order.getCart();
        if (cart == null) {
            throw new IllegalArgumentException("Cart not found in order");
        }
        Cart cartFound = cartRepo.findById(cart.getId());
        if(cartFound == null) {
            throw new IllegalArgumentException("Cart not found");
        }

        Client clientFound = clientRepo.findByEmail(client.getEmail());
        if(clientFound == null) {
            throw new IllegalArgumentException("Client not found");
        }
        int attempts = 0;
        boolean paid = false;

        while (attempts < maxRetries && !paid) {
            attempts++;
            double chance = random.nextDouble();
            if (chance <= successProbability) {
                order.setPaid(true);
                order.setClient(clientFound);
                order.setCart(cartFound);
                order.setDeliveryAddress(deliveryAddress);
                order.setCreditCardToken(creditCardToken);
                cart.setStatus(Cart.Status.PAID);
                cartRepo.save(cart);
                Order saved = orderRepo.save(order);
                mailSender.sendSimpleMessage(saved.getClient().getEmail(), "Payment success", "Your payment was successful!");
                return saved;
            }
        }

        cart.setStatus(Cart.Status.CANCELED);
        cartRepo.save(cart);
        order.setCart(cart);
        orderRepo.save(order);
        mailSender.sendSimpleMessage(order.getClient().getEmail(), "Payment failed", "Your payment failed after " + attempts + " attempts");
        return order;
    }
}
