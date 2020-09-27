package com.mutantsparade.mutantdetected.web.rest;

import com.mutantsparade.mutantdetected.domain.DnaStats;
import com.mutantsparade.mutantdetected.errors.DnaNotMutantException;
import com.mutantsparade.mutantdetected.errors.InvalidDnaCodeException;
import com.mutantsparade.mutantdetected.service.DnaStatsService;
import com.mutantsparade.mutantdetected.service.MutantDetectedService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.server.ResponseStatusException;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * This test class contains CONTROLLER tests, which means
 * that only controller layer is tested over a mocked server.
 * This can be achived thanks to Spring's MockMvc,
 * and uses @MockBean to create dependency needed by the controller.
 *
 */
@WebMvcTest(value = MutantDetectedController.class)
class MutantDetectedControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    MutantDetectedService mutantDetectedService;

    @MockBean
    DnaStatsService dnaStatsService;

    //init variables used in tests
    private static final String statsUri = "/api/stats";
    private static final String mutantsUri = "/api/mutant";

    @Test
    void testIsMutant() throws Exception {

        doNothing().when(mutantDetectedService).verifyMutant(any());

        RequestBuilder request = MockMvcRequestBuilders.post(mutantsUri)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}");
        MvcResult result = mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(request().asyncStarted())
                .andReturn();
        assertEquals(MutantDetectedController.ENTRY_ALLOWED, result.getAsyncResult());
    }

    @Test
    void testIsNotMutant() throws Exception {

        doThrow(new DnaNotMutantException("NOT mutant")).when(mutantDetectedService).verifyMutant(any());

        RequestBuilder request = MockMvcRequestBuilders.post(mutantsUri)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}");

        MvcResult result = mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(request().asyncStarted())
                .andReturn();
        assertEquals(ResponseStatusException.class, result.getAsyncResult().getClass());
        ResponseStatusException rse = (ResponseStatusException)result.getAsyncResult();
        assertEquals(HttpStatus.FORBIDDEN, rse.getStatus());
    }

    @Test
    void testInvalidDna() throws Exception {

        String exceptionMessage = "INVALID DNA";
        doThrow(new InvalidDnaCodeException(exceptionMessage)).when(mutantDetectedService).verifyMutant(any());

        RequestBuilder request = MockMvcRequestBuilders.post(mutantsUri)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}");

        MvcResult result = mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(request().asyncStarted())
                .andReturn();
        assertEquals(ResponseStatusException.class, result.getAsyncResult().getClass());
        ResponseStatusException rse = (ResponseStatusException)result.getAsyncResult();
        assertEquals(HttpStatus.BAD_REQUEST, rse.getStatus());
    }

    @Test
    void testStats() throws Exception {
        DnaStats dnaStats = new DnaStats();
        dnaStats.setCountHumanDna(0l);
        dnaStats.setCountMutantDna(0l);
        dnaStats.setRatio(0D);

        CompletableFuture<DnaStats> completableFuture =  CompletableFuture.completedFuture(dnaStats);
        when(dnaStatsService.getStats()).thenReturn(completableFuture);

        RequestBuilder request = MockMvcRequestBuilders.get(statsUri);
        MvcResult result = mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(request().asyncStarted())
                .andReturn();
        assertEquals(dnaStats, result.getAsyncResult());
    }
}