package run_my_self;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class PlanController {
    @Autowired
    private PlanRepository planRepository;
    private final AtomicLong counter = new AtomicLong();

    @PostMapping("/plans")
    public Plan post(@RequestBody Plan plan) {
        planRepository.save(plan);
        return plan;
    }

    @DeleteMapping("/plans/{id}")
    public String delete(@PathVariable("id") Integer id) {
        planRepository.deleteById(id);
        return "success";
    }
}
