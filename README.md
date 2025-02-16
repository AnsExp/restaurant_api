# Restaurant API

A simple API that offers a basic yet direct way to manage a restaurant.

---

<h2 id="settings">Configuration</h2>

### Constants

The configuration of the API is straightforward. Inside the `src/main/resources/application.yml` file, there are system constants:

```yml
constants:
  iva: 0.15
  pagination:
    size: 10
```

The `iva` constant corresponds to the restaurant sales tax. It's a decimal value that represents a percentage **0.15 = 15%**.

The `pagination.size` constant corresponds to the page size in global queries. Used in massive data queries. If you want to get the 10 records of page n, then leave it as it is.

### Database

The database to use is also in the `src/main/resources/application.yml` file.

```yml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/beagle_bar
    username: root
    password: admin
```

Remember to create the database before starting the program. If you want to change the name of the database, you can do so in the `spring.datasource.url` property. By default, the database is named `beagle_bar`.

## Endpoints

There are a total of five route controllers for different parts of the system:

## Categories Endpoint

### `POST` Method

`http://localhost:8000/api/categories`

Saves a new food category. It receives a request in the body with the following format:

```json
{
  "name": "Tapas"
}
```

Returns a response with the following format:

```json
{
  "id": 1,
  "name": "Tapas"
}
```

### `PUT` Method

`http://localhost:8000/api/categories/{id}`

Updates a category by its ID. The request body should have the following format:

```json
{
  "name": "Tapas"
}
```

The endpoint returns a response with the following format:

```json
{
  "id": 1,
  "name": "Tapas"
}
```

### `GET` Method

`http://localhost:8000/api/categories/{id}`

Gets the category by its ID. The endpoint returns a response with the following format:

```json
{
  "id": 1,
  "name": "Tapas",
  "dishes": [
    {
      "id": 0,
      "name": "example",
      "price": "$9.99"
    }
  ]
}
```

### `GET` Method

`http://localhost:8000/api/categories`

Gets all categories. The endpoint returns a response with the following format:

```json
[
  {
    "id": 1,
    "name": "Tapas",
    "dishes": [
      {
        "id": 0,
        "name": "example",
        "price": "$9.99"
      }
    ]
  },
  {
    "id": 2,
    "name": "Beverages",
    "dishes": [
      {
        "id": 0,
        "name": "example",
        "price": "$9.99"
      }
    ]
  }
]
```
### `DELETE` Method

`http://localhost:8000/api/categories/{id}`

Remove a category based on its id. The endpoint returns a response with the following format:

```json
{
  "message": "Category don't exits."
}
```

## Clients Endpoint

### `POST` Method

`http://localhost:8000/api/clients`

Saves a new client. It receives a request in the body with the following format:

```json
{
  "firstname": "Jorge",
  "lastname": "Smith",
  "phone": "0987654321",
  "email": "jorge.smith@mail.com",
  "id_card": "0123456789"
}
```

Returns a response with the following format:

```json
{
  "id": 1,
  "firstname": "Jorge",
  "lastname": "Smith",
  "phone": "0987654321",
  "email": "jorge.smith@mail.com",
  "id_card": "0123456789"
}
```

### `GET` Method

`http://localhost:8000/api/clients`

Gets a list of all clients registered on the server:

```json
[
  {
    "id": 1,
    "firstname": "Jorge",
    "lastname": "Smith",
    "phone": "0987654321",
    "email": "jorge.smith@mail.com",
    "id_card": "0123456789"
  },
  {
    "id": 2,
    "firstname": "Linda",
    "lastname": "Hamilton",
    "phone": "9876543210",
    "email": "linda.hamilton@mail.com",
    "id_card": "1234567890"
  }
]
```

It also supports query parameters for pagination of results.
    
`http://localhost:8000/api/clients?page={page}&order_by={field}`

- **page:** The page number to get.
- **order_by:** Sorts the list by a field. For example, the name field.

The <a href="#settings">page size</a> is given in the project settings.

### `GET` Method

`http://localhost:8000/api/clients/{id}`

Gets the category by its ID. The endpoint returns a response with the following format:

```json
{
  "id": 1,
  "firstname": "Jorge",
  "lastname": "Smith",
  "phone": "0987654321",
  "email": "jorge.smith@mail.com",
  "id_card": "0123456789"
}
```

## Dishes Endpoint

### `POST` Method

`http://localhost:8000/api/dishes`

Saves a new dish. It receives a request in the body with the following format:

```json
{
  "name": "Rice Marine",
  "price": 1799, // price in cents
  "category_id": 1
}
```

Returns a response with the following format:

```json
{
  "id": 1,
  "name": "Rice Marine",
  "price": "$17,99",
  "category": "Tapas"
}
```

### `GET` Method

`http://localhost:8000/api/dishes/{id}`

Gets the dish by its ID. The endpoint returns a response with the following format:

```json
{
  "id": 1,
  "name": "Rice Marine",
  "price": "$17,99",
  "category": "Tapas"
}
```

### `GET` Method

`http://localhost:8000/api/dishes`

Gets a list of all clients registered on the server:

```json
[
  {
    "id": 1,
    "name": "Rice Marine",
    "price": "$17,99",
    "category": "Tapas"
  },
  {
    "id": 2,
    "name": "Pasta",
    "price": "$9,99",
    "category": "Tapas"
  }
]
```

It also supports query parameters for pagination of results.

`http://localhost:8000/api/clients?dishes={page}&order_by={field}`

- **page:** The page number to get.
- **order_by:** Sorts the list by a field. For example, the name field.

The <a href="#settings">page size</a> is given in the project settings.
