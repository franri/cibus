package labtic;

import labtic.rmi.BackendServiceImp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import rmi.BackendService;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LoadServerTest {

    @Test
    @Bean
    public BackendService loadServer(@Autowired BackendServiceImp bsImp) throws RemoteException {
        String name = "backend";
        BackendService bs = (BackendService) bsImp;
        BackendService oStub = (BackendService) UnicastRemoteObject.exportObject(bs, 0);
        Registry oRegistry = LocateRegistry.createRegistry(4444);
        oRegistry.rebind(name, oStub);
        return bs;
        //assertTrue
    }
}
