package com.greenwhitesolutions.servicedesk.ticket.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for Ticket model.
 */
class TicketTest {

    @Test
    @DisplayName("Ticket can be created with all fields")
    void createTicket_withAllFields() {
        List<String> labels = Arrays.asList("hardware", "urgent");
        Ticket ticket = new Ticket(1L, "Test Title", "Test Description", "hardware", labels);

        assertThat(ticket.getId()).isEqualTo(1L);
        assertThat(ticket.getTitle()).isEqualTo("Test Title");
        assertThat(ticket.getDescription()).isEqualTo("Test Description");
        assertThat(ticket.getCategory()).isEqualTo("hardware");
        assertThat(ticket.getLabels()).containsExactly("hardware", "urgent");
    }

    @Test
    @DisplayName("Ticket can be created with no-arg constructor")
    void createTicket_noArg() {
        Ticket ticket = new Ticket();

        assertThat(ticket.getId()).isNull();
        assertThat(ticket.getTitle()).isNull();
        assertThat(ticket.getDescription()).isNull();
        assertThat(ticket.getCategory()).isNull();
        assertThat(ticket.getLabels()).isNull();
    }

    @Test
    @DisplayName("Ticket fields can be set via setters")
    void ticket_setters() {
        List<String> labels = Arrays.asList("software");
        Ticket ticket = new Ticket();
        ticket.setId(2L);
        ticket.setTitle("New Title");
        ticket.setDescription("New Description");
        ticket.setCategory("software");
        ticket.setLabels(labels);

        assertThat(ticket.getId()).isEqualTo(2L);
        assertThat(ticket.getTitle()).isEqualTo("New Title");
        assertThat(ticket.getDescription()).isEqualTo("New Description");
        assertThat(ticket.getCategory()).isEqualTo("software");
        assertThat(ticket.getLabels()).containsExactly("software");
    }
}
