package dcc.tp2.chercheurservice.entities;

import dcc.tp2.chercheurservice.module.Enseignant;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Chercheur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private String nom;
    private String prenom;
    private String email;
    private String password;
    private String tel;
    private String role;

    private Long id_enseignant;
    private Long id_projet;


    // juste pour récuperation de l'enseignant encadrée.
    @Transient
    private Enseignant enseignant;


    public Chercheur(Long id, String nom, String prenom, String tel, String email, String password, String role, Long id_enseignant) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.tel = tel;
        this.email = email;
        this.password = password;
        this.role = role;
        this.id_enseignant = id_enseignant;
    }
}
