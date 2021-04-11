package org.vogel.javaplayground.product.bi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vogel.javaplayground.product.ProductDTO;

import static java.util.stream.Collectors.toList;

@Component
public class ProductBiMapper {
    @Autowired
    private TextBiMapper textBiMapper;

    public ProductDTO toDTO(final ProductBi product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setTexts(product.getTexts().stream().map(textBiMapper::toDTO).collect(toList()));
        return productDTO;
    }

    public ProductBi toEntity(final ProductDTO productDTO, final ProductBi product) {
        product.setName(productDTO.getName());
        product.setTexts(textBiMapper.toEntities(productDTO.getTexts(), product.getTexts(), product));
        return product;
    }
}
