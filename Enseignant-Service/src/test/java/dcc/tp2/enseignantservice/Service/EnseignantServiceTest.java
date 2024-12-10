package dcc.tp2.enseignantservice.Service;

import dcc.tp2.enseignantservice.client.ChercheurRestFeign;
import dcc.tp2.enseignantservice.client.ProjetRestFeign;
import dcc.tp2.enseignantservice.entities.Enseignant;
import dcc.tp2.enseignantservice.repository.EnseignantRepository;
import dcc.tp2.enseignantservice.service.EnseignantService;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class EnseignantServiceTest {

    @InjectMocks
    private EnseignantService enseignantService;
    @Mock
    private EnseignantRepository enseignantRepository;
    @Mock
    private ChercheurRestFeign chercheurRestFeign;
    @Mock
    private ProjetRestFeign projetRestFeign;


    @Test
    void create_Enseignant() {
        Enseignant enseignant = new Enseignant(null, "Saibari", "Maroua", "LA23456", "maroua@mail.com", "123", "informatique", "Enseignant");
        Enseignant Enseignantsaved = new Enseignant(1L, "Saibari", "Maroua", "LA23456", "maroua@mail.com", "123", "informatique", "Enseignant");
        // action
        Mockito.when(enseignantRepository.save(enseignant)).thenReturn(Enseignantsaved);
        // tester
        Enseignant resultat = enseignantService.Create_Enseignant(enseignant);
        // vérifier
        AssertionsForClassTypes.assertThat(resultat).usingRecursiveComparison().isEqualTo(Enseignantsaved);
    }

    @Test
    void getAll_Enseignant() {
        List<Enseignant> enseignantList = List.of(
                new Enseignant(null, "Saibari", "Maroua", "LA23456", "maroua@mail.com", "123", "informatique", "Enseignant"),
                new Enseignant(null, "sab", "wissal", "KB1234", "wissal@mail.com", "123", "informatique", "Enseignant")
        );
        // action
        Mockito.when(enseignantRepository.findAll()).thenReturn(enseignantList);
        List<Enseignant> result = enseignantService.GetAll_Enseignant();
        AssertionsForClassTypes.assertThat(result).usingRecursiveComparison().isEqualTo(enseignantList);
    }

    @Test
    void get_EnseignantByID() {
        Long id = 1L;
        Enseignant enseignant = new Enseignant(1L, "Saibari", "Maroua", "LA23456", "maroua@mail.com", "123", "informatique", "Enseignant");
        Mockito.when(enseignantRepository.findById(id)).thenReturn(Optional.of(enseignant));
        Enseignant result = enseignantService.Get_EnseignantByID(id);
        AssertionsForClassTypes.assertThat(result).isNotNull();
        AssertionsForClassTypes.assertThat(result).usingRecursiveComparison().isEqualTo(enseignant);
    }

    @Test
    void Not_get_EnseignantByID() {
        Long id = 9L;
        Enseignant enseignant = new Enseignant(1L, "Saibari", "Maroua", "LA23456", "maroua@mail.com", "123", "informatique", "Enseignant");
        Mockito.when(enseignantRepository.findById(id)).thenReturn(Optional.empty());
        Enseignant result = enseignantService.Get_EnseignantByID(id);
        AssertionsForClassTypes.assertThat(result).usingRecursiveComparison().isNotEqualTo(enseignant);

    }
    @Test
    void findByEmail() {
        String email = "ali@mail.com";
        Enseignant enseignant = new Enseignant(1L, "Saibari", "Maroua", "LA23456", "maroua@mail.com", "123", "informatique", "Enseignant");
        Mockito.when(enseignantRepository.findEnseignantByEmail(email)).thenReturn(enseignant);
        Enseignant result = enseignantService.FindByEmail(email);
        AssertionsForClassTypes.assertThat(result).isNotNull();
        AssertionsForClassTypes.assertThat(result).usingRecursiveComparison().isEqualTo(enseignant);
    }

    @Test
    void Not_findByEmail() {
        String email = "abc@mail.com";
        Mockito.when(enseignantRepository.findEnseignantByEmail(email)).thenReturn(null);
        Enseignant result = enseignantService.FindByEmail(email);
        AssertionsForClassTypes.assertThat(result).isNull();
    }

    @Test
    void update_Enseignant() {
        Long id = 1L;
        Enseignant enseignant = new Enseignant(1L, "Saibari", "Maroua", "LA23456", "maroua@mail.com", "123", "informatique", "Enseignant");
        Enseignant enseignant_modify = new Enseignant(1L, "Saibari", "AL-Maroua", "LA23456", "maroua@mail.com", "123", "informatique", "Enseignant");

        Mockito.when(enseignantRepository.findById(id)).thenReturn(Optional.of(enseignant));
        Mockito.when(enseignantRepository.save(enseignant)).thenReturn(enseignant_modify);

        Enseignant result = enseignantService.Update_Enseignant(enseignant_modify, id);

        AssertionsForClassTypes.assertThat(result).isNotNull();
        AssertionsForClassTypes.assertThat(result).usingRecursiveComparison().isEqualTo(enseignant_modify);
    }

    @Test
    void delete_Enseignant() {
        Long id = 1L;
        enseignantService.Delete_Enseignant(id);
    }

    @Test
    void statistique() {
        Long id = 1L;
        //action
        Mockito.when(chercheurRestFeign.nb_chercheur_Enseignant(id)).thenReturn(2L);
        Mockito.when(projetRestFeign.nb_Projet_Enseignant(id)).thenReturn(2L);

        Map<String, Long> Statistiques = new HashMap<>();
        Statistiques.put("nombre de projet",2L);
        Statistiques.put("nombre de chercheur",2L);

        Map<String, Long> result = enseignantService.statistique(id);
        AssertionsForClassTypes.assertThat(result).usingRecursiveComparison().isEqualTo(Statistiques);
    }
}






















