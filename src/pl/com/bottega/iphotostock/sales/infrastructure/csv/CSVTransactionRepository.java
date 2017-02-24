package pl.com.bottega.iphotostock.sales.infrastructure.csv;


import com.sun.deploy.util.StringUtils;
import pl.com.bottega.iphotostock.sales.model.client.Transaction;
import pl.com.bottega.iphotostock.sales.model.money.Money;

import java.io.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

class CSVTransactionRepository {

    private String folderPath;

    public CSVTransactionRepository(String folderPath) {
        this.folderPath = folderPath;
    }

    public void saveTransactions(String clientNumber, Collection<Transaction> transactions) {
        try (PrintWriter pw = new PrintWriter(getRepositoryPath(clientNumber))) {
            for (Transaction t : transactions) {
                String[] components = {
                        t.getValue().toString(),
                        t.getDescription(),
                        t.getTimestamp().format(DateTimeFormatter.ISO_DATE_TIME)
                };
                pw.println(StringUtils.join(Arrays.asList(components), ","));
            }
        } catch (Exception ex) {
            throw new DataAccessException(ex);
        }
    }

    public Collection<Transaction> getTransactions(String clientNumber) {
        Collection<Transaction> transactions = new LinkedList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(getRepositoryPath(clientNumber)))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] attributes = line.trim().split(",");
                String[] valueString = attributes[0].split(" ");
                Money value = Money.valueOf(valueString[0]);
                String description = attributes[1];
                LocalDateTime date = LocalDateTime.parse(attributes[2]);
                Transaction transaction = new Transaction(value, description, date);
                transactions.add(transaction);
            }
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
        return transactions;
    }

    private String getRepositoryPath(String clientNumber) {
        return folderPath + File.separator + "clients-" + clientNumber + "-transactions.csv";
    }

}
