package groupe_9_daimai.com.Daimai.DTO;

import jakarta.validation.constraints.NotBlank;

public class LoginRequestDTO {

    @NotBlank(message = "L'email est obligatoire")
    private String email;

    @NotBlank(message = "Le mot de passe est obligatoire")
    private String motDepasse;

    // Getters et Setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getMotDepasse() { return motDepasse; }
    public void setMotDepasse(String motDepasse) { this.motDepasse = motDepasse; }
}