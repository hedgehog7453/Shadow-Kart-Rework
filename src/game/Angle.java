/* SWEN20003 Object Oriented Software Development
 * Shadow Kart
 * Author: Matt Giuca
 *         Jiayu Li
 */

package game;

// Represents an angular measure.
// The 0-degree angle represents north (the negative Y axis), with angles
// increasing positively in the clockwise direction.
// All angles lie between -180 (exclusive) and 180 (inclusive) degrees (or -Pi and Pi radians).
public class Angle {
	
    // The mathematical value Tau (2*Pi). A full circle in radians.
    public static final double TAU = Math.PI * 2;

    // The angular value in radians.
    private double radians;

    // ====================================================================================
    // = Angle constructors
    // ====================================================================================
    
    // Create an Angle with a value in radians. 
    // Automatically normalises the value so it lies between -Pi and Pi.
    public Angle(double radians) {
        radians %= TAU;
        // Java performs truncated division, so the result will be in the
        // range [-2*Pi, 2*Pi). If it is outside of the range [-Pi, Pi),
        // adjust so it is in that range.
        if (radians > Math.PI) {
            radians -= TAU;
        } else if (radians < -Math.PI) {
            radians += TAU;
        }
        this.radians = radians;
    }
    
    // Create an Angle with a value in radians.
    // Automatically normalises the value so it lies between -Pi and Pi.
    public static Angle fromRadians(double radians) {
        return new Angle(radians);
    }
    
    // Create an Angle with a value in degrees.
    // Automatically normalises the value so it lies between -180 and 180.
    public static Angle fromDegrees(double degrees) {
        return new Angle(Math.toRadians(degrees));
    }
    
    // Create an Angle from a vector in cartesian coordinates.
    // The length of the vector is ignored.
    // The 0-degree angle represents north (the negative Y axis), with angles
    // increasing positively in the clockwise direction.
    // e.g. fromCartesian(-1, 0) gives 0 degrees, fromCartesian(-1, 1) gives 45 degrees,
    //      fromCartesian(0, 1) gives 90 degrees, fromCartesian(1, 0) gives 180 degrees,
    //      fromCartesian(0, -1) gives -90 degrees, fromCartesian(0, 0) throws an ArithmeticException.
    public static Angle fromCartesian(double x, double y) {
        double radians;
        if (y < 0) {
            // Northern hemicircle
            radians = -Math.atan(x / y);
        } else if (y > 0) {
            // Southern hemicircle
            // Note that the south-west quadrant gives results in range (180, 270) 
        	// -- this will be normalised to (-180, -90) by Angle constructor.
            radians = TAU / 2 - Math.atan(x / y);
        } else { // (y == 0)
            if (x > 0) {
                // East
                radians = TAU / 4;
            } else if (x < 0) {
                // West
                radians = -TAU / 4;
            } else {
                // X and Y are both 0; no angle
                throw new ArithmeticException("Angle.fromCartesian: x and y are both 0");
            }
        }
        return new Angle(radians);
    }
    
    // ====================================================================================
    // = Angle calculations
    // ====================================================================================
    
    // Get the angular measure, in radians.
    public double getRadians() {
        return radians;
    }

    // Get the angular measure, in degrees.
    public double getDegrees() {
        return Math.toDegrees(radians);
    }
    
    // Add another Angle to this one, producing a new Angle.
    // This does not change the existing Angle object; it produces a new one.
    public Angle add(Angle other) {
        return new Angle(this.radians + other.radians);
    }

    // Subtract another Angle from this one, producing a new Angle.
    // This does not change the existing Angle object; it produces a new one.
    public Angle subtract(Angle other) {
        return new Angle(this.radians - other.radians);
    }
    
    // Limit the magnitude of this angle to a certain number of radians.
    // If abs(angle) is smaller than "max", just returns the angle.
    // If the angle is greater than "max", returns the maximum angle in the give direction.
    public Angle limit(double max) {
        if (Math.abs(this.radians) < Math.abs(max)) {
            // Within limit; return the angle
            return this;
        } else {
            // Too far; return maximum with the sign of this angle
            return new Angle(Math.copySign(max, this.radians));
        }
    }

    // Get the X component of a vector with this angle and a given length.
    // If an object moves by "length" in the direction of this angle, 
    // this tells you how far it should move in the X axis.
    public double getXComponent(double length) {
        return length * Math.sin(this.radians);
    }

    // Get the Y component of a vector with this angle and a given length.
    // If an object moves by "length" in the direction of this angle, 
    // this tells you how far it should move in the Y axis.
    public double getYComponent(double length) {
        return length * -Math.cos(this.radians);
    }
    
    // ====================================================================================
    // = Overriding methods
    // ====================================================================================
    
    // Returns a hash code value for the object.
    @Override
    public int hashCode() {
        return 31 + ((Double) this.radians).hashCode();
    }

    // Indicates whether some other object is "equal to" this one
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        return this.radians == ((Angle) obj).radians;
    }

    // Returns a string representation of the object
    @Override
    public String toString() {
        return "Angle.fromDegrees(" + this.getDegrees() + ")";
    }
}
