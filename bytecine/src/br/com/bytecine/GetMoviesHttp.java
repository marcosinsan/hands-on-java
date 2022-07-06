package br.com.bytecine;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class GetMoviesHttp {

	public static final String API_KEY = "apikey";
	public static final String END_POINT = "https://imdb-api.com/en/API/Search/" + API_KEY + "/Top250Movies";
	
	public static void main(String[] args) throws IOException, InterruptedException {
		
		// HTTP Client
		HttpClient client = HttpClient.newHttpClient();
		
		// Request Create
		HttpRequest request = HttpRequest.newBuilder()
				.GET()
				.timeout(Duration.ofSeconds(10))
				.uri(URI.create(END_POINT))
				.build();
		
		// Response send
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		
		// Response body
		System.out.println(response.statusCode());
		System.out.println(response.body());
		
		
	}
	
	
}
