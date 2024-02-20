import com.github.javafaker.Faker;
import io.qameta.allure.Description;
import io.qameta.allure.Issue;
import io.qameta.allure.TmsLink;
import io.qameta.allure.junit4.DisplayName;
import json.Orders;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;


@RunWith(Parameterized.class)
public class CreateOrdersTests {
    private final Orders orders;
    private final List<String> expected;
    private static final List<String> expectedOneColor = Arrays.asList("BLACK");
    private static final List<String> expectedTwoColor = Arrays.asList("BLACK","GRAY");
    OrdersSteps steps = new OrdersSteps();
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private static Faker faker = new Faker(new Locale("ru"));
    static Orders orderWithOneColor = new Orders(
            faker.name().firstName(),
            faker.name().lastName(),
            faker.address().streetAddress(),
            faker.random().nextInt(1,20).toString(),
            faker.phoneNumber().phoneNumber(),
            faker.number().numberBetween(1,20),
            sdf.format(faker.date().birthday()),
            faker.witcher().quote(),
            expectedOneColor);
    static Orders orderWithTwoColor = new Orders(
            faker.name().firstName(),
            faker.name().lastName(),
            faker.address().streetAddress(),
            faker.random().nextInt(1,20).toString(),
            faker.phoneNumber().phoneNumber(),
            faker.number().numberBetween(1,20),
            sdf.format(faker.date().birthday()),
            faker.witcher().quote(),
            expectedTwoColor);
    static Orders orderWithoutColor = new Orders(
            faker.name().firstName(),
            faker.name().lastName(),
            faker.address().streetAddress(),
            faker.random().nextInt(1,20).toString(),
            faker.phoneNumber().phoneNumber(),
            faker.number().numberBetween(1,20),
            sdf.format(faker.date().birthday()),
            faker.witcher().quote());
    public CreateOrdersTests(Orders orders, List<String> expected){
        this.orders = orders;
        this.expected = expected;
    }

    @Parameterized.Parameters
    public static Object[][] CreateOrders() {
        return new Object[][] {
                {orderWithOneColor, expectedOneColor},
                {orderWithTwoColor, expectedTwoColor},
                {orderWithoutColor, null},
        };
    }
    @After
    public void cancelOrder() {
        steps.cancelOrder();
    }
    @Test
    @DisplayName("Создание Заказов с разным набором цветов самоката")
    @Description("Тестируем orderAPI")
    @TmsLink("TestCase-1")
    @Issue("BUG-1")
    public void shouldCreateOrder() {
        steps.createOrder(orders);
        steps.checkOrderStatusCode(201);
        steps.responseNotNull();
        steps.checkOrderData("order.color",expected);
    }
}
