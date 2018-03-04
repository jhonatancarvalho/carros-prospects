package br.com.jhonatan.carrosprospects.resources;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.jhonatan.carrosprospects.CarrosProspectsApplication;
import br.com.jhonatan.carrosprospects.builders.CarroBuilder;
import br.com.jhonatan.carrosprospects.domain.Carro;
import br.com.jhonatan.carrosprospects.enums.CorCarro;
import br.com.jhonatan.carrosprospects.repositories.CarroRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(
  webEnvironment = WebEnvironment.RANDOM_PORT,
  classes = CarrosProspectsApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
  locations = "classpath:application.properties")
public class CarroResourceTest {

	@Autowired
    private MockMvc mvc;
 
    @Autowired
    private CarroRepository carroRepository;
    
    @Autowired
	private ObjectMapper objectMapper;
     
    @Test
    public void deveBuscarCarro() throws Exception {
         
        final Carro carro = CarroBuilder.builder().build();
       
        carroRepository.save(carro);
     
        mvc.perform(get("/carros/" + carro.getId())
          .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.id", is(carro.getId())))
          .andExpect(jsonPath("$.pessoaId", is(carro.getPessoaId())))
          .andExpect(jsonPath("$.modelo", is(carro.getModelo())))
          .andExpect(jsonPath("$.ano", is(carro.getAno())))
          .andExpect(jsonPath("$.cor", is(carro.getCor().toString())));
    }
    
    @Test
    public void naoDeveBuscarCarroComIdNulo() throws Exception {

        mvc.perform(get("/carros/" + null)
          .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isBadRequest());
    }
    
    @Test
    public void naoDeveBuscarCarroComIdInexistente() throws Exception {

        mvc.perform(get("/carros/" + Integer.MAX_VALUE)
          .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isNotFound());
    }
    
    @Test
    public void deveSalvarCarro() throws Exception {
         
        final Carro carro = CarroBuilder.builder().build();
        
        mvc.perform(MockMvcRequestBuilders.post("/carros/")
          .content(objectMapper.writeValueAsString(carro))
          .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isCreated());
    }
    
    @Test
    public void deveAlterarCarro() throws Exception {
         
        final Carro carro = CarroBuilder.builder().build();
        carroRepository.save(carro);
        carro.setModelo("Cruze");
        
        mvc.perform(MockMvcRequestBuilders.put("/carros/" + carro.getId())
          .content(objectMapper.writeValueAsString(carro))
          .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isNoContent());
        
        final Carro carroSalvo = carroRepository.findById(carro.getId()).get();
        assertThat(carroSalvo.getModelo(), is(carro.getModelo()));
    }
    
    @Test
    public void deveExcluirCarro() throws Exception {
         
        final Carro carro = CarroBuilder.builder().build();
        carroRepository.save(carro);
        
        mvc.perform(MockMvcRequestBuilders.delete("/carros/" + carro.getId())
          .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isNoContent());
        
        final Carro carroSalvo = carroRepository.findById(carro.getId()).orElse(null);
        assertNull(carroSalvo);
    }
    
    @Test
    public void deveBuscarTodosCarros() throws Exception {
         
        final Carro carro1 = CarroBuilder.builder().build();
        final Carro carro2 = CarroBuilder.builder().build();
       
        carroRepository.saveAll(Arrays.asList(carro1, carro2));
        final Integer quantidadeSalva = (int) carroRepository.count();
     
        mvc.perform(get("/carros/")
          .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$", hasSize(quantidadeSalva)));
    }
    
    @Test
    public void deveBuscarCoresCarro() throws Exception {
    	 	
    	final List<String> listaCores = new ArrayList<>();
    	final CorCarro[] listaCorCarro = CorCarro.values();
    	for (CorCarro corCarro : listaCorCarro) {
    		listaCores.add(corCarro.toString());
		}
    	
        mvc.perform(get("/carros/cores")
          .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$", hasItems(listaCores.toArray())));
    }
    
}
