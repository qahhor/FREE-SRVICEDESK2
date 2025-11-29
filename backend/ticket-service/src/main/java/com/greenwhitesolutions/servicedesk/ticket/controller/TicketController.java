package com.greenwhitesolutions.servicedesk.ticket.controller;

import com.greenwhitesolutions.servicedesk.ticket.model.CreateTicketRequest;
import com.greenwhitesolutions.servicedesk.ticket.model.Ticket;
import com.greenwhitesolutions.servicedesk.ticket.service.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * REST controller for ticket operations.
 */
@RestController
@RequestMapping("/api/v1/tickets")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    /**
     * Create a new ticket.
     * The ticket description is classified using the AI service to populate labels.
     *
     * @param request the ticket creation request
     * @return the created ticket with labels populated from AI classification
     */
    @PostMapping
    public ResponseEntity<Ticket> createTicket(@RequestBody CreateTicketRequest request) {
        Ticket ticket = ticketService.createTicket(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ticket);
    }

    /**
     * Get a ticket by ID.
     *
     * @param id the ticket ID
     * @return the ticket if found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getTicket(@PathVariable Long id) {
        return ticketService.getTicket(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get all tickets.
     *
     * @return collection of all tickets
     */
    @GetMapping
    public ResponseEntity<Collection<Ticket>> getAllTickets() {
        return ResponseEntity.ok(ticketService.getAllTickets());
    }
}
