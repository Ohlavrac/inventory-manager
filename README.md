# Inventory Manager

Restful API para gerenciamento de inventario, feito com base no desafio de back-end (INVENTORY MANAGEMENT API)

## Endponts

- [OK] `GET /products`: Retrieve all products.
- [OK] `POST /products`: Create a new product.
- [OK] `GET /products/{productId}`: Retrieve details about a specific product.
- [OK] `PUT /products/{productId}`: Update an existing product.
- [OK] `DELETE /products/{productId}`: Delete a product.
- [OK] `GET /categories`: Retrieve all categories.
- [OK] `POST /categories`: Create a new category.
- [OK] `GET /categories/{categoryId}`: Retrieve details about a specific category.
- [OK] `PUT /categories/{categoryId}`: Update an existing category.
- [OK] `DELETE /categories/{categoryId}`: Delete a category.
- [OK] `GET /orders`: Retrieve all orders.
- [OK] `POST /orders`: Create a new order.
- [OK] `GET /orders/{orderId}`: Retrieve details about a specific order.
- [OK] `PUT /orders/{orderId}/status`: Update a status of existing order (e.g., change order status).
- [OK] `DELETE /orders/{orderId}`: Cancel an order.

## Extras

- [OK] `POST /auth/register`: Register a new user.
- [OK] `POST /auth/login`: Authenticate with user.
- [TODO] `GET /user`: Retrive user data/infos.
- [TODO] `PUT /user`: Update user data.
- [TODO] `DELETE /user`: Delete user account.
- [TODO] `PUT /admin/user/{userID}/role`: Update the role of a user (PS: Only admin can update user role).

## Desafio

 - [Backend Challenge - Inventory Management API](https://github.com/libre-university/backend-challenges/blob/main/challenges/junior/api-inventory-management.md#backend-challenge---inventory-management-api)

