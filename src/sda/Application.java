package sda;

import sda.workers.AbstractEmployee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Szymon on 2017-02-18.
 */
public class Application {
    public static void main(String[] args) {
        List<AbstractEmployee> employeeList = new ArrayList<>();
        employeeList.add(new AbstractEmployee("Szymon", "Nowak", 2000, "JAVA"));
        employeeList.add(new AbstractEmployee("Jan", "Kowalski", 5000, "JAVA"));
        employeeList.add(new AbstractEmployee("Anna", "Wisniewska", 3000, "HR"));
        employeeList.add(new AbstractEmployee("Karolina", "Nowak", 3000, "PM"));
        employeeList.add(new AbstractEmployee("Andrzej", "Duda", 5000, "Director"));
        //1. Za pomoca filter wypisac tylko ludzi z dzialu java
        employeeList.stream()
                .filter(e -> e.getDepartment().equals("JAVA"))
                .forEach(e -> System.out.println(e));
        //2. za pomoca filter wypisac tylko kobiety
        System.out.println();
        employeeList.stream()
                .filter(e -> e.getFirstName().endsWith("a"))
                .forEach(e -> System.out.println(e));
        //3. Za pomoca filter wypisac osoby zarabiajace powyzej 3000
        System.out.println();
        employeeList.stream()
                .filter(e -> e.getSalary() >= 3000)
                .forEach(e -> System.out.println(e));
        //4. Za pomoca filter wypisac osoby z dzialu java zarabiajace powyzej 3000
        System.out.println();
        employeeList.stream()
                .filter(e -> e.getSalary() >= 3000)
                .filter(e -> e.getDepartment().equals("JAVA"))
                .forEach(e -> System.out.println(e));
        //5. Za pomoca filter oraz collect zebrac wszystkie osoby pracujace w dziale java i zwrocic je jako osobna liste
        List<AbstractEmployee> javaEmployees = employeeList.stream()
                .filter(e -> e.getDepartment().equals("JAVA"))
                .collect(Collectors.toList());
        System.out.println(javaEmployees);
        //6. Szukamy pracowników po nazwisku
        System.out.println();
        employeeList.stream()
                .filter(e -> e.getLastName().equals("Nowak"))
                .forEach(e -> System.out.println(e));
        //7. Szukamy pracowników po nazwisku (metoda startsWith)
        System.out.println();
        employeeList.stream()
                .filter(e -> e.getLastName().startsWith("Now"))
                .forEach(e -> System.out.println(e));

        //8. Dzielimy liste na mape ludzi gdzie klucz to ich imie, a vartosc to AbstractEmployee
        System.out.println();
        Map<String, AbstractEmployee> map = employeeList.stream()
                .collect(Collectors.toMap((e -> e.getFirstName()), e -> e));
        map.forEach((k, v) -> System.out.println(k + ": " + v));
        //9. zwracamy liste Employee szukanej po imie + " " + nazwisko
        System.out.println();
        employeeList.stream()
                .filter(e -> (e.getFirstName() + " " + e.getLastName()).equals("Szymon Nowak"))
                .forEach(e -> System.out.println(e));
        //10. posortuj po salary
        System.out.println();
        employeeList.sort((e1, e2) ->
                e1.getSalary() > e2.getSalary() ? 1 :
                e1.getSalary() == e2.getSalary() ? 0 : -1);
        employeeList.forEach(e -> System.out.println(e.getFirstName() + ": " + e.getSalary()));
        //11. wyswietl employee ktory zarabia najwiecej
        System.out.println();
        employeeList.sort((e1, e2) -> e1.getSalary() < e2.getSalary() ? 1 : -1);
        System.out.println(employeeList.get(0));

        System.out.println();
        AbstractEmployee richestEmployee = employeeList.stream()
                .max((e1, e2) -> e1.getSalary() > e2.getSalary() ? 1 : -1)
                .get();
        System.out.println(richestEmployee);
        //12. wyswietl employee ktory zarabia najmniej
        System.out.println();
        AbstractEmployee poorestEmployee = employeeList.stream()
                .min((e1, e2) -> e1.getSalary() > e2.getSalary() ? 1 : -1)
                .get();
        System.out.println(poorestEmployee);

        Map<String, List<AbstractEmployee>> map1 = listToMap(employeeList);
        List<AbstractEmployee> tmpList = new ArrayList<>();
        map1.entrySet().stream()
                .forEach(e -> {
                    List<AbstractEmployee> value = e.getValue();
                    value.stream()
                            .filter(e1 -> e1.getSalary() >= 3000)
                            .forEach(e1 -> tmpList.add(e1));
                });
        System.out.println(tmpList);
    }

    public static Map<String, List<AbstractEmployee>> listToMap(List<AbstractEmployee> employees) {
        Map<String, List<AbstractEmployee>> map = new HashMap<>();
        for (AbstractEmployee employee : employees) {
            if (map.containsKey(employee.getDepartment())) {
                List<AbstractEmployee> listOFEmployees = map.get(employee.getDepartment());
                listOFEmployees.add(employee);
            } else {
                ArrayList<AbstractEmployee> listOfEmployees = new ArrayList<>();
                listOfEmployees.add(employee);
                map.put(employee.getDepartment(), listOfEmployees);
            }
        }
        return map;
    }
}
