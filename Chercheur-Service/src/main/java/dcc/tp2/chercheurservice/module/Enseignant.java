package dcc.tp2.chercheurservice.module;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Enseignant {
    private Long id;
    private String nom;
    private String prenom;
    private String cne;
    private String email;
    //private String password; pas logique
    private String thematique;

    public Enseignant(long l, String enseignant, String prenom) {
    }

    public Enseignant(Long l, String john, String doe, String mail) {
    }
    //private String role;   pas logique

}
