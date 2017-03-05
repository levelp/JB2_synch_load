package ru.levelp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Этот класс не нуждается в изменениях!
 */
public class Contact {
    private String name; // Фамилия Имя Отчество
    private List<String> phones; // Список телефонов

    public Contact(String name, String... phones) {
        this(name, Arrays.asList(phones));
    }

    public Contact(String name, List<String> phones) {
        this.name = name;
        this.phones = new ArrayList<>(phones);
    }

    public String getName() {
        return name;
    }

    public List<String> getPhones() {
        return new ArrayList<>(phones);
    }

    @Override
    public String toString() {
        return "'" + name + '\'' + ", " + phones;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contact contact = (Contact) o;

        if (!name.equals(contact.name)) return false;
        return phones.equals(contact.phones);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + phones.hashCode();
        return result;
    }
}