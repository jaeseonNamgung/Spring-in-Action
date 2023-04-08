package sia.tacocloud.tacos.web.api;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sia.tacocloud.tacos.Order;
import sia.tacocloud.tacos.Taco;
import sia.tacocloud.tacos.data.TacoRepository;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
// produces : 서버가 클라이언트에게 반환하는 타입
@RequestMapping(path = "/design", produces = "application/json")
// 서로 다른 도메인간의 요청을 허용
@CrossOrigin(origins = "*")
public class DesignTacoApiController {
    private final TacoRepository tacoRepo;


    @GetMapping("/recent")
    public Iterable<Taco> recentTaco(){
        PageRequest page = PageRequest.of(
                0, 12, Sort.by("createdAt").descending()
        );
        return tacoRepo.findAll(page).getContent();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Taco> tacoById(@PathVariable("id") Long id){
        Optional<Taco> optTaco = tacoRepo.findById(id);
        return optTaco.map(taco -> new ResponseEntity<>(taco, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    // consumes : 클라이언트가 서버에게 반환하는 타입
    @PostMapping(consumes = "application/json")
    // 요청이 성공적이서 요청의 결과로 리소스가 생성되면 HTTP 201(CREATED) 상태 코드가 
    // 클라이언트에게 전달
    // @ResponseStatus(HttpStatus.CREATED)를 사용 하지 않으면 성공 시 200 코드가 나가므로
    // @ResponseStatus(HttpStatus.CREATED)를 사용 하는게 더 상세한 설명을 알려줄 수 있다.
    @ResponseStatus(HttpStatus.CREATED)
    public Taco postTaco(@RequestBody Taco taco){
        return tacoRepo.save(taco);
    }


}
