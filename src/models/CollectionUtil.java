package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
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
	
	public static Polarite listGetClassFromValue(Map<Tweet, Double> map, int n){
		Map<Polarite, Double> mapNb = new HashMap<Polarite, Double>();
		mapNb.put(Polarite.NEGATIF, 0.0);
		mapNb.put(Polarite.NEUTRE, 0.0);
		mapNb.put(Polarite.POSITIF, 0.0);
		List<Tweet> tweets = new ArrayList<Tweet>(map.keySet());
		for(int i=0;i<n;i++){
			Tweet tweet = tweets.get(i);
			System.out.println(tweet.getPolarite());
			mapNb.put(tweet.getPolarite(), mapNb.get(tweet.getPolarite()) + 1);
		}
		
		return getPolariteFromHighestNb(mapNb);
		
	}
	
	public static Polarite getPolariteFromHighestNb(Map<Polarite, Double> map){
				
		if(map.get(Polarite.NEGATIF) > map.get(Polarite.NEUTRE) && map.get(Polarite.NEGATIF) > map.get(Polarite.POSITIF))
			return Polarite.NEGATIF;
		else if(map.get(Polarite.NEUTRE) > map.get(Polarite.POSITIF) && map.get(Polarite.NEUTRE) > map.get(Polarite.NEGATIF))
			return Polarite.NEUTRE;
		else if(map.get(Polarite.POSITIF) > map.get(Polarite.NEUTRE) && map.get(Polarite.POSITIF) > map.get(Polarite.NEGATIF))
			return Polarite.POSITIF;
		
		return Polarite.NEUTRE;

	}
}
