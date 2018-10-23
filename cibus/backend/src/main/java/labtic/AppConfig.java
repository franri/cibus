package labtic;

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

    @Bean
    public BackendService loadServer(@Autowired BackendServiceImp bsImp) throws RemoteException {
        String name = "backend";
        BackendService bs = bsImp;
        BackendService oStub = (BackendService) UnicastRemoteObject.exportObject(bs, 0);
        Registry oRegistry = LocateRegistry.createRegistry(4444);
        oRegistry.rebind(name, oStub);
        System.out.println("Server loaded");
        return bs;
    }
}
