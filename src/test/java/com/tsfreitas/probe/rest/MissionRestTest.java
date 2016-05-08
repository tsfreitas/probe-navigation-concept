package com.tsfreitas.probe.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.MethodMode;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.tsfreitas.probe.ProbeApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(ProbeApplication.class)
@WebAppConfiguration
public class MissionRestTest {

	private MockMvc mockMvc = null;

	@Autowired
	private WebApplicationContext context;

	@Before
	public void init() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	public void deveIniciarMissao() throws Exception {
		// GIVEN
		String content = "{\"x\": 10, \"y\":15}";

		MockHttpServletRequestBuilder request = post("/mission/startMission").content(content)
				.contentType(MediaType.APPLICATION_JSON_VALUE);
		// WHEN
		mockMvc.perform(request).andDo(MockMvcResultHandlers.print())
				// THEN
				.andExpect(status().isCreated()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.maxCoordinate.x").value(10)).andExpect(jsonPath("$.maxCoordinate.y").value(15))
				.andExpect(jsonPath("$.deployedProbes").isArray()).andExpect(jsonPath("$.deployedProbes").isEmpty());

	}

	@Test
	public void deveDarErroPoisCoordenadaEstaIncompleta() throws Exception {
		// GIVEN
		String content = "{\"x\": 10}";

		MockHttpServletRequestBuilder request = post("/mission/startMission").content(content)
				.contentType(MediaType.APPLICATION_JSON_VALUE);
		// WHEN
		mockMvc.perform(request).andDo(MockMvcResultHandlers.print())
				// THEN
				.andExpect(status().isBadRequest()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.exception").value("MethodArgumentNotValidException"))
				.andExpect(jsonPath("$.detailedMessage").value("Required field not provided"))
				.andExpect(jsonPath("$.fieldErros").isArray()).andExpect(jsonPath("$.fieldErros[0].field").value("y"))
				.andExpect(jsonPath("$.fieldErros[0].message").value("may not be null"));
	}

	@Test
	public void devePousarSondas() throws Exception {
		// GIVEN
		String contentMission = "{\"x\": 10, \"y\":15}";
		String content1 = "{\"x\": 1, \"y\":1, \"direction\": \"NORTH\"}";
		String content2 = "{\"x\": 2, \"y\":2, \"direction\": \"SOUTH\"}";

		MockHttpServletRequestBuilder requestMission = post("/mission/startMission").content(contentMission)
				.contentType(MediaType.APPLICATION_JSON_VALUE);

		MockHttpServletRequestBuilder request1 = post("/mission/sendProbe/{probeName}", "probe1").content(content1)
				.contentType(MediaType.APPLICATION_JSON_VALUE);

		MockHttpServletRequestBuilder request2 = post("/mission/sendProbe/{probeName}", "probe2").content(content2)
				.contentType(MediaType.APPLICATION_JSON_VALUE);

		// WHEN
		mockMvc.perform(requestMission);
		ResultActions actions1 = mockMvc.perform(request1).andDo(MockMvcResultHandlers.print());
		ResultActions actions2 = mockMvc.perform(request2).andDo(MockMvcResultHandlers.print());

		// THEN
		actions1.andExpect(status().isCreated()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.deployedProbes", Matchers.hasSize(1)))
				.andExpect(jsonPath("$.deployedProbes[0].probeName").value("probe1"))
				.andExpect(jsonPath("$.deployedProbes[0].direction").value("NORTH"))
				.andExpect(jsonPath("$.deployedProbes[0].coordinate.x").value(1))
				.andExpect(jsonPath("$.deployedProbes[0].coordinate.y").value(1))
				.andExpect(jsonPath("$.maxCoordinate.x").value(10)).andExpect(jsonPath("$.maxCoordinate.y").value(15));

		actions2.andExpect(status().isCreated()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.deployedProbes", Matchers.hasSize(2)))
				.andExpect(jsonPath("$.deployedProbes[0].probeName").value("probe1"))
				.andExpect(jsonPath("$.deployedProbes[0].direction").value("NORTH"))
				.andExpect(jsonPath("$.deployedProbes[0].coordinate.x").value(1))
				.andExpect(jsonPath("$.deployedProbes[0].coordinate.y").value(1))
				.andExpect(jsonPath("$.deployedProbes[1].probeName").value("probe2"))
				.andExpect(jsonPath("$.deployedProbes[1].direction").value("SOUTH"))
				.andExpect(jsonPath("$.deployedProbes[1].coordinate.x").value(2))
				.andExpect(jsonPath("$.deployedProbes[1].coordinate.y").value(2))
				.andExpect(jsonPath("$.maxCoordinate.x").value(10)).andExpect(jsonPath("$.maxCoordinate.y").value(15));

	}

	@Test
	public void deveDarErroPoisPousouDuasSondasComMesmoNome() throws Exception {
		// GIVEN
		String contentMission = "{\"x\": 10, \"y\":15}";
		String content1 = "{\"x\": 1, \"y\":1, \"direction\": \"NORTH\"}";
		String content2 = "{\"x\": 2, \"y\":2, \"direction\": \"SOUTH\"}";

		MockHttpServletRequestBuilder requestMission = post("/mission/startMission").content(contentMission)
				.contentType(MediaType.APPLICATION_JSON_VALUE);

		MockHttpServletRequestBuilder request1 = post("/mission/sendProbe/{probeName}", "probe1").content(content1)
				.contentType(MediaType.APPLICATION_JSON_VALUE);

		MockHttpServletRequestBuilder request2 = post("/mission/sendProbe/{probeName}", "probe1").content(content2)
				.contentType(MediaType.APPLICATION_JSON_VALUE);

		// WHEN
		mockMvc.perform(requestMission);
		ResultActions actions1 = mockMvc.perform(request1).andDo(MockMvcResultHandlers.print());
		ResultActions actions2 = mockMvc.perform(request2).andDo(MockMvcResultHandlers.print());

		// THEN
		actions1.andExpect(status().isCreated());

		actions2.andExpect(status().isBadRequest()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.exception").value("AlreadyExistProbeException"))
				.andExpect(jsonPath("$.detailedMessage").value("Probe already exists"))
				.andExpect(jsonPath("$.fieldErros").isEmpty());

	}

	@Test
	public void deveDarErroPoisSondaNaoTemDirecao() throws Exception {

		// GIVEN
		String contentMission = "{\"x\": 10, \"y\":15}";
		String content1 = "{\"x\": 1, \"y\":1}";

		MockHttpServletRequestBuilder requestMission = post("/mission/startMission").content(contentMission)
				.contentType(MediaType.APPLICATION_JSON_VALUE);

		MockHttpServletRequestBuilder request1 = post("/mission/sendProbe/{probeName}", "probe1").content(content1)
				.contentType(MediaType.APPLICATION_JSON_VALUE);

		// WHEN
		mockMvc.perform(requestMission);
		ResultActions actions1 = mockMvc.perform(request1).andDo(MockMvcResultHandlers.print());

		// THEN
		actions1.andExpect(status().isBadRequest()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.exception").value("MethodArgumentNotValidException"))
				.andExpect(jsonPath("$.detailedMessage").value("Required field not provided"))
				.andExpect(jsonPath("$.fieldErros").isArray())
				.andExpect(jsonPath("$.fieldErros[0].field").value("direction"))
				.andExpect(jsonPath("$.fieldErros[0].message").value("may not be null"));
	}

	@Test
	@DirtiesContext(methodMode = MethodMode.BEFORE_METHOD)
	public void deveDarErroPoisNaoHaMissaoIniciada() throws Exception {
		// GIVEN
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/mission/report");

		// WHEN
		ResultActions response = mockMvc.perform(request).andDo(MockMvcResultHandlers.print());

		// THEN
		response.andExpect(status().isBadRequest()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.exception").value("MissionNotStartedException"))
				.andExpect(jsonPath("$.detailedMessage")
						.value("Mission not started. First call POST /mission/startMission"))
				.andExpect(jsonPath("$.fieldErros").isEmpty());
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
