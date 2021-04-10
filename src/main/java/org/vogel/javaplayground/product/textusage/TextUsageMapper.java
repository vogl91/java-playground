package org.vogel.javaplayground.product.textusage;

import org.springframework.stereotype.Component;

@Component
public class TextUsageMapper {
    public TextUsageDTO toDTO(final TextUsage textUsage) {
        final var textUsageDTO = new TextUsageDTO();
        textUsageDTO.setId(textUsage.getId());
        textUsageDTO.setName(textUsage.getName());
        return textUsageDTO;
    }

    public TextUsage toEntity(final TextUsageDTO textUsageDTO, final TextUsage textUsage) {
        textUsage.setId(textUsageDTO.getId());
        textUsage.setName(textUsageDTO.getName());
        return textUsage;
    }
}
