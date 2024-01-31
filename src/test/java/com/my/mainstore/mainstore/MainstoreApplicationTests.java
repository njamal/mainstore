package com.my.mainstore.mainstore;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.my.mainstore.MainstoreApplication;
import com.my.mainstore.controller.ProductsController;
import com.my.mainstore.service.RecurService;

import lombok.extern.slf4j.Slf4j;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MainstoreApplication.class)
@Slf4j
class MainstoreApplicationTests {
	@Autowired
	RecurService recurService;
	@Autowired
	ProductsController productsController;

	@Test
	void contextLoads() {
	}
//	List<String> monthlyRecur(int duration,LocalDate today);
//
//	List<String> weeklyRecur(String inputDate)throws ParseException;
//	@Test
//	void getMonthlyRecurDateTest() throws ParseException {
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//		LocalDate localDate = LocalDate.parse("23/07/2021", formatter);
//		List<String> invoiceDate = recurService.monthlyRecur(3, localDate);
//		Assertions.assertNotNull(invoiceDate);
//		if (invoiceDate != null) {
//			log.info("Monthly Invoice Date ::: " + invoiceDate);
//		}
//	}
	
	@Test
	void getWeeklyRecurDateTest() throws ParseException {
		List<String> invoiceDate = recurService.weeklyRecur("23/07/2021");
		Assertions.assertNotNull(invoiceDate);
		if (invoiceDate != null) {
			log.info("Weekly Invoice Date ::: " + invoiceDate);
		}
	}
	
	@Test
	void weeklyRecurrControllerTest() throws ParseException, JsonMappingException, JsonProcessingException, JSONException {
		HashMap<Object, Object> recurred = productsController.getRecurring(3, "weekly", 10, "23/07/2021");
		Assertions.assertNotNull(recurred);
		if (recurred != null) {
			log.info("Weekly Recurred List ::: " + recurred);
		}
	}
	
	@Test
	void monthlyRecurrControllerTest() throws ParseException, JsonMappingException, JsonProcessingException, JSONException {
		HashMap<Object, Object> recurred = productsController.getRecurring(3, "monthly", 10, "23/07/2021");
		Assertions.assertNotNull(recurred);
		if (recurred != null) {
			log.info("Monthly Recurred List ::: " + recurred);
		}
	}
}
