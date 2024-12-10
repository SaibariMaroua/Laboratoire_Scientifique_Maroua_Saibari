package dcc.tp2.enseignantservice.Repository;

import dcc.tp2.enseignantservice.entities.Enseignant;
import dcc.tp2.enseignantservice.repository.EnseignantRepository;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

class EnseignantRepositoryTest {

    @Autowired
    private EnseignantRepository enseignantRepository;

    @BeforeEach
    void setUp() {
        enseignantRepository.save(new Enseignant(null, "saibari", "maroua", "LA1234", "maroua@mail.com", "123", "informatique", "Enseignant"));
        enseignantRepository.save(new Enseignant(null, "sab", "wissal", "LB1234", "wissal@mail.com", "123", "informatique", "Enseignant"));
    }

    @Test
    void findEnseignantByEmail() {
        String email = "maroua@mail.com";
        Enseignant enseignant = new Enseignant(null, "saibari", "maroua", "LA1234", "maroua@mail.com", "123", "informatique", "Enseignant");

        Enseignant result = enseignantRepository.findEnseignantByEmail(email);

        AssertionsForClassTypes.assertThat(result).isNotNull();
        AssertionsForClassTypes.assertThat(result).usingRecursiveComparison().ignoringFields("id").isEqualTo(enseignant);

    }
    @Test
    void Not_findEnseignantByEmail() {
        String email = "abc@mail.com";
        Enseignant result = enseignantRepository.findEnseignantByEmail(email);
        AssertionsForClassTypes.assertThat(result).isNull();

    }

}








   /* @Autowired
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
}*/