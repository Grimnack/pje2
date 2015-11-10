package models;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class CollectionUtil {

	public static <K, V extends Comparable<? super V>> Map<K, V>  mapSortByValue( Map<K, V> map ){
		List<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K, V>>( map.entrySet() );
		Collections.sort( list, new Comparator<Map.Entry<K, V>>(){
		
			public int compare( Map.Entry<K, V> o1, Map.Entry<K, V> o2 ){
				return (o1.getValue()).compareTo( o2.getValue() );
			}
		});

		Map<K, V> result = new LinkedHashMap<K, V>();
		for (Map.Entry<K, V> entry : list){
			result.put( entry.getKey(), entry.getValue() );
		}
		return result;

	}
	
	public static int listGetAvg(List<Double> list){
		int sum = 0;
		for(Double d : list)
			sum += d;
		return sum / list.size();
		
	}
	
	public static Polarite getPolariteFromHighestProb(Map<Polarite, Double> map){
		if(map.get(Polarite.NEGATIF) > map.get(Polarite.NEUTRE) && map.get(Polarite.NEGATIF) > map.get(Polarite.POSITIF))
			return Polarite.NEGATIF;
		else if(map.get(Polarite.NEUTRE) > map.get(Polarite.POSITIF))
			return Polarite.NEUTRE;
		
		return Polarite.POSITIF;

	}
}
