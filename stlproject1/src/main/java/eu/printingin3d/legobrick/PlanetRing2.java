/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.printingin3d.legobrick;

import eu.printingin3d.javascad.basic.Radius;
import eu.printingin3d.javascad.coords.*;
import eu.printingin3d.javascad.exceptions.IllegalValueException;
import eu.printingin3d.javascad.models.*;
import eu.printingin3d.javascad.tranzitions.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author abaoubas
 */
public class PlanetRing2 extends Union {

    private static final double SUN_RADIUS = 3.5;
    private static final double AXON_RADIUS = 0.45;
    private static final double PLANET_RADIUS = 0.85;

    private static double ORBIT_RADIUS = 1.7;
    private static double CASE_SIZE = 31;
    private static final double TILT = 2;
    private static double ECLIPSE_SMALL = 20;
    private static double ECLIPSE_LARGE = 25;

    private static String stlFileBasePath = "F:\\Projects\\GitHub\\photoStars\\stlproject1\\src\\main\\resources\\stl\\";

    public PlanetRing2(double lat, double lng, long date, int type) {
        super(getModels(lat, lng, date, type));
    }

    private static List<Abstract3dModel> getModels(double lat, double lng, long date, int type) {
        List<Abstract3dModel> models = new ArrayList<>();

        Abstract3dModel modelcase;
        switch (type) {

            case 2:
                modelcase =  new Translate(new StlFile(stlFileBasePath + "bracelet_3_2.STL"), new Coords3d(0, 52, 0)).rotate(Angles3d.zOnly(90));
                //max orbit = 16, sun is 3.5 + (2*0.85) spare, so 10.8 / (8-1) = 1.5429
                ORBIT_RADIUS = 1.5429;
                CASE_SIZE = 32;
                break;
            case 3:
                modelcase = new Translate(new StlFile(stlFileBasePath + "bracelet_eclipse.STL"), new Coords3d(0, 200, 0));
                //max orbit = 20, sun is 3.5 + (2*0.85) spare, so 14.8 / (8-1) = 2.1143
                ORBIT_RADIUS = 2.1143;
                ECLIPSE_SMALL = 20;
                ECLIPSE_LARGE = 25;
                break;
            case 1:
            default:
                //max orbit = 15.5, sun is 3.5 + (2*0.85) spare, so 10.3 / (8-1) = 1.4714
                ORBIT_RADIUS = 1.4714;
                CASE_SIZE = 31;
                modelcase = new Translate(new StlFile(stlFileBasePath + "case (repaired).stl"), new Coords3d(-120.8, -54.5, 1.5));
                break;
        }

        //models.add(modelcase_repaired_);
        models.add(modelcase);
        Abstract3dModel model_anaglufo = getSun();
        models.add(model_anaglufo);
        //models.add(new Union(model_anaglufo, modelcase_repaired_));

//        for (int i = 1; i < 24; i += 1) {
//            date += TimeUnit.MILLISECONDS.convert(15, TimeUnit.DAYS);
        models.add(getPlanetOrbit(PLANET_RADIUS, lat, lng, date, 1, type)); //Mercury
        models.add(getPlanetOrbit(PLANET_RADIUS, lat, lng, date, 2, type));//Venus
        models.add(getPlanetOrbit(PLANET_RADIUS, lat, lng, date, 3, type)); //Earth
        models.add(getPlanetOrbit(PLANET_RADIUS, lat, lng, date, 4, type)); //Mars
        models.add(getPlanetOrbit(PLANET_RADIUS, lat, lng, date, 5, type)); //Jupiter
        models.add(getPlanetOrbit(PLANET_RADIUS, lat, lng, date, 6, type)); //Saturn
        models.add(getPlanetOrbit(PLANET_RADIUS, lat, lng, date, 7, type)); //Uranus
        models.add(getPlanetOrbit(PLANET_RADIUS, lat, lng, date, 8, type)); //Neptune
//        }
        return models;
    }

    private static Abstract3dModel getPlanetOrbit(double planetRadius, double lat, double lng, long date, int planetI, int type) {

        Abstract3dModel planet = getPlanet(planetRadius);
        Abstract3dModel t_planet = PositionPlanet(planet, lat, lng, date, planetI, type);
        return t_planet;
    }

