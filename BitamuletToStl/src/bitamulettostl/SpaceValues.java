package bitamulettostl;

public class SpaceValues {

    public long time;
    public int planet;
    public double X, Y, Z;
    public double distance;
    public double VertAzimuth;
    public double HorizAzimuth;
    public double latitude;
    public double longitude;

    public SpaceValues(double latitude, double longitude, long time, int i,
            double[] results) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.time = time;
        this.planet = i;
        this.X = results[0];
        this.Y = results[1];
        this.Z = results[2];
        this.distance = results[3];
        this.VertAzimuth = results[4];
        this.HorizAzimuth = results[5];
    }

    public double getOrbitRadius() {
        switch (planet) {
            case 0:
                return 0.1;
            case 1:
                return 0.4;
            case 2:
                return 0.8;
            case 3:
                return 1.1;
            case 4:
                return 1.8;
            case 5:
                return 5.6;

            case 6:
                return 9.5;
            case 7:
                return 21.0;
            case 8:
                return 30.0;
            case 9:
                return 35.0;
            default:
                return 100.0;

        }
    }

    public double normalizedX() {
        double min, max;
        min = -getOrbitRadius();
        max = getOrbitRadius();

        return (X - min) / (max - min);
    }

    public double normalizedY() {
        double min, max;
        min = -getOrbitRadius();
        max = getOrbitRadius();
        return (Y - min) / (max - min);
    }

    public double normalizedDistance() {
        double min, max;
        switch (planet) {
            case 0:
                min = 0.98;
                max = 1.02;
                break;
            case 1:
                min = 0.5;
                max = 1.5;
                break;
            case 2:
                min = 0.2;
                max = 1.8;
                break;
            case 3:
                min = 0;
                max = 0.1;
                break;
            case 4:
                min = 0.4;
                max = 2.6;
                break;
            case 5:
                min = 3.9;
                max = 6.4;
                break;
            case 6:
                min = 8.0;
                max = 11.0;
                break;
            case 7:
                min = 18.9;
                max = 21.1;
                break;
            case 8:
                min = 28.9;
                max = 31.2;
                break;
            case 9:
                min = 29.2;
                max = 33.8;
                break;
            default:
                min = -100.0;
                max = 100.0;
                break;
        }
        return (distance - min) / (max - min);
    }
}
