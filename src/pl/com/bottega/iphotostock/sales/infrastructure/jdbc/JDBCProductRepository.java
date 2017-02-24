package pl.com.bottega.iphotostock.sales.infrastructure.jdbc;

import pl.com.bottega.iphotostock.sales.infrastructure.csv.DataAccessException;
import pl.com.bottega.iphotostock.sales.model.client.Client;
import pl.com.bottega.iphotostock.sales.model.money.IntegerMoney;
import pl.com.bottega.iphotostock.sales.model.money.Money;
import pl.com.bottega.iphotostock.sales.model.product.Clip;
import pl.com.bottega.iphotostock.sales.model.product.Picture;
import pl.com.bottega.iphotostock.sales.model.product.Product;
import pl.com.bottega.iphotostock.sales.model.product.ProductRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;


public class JDBCProductRepository implements ProductRepository {

    private Connection connection;

    private static final String GET_PRODUCT_SQL = "SELECT * FROM products WHERE id = ?";

    public JDBCProductRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void put(Product product) {

    }

    @Override
    public Product get(String number) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_PRODUCT_SQL);
            preparedStatement.setString(1, number);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next())
                return null;
            return parseProduct(resultSet);
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    private Product parseProduct(ResultSet resultSet) throws SQLException {
        String number = resultSet.getString("id");
        String name = resultSet.getString("name");
        boolean available = resultSet.getBoolean("available");
        Money price = new IntegerMoney(resultSet.getLong("pricecents"), Money.DEFAULT_CURRENCY);
        Long length = resultSet.getLong("length");
        String type = resultSet.getString("type");
        if (type.equals("Picture")) {
            return new Picture(number, name, new HashSet<>(), price, available);
        } else {
            return new Clip(available, name, price, number, length);
        }
    }

    @Override
    public List<Product> find(Client client, String nameQuery, String[] tags, Money priceFrom, Money priceTo, boolean onlyActive) {
        return null;
    }
}
