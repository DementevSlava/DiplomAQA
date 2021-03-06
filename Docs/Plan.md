### Перечень автоматизируемых сценариев

 I. Тестирование двух способов оплаты:
 1. Обычная оплата по дебетовой карте. Сервис Payment Gate
 - Открыть сайт
 - Нажать "Купить"
 - Ввести данные карты
 - Нажать кнопку "Продолжить"

 2. Уникальная технология: выдача кредита по данным банковской карты
 Сервис Credit Gate
 
 - Открыть сайт
 - Нажать "Купить в кредит"
 - Ввести данные карты
 - Нажать кнопку "Продолжить"

II. Проверить поддержку двух СУБД:
Подключить БД MySQL и БД PostgreSQL  
В базах данных должны появиться данные об операции в таблицах:
* При оплате дебетовой картой - payment_entity и order_entity.
* При оплате в кредит - credit_request_entity и order_entity.
* Убедится, что данные карт не сохраняются

При оплате тура картой со статусом DECLINED, запись формируется только в таблице payment_entity.


### Перечень используемых инструментов с обоснованием выбора

* IntelliJ IDEA  
Удобный инструмент написания кода как для профессионала, так и для новичка.
Который позволит минимизировать ошибки в коде.

* Java  
Язык написания авто-тестов. Широко распространен и универсален.

* Система контроля версий - Git  
Хранение кода, в том числе авто-тестов. У всех была возможность взаимодействовать с проектом up-to-date.
GIT умеет откатывать версии в случае если что-то пошло нет так. Также система веток позволяет разрабатывать различные сервисы параллельно
основной и альтернативные версии проекта.

* JUNIT 5  
 Платформа для написания авто-тестов и их запуска; в файле build.gradle в зависимостях включить поддержку JUnit Jupiter
 
* Gradle
Cистема управления зависимостями. Проект с автотестами создать на базе Gradle.

* CSS-селекторы  

* Selenide  
это фреймворк для автоматизированного тестирования веб-приложений на основе Selenium WebDriver.

* СУБД MySQL и PostgreSQL. используем две так как эти СУБД поддерживаются приложением (как заявлено).

* Node.js
  (Альтернатива - в контейнере Docker) Данное приложение понадобится для запуска симулятора банковских серверов, т.к. они написаны на Node.js

### Перечень и описание возможных рисков при автоматизации
* Медленный интернет или его отсутствие
* Сложности в настройки Docker. Необходимо проверить поддержку 2х СУБД - MySQL и PostgreSQL.
 А также настроить работу симулятора банковских сервисов.

### Интервальная оценка с учётом рисков (в часах)

* Подготовка к проведению тестирования: 8 часов
* Написание автотестов: 40 часов
* Подготовка отчетов о проведении тестирования: 16 часов

### План сдачи работ

* планирование автоматизации тестирования 06.08.2020
* Авто-тесты будут готовы 15.08.2020
* Подготовке отчётных документов по итогам автоматизированного тестирования 17.08.2020
* Подготовка отчётных документов по итогам автоматизации 19.08.2020
