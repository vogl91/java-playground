package org.vogel.javaplayground.common;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Transactional(Transactional.TxType.MANDATORY)
public abstract class AbstractDAO<T> {

    @PersistenceContext
    protected EntityManager em;

    abstract protected Class<T> getEntityClass();

    public Optional<T> findById(final Long id) {
        return Optional.ofNullable(em.find(getEntityClass(), id));
    }

    public T findExistingById(final Long id) {
        return findById(id).orElseThrow(() -> new EntityNotFoundException(
                String.format("entity '%s' with id '%d' not found", getEntityClass().getSimpleName(), id)));
    }

    public T merge(final T entity) {
        return em.merge(entity);
    }

    public List<T> executeNamedQuery(final String queryName, Map<String, Object> parameters) {
        TypedQuery<T> query = em.createNamedQuery(queryName, getEntityClass());
        parameters.forEach(query::setParameter);

        return query.getResultList();
    }
}
