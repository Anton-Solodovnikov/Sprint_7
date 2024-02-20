import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

public class ListOfOrdersTest {
    OrdersSteps steps = new OrdersSteps();

    @Test
    @DisplayName("Проверка вывода списка заказов")
    @Description("Тестируем orderAPI")
    public void getOrdersListTest() {
        steps.checkOrdersList();
    }
}
