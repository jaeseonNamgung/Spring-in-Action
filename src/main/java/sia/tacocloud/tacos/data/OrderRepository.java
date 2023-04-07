package sia.tacocloud.tacos.data;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import sia.tacocloud.tacos.Ingredient;
import sia.tacocloud.tacos.Order;
import sia.tacocloud.tacos.User;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order, String> {

    List<Order> findByUserOrderByPlacedAtDesc(User user, Pageable pageable);
}
