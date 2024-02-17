import io.restassured.response.Response;
import json.Courier;

public class CourierApi extends BaseHttpClient {

    private final String apiPath = "/api/v1/courier/";

    public Response createCourier(Courier courier) {
        return doPostRequest(apiPath, courier);
    }

    public Response loginCourier(Courier courier) {
        String secondPartOfApiPath = apiPath + "login";
        return doPostRequest(secondPartOfApiPath, courier);
    }

    public Response deleteCourier(int id) {
        return doDeleteRequest(apiPath + id);
    }


}
