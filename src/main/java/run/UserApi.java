package run;

import static spark.Spark.*;

import spark.Request;
import spark.Response;
import spark.Route;

public class UserApi {

	public static Route addUsername = (Request request, Response response) -> {
		System.out.println("good");
		return "hello";
		
	};
    
	public static Route changeUsername;
	public static Route deleteUsername;
	


}
