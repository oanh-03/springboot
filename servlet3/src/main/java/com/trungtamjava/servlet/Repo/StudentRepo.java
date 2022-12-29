package com.trungtamjava.servlet.Repo;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.trungtamjava.servlet.entity.Student;

public interface StudentRepo extends JpaRepository<Student, Integer> {
	@Query("SELECT s FROM Student s JOIN s.user u where u.name LIKE :x")
    Page<Student> searchByName(@Param("x") String s, Pageable pageable);

    Page<Student> findByStudentCode(String studentCode,Pageable pageable);

    @Query("SELECT s FROM Student s  WHERE s.studentCode = :x")
    Page<Student> searchByCode(@Param("x") String s, Pageable pageable);

    @Query("SELECT s FROM Student s JOIN s.user u " +
            "WHERE s.studentCode LIKE :code AND u.name LIKE :name")
    Page<Student> searchByNameAndCode(@Param("code") String code,
                                      @Param("name") String name, Pageable pageable);

}
