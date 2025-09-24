package groupe_9_daimai.com.Daimai.Controller;

import groupe_9_daimai.com.Daimai.DTO.ParrainageDTO;
import groupe_9_daimai.com.Daimai.Entite.Parrain;
import groupe_9_daimai.com.Daimai.Service.ParrainService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parrains")
public class ParrainController {

    private final ParrainService parrainService;

    public ParrainController(ParrainService parrainService) {
        this.parrainService = parrainService;
    }

    // Parrainer un enfant
    @PostMapping("/parrainer")
    public ResponseEntity<Parrain> parrainerEnfant(@RequestBody ParrainageDTO dto) {
        return ResponseEntity.ok(parrainService.parrainerEnfant(dto));
    }

    // CRUD déjà existants...
    @PostMapping
    public ResponseEntity<Parrain> createParrain(@RequestBody Parrain parrain) {
        return ResponseEntity.ok(parrainService.createParrain(parrain));
    }

    @GetMapping
    public ResponseEntity<List<Parrain>> getAllParrains() {
        return ResponseEntity.ok(parrainService.getAllParrains());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Parrain> getParrainById(@PathVariable Long id) {
        return parrainService.getParrainById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Parrain> updateParrain(@PathVariable Long id, @RequestBody Parrain parrain) {
        return ResponseEntity.ok(parrainService.updateParrain(id, parrain));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParrain(@PathVariable Long id) {
        parrainService.deleteParrain(id);
        return ResponseEntity.noContent().build();
    }
}
