package com.hthk.common.utils;

import org.reflections.Reflections;
import org.reflections.util.ConfigurationBuilder;

import java.util.HashSet;
import java.util.Set;

public class CustomAnnotationUtils {

    public static <T> Set<Class<? extends T>> filter(
            String packageFolder,
            Class<T> clz,
            Class anno
    ) {
        Reflections reflections = new Reflections(new ConfigurationBuilder().forPackages(packageFolder));
        Set<Class<? extends T>> supertypeSet = reflections.getSubTypesOf(clz);
        Set<Class<? extends T>> filterSet = new HashSet<>();
        for (Class<? extends T> subClz : supertypeSet) {
            if (subClz.getAnnotation(anno) != null) {
                filterSet.add(subClz);
            }
        }
        return filterSet;
    }

}
