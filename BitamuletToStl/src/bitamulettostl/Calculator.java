package bitamulettostl;

/**
 * 
 */


import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author abaoubas
 *
 */
public class Calculator {
	//Space field parameters for planetary bodies, valid to within 1 degree 1900AD-2100AD
    private static double[][] planetData =
    { 
        {0.38709927,      0.20563593,      7.00497902,      252.25032350,     77.45779628,     48.33076593},
        {0.00000037,      0.00001906,     -0.00594749,   149472.67411175,      0.16047689,     -0.12534081},
        {0.72333566,      0.00677672,      3.39467605,      181.97909950,    131.60246718,     76.67984255},
        {0.00000390,     -0.00004107,     -0.00078890,    58517.81538729,      0.00268329,     -0.27769418},
        {1.00000261,      0.01671123,     -0.00001531,      100.46457166,   102.93768193,      0.0},
        {0.00000562,     -0.00004392 ,    -0.01294668,    35999.37244981,      0.32327364,      0.0},
        {1.52371034,     0.09339410,      1.84969142,       -4.55343205,    -23.94362959,     49.55953891},
        {0.00001847,      0.00007882,     -0.00813131,    19140.30268499,      0.44441088,     -0.29257343},
        {5.20288700,      0.04838624,      1.30439695,       34.39644051,     14.72847983,   100.47390909},
        {-0.00011607,     -0.00013253,     -0.00183714,     3034.74612775,      0.21252668,      0.20469106},
        {9.53667594,      0.05386179,      2.48599187,       49.95424423,     92.59887831,    113.66242448},
        {-0.00125060,     -0.00050991,      0.00193609,     1222.49362201,     -0.41897216,     -0.28867794},
        {19.18916464,      0.04725744,      0.77263783,      313.23810451,    170.95427630,     74.01692503},
        {-0.00196176,     -0.00004397,     -0.00242939,      428.48202785,      0.40805281,      0.04240589},
        {30.06992276,      0.00859048,      1.77004347,      -55.12002969,     44.96476227,    131.78422574},
        { 0.00026291,      0.00005105,      0.00035372,      218.45945325,     -0.32241464,     -0.00508664},
        {39.48211675,      0.24882730,     17.14001206,      238.92903833,    224.06891629,    110.30393684},
        {-0.00031596,      0.00005170,      0.00004818,      145.20780515,     -0.04062942,     -0.01183482}
    };

    public static SpaceValues calcSpace2(double latitude, double longitude, long UTC, int planet){
    	double[] results = calcSpace(latitude, longitude, UTC, planet);
    	SpaceValues spaceValues = new SpaceValues(latitude, longitude, UTC, planet, results);
    	return spaceValues;
    	
    }
    
   

