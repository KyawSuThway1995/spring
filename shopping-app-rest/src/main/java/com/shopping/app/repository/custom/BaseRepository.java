package com.shopping.app.repository.custom;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<T, ID> extends JpaRepository<T, ID> {
	List<T> find(String jpql, Map<String, Object> params);
	//long findCount(String jpql, Map<String, Object> params);
	<DTO> List<DTO> find(String jpql, Map<String, Object> params, Class<DTO> type);
}
