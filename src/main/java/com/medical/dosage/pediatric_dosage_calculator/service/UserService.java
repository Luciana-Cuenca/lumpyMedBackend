package com.medical.dosage.pediatric_dosage_calculator.service;

import com.medical.dosage.pediatric_dosage_calculator.model.Rol;
import com.medical.dosage.pediatric_dosage_calculator.model.User;
import com.medical.dosage.pediatric_dosage_calculator.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;


@Service //mark this class ass a service component -- marca esta clase como un componente de servicio
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;



    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    //register a new user - registra un nuevo usuario
    public User registerUser(User user){

        //check if the email is already registered - comprueba si el correo ya esta registrado
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("el correo ya esta registrado");
        }

        user.setRol(Rol.USER);

        //encrypt the paswords before save it
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        //save it in the db - lo guarda en la db
        return userRepository.save(user);
        
    }

    //search by username - busca por nombre del usuario
    public Optional <User> findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    //search by email - busca por email
    public boolean existsByEmail(String email){
        return userRepository.existsByEmail(email);
    }

    public boolean loginUser(String username, String password){
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            //compare the password encrypt one - compara la contaseña con la encriptada
            return passwordEncoder.matches(password, user.getPassword());
            
        }

        return false; // the user don't exist - el usuario no exixte

    }

    public User createAdmin(String username, String email, String password) {
    User admin = new User();
    admin.setUsername(username);
    admin.setEmail(email);
    admin.setPassword(passwordEncoder.encode(password));
    admin.setRol(Rol.ADMIN);
    return userRepository.save(admin);
}

}
