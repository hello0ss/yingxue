package com.ss.repository;

import com.ss.entity.Emp;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface EmpRepository extends ElasticsearchRepository<Emp,String> {

    List<Emp> findByName (String name);
}
