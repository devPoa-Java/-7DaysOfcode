import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

public class Main {

	public static void main(String[] args) throws IOException, InterruptedException {
		
		String apiKey = "APIKEY";
		
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
<<<<<<< HEAD
				.uri(URI.create("https://imdb-api.com/en/API/Top250Movies/" + apiKey))
=======
				.uri(URI.create("https://imdb-api.com/en/API/Top250Movies/{APIKEY}"))
>>>>>>> ac075ef3f4bb8adf3c08067b43b3b51a6bf44f18
				.GET()
				.build();

		HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
		String json = response.body();

		System.out.println("Resposta:" + json);

	}

}