    //Main driver for calculation
    //Latitude and Longitude are in Degrees, and signed
    //UTC is in milliseconds since epoch time (Jan 1, 1970)
    //Planet is the order in the solar system with the Sun = 0
    public static double[] calcSpace(double latitude, double longitude, long UTC, int planet)
    {
        //Create Date object from epoch UTC
        Date temp = new Date(UTC);
        

        //Calculate angle of meridian past solar noon
        double solarAng = calcSolarAng(temp);

        //Calculate local North/East/Vertical vectors
        double[][] NEV = calcNEV(latitude, longitude);

        //Calculates location of planet
        double[] calced = calcXYZ(UTC, planet);
        //Calculates location of earth
        double[] earth = calcXYZ(UTC, 3);
        //Calculats location of the sun
        double[] sun = calcXYZ(UTC, 0);

        //Finds current rotation of earth in it's orbit
        double planeAng = Math.atan2(sun[1] - earth[1], sun[0] - earth[0]);
        planeAng += solarAng;

        //Rotates earth by the daily rotation
        for (int i = 0; i < 4; i++){
            NEV[i] = rotZ(planeAng, NEV[i]);
        }
        //Rotates earth by the orbital plane tilt
        for (int j = 0; j < 4; j++){
            NEV[j] = rotX(-23.4/180*Math.PI, NEV[j]);
        }
        //Moves rotated earth to correct location in space
        for (int k = 0; k < 4; k++){
            NEV[k] = diff(NEV[k], earth, 1.0/149597871.0);
        }
       
        //Calculates line of sight from earth observer location to object
        double[] LOS = diff(NEV[0], calced, -1);

        //Centers our NEV vectors
        for (int l = 1; l < 4; l++){
            NEV[l] = diff(NEV[0], NEV[l], -1);
        }

        //Calculates the North/East/Vertical components of the Line of SIght
        double N = NEV[1][0] * LOS[0] + NEV[1][1] * LOS[1] + NEV[1][2] * LOS[2];
        double E = NEV[2][0] * LOS[0] + NEV[2][1] * LOS[1] + NEV[2][2] * LOS[2];
        double V = NEV[3][0] * LOS[0] + NEV[3][1] * LOS[1] + NEV[3][2] * LOS[2];

        //Calculates Azimuth
        double azimuth = Math.atan2(E,N)*180/Math.PI;

        //Calculates Altitude of Line of Sight
        double lNE = Math.sqrt(N*N + E*E);
        double altitude = Math.atan2(V, lNE) * 180 / Math.PI;

        //Calculates distance from earth to object
        double distance = Math.sqrt(Math.pow((calced[0] - earth[0]), 2) + Math.pow((calced[1] - earth[1]), 2) + Math.pow((calced[2] - earth[2]), 2)); 

        //Return values as X_coor, Y_coor, Z_coor, Distance to Earth, Azimuth, Altitude in degrees
        return new double[] { calced[0], calced[1], calced[2], distance, azimuth, altitude };
    }

    //Utility function to work with 3-vectors
    static double[] diff(double[] a, double[]b, double sign){
        double[] temp = new double[3];
        for (int i = 0; i < 3; i++)
        {
            temp[i] = b[i] + sign * a[i];
        }
        return temp;
    }

    //Calculates the local north/east/vertical vectors based on lat/long
    public static double[][] calcNEV(double latitude, double longitude)
    {
        //Function constants
        double rLat = latitude * Math.PI / 180;
        double rLong = longitude * Math.PI / 180;
        double radius = 6378;

        //Centers an NEV triplet at 0,0
        double[] center = { radius, 0, 0 };
        double[] north =  { radius, 0, 1 };
        double[] east = { radius, 1, 0 };
        double[] vertical = { radius+1, 0, 0 };

        //Rotate by latitude
        center = rotY(-1.0 * rLat, center);
        north = rotY(-1.0 * rLat, north);
        east = rotY(-1.0 * rLat, east);
        vertical = rotY(-1.0 * rLat, vertical);

        //Rotate by longitude
        center = rotZ(rLong, center);
        north = rotZ(rLong, north);
        east = rotZ(rLong, east);
        vertical = rotZ(rLong, vertical);

        //return NEV triplet
        return new double[][] { center, north, east, vertical };
    }

    //Rotate by angle (radians) about x-axis
    public static double[] rotX(double angle, double[] input)
    {
        double[] output = new double[3];

        output[0] = input[0];
        output[1] = Math.cos(angle)*input[1] - Math.sin(angle)*input[2];
        output[2] = Math.sin(angle) * input[1] + Math.cos(angle) * input[2];

        return output;
    }

    //Rotate by angle (radians) about y-axis
    public static double[] rotY(double angle, double[] input)
    {
        double[] output = new double[3];

        output[0] = Math.cos(angle) * input[0] + Math.sin(angle) * input[2];
        output[1] = input[1];
        output[2] = -1.0*Math.sin(angle) * input[0] + Math.cos(angle) * input[2];

        return output;
    }

    //Rotate by angle (radians) about z-axis
    public static double[] rotZ(double angle, double[] input)
    {
        double[] output = new double[3];

        output[0] = Math.cos(angle) * input[0] - Math.sin(angle) * input[1];
        output[1] = Math.sin(angle) * input[0] + Math.cos(angle) * input[1];
        output[2] = input[2];

        return output;
    }

