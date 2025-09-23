package groupe_9_daimai.com.Daimai.Controller;

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

    // CREATE
    @PostMapping
    public ResponseEntity<Parrain> createParrain(@RequestBody Parrain parrain) {
        return ResponseEntity.ok(parrainService.createParrain(parrain));
    }

    // READ ALL
    @GetMapping
    public ResponseEntity<List<Parrain>> getAllParrains() {
        return ResponseEntity.ok(parrainService.getAllParrains());
    }

    // READ ONE
    @GetMapping("/{id}")
    public ResponseEntity<Parrain> getParrainById(@PathVariable Long id) {
        return parrainService.getParrainById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Parrain> updateParrain(@PathVariable Long id, @RequestBody Parrain parrain) {
        return ResponseEntity.ok(parrainService.updateParrain(id, parrain));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParrain(@PathVariable Long id) {
        parrainService.deleteParrain(id);
        return ResponseEntity.noContent().build();
    }
}
