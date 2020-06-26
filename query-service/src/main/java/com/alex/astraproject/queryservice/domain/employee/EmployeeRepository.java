package com.alex.astraproject.queryservice.domain.employee;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends PagingAndSortingRepository<EmployeeEntity, String> {

	Optional<EmployeeEntity> findFirstByEmail(String email);

	@Query("MATCH e:Employee (:WORK_IN) c:Company (:WORK_IN) er:Employee { id: $1 } RETURN e")
	List<EmployeeEntity> findManyThatWorksWithOne(String id);

}