    //Calcualtes the angle in the sky between the sun and the prime meridian
    public static double calcSolarAng(Date temp)
    {
    	GregorianCalendar gc = new GregorianCalendar();
    	gc.setTime(temp);
        double gamma = (2.0 * Math.PI) / 365.0 * (gc.DAY_OF_YEAR - 1.0 + (gc.HOUR_OF_DAY - 12.0) / 24.0);
        double corr = 229.18 * (0.000075 + 0.001868 * Math.cos(gamma) - 0.032077 * Math.sin(gamma) - 0.014615 * Math.cos(2 * gamma) - 0.040849 * Math.sin(2 * gamma));
        double solarT = gc.HOUR_OF_DAY  * 60 + gc.MINUTE + gc.SECOND / 60 + corr;
        double ang = (solarT / 4) - 180;
        return ang*Math.PI/180.0;
    }

    //Calculates location of the planet
    public static double[] calcXYZ(long UTC, int planet)
    {
        //Sun is always in the middle
        if (planet == 0)
        {
            return new double[]{0,0,0};
        }

        planet--;

        //Function constants
        double JD = (UTC-1395921600000.0)/86400004.0 + 2456744.0;
        double T = (JD - 2451545.0) / 36525.0;

        double a = planetData[planet * 2][ 0] + planetData[planet * 2 + 1][0] * T;
        double e = planetData[planet * 2][ 1] + planetData[planet * 2 + 1][ 1] * T;
        double I = planetData[planet * 2][ 2] + planetData[planet * 2 + 1][ 2] * T;
        double L = planetData[planet * 2][ 3] + planetData[planet * 2 + 1][ 3] * T;
        double w_bar = planetData[planet * 2][ 4] + planetData[planet * 2 + 1][ 4] * T;
        double omega = planetData[planet * 2][ 5] + planetData[planet * 2 + 1][ 5] * T;

        double w = w_bar - omega;

        //Calculate mean ecentricity
        double M = L - w_bar;
        M = M % 360;
        if (M > 180)
        {
            M = -360 + M;
        }

        //Constants
        double E0 = M + 180.0 / Math.PI * e * Math.sin(M * Math.PI / 180); ;
        double delta_E;

        //Iterate to find sub orbital eccentricity
        do
        {
            double delta_M = M - (E0 - 180.0 / Math.PI * e * Math.sin(E0 * Math.PI / 180));
            delta_E = delta_M / (1 - e * Math.cos(E0 * Math.PI / 180));
            E0 = E0 + delta_E;
        } while (Math.abs(delta_E) > .000001);

        //Put in orbital frame
        double xprime = a * (Math.cos(E0 * Math.PI / 180) - e);
        double yprime = a * Math.sqrt(1 - e * e) * Math.sin(E0 * Math.PI / 180);
        double zprime = 0;

        //Rotate into heliocentric frame
        double xout = (Math.cos(w * Math.PI / 180) * Math.cos(omega * Math.PI / 180) - Math.sin(w * Math.PI / 180) * Math.sin(omega * Math.PI / 180) * Math.cos(I * Math.PI / 180)) * xprime + (-1 * Math.sin(w * Math.PI / 180) * Math.cos(omega * Math.PI / 180) - Math.cos(w * Math.PI / 180) * Math.sin(omega * Math.PI / 180) * Math.cos(I * Math.PI / 180)) * yprime;
        double yout = (Math.cos(w * Math.PI / 180) * Math.sin(omega * Math.PI / 180) + Math.sin(w * Math.PI / 180) * Math.cos(omega * Math.PI / 180) * Math.cos(I * Math.PI / 180)) * xprime + (-1 * Math.sin(w * Math.PI / 180) * Math.sin(omega * Math.PI / 180) + Math.cos(w * Math.PI / 180) * Math.cos(omega * Math.PI / 180) * Math.cos(I * Math.PI / 180)) * yprime;
        double zout = (Math.sin(w * Math.PI / 180) * Math.sin(I * Math.PI / 180)) * xprime + (Math.cos(w * Math.PI / 180) * Math.sin(I * Math.PI / 180)) * yprime;
        
        //Return
        return new double[]{xout, yout, zout};
    }
}
