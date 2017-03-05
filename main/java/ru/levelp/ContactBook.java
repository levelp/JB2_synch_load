package ru.levelp;

import java.util.List;

public interface ContactBook {
    /**
     * Сохранение списка контактов в файл
     *
     * @param fileName Имя файла для сохранения
     * @param list     Список контактов
     */
    void save(String fileName, List<Contact> list);

    /**
     * Загрузка контактов из файла
     *
     * @param fileName Имя файла для загрузки контактов
     * @return Список контактов из файла
     */
    List<Contact> load(String fileName);

}
