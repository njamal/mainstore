package com.my.mainstore.controller;

import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.my.mainstore.model.Products;
import com.my.mainstore.service.ProductsService;
import com.my.mainstore.service.RecurService;

import static java.lang.System.*;

@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:4200"})
@RestController
@RequestMapping("/productsMain")
public class ProductsController {
    protected final Log logger = LogFactory.getLog(this.getClass());
    @Autowired
    ProductsService productsService;
    @Autowired
    RecurService recurService;

    /*
     * Recurring Monthly/Weekly.
     */
    @PostMapping(path = "/getRecurring", produces = MediaType.APPLICATION_JSON_VALUE)
    public HashMap<Object, Object> getRecurring(@RequestParam("duration") Integer duration,
                                                @RequestParam("subType") String subType, @RequestParam("amt") Integer amt,
                                                @RequestParam("dtString") String dtString)
            throws JsonProcessingException, JSONException, ParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDate = LocalDate.parse(dtString, formatter);

//		List<Object> List = Arrays.asList(amt.toString());
//		List<String> List1 = Arrays.asList(duration.toString());
//		List<String> List2 = Arrays.asList(subType);
//	
//		List = Stream.concat(List.stream(), List1.stream()).parallel().collect(Collectors.toList());
//		
//		List = Stream.concat(List.stream(), List2.stream()).parallel().collect(Collectors.toList());

        HashMap<Object, Object> map = new HashMap<Object, Object>();
        map.put("subscribeType", subType);
        map.put("durationMonth", duration);
        map.put("amt", amt);
        java.util.List<Object> invoiceDate = Collections.emptyList();
        if (subType.equalsIgnoreCase("monthly")) {
            List<String> invoiceDate1 = recurService.monthlyRecur(duration, localDate);
            //invoiceDate = Stream.concat(invoiceDate.stream(), invoiceDate1.stream()).parallel().collect(Collectors.toList());
            map.put("invoiceDate", invoiceDate1);
        }
        if (subType.equalsIgnoreCase("weekly")) {
            List<String> invoiceDate2 = recurService.weeklyRecur(dtString);
//			invoiceDate = Stream.concat(invoiceDate.stream(), invoiceDate2.stream()).parallel().collect(Collectors.toList());			
            map.put("invoiceDate", invoiceDate2);

        }
        //invoiceDate = Stream.concat(invoiceDate.stream(), List.stream()).parallel().collect(Collectors.toList());
        return map;
    }

    /*
     * Get All Products List.
     */
    @GetMapping("/getAllproductsList")
    public List<Products> getAllProductsList() {
        return productsService.getAllProductsList();
    }

    /*
     * Deleted products By ID
     */
