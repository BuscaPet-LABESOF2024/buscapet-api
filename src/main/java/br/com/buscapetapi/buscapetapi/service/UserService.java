package br.com.buscapetapi.buscapetapi.service;

import br.com.buscapetapi.buscapetapi.model.User;
import br.com.buscapetapi.buscapetapi.repository.UserRepository;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User createUser(User userInput){
        userInput.setCreatedAt(LocalDate.now());
        userInput.setUpdatedAt(LocalDate.now());
        return userRepository.save(userInput);
    }

    public User findById(Long id){
        Optional<User> existingUser = userRepository.findById(id);

        if (existingUser.isPresent()){
            User user = existingUser.get();
            return user;
        }
        return null;
    }

    public User updateUser(User userInput){
        Optional<User> existingUser = userRepository.findById(userInput.getId());

        if (existingUser.isPresent()){
            User user = existingUser.get();

            user.setName(userInput.getName());
            user.setPassword(userInput.getPassword());
            user.setAddress(userInput.getAddress());
            user.setPhone(userInput.getPhone());
            user.setCreatedAt(userInput.getCreatedAt());
            user.setUpdatedAt(LocalDate.now());

        }
        return userRepository.save(userInput);
    }
}
