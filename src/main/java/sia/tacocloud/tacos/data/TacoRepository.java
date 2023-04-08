package sia.tacocloud.tacos.data;

import org.springframework.data.jpa.repository.JpaRepository;
import sia.tacocloud.tacos.Taco;

public interface TacoRepository extends JpaRepository<Taco, Long> {

}
