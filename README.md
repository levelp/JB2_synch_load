# Асинхронная сериализация 
Необходимо реализовать механизм асинхронного сбрасывания файлов на диск.

---

## Какой стратегии выгрзки придерживаться?

---

У вас в поле хранится информация о всех контактах. Контакты выгружаются на диск в порядке поступления. Причем загрузка должна происходить срезу, как только вы определили, что вам это можно сделать. При этом в файле должно быть максимальное возможное количество элементов.
Например, ваш лимит для файла - 100 элементов, у вас уже хранится 99 элементов, и программа сгенерировала и "впрыснула" еще 99 элементов. Вы сразу же можете определить, что как минимум 100 элементов вы можете записать на диск. 
Тут же, если у вас меньше 100 элементов, то вы должны их выгружать только по непосредственному запросу **saveAll**.

---

## Как реализовывать?

---

1. Реализовать конкретный класс _**AsyncLoader**_:
    1. Свойство **limit** означает максимальное количество записей (контактов), которое должно оказаться в одном файле
    1. Свойство **serializationType** показывает, какой тип сериализации сы должны использовать. Вы можете реализовать не все типы сериализации, или же добавить свои. Соответственно тут есть подзадача - реализовать **ContactBook** для каждого типа сериализации.
    1. Свойство **filePrefix** показывает, какое название будет у нашего файла. Для генерации имен используется уже существующий класс **FileNameGenerator**, который хранит все уже сгенерированные названия и (sic!) использует их потом для проверки того, что было записано и что было реально сгенерировано.
    1. Поле **fileNameGenerator**, соответственно, должно использоваться для генерации имен. Поля гененрируются в порядке а формате ***filePrefix-#num#.#ext#***, где расширение определяется типом сериализации.
    1. Поле **contactBook** инициализируется с помощью соответствующего (абстрактного) метода, который вам надо реализовать.
    1. Внутри **contacts** должны храниться те контакты, которые еще не загружены на диск. 
    1. Вам не нужно создавать дополнительные `публичные` методы, так как в любом случае тестовый класс (**LoaderTest**) будет использовать те, что уже заданы нами.
    1. Для понимания, как работать с методами, к ним есть маленькие пояснения
2. Каждый поток, используемый для асинхронной записи, должен иметь свое вменяемое имя и должен информировать (_System.out_) нас
    1. В самом начале метода **run**, что он начал работу (указав свое имя, конечно)
    1. В самом конце метода **run**, что он закончил работу (указав свое имя, конечно)
2. Файлы _**Contact**_, _**ContactBook**_, _**AsyncLoader**_ изменяться не должны
2. Файл _**FileNameGenerator**_ можно менять по вашему усмотрению, однако должно соблюдаться правило, что все сгенерированные через **nextFileName** имена должны возвращаться потом в **getAllNames**.
2. Файл _**MockData**_ содержит методы для случайной генерации
2. В файле _**SerilizationType**_ можно добавлять новые значения enum'а
2. Файл _**LoaderTest**_ можно изменять для тестовых целей (не сломав, конечно)
2. Пример запуска приложения показан в методе _**Main.main(String... args)**_
 
 
---

## Как работает программа?

---
1. Создаем наш загрузчик, указав префикс для файла, тип сериализации и то, какое максимальное число записей должно быть в каждом файле. 
2. Создаем тестирующую часть, указав:
  * **period** - с какой частотой тестирующая часть "впрыскивает" новую порцию данных в наш загрузчик
  * **amount** - сколько данных при этом она генерирует
  * **limit** - (неточное значение) - примерное количество данных, которое будет сгенерировано (см. пояснение). На самом деле будет сгенерировано **N** записей, где **N** - наименьшее число из чисел формата **amount * x** для целого **x**, такое, что **N > limit**.
3. Тестирующая часть генерирует осмысленные данные случайным образом по таймеру (отдельный поток).
4. После завершения всех записывающих потоков (метода **join** в **AsyncLoader**) мы считываем все данные из файлов, которые нагенерировали, и сравниваем их с тем, что в результате у нас хранится в "истории" теста. Если найдется разница, то об этом будет сообщено. В частности важен порядок, в котором записаны и считанны данные.
5. Вся "история" хранится в памяти, так что ограничена максимальным возможным размером списка (можете проверить, что будет иначе). 


---

## Пример запуска

---

```java
        AsyncLoader loader = new MyAsyncLoader("test", SerializationType.JSON, 100);
        LoaderTest generator = new LoaderTest(1000, 243, 2000, loader);
        generator.start();
```

В результате тестовый класс будет присылать нам каждые 1000мс по 243 новых элемента, сгенерировав 2187 элементов. При этом у нас сгенерируется 22 файла, первые 21 файл будут содержать по 100 элементов, последний - 87 элементов. Каждый файл будет иметь название типа "test-1.json", "test-2.json", и т.д.
