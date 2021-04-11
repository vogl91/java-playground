package org.vogel.javaplayground.common;

import org.hibernate.graph.GraphSemantic;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
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

    public Optional<T> findById(final Long id, final String graphName) {
        EntityGraph<?> entityGraph = em.getEntityGraph(graphName);
        return Optional.ofNullable(
                em.find(getEntityClass(), id, Map.of(GraphSemantic.LOAD.getJpaHintName(), entityGraph)));
    }

    public T findExistingById(final Long id) {
        return findById(id).orElseThrow(() -> new EntityNotFoundException(
                String.format("entity '%s' with id '%d' not found", getEntityClass().getSimpleName(), id)));
    }

    public T findExistingById(final Long id, final String graphName) {
        return findById(id, graphName).orElseThrow(() -> new EntityNotFoundException(
                String.format("entity '%s' with id '%d' not found", getEntityClass().getSimpleName(), id)));
    }

    public List<T> findAll() {
        var cb = em.getCriteriaBuilder();
        CriteriaQuery<T> query = cb.createQuery(getEntityClass());
        query.from(getEntityClass());
        return em.createQuery(query).getResultList();

    }

    public T getReference(Long id) {
        return em.getReference(getEntityClass(), id);
    }

    public T merge(final T entity) {
        return em.merge(entity);
    }

    public T persist(final T entity) {
        em.persist(entity);
        return entity;
    }

    public void flush() {
        em.flush();
    }

    public List<T> executeNamedQuery(final String queryName, Map<String, Object> parameters) {
        TypedQuery<T> query = em.createNamedQuery(queryName, getEntityClass());
        parameters.forEach(query::setParameter);

        return query.getResultList();
    }
}
