package org.vogel.javaplayground.product.textusage;

import org.springframework.stereotype.Component;
import org.vogel.javaplayground.common.AbstractDAO;

@Component
public class TextUsageDAO extends AbstractDAO<TextUsage> {

    @Override
    protected Class<TextUsage> getEntityClass() {
        return TextUsage.class;
    }
}
