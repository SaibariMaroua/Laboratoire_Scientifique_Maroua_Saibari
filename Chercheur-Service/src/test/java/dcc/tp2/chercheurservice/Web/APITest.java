package dcc.tp2.chercheurservice.Web;

import dcc.tp2.chercheurservice.entities.Chercheur;
import dcc.tp2.chercheurservice.service.ChercheurService;
import dcc.tp2.chercheurservice.web.API;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(API.class)
public class APITest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ChercheurService chercheurService;

    private Chercheur chercheur;

    @BeforeEach
    void setUp() {
        chercheur = new Chercheur(1L, "Nom", "Prenom", "email@example.com", "password", "123456", "ROLE_CHE", 1L, 1L, null);
    }

    @Test
    void testAddChercheur() throws Exception {
        when(chercheurService.Create_Chercheur(Mockito.any(Chercheur.class))).thenReturn(chercheur);

        mockMvc.perform(post("/Chercheurs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "nom": "Nom",
                            "prenom": "Prenom",
                            "email": "email@example.com",
                            "password": "password",
                            "tel": "123456",
                            "role": "ROLE_CHE",
                            "id_enseignant": 1,
                            "id_projet": 1
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.nom", is("Nom")))
                .andExpect(jsonPath("$.prenom", is("Prenom")))
                .andExpect(jsonPath("$.email", is("email@example.com")));
    }

    @Test
    void testGetAllChercheurs() throws Exception {
        List<Chercheur> chercheurs = Arrays.asList(chercheur,
                new Chercheur(2L, "Jane", "Smith", "jane.smith@example.com", "password123", "654321", "ROLE_CHE", 2L, 2L, null));

        when(chercheurService.GetALL_Chercheur()).thenReturn(chercheurs);

        mockMvc.perform(get("/Chercheurs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(2)))
                .andExpect(jsonPath("$[0].nom", is("Nom")))
                .andExpect(jsonPath("$[1].nom", is("Jane")));
    }

    @Test
    void testGetChercheurById() throws Exception {
        when(chercheurService.Get_ChercheurById(1L)).thenReturn(chercheur);

        mockMvc.perform(get("/Chercheurs/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.nom", is("Nom")));
    }

    @Test
    void testUpdateChercheur() throws Exception {
        Chercheur updatedChercheur = new Chercheur(1L, "Jane", "Doe", "jane.doe@example.com", "newpassword", "654321", "ROLE_CHE", 1L, 1L, null);
        when(chercheurService.Update_Chercheur(Mockito.anyLong(), Mockito.any(Chercheur.class))).thenReturn(updatedChercheur);

        mockMvc.perform(put("/Chercheurs/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "nom": "Jane",
                            "prenom": "Doe",
                            "email": "jane.doe@example.com",
                            "password": "newpassword",
                            "tel": "654321",
                            "role": "ROLE_CHE",
                            "id_enseignant": 1,
                            "id_projet": 1
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom", is("Jane")))
                .andExpect(jsonPath("$.email", is("jane.doe@example.com")));
    }

    @Test
    void testDeleteChercheur() throws Exception {
        doNothing().when(chercheurService).Delete_Chercheur(1L);

        mockMvc.perform(delete("/Chercheurs/1"))
                .andExpect(status().isOk());

        verify(chercheurService, times(1)).Delete_Chercheur(1L);
    }

    @Test
    void testGetByEmail() throws Exception {
        // Simuler la réponse du service pour l'email fourni
        Chercheur chercheur = new Chercheur();
        chercheur.setEmail("email@example.com");
        chercheur.setPassword("password");
        chercheur.setRole("ROLE_CHE");  // Assurez-vous que le rôle est bien défini

        // Lorsque le service chercheur renvoie le chercheur avec l'email donné
        when(chercheurService.Get_ChercheurByEmail("email@example.com")).thenReturn(chercheur);

        // Effectuer la requête et vérifier la réponse
        mockMvc.perform(get("/Chercheurs/email/email@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("email@example.com"))
                .andExpect(jsonPath("$.password").value("password"))
                .andExpect(jsonPath("$.scope").value("ROLE_CHE"));  // Vérification de "scope"
    }


}
