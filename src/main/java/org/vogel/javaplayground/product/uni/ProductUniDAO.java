package org.vogel.javaplayground.product.uni;

import org.springframework.stereotype.Component;
import org.vogel.javaplayground.common.AbstractDAO;

@Component
public class ProductUniDAO extends AbstractDAO<ProductUni> {
    @Override
    protected Class<ProductUni> getEntityClass() {
        return ProductUni.class;
    }
}
