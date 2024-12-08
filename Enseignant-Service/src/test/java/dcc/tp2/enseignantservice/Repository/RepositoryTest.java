package dcc.tp2.enseignantservice.Repository;

import dcc.tp2.enseignantservice.entities.Enseignant;
import dcc.tp2.enseignantservice.repository.EnseignantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class RepositoryTest {

    @Autowired
    private EnseignantRepository enseignantRepository;

    private Enseignant enseignant;

    @BeforeEach
    public void setUp() {
        enseignant = new Enseignant();
        enseignant.setNom("Dupont");
        enseignant.setPrenom("Jean");
        enseignant.setEmail("jean.dupont@example.com");
        enseignant.setPassword("password123");
        enseignantRepository.save(enseignant);
    }

    @Test
    public void testFindEnseignantByEmail() {
        Enseignant found = enseignantRepository.findEnseignantByEmail("jean.dupont@example.com");
        assertThat(found).isNotNull();
        assertThat(found.getEmail()).isEqualTo("jean.dupont@example.com");
    }

    @Test
    public void testFindEnseignantByEmail_NotFound() {
        Enseignant found = enseignantRepository.findEnseignantByEmail("nonexistent@example.com");
        assertThat(found).isNull();
    }
}
