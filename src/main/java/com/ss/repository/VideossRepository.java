package com.ss.repository;

import com.ss.entity.Videoss;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface VideossRepository extends ElasticsearchRepository<Videoss,String> {


}
