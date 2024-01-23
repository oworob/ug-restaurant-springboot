# Spring Boot Restaurant

This project is a full stack app that allows users to place orders in a fictional restaurant. It was made as a University project in 2023.

### Technologies:

* Frontend - Thymeleaf + Sass + Vue.js
* Backend - Java Spring Boot
* Database - MongoDB

### Functions:

A user can create an order, choose whether they prefer take away or eat in. They can add a new meal and select one from a variety of categories. Additionally, each meal comes with a list of available extras that the user can add.

The app has an admin panel that, upon logging in, displays total predicted income from orders, a table containing all orders and statistics from current month. An order can be completed, cancelled or deleted. An admin can also manage each available meal and change it's price, name and category.

The app loads initial data (meals and extras) as Beans from an XML file.

### Instructions:

The entire app works in Docker. Simply navigate to the main project folder and run:

`docker compose up --build`

Please note that the first launch may take a while.

### Screenshots:

![home page](https://github.com/oworob/ug-restaurant-springboot/blob/main/screenshots/home.png)

![meals](https://github.com/oworob/ug-restaurant-springboot/blob/main/screenshots/meals.png)

![extras](https://github.com/oworob/ug-restaurant-springboot/blob/main/screenshots/extras.png)

![order](https://github.com/oworob/ug-restaurant-springboot/blob/main/screenshots/order.png)

![admin](https://github.com/oworob/ug-restaurant-springboot/blob/main/screenshots/admin.png)
