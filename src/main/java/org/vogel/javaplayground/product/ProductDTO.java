package org.vogel.javaplayground.product;

import lombok.Getter;
import lombok.Setter;
import org.vogel.javaplayground.product.TextDTO;

import java.util.List;

@Getter
@Setter
public class ProductDTO {
    private Long id;
    private String name;
    private List<TextDTO> texts;
}
