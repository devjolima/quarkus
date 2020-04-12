package br.com.food.controller;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import br.com.food.entity.Food;

@ApplicationScoped
public class FoodController {

    public Food update(Long id, Food food) {
        Food foodEntity = Food.findById(id);
        if (foodEntity == null) {
            throw new WebApplicationException("Food with id of " + id + " does not exist.", Response.Status.NOT_FOUND);
        }
        foodEntity.setName(food.getName());
        foodEntity.setCalories(food.getCalories());
        return foodEntity;
    }

    /**
     * This method is main purpose to show simple "Business" example
     * @param food
     * @return
     */
    public boolean isFoodNameIsNotEmpty(Food food) {
        return food.getName().isEmpty();
 
   }

}