package helpers;

public class Calculator {
	
	// Returns the angle of rotation to make the kart at point1 to face point2 in radians.
    public static double calcAngle(double x1, double y1, double x2, double y2) {
    	double rotation;
    	if(x1 < x2) {
    		rotation = (y1 > y2) ? Math.atan((x2-x1)/(y1-y2)) : (Math.PI-Math.atan((x2-x1)/(y2-y1)));
    	} else {
    		rotation = (y1 > y2) ? -Math.atan((x1-x2)/(y1-y2)) : -(Math.PI-Math.atan((x1-x2)/(y2-y1)));
    	}
    	return rotation;
    }
    
    // Calculates the distance between two points.
    public static double calcDistance(double x1, double y1, double x2, double y2) {
    	return Math.sqrt(Math.pow(y2-y1, 2) + Math.pow(x2-x1, 2));
    }
    
}
