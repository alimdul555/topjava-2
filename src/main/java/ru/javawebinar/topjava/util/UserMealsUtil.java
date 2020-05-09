package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );


        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(14, 0), 2000));

    }



    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExcess> userMealWithExcesses = new ArrayList<>();
 //       Map<LocalDate,Integer> userMealByDateSumme = meals.stream().collect(Collectors.groupingBy(UserMeal::getDate, Collectors.summingInt(UserMeal::getCalories)));
        Map<LocalDate,List<UserMeal>> userMealByDate = meals.stream().collect(Collectors.groupingBy(UserMeal::getDate));

        for(Map.Entry<LocalDate,List<UserMeal>> entry: userMealByDate.entrySet()){
            int sum = entry.getValue().stream().mapToInt(userMeal -> userMeal.getCalories()).sum();
            for (UserMeal userMeal: entry.getValue()){
                if (TimeUtil.isBetweenHalfOpen(userMeal.getDateTime().toLocalTime(), startTime, endTime) && sum > caloriesPerDay )
                {
                    userMealWithExcesses.add(new UserMealWithExcess(userMeal.getDateTime(),userMeal.getDescription(),userMeal.getCalories(), true));
                }

            }

        }

        return userMealWithExcesses;
    }


}
