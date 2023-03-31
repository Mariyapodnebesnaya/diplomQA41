# Дипломная работа по курсу "Тестировщик ПО"
Дипломный проект — автоматизация тестирования комплексного сервиса, взаимодействующего с СУБД и API Банка.

### **Приложение представляет собой веб-сервис, который предлагает купить тур по определённой цене двумя способами:**

- Обычная оплата по дебетовой карте. 
- Уникальная технология: выдача кредита по данным банковской карты.
# Документация
1. [Дипломное задание](https://github.com/netology-code/qa-diploma)
2. [План автоматизации тестирования](https://github.com/Mariyapodnebesnaya/diplomQA41/blob/master/docs/Plan.md)
3. [Отчет о проведенном тестировании](https://github.com/Mariyapodnebesnaya/diplomQA41/blob/master/docs/Report.md)
4. [Отчёт по итогам автоматизации](https://github.com/Mariyapodnebesnaya/diplomQA41/blob/master/docs/Summary.md)

# Запуск приложения
### Предусловия:
1. Установленная IntelliJ IDEA.
2. Установленный Docker Desktop. Скачать можно [здесь](https://www.docker.com/).
### Иструкция по запуску:
1. Открыть IntelliJ IDEA
2. Склонировать [репозиторий](https://github.com/Mariyapodnebesnaya/diplomQA41)
3. В терминале ItelliJ IDEA запустить необходимые базы данных (MySQL и PostgreSQL), а также NodeJS. Параметры для запуска хранятся в файле `docker-compose.yml` Для запуска необходимо ввести в терминале команду: `docker compose up`. Проверка работающих контейнеров: `docker ps`.
4. В новой вкладке терминала запустить целевое приложение:               
      - **для MySQL**- `java -jar artifacts/aqa-shop.jar -Dspring.datasource.url=jdbc:mysql://localhost:3306/app`              
      - **для PostgresSQL** - `java -jar artifacts/aqa-shop.jar -Dspring.datasource.url=jdbc:postgresql://localhost:5432/app`     
5. В новой вкладке терминала запускаем `gate-simulator`: `cd gate-simulator` далее `npm start`.

### Запуск тестов:
 - **для MySQL**- `./gradlew clean test allureReport -Durl=jdbc:mysql://localhost:3306/app`
 - **для PostgresSQL** - `./gradlew clean test allureReport -Durl=jdbc:postgresql://localhost:5432/app`

### Создания отчета Allure:
`./gradlew allureServe`

### Перезапуск приложения и тестов:
Если требуется перезапустить приложение и/или тесты (например, для другой БД), необходимо выполнить остановку работы в запущенных ранее вкладках терминала нажав в них Ctrl+С. Далее выполняем команду: `./gradlew clean`

## **Окружение**
**Операционная система:** Windows 10 Pro  
**IDE:** IntelliJ IDEA Community Edition 2022.1.4  
**JAVA:** corretto-11


