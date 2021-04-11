package org.vogel.javaplayground.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static java.util.stream.Collectors.toList;

@Component
public class ProductMapper {
    @Autowired
    private TextMapper textMapper;

    public ProductDTO toDTO(final Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setTexts(product.getTexts().stream().map(textMapper::toDTO).collect(toList()));
        return productDTO;
    }

    public Product toEntity(final ProductDTO productDTO, final Product product) {
        product.setName(productDTO.getName());
        product.setTexts(textMapper.toEntities(productDTO.getTexts(), product.getTexts(), product));
        return product;
    }
}
