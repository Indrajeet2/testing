package org.usb.testing;

import org.testng.Assert;
//import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import org.json.simple.JSONObject;


import static io.restassured.RestAssured.given;

public class APITest {


	@Test
	public void getUsers() {

		Response response =  given().baseUri("https://reqres.in/api/users").queryParam("page","2")
				.when().get();
		response .then().statusCode(200);
		JsonPath data = new JsonPath(response.asString());
		System.out.println(data.toString());
		Assert.assertEquals(data.getString("total_pages"), "2");
		Assert.assertEquals(response.getHeader("server"), "cloudflare");

	}

	@Test
	public void getUserById() {
		Response response =  given().baseUri("https://reqres.in/api/users/2")
				.when().get();
		response.then().statusCode(200);
				JsonPath data = new JsonPath(response.asString());
				System.out.println(data.toString());
				Assert.assertEquals(data.getString("data.first_name"), "Janet");
				Assert.assertEquals(data.getString("data.last_name"), "Weaver");
	}



	@Test(description = "To create a new user")
	public void createUser() {

		JSONObject data = new JSONObject();
		JSONObject data2 = new JSONObject();

		data.put("id", "20");
		data.put("email","injaiswa@gmail.com");
		data.put("first_name", "Indrajeet");
		data.put("last_name", "Jaiswal");
		data.put("avatar","https://reqres.in/img/faces/2-image.jpg");
		data2.put("data",data);
		// GIVEN
		Response response =	given()
		.baseUri("https://reqres.in/api/users")
		.contentType(ContentType.JSON)
		.body(data2.toString())
		// WHEN
		.when()
		.post();
		JsonPath data1 = new JsonPath(response.asString());
		// THEN
		response.then()
		.statusCode(201);

	Assert.assertEquals(data1.getString("data.first_name"), "Indrajeet");
	Assert.assertEquals(data1.getString("data.last_name"), "Jaiswal");
		


	}
}
