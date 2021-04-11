package org.vogel.javaplayground.testusage;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.vogel.javaplayground.AbstractIntegrationTest;
import org.vogel.javaplayground.product.textusage.TextUsageController;
import org.vogel.javaplayground.product.textusage.TextUsageDTO;
import org.vogel.javaplayground.product.textusage.TextUsageListDTO;

import static org.assertj.core.api.Assertions.assertThat;

public class TextUsageControllerTest extends AbstractIntegrationTest {
    @Test
    public void insertTextUsage() {
        final TextUsageDTO textUsageDTO = new TextUsageDTO();
        textUsageDTO.setName("STD");

        ResponseEntity<TextUsageDTO> response = testRestTemplate.postForEntity(
                TextUsageController.BASE_PATH,
                textUsageDTO,
                TextUsageDTO.class
        );

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isNotNull();
        final TextUsageDTO responseTextUsageDTO = response.getBody();
        assertThat(responseTextUsageDTO.getId()).isNotNull();
        assertThat(responseTextUsageDTO.getName()).isEqualTo(textUsageDTO.getName());
    }

    @Test
    public void insertMultipleTextUsagesAndGetAll() {
        {
            final TextUsageDTO textUsageDTO = new TextUsageDTO();
            textUsageDTO.setName("STD");

            ResponseEntity<TextUsageDTO> response = testRestTemplate.postForEntity(
                    TextUsageController.BASE_PATH,
                    textUsageDTO,
                    TextUsageDTO.class
            );

            assertThat(response.getStatusCodeValue()).isEqualTo(200);
            assertThat(response.getBody()).isNotNull();
            final TextUsageDTO responseTextUsageDTO = response.getBody();
            assertThat(responseTextUsageDTO.getId()).isNotNull();
            assertThat(responseTextUsageDTO.getName()).isEqualTo(textUsageDTO.getName());
        }
        {
            final TextUsageDTO textUsageDTO = new TextUsageDTO();
            textUsageDTO.setName("HSB");

            ResponseEntity<TextUsageDTO> response = testRestTemplate.postForEntity(
                    TextUsageController.BASE_PATH,
                    textUsageDTO,
                    TextUsageDTO.class
            );

            assertThat(response.getStatusCodeValue()).isEqualTo(200);
            assertThat(response.getBody()).isNotNull();
            final TextUsageDTO responseTextUsageDTO = response.getBody();
            assertThat(responseTextUsageDTO.getId()).isNotNull();
            assertThat(responseTextUsageDTO.getName()).isEqualTo(textUsageDTO.getName());
        }
        {
            ResponseEntity<TextUsageListDTO> response = testRestTemplate.getForEntity(
                    TextUsageController.BASE_PATH,
                    TextUsageListDTO.class
            );

            assertThat(response.getStatusCodeValue()).isEqualTo(200);
            assertThat(response.getBody()).isNotNull();
            assertThat(response.getBody().getTextUsages()).hasSize(2);
            assertThat(response.getBody().getTextUsages())
                    .map(TextUsageDTO::getName)
                    .containsExactlyInAnyOrder("STD", "HSB");
        }
    }
}
