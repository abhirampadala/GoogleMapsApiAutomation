import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import static org.hamcrest.Matchers.equalTo;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import files.CommonUtility;
import files.payloadData;
import files.resources;

import static io.restassured.RestAssured.given;


public class Postreqest {
	
	Properties prop = new Properties();
	CommonUtility util = new CommonUtility();
	
	@BeforeTest
	public void getData() throws IOException {
		FileInputStream fis = new FileInputStream("C:\\Users\\abhirama\\Desktop\\abhi\\GoogleMapsApi\\src\\files\\env.properties");
		prop.load(fis);
	}
	
	@Test
	public void postdata() {
		
		RestAssured.baseURI=prop.getProperty("Host");
		Response res = given().
		queryParam("key", prop.getProperty("Key")).log().all().
		body(payloadData.getAddPostData()).
		
		when().
		post(resources.postResource()).
		then().assertThat().statusCode(200).and().contentType(ContentType.JSON).and().
		body("status",equalTo("OK")).
		extract().response();
		
		JsonPath js = util.rawtoJson(res);
		String placeid = js.get("place_id");
//		String responseString = res.asString();
//		
//		System.out.println(responseString);
//		
//		JsonPath js = new JsonPath(responseString);
//		String placeid = js.get("place_id");
//		System.out.println(placeid);
		
		given().
		queryParam("key", prop.getProperty("Key")).
		body("{" + 
				"\"place_id\": \""+placeid+"\""+
				"}").
		when().
		post("/maps/api/place/delete/json").
		then().assertThat().statusCode(200).and().contentType(ContentType.JSON).and().
		body("status",equalTo("OK"));
		
	}

}
