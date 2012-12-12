wmata-gtfsnames
===============

Scrape bus route names from WMATA's schedule listings and add them to the GTFS feed in route_long_name.  Improves usability of OneBusAway, etc.

Use in conjunction with the OneBusAway GTFS transformer: http://developer.onebusaway.org/modules/onebusaway-gtfs-modules/current-SNAPSHOT/onebusaway-gtfs-transformer-cli.html

Pass a JSON string like the following to the transformer (along with any other transformations as needed):

{"op":"transform","class":"com.kurtraschke.wmatagtfsnames.BusRouteNameTransformStrategy"}
