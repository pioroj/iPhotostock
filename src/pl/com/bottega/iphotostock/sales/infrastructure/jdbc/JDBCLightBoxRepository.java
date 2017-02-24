package pl.com.bottega.iphotostock.sales.infrastructure.jdbc;


import pl.com.bottega.iphotostock.sales.infrastructure.csv.DataAccessException;
import pl.com.bottega.iphotostock.sales.model.client.Client;
import pl.com.bottega.iphotostock.sales.model.lightbox.LightBox;
import pl.com.bottega.iphotostock.sales.model.lightbox.LightBoxRepository;
import pl.com.bottega.iphotostock.sales.model.product.Product;
import pl.com.bottega.iphotostock.sales.model.product.ProductRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class JDBCLightBoxRepository implements LightBoxRepository {

    private ProductRepository productRepository;
    private Connection connection;

    private static final String GET_LIGHTBOX_ID_SQL = "SELECT id " +
            "FROM lightboxes " +
            "WHERE client_id = ? " +
            "AND name = ?";

    private static final String GET_PRODUCT_ID_SQL = "SELECT id FROM products WHERE name = ?";

    private static final String INSERT_LIGHTBOX_SQL =
            "INSERT INTO lightboxes (client_id, name) VALUES (?, ?)";

    private static final String INSERT_PRODUCT_SQL =
            "INSERT INTO lightboxes_products VALUES (?, ?)";

    private static final String GET_CLIENTID_BY_NUMBER_SQL = "SELECT id FROM clients WHERE number = ?";

    private static final String GET_LIGHTBOX_FOR_CLIENT_SQL = "SELECT id, name FROM lightboxes WHERE client_id = ?";

    private static final String GET_LIGHTBOX_BY_NAME_SQL = "SELECT lightboxes.client_id, lightboxes.name AS name, product_id " +
            "FROM lightboxes " +
            "JOIN clients ON lightboxes.client_id = clients.id " +
            "WHERE clients.number = ? AND lightboxes.name = ?";

    private static final String GET_LIGHTBOX_NAMES_SQL = "SELECT * FROM lightboxes WHERE client_id = ?";

    private static final String GET_LIGHTBOXES_PRODUCTS_SQL =
            "SELECT * FROM lightboxes_products WHERE lightbox_id = ? AND product_id = ?";

    private static final String GET_PRODUCT_ID_FROM_LIGHTBOXES_SQL =
            "SELECT product_id FROM lightboxes_products WHERE lightbox_id = ?";

    public JDBCLightBoxRepository(Connection connection, ProductRepository productRepository) {
        this.connection = connection;
        this.productRepository = productRepository;
    }

    @Override
    public void put(LightBox lightBox) {
        String name = lightBox.getName();
        String clientNumber = lightBox.getOwner().getNumber();
        try {
            int clientID = getClientID(clientNumber);
            Integer lightBoxID = getLightBoxID(clientID, name);
            if (lightBoxID == null) {
                putLightBox(clientID, name);
                lightBoxID = getLightBoxID(clientID, name);

                for (Product product : lightBox) {
                    int productID = Integer.parseInt(product.getNumber());
                    if (getProductIDFromLightboxesProducts(lightBoxID, productID) == null) {
                        putProductIntoLightboxes(lightBoxID, productID);
                    }
                }

            } else {
                //TODO iteracja po produktach obiektu LB, uzywajac lbID. robimy selecta na id produktow tego idLB.
                //TODO while (resultset.next) sprawdzamy czy jest taki id produktu. Jesli nie to insertujemy krotkę z nim
                for (Product product : lightBox) {
                    int productID = Integer.parseInt(product.getNumber());
                    if (getRecordFromLightboxesProducts(lightBoxID, productID) == null) {
                        putProductIntoLightboxes(lightBoxID, productID);
                    }
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    private Integer getRecordFromLightboxesProducts(Integer lightBoxID, int productID) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(GET_LIGHTBOXES_PRODUCTS_SQL);
        preparedStatement.setInt(1, lightBoxID);
        preparedStatement.setInt(2, productID);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (!resultSet.next())
            return null;
        return resultSet.getInt("lightbox_id");
    }

    private void putProductIntoLightboxes(Integer lightBoxID, int productID) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PRODUCT_SQL);
        preparedStatement.setInt(1, lightBoxID);
        preparedStatement.setInt(2, productID);
        preparedStatement.executeUpdate();
    }

    private Integer getProductIDFromLightboxesProducts(Integer lightBoxID, int productID) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(GET_PRODUCT_ID_FROM_LIGHTBOXES_SQL);
        preparedStatement.setInt(1, lightBoxID);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (!resultSet.next())
            return null;
        return resultSet.getInt("product_id");
    }

    private void putLightBox(int clientID, String name) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_LIGHTBOX_SQL);
        preparedStatement.setInt(1, clientID);
        preparedStatement.setString(2, name);
        preparedStatement.executeUpdate();
    }

    private Integer getLightBoxID(int clientID, String name) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(GET_LIGHTBOX_ID_SQL);
        preparedStatement.setInt(1, clientID);
        preparedStatement.setString(2, name);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (!resultSet.next())
            return null;
        return resultSet.getInt("id");
    }

    private int getClientID(String clientNumber) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(GET_CLIENTID_BY_NUMBER_SQL);
        preparedStatement.setString(1, clientNumber);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getInt("id");
    }

    @Override
    public Collection<LightBox> getFor(Client client) {
        Collection<LightBox> lightBoxes = new HashSet<>();
        String clientNumber = client.getNumber();
        try {
            Integer clientID = getClientID(clientNumber);
            for (LightBox lb : lightBoxes) {
                addLightBoxFromDatabaseTo(lightBoxes, clientID, client);
            }
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
        return lightBoxes;
    }

    private void addLightBoxFromDatabaseTo(Collection<LightBox> lightBoxes, Integer clientID, Client client) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(GET_LIGHTBOX_FOR_CLIENT_SQL);
        preparedStatement.setInt(1, clientID);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            String name = resultSet.getString("name");
            LightBox lightBox = new LightBox(client, name);
            lightBoxes.add(lightBox);
        }
        //TODO dobrać produkty z lb_prod
    }

    @Override
    public LightBox findLightBox(Client client, String lightBoxName) {
        return null;
    }

    @Override
    public Collection<String> getLightBoxNames(Client client) {
        Collection<String> names = new ArrayList<>();

        return null;
    }
}
