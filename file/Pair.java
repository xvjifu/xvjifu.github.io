package com.xjf.util;

import java.io.Serializable;
import java.util.Map.Entry;

public class Pair<K,V> implements Entry<K, V>,Serializable{

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	

	public Pair() {};
	public Pair(K key, V value) {
		this.key = key;
		this.value = value;
	}
	
	public K key;
	public V value;

	public K getKey(K[] k_arr,V[] v_arr,V v) {
		for(int i=0 ; i<v_arr.length ; i++){
			if(v.equals(v_arr[i])){
				if(k_arr.length>i){
					return k_arr[i];
				}else{
					return null;
				}
			}
		}
		return null;
	}
	

	public V getValue(K[] k_arr,K k,V[] v_arr) {
		for(int i=0 ; i<k_arr.length ; i++){
			if(k.equals(k_arr[i])){
				if(v_arr.length>i){
					return v_arr[i];
				}else{
					return null;
				}
			}
		}
		return null;
	}

	@Override
	public K getKey() {
		return key;
	}

	@Override
	public V getValue() {
		return value;
	}

	@Override
	public V setValue(V value) {
		return this.value = value;
	}
	
	@Override
	public String toString(){
		return "[" + key + ", " + value + "]" ;
	}
}
