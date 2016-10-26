
public class GSkyline {
    public static void main(String[] args) {
        PointSet pointSet = new PointSet();
        String path = "E:\\datasets\\anti_2.txt";
        pointSet.readFromFile(path);
        pointSet.sort();

        SkylineLayers skylineLayer = new SkylineLayers();
        skylineLayer.createSkylineLayers(pointSet);
    }
}
