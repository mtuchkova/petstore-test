import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import model.Pet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.HTTP_OK;

public class PetStoreService {

    private static final Logger log = LoggerFactory.getLogger(PetStoreService.class.getName());
    private static final String ROOT_URI = "https://petstore.swagger.io/v2";
    private static final String ADD_PET_ENDPOINT = "/pet";
    private static final String PET_BY_ID_ENDPOINT = "/pet/{petId}";
    private final RequestSpecification requestSpecification;

    public PetStoreService() {
        requestSpecification = new RequestSpecBuilder().setBaseUri(ROOT_URI)
                .build()
                .contentType("application/json; charset=utf-8");
    }

    public Pet sendAddPetToStoreRequest(Pet pet) throws PetStoreServiceException {
        Response response = given(requestSpecification).body(pet)
                .post(ADD_PET_ENDPOINT);
        checkResponseStatusCode(response);
        return response.as(Pet.class);
    }

    public Pet sendGetPetByIdRequest(Long petId) throws PetStoreServiceException {
        Response response = given(requestSpecification).pathParam("petId", petId)
                .get(PET_BY_ID_ENDPOINT);
        checkResponseStatusCode(response);
        return response.as(Pet.class);
    }

    public void sendDeletePetByIdRequest(Long petId) throws PetStoreServiceException {
        Response response = given(requestSpecification).pathParam("petId", petId)
                .delete(PET_BY_ID_ENDPOINT);
        checkResponseStatusCode(response);
    }

    private void checkResponseStatusCode(Response response) throws PetStoreServiceException {
        int statusCode = response.statusCode();
        if (statusCode != HTTP_OK) {
            String responseBody = response.getBody().asString();
            log.error(responseBody);
            throw new PetStoreServiceException(responseBody);
        }
    }
}
