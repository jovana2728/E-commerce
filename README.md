PDS – mikroservisi (Eureka, API Gateway, Users, Orders)

Korišćeno:
Java 17  
Spring Boot 3.5.x  
Spring Cloud 2025.0.0  
Maven  

Servisi se pokreću sledećim redosledom:  
discovery-service → users-service → products-service → orders-service → api-gateway  

Moguće je pristupiti Eureka dashboard-u na  
http://localhost:8761 — gde se prikazuju svi registrovani servisi.

Dodatno, moguće je slati zahteve preko Swagger UI-ja za users- i orders-service.

Centralna pristupna tačka sistema je preko API Gateway-a:  
http://localhost:8080/api/**  

Praćenje CircuitBreaker stanja moguće je putem Actuatora na:  
http://localhost:8082/actuator/circuitbreakers  

Portovi:
- discovery-service : 8761  
- api-gateway : 8080  
- users-service : 8081  
- orders-service : 8082
