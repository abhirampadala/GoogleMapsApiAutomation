import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static org.hamcrest.Matchers.equalTo;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import files.CommonUtility;
import files.resources;

import static io.restassured.RestAssured.given;

public class Getrequest {
	
	Properties prop = new Properties();
	CommonUtility util = new CommonUtility();
	
	@BeforeTest
	public void getData() throws IOException {
		FileInputStream fis = new FileInputStream("C:\\Users\\abhirama\\Desktop\\abhi\\GoogleMapsApi\\src\\files\\env.properties");
		prop.load(fis);
	}
	
	
	@Test
	public void Test() {
		
		RestAssured.baseURI=prop.getProperty("GoogleHost");;
		
		Response res =given().
				param("location","-33.8670522,151.1957362").
				param("radius","500").
				param("key",prop.getProperty("GoogleKey")).log().all().
				when().
				get(resources.getResource()).
				then().assertThat().statusCode(200).and().contentType(ContentType.JSON).and().
				extract().response();
		
				JsonPath js = util.rawtoJson(res);
				int count = js.get("results.size()");
				for (int i=0;i<=count;i++) {
					String name = js.get("results["+i+"].name");
					System.out.println(name);
				}
				
		
	}

}
