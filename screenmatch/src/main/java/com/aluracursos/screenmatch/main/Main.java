package com.aluracursos.screenmatch.main;

import com.aluracursos.screenmatch.model.DataShow;
import com.aluracursos.screenmatch.model.Episode;
import com.aluracursos.screenmatch.model.EpisodeData;
import com.aluracursos.screenmatch.model.SeasonData;
import com.aluracursos.screenmatch.service.APIConsumption;
import com.aluracursos.screenmatch.service.DataConversor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private Scanner keyboard = new Scanner(System.in);
    private APIConsumption apiConsumption = new APIConsumption();
    private final String URL_BASE = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=d7daaab3&";
    private DataConversor dataConversor = new DataConversor();

    public void showMenu() {
        System.out.println("Por favor escribe el nombre de la serie que desea buscar:");

        //search general data of all shows
        var showName = keyboard.nextLine();
        var json = apiConsumption.getData(URL_BASE + showName.replace(" ", "+") + API_KEY);
        var data = dataConversor.getData(json, DataShow.class);
        System.out.println(data);

        //search data for all seasons
        List<SeasonData> seasonsList = new ArrayList<>();
        for (int i = 1; i <= data.totalSeasons(); i++) {
            json = apiConsumption.getData(URL_BASE + showName.replace(" ", "+") + "&Season=" + i + API_KEY);
            var seasonData = dataConversor.getData(json, SeasonData.class);
            seasonsList.add(seasonData);
        }
//        seasonsList.forEach(System.out::println);

        //show only the title of the episodes for the seasons
//        for (int i = 0; i < data.totalSeasons(); i++) {
//            List<EpisodeData> episodesSeason = seasonsList.get(i).episodeDataList();
//            for (int j = 0; j < episodesSeason.size(); j++) {
//                System.out.println(episodesSeason.get(j).title());
//            }
//        }
//        seasonsList.forEach(t -> t.episodesDataList()
//                .forEach(e -> System.out.println(e.title()));

        //Convert all information to a list of type DataEpisode
        List<EpisodeData> episodeDataList = seasonsList.stream()
                .flatMap(t -> t.episodesDataList().stream())
                .collect(Collectors.toList());

        //Top five episodes
//        System.out.println("Top 5 episodios");
////        episodeDataList.stream()
////                .filter(e -> !e.rate().equalsIgnoreCase("N/A"))
////                .peek(e -> System.out.println("Primer filtro (N/A) " + e))
////                .sorted(Comparator.comparing(EpisodeData::rate).reversed())
////                .peek(e -> System.out.println("Segundo ordenar (M>m) " + e))
////                .map(e -> e.title().toUpperCase())
////                .peek(e -> System.out.println("Tercer filtro Mayusculas (m>M)" + e))
////                .limit(5)
////                .forEach(System.out::println);

        //Convert the data to a list of type Episode
        List<Episode> episodeList = seasonsList.stream()
                .flatMap(t -> t.episodesDataList().stream()
                        .map(d -> new Episode(t.season(), d)))
                .collect(Collectors.toList());

        //episodeList.forEach(System.out::println);

        //Episode search from x year
        System.out.println("Por favor indica el año a partir del cual deseas ver los episodios: ");
        var year = keyboard.nextInt();
        keyboard.nextLine();

        LocalDate searchDate = LocalDate.of(year, 1, 1);

//        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyy");
//        episodeList.stream()
//                .filter(e -> e.getReleasedDate() != null && e.getReleasedDate().isAfter(searchDate))
//                .forEach(e -> System.out.println(
//                        "Season " + e.getSeason() +
//                                " Episode " + e.getTitle() +
//                                " Released Date " + e.getReleasedDate().format(dateTimeFormatter)
//                ));

        //find espisodes by a title or a piece of the title
        System.out.println("Escriba el titulo del episodio que desea ver");
        var titlePiece = keyboard.nextLine();
        Optional<Episode> episodeSearched = episodeList.stream()
                .filter(e -> e.getTitle().toUpperCase().contains(titlePiece.toUpperCase()))
                .findFirst();
        if (episodeSearched.isPresent()) {
            System.out.println("Episodio encontrado");
            System.out.println("Los datos son: " + episodeSearched.get());
        } else {
            System.out.println("Episodio no encontrado");
        }
        /*episodeList.stream()
                .filter(e -> e.getTitle().toUpperCase().contains(titlePiece.toUpperCase()))
                .forEach(System.out::println);*/
    }
}