    private static Abstract3dModel PositionPlanet(Abstract3dModel planet, double lat, double lng, long date, int planetI, int type) throws IllegalValueException {
        SpaceValues spaceValues = Calculator.calcSpace2(lat, lng, date, planetI);
        System.out.println(planetI + ": " + spaceValues.X + ", " + spaceValues.Y + ", " + spaceValues.Z);
        double orbitSize = SUN_RADIUS + PLANET_RADIUS + ORBIT_RADIUS * (planetI - 1);
        double oldSize = Math.sqrt(Math.pow(spaceValues.X, 2) + Math.pow(spaceValues.Y, 2));
        double sizeFactor = orbitSize / oldSize;

        double x = spaceValues.X == 0
                ? 0
                : Math.sqrt(Math.pow(Math.sqrt(Math.pow(spaceValues.X, 2) + Math.pow(spaceValues.Y, 2)) * sizeFactor, 2) / (1 + Math.pow(spaceValues.Y / spaceValues.X, 2))) * (spaceValues.X / Math.abs(spaceValues.X));

        double y = spaceValues.X == 0
                ? spaceValues.Y * sizeFactor
                : spaceValues.Y * x / spaceValues.X;

        double z = 0;

        Translate t_planet = new Translate(planet, new Coords3d(x, y, z));

        //create the axon
        double deg_in_radians = Math.atan2(y, x);
        double tilt = spaceValues.VertAzimuth > 0 ? TILT : -TILT;

        Abstract3dModel planet_with_axon = getOrbit(new Translate(planet, new Coords3d(orbitSize, 0, 0)), deg_in_radians, tilt, type);


        return planet_with_axon;
    }

    private static Abstract3dModel getOrbit(Abstract3dModel planet, double radians, double tilt, int type) {
        double deg = radians * (180 / Math.PI);
        double size = CASE_SIZE / 2;
        switch (type) {
            case 1:
                radians += Math.PI / 4;
                if (Math.cos(radians) != 0) {
                    double rad2 = radians;
                    if (rad2 < 0) {
                        rad2 = Math.PI + rad2;
                    }
                    rad2 = rad2 % (Math.PI / 2);

                    if (rad2 > Math.PI / 4) {
                        rad2 = Math.PI / 2 - rad2;
                    }
                    size = (CASE_SIZE / 2) / Math.abs(Math.cos(rad2));
                }
                break;
            case 3:
                size = ECLIPSE_SMALL * ECLIPSE_LARGE
                        / Math.sqrt(Math.pow(ECLIPSE_LARGE, 2) * Math.pow(Math.sin(radians), 2) + Math.pow(ECLIPSE_SMALL, 2) * Math.pow(Math.cos(radians), 2));

                break;
            case 2:
            default:
                break;

        }
        size = size - SUN_RADIUS + 0.5;

        Abstract3dModel axon = new Rotate(
                new Translate(
                        new Translate(
                                new Rotate(
                                        new Cylinder(size, Radius.fromRadius(AXON_RADIUS)),
                                        new Angles3d(90, 0, 0)),
                                new Coords3d(0, size / 2, 0)),
                        new Coords3d(0, SUN_RADIUS, 0)),
                new Angles3d(0, 0, -90));
        Abstract3dModel axonWithPlanet = new Union(planet, axon);
        return new Rotate(
                axonWithPlanet,
                new Angles3d(tilt, tilt, deg)
        );

    }

    static Abstract3dModel planet_hole;

    private static Abstract3dModel getPlanet(double radius) {
        return new Sphere(Radius.fromRadius(radius));
//        if (planet_hole == null) {
//            planet_hole = new Translate(new StlFile(stlFileBasePath + "planet_hole.STL"), new Coords3d(-120.76, -65.35, 1.5));
//        }
//        return planet_hole;
    }

    private static Abstract3dModel getSun() {
        //return new Translate(new StlFile(stlFileBasePath + "anaglufo.STL"), new Coords3d(-120.8, -54.5, 1.5));
        return new Union(
                new Cylinder(0.9, Radius.fromRadius(SUN_RADIUS)),
                new Cylinder(1.8, Radius.fromRadius(2.5))
        );

    }

}
