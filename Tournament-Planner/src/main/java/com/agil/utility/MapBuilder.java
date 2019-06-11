package com.agil.utility;

import java.util.HashMap;

public class MapBuilder<K,V> {
	
	private HashMap<K, V> hashMap;
	
	public MapBuilder() {
		hashMap = new HashMap<>();
	}
	
	public MapBuilder<K,V> addPair(K k, V v) {
		hashMap.put(k, v);
		return this;
	}
	
	public HashMap<K, V> build(){
		return hashMap;
	}
	
}
