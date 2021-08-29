package ecma.ai.lesson6_task2.service;

import ecma.ai.lesson6_task2.entity.User;
import ecma.ai.lesson6_task2.payload.ApiResponse;
import ecma.ai.lesson6_task2.payload.UserDto;
import ecma.ai.lesson6_task2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class UserService {
    @Autowired
    UserRepository userRepository;


    public List<User> getList() {
        return userRepository.findAll();
    }

    public ApiResponse getById(Integer id) {
        Optional<User> byId = userRepository.findById(id);
        return byId.map(user -> new ApiResponse("Successfully", true, user)).orElseGet(() -> new ApiResponse("User not found!", false));
    }

    public ApiResponse addUser(UserDto userDto) {
        Optional<User> byRole = userRepository.findByRole(userDto.getRole().getRoleName());
        if (byRole.isPresent()) return new ApiResponse("Bunday rolli user bor", false);

        User user = new User();
        user.setFullName(userDto.getFullName());
        user.setRole(userDto.getRole());
        userRepository.save(user);
        return new ApiResponse("User added succesfully!!!", true);
    }

    public ApiResponse editUser(Integer id, UserDto userDto) {
        Optional<User> byId = userRepository.findById(id);
        if (!byId.isPresent()) return new ApiResponse("User not found!", false);
        Optional<User> byRole = userRepository.findByRole(userDto.getRole().getRoleName());
        if (byRole.isPresent()) return new ApiResponse("Bunday rolli user bor", false);

        User user = byId.get();
        user.setFullName(userDto.getFullName());
        user.setRole(userDto.getRole());
        userRepository.save(user);
        return new ApiResponse("User edit successfully!!!", true);
    }

    public ApiResponse deleteUser(Integer id) {
        Optional<User> byId = userRepository.findById(id);
        if (!byId.isPresent()) return new ApiResponse("User not found!", false);
        userRepository.deleteById(id);
        return new ApiResponse("User deleted success!", false);
    }
}
