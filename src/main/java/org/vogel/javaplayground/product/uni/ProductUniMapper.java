package org.vogel.javaplayground.product.uni;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vogel.javaplayground.product.ProductDTO;

import static java.util.stream.Collectors.toList;

@Component
public class ProductUniMapper {
    @Autowired
    private TextUniMapper textUniMapper;

    public ProductDTO toDTO(final ProductUni product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setTexts(product.getTexts().stream().map(textUniMapper::toDTO).collect(toList()));
        return productDTO;
    }

    public ProductUni toEntity(final ProductDTO productDTO, final ProductUni product) {
        product.setName(productDTO.getName());
        product.setTexts(textUniMapper.toEntities(productDTO.getTexts(), product.getTexts(), product));
        return product;
    }
}
