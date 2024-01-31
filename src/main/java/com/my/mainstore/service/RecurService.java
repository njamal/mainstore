package com.my.mainstore.service;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;


public interface RecurService {

	List<String> weeklyRecur(String inputDate)throws ParseException;

	List<String> monthlyRecur(int duration, LocalDate today);

}
