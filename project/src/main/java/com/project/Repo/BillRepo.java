package com.project.Repo;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.dto.BillStatistic;
import com.project.entity.Bill;


public interface BillRepo extends JpaRepository<Bill, Integer> {
	
	@Modifying
	@Query("DELETE FROM Bill u WHERE u.buyDate >= :x ")
	void deleteByCreateaAt(@Param("x") Date s);
	
	@Query("SELECT u FROM Bill u WHERE u.buyDate >= :x ")
	List<Bill> searchByDate(@Param("x") Date s);

	///Đếm số lượng đơn group by MONTH(buyDate) 
	//- dùng custom object để build
	// SELECT id, MONTH(buyDate) from bill;
	// select count(*), MONTH(buyDate) from bill
	// group by MONTH(buyDate)
	@Query("SELECT count(b.id), month(b.buyDate)"
			+ " FROM Bill b GROUP BY month(b.buyDate) ")
	List<Object[]> thongKeBill();

	@Query("SELECT new jmaster.io.project3.dto.BillStatistic(count(b.id), month(b.buyDate)) "
			+ " FROM Bill b GROUP BY month(b.buyDate) ")
	List<BillStatistic> thongKeBill2();
}
