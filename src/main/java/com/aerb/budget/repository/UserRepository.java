package com.aerb.budget.repository;

import com.aerb.budget.entity.User;
import com.aerb.budget.entity.MyEnums.Position;
import com.aerb.budget.entity.MyEnums.Status;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {

	Optional<User> findByEmpId(String empId);
	 Optional<User> findByEmail(String email);
	
    Optional<User> findById(String empId);
    List<User> findByRoleIn(List<String> asList);
    Optional<User> findByUId(String identifier);
    List<User> findByDivisionAndRoleIn(String division, List<String> of);

    //List<User> findByDivisionAndSectionAndRole(String division, String section, MyEnums.Role role);
    //List<User> findByDivisionAndRole(String division, MyEnums.Role role);
	List<User> findByDivisionAndRoleAndStatus(String division, Position role, Status status);
	List<User> findByDivisionAndSectionAndRoleAndStatus(String division, String section, Position role, Status status);
	User findByEmpIdAndStatus(String empId, Status active);
	User findFirstByDivisionAndSectionAndRoleAndStatus(String division, String section, Position sectionHead,
			Status active);
	User findFirstByDivisionAndRoleAndStatus(String division, Position role, Status status);
	User findFirstByRoleAndStatus(Position cao, Status active);
	
	List<User> findByEmpIdContainingIgnoreCaseOrFullNameContainingIgnoreCaseAndStatus(String empIdPart, String namePart, Status status);
	
	@Query("""
	        SELECT u FROM User u WHERE
	        LOWER(u.fullName) LIKE LOWER(CONCAT('%', :query, '%')) OR
	        LOWER(u.empId) LIKE LOWER(CONCAT('%', :query, '%')) OR
	        LOWER(u.email) LIKE LOWER(CONCAT('%', :query, '%')) OR
	        LOWER(u.uId) LIKE LOWER(CONCAT('%', :query, '%')) OR
	        LOWER(u.designation) LIKE LOWER(CONCAT('%', :query, '%'))
	        """)
	    List<User> searchUsers(@Param("query") String query, Pageable pageable);
	List<User> findAll(Specification<User> spec);
	void deleteById(String empId);
}
