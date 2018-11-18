package labtic;

import org.springframework.beans.factory.annotation.Value;
import rmi.BackendService;
import labtic.rmi.BackendServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

@Configuration
public class AppConfig {

    @Value("${server.port}")
    Integer port;

    @Bean
    public BackendService loadServer(@Autowired BackendServiceImp bsImp) throws RemoteException {
        String name = "backend";
        BackendService bs = bsImp;
        BackendService oStub = (BackendService) UnicastRemoteObject.exportObject(bs, 0);
        Registry oRegistry = LocateRegistry.createRegistry(port);
        oRegistry.rebind(name, oStub);
        System.out.println("Server loaded");
        return bs;
    }
}
