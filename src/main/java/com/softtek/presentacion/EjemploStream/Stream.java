package com.softtek.presentacion.EjemploStream;

import com.softtek.modelo.Cliente;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Comparator.comparingInt;
import static java.util.Comparator.reverseOrder;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

class ProbarStream {
    private void m1Buscar(List<Cliente> list, String searchText) {
        Predicate<Cliente> fx = e -> e.getTrabajo().toLowerCase().contains(searchText);

        list.stream()
                .filter(fx)
                .forEach(System.out::println);
    }

    private void m2Descendente(List<Cliente> list) {
        Comparator<Cliente> inverse = (x1, x2) -> x2.getIdCliente() - x1.getIdCliente();

        list.stream()
                .sorted(inverse)
                .forEach(System.out::println);
    }

    private void m3Adulto(List<Cliente> list) {
        Predicate<Cliente> isAdult = e -> Period.between(e.getFNacimiento(), LocalDate.now()).getYears() >= 18;

        list.stream()
                .filter(isAdult)
                .forEach(System.out::println);
    }

    public void m4MayorAdulto(List<Cliente> list) {
        list.stream()
                .sorted(Comparator.comparing(Cliente::getFNacimiento)) //e -> e.getBirthDate()
                .limit(1)
                .forEach(System.out::println);
    }

    public void m5MaxMinSalario(List<Cliente> list) {
        double max = list.stream()
                .mapToDouble(Cliente::getSalario)
                .max()
                .orElse(0);

        double min = list.stream()
                .mapToDouble(Cliente::getSalario)
                .min()
                .orElse(0);

        Cliente cli = list.stream()
                .max(Comparator.comparing(Cliente::getSalario))
                .orElse(new Cliente());

        System.out.println("Max Salary: " + max);
        System.out.println("Min Salary: " + min);
        System.out.println("Cliente Max Salary " + cli);
    }

    public void m6Promedio(List<Cliente> list) {
        double avg = list.stream()
                .mapToDouble(Cliente::getSalario)
                .average()
                .orElse(0);
        System.out.println(avg);
    }

    public void m7Resumen(List<Cliente> list) {
        DoubleSummaryStatistics stats = list.stream()
                .mapToDouble(Cliente::getSalario)
                .summaryStatistics();

        System.out.println(stats);
        System.out.println("Count: " + stats.getCount());
        System.out.println("Average: " + stats.getAverage());
        System.out.println("Max: " + stats.getMax());
        System.out.println("Min: " + stats.getMin());
        System.out.println("Sum: " + stats.getSum());
    }

    public void m8Distinto(List<Cliente> list) {
        list.stream()
                .distinct()
                .forEach(System.out::println);
    }

    public void m9Contar(List<Cliente> list) {
        long count1 = list.stream()
                //.filter()
                .count();
        int count2 = list.size();

        System.out.println(count1);
        System.out.println(count2);
    }

    public void m10skipLimit(List<Cliente> list) {
        list.stream()
                .skip(4)
                .limit(2)
                .forEach(System.out::println);
    }

    public void m11getAnyYounger(List<Cliente> list) {
        Predicate<Cliente> isYounger = e -> Period.between(e.getFNacimiento(), LocalDate.now()).getYears() < 18;

        boolean rpta = list.stream()
                .anyMatch(isYounger);

        System.out.println("Is any younger: " + rpta);
    }

    public void m12map(List<Cliente> list) {
        list.stream()
                .map(e -> {
                    e.setSalario(e.getSalario() * 1.10);
                    return e.getSalario();
                })
                .forEach(System.out::println);
    }

    public void m12flatMap(List<Cliente> list) {
        list.stream()
                .flatMap(e -> {
                    e.setSalario(e.getSalario() * 1.10);
                    return Stream.of(e, "a", "b");
                })
                .forEach(System.out::println);
    }

    public void m14peek(List<Cliente> list) {
        List<Cliente> newList = list.stream()
                .filter(e -> e.getSalario() > 3000)
                .peek(System.out::println)
                .filter(e -> e.getSalario() > 4000)
                .peek(System.out::println)
                .collect(Collectors.toList());
    }

    public void m15GroupBy(List<Cliente> list) {
        Map<String, List<Cliente>> byCountry = list.stream()
                .collect(groupingBy(Cliente::getPais));

        Map<String, Map<String, List<Cliente>>> byCountryAndJob = list.stream()
                .collect(groupingBy(Cliente::getPais, groupingBy(Cliente::getTrabajo)));

        System.out.println(byCountry);
        System.out.println(byCountryAndJob);
    }

    public void m16ToMapToSet(List<Cliente> list) {
        Map<Integer, Cliente> map = list.stream()
                .collect(toMap(Cliente::getIdCliente, Function.identity()));

        //Set<Cliente> set = list.stream().collect(toSet());
        Set<Cliente> set = new HashSet<>(list);

        System.out.println(map.keySet());
        System.out.println(map.values());
        map.entrySet().forEach(System.out::println);
    }

    public void m17Order(List<Cliente> list) {
        list.stream()
                .sorted(comparingInt(Cliente::getIdCliente).reversed())
                .forEach(System.out::println);

        Stream.of(1, 2, 4, 5)
                .sorted(reverseOrder())
                .forEach(System.out::println);
    }

    public static void main(String[] args) {
        ProbarStream appStream = new ProbarStream();

        Cliente e1 = new Cliente(1, "Cliente1", "Trainee Developer", LocalDate.of(1991, 1, 1), 1000.00, "Peru");
        Cliente e2 = new Cliente(2, "Cliente2", "QA", LocalDate.of(1993, 2, 1), 2000.00, "Peru");
        Cliente e3 = new Cliente(3, "Cliente3", "Architect", LocalDate.of(1995, 3, 1), 3000.00, "Paraguay");
        Cliente e4 = new Cliente(4, "Cliente4", "Cloud Engineer", LocalDate.of(1987, 4, 1), 4000.00, "Colombia");
        Cliente e5 = new Cliente(5, "Cliente5", "DevOps Engineer", LocalDate.of(1956, 1, 1), 5000.00, "Colombia");
        Cliente e6 = new Cliente(6, "Cliente6", "Scrum Master", LocalDate.of(2002, 11, 1), 4500.00, "Argentina");
        Cliente e7 = new Cliente(7, "Cliente7", "Leader Project", LocalDate.of(1998, 12, 1), 10000.00, "Mexico");
        Cliente e8 = new Cliente(8, "Cliente8", "Senior Developer", LocalDate.of(1996, 7, 1), 7000.00, "Rep. Dominicana");
        Cliente e9 = new Cliente(9, "Cliente9", "Junior Developer", LocalDate.of(2005, 8, 1), 500.00, "Ecuador");
        Cliente e10 = new Cliente(10, "Cliente10", "Mobile Developer", LocalDate.of(1975, 9, 1), 3000.00, "Chile");
        Cliente e11 = new Cliente(11, "Cliente11", "Accounting", LocalDate.of(1985, 7, 3), 2000.00, "España");
        Cliente e12 = new Cliente(12, "Cliente12", "Manager", LocalDate.of(1980, 9, 2), 2000.00, "Mexico");
        Cliente e13 = new Cliente(13, "Cliente13", "Manager II", LocalDate.of(1980, 9, 2), 2000.00, "Peru");


        List<Cliente> list = Arrays.asList(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10, e11, e12, e13);

        ProbarStream stream= new ProbarStream();

        stream.m16ToMapToSet(list);
    }
}