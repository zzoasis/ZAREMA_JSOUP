package com.example.zarema_jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MusicReader {

    private static final String BASE_URL = "https://spotifycharts.com/regional/global/daily/latest";

    // Парсинг котировок из формата html web-страницы банка, при ошибке доступа возвращаем null
    public static String getShowsData() {
        StringBuilder data = new StringBuilder();
        try {
            Document doc = Jsoup.connect(BASE_URL).timeout(5000).get(); // Создание документа JSOUP из html
            data.append("TOP 200 Spotify:\n");
            data.append(String.format("%12s\n", "Музыка").trim());
            data.append("\n");
            Elements e = doc.select("div.container"); // Ищу в документе "<div class="container"> с данными
            Elements tables = e.select("table.chart-table"); // Ищу таблицу, которая принадлежит классу "container"
            Element table = tables.get(0); // Выбираю 1 таблицу
            Elements tbodies = table.select("tbody"); // Ищу тело таблицы, которая принадлежит классу "chart-list"
            Element tbody = tbodies.get(0); // Выибраю 1 тело таблицы
            for (Element row : tbody.select("tr")) {
                // Цикл по столбцам таблицы
                Elements td = row.select("td");
                Element tdNumber = td.get(1); // Выбираю 1 столбец
                Element tdTitle = td.get(3); // Выбираю 2 столбец с названием песни
                data.
                        append(tdNumber.text()).
                        append(". ").
                        append(tdTitle.text()).
                        append("\n");
            }
        } catch (Exception ignored) {
            return null; // При ошибке доступа возвращаем null
        }
        return data.toString().trim(); // Возвращаем результат
    }

}

