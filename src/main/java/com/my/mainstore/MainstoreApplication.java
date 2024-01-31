package com.my.mainstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;
//@EntityScan(basePackages = "com.my.mainstore.model")
//@ComponentScan(basePackages = "com.my.mainstore.model")
@RestController
@EnableAutoConfiguration
@SpringBootApplication(scanBasePackages= {"com.my.mainstore.*"})
public class MainstoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(MainstoreApplication.class, args);
	}
//	@Service
//	class SomeService {
//
//	    public void doSth(String[] args){
//	        System.out.println(Arrays.toString(args));
//	        ArrayList<String> cars = new ArrayList<String>();
//	    	cars.add("Volvo");
//	    	cars.add("BMW");
//	    	cars.add("Ford");
//	    	cars.add("Mazda");
//	    	System.out.println("Add In Cars ::: " + cars);
//	    }
//	}
}
