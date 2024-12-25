# Управление продуктами в центрах выполнения (Fulfillment Centers) API

Этот проект реализует REST API для управления продуктами в различных центрах выполнения (Fulfillment Centers) и поддержания данных о состоянии запасов. Он включает операции для получения, добавления, обновления и удаления продуктов, а также предоставляет возможность фильтрации по статусу и расчета общего значения всех продуктов с состоянием `Sellable`.

## Стек технологий
- **Java** (JDK 17)
- **Spring Boot** (версии 2.x)
- **Spring Data JPA**
- **PostgreSQL**
- **Swagger** для документации API

## Основные функции

- CRUD-операции для продуктов:
- Создание, чтение, обновление и удаление продуктов.
- Фильтрация продуктов по статусу.
- Расчет общего значения всех продуктов с состоянием `Sellable`.
- Возможность запроса общего значения для всех продуктов в конкретном Fulfillment Center.

## Структура проекта

- **src/main/java/com/example/fulfillment** – основной код приложения
    - **controllers** – обработка HTTP-запросов
    - **entity** – описание сущностей
    - **repositories** – интерфейсы для взаимодействия с базой данных
    - **services** – бизнес-логика
    - **dto** – объекты передачи данных (Data Transfer Object)
    - **config** - пакет для конфигурационных классов
    - **exception** - пакет для обработки исключений
- **src/main/resources** – конфигурационные файлы
    - **application.properties** – настройки подключения к базе данных

## Настройка и запуск

### 1. Настройка базы данных (PostgreSQL)

1. Установите и запустите PostgreSQL на вашем компьютере.
2. Создайте базу данных с именем `fulfillment_db`.

```sql
CREATE DATABASE fulfillment_db;
```
3. Создайте таблицу для хранения продуктов, используя следующий SQL-запрос:

```sql
CREATE TABLE products (
    product_id VARCHAR(50) PRIMARY KEY,
    status VARCHAR(50) NOT NULL,
    fulfillment_center VARCHAR(50) NOT NULL,
    quantity INT NOT NULL,
    value DECIMAL(10, 2) NOT NULL
);
```

### 2. Конфигурация подключения к базе данных

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/fulfillment_db
spring.datasource.username=ваш_пользователь
spring.datasource.password=ваш_пароль
```

### 3. Сборка и запуск проекта

#### 3.1 Сборка проекта 
С помощью Maven соберите проект:

```bash
mvn clean install
```

#### 3.2 Запуск проекта

Для запуска приложения используйте команду:

```bash
mvn spring-boot:run
```
Приложение будет запущено на порту 8080 по умолчанию. Вы можете изменить порт в файле application.properties, если нужно.
 ```properties
server.port=8080
```
Приложение будет доступно по адресу: http://localhost:8080.

### 4. Доступ к Swagger UI

Документация API доступна через Swagger. После запуска приложения откройте в браузере:
```bash
http://localhost:8080/swagger-ui.html
```
## Описание API

### 1.Получение списка продуктов

GET ```/api/products```

Возвращает список всех продуктов.

Пример запроса:

```http
GET http://localhost:8080/api/products
```

Пример ответа:

```json
[
  {
    "productId": "p1",
    "status": "Sellable",
    "fulfillmentCenter": "fc1",
    "quantity": 100,
    "value": 50.0
  },
  {
    "productId": "p2",
    "status": "Unfulfillable",
    "fulfillmentCenter": "fc2",
    "quantity": 0,
    "value": 0.0
  }
]
```

### 2. Фильтрация продуктов по статусу

GET ```/api/products/status/{status}```

Возвращает список продуктов, фильтруя их по статусу.

Пример запроса:

```http
GET http://localhost:8080/api/products/status/Sellable
```

Пример ответа:

```json
[
  {
    "productId": "p1",
    "status": "Sellable",
    "fulfillmentCenter": "fc1",
    "quantity": 100,
    "value": 50.0
  }
]

```
### 3. Добавление нового продукта
POST ```/api/products```

Добавляет новый продукт в систему.

Пример запроса:

```http
POST http://localhost:8080/api/products
Content-Type: application/json

{
  "productId": "p3",
  "status": "Inbound",
  "fulfillmentCenter": "fc3",
  "quantity": 50,
  "value": 30.0
}
```
Пример ответа:

```json
{
  "productId": "p3",
  "status": "Inbound",
  "fulfillmentCenter": "fc3",
  "quantity": 50,
  "value": 30.0
}
```

### 4. Обновление информации о продукте
   PUT ```/api/products/{productId}```

Обновляет данные о продукте по его идентификатору.

Пример запроса:
```http

PUT http://localhost:8080/api/products/p1
Content-Type: application/json

{
"status": "Sellable",
"fulfillmentCenter": "fc1",
"quantity": 150,
"value": 55.0
}
```
Пример ответа:
```json
{
"productId": "p1",
"status": "Sellable",
"fulfillmentCenter": "fc1",
"quantity": 150,
"value": 55.0
}
```
### 5. Удаление продукта
   DELETE ```/api/products/{productId}```

Удаляет продукт по его идентификатору.

Пример запроса:
```http
DELETE http://localhost:8080/api/products/p1
```
Пример ответа:
```json
{
"message": "Product p1 deleted successfully"
}
```
### 6. Вычисление общего значения продуктов с состоянием "Sellable"
   GET ```/api/products/value/sellable```

Возвращает общее значение всех продуктов с состоянием Sellable.

Пример запроса:
```http
GET http://localhost:8080/api/products/value/sellable
```
Пример ответа:
```json
{
"totalValue": 5000.0
}
```
### 7. Вычисление общего значения продуктов для конкретного Fulfillment Center
   GET ```/api/products/value/fulfillment-center/{fulfillmentCenter}```

Возвращает общее значение всех продуктов для конкретного центра выполнения.

Пример запроса:
```http
GET http://localhost:8080/api/products/value/fulfillment-center/fc1
```
Пример ответа:
```json
{
"totalValue": 3000.0
}
```
