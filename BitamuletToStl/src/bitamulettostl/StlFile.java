package bitamulettostl;

import bitamulettostl.files.FilesLoader;
import java.util.ArrayList;
import java.util.List;

import eu.printingin3d.javascad.context.IColorGenerationContext;
import eu.printingin3d.javascad.coords.Boundaries3d;
import eu.printingin3d.javascad.coords.Boundary;
import eu.printingin3d.javascad.coords.Coords3d;
import eu.printingin3d.javascad.models.Abstract3dModel;
import eu.printingin3d.javascad.models.Atomic3dModel;
import eu.printingin3d.javascad.models.SCAD;
import eu.printingin3d.javascad.vrl.CSG;
import eu.printingin3d.javascad.vrl.FacetGenerationContext;
import eu.printingin3d.javascad.vrl.Polygon;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Represents a cuboid.
 *
 * @author ivivan <ivivan@printingin3d.eu>
 */
public class StlFile extends Atomic3dModel {

    private String filename;

    public StlFile(String filename) {
        super();
        this.filename = filename;
    }

    @Override
    protected SCAD innerToScad(IColorGenerationContext context) {
        return new SCAD("import(\"" + filename + "\");\n");
    }

    @Override
    protected Boundaries3d getModelBoundaries() {
        return new Boundaries3d(
                new Boundary(-1, 1),
                new Boundary(-1, 1),
                new Boundary(-1, 1));
    }

    @Override
    protected Abstract3dModel innerCloneModel() {
        return new StlFile(filename);
    }

    @Override
    protected CSG toInnerCSG(FacetGenerationContext context) {
        List<Float> readVertices = ReadStl();

        List<Polygon> polygons = new ArrayList<>();
        for (int i = 0; i < readVertices.size(); i += 9) {
            List<Coords3d> vertices = new ArrayList<>();
            for (int j = 0; j < 9; j += 3) {
                Coords3d pos = new Coords3d(
                        readVertices.get(i + j),
                        readVertices.get(i + j + 1),
                        readVertices.get(i + j + 2)
                );
                vertices.add(pos);
            }

            polygons.add(Polygon.fromPolygons(vertices, context.getColor()));
        }
        return new CSG(polygons);

    }

    //From :https://github.com/kshoji/STLViewer/blob/master/src/jp/kshoji/stlviewer/object/STLObject.java
    public List<Float> ReadStl() {

        InputStream inputStream = null;
        try {           
                      
            inputStream = FilesLoader.class.getResourceAsStream(filename);
            int n = 0;
            byte[] buffer = new byte[4];
            inputStream.skip(80);
            n = inputStream.read(buffer);
            //System.out.println("n==" + n);
            int size = getIntWithLittleEndian(buffer, 0);
            //System.out.println("size==" + size);
            List<Float> normalList = new ArrayList<Float>();
            List<Float> vertexList = new ArrayList<Float>();
            for (int i = 0; i < size; i++) {
                //normal
                for (int k = 0; k < 3; k++) {
                    inputStream.read(buffer);
                    normalList.add(Float.intBitsToFloat(getIntWithLittleEndian(buffer, 0)));
                }

                for (int j = 0; j < 3; j++) {
                    inputStream.read(buffer);
                    float x = Float.intBitsToFloat(getIntWithLittleEndian(buffer, 0));
                    inputStream.read(buffer);
                    float y = Float.intBitsToFloat(getIntWithLittleEndian(buffer, 0));
                    inputStream.read(buffer);
                    float z = Float.intBitsToFloat(getIntWithLittleEndian(buffer, 0));
                    adjustMaxMin(x, y, z);
                    vertexList.add(x);
                    vertexList.add(y);
                    vertexList.add(z);
                }
                inputStream.skip(2);

            }
//            System.out.println("normalList size== " + normalList.size());
//            System.out.println("vertexList size== " + vertexList.size());
            return vertexList;
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public float maxX = Float.MIN_VALUE;
    public float maxY = Float.MIN_VALUE;
    public float maxZ = Float.MIN_VALUE;
    public float minX = Float.MAX_VALUE;
    public float minY = Float.MAX_VALUE;
    public float minZ = Float.MAX_VALUE;

    private void adjustMaxMin(float x, float y, float z) {
        if (x > maxX) {
            maxX = x;
        }
        if (y > maxY) {
            maxY = y;
        }
        if (z > maxZ) {
            maxZ = z;
        }
        if (x < minX) {
            minX = x;
        }
        if (y < minY) {
            minY = y;
        }
        if (z < minZ) {
            minZ = z;
        }
    }

    private int getIntWithLittleEndian(byte[] stlBytes, int offset) {
        return (0xff & stlBytes[offset]) | ((0xff & stlBytes[offset + 1]) << 8) | ((0xff & stlBytes[offset + 2]) << 16) | ((0xff & stlBytes[offset + 3]) << 24);
    }
}
