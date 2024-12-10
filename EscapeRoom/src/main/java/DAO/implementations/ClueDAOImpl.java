package DAO.implementations;

import DAO.Parser;
import DAO.interfaces.ClueDAO;
import classes.enums.Theme;
import classes.item.ItemFactory;
import classes.item.implementations.Clue;
import classes.item.implementations.ItemFactoryImpl;
import connections.DbConnection;
import connections.DbConnectionImpl;
import connections.attribute.Attribute;
import DAO.Query;
import connections.callback.ParsingCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class ClueDAOImpl implements ClueDAO, ParsingCallback<Clue> {

    DbConnection dbConnection = DbConnectionImpl.getInstance();
    Parser<Clue> parser = new Parser<>(this);
    ItemFactory itemFactory = new ItemFactoryImpl();

    private static final String IDCLUE = "idclue";
    private static final String NAME = "name";
    private static final String PRICE = "price";
    private static final String THEME = "theme";

    @Override
    public Boolean addClue(Clue clue, Integer enigmaId) {
        List<Attribute> queryAttributeList = new ArrayList<>();
        queryAttributeList.add(new Attribute(clue.getName(), String.class));
        queryAttributeList.add(new Attribute(clue.getPrice(), Double.class));
        queryAttributeList.add(new Attribute(clue.getTheme().name(), String.class));
        queryAttributeList.add(new Attribute(enigmaId, Integer.class));
        return dbConnection.create(Query.CREATECLUE, queryAttributeList);
    }

    @Override
    public List<Clue> getAllCluesByEnigma(Integer enigmaId) {

        List<Attribute> queryAttributeList = List.of(new Attribute<>(enigmaId, Integer.class));
        List<Attribute> outputAttributes = Arrays.asList(
                new Attribute<>(IDCLUE, null, Integer.class),
                new Attribute<>(NAME, null, String.class),
                new Attribute<>(PRICE, null, Double.class),
                new Attribute<>(THEME, null, String.class));

        return this.getClues(Query.GETCLUEBYENIGMA, queryAttributeList, outputAttributes);
    }

    @Override
    public Clue getClueById(Integer itemId) {
        List<Clue> clues = new ArrayList<>();

        List<Attribute> queryAttributeList = List.of(new Attribute<>(itemId, Integer.class));
        List<Attribute> outputAttributes = Arrays.asList(
                new Attribute<>(IDCLUE, null, Integer.class),
                new Attribute<>(NAME, null, String.class),
                new Attribute<>(PRICE, null, Double.class),
                new Attribute<>(THEME, null, String.class));

        return this.getClues(Query.GETCLUEBYID, queryAttributeList, outputAttributes).getFirst();
    }

    @Override
    public List<Clue> getData() {
        List<Clue> clues = new ArrayList<>();

        List<Attribute> queryAttributeList = List.of();
        List<Attribute> outputAttributes = Arrays.asList(
                new Attribute<>(IDCLUE, null, Integer.class),
                new Attribute<>(NAME, null, String.class),
                new Attribute<>(PRICE, null, Double.class),
                new Attribute<>(THEME, null, String.class));

        return this.getClues(Query.GETALLCLUES, queryAttributeList, outputAttributes);
    }

    private List<Clue> getClues(String query, List<Attribute> queryAttributes, List<Attribute> outputAttributes) {
        List<Clue> clues = new ArrayList<>();

        List<HashSet<Attribute>> clueList = dbConnection.query(query, queryAttributes, outputAttributes);


        if (clueList.isEmpty()) return List.of();

        for (HashSet<Attribute> attributeValues: clueList) {
            Clue clue = itemFactory.createClue();
            parser.parseObject(clue, attributeValues);
            clues.add(clue);
        }

        return clues;
    }

    @Override
    public void delete(Integer id) {
        List<Attribute> queryAttributeList = new ArrayList<>();
        queryAttributeList.add(new Attribute(id, Integer.class));
        dbConnection.delete(Query.DELETECLUE, queryAttributeList);
    }

    @Override
    public void onCallbackString(Clue object, Attribute<String> attribute) {
        switch (attribute.getName()) {
            case NAME -> object.setName(attribute.getValue());
            case THEME -> object.setTheme(Theme.valueOf(attribute.getValue()));
        }
    }

    @Override
    public void onCallbackInteger(Clue object, Attribute<Integer> attribute) {
        if (attribute.getName().equals(IDCLUE)) {
            object.setItemId(attribute.getValue());
        }
    }

    @Override
    public void onCallbackDouble(Clue object, Attribute<Double> attribute) {
        if (attribute.getName().equals(PRICE)) {
            object.setPrice(attribute.getValue());
        }
    }

    @Override
    public void onCallbackBoolean(Clue object, Attribute<Boolean> attribute) {}
}
