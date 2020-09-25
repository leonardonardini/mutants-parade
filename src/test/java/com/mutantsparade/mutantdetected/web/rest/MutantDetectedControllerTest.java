package com.mutantsparade.mutantdetected.web.rest;

import com.mutantsparade.mutantdetected.domain.DnaStats;
import com.mutantsparade.mutantdetected.service.DnaStatsService;
import com.mutantsparade.mutantdetected.service.MutantDetectedService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = MutantDetectedController.class)
class MutantDetectedControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    MutantDetectedService mutantDetectedService;

    @MockBean
    DnaStatsService dnaStatsService;

    @Test
    void testIsMutant() throws Exception {

        when(mutantDetectedService.isMutant(any())).thenReturn(true);

        RequestBuilder request = MockMvcRequestBuilders.post("/api/mutant")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "\"dna\":[\"AAAAAA\",\"AAAAAA\",\"AAAAAA\",\"AAAAAA\",\"AAAAAA\",\"AAAAAA\"]\n" +
                        "}");
        mvc.perform(request).andExpect(status().isOk());
    }

    @Test
    void testIsNotMutant() throws Exception {

        when(mutantDetectedService.isMutant(any())).thenReturn(false);

        RequestBuilder request = MockMvcRequestBuilders.post("/api/mutant")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "\"dna\":[\"AAAAAA\",\"AAAAAA\",\"AAAAAA\",\"AAAAAA\",\"AAAAAA\",\"AAAAAA\"]\n" +
                        "}");
        mvc.perform(request).andExpect(status().isForbidden());
    }

    @Test
    void stats() throws Exception {

        DnaStats dnaStats = new DnaStats();
        dnaStats.setCount_human_dna(0L);
        dnaStats.setCount_mutant_dna(0L);
        dnaStats.setRatio(0D);

        CompletableFuture<DnaStats> completableFuture =  CompletableFuture.completedFuture(dnaStats);
        when(dnaStatsService.getStats()).thenReturn(completableFuture);

        RequestBuilder request = MockMvcRequestBuilders.get("/api/stats");
        MvcResult result = mvc.perform(request).andExpect(status().isOk()).andReturn();
        assertEquals(dnaStats, result.getAsyncResult());
    }
}