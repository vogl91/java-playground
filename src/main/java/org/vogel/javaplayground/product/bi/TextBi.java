package org.vogel.javaplayground.product.bi;

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
@Table(name = "t_text_bi")
@SequenceGenerator(name = "text_bi_id_seq", sequenceName = "text_bi_id_seq", allocationSize = 10)
public class TextBi extends AbstractEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "text_bi_id_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "template", nullable = false)
    private String template;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false, foreignKey = @ForeignKey(name = "fk_text_bi_product_bi"))
    private ProductBi product;

    @OneToOne
    @JoinColumn(name = "text_usage_id", nullable = false, foreignKey = @ForeignKey(name = "fk_text_bi_text_usage"))
    private TextUsage textUsage;
}
