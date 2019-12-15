package com.falcon;

import java.util.HashSet;
import java.util.Set;

public class LibraryMethods{
    
    public static <E> Set<E> singletonSet(E elt){
        java.util.Set<E> out = new HashSet<>();
        out.add(elt);
        return out;
    }
    
    public static <E> Set<E> EmptySet(){
        java.util.Set<E> out = new HashSet<>();
        return out;
    }
    
    public static <E> Set<E> setUnion(Set<E> set1, Set<E> set2){
        java.util.Set<E> out = new HashSet<>();
        out.addAll(set1);
        out.addAll(set2);
        return out;
    }
}