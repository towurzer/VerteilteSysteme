package org.aau.homework.assignment_05.tobi.ex2;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import org.aau.homework.assignment_05.tobi.reusables.ApiClient;

@Path("/news-service")
public class NewsWebService {
    @GET
    @Produces("text/html")
    public String printLatestNewsForTopic(@QueryParam("topic") String topic) {
        StringBuilder html = new StringBuilder();
        html.append("<h1>My news Service</h1><br>");
        html.append("<h2>Searching for news Articles of topic: '%s'</h2><br>".formatted(topic));
        ApiClient.queryNewsDataForTopic(topic).forEach(article -> html.append(article.toHTML()));
        return html.toString();
    }
}
