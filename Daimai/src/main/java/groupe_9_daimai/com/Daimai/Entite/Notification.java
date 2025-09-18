package groupe_9_daimai.com.Daimai.Entite;

import groupe_9_daimai.com.Daimai.Entite.enums.TypeNotifcation;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "Notification")
@Getter
@Setter
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private TypeNotifcation typeNotifcation;

    @Column
    private String contenue;

    @Column
    private LocalDate dateEnvoi;
}
