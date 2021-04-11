package org.vogel.javaplayground.product;

import org.springframework.stereotype.Component;
import org.vogel.javaplayground.common.AbstractDAO;

@Component
public class ProductDAO extends AbstractDAO<Product> {
    @Override
    protected Class<Product> getEntityClass() {
        return Product.class;
    }
}
