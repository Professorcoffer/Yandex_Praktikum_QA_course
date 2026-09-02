package org.example;

import java.util.ArrayList;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();
        list.add("CAT pussy");
        list.add("DOG puppy");
        list.add("HORSE horsy");
        list.add("COW burionka");
        list.add("NOT_DEFINED wtfBro");
        list.add("CAT kitty");
        list.add("COW burionka");

        AnimalFarm farm = new AnimalFarm(list);

        farm.add(Animal.DOG, "Scoobie");
        farm.add(Animal.CAT);
        farm.add("Vasya");

        System.out.println("Проверка пересчёта животных: " + farm.countedAnimals());

        System.out.println("Проверка имён животных: " + farm.uniqueNames());

        System.out.println("Проверка вывода в строку: " + farm);


    }
}