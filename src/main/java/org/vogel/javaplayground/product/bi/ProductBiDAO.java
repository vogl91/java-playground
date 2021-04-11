package org.vogel.javaplayground.product.bi;

import org.springframework.stereotype.Component;
import org.vogel.javaplayground.common.AbstractDAO;

@Component
public class ProductBiDAO extends AbstractDAO<ProductBi> {
    @Override
    protected Class<ProductBi> getEntityClass() {
        return ProductBi.class;
    }
}
