package com.tsfreitas.probe.rest;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import com.tsfreitas.probe.ProbeApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(ProbeApplication.class)
@WebIntegrationTest({"server.port=9099"})
public class MissionRestTest {
	
	RestTemplate restTemplate = new TestRestTemplate();
	
	
	@Test
	public void deveIniciarMissao() {
		Assert.fail();
	}
	
	
	@Test
	public void devePegarRelatorioDaMissao() {
		Assert.fail();
	}
	
	@Test
	public void deveDarErroPoisNaoHaMissaoIniciada() {
		Assert.fail();
	}
	
	@Test
	public void deveEnviarASonda() {
		Assert.fail();
	}
	
	@Test
	public void deveEnviarComandoParaASonda() {
		Assert.fail();
	}
	
	@Test
	public void deveFazerMissaoEmBatch() {
		Assert.fail();
	}

}
