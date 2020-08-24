# Дипломный проект профессии «Тестировщик»

### План автоматизации
[Plan](https://github.com/DementevSlava/DiplomAQA/blob/master/Docs/Plan.md).

### Запуск приложения
Для запуска приложения необходимо:

1. Запустить Docker Toolbox
2. Запустить контейнеры mysql, postgres и Node.js командой  
```docker-compose up```  
3. Открыть второй терминал и запустить SUT командой
   - для подключения БД MySql:  
```java -Dspring.datasource.url=jdbc:mysql://192.168.99.100:3306/app -jar artifacts\aqa-shop.jar```
   - для подключения Postgres:  
```java -Dspring.datasource.url=jdbc:postgresql://192.168.99.100:5432/app -jar artifacts\aqa-shop.jar```
4. Запустить автотесты командой  
   - для конфигурации БД MySql:  
```gradlew clean test -Ddb.url=jdbc:mysql://192.168.99.100:3306/app allureReport```
   - для конфигурации БД Postgres:  
```gradlew clean test -Ddb.url=jdbc:postgresql://192.168.99.100:5432/app allureReport```
5. Посмотреть отчет можно командой
```gradlew allureServe```  
откроется страница в браузере  
По окончании просмотра
6. Закрыть ```allureServe``` командой ```Ctrl+C``` Завершить? ```Y```
7. Остановить SUT командой ```Ctrl+C```
8. Остановить контейнеры mysql, postgres и Node.js командой  
```Ctrl+C``` затем ```docker-compose down```
  
### Описание приложения

Приложение предлагает купить тур по определённой цене с помощью двух способов:

1. Обычная оплата по дебетовой карте
2. Уникальная технология: выдача кредита по данным банковской карты

Само приложение не обрабатывает данные по картам, а пересылает их банковским сервисам:

- сервису платежей (далее - Payment Gate)
- кредитному сервису (далее - Credit Gate)

Приложение должно в собственной СУБД сохранять информацию о том,
каким способом был совершён платёж и успешно ли он был совершён
(при этом данные карт сохранять не допускается).

В файле [application.properties](https://github.com/DementevSlava/DiplomAQA/blob/master/application.properties) приведён ряд типовых настроек:

- учётные данные и url для подключения к СУБД
- url-адреса банковских сервисов

Симулятор написан на Node.js, поэтому для запуска вам нужен либо Docker,
либо установленный Node.js. Симулятор расположен в каталоге [gate-simulator](https://github.com/DementevSlava/DiplomAQA/tree/master/gate-simulator).  
Запускается симулятор командой npm start на порту 9999.

Симулятор позволяет для заданного набора карт генерировать предопределённые ответы.

Набор карт представлен в формате JSON в файле data.json.

Разработчики сделали один сервис, симулирующий и Payment Gate, и Credit Gate.
