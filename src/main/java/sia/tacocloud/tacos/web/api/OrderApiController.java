package sia.tacocloud.tacos.web.api;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sia.tacocloud.tacos.Order;
import sia.tacocloud.tacos.data.OrderRepository;

@RequiredArgsConstructor
@CrossOrigin("*")
@RestController
public class OrderApiController {

    private final OrderRepository repo;


    /*
    * PatchMapping : 부분 수정 시 사용
    * PutMapping : 전체 수정 시 사용
    * */
    @PatchMapping(path="/{orderId}", consumes="application/json")
    public Order patchOrder(@PathVariable("orderId") Long orderId,
                            @RequestBody Order patch) {

        Order order = repo.findById(orderId).get();
        if (patch.getDeliveryName() != null) {
            order.setDeliveryName(patch.getDeliveryName());
        }
        if (patch.getDeliveryStreet() != null) {
            order.setDeliveryStreet(patch.getDeliveryStreet());
        }
        if (patch.getDeliveryCity() != null) {
            order.setDeliveryCity(patch.getDeliveryCity());
        }
        if (patch.getDeliveryState() != null) {
            order.setDeliveryState(patch.getDeliveryState());
        }
        if (patch.getDeliveryZip() != null) {
            order.setDeliveryZip(patch.getDeliveryState());
        }
        if (patch.getCcNumber() != null) {
            order.setCcNumber(patch.getCcNumber());
        }
        if (patch.getCcExpiration() != null) {
            order.setCcExpiration(patch.getCcExpiration());
        }
        if (patch.getCcCVV() != null) {
            order.setCcCVV(patch.getCcCVV());
        }
        return repo.save(order);
    }

    @DeleteMapping("/{orderId}")
    //  HttpStatus.NO_CONTENT : 204 코드로 반환 값이 없다는 것을 의미
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable("orderId") Long id){
        try {
            repo.deleteById(id);
        }catch (EmptyResultDataAccessException e){}
    }
}
