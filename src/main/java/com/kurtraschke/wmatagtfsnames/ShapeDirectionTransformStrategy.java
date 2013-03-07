package com.kurtraschke.wmatagtfsnames;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.onebusaway.gtfs.model.AgencyAndId;
import org.onebusaway.gtfs.model.ShapePoint;
import org.onebusaway.gtfs.model.Trip;
import org.onebusaway.gtfs.services.GtfsMutableRelationalDao;
import org.onebusaway.gtfs_transformer.services.GtfsTransformStrategy;
import org.onebusaway.gtfs_transformer.services.TransformContext;

/**
 *
 * @author kurt
 */
public class ShapeDirectionTransformStrategy implements GtfsTransformStrategy {

    String _shapeID;
    String _shapeDirection;

    public void setShapeID(String shapeID) {
        _shapeID = shapeID;
    }

    public void setShapeDirection(String shapeDirection) {
        _shapeDirection = shapeDirection;
    }

    public void run(TransformContext context, GtfsMutableRelationalDao dao) {
        
        String agencyID = context.getDefaultAgencyId();

        String newShapeID = _shapeID + "R";

        List<ShapePoint> shapePoints = new ArrayList(dao.getShapePointsForShapeId(new AgencyAndId(agencyID, _shapeID)));

        Collections.sort(shapePoints, new Comparator<ShapePoint>() {
            public int compare(ShapePoint o1, ShapePoint o2) {
                return new Integer(o1.getSequence()).compareTo(new Integer(o2.getSequence()));
            }
        });

        Collections.reverse(shapePoints);

        int newIndex = 1;

        for (ShapePoint sp : shapePoints) {
            ShapePoint nsp = new ShapePoint();
            nsp.setShapeId(new AgencyAndId(agencyID, newShapeID));
            nsp.setSequence(newIndex++);
            nsp.setLat(sp.getLat());
            nsp.setLon(sp.getLon());
            if (sp.isDistTraveledSet()) {
                nsp.setDistTraveled(sp.getDistTraveled());
            }

            dao.saveEntity(nsp);   
        }
        
        Collection<Trip> allTrips = dao.getAllTrips();

        for (Trip t : allTrips) {
            if (t.getShapeId().getId().equals(_shapeID) && !t.getDirectionId().equals(_shapeDirection)) {
                t.getShapeId().setId(newShapeID);
            }
        }
    }
}
