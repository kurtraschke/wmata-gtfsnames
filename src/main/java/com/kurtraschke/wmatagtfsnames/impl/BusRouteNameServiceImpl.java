package com.kurtraschke.wmatagtfsnames.impl;

import com.kurtraschke.wmatagtfsnames.services.BusRouteNameService;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author kurt
 */
public class BusRouteNameServiceImpl implements BusRouteNameService {

    private Map<String, String> routeNames = new HashMap<String, String>();
    private String[] scheduleLists = new String[]{"http://wmata.com/bus/timetables/timetables-state.cfm?State=DC",
        "http://wmata.com/bus/timetables/timetables-state.cfm?State=MD",
        "http://wmata.com/bus/timetables/timetables-state.cfm?State=VA"};

    public String getNameForRoute(String routeID) {
        return routeNames.get(routeID);
    }

    private Map<String, String> ingest(String url) throws IOException {
        Map<String, String> out = new HashMap<String, String>();
        Document doc = Jsoup.connect(url).get();

        Elements routes = doc.select("td.standard p");

        for (Element e : routes) {

            String text = e.text();

            Pattern pattern = Pattern.compile("\\( ([A-Z0-9, ]*) \\) (.*)");

            Matcher matcher = pattern.matcher(text);

            if (matcher.matches()) {
                String allRoutes = matcher.group(1);
                String routeName = matcher.group(2);

                String[] routeIDs = allRoutes.split(",");

                for (String routeID : routeIDs) {
                    out.put(routeID.trim(), routeName);
                }

            }

        }

        return out;
    }

    public BusRouteNameServiceImpl() throws IOException {
        for (String scheduleList : scheduleLists) {
            routeNames.putAll(ingest(scheduleList));
        }
    }
}
