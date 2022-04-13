import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

	public static void main(String[] args) throws IOException, InterruptedException {

		String apiKey = "APIKEY";

		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()

				.uri(URI.create("https://imdb-api.com/en/API/Top250Movies/" + apiKey))
				.GET()
				.build();

		HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
		String json = response.body();

		String[] movies = parseJsonMovies(json);

     	List<String> titles = parseTitles(movies);
		titles.forEach(System.out::println);

		List<String> urlImages = parseUrlImages(movies);
		urlImages.forEach(System.out::println);

		List<String> years = parseYears(movies);
		years.forEach(System.out::println);

		List<String> imDbRatings = parseImDbRatings(movies);
		imDbRatings.forEach(System.out::println);

	}

	private static String[] parseJsonMovies(String json) {
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
	
	private static List<String> parseYears(String[] moviesArray){
	    return parseAttribute(moviesArray, 4);
	}
	private static List<String> parseImDbRatings(String[] moviesArray){
		return parseAttribute(moviesArray, 7);
	}
	private static List<String> parseAttribute(String[] moviesArray, int pos) {
		return Stream.of(moviesArray)
			.map(e -> e.split("\",\"")[pos]) 
			.map(e -> e.split(":\"")[1]) 
			.map(e -> e.replaceAll("\"", ""))
			.collect(Collectors.toList());
	}
}
