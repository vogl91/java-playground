package org.vogel.javaplayground.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping(path = "/product")
@Transactional
public class ProductController {
    @Autowired
    private ProductDAO productDAO;

    @Autowired
    private ProductMapper productMapper;

    @GetMapping("")
    public List<ProductDTO> getAll() {
        return productDAO.findAll().stream().map(productMapper::toDTO).collect(toList());
    }

    @GetMapping("/{id}")
    public ProductDTO getById(@PathVariable("id") final Long id) {
        Product product = productDAO.findExistingById(id, Product.ENTITY_GRAPH_FETCH_ALL);
        return productMapper.toDTO(product);
    }

    @PostMapping("")
    public ProductDTO upsert(@RequestBody ProductDTO productDTO) {
        Product product = productDTO.getId() == null
                ? new Product() // no persist because this causes hibernate to issue an insert with null values
                : productDAO.findExistingById(productDTO.getId(), Product.ENTITY_GRAPH_FETCH_ALL);
        product = productMapper.toEntity(productDTO, product);
        product = productDAO.merge(product);
        productDAO.flush();
        return productMapper.toDTO(product);
    }
}
