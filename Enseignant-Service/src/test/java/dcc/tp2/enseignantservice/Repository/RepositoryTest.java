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
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class RepositoryTest {

    @Autowired
    private EnseignantRepository enseignantRepository;

    @BeforeEach
    void setUp() {
        enseignantRepository.save(new Enseignant(null,"wissal","sab","la1234","wissal@mail.com","wissal123","informatique","Enseignant"));
        enseignantRepository.save(new Enseignant(null,"maroua","saibari","LB2244","maroua@mail.com","maroua123","informatique","Enseignant"));
    }


    @Test
    public void souldfindEnseignantByEmail(){
        String email = "wissal@mail.com";
        Enseignant enseignant = new Enseignant(null,"wissal","sab","la1234","wissal@mail.com","wissal123","informatique","Enseignant");
        Enseignant result = enseignantRepository.findEnseignantByEmail(email);
        AssertionsForClassTypes.assertThat(result).isNotNull();
    }

    @Test
    public void souldNotfindEnseignantByEmail(){
        String email = "xy@z.com";
        Enseignant result = enseignantRepository.findEnseignantByEmail(email);
        AssertionsForClassTypes.assertThat(result).isNull();
    }

}