package com.my.mainstore.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.my.mainstore.service.RecurService;

@Service
//@Qualifier("RecurService")
public class RecurServiceImpl implements RecurService {
	@Override
	public List<String> weeklyRecur(String inputDate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//String inputDate="8/07/2021";  
		inputDate = inputDate != null || !inputDate.equals("") ? inputDate : sdf.format(new Date());
		Date date = new SimpleDateFormat("dd/MM/yyyy").parse(inputDate);
		
		//SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	   // String inputDate1 = "23/07/2021";
	   // Date date = new SimpleDateFormat("dd/MM/yyyy").parse(inputDate);
	    Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime(date);
		calendar1.add(Calendar.MONTH, 0);

	    Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(date);
		calendar2.add(Calendar.MONTH, 3);
		
		List<String> weeklyInvoiceDate1 = new ArrayList<String>();
	    while (calendar1.before(calendar2)) {
	    	
	            calendar1.setFirstDayOfWeek(Calendar.TUESDAY);
	            	calendar1.add(Calendar.DAY_OF_MONTH, -1);
	            int day = calendar1.get(Calendar.DAY_OF_WEEK);
	            
	            day = (day + 6) % 7;
	            day = day == 0 ? 7 : day;
	            calendar1.add(Calendar.DAY_OF_YEAR, 2 - day);
	    	    
	            calendar1.add(Calendar.DATE, 7);

	    		String beginDate = sdf.format(calendar1.getTime());
	    		
	    		weeklyInvoiceDate1.add(beginDate);
	        }
	  //  weeklyInvoiceDate.get(2).setWeeklyInvoiceDate(weeklyInvoiceDate1);
	return weeklyInvoiceDate1;
	}	

//	@Override
//	public ArrayList<String> weeklyRecur(String inputDate) throws ParseException {
//		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
////String inputDate="8/07/2021";  
//		inputDate = inputDate != null || !inputDate.equals("") ? inputDate : sdf.format(new Date());
//		Date date = new SimpleDateFormat("dd/MM/yyyy").parse(inputDate);
//////////////////////////////////////////////////////
//		Calendar calendar1 = Calendar.getInstance();
//		calendar1.setTime(date);
//		// Set the first day of the week, according to Chinese custom the first day of
//		// the week is Monday
//		calendar1.setFirstDayOfWeek(Calendar.TUESDAY);
//
//		// Determine whether the date to be calculated is Sunday, if it is, subtract one
//		// day to calculate Saturday, otherwise there will be problems, and the
//		// calculation will go to the next week
//		int dayOfWeek = calendar1.get(Calendar.DAY_OF_WEEK);// Get the current date is the day of the week
//		if (1 == dayOfWeek) {
//			calendar1.add(Calendar.DAY_OF_MONTH, -1);
//		}
//
//		// Get the current date is the day of the week
//		int day = calendar1.get(Calendar.DAY_OF_WEEK);
//
//		calendar1.add(Calendar.DATE, 7 * 1);
//
//		calendar1.add(Calendar.DATE, calendar1.getFirstDayOfWeek() - day);
//
//		String beginDate = sdf.format(calendar1.getTime());
//		calendar1.add(Calendar.DATE, 6);
//
//		String endDate = sdf.format(calendar1.getTime());
//
//		System.out.println(beginDate + " to " + endDate);
//		ArrayList<String> invoiceDate = new ArrayList<String>();
//		invoiceDate.add(beginDate);
//		return invoiceDate;
//	}

	@Override
	public List<String> monthlyRecur(int duration, LocalDate today) {
		DateTimeFormatter df = DateTimeFormatter.ofPattern("d-M-yyyy");
		DateTimeFormatter sdf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		today = today != null || !today.equals("") ? today : LocalDate.now();
		int month = today.getMonthValue();
		int year = today.getYear();

		String input_StartMonthYear = month + "-" + year;
		LocalDate start1 = LocalDate.parse("20-" + input_StartMonthYear, df);

		if (today.compareTo(start1) >= 0) {
			month += 1;
			input_StartMonthYear = month + "-" + year;
		} else {
			input_StartMonthYear = month + "-" + year;
		}
		LocalDate durationReccured = LocalDate.now().plusMonths(duration);

		int endMonthRecurred = durationReccured.getMonthValue();
		int endYearRecurred = durationReccured.getYear();
		String input_EndMonthYear = endMonthRecurred + "-" + endYearRecurred;
		int i = 1;

		LocalDate start = LocalDate.parse("20-" + input_StartMonthYear, df);
		LocalDate end = LocalDate.parse("20-" + input_EndMonthYear, df);
		List<String> invoiceDate = new ArrayList<String>();
		List<String> invoiceDate1 = new ArrayList<String>();
		for (LocalDate d = start; d.isBefore(end.plusMonths(1)); d = d.plusMonths(1)) {
			// String monthSeq ;
			// monthSeq = String.valueOf(i++);
			invoiceDate.add(sdf.format(d));
			//invoiceDate.add(new String(null, subType, duration,invoiceDate1,null));
		}
		//invoiceDate.get(4).setWeeklyInvoiceDate(invoiceDate1);
		return invoiceDate;
	}

//@Override
//public Products updateProducts(Products prod) {
//	Products p = productsDao.updateProducts(prod);
//	return p ;
//}
//
//@Override
//public Products createProducts(Products prod){
//	Products p = productsDao.createProducts(prod);
//	return p ;
//}
//
//@Override
//public Products getProductById(Long prodid){
//	Products p = productsDao.getProductById(prodid);
//	return p ;
//}
//
//@Override
//public List<Products> getProductsSearchingByParam(String param) {
//	List<Products> p = productsDao.getProductsSearchingByParam(param);
//	return p;
//}

}
