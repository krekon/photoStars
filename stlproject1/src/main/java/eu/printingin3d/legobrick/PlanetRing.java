/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.printingin3d.legobrick;

import eu.printingin3d.javascad.basic.Radius;
import eu.printingin3d.javascad.coords.*;
import eu.printingin3d.javascad.coords2d.Coords2d;
import eu.printingin3d.javascad.exceptions.IllegalValueException;
import eu.printingin3d.javascad.models.*;
import eu.printingin3d.javascad.models2d.*;
import eu.printingin3d.javascad.tranzitions.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author abaoubas
 */
public class PlanetRing extends Union {

    private static final double ORBIT_WEIGHT = 5.0;
    private static final double ORBIT_RADIUS = 100.0;

    public PlanetRing(double lat, double lng, long date) {
        super(getModels(lat, lng, date));
    }

    private static List<Abstract3dModel> getModels(double lat, double lng, long date) {
        List<Abstract3dModel> models = new ArrayList<>();

        //models.add(getSun());
        models.add(getPlanetOrbit(200, 17.9, lat, lng, date, 1)); //Mercury
        models.add(new Rotate(getPlanetOrbit(300, 28.4, lat, lng, date, 2), new Angles3d(5, 0, 45)));//Venus
        models.add(getPlanetOrbit(400, 28.5, lat, lng, date, 3)); //Earth
        models.add(getPlanetOrbit(500, 19.0, lat, lng, date, 4)); //Mars
        models.add(getPlanetOrbit(600, 50, lat, lng, date, 5)); //Jupiter

        models.add(getSaturnOrbit(700, 44.8, lat, lng, date, 6)); //Saturn
        models.add(getPlanetOrbit(800, 30.3, lat, lng, date, 7)); //Uranus
        models.add(getPlanetOrbit(900, 30.0, lat, lng, date, 8)); //Neptune

//        Translate t_planet1 = new Translate(getPlanet(20), new Coords3d(100, 0, 0));
//        Translate t_planet2 = new Translate(getPlanet(25), new Coords3d(200, 0, 0));
//        Translate t_planet3 = new Translate(getPlanet(50), new Coords3d(400, 0, 0));
//        
//        models.add(t_planet1);
//        models.add(t_planet2);
//        models.add(t_planet3);
//		models.add(addAxles(xSize, ySize));
//		models.add(getKnobs(base, xSize, ySize));
        return models;
    }

    private static Abstract3dModel getPlanetOrbit(double orbitRadius, double planetRadius, double lat, double lng, long date, int planetI) {

        Abstract3dModel planet = getPlanet(planetRadius);
        Translate t_planet = PositionPlanet(planet, lat, lng, date, planetI);
        return new Union(getOrbit(orbitRadius), t_planet);

    }

    private static Translate PositionPlanet(Abstract3dModel planet, double lat, double lng, long date, int planetI) throws IllegalValueException {
        SpaceValues spaceValues = Calculator.calcSpace2(lat, lng, date, planetI);
        System.out.println(planetI + ": " + spaceValues.X + ", " + spaceValues.Y + ", " + spaceValues.Z);
        double diameter = 100 + 100 * planetI;
//        double x = spaceValues.X / spaceValues.getOrbitRadius() * diameter;
//        double y = spaceValues.Y / spaceValues.getOrbitRadius() * diameter;
//        double z = spaceValues.Z / spaceValues.getOrbitRadius() * diameter;

        double tempX = spaceValues.X ;
        double tempY = spaceValues.Y ;
        double y = Math.sqrt(
                Math.pow(diameter, 2)
                - Math.pow(
                        ((Math.pow(diameter, 2) * Math.pow(tempX, 2)) / (Math.pow(tempX, 2) + Math.pow(tempY, 2)))
                        , 2)
        );
        double x = Math.sqrt(Math.pow(diameter, 2) - Math.pow(y, 2));
        double z = 0;

        if (tempX < 0) {
            x = -x;
        }

        if (tempY < 0) {
            y = -y;
        }

        Translate t_planet = new Translate(planet, new Coords3d(x, y, z));
        return t_planet;
    }

    private static Abstract3dModel getOrbit(double radius) {
        Circle r = new Circle(Radius.fromRadius(ORBIT_WEIGHT));
        return new Ring(radius, r);
    }

    private static Abstract3dModel getPlanet(double radius) {
        return new Sphere(Radius.fromRadius(radius));
    }

    private static Abstract3dModel getSun() {
        return new Sphere(Radius.fromRadius(ORBIT_RADIUS));
    }

    private static Abstract3dModel getSaturn(double radius) {

        Abstract3dModel Saturn
                = new Union(
                        new Rotate(
                                new Scale(
                                        new Ring(radius, new Circle(Radius.fromDiameter(50))),
                                        new Coords3d(1, 1, 1 / 8)),
                                new Angles3d(45, -45, -45)),
                        new Sphere(Radius.fromRadius(radius))
                );
        return Saturn;
    }

    private static Abstract3dModel getSaturnOrbit(double orbitRadius, double planetRadius, double lat, double lng, long date, int planetI) {

        Translate t_planet = PositionPlanet(getSaturn(planetRadius), lat, lng, date, planetI);

        return new Union(getOrbit(orbitRadius), t_planet);

    }
}
