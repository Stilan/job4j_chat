package ru.job4j.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.dto.UserDTO;
import ru.job4j.model.Address;
import ru.job4j.model.User;



import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

@RestController
public class ExampleController {


    private final Map<Integer, Address> addressHashMap = new HashMap<>(Map.ofEntries(
            Map.entry(1, new Address(
                    1, "Russia", "Moscow", "Gogolya", "5a"
            )),
            Map.entry(2, new Address(
                    2, "Russia", "St. Petersburg", "Dostoevskogo", "10"
            )),
            Map.entry(3, new Address(
                    3, "Russia", "Ufa", "Aksakova", "93"
            ))
    ));
    private final Map<Integer, User> userHashMap = new HashMap<>(Map.ofEntries(
            Map.entry(1, new  User(1, "Dima", "Ivanov", addressHashMap.get(1))
            )

    ));


    @GetMapping("/all")
    public List<Address> all(){
        List<Address> list = new ArrayList<Address>(addressHashMap.values());
        return list;
    }

    @PostMapping("/example1")
    public User example1(@RequestBody UserDTO userDTO) {
        var address = addressHashMap.get(userDTO.getAddressId());
        var user = new User(userDTO.getName(), userDTO.getSurname());
        user.setAddress(address);
        userHashMap.put(user.getId(), user);
        return userHashMap.get(user.getId());
    }

    @PatchMapping("/example2")
    public Address example2(@RequestBody Address address) throws InvocationTargetException, IllegalAccessException {
        var current = addressHashMap.get(address.getId());
        if (current == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        var methods = current.getClass().getDeclaredMethods();
        /* представляющих все методы, определенные в этом объекте класса.*/
        var namePerMethod = new HashMap<String, Method>();
        /* HashMap методов */
        for (var method: methods) {
            var name = method.getName();
            /*начинается ли строка с указанного префикса */
            if (name.startsWith("get") || name.startsWith("set")) {
                namePerMethod.put(name, method);
            }
        }
        /*возвращает набор всех ключей отображе */
        for (var name : namePerMethod.keySet()) {
            if (name.startsWith("get")) {
                var getMethod = namePerMethod.get(name);
                var setMethod = namePerMethod.get(name.replace("get", "set"));
                if (setMethod == null) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            "Impossible invoke set method from object : " + current + ", Check set and get pairs.");
                }
                /* getMethod.invoke(address); */
                var newValue = getMethod.invoke(address);
                if (newValue != null) {
                    /* объекта (Object obj, Object... args) obj - объект, из которого вызывается базовый метод
                       args - аргументы, используемые для вызова метода*/
                    setMethod.invoke(current, newValue);
                }
            }
        }
        addressHashMap.put(address.getId(), address);
        return current;
    }

    @PostMapping("/example3")
    public Map<String, String> saveAddress(@RequestBody Map<String, String> body) {
        var address = new Address(
                0, body.get("country"), body.get("city"), body.get("street"), body.get("house")
        );
        address.setId(addressHashMap.size() + 1);
        addressHashMap.put(address.getId(), address);
        return Map.of(
                "id", String.valueOf(address.getId()),
                "country", address.getCountry(),
                "city", address.getCity(),
                "street", address.getStreet(),
                "house", address.getHouse()
        );
    }
}