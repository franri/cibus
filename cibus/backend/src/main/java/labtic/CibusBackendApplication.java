package labtic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.rmi.RemoteException;

@SpringBootApplication
@EntityScan(basePackages = "entities")
public class CibusBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(CibusBackendApplication.class, args);
        System.out.println(org.hibernate.Version.getVersionString() + "HOLAAAAA");
    }

}
