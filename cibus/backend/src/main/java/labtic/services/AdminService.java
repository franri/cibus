package labtic.services;


import entities.Admin;
import labtic.database.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    AdminRepository adminRepository;

    public void save(Admin admin){
        adminRepository.save(admin);
    }
}
