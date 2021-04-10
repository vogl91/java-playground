package org.vogel.javaplayground.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.vogel.javaplayground.common.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "t_user", uniqueConstraints = {
        @UniqueConstraint(columnNames = "name", name = "uc_user_name")
})
@NamedQueries({
        @NamedQuery(name = "user.findByName", query = "select u from User u where u.name = :name")
})
@SequenceGenerator(name = "user_id_seq", sequenceName = "user_id_seq")
public class User extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "last_name", nullable = false)
    private String lastName;
}
