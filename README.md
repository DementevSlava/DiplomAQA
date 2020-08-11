### Дипломный проект профессии «Тестировщик»

##### План автоматизации
[Plan](https://github.com/DementevSlava/DiplomAQA/blob/master/Docs/Plan.md)

##### Запуск приложения
1. Запустить Docker Toolbox
2. Запустить контейтры mysql, postgres и Node.js командой  
```docker-compose up```  
3. Открыть второй терминал и запустить SUT командой  
```java -jar artifacts\aqa-shop.jar```  
4. Запустить автотесты командой  
```gradlew test```
