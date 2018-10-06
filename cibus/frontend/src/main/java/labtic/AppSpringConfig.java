package labtic;


import entities.Neighbourhood;
import javafx.stage.Stage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rmi.BackendService;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;


@Configuration
public class AppSpringConfig {

    @Bean
    public BackendService backendServiceClient() throws RemoteException, NotBoundException {

        String sObjectService = "backend";

        Registry oRegistry = LocateRegistry.getRegistry(4444);
        BackendService bs = (BackendService) oRegistry.lookup(sObjectService);
        List<Neighbourhood> lista = bs.getListaBarrios();
        for(Neighbourhood barrio : lista){
            System.out.println(barrio.getName());
        }
        System.out.println("Bien cargado");
        return bs;
//        return new BackendServiceImpl();
    }

    @Bean
    public Stage stage(){
        return new Stage();
    }

}
