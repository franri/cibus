package labtic;

import entities.Neighbourhood;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.rmi.RemoteException;

@SpringBootApplication
@EntityScan(basePackages = "entities")
public class CibusBackendApplication {

    public static void main(String[] args) throws RemoteException {
        SpringApplication.run(CibusBackendApplication.class, args);
        System.out.println(org.hibernate.Version.getVersionString() + "HOLAAAAA");
    }

}
