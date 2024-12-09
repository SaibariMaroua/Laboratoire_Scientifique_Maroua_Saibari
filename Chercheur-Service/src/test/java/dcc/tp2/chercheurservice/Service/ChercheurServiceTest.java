package dcc.tp2.chercheurservice.Service;

import dcc.tp2.chercheurservice.client.EnseignantRestFeign;
import dcc.tp2.chercheurservice.entities.Chercheur;
import dcc.tp2.chercheurservice.repository.ChercheurRepository;
import dcc.tp2.chercheurservice.service.ChercheurService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ChercheurServiceTest {

    private ChercheurRepository chercheurRepository;
    private EnseignantRestFeign enseignantRestFeign;
    private ChercheurService chercheurService;

    @BeforeEach
    void setUp() {
        chercheurRepository = mock(ChercheurRepository.class);
        enseignantRestFeign = mock(EnseignantRestFeign.class);
        chercheurService = new ChercheurService(chercheurRepository, enseignantRestFeign);
    }

    @Test
    void testCreateChercheur() {
        Chercheur chercheur = new Chercheur(null, "Alice", "Johnson", "123456", "alice.johnson@example.com", "password", "ROLE_USER", 1L);
        when(chercheurRepository.save(chercheur)).thenReturn(chercheur);

        Chercheur result = chercheurService.Create_Chercheur(chercheur);

        assertNotNull(result);
        assertEquals("Alice", result.getNom());
        verify(chercheurRepository, times(1)).save(chercheur);
    }

    @Test
    void testGetAllChercheur() {
        Chercheur chercheur1 = new Chercheur(1L, "Alice", "Johnson", "123456", "alice.johnson@example.com", "password", "ROLE_USER", 1L);
        Chercheur chercheur2 = new Chercheur(2L, "Bob", "Smith", "789012", "bob.smith@example.com", "password123", "ROLE_ADMIN", 2L);

        when(chercheurRepository.findAll()).thenReturn(Arrays.asList(chercheur1, chercheur2));
        when(enseignantRestFeign.Enseignant_ByID(1L)).thenReturn(new dcc.tp2.chercheurservice.module.Enseignant(1L, "John", "Doe", "john.doe@example.com"));
        when(enseignantRestFeign.Enseignant_ByID(2L)).thenReturn(new dcc.tp2.chercheurservice.module.Enseignant(2L, "Jane", "Smith", "jane.smith@example.com"));

        List<Chercheur> result = chercheurService.GetALL_Chercheur();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(chercheurRepository, times(1)).findAll();
        verify(enseignantRestFeign, times(2)).Enseignant_ByID(anyLong());
    }

    @Test
    void testGetChercheurById() {
        Chercheur chercheur = new Chercheur(1L, "Alice", "Johnson", "123456", "alice.johnson@example.com", "password", "ROLE_USER", 1L);
        when(chercheurRepository.findById(1L)).thenReturn(java.util.Optional.of(chercheur));
        when(enseignantRestFeign.Enseignant_ByID(1L)).thenReturn(new dcc.tp2.chercheurservice.module.Enseignant(1L, "John", "Doe", "john.doe@example.com"));

        Chercheur result = chercheurService.Get_ChercheurById(1L);

        assertNotNull(result);
        assertEquals("Alice", result.getNom());
        verify(chercheurRepository, times(1)).findById(1L);
        verify(enseignantRestFeign, times(1)).Enseignant_ByID(1L);
    }

    @Test
    void testGetChercheurByEmail() {
        Chercheur chercheur = new Chercheur(1L, "Alice", "Johnson", "123456", "alice.johnson@example.com", "password", "ROLE_USER", 1L);
        when(chercheurRepository.findChercheurByEmail("alice.johnson@example.com")).thenReturn(chercheur);

        Chercheur result = chercheurService.Get_ChercheurByEmail("alice.johnson@example.com");

        assertNotNull(result);
        assertEquals("Alice", result.getNom());
        verify(chercheurRepository, times(1)).findChercheurByEmail("alice.johnson@example.com");
    }

    @Test
    void testUpdateChercheur() {
        Chercheur existingChercheur = new Chercheur(1L, "Alice", "Johnson", "123456", "alice.johnson@example.com", "password", "ROLE_USER", 1L);
        Chercheur updatedChercheur = new Chercheur(null, "Alice", "Williams", "654321", "alice.williams@example.com", "newpassword", "ROLE_ADMIN", 1L);

        when(chercheurRepository.findById(1L)).thenReturn(java.util.Optional.of(existingChercheur));
        when(chercheurRepository.save(existingChercheur)).thenReturn(existingChercheur);

        Chercheur result = chercheurService.Update_Chercheur(1L, updatedChercheur);

        assertNotNull(result);
        assertEquals("Alice", result.getNom());
        assertEquals("alice.williams@example.com", result.getEmail());
        verify(chercheurRepository, times(1)).save(existingChercheur);
    }

    @Test
    void testDeleteChercheur() {
        doNothing().when(chercheurRepository).deleteById(1L);

        chercheurService.Delete_Chercheur(1L);

        verify(chercheurRepository, times(1)).deleteById(1L);
    }

    @Test
    void testGetNombreDeChercheurParEnseignant() {
        when(chercheurRepository.nombre_chercheur_Enseignant(1L)).thenReturn(5L);

        long result = chercheurService.getNombreDeChercheurParEnseignant(1L);

        assertEquals(5L, result);
        verify(chercheurRepository, times(1)).nombre_chercheur_Enseignant(1L);
    }
}
