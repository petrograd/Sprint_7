package order;

import lombok.Data;
import java.util.List;
@Data
public class Order {
   private String firstName;
    private String lastName;
    private String address;
    private Integer metroStation;
    private String phone;
    private Integer rentTime;
    private String deliveryDate;
    private String comment;
    private List<String> color;

    public Order() {
    }

    public Order(String firstName, String lastName, String address, Integer metroStation, String phone, Integer rentTime, String deliveryDate, String comment, List<String> color) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    public static Order getDefault() {
        return new Order("Женя",
                "Лукашин",
                "3-я улица Строителей, дом 25, квартира 12",
                1,
                "+74953333222",
                3,
                "2022-09-28",
                "Вы думаете, Вы в Москве?!",
                null);

    }

}
