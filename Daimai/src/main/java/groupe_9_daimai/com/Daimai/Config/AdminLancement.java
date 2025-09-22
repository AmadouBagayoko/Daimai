package groupe_9_daimai.com.Daimai.Config;



import groupe_9_daimai.com.Daimai.Entite.Administrateur;
import groupe_9_daimai.com.Daimai.Repository.AdministrateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.boot.CommandLineRunner;

@Configuration
public class AdminLancement implements CommandLineRunner {

    @Autowired
    private AdministrateurRepository administrateurRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Vérifier si un admin existe déjà
        if (administrateurRepository.findAll().isEmpty()) {
            Administrateur admin = new Administrateur();
            admin.setNom("Dolo");
            admin.setPrenom("Oumar");
            admin.setEmail("dolooumar60@gmail.com");
            admin.setTelephone("93761029");
            admin.setMotDepasse(passwordEncoder.encode("dolo")); // mot de passe par défaut
            administrateurRepository.save(admin);

            System.out.println("Administrateur par défaut créé :" + admin.getEmail() + " " + admin.getNom() + " " + admin.getTelephone());
        }
    }
}

