package eu.printingin3d.legobrick;

import java.io.File;
import java.io.IOException;

import eu.printingin3d.javascad.exceptions.IllegalValueException;
import eu.printingin3d.javascad.models.Abstract3dModel;
import eu.printingin3d.javascad.vrl.export.StlTextFile;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class Main {

    /**
     * @param args
     * @throws IOException
     * @throws IllegalValueException
     */
    public static void main(String[] args) throws IllegalValueException, IOException {
        if (args.length == 0) {
            args = new String[3];
            args[0] = "2012-01-10 23:13:26";
            args[1] = "37.9838100";
            args[2] = "23.7275390";
        }

        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.from(f.parse(args[0])); //
        long runOnDate = dateTime.toInstant(ZoneOffset.UTC).toEpochMilli();
        double lat = Double.parseDouble(args[1]);
        double lng = Double.parseDouble(args[2]);

        System.out.println(System.getProperty("user.dir"));
        System.out.println("Creating model 1...");
        Abstract3dModel model1 = new PlanetRing2(lat, lng, runOnDate, 1);
        new StlTextFile(new File("F:/Projects/GitHub/photoStars/test/model1.stl")).writeToFile(model1.toCSG().toFacets());
        
        System.out.println("Creating model 2...");
        Abstract3dModel model2 = new PlanetRing2(lat, lng, runOnDate, 2);
        new StlTextFile(new File("F:/Projects/GitHub/photoStars/test/model2.stl")).writeToFile(model2.toCSG().toFacets());
        
        System.out.println("Creating model 3...");
        Abstract3dModel model3 = new PlanetRing2(lat, lng, runOnDate, 3);
        new StlTextFile(new File("F:/Projects/GitHub/photoStars/test/model3.stl")).writeToFile(model3.toCSG().toFacets());

        System.out.println("MAIN Completed!");

        //Abstract3dModel model = new PlanetRing(lat, lng, System.currentTimeMillis());       
//        new SaveScadFiles(new File("F:/Projects/GitHub/photoStars/test")).
//                addModel("model.scad", model)
//                .saveScadFiles();
//         FileExporterFactory
//     .createExporter(new File("F:/Projects/GitHub/photoStars/test/lego_brick4.stl"))
//     .writeToFile(model2.toCSG().toFacets());
    }

    private static void RunIt(double lat, double lng, DateFormat df, String sdate, int i) throws ParseException {
        SpaceValues spaceValues = Calculator.calcSpace2(lat, lng, df.parse(sdate).getTime(), i);
        Print(spaceValues, df);
    }

    private static void PrintHeader() {
        writeTextFile("planet ; time ; DateTime ;"
                + "X ; Y ; Z ;"
                + "HorizAzimuth ; VertAzimuth ;"
                + "distance ;"
                + "latitude ; longitude ;\r\n");
    }

    private static void Print(SpaceValues spaceValues, DateFormat df) {
        writeTextFile(spaceValues.planet + ";" + spaceValues.time + ";" + df.format(spaceValues.time) + ";"
                + spaceValues.X + ";" + spaceValues.Y + ";" + spaceValues.Z + ";"
                + spaceValues.HorizAzimuth + ";" + spaceValues.VertAzimuth + ";"
                + spaceValues.distance
                + ";" + spaceValues.latitude + ";" + spaceValues.longitude + ";\r\n");
    }

    public static void writeTextFile(String ss, boolean append) {
        writeTextFile("F:/Projects/GitHub/photoStars/test/spaceValues.csv", ss, append);
    }

    public static void writeTextFile(String ss) {
        writeTextFile("F:/Projects/GitHub/photoStars/test/spaceValues.csv", ss);
    }

    public static void writeTextFile(String filename, String ss) {
        writeTextFile(filename, ss, true);
    }

    public static void writeTextFile(String filename, String ss, boolean append) {
        FileWriter output = null;
        try {
            output = new FileWriter(filename, append);
            BufferedWriter writer = new BufferedWriter(output);
            writer.append(ss);
            writer.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (output != null) {
                try {
                    output.flush();
                    output.close();
                } catch (IOException e) {

                }
            }
        }
    }
}
