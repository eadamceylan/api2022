package get_requests;

import base_urls.JsonPlaceHolderBaseUrl;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Test;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class Get07Practice extends JsonPlaceHolderBaseUrl {
    /*
        Given
              https://jsonplaceholder.typicode.com/todos
      When
          I send GET Request to the URL
      Then
          1)Status code is 200
          2)Print all ids greater than 190 on the console
            Assert that there are 10 ids greater than 190
          3)Print all userIds whose ids are less than 5 on the console
            Assert that the number of userIds whose ids are less than 5 is 4
          4)Print all titles whose ids are less than 5
            Assert that "delectus aut autem" is one of the titles whose id is less than 5
     */

    @Test
    public void get01(){

        //1. step: Set the URL
        spec.pathParam("first","todos");

        //2. step: Set the expected data

        // 3. step: send the request and get the response
        Response response = given().spec(spec).when().get("/{first}");
        //response.prettyPrint();

        // 4. step: Do assertions

        //1)Status code is 200
        response.then().assertThat().statusCode(200);

        // 2)Print all ids greater than 190 on the console

        JsonPath json  = response.jsonPath();
        List<Integer> myList = json.getList("findAll{it.id>190}.id");
        System.out.println(myList);// [191, 192, 193, 194, 195, 196, 197, 198, 199, 200]

        // Assert that there are 10 ids greater than 190
        assertEquals("Not match",10, myList.size());



        // 3)Print all userIds whose ids are less than 5 on the console
        List<Integer> userIds = json.getList("findAll{it.id<5}.userIds");
        System.out.println(userIds);
        //   Assert that the number of userIds whose ids are less than 5 is 4
        assertEquals("Did not match",4,userIds.size());

       // 4)Print all titles whose ids are less than 5
        List<String> title = json.getList("findAll{it.id<5}.title");
        System.out.println(title);

        // Assert that "delectus aut autem" is one of the titles whose id is less than 5
        assertTrue(title.contains("delectus aut autem"));

        assertTrue(title.stream().anyMatch(t->t.equals("delectus aut autem")));

    }
}
