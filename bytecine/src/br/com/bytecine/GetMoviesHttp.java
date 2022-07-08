package br.com.bytecine;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GetMoviesHttp {

	
	public static void main(String[] args) throws Exception {

		String apikey = "k_4xf7zg1p";
		URI apiIMDB = URI.create("https://imdb-api.com/en/API/Top250Movies/" + apikey);
		
		// HTTP Client
		HttpClient client = HttpClient.newHttpClient();
		
		// Request Create
		HttpRequest request = HttpRequest.newBuilder()
				.GET()
				.uri(apiIMDB)
				.build();
		
		// Response send
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		String json = response.body();
		
		// Response body
		System.out.println("Resposta: " + json);
		
		// Create parser of json to list
		
		String[] listMovies = parseJsonMovies(json);
		
		List<String> titles = parseTitles(listMovies);
		titles.forEach(System.out::println);
		
		List<String> urlImages = parseUrlImages(listMovies);
		urlImages.forEach(System.out::println);
		
		System.out.println(titles.get(1));
		
	}
	
	// methods
	private static String[] parseJsonMovies (String json) {
		Matcher matcher = Pattern.compile(".*\\[(.*)\\].*").matcher(json);

		if (!matcher.matches()) {
			throw new IllegalArgumentException("no match in " + json);
		}

		String[] moviesArray = matcher.group(1).split("\\},\\{");
		moviesArray[0] = moviesArray[0].substring(1);
		int last = moviesArray.length - 1;
		String lastString = moviesArray[last];
		moviesArray[last] = lastString.substring(0, lastString.length() - 1);
		
		return moviesArray;
	}
	
	private static List<String> parseTitles(String[] moviesArray) {
		return parseAttribute(moviesArray, 3);
	}
	
	private static List<String> parseUrlImages(String[] moviesArray) {
		return parseAttribute(moviesArray, 5);
	}
	
	private static List<String> parseAttribute(String[] moviesArray, int pos) {
		return Stream.of(moviesArray)
			.map(e -> e.split("\",\"")[pos]) 
			.map(e -> e.split(":\"")[1]) 
			.map(e -> e.replaceAll("\"", ""))
			.collect(Collectors.toList());
	}
}
