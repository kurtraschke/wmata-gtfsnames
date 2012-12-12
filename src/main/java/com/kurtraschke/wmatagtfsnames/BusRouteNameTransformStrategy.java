package com.kurtraschke.wmatagtfsnames;

import com.kurtraschke.wmatagtfsnames.impl.BusRouteNameServiceImpl;
import com.kurtraschke.wmatagtfsnames.services.BusRouteNameService;
import java.io.IOException;
import java.util.Collection;
import org.onebusaway.gtfs.model.Route;
import org.onebusaway.gtfs.services.GtfsMutableRelationalDao;
import org.onebusaway.gtfs_transformer.services.GtfsTransformStrategy;
import org.onebusaway.gtfs_transformer.services.TransformContext;

/**
 *
 * @author kurt
 */
public class BusRouteNameTransformStrategy implements GtfsTransformStrategy {

    BusRouteNameService brs;

    public void run(TransformContext context, GtfsMutableRelationalDao dao) {

        Collection<Route> allRoutes = dao.getAllRoutes();

        for (Route r : allRoutes) {
        
            if (r.getType() == 3 && r.getLongName() == null) {
                String routeLongName = brs.getNameForRoute(r.getShortName());
                
                if (routeLongName != null) {
                    r.setLongName(routeLongName);
                }
            }        
        }
    }

    public BusRouteNameTransformStrategy() throws IOException {
        brs = (BusRouteNameService) new BusRouteNameServiceImpl();

    }
}
