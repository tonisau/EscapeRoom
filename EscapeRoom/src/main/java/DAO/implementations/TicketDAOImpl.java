package DAO.implementations;

import DAO.Parser;
import DAO.Query;
import DAO.interfaces.TicketDAO;
import classes.Ticket;
import classes.enums.Material;
import classes.enums.Theme;
import connections.DbConnection;
import connections.DbConnectionImpl;
import connections.attribute.Attribute;
import connections.callback.ParsingCallback;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class TicketDAOImpl implements TicketDAO, ParsingCallback<Ticket> {
    DbConnection dbConnection = DbConnectionImpl.getInstance();
    Parser<Ticket> parser = new Parser<>(this);

    private static final String INCOME = "Income";

    @Override
    public void onCallbackString(Ticket object, Attribute<String> attribute) {

    }

    @Override
    public void onCallbackInteger(Ticket object, Attribute<Integer> attribute) {

    }

    @Override
    public void onCallbackDouble(Ticket object, Attribute<Double> attribute) {
        if(attribute.getName().equals(INCOME)) object.setPrice(attribute.getValue());
    }

    @Override
    public void onCallbackBoolean(Ticket object, Attribute<Boolean> attribute) {

    }

    @Override
    public List<Ticket> getData() {
        return List.of();
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public void addTicket(Ticket ticket){
        List<Attribute> attributeList = new ArrayList<>();
        attributeList.add(new Attribute<>(ticket.getPrice(), Double.class));
        attributeList.add(new Attribute<>(ticket.getSaleDate(), LocalDateTime.class));
        attributeList.add(new Attribute<>(ticket.getRoomId(), Integer.class));
        dbConnection.create(Query.CREATETICKET, attributeList);
    }

    public Double getIncomeBetweenDates(LocalDateTime dateFrom, LocalDateTime dateTo){
        List<Attribute> queryAttributeList = new ArrayList<>();
        queryAttributeList.add(new Attribute<>(dateFrom, LocalDateTime.class));
        queryAttributeList.add(new Attribute<>(dateTo, LocalDateTime.class));

        List<Attribute> outputAttributes = Arrays.asList(
                new Attribute<>(INCOME, null, Double.class));
        HashSet<Attribute> attributeValues = dbConnection
                .query(Query.GETTICKETINCOME, queryAttributeList, outputAttributes)
                .getFirst();

        if (attributeValues.isEmpty()) return 0.0;

        Ticket ticket = new Ticket();
        parser.parseObject(ticket, attributeValues);
        return ticket.getPrice();

    }
}
