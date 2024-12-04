package DAO.interfaces;

import classes.Ticket;

public interface TicketDAO extends DAO<Ticket> {
    void addTicket(Ticket ticket);
}
