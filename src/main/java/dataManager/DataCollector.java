package dataManager;

import annotations.Life;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;
import life.Organism;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DataCollector {
    public List<Class<?>> getAllClassesInClasspath() {
        try (ScanResult scanResult = new ClassGraph().enableAllInfo().scan()) {
            return scanResult.getAllClasses().loadClasses(true);
        }
    }

    public List<Class<?>> getAllLife() {
        List<Class<?>> allClasses = getAllClassesInClasspath();
        return allClasses.stream().filter(currentClass ->
                currentClass.isAnnotationPresent(Life.class)
        ).collect(Collectors.toList());
    }

    public List <Organism> getOrganismList() {
        List<Organism> organismList = new ArrayList<>();
        List<Class<?>> allClasses = getAllLife();
        for (Class<?> clazz : allClasses) {
            Object object = null;
            try {
                object = clazz.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (object instanceof Organism) {
                organismList.add((Organism) object);
            }
        }
        return organismList;
    }
}
