package com.challenge.resizephoto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import com.challenge.resizephoto.resource.model.RequestList;
import com.challenge.resizephoto.resource.model.ResponseList;
import com.challenge.resizephoto.service.ResizePhotoService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ResizePhotoApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ResizePhotoService resizePhotoService;
	
	@Test
	public void contextLoads() {
	}
	
	@Test
	public void testResizedImagesFromEspecificUrl() {
		
		String uri = "http://54.152.221.29/images.json";
		
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getForObject(uri, RequestList.class);
		
//		Mockito.when(
//				resizePhotoService.savePhotosResized(Mockito.anyObject(), Mockito.anyObject())
//				) ;
		
		ResponseEntity responseEntity = restTemplate.getForEntity("http://localhost:8080/resizephoto/urls", ResponseEntity.class);
		
		ResponseList response = (ResponseList) responseEntity.getBody();
		
		assertThat(response.getImages().size()).isEqualTo(30);
	}

}
