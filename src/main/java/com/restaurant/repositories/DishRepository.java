package com.restaurant.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.restaurant.entities.Dish;

@Repository
public interface DishRepository extends JpaRepository<Dish, Long> {
}
