package com.cs.repository;

import com.cs.entity.LogInfoEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface LogInfoRepository extends CrudRepository<LogInfoEntity,String> {
}
