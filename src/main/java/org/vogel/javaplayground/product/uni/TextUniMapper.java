package org.vogel.javaplayground.product.uni;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vogel.javaplayground.common.exception.InvalidIdException;
import org.vogel.javaplayground.product.TextDTO;
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
public class TextUniMapper {
    @Autowired
    private TextUsageMapper textUsageMapper;

    @Autowired
    private TextUsageDAO textUsageDAO;

    public TextDTO toDTO(final TextUni text) {
        TextDTO textDTO = new TextDTO();
        textDTO.setId(text.getId());
        textDTO.setTemplate(text.getTemplate());
        textDTO.setTextUsage(textUsageMapper.toDTO(text.getTextUsage()));
        return textDTO;
    }

    public TextUni toEntity(final TextDTO textDTO, final TextUni text) {
        // do not map primary key (id)
        text.setTemplate(textDTO.getTemplate()); // primitive property. just map value
        text.setTextUsage(textUsageDAO.getReference(textDTO.getTextUsage().getId())); // OneToOne reference. get from
        // context
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
    public Set<TextUni> toEntities(List<TextDTO> textDTOs, Set<TextUni> texts, final ProductUni product) {
        final Map<Long, TextUni> textsById = texts.stream().collect(toMap(TextUni::getId, identity()));
        Map<Boolean, List<TextDTO>> textDTOsByNewAndExisting =
                textDTOs.stream().collect(groupingBy(text -> text.getId() == null));
        List<TextDTO> newTextDTOs = textDTOsByNewAndExisting.getOrDefault(true, emptyList());
        List<TextDTO> existingTextDTOs = textDTOsByNewAndExisting.getOrDefault(false, emptyList());

        texts.clear();
        existingTextDTOs.stream()
                        .map(textDTO -> toEntity(
                                textDTO,
                                Optional.ofNullable(textsById.get(textDTO.getId()))
                                        .orElseThrow(() -> new InvalidIdException(TextUni.class, textDTO.getId()))
                                                ))
                        .forEachOrdered(texts::add);
        newTextDTOs.stream()
                   .map(textDTO -> toEntity(textDTO, new TextUni()))
                   .forEachOrdered(texts::add);
        return texts;
    }
}
