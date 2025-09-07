package co.com.tokenization.tokenization_api.infrastructure.drivenAdapters.adapters;


import co.com.tokenization.tokenization_api.domain.model.CartItem;
import co.com.tokenization.tokenization_api.domain.model.Product;

import co.com.tokenization.tokenization_api.domain.model.gateway.CartItemRepository;
import co.com.tokenization.tokenization_api.infrastructure.drivenAdapters.jpa.entity.CartEntity;
import co.com.tokenization.tokenization_api.infrastructure.drivenAdapters.jpa.entity.CartItemEntity;
import co.com.tokenization.tokenization_api.infrastructure.drivenAdapters.jpa.entity.ProductEntity;
import co.com.tokenization.tokenization_api.infrastructure.drivenAdapters.jpa.repository.CartItemJPARepository;
import co.com.tokenization.tokenization_api.infrastructure.drivenAdapters.jpa.repository.CartJPARepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CartItemRepositoryAdapter implements CartItemRepository {

    private final CartItemJPARepository jpaRepo;

    @Override
    public CartItem save(CartItem item) {
        CartItemEntity entity = new CartItemEntity();
        entity.setId(item.getId());

        CartEntity cartEntity = new CartEntity();
        cartEntity.setId(item.getCartId());
        entity.setCart(cartEntity);

        ProductEntity pe = new ProductEntity(
                item.getProduct().getId(),
                item.getProduct().getName(),
                item.getProduct().getPrice(),
                item.getProduct().getStock()
        );
        entity.setProduct(pe);
        entity.setQuantity(item.getQuantity());

        CartItemEntity saved = jpaRepo.save(entity);

        return new CartItem(
                saved.getId(),
                saved.getCart().getId(),
                new Product(
                        saved.getProduct().getId(),
                        saved.getProduct().getName(),
                        saved.getProduct().getPrice(),
                        saved.getProduct().getStock()
                ),
                saved.getQuantity()
        );
    }
}

