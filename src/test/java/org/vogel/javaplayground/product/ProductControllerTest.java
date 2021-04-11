package org.vogel.javaplayground.product;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.vogel.javaplayground.AbstractIntegrationTest;
import org.vogel.javaplayground.product.textusage.TextUsageController;
import org.vogel.javaplayground.product.textusage.TextUsageDTO;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductControllerTest extends AbstractIntegrationTest {

    @Test
    public void insertProductWithText() {
        TextUsageDTO textUsageDTO;
        {
            textUsageDTO = new TextUsageDTO();
            textUsageDTO.setName("STD");

            ResponseEntity<TextUsageDTO> response = testRestTemplate.postForEntity(
                    TextUsageController.BASE_PATH,
                    textUsageDTO,
                    TextUsageDTO.class
            );

            assertThat(response.getStatusCodeValue()).isEqualTo(200);
            assertThat(response.getBody()).isNotNull();
            textUsageDTO = response.getBody();
        }

        {
            final var productDTO = new ProductDTO();
            productDTO.setName("Sessel");

            final var textDTO = new TextDTO();
            textDTO.setTemplate("Ein sehr schöner Sessel\nMit vielen Features");
            textDTO.setTextUsage(textUsageDTO);
            productDTO.setTexts(singletonList(textDTO));

            ResponseEntity<ProductDTO> response = testRestTemplate.postForEntity(
                    ProductController.BASE_PATH,
                    productDTO,
                    ProductDTO.class
            );

            assertThat(response.getStatusCodeValue()).isEqualTo(200);
            ProductDTO responseProduct = response.getBody();
            assertThat(responseProduct).isNotNull();
            assertThat(responseProduct.getId()).isNotNull();
            assertThat(responseProduct.getName()).isEqualTo(productDTO.getName());
        }
    }
}
