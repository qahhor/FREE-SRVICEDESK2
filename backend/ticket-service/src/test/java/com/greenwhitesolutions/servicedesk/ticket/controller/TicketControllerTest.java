package com.greenwhitesolutions.servicedesk.ticket.controller;

import com.greenwhitesolutions.servicedesk.ticket.client.AiClient;
import com.greenwhitesolutions.servicedesk.ticket.model.CreateTicketRequest;
import com.greenwhitesolutions.servicedesk.ticket.model.Ticket;
import com.greenwhitesolutions.servicedesk.ticket.service.TicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Unit tests for TicketController.
 */
@ExtendWith(MockitoExtension.class)
class TicketControllerTest {

    @Mock
    private AiClient aiClient;

    private TicketController ticketController;
    private TicketService ticketService;

    @BeforeEach
    void setUp() {
        ticketService = new TicketService(aiClient);
        ticketController = new TicketController(ticketService);
    }

    @Test
    void createTicket_shouldReturnTicketWithLabels() {
        // Given
        List<String> mockLabels = Arrays.asList("hardware", "urgent");
        when(aiClient.classify(anyString())).thenReturn(mockLabels);

        CreateTicketRequest request = new CreateTicketRequest(
                "Computer not starting",
                "My computer won't start and the screen is black"
        );

        // When
        ResponseEntity<Ticket> response = ticketController.createTicket(request);

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());

        Ticket ticket = response.getBody();
        assertNotNull(ticket.getId());
        assertEquals("Computer not starting", ticket.getTitle());
        assertEquals("My computer won't start and the screen is black", ticket.getDescription());
        assertEquals("hardware", ticket.getCategory());
        assertEquals(mockLabels, ticket.getLabels());
    }

    @Test
    void createTicket_withEmptyLabels_shouldUseGeneralCategory() {
        // Given
        when(aiClient.classify(anyString())).thenReturn(List.of());

        CreateTicketRequest request = new CreateTicketRequest(
                "General inquiry",
                "I have a general question about the service"
        );

        // When
        ResponseEntity<Ticket> response = ticketController.createTicket(request);

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());

        Ticket ticket = response.getBody();
        assertEquals("general", ticket.getCategory());
        assertTrue(ticket.getLabels().isEmpty());
    }

    @Test
    void getTicket_existingTicket_shouldReturnTicket() {
        // Given
        List<String> mockLabels = Arrays.asList("network");
        when(aiClient.classify(anyString())).thenReturn(mockLabels);

        CreateTicketRequest request = new CreateTicketRequest(
                "Network issue",
                "Cannot connect to the internet"
        );
        ResponseEntity<Ticket> createResponse = ticketController.createTicket(request);
        Long ticketId = createResponse.getBody().getId();

        // When
        ResponseEntity<Ticket> getResponse = ticketController.getTicket(ticketId);

        // Then
        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        assertNotNull(getResponse.getBody());
        assertEquals(ticketId, getResponse.getBody().getId());
    }

    @Test
    void getTicket_nonExistingTicket_shouldReturn404() {
        // When
        ResponseEntity<Ticket> response = ticketController.getTicket(999L);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getAllTickets_shouldReturnAllCreatedTickets() {
        // Given
        when(aiClient.classify(anyString())).thenReturn(Arrays.asList("software"));

        ticketController.createTicket(new CreateTicketRequest("Ticket 1", "Description 1"));
        ticketController.createTicket(new CreateTicketRequest("Ticket 2", "Description 2"));

        // When
        var response = ticketController.getAllTickets();

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
    }
}
