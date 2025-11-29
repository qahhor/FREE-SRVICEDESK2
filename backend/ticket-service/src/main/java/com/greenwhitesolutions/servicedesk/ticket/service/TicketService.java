package com.greenwhitesolutions.servicedesk.ticket.service;

import com.greenwhitesolutions.servicedesk.ticket.client.AiClient;
import com.greenwhitesolutions.servicedesk.ticket.model.CreateTicketRequest;
import com.greenwhitesolutions.servicedesk.ticket.model.Ticket;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Service for managing tickets with in-memory storage.
 */
@Service
public class TicketService {

    private final Map<Long, Ticket> ticketStore = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);
    private final AiClient aiClient;

    public TicketService(AiClient aiClient) {
        this.aiClient = aiClient;
    }

    /**
     * Create a new ticket.
     * Calls AI service to classify the ticket description and populate labels.
     *
     * @param request the ticket creation request
     * @return the created ticket with AI-generated labels
     */
    public Ticket createTicket(CreateTicketRequest request) {
        Long id = idGenerator.getAndIncrement();

        // Call AI service to classify the description
        List<String> labels = aiClient.classify(request.getDescription());

        // Set category from first label if available
        String category = labels.isEmpty() ? "general" : labels.get(0);

        Ticket ticket = new Ticket(id, request.getTitle(), request.getDescription(), category, labels);
        ticketStore.put(id, ticket);

        return ticket;
    }

    /**
     * Get a ticket by ID.
     *
     * @param id the ticket ID
     * @return the ticket if found
     */
    public Optional<Ticket> getTicket(Long id) {
        return Optional.ofNullable(ticketStore.get(id));
    }

    /**
     * Get all tickets.
     *
     * @return collection of all tickets
     */
    public Collection<Ticket> getAllTickets() {
        return ticketStore.values();
    }
}
