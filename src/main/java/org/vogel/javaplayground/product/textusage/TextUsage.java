package org.vogel.javaplayground.product.textusage;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "t_text_usage", uniqueConstraints = {
        @UniqueConstraint(name = "uc_text_usage_name", columnNames = {"name"})
})
@SequenceGenerator(name = "text_usage_id_seq", sequenceName = "text_usage_id_seq")
public class TextUsage {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "text_usage_id_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;
}
