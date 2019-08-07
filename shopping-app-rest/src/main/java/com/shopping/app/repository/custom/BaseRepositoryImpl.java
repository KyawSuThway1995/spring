package com.shopping.app.repository.custom;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

public class BaseRepositoryImpl<T, ID> extends SimpleJpaRepository<T, ID> implements BaseRepository<T, ID> {

	private JpaEntityInformation<T, ID> info;
	private EntityManager em;
	
	
	public BaseRepositoryImpl(JpaEntityInformation<T, ID> entityInformation, EntityManager entityManager) {
		super(entityInformation, entityManager);
		
		this.info = entityInformation;
		this.em = entityManager;
	}

	@Override
	public List<T> find(String jpql, Map<String, Object> params) {
		
		TypedQuery<T> query = em.createQuery(jpql, info.getJavaType());
		this.setParams(query, params);
		return query.getResultList();
	}

	/*
	 * @Override public long findCount(String jpql, Map<String, Object> params) {
	 * TypedQuery<Long> query = em.createQuery(jpql, Long.class);
	 * this.setParams(query, params); return query.getSingleResult(); }
	 */
	@Override
	public <DTO> List<DTO> find(String jpql, Map<String, Object> params, Class<DTO> type) {
		TypedQuery<DTO> query = em.createQuery(jpql, type);
		this.setParams(query, params);
		return query.getResultList();
	}
	
	private <E> void setParams(TypedQuery<E> query, Map<String, Object> params) {
		
		if(null != params) {
			for(String key : params.keySet()) {
				query.setParameter(key, params.get(key));
			}
		}
	}

}
