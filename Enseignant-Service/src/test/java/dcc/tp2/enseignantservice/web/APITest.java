package dcc.tp2.enseignantservice.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import dcc.tp2.enseignantservice.entities.Enseignant;
import dcc.tp2.enseignantservice.service.EnseignantService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


@WebMvcTest(API.class)
class APITest {

    @MockBean
    private EnseignantService enseignantService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private List<Enseignant> enseignantList;

    @BeforeEach
    void setUp() {
        this.enseignantList = List.of(
                new Enseignant(1L, "Saibari", "Maroua", "LA12345", "maroua@mail.com", "123", "informatique", "Enseignant"),
                new Enseignant(2L, "sab", "wissal", " KB11111", "wissal@mail.com", "123", "informatique", "Enseignant")
        );
    }


    @Test
    void add() throws Exception{
        Enseignant enseignant =  new Enseignant(null, "Saibari", "Maroua", "LA12345", "maroua@mail.com", "123", "informatique", "Enseignant");
        Mockito.when(enseignantService.Create_Enseignant(Mockito.any(Enseignant.class))).thenReturn(enseignantList.get(0));

        mockMvc.perform(MockMvcRequestBuilders.post("/Enseignants")
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(enseignant)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(enseignantList.get(0))));
    }


    @Test
    void getALL() throws Exception {

        Mockito.when(enseignantService.GetAll_Enseignant()).thenReturn(enseignantList);

        mockMvc.perform(MockMvcRequestBuilders.get("/Enseignants"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(enseignantList)));

    }

    @Test
    void get_ByID() throws Exception {
        Long id=1L;
        Mockito.when(enseignantService.Get_EnseignantByID(Mockito.anyLong())).thenReturn(enseignantList.get(0));

        mockMvc.perform(MockMvcRequestBuilders.get("/Enseignants/{id}",id))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(enseignantList.get(0))));
    }

    @Test
    void update() throws Exception {
        Long id = 1L;
        Enseignant enseignant_modifiy =  new Enseignant(null, "Saibari", "AL-Maroua", "LA12345", "maroua@mail.com", "123", "informatique", "Enseignant");

        Mockito.when(enseignantService.Update_Enseignant(Mockito.any(Enseignant.class),Mockito.anyLong())).thenReturn(enseignant_modifiy);

        mockMvc.perform(MockMvcRequestBuilders.put("/Enseignants/{id}",id)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(enseignant_modifiy)))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(enseignant_modifiy)));


    }

    @Test
    void delete() throws Exception{
        Long id = 1L;
        mockMvc.perform(MockMvcRequestBuilders.delete("/Enseignants/{id}",id))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void statistique() throws Exception{
        Long id = 1L;

        Map<String, Long> Statistiques = new HashMap<>();
        Statistiques.put("nombre de projet",2L);
        Statistiques.put("nombre de chercheur",2L);

        Mockito.when(enseignantService.statistique(Mockito.anyLong())).thenReturn(Statistiques);
        mockMvc.perform(MockMvcRequestBuilders.get("/Enseignants/statistique/{id}",id))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(Statistiques)));
    }
    @Test
    void getByemail() throws Exception{
        String email="ali@mail.com";
        Mockito.when(enseignantService.FindByEmail(Mockito.anyString())).thenReturn(enseignantList.get(0));

        Map<String, String> infos_user = new HashMap<>();
        infos_user.put("email", enseignantList.get(0).getEmail());
        infos_user.put("password", enseignantList.get(0).getPassword());
        infos_user.put("scope", enseignantList.get(0).getRole());

        mockMvc.perform(MockMvcRequestBuilders.get("/Enseignants/email/{email}",email))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(infos_user)));
    }
}








