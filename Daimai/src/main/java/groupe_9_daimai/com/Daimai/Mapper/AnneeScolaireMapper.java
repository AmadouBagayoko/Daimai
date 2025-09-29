package groupe_9_daimai.com.Daimai.Mapper;

import groupe_9_daimai.com.Daimai.DTO.AnneeScolaireRequestDTO;
import groupe_9_daimai.com.Daimai.DTO.AnneeScolaireResponseDTO;
import groupe_9_daimai.com.Daimai.Entite.AnneeScolaire;
import groupe_9_daimai.com.Daimai.Entite.Association;
import org.springframework.stereotype.Component;

@Component
public class AnneeScolaireMapper {

    public AnneeScolaireResponseDTO toResponseDTO(AnneeScolaire anneeScolaire) {
        if (anneeScolaire == null) {
            return null;
        }

        return new AnneeScolaireResponseDTO(
                anneeScolaire.getId(),
                anneeScolaire.getLibelle(),
                anneeScolaire.getDateDebut(),
                anneeScolaire.getDateFin(),
                anneeScolaire.getAssociation().getId(),
                anneeScolaire.getAssociation().getNom(),
                anneeScolaire.isActive()
        );
    }

    public AnneeScolaire toEntity(AnneeScolaireRequestDTO anneeScolaireDTO, Association association) {
        if (anneeScolaireDTO == null) {
            return null;
        }

        AnneeScolaire anneeScolaire = new AnneeScolaire();
        anneeScolaire.setLibelle(anneeScolaireDTO.getLibelle());
        anneeScolaire.setDateDebut(anneeScolaireDTO.getDateDebut());
        anneeScolaire.setDateFin(anneeScolaireDTO.getDateFin());
        anneeScolaire.setAssociation(association);
        anneeScolaire.setActive(true);

        return anneeScolaire;
    }
}