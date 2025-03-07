import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;


@QuarkusTest
@QuarkusTestResource(WiremockGithubApi.class)
class GithubServicesTestTest {


    @Test
    void testGetUserRepositoriesSuccessJson() {




        String username = "octocat";


        given().accept("application/json").when().get("/repositories/" + username).then().statusCode(200);
        // .body(is(Response.jsonData));
    }


}














