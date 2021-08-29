package ecma.ai.lesson6_task2.controller;

import ecma.ai.lesson6_task2.payload.UserDto;
import ecma.ai.lesson6_task2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@PreAuthorize(value = "hasAnyRole('DIRECTOR','MANAGER')")
@RestController
@RequestMapping("api/user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/{id}")
    public HttpEntity<?> getById(@PathVariable Integer id) {
        return ResponseEntity.ok().body(userService.getById(id));
    }

    @PostMapping
    public HttpEntity<?> addUser(@Valid @RequestBody UserDto userDto) {
        return ResponseEntity.ok().body(userService.addUser(userDto));
    }

    @PutMapping("/{id}")
    public HttpEntity<?> editUser(@Valid @RequestBody UserDto us, @PathVariable Integer id) {
        return ResponseEntity.ok().body(userService.editUser(id, us));
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteUser(@PathVariable Integer id) {
        return ResponseEntity.ok().body(userService.deleteUser(id));
    }

}