/**
 package dcc.tp2.enseignantservice.Controller;

 import dcc.tp2.enseignantservice.entities.Enseignant;
 import dcc.tp2.enseignantservice.service.EnseignantService;
 import dcc.tp2.enseignantservice.web.API;
 import org.junit.jupiter.api.BeforeEach;
 import org.junit.jupiter.api.Test;
 import org.mockito.Mockito;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
 import org.springframework.boot.test.mock.mockito.MockBean;
 import org.springframework.http.MediaType;
 import org.springframework.test.web.servlet.MockMvc;

 import java.util.Arrays;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;

 import static org.hamcrest.Matchers.*;
 import static org.mockito.Mockito.*;
 import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
 import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

 @WebMvcTest(API.class)
 public class APITest {

 @Autowired
 private MockMvc mockMvc;

 @MockBean
 private EnseignantService enseignantService;

 private Enseignant enseignant;

 @BeforeEach
 void setUp() {
 enseignant = new Enseignant(1L, "John", "Doe", "CNE123", "john.doe@example.com", "password", "Mathématiques", "ROLE_USER");
 }

 @Test
 void testAddEnseignant() throws Exception {
 when(enseignantService.Create_Enseignant(Mockito.any(Enseignant.class))).thenReturn(enseignant);

 mockMvc.perform(post("/Enseignants")
 .contentType(MediaType.APPLICATION_JSON)
 .content("""
 {
 "nom": "John",
 "prenom": "Doe",
 "cne": "CNE123",
 "email": "john.doe@example.com",
 "password": "password",
 "thematique": "Mathématiques",
 "role": "ROLE_USER"
 }
 """))
 .andExpect(status().isOk())
 .andExpect(jsonPath("$.id", is(1)))
 .andExpect(jsonPath("$.nom", is("John")))
 .andExpect(jsonPath("$.prenom", is("Doe")))
 .andExpect(jsonPath("$.email", is("john.doe@example.com")));
 }

 @Test
 void testGetAllEnseignants() throws Exception {
 List<Enseignant> enseignants = Arrays.asList(enseignant,
 new Enseignant(2L, "Jane", "Smith", "CNE456", "jane.smith@example.com", "password123", "Physique", "ROLE_ADMIN"));

 when(enseignantService.GetAll_Enseignant()).thenReturn(enseignants);

 mockMvc.perform(get("/Enseignants"))
 .andExpect(status().isOk())
 .andExpect(jsonPath("$.size()", is(2)))
 .andExpect(jsonPath("$[0].nom", is("John")))
 .andExpect(jsonPath("$[1].nom", is("Jane")));
 }

 @Test
 void testGetEnseignantById() throws Exception {
 when(enseignantService.Get_EnseignantByID(1L)).thenReturn(enseignant);

 mockMvc.perform(get("/Enseignants/1"))
 .andExpect(status().isOk())
 .andExpect(jsonPath("$.id", is(1)))
 .andExpect(jsonPath("$.nom", is("John")));
 }

 @Test
 void testUpdateEnseignant() throws Exception {
 Enseignant updatedEnseignant = new Enseignant(1L, "Jane", "Doe", "CNE123", "jane.doe@example.com", "newpassword", "Informatique", "ROLE_ADMIN");
 when(enseignantService.Update_Enseignant(Mockito.any(Enseignant.class), eq(1L))).thenReturn(updatedEnseignant);

 mockMvc.perform(put("/Enseignants/1")
 .contentType(MediaType.APPLICATION_JSON)
 .content("""
 {
 "nom": "Jane",
 "prenom": "Doe",
 "cne": "CNE123",
 "email": "jane.doe@example.com",
 "password": "newpassword",
 "thematique": "Informatique",
 "role": "ROLE_ADMIN"
 }
 """))
 .andExpect(status().isOk())
 .andExpect(jsonPath("$.nom", is("Jane")))
 .andExpect(jsonPath("$.email", is("jane.doe@example.com")));
 }

 @Test
 void testDeleteEnseignant() throws Exception {
 doNothing().when(enseignantService).Delete_Enseignant(1L);

 mockMvc.perform(delete("/Enseignants/1"))
 .andExpect(status().isOk());

 verify(enseignantService, times(1)).Delete_Enseignant(1L);
 }

 @Test
 void testStatistiques() throws Exception {
 Map<String, Long> stats = new HashMap<>();
 stats.put("nombre de projet", 3L);
 stats.put("nombre de chercheur", 5L);

 when(enseignantService.statistique(1L)).thenReturn(stats);

 mockMvc.perform(get("/Enseignants/statistique/1"))
 .andExpect(status().isOk())
 .andExpect(jsonPath("$.['nombre de projet']", is(3)))
 .andExpect(jsonPath("$.['nombre de chercheur']", is(5)));
 }

 @Test
 void testGetByEmail() throws Exception {
 when(enseignantService.FindByEmail("john.doe@example.com")).thenReturn(enseignant);

 mockMvc.perform(get("/Enseignants/email/john.doe@example.com"))
 .andExpect(status().isOk())
 .andExpect(jsonPath("$.email", is("john.doe@example.com")))
 .andExpect(jsonPath("$.scope", is("ROLE_USER")));
 }
 }

 */
