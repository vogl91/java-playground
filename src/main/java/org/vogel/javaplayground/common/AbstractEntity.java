package org.vogel.javaplayground.common;

import org.springframework.data.util.ProxyUtils;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
public abstract class AbstractEntity<PK extends Serializable> {
    public abstract PK getId();

    @Override
    public boolean equals(Object obj) {
        if (null == obj) {
            return false;
        }

        if (this == obj) {
            return true;
        }

        if (!getClass().equals(ProxyUtils.getUserClass(obj))) {
            return false;
        }

        AbstractEntity<?> that = (AbstractEntity<?>) obj;

        return null != this.getId() && this.getId().equals(that.getId());
    }

    @Override
    public int hashCode() {

        int hashCode = 17;

        hashCode += null == getId() ? 0 : getId().hashCode() * 31;

        return hashCode;
    }
}
