package org.example.userservice.Controlers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.userservice.Dto.EmailChangeDto;
import org.example.userservice.Dto.UserDto;
import org.example.userservice.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserDto> findAll() {
        return userService.findAll();
    }

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@Valid @RequestBody UserDto userDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errors);
        }
        return userService.addUser(userDto);
    }

    @DeleteMapping("/delete/{email}")
    public ResponseEntity<?> deleteUser(@PathVariable("email") String email) {
        return userService.deleteUser(email);
    }

    @PatchMapping("/{id}/change-email")
    public ResponseEntity<?> changeEmail(
            @PathVariable("id") Integer userId,
            @Valid @RequestBody EmailChangeDto emailChangeDto,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errors);
        }
        return userService.changeEmail(userId, emailChangeDto);
    }

    @GetMapping("{email}")
    public ResponseEntity<?> GetId(@PathVariable("email") String email) {
        return userService.findByEmail(email);
    }

    @GetMapping("/id/{id}")
    public UserDto getUserById(@PathVariable("id") Integer id) {
        return userService.findById(id);
    }
}
