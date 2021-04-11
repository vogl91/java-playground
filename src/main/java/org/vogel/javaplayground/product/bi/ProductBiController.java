package org.vogel.javaplayground.product.bi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.vogel.javaplayground.product.ProductDTO;

import javax.transaction.Transactional;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping(path = "/product/bi")
@Transactional
public class ProductBiController {
    @Autowired
    private ProductBiDAO productBiDAO;

    @Autowired
    private ProductBiMapper productBiMapper;

    @GetMapping("")
    public List<ProductDTO> getAll() {
        return productBiDAO.findAll().stream().map(productBiMapper::toDTO).collect(toList());
    }

    @GetMapping("/{id}")
    public ProductDTO getById(@PathVariable("id") final Long id) {
        ProductBi product = productBiDAO.findExistingById(id, ProductBi.ENTITY_GRAPH_FETCH_ALL);
        return productBiMapper.toDTO(product);
    }

    @PostMapping("")
    public ProductDTO upsert(@RequestBody ProductDTO productDTO) {
        ProductBi product = productDTO.getId() == null
                ? new ProductBi() // no persist because this causes hibernate to issue an insert with null values
                : productBiDAO.findExistingById(productDTO.getId(), ProductBi.ENTITY_GRAPH_FETCH_ALL);
        product = productBiMapper.toEntity(productDTO, product);
        product = productBiDAO.merge(product);
        productBiDAO.flush();
        return productBiMapper.toDTO(product);
    }
}
