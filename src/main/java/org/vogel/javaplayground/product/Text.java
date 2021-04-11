package org.vogel.javaplayground.product;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.vogel.javaplayground.common.AbstractEntity;
import org.vogel.javaplayground.product.textusage.TextUsage;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "t_text")
@SequenceGenerator(name = "text_id_seq", sequenceName = "text_id_seq", allocationSize = 10)
public class Text extends AbstractEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "text_id_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "template", nullable = false)
    private String template;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false, foreignKey = @ForeignKey(name = "fk_text_product"))
    private Product product;

    @OneToOne
    @JoinColumn(name = "text_usage_id", nullable = false, foreignKey = @ForeignKey(name = "fk_text_text_usage"))
    private TextUsage textUsage;
}
