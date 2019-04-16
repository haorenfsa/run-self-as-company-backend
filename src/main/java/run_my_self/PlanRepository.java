package run_my_self;

import org.springframework.data.repository.CrudRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface PlanRepository extends CrudRepository<Plan, Integer> {
    Iterable<Plan> findByDate(String date);
}
