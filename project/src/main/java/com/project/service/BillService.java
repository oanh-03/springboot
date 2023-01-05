package com.project.service;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.project.Repo.BillRepo;
import com.project.Repo.UserRepo;
import com.project.entity.Bill;


@Service
public class BillService {
@Autowired
BillRepo billRepo;
@Autowired
UserRepo userRepo;
	@PersistenceContext
	EntityManager entityManager;

	@SuppressWarnings("unchecked")
	public List<Bill> searchByDate(@Param("x") Date s) {
		String jpql = "SELECT u FROM Bill u WHERE " 
					+ "u.createdAt >= :x ";

		return entityManager.createQuery(jpql)
				.setParameter("x", s)
				.setMaxResults(10)
				.setFirstResult(0)
				.getResultList();
	}

}
