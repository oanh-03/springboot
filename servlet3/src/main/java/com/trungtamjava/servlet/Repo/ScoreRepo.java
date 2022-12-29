package com.trungtamjava.servlet.Repo;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.trungtamjava.servlet.entity.Course;
import com.trungtamjava.servlet.entity.Score;
import com.trungtamjava.servlet.entity.Student;

public interface ScoreRepo extends JpaRepository<Score, Integer> {
	@Query("SELECT s FROM Score s WHERE s.id =:x")
	Page<Score> searchById(@Param("x")Integer id,Pageable pageable);
	
	@Query("SELECT s FROM Score s WHERE s.score =:x")
	Page<Score> searchByScore(@Param("x")Double score,Pageable pageable);
	
	@Query("SELECT s FROM Score s JOIN s.course c WHERE c.id =:x")
	Page<Score> searchByCourseId(@Param("x")Integer cid,Pageable pageable);
	
	@Query("SELECT s FROM Score s JOIN s.student st WHERE st.id =:x")
	Page<Score> searchByStudentId(@Param("x")Integer sid,Pageable pageable);
	
	@Query("SELECT s FROM Score s JOIN s.course c WHERE c.name LIKE :x")
	Page<Score> searchByCourseName(@Param("x")Integer s,Pageable pageable);
	
	@Query("SELECT s FROM Score s JOIN s.student st WHERE st.studentCode LIKE :x")
	Page<Score> searchByStudentCode(@Param("x")String s,Pageable pageable);
	
}
