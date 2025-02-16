package com.restaurant.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.restaurant.entities.Category;
import com.restaurant.entities.Dish;
import com.restaurant.repositories.CategoryRepository;
import com.restaurant.repositories.DishRepository;
import com.restaurant.requests.CategoryRequest;
import com.restaurant.responses.CategoryFoundResponse;
import com.restaurant.responses.CategorySavedResponse;
import com.restaurant.utils.Formatter;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CategoryService {

    private final CategoryRepository categoriesRepository;
    private final DishRepository dishRepository;

    public List<CategoryFoundResponse> listCategories() {
        return categoriesRepository.findAll().stream()
                .map(this::convertEntityToResponse)
                .toList();
    }

    public CategoryFoundResponse findCategory(long id) {
        Optional<Category> category = categoriesRepository.findById(id);
        return category.map(this::convertEntityToResponse).orElse(null);
    }

    public CategorySavedResponse save(CategoryRequest categoryRequest) {
        Category category = convertRequestToEntity(categoryRequest);
        categoriesRepository.save(category);
        return CategorySavedResponse.builder().id(category.getId()).name(category.getName()).build();
    }

    public CategorySavedResponse modify(long id, CategoryRequest categoryRequest) {
        Category category = categoriesRepository.findById(id).orElse(null);
        category.setName(categoryRequest.name());
        categoriesRepository.save(category);
        return CategorySavedResponse.builder().id(category.getId()).name(category.getName()).build();
    }

    public void remove(long id) {
        Category category = categoriesRepository.findById(id).orElse(null);
        if (category == null)
            return;
        category.getDishes().forEach(dish -> dish.setCategory(null));
        dishRepository.saveAll(category.getDishes());
        categoriesRepository.delete(category);
    }

    public boolean exits(long id) {
        return categoriesRepository.existsById(id);
    }

    private Category convertRequestToEntity(CategoryRequest categoryRequest) {
        return Category
                .builder()
                .name(categoryRequest.name())
                .dishes(List.of())
                .build();
    }

    private CategoryFoundResponse convertEntityToResponse(Category category) {
        return CategoryFoundResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .dishes(category.getDishes().stream()
                        .map(this::convertDishToResponse)
                        .toList())
                .build();
    }

    private CategoryFoundResponse.DishResponse convertDishToResponse(Dish dish) {
        return CategoryFoundResponse.DishResponse.builder()
                .id(dish.getId())
                .name(dish.getName())
                .price(Formatter.prettyCurrency(dish.getPrice()))
                .build();
    }
}
