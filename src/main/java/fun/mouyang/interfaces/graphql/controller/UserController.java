package fun.mouyang.interfaces.graphql.controller;

import fun.mouyang.interfaces.graphql.dto.Order;
import fun.mouyang.interfaces.graphql.dto.User;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
public class UserController implements GetUserQueryResolver, AddUserMutationResolver {

    private final static Map<String, User> USERS = initUsers();

    private static Map<String, User> initUsers() {
        User user = User.builder()
            .setId("1")
            .setName("Michael")
            .setAge(25)
            .build();

        Map<String, User> users = new HashMap<>();
        users.put("1", user);
        return users;
    }

    @Override
    @QueryMapping("getUser")
    public User getUser(@Argument String id) {
        System.out.println("In getUser()");

        return USERS.get(id);
    }

    @SchemaMapping("orders")
    public List<Order> getUserOrders(User user) {
        System.out.println("In getUserOrders()");

        Order order1 = Order.builder()
                .setId("1")
                .setTitle("Food")
                .setPrice(100)
                .build();

        Order order2 = Order.builder()
                .setId("2")
                .setTitle("Book")
                .setPrice(200)
                .build();

        if (Objects.equals(user.getId(), "1")) {
            return List.of(order1, order2);
        } else {
            return null;
        }
    }

    @Override
    @MutationMapping("addUser")
    public User addUser(@Argument String id,
                        @Argument String name,
                        @Argument Integer age) {
        User newUser = User.builder()
                .setId(id)
                .setName(name)
                .setAge(age)
                .build();
        USERS.put(id, newUser);
        return newUser;
    }
}
