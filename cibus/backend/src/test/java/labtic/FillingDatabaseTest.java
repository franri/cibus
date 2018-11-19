package labtic;


import entities.Consumer;
import entities.Food;
import entities.Neighbourhood;
import entities.Restaurant;
import labtic.services.*;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalTime;
import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FillingDatabaseTest {

    @Autowired
    AdminService as;

    @Autowired
    FoodService fs;

    @Autowired
    NeighbourhoodService ns;

    @Autowired
    RestaurantService rs;

    @Autowired
    ConsumerService cs;

    @Before
    public void filling() throws IOException, URISyntaxException {

        Food f1 = new Food("Papas");
        Food f2 = new Food("Asado");
        Food f3 = new Food("Hamburguesa");
        Food f4 = new Food("Sushi");
        Food f5 = new Food("Pasta");
        Food f6 = new Food("Ensalada");
        fs.save(f1);
        fs.save(f2);
        fs.save(f3);
        fs.save(f4);
        fs.save(f5);
        fs.save(f6);

        Neighbourhood n1 = new Neighbourhood("Ciudad Vieja");
        Neighbourhood n2 = new Neighbourhood("Centro");
        Neighbourhood n3 = new Neighbourhood("Pocitos");
        Neighbourhood n4 = new Neighbourhood("Cordón");
        Neighbourhood n5 = new Neighbourhood("Buceo");
        Neighbourhood n6 = new Neighbourhood("Carrasco");
        Neighbourhood n7 = new Neighbourhood("Malvín");
        Neighbourhood n8 = new Neighbourhood("Aguada");
        ns.save(n1);
        ns.save(n2);
        ns.save(n3);
        ns.save(n4);
        ns.save(n5);
        ns.save(n6);
        ns.save(n7);
        ns.save(n8);

        LocalTime oh = LocalTime.of(8,0);
        LocalTime ch = LocalTime.of(22,0);

        byte[] la = getBytesOfImage(new File(getClass().getClassLoader().getResource("letterIcons/A.png").getFile()));
        byte[] lb = getBytesOfImage(new File(getClass().getClassLoader().getResource("letterIcons/B.png").getFile()));
        byte[] lc = getBytesOfImage(new File(getClass().getClassLoader().getResource("letterIcons/C.png").getFile()));
        byte[] ld = getBytesOfImage(new File(getClass().getClassLoader().getResource("letterIcons/D.png").getFile()));
        byte[] le = getBytesOfImage(new File(getClass().getClassLoader().getResource("letterIcons/E.png").getFile()));
        byte[] lf = getBytesOfImage(new File(getClass().getClassLoader().getResource("letterIcons/F.png").getFile()));

        Restaurant ra = new Restaurant();
        ra.setName("RA");
        ra.setEmail("ra");
        ra.setRut("1");
        ra.setPassword("ra");
        ra.setAddress("adress");
        ra.setPhoneNumber(27008989L);
        ra.setNeighbourhood(n1);
        ra.getFoods().addAll(Arrays.asList(f1, f2, f3, f4, f5, f6));
        ra.setMaxCapacity(95L);
        ra.setFreePlaces(95L);
        ra.setTableForTwo(30L);
        ra.setTableForFour(20L);
        ra.setOpeningHour(oh);
        ra.setClosingHour(ch);
        ra.setProfilePicture(la);
        ra.setCanBeShown(true);


        Restaurant rb = new Restaurant();
        rb.setName("RB");
        rb.setEmail("rb");
        rb.setRut("2");
        rb.setPassword("rb");
        rb.setAddress("adress");
        rb.setPhoneNumber(27008989L);
        rb.setNeighbourhood(n1);
        rb.getFoods().addAll(Arrays.asList(f1, f2, f3));
        rb.setMaxCapacity(95L);
        rb.setFreePlaces(95L);
        rb.setTableForTwo(30L);
        rb.setTableForFour(20L);
        rb.setOpeningHour(oh);
        rb.setClosingHour(ch);
        rb.setProfilePicture(lb);
        rb.setCanBeShown(true);

        Restaurant rc = new Restaurant();
        rc.setName("RC");
        rc.setEmail("rc");
        rc.setRut("3");
        rc.setPassword("rc");
        rc.setAddress("adress");
        rc.setPhoneNumber(27008989L);
        rc.setNeighbourhood(n1);
        rc.getFoods().addAll(Arrays.asList(f4, f5, f6));
        rc.setMaxCapacity(95L);
        rc.setFreePlaces(95L);
        rc.setTableForTwo(30L);
        rc.setTableForFour(20L);
        rc.setOpeningHour(oh);
        rc.setClosingHour(ch);
        rc.setProfilePicture(lc);
        rc.setCanBeShown(true);

        Restaurant rd = new Restaurant();
        rd.setName("RD");
        rd.setEmail("rd");
        rd.setRut("4");
        rd.setPassword("rd");
        rd.setAddress("adress");
        rd.setPhoneNumber(27008989L);
        rd.setNeighbourhood(n2);
        rd.getFoods().addAll(Arrays.asList(f3, f4, f5));
        rd.setMaxCapacity(95L);
        rd.setFreePlaces(95L);
        rd.setTableForTwo(30L);
        rd.setTableForFour(20L);
        rd.setOpeningHour(oh);
        rd.setClosingHour(ch);
        rd.setProfilePicture(ld);
        rd.setCanBeShown(true);

        Restaurant re = new Restaurant();
        re.setName("RE");
        re.setEmail("re");
        re.setRut("5");
        re.setPassword("re");

        Restaurant rf = new Restaurant();
        rf.setName("RF");
        rf.setEmail("rf");
        rf.setRut("6");
        rf.setPassword("rf");

        rs.save(ra);
        rs.save(rb);
        rs.save(rc);
        rs.save(rd);
        rs.save(re);
        rs.save(rf);

        Consumer ca = new Consumer();
        ca.setFirstName("Ca");
        ca.setLastName("Ca");
        ca.setPhoneNumber(1L);
        ca.setEmail("ca");
        ca.setPassword("ca");

        Consumer cb = new Consumer();
        cb.setFirstName("Cb");
        cb.setLastName("Cb");
        cb.setPhoneNumber(1L);
        cb.setEmail("cb");
        cb.setPassword("cb");

        Consumer cc = new Consumer();
        cc.setFirstName("Cc");
        cc.setLastName("Cc");
        cc.setPhoneNumber(1L);
        cc.setEmail("cc");
        cc.setPassword("cc");

        Consumer cd = new Consumer();
        cd.setFirstName("Cd");
        cd.setLastName("Cd");
        cd.setPhoneNumber(1L);
        cd.setEmail("cd");
        cd.setPassword("cd");

        cs.save(ca);
        cs.save(cb);
        cs.save(cc);
        cs.save(cd);

    }

    @Ignore
    private byte[] getBytesOfImage(File file) throws IOException, URISyntaxException {
        BufferedImage bufferedPic = ImageIO.read(file);
        ByteArrayOutputStream picStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedPic, "jpg", picStream);
        return picStream.toByteArray();
    }

    @Test
    public void load(){}

}
