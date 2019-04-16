package run_my_self;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import run_my_self.Response;

@RestController
public class PlanController {
    @Autowired
    private PlanRepository planRepository;
    private final AtomicLong counter = new AtomicLong();

    @PostMapping("/plans")
    public @ResponseBody Response post(@RequestBody Plan plan) {
        return new Response(planRepository.save(plan));
    }

    @GetMapping("/plans")
    public @ResponseBody Response get(@RequestParam(required = false, name = "date") String date) {
        if (date == null) {
            return new Response(planRepository.findAll());
        }
        return new Response(planRepository.findByDate(date));
    }

    @PutMapping("/plans/{id}")
    public Response patch(@PathVariable("id") Integer id, @RequestBody Plan plan) {
        plan.setId(id);
        return new Response(planRepository.save(plan));
    }

    @DeleteMapping("/plans/{id}")
    public Response delete(@PathVariable("id") Integer id) {
        planRepository.deleteById(id);
        return new Response("");
    }
}
