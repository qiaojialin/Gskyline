package algorithm;

import common.layer.SkylineLayer;
import common.layer.SkylineLayers;
import common.point.Point;
import common.point.PointSet;
import common.result.ResultSet;
import common.result.SGroup;
import common.unit.Unit;
import common.unit.Units;

import java.util.*;

public class UnitWise {

	private List<Unit> unitSet = new ArrayList<>(); //初始单元素unit的集合

	public ResultSet unitWise(String path, int k) {
		PointSet pointSet = new PointSet();
		pointSet.readFromFile(path);
		pointSet.sort();
//        pointSet.print();

		SkylineLayers skylineLayers = new SkylineLayers();
		skylineLayers.createSkylineLayers(pointSet);
//        skylineLayers.print();

		ResultSet resultSet = new ResultSet();
		skylineLayers.makeDSG(pointSet);
		skylineLayers.preProcessing(pointSet, k, resultSet);
//        skylineLayers.print();
//        System.out.println();

		new UnitWise().generateGroups(skylineLayers, pointSet, k, resultSet);
		return resultSet;
	}

	public void generateGroups(SkylineLayers layers, PointSet pointSet, int k, ResultSet resultSet) {
		List<Point> pSet = pointSet.pSet;
		for(SkylineLayer layer: layers.layers) {
			for(int p: layer.points) {
				Unit unit = pSet.get(p).unit();
				unitSet.add(unit);
			}
		}

		Collections.sort(unitSet, new Comparator<Unit>(){
			@Override
			public int compare(Unit u1, Unit u2) {
				Integer i1 = u1.size();
				Integer i2 = u2.size();
				//降序
				int result = -i1.compareTo(i2);
				if(result == 0) {
					if(u1.index > u2.index)
						result = -1;
					else if(u1.index < u2.index)
						result = 1;
				}
				return result;
			}
		});


		for(int i = 0; i< unitSet.size(); i++) {
//			System.out.println();
//			System.out.println(" 当前处理 u" + unitSet.get(i).index);

			if(lastSize(i) == k) {
				Units units = new Units();
				for(int j = i; j< unitSet.size(); j++) {
					units.add(j);
				}
				SGroup sGroup = new SGroup(unitSet, units);
//				System.out.println("@++++<<<111: " + sGroup);
				resultSet.add(sGroup);
				break;
			} else if(lastSize(i) < k) {
				break;
			}

			//candidates = G'
			List<Units> candidates = new ArrayList<>();
			Units units = new Units();
			units.add(i);
			candidates.add(units);



			while(!candidates.isEmpty()) {

				//newCandidates = G''
				List<Units> newCandidates = new ArrayList<>();

				for(Units candidate: candidates) {
//					System.out.println("candidate:" + candidate);
					//生成units的所有父节点
					List<Integer> parentSet = new ArrayList<>();
					for(int u: candidate.units) {
						Unit unit = unitSet.get(u);
						parentSet.removeAll(pSet.get(unit.index).parents);
						parentSet.addAll(pSet.get(unit.index).parents);
					}

					//初始tail set
					List<Integer> tailSet = tailSet(candidate);

					//去除tail set中是units父节点的unit
					Iterator<Integer> iterator = tailSet.iterator();
					while(iterator.hasNext()) {
						int unit = iterator.next();
						if(parentSet.contains(unitSet.get(unit).index)) {
							iterator.remove();
						}
					}

//					System.out.print("tail set: ");
//					for(int tail: tailSet) {
//						System.out.print(" " + tail);
//					}
//					System.out.println();

					//将tail set中的每一个元素添加到当前units中形成新的候选集
					for(int tail: tailSet) {
//						System.out.println("tail: " + tail);
						Units newCandidate = new Units(candidate);
						newCandidate.add(tail);
//						System.out.println("new candidate: " + newCandidate);
						//如果新的候选集的点的个数等于k，输出为结果
						if(newCandidate.pointNum(unitSet) == k) {
//							System.out.println("ok");
							SGroup sGroup = new SGroup(unitSet, newCandidate);
//							System.out.println("@++++<<<222: " + sGroup);
							resultSet.add(sGroup);
						}
						//如果新的候选集的点的个数小于k，保留
						else if(newCandidate.pointNum(unitSet) < k) {
							newCandidates.add(newCandidate);
						}
					}
				}
				//
				candidates.clear();
				candidates.addAll(newCandidates);

			}
		}
	}

	//查看此unit以及后面所有并集的元素个数
	public int lastSize(int u) {
		List<Integer> last = new ArrayList<>();
		for(int i = u; i< unitSet.size(); i++) {
			Unit unit = unitSet.get(i);
			last.removeAll(unit.unit);
			last.addAll(unit.unit);
		}
		return last.size();
	}

	//tail set
	public List<Integer> tailSet(Units units) {

		int maxIndex = units.get(units.units.size()-1);
		List<Integer> tails = new ArrayList<>();
		int i = maxIndex + 1;
		while(i < unitSet.size()) {
			if(!units.contains(i))
				tails.add(i);
			i++;
		}
		return tails;
	}
}