/*
package dcc.tp2.enseignantservice.Service;


import dcc.tp2.enseignantservice.client.ChercheurRestFeign;
import dcc.tp2.enseignantservice.client.ProjetRestFeign;
import dcc.tp2.enseignantservice.entities.Enseignant;
import dcc.tp2.enseignantservice.repository.EnseignantRepository;
import dcc.tp2.enseignantservice.service.EnseignantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
        import static org.mockito.Mockito.*;

public class ServiceTest {

    private EnseignantRepository enseignantRepository;
    private ChercheurRestFeign chercheurRestFeign;
    private ProjetRestFeign projetRestFeign;
    private EnseignantService enseignantService;

    @BeforeEach
    void setUp() {
        enseignantRepository = mock(EnseignantRepository.class);
        chercheurRestFeign = mock(ChercheurRestFeign.class);
        projetRestFeign = mock(ProjetRestFeign.class);
        enseignantService = new EnseignantService(enseignantRepository, chercheurRestFeign, projetRestFeign);
    }

    @Test
    void testCreateEnseignant() {
        Enseignant enseignant = new Enseignant(null, "John", "Doe", "CNE123", "john.doe@example.com", "password", "Mathématiques", "ROLE_USER");
        when(enseignantRepository.save(enseignant)).thenReturn(enseignant);

        Enseignant result = enseignantService.Create_Enseignant(enseignant);

        assertNotNull(result);
        assertEquals("John", result.getNom());
        verify(enseignantRepository, times(1)).save(enseignant);
    }

    @Test
    void testGetAllEnseignant() {
        Enseignant enseignant1 = new Enseignant(1L, "John", "Doe", "CNE123", "john.doe@example.com", "password", "Mathématiques", "ROLE_USER");
        Enseignant enseignant2 = new Enseignant(2L, "Jane", "Smith", "CNE456", "jane.smith@example.com", "password123", "Physique", "ROLE_ADMIN");

        when(enseignantRepository.findAll()).thenReturn(Arrays.asList(enseignant1, enseignant2));

        List<Enseignant> result = enseignantService.GetAll_Enseignant();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(enseignantRepository, times(1)).findAll();
    }

    @Test
    void testGetEnseignantByID() {
        Enseignant enseignant = new Enseignant(1L, "John", "Doe", "CNE123", "john.doe@example.com", "password", "Mathématiques", "ROLE_USER");
        when(enseignantRepository.findById(1L)).thenReturn(Optional.of(enseignant));

        Enseignant result = enseignantService.Get_EnseignantByID(1L);

        assertNotNull(result);
        assertEquals("John", result.getNom());
        verify(enseignantRepository, times(1)).findById(1L);
    }

    @Test
    void testFindByEmail() {
        Enseignant enseignant = new Enseignant(1L, "John", "Doe", "CNE123", "john.doe@example.com", "password", "Mathématiques", "ROLE_USER");
        when(enseignantRepository.findEnseignantByEmail("john.doe@example.com")).thenReturn(enseignant);

        Enseignant result = enseignantService.FindByEmail("john.doe@example.com");

        assertNotNull(result);
        assertEquals("John", result.getNom());
        verify(enseignantRepository, times(1)).findEnseignantByEmail("john.doe@example.com");
    }

    @Test
    void testUpdateEnseignant() {
        Enseignant existingEnseignant = new Enseignant(1L, "John", "Doe", "CNE123", "john.doe@example.com", "password", "Mathématiques", "ROLE_USER");
        Enseignant updatedEnseignant = new Enseignant(null, "Jane", "Doe", "CNE456", "jane.doe@example.com", "newpassword", "Informatique", "ROLE_ADMIN");

        when(enseignantRepository.findById(1L)).thenReturn(Optional.of(existingEnseignant));
        when(enseignantRepository.save(existingEnseignant)).thenReturn(existingEnseignant);

        Enseignant result = enseignantService.Update_Enseignant(updatedEnseignant, 1L);

        assertNotNull(result);
        assertEquals("Jane", result.getNom());
        assertEquals("jane.doe@example.com", result.getEmail());
        verify(enseignantRepository, times(1)).save(existingEnseignant);
    }

    @Test
    void testDeleteEnseignant() {
        doNothing().when(enseignantRepository).deleteById(1L);

        enseignantService.Delete_Enseignant(1L);

        verify(enseignantRepository, times(1)).deleteById(1L);
    }

    @Test
    void testStatistique() {
        when(chercheurRestFeign.nb_chercheur_Enseignant(1L)).thenReturn(5L);
        when(projetRestFeign.nb_Projet_Enseignant(1L)).thenReturn(3L);

        Map<String, Long> stats = enseignantService.statistique(1L);

        assertNotNull(stats);
        assertEquals(5L, stats.get("nombre de chercheur"));
        assertEquals(3L, stats.get("nombre de projet"));
        verify(chercheurRestFeign, times(1)).nb_chercheur_Enseignant(1L);
        verify(projetRestFeign, times(1)).nb_Projet_Enseignant(1L);
    }

}
*/