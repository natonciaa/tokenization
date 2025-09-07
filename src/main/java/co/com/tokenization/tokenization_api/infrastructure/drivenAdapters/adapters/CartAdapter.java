package co.com.tokenization.tokenization_api.infrastructure.drivenAdapters.adapters;

import co.com.tokenization.tokenization_api.domain.model.Cart;
import co.com.tokenization.tokenization_api.domain.model.CartItem;
import co.com.tokenization.tokenization_api.domain.model.Product;
import co.com.tokenization.tokenization_api.domain.model.gateway.CartRepository;
import co.com.tokenization.tokenization_api.domain.model.gateway.ProductRepository;
import co.com.tokenization.tokenization_api.infrastructure.drivenAdapters.jpa.entity.CartEntity;
import co.com.tokenization.tokenization_api.infrastructure.drivenAdapters.jpa.entity.CartItemEntity;
import co.com.tokenization.tokenization_api.infrastructure.drivenAdapters.jpa.entity.ProductEntity;
import co.com.tokenization.tokenization_api.infrastructure.drivenAdapters.jpa.repository.CartJPARepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class CartAdapter implements CartRepository {

    private final CartJPARepository jpaRepo;
    private final ProductRepository productRepo;

    @Override
    public Cart save(Cart cart) {
        CartEntity entity = new CartEntity();
        entity.setId(cart.getId());
        entity.setStatus(CartEntity.Status.valueOf(cart.getStatus().name()));
        int totalCost = cart.getItems().stream()
                .mapToInt(item -> (int) (item.getProduct().getPrice() * item.getQuantity()))
                .sum();
        entity.setTotalCost(totalCost);

        entity.setItems(cart.getItems().stream().map(item -> {
            CartItemEntity cie = new CartItemEntity();
            cie.setId(item.getId());
            cie.setCart(entity);
            Product product = productRepo.findByNameContaining(item.getProduct().getName()).getFirst();
            if (product == null) {
                throw new RuntimeException("Product not found: " + item.getProduct().getName());
            }
            ProductEntity pe = new ProductEntity(
                    product.getId(),
                    product.getName(),
                    product.getPrice(),
                    product.getStock()
            );
            cie.setProduct(pe);
            cie.setQuantity(item.getQuantity());
            return cie;
        }).collect(Collectors.toList()));

        CartEntity saved = jpaRepo.save(entity);

        return new Cart(
                saved.getId(),
                saved.getItems().stream()
                        .map(ci -> new CartItem(
                                ci.getId(),
                                saved.getId(),
                                new Product(
                                        ci.getProduct().getId(),
                                        ci.getProduct().getName(),
                                        ci.getProduct().getPrice(),
                                        ci.getProduct().getStock()
                                ),
                                ci.getQuantity()
                        ))
                        .collect(Collectors.toList()),
                Cart.Status.valueOf(saved.getStatus().name()),
                saved.getTotalCost()
        );
    }

    @Override
    public Cart findById(Long id) {
        CartEntity saved = jpaRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Cart not found with id: " + id));
        return new Cart(
                saved.getId(),
                saved.getItems().stream()
                        .map(ci -> new CartItem(
                                ci.getId(),
                                saved.getId(),
                                new Product(
                                        ci.getProduct().getId(),
                                        ci.getProduct().getName(),
                                        ci.getProduct().getPrice(),
                                        ci.getProduct().getStock()
                                ),
                                ci.getQuantity()
                        ))
                        .collect(Collectors.toList()),
                Cart.Status.valueOf(saved.getStatus().name()),
                saved.getTotalCost()
        );
    }
}
