# Инструкция

## Настройка окружения:
необходимо установить и настроить следующие компоненты

 * git
 
 * jdk 8
 
 * maven
 
 * [chrome driver] (выберите версию, соответствующую установленной версии chrome браузера на компьютере, где предполагается запускать автотесты, я использую версию chrome браузера 75.0.3770.142 и драйвер к нему версии 75.0.3770.140)


## Запуск автотестов:
Перед запуском автотестов в файле serenity.properties в параметре с ключом webdriver.chrome.driver укажите путь к установленному chrome driver.

Автотесты запускаются в корне проекта (папка с pom.xml файлом) командой

```mvn -U clean integration-test serenity:aggregate```


## Результаты прогона автотестов:

* HTML отчет находится в папке проекта по пути ../target/site/serenity/index.html

* Лог файл result.log находится в папке проекта


Дополнительная информация об используемом фреймворке: [The Serenity Reference Manual]


[The Serenity Reference Manual]: <http://www.thucydides.info/docs/serenity/>

[chrome driver]: <http://chromedriver.storage.googleapis.com/index.html>