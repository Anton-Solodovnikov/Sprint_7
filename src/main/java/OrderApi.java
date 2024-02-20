import groovy.transform.ToString;
import io.restassured.response.Response;
import json.Orders;

public class OrderApi extends BaseHttpClient {

    private final String apiPath = "/api/v1/orders/";

    public Response createOrder(Orders orders) {
        return doPostRequest(apiPath, orders);
    }

    public Response cancelOrder(String track) {
        String json = "{\"track\":"+ track +"}";
        return doPutRequest(apiPath + "cancel", json);
    }

    public Response getOrders() {
        return doGetRequest(apiPath);
    }

    public Response getOrderByTrack(String track) {
        return doGetRequest(apiPath + "track?t="+ track);
    }
}