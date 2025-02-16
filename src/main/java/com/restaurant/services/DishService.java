package com.restaurant.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.restaurant.entities.Category;
import com.restaurant.entities.Dish;
import com.restaurant.repositories.CategoryRepository;
import com.restaurant.repositories.DishRepository;
import com.restaurant.requests.DishRequest;
import com.restaurant.responses.DishResponse;
import com.restaurant.utils.Formatter;

@Service
public class DishService {

    @Value("${constants.pagination.size}")
    private int pageSize;

    private final DishRepository dishRepository;
    private final CategoryRepository categoryRepository;

    public DishService(DishRepository dishRepository, CategoryRepository categoryRepository) {
        this.dishRepository = dishRepository;
        this.categoryRepository = categoryRepository;
    }

    public DishResponse save(DishRequest dishRequest) {
        Dish dish = convertRequestToEntity(dishRequest);
        dishRepository.save(dish);
        return convertEntityToResponse(dish);
    }

    public DishResponse find(long id) {
        Dish dish = dishRepository.findById(id).orElse(null);
        return dish != null ? convertEntityToResponse(dish) : null;
    }

    public List<DishResponse> list() {
        return dishRepository.findAll().stream().map(this::convertEntityToResponse).toList();
    }

    public List<DishResponse> list(String field) {
        return dishRepository.findAll(Sort.by(field)).stream().map(this::convertEntityToResponse).toList();
    }

    public List<DishResponse> list(int page) {
        return dishRepository.findAll(PageRequest.of(page, pageSize)).map(this::convertEntityToResponse).toList();
    }

    public List<DishResponse> list(int page, String field) {
        return dishRepository.findAll(PageRequest.of(page, pageSize, Sort.by(field))).map(this::convertEntityToResponse).toList();
    }

    public long count() {
        return dishRepository.count();
    }

    private Dish convertRequestToEntity(DishRequest dishRequest) {

        Category categoryFound = categoryRepository.findById(dishRequest.categoryId()).orElse(null);
        Category category = null;

        if (categoryFound != null)
            category = Category
                    .builder()
                    .id(categoryFound.getId())
                    .name(categoryFound.getName())
                    .build();

        return Dish
                .builder()
                .id(dishRequest.id())
                .name(dishRequest.name())
                .price(dishRequest.price())
                .category(category)
                .build();
    }

    private DishResponse convertEntityToResponse(Dish dish) {
        return DishResponse
                .builder()
                .id(dish.getId())
                .name(dish.getName())
                .category(dish.getCategory().getName())
                .price(Formatter.prettyCurrency(dish.getPrice()))
                .build();
    }
}
