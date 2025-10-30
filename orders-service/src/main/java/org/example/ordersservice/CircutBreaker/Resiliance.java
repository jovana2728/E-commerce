package org.example.ordersservice.CircutBreaker;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.ordersservice.Dto.UserDto;
import org.example.ordersservice.Feign.UserFeign;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class Resiliance {

    private final UserFeign userFeign;

    // --- FEIGN poziv po emailu (vraca ResponseEntity)
    @Retry(name = "userServiceRetry")
    @CircuitBreaker(name = "userServiceCB", fallbackMethod = "getUserFallback")
    public ResponseEntity<?> fetchUserByEmailOrThrow(String email) {
        log.info("Poziv user-service-a (Feign) email={}", email);
        return userFeign.GetId(email);
    }

    public ResponseEntity<?> getUserFallback(String email, Throwable t) {
        log.warn("CB fallback pri trazenju korisnika sa email={} cause={}", email, t.toString());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("Users service unavailable - fallback for email=" + email);
    }

    // --- FEIGN poziv po ID (vraca ResponseEntity)
    @Retry(name = "userServiceRetry")
    @CircuitBreaker(name = "userServiceCB", fallbackMethod = "getUserByIdFallback")
    public ResponseEntity<?> fetchUserByIdOrThrow(Integer id) {
        log.info("Poziv user-service-a (Feign) id={}", id);
        UserDto user = userFeign.GetById(id);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("User sa ID " + id + " ne postoji.");
        }
        return ResponseEntity.ok(user);
    }

    public ResponseEntity<?> getUserByIdFallback(Integer id, Throwable t) {
        log.warn("CB fallback pri trazenju korisnika id={} cause={}", id, t.toString());
        UserDto fallbackUser = new UserDto();
        fallbackUser.setFirstName("Unknown User (CB Fallback)");
        fallbackUser.setLastName("Unknown User (CB Fallback)");
        fallbackUser.setEmail("unknown@example.com");
        fallbackUser.setPhone("Unknown Phone");
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(fallbackUser);
    }
}
