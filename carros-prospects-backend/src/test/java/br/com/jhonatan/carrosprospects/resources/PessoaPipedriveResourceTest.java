package br.com.jhonatan.carrosprospects.resources;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import br.com.jhonatan.carrosprospects.builders.PessoaPipedriveBuilder;
import br.com.jhonatan.carrosprospects.domain.PessoaPipedrive;
import br.com.jhonatan.carrosprospects.services.PessoaPipedriveService;

@RunWith(SpringRunner.class)
@WebMvcTest(PessoaPipedriveResource.class)
public class PessoaPipedriveResourceTest {

	@Autowired
    private MockMvc mvc;
	
	@MockBean
    private PessoaPipedriveService pessoaPipedriveService;
	
	@Test
    public void deveBuscarPessoaPipedrive() throws Exception {
         
		final String token = "123";
        final PessoaPipedrive pessoaPipedrive = PessoaPipedriveBuilder.builder().build();
        
        given(pessoaPipedriveService.findById(token, pessoaPipedrive.getId())).willReturn(pessoaPipedrive);
     
        mvc.perform(get("/pessoas/" + pessoaPipedrive.getId() + "?token=" + token)
          .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.id", is(pessoaPipedrive.getId())))
          .andExpect(jsonPath("$.name", is(pessoaPipedrive.getName())));
    }
	
	@Test
    public void deveBuscarTodasPessoasPipedrive() throws Exception {
         
		final String token = "123";
        final PessoaPipedrive pessoaPipedrive = PessoaPipedriveBuilder.builder().build();
        
        given(pessoaPipedriveService.findAll(token)).willReturn(Arrays.asList(pessoaPipedrive));
     
        mvc.perform(get("/pessoas?token=" + token)
          .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$[0].id", is(pessoaPipedrive.getId())))
          .andExpect(jsonPath("$[0].name", is(pessoaPipedrive.getName())));
    }
 
}