//@DeleteMapping("/deletedProductById/{prodid}")
//public Map<String, Boolean> deletedProductById(@PathVariable Long  prodid) throws Exception{
//	productsService.deletedById(prodid);
//	Map<String, Boolean> response = new HashMap<>();
//	response.put("Deleted Product ID : " +prodid, Boolean.TRUE);
//	return response;
//}

    @DeleteMapping("/deletedProductById/{prodid}")
    public ResponseEntity<String> deletedProductById(@PathVariable long prodid) {
        Products p = productsService.deletedById(prodid);
        if (p != null) {
           // return ResponseEntity.noContent().build();
            return ResponseEntity.status(HttpStatus.OK).body("Product ID :" + p.getProdid() + " was deleted.");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No class com.my.mainstore.model.Products entity with id" + p.getProdid() + " exists!!!!!! ");
       // No class com.my.mainstore.model.Products entity with id 6 exists!
      //  return ResponseEntity.notFound().build();
    }

    /*
     * Insert New Product into DB
     */
    @PostMapping("/createProduct")
    public ResponseEntity<?> createdProduct(@RequestBody Products p) {
        out.println("Products ::::  " + p);
        Products createdProducts = productsService.createProducts(p);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(createdProducts.getProdid()).toUri();
        ResponseEntity.created(uri).build();

        return ResponseEntity.status(HttpStatus.OK).body(createdProducts);
       // return ResponseEntity.created(uri).build();
    }
//@PostMapping("/createProduct")
////public Products createProduct(@Valid @RequestBody Products products) {
//	public ResponseEntity<?>createProduct(@Valid @RequestBody Products productsDetail) throws ResourceNotFoundException{
//	 Products p = productsService.createProducts(productsDetail);
//	 
//	 if(null != p) {
//		// return ResponseEntity.status(HttpStatus.OK).body("Updated Product for : " + p);
//		 return new ResponseEntity<Products>(p, HttpStatus.OK);
//	 }
//	 return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product Id : " + p.getProdid() + " Are Not Found!");	
//}

    /*
     * get Product Searching by PARAM
     */
    @GetMapping("/getProductById/{prodid}")
    public ResponseEntity<?> getProductById(@PathVariable long prodid) throws ResourceNotFoundException {
        Products p;
        //	try {
        p = productsService.getProductById(prodid);
        if (null != p) {
            return ResponseEntity.status(HttpStatus.OK).body(p);
            //	return p;
        }
        //eventPublisher.publishEvent(new SingleResourceRetrievedEvent(this, response));
        //	if(null != p) {
        logger.info("GET product data BY ID : " + p.getProdid() + " !!!!!!! ");
      //  return ResponseEntity.notFound().build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(p);
    }

    @GetMapping("/getProductSearchingByParam/{param}")
    public List<Products> getProductById(@PathVariable String param) {
        out.println(" Param @!@#!%^!@#%& ::::::: " + param);
        List<Products> p = productsService.getProductsSearchingByParam(param);
        return p;
    }

    /*
     * Update product By ID
     */
    @PutMapping("updateProduct/{prodid}")
    public ResponseEntity<?> updateProduct(@PathVariable(value = "prodid") Long prodid,
                                           @Valid @RequestBody Products productsDetail) throws ResourceNotFoundException {
        Products updateProduct = new Products();
        Products prod = productsService.getProductById(prodid);
        out.println("prod.getProddesc() #### !!!! " + prod.getProddesc());
        if (null != prod) {
            if (productsDetail.getQty() <= 0)
                logger.info("Nothing to do");
            else prod.setQty(productsDetail.getQty());
            if (productsDetail.getProdcat() == null || productsDetail.getProdcat().isEmpty())
                logger.info("Nothing to do");
            else prod.setProdcat(productsDetail.getProdcat());
            if (productsDetail.getProddesc() == null || productsDetail.getProddesc().isEmpty())
                logger.info("Nothing to do");
            else prod.setProddesc(productsDetail.getProddesc());
            if (productsDetail.getProdname() == null || productsDetail.getProdname().isEmpty())
                logger.info("Nothing to do");
            else prod.setProdname(productsDetail.getProdname());

            productsService.updateProducts(prod);
            return ResponseEntity.status(HttpStatus.OK).body(prod);
            //return ResponseEntity.status(HttpStatus.OK).body("Updated Product for : " + prod);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product Id : " + prodid + " Are Not Found!");
    }

    //@GetMapping("/test1")
//public ArrayList<String> getAllArrayList(){
//public class Node {
//	private String description = "";
//	private Node leftNode = null;
//	private Node rightNode = null;
//	
//	public Node() {}
//	public Node(String description) {
//		this.description = description;
//	}
//	public String getDescription() {
//		return description;
//	}
//	public void setDescription(String description) {
//		this.description = description;
//	}
//	public Node getLeftNode() {
//		return leftNode;
//	}
//	public void setLeftNode(Node leftNode) {
//		this.leftNode = leftNode;
//	}
//	public Node getRightNode() {
//		return rightNode;
//	}
//	public void setRightNode(Node rightNode) {
//		this.rightNode = rightNode;
//	}		
//}
//}
//
//@GetMapping("/test2")
//public class TestNode {
//	public static void main(final String[] args) {
//		Node root = new Node("Main Parent");
//		Node left = new Node("L");
//		Node right = new Node("R");
//		
//		root.setLeftNode(left);
//		root.setRightNode(right);
//		
//		Node leftleft = new Node("L.L");
//		Node leftright = new Node("L.R");
//
//		left.setLeftNode(leftleft);
//		left.setRightNode(leftright);
//
//		Node rightleft = new Node("R.L");
//		Node rightright = new Node("R.R");
//
//		right.setLeftNode(rightleft);
//		right.setRightNode(rightright);
//		
//		Node leftleftleft = new Node("L.L.L");
//		Node leftleftright = new Node("L.L.R");
//		
//		leftleft.setLeftNode(leftleftleft);
//		leftleft.setRightNode(leftleftright);
//
//		Node rightrightleft = new Node("R.R.L");
//		Node rightrightright = new Node("R.R.R");
//		
//		rightright.setLeftNode(rightrightleft);
//		rightright.setRightNode(rightrightright);
//		
//		
//		root.print();		
//	}
//}
    static class Singleton {
        // static variable single_instance of type Singleton
        private static Singleton single_instance = null;

        // variable of type String
        public String s;

        // private constructor restricted to this class itself
        private Singleton() {
            s = "Hello I am a string part of Singleton class";
        }

        // static method to create instance of Singleton class
        public static Singleton Singleton() {
            // To ensure only one instance is created
            if (single_instance == null) {
                single_instance = new Singleton();
            }
            return single_instance;
        }
    }

    @PostMapping("/arrayList1")
    public void getAllArrayList1() throws JsonProcessingException {
//create a list of integers
        List<Integer> number = Arrays.asList(2, 3, 4, 5);

// demonstration of map method
        List<Integer> square = number.stream().map(x -> x * x).collect(Collectors.toList());
        out.println(square);

/////////// create a list of String //////////////////////////
        List<String> names = Arrays.asList("Reflection", "Collection", "Stream");

///demonstration of filter method
        List<String> result = names.stream().filter(s -> s.startsWith("S")).collect(Collectors.toList());
        out.println(result);

//////////// demonstration of sorted method//////////////////////
        List<String> show = names.stream().sorted().collect(Collectors.toList());
        out.println(show);

//////////////////// create a list of integers////////////////
        List<Integer> numbers = Arrays.asList(2, 3, 4, 5, 2);

/////////////// collect method returns a set///////////////////////////
        Set<Integer> squareSet = numbers.stream().map(x -> x * x).collect(Collectors.toSet());
        out.println(squareSet);

////////////////////// demonstration of forEach method//////////////////////
        number.stream().map(x -> x * x).forEach(y -> out.println(y));

///////////////////// demonstration of reduce method////////////////////////
        int even = number.stream().filter(x -> x % 2 == 0).reduce(0, (ans, i) -> ans + i);

        out.println(even);

        Stream<Integer> streamIterated = Stream.iterate(40, n -> n + 2).limit(20);
        out.println(streamIterated);

        Collection<String> collection = Arrays.asList("a", "b", "c");
        Stream<String> streamOfCollection = collection.stream();
        out.println(streamOfCollection);

    }

    @PostMapping("/arrayList")
    public void getAllArrayList() throws JsonProcessingException {

        // instantiating Singleton class with variable x
        Singleton x = Singleton.Singleton();

        // instantiating Singleton class with variable y
        Singleton y = Singleton.Singleton();

        // instantiating Singleton class with variable z
        Singleton z = Singleton.Singleton();

        // changing variable of instance x
        x.s = (x.s).toUpperCase();

        out.println("String from x is " + x.s);
        out.println("String from y is " + y.s);
        out.println("String from z is " + z.s);
        out.println("\n");

        // changing variable of instance x
        z.s = (z.s).toLowerCase();

        out.println("String from x is " + x.s);
        out.println("String from y is " + y.s);
        out.println("String from z is " + z.s);
//	///////////////
//	String a="stack";
//	  System.out.println(a);//prints stack
//	//  a.setValue("overflow");
//	  a = "overflow";
//	  System.out.println(a);//if mutable it would print overflow
//	return a ;
        /////////////////////////////////////////
        // int solution(int X = 6; int Y = 13; int[] A) {
//	int X = 7; int Y = 42; int[] A = {6,42,11,7,1}; //output 4
        // int X = 6; int Y = 13; int[] A = {13, 13, 1, 6}; //output -1
        //// int X = 100; int Y = 63; int[] A = {100,63,1,6,2,13}; // ouput 5
//	int X = 100; int Y = 63; int[]A = new int[]{100,63,1,6,2,13};
        // int X = 100; int Y = 63; int[]A = new int[] {6,42,11,7,1,100,63,1,6,2,13};
//	int X = 100; int Y = 63; int[]T = new int[] {-3,-14,-5,7,8,42,8,13}; // -3 -14 winter
//////////////////
//	int X = 100; int Y = 63; int[]T = new int[] {-3,-14}; // -3 -14 winter 34
//        int N = T.length;
//        int result = -1;
//        int nX = 0;
//        int nY = 0;
//        for (int i = 0; i < N; i++) {
////            if (T[i] == -3)
////                nX += 1;
////            else if (T[i] == -14)
////                nY += 1;
////            if (nX == nY)
//                result = i;
//        }
//        return result;
        ////////////////////////////////////////////
        // }
        ///////
//        public class Solution {
//
//        	public static void main(String[] args) {
//        		int[] A = new int[]{2,3,1,5};
//        		System.out.println(solution(A));
//        	}
//        	
//        	public static int solution(int[] A) {
//        		int[] counters = new int[A.length+2];
//        		for (int i = 0; i < A.length; i++) {
//        			counters[A[i]] = 1;
//        		}
//        		for (int i = 1; i < counters.length; i++) {
//        			if (counters[i] == 0)
//        				return i;
//        		}
//        		//no element is missing
//        		return -1;
//        	}
//        }
        //////
//	return null;

//        import java.util.*;
//
//        class Solution {
//            int solution(int X, int Y, int[] A) {
//                	X = 7; Y = 42; A = new int[]{6,42,11,7,1}; //output 4
//        	//X = 6; Y = 13; A = new int[]{13, 13, 1, 6}; //output -1
////        	X = 100; Y = 63; A = new int[]{100,63,1,6,2,13}; // ouput  5
//                int N = A.length;
//                int result = -1;
//                int nX = 0;
//                int nY = 0;
//                for (int i = 0; i < N; i++) {
//                    if (A[i] == X)
//                        nX += 1;
//                    else if (A[i] == Y)
//                        nY += 1;
//                    if (nX == nY)
//                        result = i;
//                }
//                return result;
//            }
//        }

//	ObjectMapper mapper = new ObjectMapper();
//	public StringBuilder getAllArrayList(){	
        // public static String generateAccountNumber(String identificationNumber) {
//		String generatedAccountNumber = "";
//		String identificationNumber = "597";
//		char[] numbers = identificationNumber.toCharArray();
//		System.out.println("numbers ::::: " + mapper.writeValueAsString(numbers));
//		int num;
//
//		for (int a = 0; a < numbers.length; a++) {
//			System.out.println("numbers aaa ::::: " + numbers[a]);
//			System.out.println("numbers bbb ::::: " + Character.getNumericValue(numbers[a]));
//			num = Character.getNumericValue(numbers[a]);
//			
//			if (numbers[a] > 0) { System.out.println("numbers xxx ::::: " + num);
//			System.out.println("vvvvv ::::: " + Character.getNumericValue(numbers[a]));
//			System.out.println("gggggAAA ::::: " + (5%10));
//			System.out.println("gggggBBB ::::: " + (9%10));
//			System.out.println("gggggCCC ::::: " + (10%10));
//			System.out.println("numbers ccc ::::: " + (a-1));
//			System.out.println("mmmmm :::: " + Character.getNumericValue(a-1));
//				num = (num + (1-a)) % 10;
//				System.out.println("numbers yyyy ::::: " + num);
//				generatedAccountNumber += num;
//			}
//			System.out.println("generatedAccountNumber" + generatedAccountNumber);
//		}
//		
//		return generatedAccountNumber; //546 --> bug 46
//	}

//	ArrayList<Person> list = new ArrayList<Person>();
//	
//	list.add(new Person("Andy", 25, 1));
//	list.add(new Person("Brad", 22, 2));
//	list.add(new Person("Andy", 30, 3));

        // list.sort(Comparator.comparing(Person::getAge).thenComparing(Person::getName));
//	Comparator<Person> byName = (x, y) -> x.name.compareTo(y.name);
//	Comparator<Person> byAge = (l, r) -> Integer.compare(r.age, l.age);

//	List<Person> sorted = list.stream().
//		    sorted(Comparator.comparing(Person::getAge).thenComparing(Person::getName).collect(Collectors.toList());
//	System.out.println("list" + list);
        // list.stream().sorted(byName.thenComparing(byAge)).forEach(System.out::println);
        //// System.out::println(Collections.sort(list));
//	List<Person> sorted = list.stream().
//		    sorted(Comparator.comparing(Person::getAge).thenComparing(Person::getName).collect(Collectors.toList()));

//	ArrayList<String> cars = new ArrayList<String>();
//	cars.add("Volvo");
//	cars.add("BMW");
//	cars.add("Ford");
//	cars.add("Mazda");
//	System.out.println("Add In Cars ::: " + cars);
//	
//	///***Reverse String***///
//	String input = "abc";
//	StringBuilder input1 = new StringBuilder();
//	input1.append(input);
//	input1.reverse();
//	System.out.println("Reverse ::: " + input1);
//	
//	///***Reverse Number***///
//	int num = 123456789;
//	int reversenum = 0;
//	while(num!=0) {
//		reversenum = reversenum * 10 ;
//		reversenum = reversenum + num%10;
//		num = num/10;
//		System.out.println("ReverseNum ::: " + reversenum);
//		System.out.println("num ::: " + num);
//	}

//	List<Integer> numbers = Arrays.asList(new Integer[]{1,2,1,3,4,4});
//	List<Integer> repeatingNumbers = StreamEx.of(numbers).distinct(2).toList();
//	List<Integer> numbers = Arrays.asList(1, 2, 1, 3, 4, 4);
//	List<Integer> duplicated = (List<Integer>) numbers
//	  .stream().filter(n -> numbers.stream().filter(x -> x == n).count() > 1).collect(Collectors.toSet());
//	
//	return duplicated;
    }

//    public int aMethod()
//    {
//        static int i = 0;
//        i++;
//        return i;
//    }


    @PostMapping("/test")
    public void geTest() throws JsonProcessingException {
//		{
//			static int i = 0;
//	        i++;
//	        return i;
//
//	        Test test = new Test();
//	        test.aMethod();
//	        int j = test.aMethod();
//	        System.out.println(j);
//	    }

//		final StringBuffer a = new StringBuffer(); 
//        final StringBuffer b = new StringBuffer(); 
//
//        new Thread() 
//        { 
//            public void run() 
//            {
//                System.out.print(a.append("A")); 
//                synchronized(b) 
//                { 
//                    System.out.print(b.append("B")); 
//                } 
//            } 
//        }.start(); 
//            
//        new Thread() 
//        {
//            public void run() 
//            {
//                System.out.print(b.append("C")); 
//                synchronized(a) 
//                {
//                    System.out.print(a.append("D")); 
//                } 
//            } 
//        }.start(); 

//	    final short x = 2;
//	    int y = 0;
////	    public static void main(String [] args) 
////	    {
//	        for (int z=0; z < 4; z++) 
//	        {
//	            switch (z) 
//	            {
//	                case x: System.out.print("0 ");
//	                default: System.out.print("def ");
//	                case x-1: System.out.print("1 ");  
//	                            break;
//	                case x-2: System.out.print("2 ");
//	            }
//	        }
    }


//		int length = arr.length;
//		boolean isTree = false;
//		if(length >= 2) {
//		    if(arr[length-1] == 0 && arr[length-2] == 0) {
//		        isTree = true;
//		    }
//		}
//		System.out.println(isTrue);


    //}

    /**
     * Get the date range of the week (Monday and Sunday date) based on the current
     * date
     *
     * @return
     * @throws ParseException
     */
    @PostMapping("/getTimeInterval")
    public String getTimeInterval() throws JsonProcessingException, ParseException {
//public String getTimeInterval(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        // Date date = new Date();
        String sDate1 = "24/07/2021";
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
        ////////////////////////////////////////////////////////////////////////////////////////////////////

        ////////////////////////////////////////////////////////////////////////////////////////////////////
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // Determine if the date to be calculated is Sunday, if it is, then subtract one
        // day to calculate Saturday, otherwise there will be problems, and the
        // calculation will go to the next week.
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// Get the current date is the day of the week
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
//      calendar1.add(Calendar.DATE, 7*i);
//
//  	calendar1.add(Calendar.DATE, calendar1.getFirstDayOfWeek() - day);
        // Set the first day of the week, according to Chinese custom, the first day of
        // the week is Monday
        cal.setFirstDayOfWeek(Calendar.TUESDAY);
        // Get the current date is the day of the week
        int day = cal.get(Calendar.DAY_OF_WEEK);
        // According to the rules of the calendar, subtract the difference between the
        // day of the week and the first day of the week for the current date.
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);

        String imptimeBegin = sdf.format(cal.getTime());
        // System.out.println("Monday of the week of Monday:" + imptimeBegin);
        cal.add(Calendar.DATE, 6);
        String imptimeEnd = sdf.format(cal.getTime());
        out.println("Date:::::::" + imptimeBegin + "," + imptimeEnd);
        return imptimeBegin + "," + imptimeEnd;
    }

    @PostMapping("/getWeeklyrange1") ///// yg ni ok!!!!
    public List<String> getWeeklyrange1(@RequestParam("inputDate") String inputDate)
            throws JsonProcessingException, ParseException {
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
        //String inputDate1 = "23/07/2021";
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse(inputDate);
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date);
        calendar1.add(Calendar.MONTH, 0);

        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date);
        calendar2.add(Calendar.MONTH, 3);

        List<String> weeklyInvoiceDate = new ArrayList<String>();
        while (calendar1.before(calendar2)) {
            calendar1.setFirstDayOfWeek(Calendar.TUESDAY);
            calendar1.add(Calendar.DAY_OF_MONTH, -1);
            int day = calendar1.get(Calendar.DAY_OF_WEEK);

            day = (day + 6) % 7;
            day = day == 0 ? 7 : day;
            calendar1.add(Calendar.DAY_OF_YEAR, 2 - day);

            calendar1.add(Calendar.DATE, 7);

            String beginDate = sdf1.format(calendar1.getTime());
            weeklyInvoiceDate.add(beginDate);
        }
        return weeklyInvoiceDate;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
//	@PostMapping("/getWeeklyrange") ///// yg ni ok!!!!
//	public String getWeeklyrange() throws JsonProcessingException, ParseException {
//		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//		String inputDate = "8/07/2021";
//		Date date = new SimpleDateFormat("dd/MM/yyyy").parse(inputDate);
//		////////////////////////////////////////////////////
//		Calendar calendar1 = Calendar.getInstance();
//		calendar1.setTime(date);
//		// Set the first day of the week, according to Chinese custom the first day of
//		// the week is Monday
//
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
//
//		return beginDate + "," + endDate;
//	}

}
