package com.trungtamjava.servlet.jobs;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.trungtamjava.servlet.Repo.UserRepo;
import com.trungtamjava.servlet.entity.User;
import com.trungtamjava.servlet.service.EmailService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class BirthDateJob {
@Autowired
UserRepo userRepo;

@Autowired
EmailService emailService;
//tim user co ngay sinh hom nay va gui mail chuc mung
@Scheduled(cron = " 0 * * * * * ")
public void sendEmailBirthdate() {
	System.out.println("Init birthdate job");
	//1. tim user co ngay sinh hnay
	Calendar cal = Calendar.getInstance();
	cal.set(Calendar.HOUR, 0);
	cal.set(Calendar.MINUTE, 0);
	cal.set(Calendar.SECOND, 0);
	cal.set(Calendar.MILLISECOND, 0);
	//+ - ngay, gio
	//cal.add(1, 0);
	Date now = cal.getTime();
	log.info("" +now);
	List<User> users = userRepo.findByBirthdate(cal.get(Calendar.DATE),
			cal.get(Calendar.MONTH)+1);
	for (User user : users) {
		log.info(user.getName());
		//gia su username la email
		emailService.sendBirthday(user.getUsername(),user.getName());
		//System.out.println(user.getName());
	}
	//test
	//emailService.sendTest();
}
}
