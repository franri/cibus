package labtic.services;

import entities.User;
import labtic.database.UserRepository;
import labtic.services.exceptions.InvalidInformation;
import labtic.services.exceptions.UserAlreadyRegistered;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public void addUser(String emailAddress, String username, String password) throws InvalidInformation, UserAlreadyRegistered {
        if ( "".equals(emailAddress) || "".equals(username) || "".equals(password)){
            throw new InvalidInformation("Ingrese correctamente los datos");
        }
        if (userRepository.findOneByEmail(emailAddress) != null){
            throw new UserAlreadyRegistered("Este usuario ya existe");
        }
        userRepository.save(new User(emailAddress, username, password));
    }

    public void save(User user){userRepository.save(user);}
}
