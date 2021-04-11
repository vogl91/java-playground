package org.vogel.javaplayground.product.textusage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TextUsageListDTO {
    private List<TextUsageDTO> textUsages;
}
