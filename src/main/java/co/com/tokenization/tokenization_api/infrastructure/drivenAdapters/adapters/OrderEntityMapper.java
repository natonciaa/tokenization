package co.com.tokenization.tokenization_api.infrastructure.drivenAdapters.adapters;

import co.com.tokenization.tokenization_api.domain.model.*;
import co.com.tokenization.tokenization_api.infrastructure.drivenAdapters.jpa.entity.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Collections;
import java.util.List;

public class OrderEntityMapper {

    private final ObjectMapper mapper = new ObjectMapper();

    public OrderEntity toEntity(Order order) {
        if (order == null) return null;
        OrderEntity entity = new OrderEntity();
        entity.setId(order.getId());
        entity.setClient(mapper.convertValue(order.getClient(), ClientEntity.class));

        Cart cart = order.getCart();
        if (cart == null) return null;
        CartEntity cartEntity = new CartEntity();
        cartEntity.setId(cart.getId());
//        cartEntity.setTotalCost(cart.getTotalCost());
        cartEntity.setStatus(cart.getStatus() != null ? CartEntity.Status.valueOf(cart.getStatus().name()) : null);

        List<CartItemEntity> itemEntities = cart.getItems() != null ? cart.getItems().stream()
                .map(item -> {
                    CartItemEntity itemEntity = new CartItemEntity();
                    itemEntity.setId(item.getId());
                    itemEntity.setQuantity(item.getQuantity());
                    itemEntity.setProduct(mapper.convertValue(item.getProduct(), ProductEntity.class));
                    itemEntity.setCart(cartEntity);
                    cartEntity.setTotalCost(cartEntity.getTotalCost() + itemEntity.getProduct().getPrice());
                    return itemEntity;
                })
                .toList() : Collections.emptyList();
        cartEntity.setItems(itemEntities);

        entity.setCart(cartEntity);
        entity.setCreditCardToken(order.getCreditCardToken());
        entity.setDeliveryAddress(order.getDeliveryAddress());
        entity.setPaid(order.isPaid());
        return entity;
    }

    public Order toDomain(OrderEntity entity) {
        if (entity == null) return null;
        Order order = new Order();
        order.setId(entity.getId());
        order.setClient(mapper.convertValue(entity.getClient(), Client.class));

        CartEntity cartEntity = entity.getCart();
        Cart cart = new Cart();
        cart.setId(cartEntity.getId());
        cart.setTotalCost(cartEntity.getTotalCost());
        cart.setStatus(Cart.Status.valueOf(cartEntity.getStatus().name()));

        List<CartItem> items = cartEntity.getItems().stream()
                .map(itemEntity -> {
                    CartItem cartItem = new CartItem();
                    cartItem.setId(itemEntity.getId());
                    cartItem.setQuantity(itemEntity.getQuantity());
                    cartItem.setProduct(mapper.convertValue(itemEntity.getProduct(), Product.class));
                    cartItem.setCartId(cart.getId());
                    return cartItem;
                }).toList();
        cart.setItems(items);

        order.setCart(cart);
        order.setCreditCardToken(entity.getCreditCardToken());
        order.setDeliveryAddress(entity.getDeliveryAddress());
        order.setPaid(entity.isPaid());
        return order;
    }

}
