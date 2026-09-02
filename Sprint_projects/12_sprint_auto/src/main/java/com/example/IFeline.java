package com.example;

import java.util.List;

public interface IFeline extends Predator {
    int getKittens();

    int getKittens(int count);

    List<String> getFood(String animalKind) throws Exception;
}
