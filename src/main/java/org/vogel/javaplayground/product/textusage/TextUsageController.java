package org.vogel.javaplayground.product.textusage;

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
@RequestMapping(path = "/textusage")
@Transactional
public class TextUsageController {

    @Autowired
    private TextUsageDAO textUsageDAO;

    @Autowired
    private TextUsageMapper textUsageMapper;

    @GetMapping("")
    public List<TextUsageDTO> getAll() {
        return textUsageDAO.findAll().stream().map(textUsageMapper::toDTO).collect(toList());
    }

    @GetMapping("/{id}")
    public TextUsageDTO getById(@PathVariable("id") final Long id) {
        final TextUsage textUsage = textUsageDAO.findExistingById(id);
        return textUsageMapper.toDTO(textUsage);
    }

    @PostMapping("")
    public TextUsageDTO upsert(@RequestBody TextUsageDTO textUsageDTO) {
        TextUsage textUsage = textUsageDTO.getId() == null
                ? new TextUsage()
                : textUsageDAO.findExistingById(textUsageDTO.getId());
        textUsage = textUsageMapper.toEntity(textUsageDTO, textUsage);
        textUsage = textUsageDAO.merge(textUsage);
        return textUsageMapper.toDTO(textUsage);
    }
}
