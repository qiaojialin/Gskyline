
public class GSkyline {
    public static void main(String[] args) {
        PointSet pointSet = new PointSet();
        String path = "E:\\datasets\\test.txt";
        pointSet.readFromFile(path);
        pointSet.sort();
//        pointSet.print();

        SkylineLayers skylineLayers = new SkylineLayers();
        skylineLayers.createSkylineLayers(pointSet);
//        skylineLayers.print();

        int k = 4;
        ResultSet resultSet = new ResultSet();
        skylineLayers.makeDSG(pointSet);
        skylineLayers.preProcessing(pointSet, k, resultSet);
//        skylineLayers.print();
//        System.out.println();

        new UnitWise().generateGroups(skylineLayers, pointSet, k, resultSet);
        resultSet.print();
    }
}
