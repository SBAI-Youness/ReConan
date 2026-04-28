package org.reconan;

import org.reconan.repository.TableRepository;

public class Main {
    public static void main(String[] args) {

        System.out.println("Starting application...");

        TableRepository repository = new TableRepository();
        repository.printAllTables();

        System.out.println("Done.");
    }
}