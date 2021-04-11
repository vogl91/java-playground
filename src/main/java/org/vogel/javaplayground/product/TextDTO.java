package org.vogel.javaplayground.product;

import lombok.Getter;
import lombok.Setter;
import org.vogel.javaplayground.product.textusage.TextUsageDTO;

@Getter
@Setter
public class TextDTO {
    private Long id;
    private String template;
    private TextUsageDTO textUsage;
}
