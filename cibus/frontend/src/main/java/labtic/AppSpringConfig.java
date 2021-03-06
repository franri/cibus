package labtic;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rmi.BackendService;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


@Configuration
public class AppSpringConfig {

    @Value("${server.port}")
    Integer port;

    @Value("${server.host}")
    String host;

    @Bean
    public BackendService backendServiceClient() throws RemoteException, NotBoundException {

        String sObjectService = "backend";


        Registry oRegistry = LocateRegistry.getRegistry(host, port);
        BackendService bs = (BackendService) oRegistry.lookup(sObjectService);
//        List<Food> lista = bs.getListaComidas();
//        for(Food food : lista){
//            System.out.println(food.getFoodName());
//        }
        System.out.println("Bien cargado");
        return bs;
//        return new BackendServiceImpl();
    }

//    @Bean
//    public Stage stage(){
//        return new Stage();
//    }


}
