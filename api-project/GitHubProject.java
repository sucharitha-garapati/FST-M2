package Projects;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

@Test
public class GitHubProject {
	//Declare Request specification
	RequestSpecification requestSpec;
	int sshID;
	
	 @BeforeClass
	 public void setUp() {
	//Set request specification
	requestSpec = new RequestSpecBuilder()
	//Set content type
	.setContentType(ContentType.JSON)
	.addHeader("Authorization","token ")
	//Set Base URL
	.setBaseUri("https://api.github.com")
	//Build request specification
	.build();
}
	 
	 public void addNewKey() {
	        // Create JSON request
	        String reqBody = "{\"title\": \"TestAPIKey\", \"key\": \"ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQC7dqkfnnVvnvd1vw+bnQzrkC/u3L8sBcVTcJKzTHqtX7erSul/rc8psEbPgxvq6zb83S0UdwnnHLYc5keGQ1Th2Nai4/lsrDuRB/AE1Smi7Ccr+oZosc1Ib02VqmTPnXLpNHFCCSgUGqFI4iCPqlNwFJgRnL/EVGV0Um8D0XKB5SqMUlFgMbcAG79cgXWlxJbOm2Orv3tyWWVRuhLS6YtQkgdBGbEc4KVFJBzmwLejxnC/11+BC6yX8vsiYHxFSM7qzUMKvRpJ1XK1YxyYhYy9X0RGQa+9Ly18EHYuasuM/RKFr5RJwtEhuqT8L4sMB7phNLtkkkaDe52WpXej\"}";
	 
	        Response response = 
	            given().spec(requestSpec) // Set headers
	            .body(reqBody) // Add request body
	            .when().post("/user/keys"); // Send POST request
	        
	        sshID = response.then().extract().path("id");
	        
	        // Assertions
	        response.then().statusCode(201);
	        response.then().body("id", equalTo(sshID));
	 }
	 
	 @Test(priority=2)
	    public void getKeyInfo() {
	        Response response = 
	            given().spec(requestSpec) // Set headers
	            .when().get("/user/keys"); // Send GET request
	 
	        Reporter.log(response.body().asPrettyString());
	        // Assertion
	        response.then().statusCode(200);
	        response.then().body("[0].id", equalTo(sshID));
	    }
	 
	    @Test(priority=3)
	    public void deleteKey() {
	        Response response = 
	            given().spec(requestSpec) // Set headers
	            .when().pathParam("id", sshID) // Set path parameter
	            .delete("/user/keys/{id}"); // Send DELETE request
	        
	        Reporter.log(response.body().asPrettyString());
	        // Assertion
	        response.then().statusCode(204);
	    }
}
