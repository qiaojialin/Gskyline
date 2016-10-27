
public class GSkyline {
    public static void main(String[] args) {
        PointSet pointSet = new PointSet();
        String path = "E:\\datasets\\anti_2.txt";
        pointSet.readFromFile(path);
        pointSet.sort();

        int k = 4;
        ResultSet resultSet = new ResultSet();

        SkylineLayers skylineLayers = new SkylineLayers();
        skylineLayers.createSkylineLayers(pointSet);

        skylineLayers.makeDSG(pointSet);
        skylineLayers.preProcessing(pointSet, k, resultSet);


    }
}
