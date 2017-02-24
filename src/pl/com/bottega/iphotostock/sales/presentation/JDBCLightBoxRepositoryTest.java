package pl.com.bottega.iphotostock.sales.presentation;


import pl.com.bottega.iphotostock.sales.infrastructure.jdbc.JDBCClientRepository;
import pl.com.bottega.iphotostock.sales.infrastructure.jdbc.JDBCLightBoxRepository;
import pl.com.bottega.iphotostock.sales.infrastructure.jdbc.JDBCProductRepository;
import pl.com.bottega.iphotostock.sales.model.client.Client;
import pl.com.bottega.iphotostock.sales.model.client.ClientRepository;
import pl.com.bottega.iphotostock.sales.model.lightbox.LightBox;
import pl.com.bottega.iphotostock.sales.model.lightbox.LightBoxRepository;

import pl.com.bottega.iphotostock.sales.model.money.Money;
import pl.com.bottega.iphotostock.sales.model.product.Picture;
import pl.com.bottega.iphotostock.sales.model.product.ProductRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class JDBCLightBoxRepositoryTest {

    public static void main(String[] args) throws SQLException {
        Connection c = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost:9001/photostock", "SA", "");

        ProductRepository productRepository = new JDBCProductRepository(c);
        LightBoxRepository lightBoxRepository = new JDBCLightBoxRepository(c, productRepository);
        ClientRepository clientRepository = new JDBCClientRepository(c);

        Client client = clientRepository.get("100");
        LightBox lightBox = new LightBox(client, "fotki");
        lightBox.add(new Picture("1", "owca", new ArrayList<String>(), Money.ZERO));
        lightBoxRepository.put(lightBox);

        lightBox.add(new Picture("2", "baram", new ArrayList<String>(), Money.ZERO));
        lightBoxRepository.put(lightBox);
    }

}
