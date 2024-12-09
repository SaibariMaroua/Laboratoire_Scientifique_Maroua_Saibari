package dcc.tp2.chercheurservice.Repository;

import dcc.tp2.chercheurservice.entities.Chercheur;
import dcc.tp2.chercheurservice.repository.ChercheurRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.junit.jupiter.api.Assertions.*;

        import java.util.Optional;

@DataJpaTest
public class ChercheurRepositoryTest {

    @Autowired
    private ChercheurRepository chercheurRepository;

    private Chercheur chercheur;

    @BeforeEach
    public void setUp() {
        // Préparer un chercheur de test
        chercheur = new Chercheur();
        chercheur.setNom("Dupont");
        chercheur.setPrenom("Jean");
        chercheur.setEmail("jean.dupont@example.com");
        chercheur.setPassword("password123");
        chercheur.setTel("0123456789");
        chercheur.setRole("Chercheur");
        chercheur.setId_enseignant(1L);
        chercheur.setId_projet(2L);
    }

    @Test
    public void testSaveChercheur() {
        // Sauvegarder un chercheur et vérifier qu'il est bien persisté
        Chercheur savedChercheur = chercheurRepository.save(chercheur);
        assertNotNull(savedChercheur.getId());  // Vérifier que l'ID a été généré
        assertEquals(chercheur.getNom(), savedChercheur.getNom());
    }

    @Test
    public void testFindByEmail() {
        // Sauvegarder le chercheur
        chercheurRepository.save(chercheur);

        // Rechercher par email
        Optional<Chercheur> foundChercheur = Optional.ofNullable(chercheurRepository.findChercheurByEmail(chercheur.getEmail()));
        assertTrue(foundChercheur.isPresent());
        assertEquals(chercheur.getEmail(), foundChercheur.get().getEmail());
    }

    @Test
    public void testDeleteChercheur() {
        // Sauvegarder le chercheur
        Chercheur savedChercheur = chercheurRepository.save(chercheur);

        // Supprimer le chercheur
        chercheurRepository.delete(savedChercheur);

        // Vérifier qu'il a été supprimé
        Optional<Chercheur> foundChercheur = chercheurRepository.findById(savedChercheur.getId());
        assertFalse(foundChercheur.isPresent());
    }

    @Test
    public void testFindById() {
        // Sauvegarder un chercheur
        Chercheur savedChercheur = chercheurRepository.save(chercheur);

        // Trouver le chercheur par ID
        Optional<Chercheur> foundChercheur = chercheurRepository.findById(savedChercheur.getId());
        assertTrue(foundChercheur.isPresent());
        assertEquals(savedChercheur.getId(), foundChercheur.get().getId());
    }
}
