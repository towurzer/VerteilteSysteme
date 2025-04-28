package org.aau.homework.assignment_05.philipp.ex2;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import org.aau.homework.assignment_05.philipp.util.NewsClient;

@Path("/news-service/philipp")
public class NewsService {
    @GET
    @Produces("text/html")
    public String printLatestNewsForTopic(@QueryParam("topic") String topic) {
        StringBuilder html = new StringBuilder();
        html.append("<h1>News Service</h1><br>");
        html.append("<h2>Results for topic: '%s'</h2><br>".formatted(topic));
        NewsClient.getNewsArticlesForTopic(topic).forEach(article -> html.append(article.toHTML()));
        return html.toString();
    }
}