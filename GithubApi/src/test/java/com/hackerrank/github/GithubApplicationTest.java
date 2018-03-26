package com.hackerrank.github;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hackerrank.github.config.GithubConfiguration;
import com.hackerrank.github.models.*;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class GithubApplicationTest {

    private static final String HOST = "http://localhost:7200";
    private String inputFile;

    public GithubApplicationTest(String inputFile) {
        this.inputFile = inputFile;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> inputFiles() {
        return Arrays.asList(new Object[][] {
                {"./testcases/http00.json"},
                {"./testcases/http01.json"},
                {"./testcases/http02.json"},
                {"./testcases/http03.json"},
                {"./testcases/http04.json"}
        });
    }

    @ClassRule
    public static final DropwizardAppRule<GithubConfiguration> RULE =
            new DropwizardAppRule<>(GithubApplication.class, ResourceHelpers.resourceFilePath("config.yml"));

    @Test
    public void testGetAllEvents() throws IOException {
        Scanner scanner = new Scanner(new File(getClass().getClassLoader().getResource(inputFile).getFile()));
        ObjectMapper mapper = new ObjectMapper();
        int line = 1;
        while (scanner.hasNextLine()){
            String inputLine = scanner.nextLine();
            Input input = getInput(inputLine, mapper);
            if(input != null) {
                URL url = new URL(HOST + input.getRequest().getUrl());
                System.out.println(line++ + ": " + url.toString());
                HttpURLConnection con = getHttpURLConnectionForEventInput(input, url);
                if (input instanceof InputResponseBodyObject) {
                    InputResponseBodyObject in = (InputResponseBodyObject) input;
                    int responseCode = con.getResponseCode();
                    assertEquals(in.getResponse().getStatusCode(), responseCode);
                    if (in.getResponse().getBody().getId() == null)
                        assertEquals("", getResponseBody(con));
                    else {
                        String body = mapper.writeValueAsString(in.getResponse().getBody());
                        assertEquals(body, getResponseBody(con));
                    }
                } else if (input instanceof InputResponseBodyArray) {
                    InputResponseBodyArray in = (InputResponseBodyArray) input;
                    int responseCode = con.getResponseCode();
                    assertEquals(in.getResponse().getStatusCode(), responseCode);
                    if (in.getResponse().getBody().length == 0)
                        assertEquals("[]", getResponseBody(con));
                    else {
                        String body = mapper.writeValueAsString(in.getResponse().getBody());
                        assertEquals(body, getResponseBody(con));
                    }
                } else {
                    InputResponseBodyActors in = (InputResponseBodyActors) input;
                    int responseCode = con.getResponseCode();
                    assertEquals(in.getResponse().getStatusCode(), responseCode);
                    if (in.getResponse().getBody().length == 0)
                        assertEquals("[]", getResponseBody(con));
                    else {
                        String body = mapper.writeValueAsString(in.getResponse().getBody());
                        assertEquals(body, getResponseBody(con));
                    }
                }
            }
            else {
                InputRequestBodyActor actorInput = mapper.readValue(inputLine, InputRequestBodyActor.class);
                URL url = new URL(HOST + actorInput.getRequest().getUrl());
                System.out.println(line++ + ": " + url.toString());
                HttpURLConnection con = getHttpURLConnectionForActorInput(actorInput, url);
                int responseCode = con.getResponseCode();
                assertEquals(actorInput.getResponse().getStatusCode(), responseCode);
                if (actorInput.getResponse().getBody().getId() == null)
                    assertEquals("", getResponseBody(con));
                else {
                    String body = mapper.writeValueAsString(actorInput.getResponse().getBody());
                    assertEquals(body, getResponseBody(con));
                }
            }
        }
    }

    private HttpURLConnection getHttpURLConnectionForActorInput(InputRequestBodyActor input, URL url) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod(input.getRequest().getMethod());
        if (input.getRequest().getHeaders().getContentType() != null && !input.getRequest().getHeaders().getContentType().isEmpty())
            con.setRequestProperty("Content-Type", input.getRequest().getHeaders().getContentType());
        if (input.getRequest().getBody() != null && input.getRequest().getBody().getId() != null) {
            con.setDoOutput(true);
            String body = mapper.writeValueAsString(input.getRequest().getBody());
            con.getOutputStream().write(body.getBytes("UTF8"));
        }
        return con;
    }

    private HttpURLConnection getHttpURLConnectionForEventInput(Input input, URL url) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod(input.getRequest().getMethod());
        if (input.getRequest().getHeaders().getContentType() != null && !input.getRequest().getHeaders().getContentType().isEmpty())
            con.setRequestProperty("Content-Type", input.getRequest().getHeaders().getContentType());
        if (input.getRequest().getBody() != null && input.getRequest().getBody().getId() != null) {
            con.setDoOutput(true);
            String body = mapper.writeValueAsString(input.getRequest().getBody());
            con.getOutputStream().write(body.getBytes("UTF8"));
        }
        return con;
    }

    private String getResponseBody(HttpURLConnection con) throws IOException {
        StringBuilder response = new StringBuilder();
        int code = con.getResponseCode();
        if(code == 400 || code == 404 || code == 500)
            return "";

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }

    private Input getInput(String line, ObjectMapper mapper) throws IOException {
        Input input;
        try {
            input = mapper.readValue(line, InputResponseBodyObject.class);
        } catch (JsonMappingException e) {
            try {
                input = mapper.readValue(line, InputResponseBodyArray.class);
            } catch (JsonMappingException ex) {
                try {
                    input = mapper.readValue(line, InputResponseBodyActors.class);
                } catch (JsonMappingException ee) {
                    return null;
                }
            }
        }
        return input;
    }
}
