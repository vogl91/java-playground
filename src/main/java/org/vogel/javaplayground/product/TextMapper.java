package org.vogel.javaplayground.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vogel.javaplayground.common.exception.InvalidIdException;
import org.vogel.javaplayground.product.textusage.TextUsageDAO;
import org.vogel.javaplayground.product.textusage.TextUsageMapper;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static java.util.Collections.emptyList;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

@Component
public class TextMapper {
    @Autowired
    private TextUsageMapper textUsageMapper;

    @Autowired
    private TextUsageDAO textUsageDAO;

    public TextDTO toDTO(final Text text) {
        TextDTO textDTO = new TextDTO();
        textDTO.setId(text.getId());
        textDTO.setTemplate(text.getTemplate());
        textDTO.setTextUsage(textUsageMapper.toDTO(text.getTextUsage()));
        return textDTO;
    }

    public Text toEntity(final TextDTO textDTO, final Text text, final Product product) {
        // do not map primary key (id)
        text.setTemplate(textDTO.getTemplate()); // primitive property. just map value
        text.setTextUsage(textUsageDAO.getReference(textDTO.getTextUsage().getId())); // OneToOne reference. get from
        // context
        text.setProduct(product); // parent reference. get from caller
        return text;
    }

    /**
     * merge List of TextDTOs into a set of Texts.
     * textDTOs without id are treated as new.
     * textDTOs with id are treated as already existing and are matched against texts.
     * texts which are not matched are removed from the set.
     *
     * @param textDTOs new version of Texts
     * @param texts    current persisted version of Texts. All Texts must have an Id
     * @param product  parent. Only needed for bidirectional associations
     * @return new Set with texts created and updated from textDTOs
     */
    public Set<Text> toEntities(List<TextDTO> textDTOs, Set<Text> texts, final Product product) {
        final Map<Long, Text> textsById = texts.stream().collect(toMap(Text::getId, identity()));
        Map<Boolean, List<TextDTO>> textDTOsByNewAndExisting =
                textDTOs.stream().collect(groupingBy(text -> text.getId() == null));
        List<TextDTO> newTextDTOs = textDTOsByNewAndExisting.getOrDefault(true, emptyList());
        List<TextDTO> existingTextDTOs = textDTOsByNewAndExisting.getOrDefault(false, emptyList());

        texts.clear();
        existingTextDTOs.stream()
                        .map(textDTO -> toEntity(
                                textDTO,
                                Optional.ofNullable(textsById.get(textDTO.getId()))
                                        .orElseThrow(() -> new InvalidIdException(Text.class, textDTO.getId())),
                                product
                                                ))
                        .forEachOrdered(texts::add);
        newTextDTOs.stream()
                   .map(textDTO -> toEntity(textDTO, new Text(), product))
                   .forEachOrdered(texts::add);
        return texts;
    }
}
