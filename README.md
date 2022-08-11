Описание:
---
```
Сервис для бронирования услуг автомойки
```
Endpoints:
--- 
Регистрация нового пользователя (Анонимный пользователь):
```
POST localhost:8080/api/v1/users
```
Пример ввода данных:
```
{
    "name" : "Andrew",
    "surname" : "Perevalov",
    "login" : "andreyka",
    "email" : "andrey@mail.ru",
    "password" : "Andrew",
    "phoneNumber" : "89217753241"
}
```
Войти в систему с логином и паролем (Анонимный пользователь):
```
POST localhost:8080/api/v1/auth/login
```
Пример ввода данных:
```
{
    "login" : "andreyka",
    "password" : "Andrew"
}
```
Получение нового access-token-а и refresh-token-а (Анонимный пользователь):
```
POST localhost:8080/api/v1/auth/access-token
```
Пример ввода данных:
```
{
    "refreshToken" : "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyZXlrYSIsImV4cCI6MTY2MDc4NDY3NCwicm9sZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfVVNFUiJ9XX0.WViSSvAr-8iLTHLqER9kcd2XIYujlADII8TMqfRAELs"
}
```
Изменение роли пользователя на оператора или/и админа (Только админ):
```
PUT localhost:8080/api/v1/users/update-role
```
Пример ввода данных:
```
{
    "userId" : "5",
    "roleId" : "2"
}
```
Удаление пользователя, личная информация остаются в базе данных (Админ не может только удалить свой аккаунт, пользователь может только удалить свой аккаунт):
```
DELETE localhost:8080/api/v1/users/{id}
где {id} - id пользователя
```
Получить все записи по боксу или/и дате и времени (Админ все и оператор только свои боксы):
```
GET localhost:8080/api/v1/bookings/box/{id}?date=2022-08-19&time=07:30&page=0&size=20
где {id} - id бокса, date - дата, time - время
```
Получить все записи пользователя активные, завершенные или отмененные (Админ все, оператор и пользователь только свои):
```
GET localhost:8080/api/v1/bookings/user/{id}?status=Active&page=0&size=20
где {id} - id пользователя, status - статус бронирования
```
Получить все услуги, которые заказывал пользователь (Админ все, оператор и пользователь только свои):
```
GET localhost:8080/api/v1/bookings/operations/user/{id}?page=0&size=20
где {id} - id пользователя
```
Получить выручку за определенный период времени (Только админ):
```
GET localhost:8080/api/v1/bookings/total-sum?dateFrom=2022-08-09&dateTo=2022-08-30&page=0&size=20
где dateFrom - дата начала интервала, dateTo - дата конца интервала запроса
```
Создать заказ (Админ, оператор и пользователь только на себя):
```
POST localhost:8080/api/v1/bookings
```
Пример ввода данных:
```
{
    "date" : "2022-08-17",
    "startTime" : "17:00",
    "operations" : [1,3],
    "userId" : "5"
}
```
Редактировать заказ (Админ все, оператор свои боксы и пользователь только свои заказы):
```
PUT localhost:8080/api/v1/bookings/{id}
где {id} - id заказа
```
Пример ввода данных:
```
{
    "date" : "2022-08-29",
    "startTime" : "05:30",
    "operations" : [1],
    "cancel" : "false"
}
```
Отметить заказ завершенным (Админ все, оператор свои боксы и пользователь только свои заказы):
```
PUT localhost:8080/api/v1/bookings/complete-booking/{id}
где {id} - id заказа
```
Отменить заказ (Админ все, оператор свои боксы и пользователь только свои заказы):
```
PUT localhost:8080/api/v1/bookings/cancel-booking
```
Пример ввода данных:
```
{
    "id":1,
    "status":"Cancel"
}
```
Редактирование размера мин и макс скидок (Только админ):
```
PUT localhost:8080/api/v1/discounts
```
Пример ввода данных:
```
{
    "name" : "max",
    "value" : 20.0
}
```
Назначить скидку на заказ (Админ и оператор свои боксы):
```
PUT localhost:8080/api/v1/bookings/create-discount
```
Пример ввода данных:
```
{
    "value": 5.0,
    "bookingId" : 1
}
```
Убрать скидку у заказа (Админ и оператор свои боксы):
```
PUT localhost:8080/api/v1/bookings/delete-discount/{id}
где {id} - id заказа
```
Получить каталог всех услуг (Админ, оператор и пользователь):
```
GET localhost:8080/api/v1/operations?page=0&size=20
```
Добавить новую услугу (Только админ):
```
POST localhost:8080/api/v1/operations
```
Пример ввода данных:
```
{
    "name" : "light washing",
    "leadTime" : 15,
    "price" : 150.0
}
```
Добавить новый бокс (Только админ):
```
POST localhost:8080/api/v1/boxes
```
Пример ввода данных:
```
{
    "name" : "box",
    "ratio" : "1.5",
    "workFromTime" : "08:00",
    "workToTime" : "23:59",
    "userId" : "2"
}
```
Ссылка на коллекцию постмана: https://www.getpostman.com/collections/c55f9ffa774eec8321a1
