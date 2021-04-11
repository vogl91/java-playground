package org.vogel.javaplayground.product.uni;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.vogel.javaplayground.common.AbstractEntity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedSubgraph;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "t_product_uni", uniqueConstraints = {
        @UniqueConstraint(name = "uc_product_uni_name", columnNames = {"name"})
})
@NamedEntityGraph(name = ProductUni.ENTITY_GRAPH_FETCH_ALL,
        attributeNodes = {
                @NamedAttributeNode(value = "texts", subgraph = "text"),
        },
        subclassSubgraphs = {
                @NamedSubgraph(name = "text", attributeNodes = {@NamedAttributeNode("textUsage")})
        })
@SequenceGenerator(name = "product_uni_id_seq", sequenceName = "product_uni_id_seq", allocationSize = 10)
public class ProductUni extends AbstractEntity<Long> {
    public static final String ENTITY_GRAPH_FETCH_ALL = "product_uni.fetch-all";
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_uni_id_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "product_id", nullable = false, foreignKey = @ForeignKey(name = "fk_text_uni_product_uni"))
    private Set<TextUni> texts = new HashSet<>();
}
