package com.richard.store.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {

    // find all the categories by name starting with ('ac...'
    // select * from category where name like 'a%' order by name asc
    List<Category> findAllByNameStartingWithIgnoreCaseOOrderByNameAsc(String name);

    // JPQL syntax
    @Query("""
            SELECT c FROM Category c
            WHERE c.name LIKE lower(:catName)
            ORDER BY c.name ASC
            """)
    List<Category> findAllByName(@Param("catName") String categoryName);



    @NativeQuery(value = "select * from TBL_CAT_SDSD_SDSD_SDSD where TBL_CAT_COL_NAME_NAME like lower(:catName) order by name asc")
    List<Category> findAllByNameUsingNativeQuery(@Param("catName") String categoryName);


    @Query(name = "Category.namedQueryFindByName")
    List<Category> namedQueryFindByName(@Param("catName") String categoryName);
}
