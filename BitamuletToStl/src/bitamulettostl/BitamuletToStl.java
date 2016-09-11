/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bitamulettostl;

import eu.printingin3d.javascad.exceptions.IllegalValueException;
import eu.printingin3d.javascad.models.Abstract3dModel;
import eu.printingin3d.javascad.vrl.export.StlTextFile;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author abaoubas
 */
public class BitamuletToStl {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IllegalValueException, IOException {
        if (args.length == 0) {
            args = new String[3];
            args[0] = "2012-10-18 23:13:26";
            args[1] = "37.9838100";
            args[2] = "23.7275390";
        }

        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.from(f.parse(args[0])); //
        long runOnDate = dateTime.toInstant(ZoneOffset.UTC).toEpochMilli();
        double lat = Double.parseDouble(args[1]);
        double lng = Double.parseDouble(args[2]);

        Create3Models(lat, lng, runOnDate);
    }

//    private static void RunIt(double lat, double lng, DateFormat df, String sdate, int i) throws ParseException {
//        SpaceValues spaceValues = Calculator.calcSpace2(lat, lng, df.parse(sdate).getTime(), i);
//        Print(spaceValues, df);
//    }
//
//    private static void PrintHeader() {
//        writeTextFile("planet ; time ; DateTime ;"
//                + "X ; Y ; Z ;"
//                + "HorizAzimuth ; VertAzimuth ;"
//                + "distance ;"
//                + "latitude ; longitude ;\r\n");
//    }
//
//    private static void Print(SpaceValues spaceValues, DateFormat df) {
//        writeTextFile(spaceValues.planet + ";" + spaceValues.time + ";" + df.format(spaceValues.time) + ";"
//                + spaceValues.X + ";" + spaceValues.Y + ";" + spaceValues.Z + ";"
//                + spaceValues.HorizAzimuth + ";" + spaceValues.VertAzimuth + ";"
//                + spaceValues.distance
//                + ";" + spaceValues.latitude + ";" + spaceValues.longitude + ";\r\n");
//    }
//
//    public static void writeTextFile(String ss, boolean append) {
//        writeTextFile("F:/Projects/GitHub/photoStars/test/spaceValues.csv", ss, append);
//    }
//
//    public static void writeTextFile(String ss) {
//        writeTextFile("F:/Projects/GitHub/photoStars/test/spaceValues.csv", ss);
//    }
//
//    public static void writeTextFile(String filename, String ss) {
//        writeTextFile(filename, ss, true);
//    }
//
//    public static void writeTextFile(String filename, String ss, boolean append) {
//        FileWriter output = null;
//        try {
//            output = new FileWriter(filename, append);
//            BufferedWriter writer = new BufferedWriter(output);
//            writer.append(ss);
//            writer.close();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        } finally {
//            if (output != null) {
//                try {
//                    output.flush();
//                    output.close();
//                } catch (IOException e) {
//
//                }
//            }
//        }
//    }
    public static String Create3Models(double lat, double lng, long runOnDate) throws IOException {
        String outputPath = System.getProperty("user.dir") + "\\MODELS\\";
        File theDir = new File(outputPath);
        // if the directory does not exist, create it
        if (!theDir.exists()) {
            System.out.println("creating directory: " + outputPath);
            boolean result = false;
            try {
                theDir.mkdir();
                result = true;
            } catch (SecurityException se) {
                System.out.println("ERROR! Cannot create director. " + se.toString());
                return "ERROR! Cannot create director. " + se.toString();
            }
        }
        System.out.println("Output will be at " + outputPath);
        System.out.println("Creating model 1...");
        Abstract3dModel model1 = new PlanetRing2(lat, lng, runOnDate, 1);
        String f1 = outputPath + "model1.stl";
        new StlTextFile(new File(f1)).writeToFile(model1.toCSG().toFacets());
        System.out.println("You can find the SQUARE version at " + f1);
        System.out.println("Creating model 2...");
        Abstract3dModel model2 = new PlanetRing2(lat, lng, runOnDate, 2);
        String f2 = outputPath + "model2.stl";
        new StlTextFile(new File(f2)).writeToFile(model2.toCSG().toFacets());
        System.out.println("You can find the CIRCLE version at " + f2);
        System.out.println("Creating model 3...");
        Abstract3dModel model3 = new PlanetRing2(lat, lng, runOnDate, 3);
        String f3 = outputPath + "model3.stl";
        new StlTextFile(new File(f3)).writeToFile(model3.toCSG().toFacets());
        System.out.println("You can find the ELLIPSE version at " + f3);
        System.out.println("Completed!");

        //Abstract3dModel model = new PlanetRing(lat, lng, System.currentTimeMillis());
//        new SaveScadFiles(new File("F:/Projects/GitHub/photoStars/test")).
//                addModel("model.scad", model)
//                .saveScadFiles();
//         FileExporterFactory
//     .createExporter(new File("F:/Projects/GitHub/photoStars/test/lego_brick4.stl"))
//     .writeToFile(model2.toCSG().toFacets());
        StringBuilder sb = new StringBuilder();
        sb.append("You can find the SQUARE version at " + f1);
        sb.append(System.getProperty("line.separator"));
        sb.append("You can find the CIRCLE version at " + f2);
        sb.append(System.getProperty("line.separator"));
        sb.append("You can find the ELLIPSE version at " + f3);
        sb.append(System.getProperty("line.separator"));

        return sb.toString();
    }
}
