package com.restaurant.validators;

import java.util.List;
import java.util.function.Predicate;

public interface Validator<T> extends Predicate<T> {
    List<String> errorStack();
}
