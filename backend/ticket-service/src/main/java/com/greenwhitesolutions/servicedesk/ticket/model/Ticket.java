package com.greenwhitesolutions.servicedesk.ticket.model;

import java.util.List;

/**
 * Ticket model for representing support tickets.
 */
public class Ticket {

    private Long id;
    private String title;
    private String description;
    private String category;
    private List<String> labels;

    public Ticket() {
    }

    public Ticket(Long id, String title, String description, String category, List<String> labels) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.labels = labels;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }
}
