package labtic.services;

import entities.User;
import exceptions.NoUserFound;
import labtic.database.UserRepository;
import exceptions.InvalidInformation;
import exceptions.UserAlreadyRegistered;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public void addUser(String emailAddress, String username, String password) throws UserAlreadyRegistered {
        if (userRepository.findOneByEmail(emailAddress) != null){
            throw new UserAlreadyRegistered("Este usuario ya existe");
        }
        userRepository.save(new User(emailAddress,password));
    }

    public void save(User user){userRepository.save(user);}

    public User findByEmail(String email) throws NoUserFound {
        User user = userRepository.findOneByEmail(email);
        if(user == null){
            throw new NoUserFound(null);
        }
        return user;
    }
}
