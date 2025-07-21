// CategoryRepository.java
package com.aerb.docs.repository;

import com.aerb.docs.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByParentIsNull(); // Only top-level categories
}
