package com.greenwhitesolutions.servicedesk.ticket.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for CreateTicketRequest model.
 */
class CreateTicketRequestTest {

    @Test
    @DisplayName("CreateTicketRequest can be created with all fields")
    void createTicketRequest_withAllFields() {
        CreateTicketRequest request = new CreateTicketRequest("Test Title", "Test Description");

        assertThat(request.getTitle()).isEqualTo("Test Title");
        assertThat(request.getDescription()).isEqualTo("Test Description");
    }

    @Test
    @DisplayName("CreateTicketRequest can be created with no-arg constructor")
    void createTicketRequest_noArg() {
        CreateTicketRequest request = new CreateTicketRequest();

        assertThat(request.getTitle()).isNull();
        assertThat(request.getDescription()).isNull();
    }

    @Test
    @DisplayName("CreateTicketRequest fields can be set via setters")
    void createTicketRequest_setters() {
        CreateTicketRequest request = new CreateTicketRequest();
        request.setTitle("New Title");
        request.setDescription("New Description");

        assertThat(request.getTitle()).isEqualTo("New Title");
        assertThat(request.getDescription()).isEqualTo("New Description");
    }
}
