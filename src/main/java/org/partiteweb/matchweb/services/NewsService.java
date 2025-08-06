package org.partiteweb.matchweb.services;

import org.springframework.stereotype.Service;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

@Service
public class NewsService {

    private final ArrayList<String> newsList;
    private int counter = -1;

    public NewsService() {
        newsList = fetchNews();
    }

    private ArrayList<String> fetchNews() {
        try {
            ArrayList<String> newsList = new ArrayList<>();

            InputStream inputStream = getClass().getResourceAsStream("/static/data/news.txt");

            assert inputStream != null;
            Scanner scanner = new Scanner(inputStream);
            while (scanner.hasNextLine()) {
                newsList.add(scanner.nextLine());
            }
            scanner.close();

            Collections.shuffle(newsList); //randomizes news

            return newsList;

        } catch (Exception exc) {
            System.err.println("Err: failed to open news.txt. File not found.");
            return null;
        }
    }

    public String getNextNews() {
        counter = (counter + 1) % newsList.size();
        return newsList.get(counter);
    }
}
