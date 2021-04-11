package org.vogel.javaplayground.product.uni;

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
@RequestMapping(path = "/product/uni")
@Transactional
public class ProductUniController {
    @Autowired
    private ProductUniDAO productUniDAO;

    @Autowired
    private ProductUniMapper productUniMapper;

    @GetMapping("")
    public List<ProductDTO> getAll() {
        return productUniDAO.findAll().stream().map(productUniMapper::toDTO).collect(toList());
    }

    @GetMapping("/{id}")
    public ProductDTO getById(@PathVariable("id") final Long id) {
        ProductUni product = productUniDAO.findExistingById(id, ProductUni.ENTITY_GRAPH_FETCH_ALL);
        return productUniMapper.toDTO(product);
    }

    @PostMapping("")
    public ProductDTO upsert(@RequestBody ProductDTO productDTO) {
        ProductUni product = productDTO.getId() == null
                ? new ProductUni() // no persist because this causes hibernate to issue an insert with null values
                : productUniDAO.findExistingById(productDTO.getId(), ProductUni.ENTITY_GRAPH_FETCH_ALL);
        product = productUniMapper.toEntity(productDTO, product);
        product = productUniDAO.merge(product);
        productUniDAO.flush();
        return productUniMapper.toDTO(product);
    }
}
