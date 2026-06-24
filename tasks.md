

---



# Technical Assessment: Full-Stack (Spring Boot + Angular)



## 1. General Indications







* **Back End:** Apply good practices, use the Repository pattern, and any other patterns considered necessary. Handle entities using JPA. Exception messages must be handled. Write a minimum of two unit tests for the endpoints. The solution must be deployed in Docker. You will have a technical interview to defend your solution.





* **Front End:** Implement good practices (Clean Code, SOLID). Implement unit tests (using Jest for Angular). **Strict Rule:** Do not use any style frameworks or pre-fabricated components to mockup the proposed design.







## 2. Tools and Technologies







* **Back-End:** Java Spring Boot, IDE of preference, Relational Database, Postman v9.13.2.





* **Front-End:** Angular.







## 3. Back-End Requirements







### Data Models & Entities



Create a REST API handling standard verbs (GET, POST, PUT, PATCH, DELETE).



* **Persona:**

* Attributes: Nombre, género, edad, identificación, dirección, teléfono.





* Must have a primary key (PK).









* **Cliente:**

* Must inherit from the `Persona` class.





* Attributes: clienteid, contraseña, estado.





* Must have a unique primary key (PK).









* **Cuenta (Account):**

* Attributes: Número de cuenta, tipo de cuenta, saldo inicial, estado.





* Must have a unique primary key.









* **Movimientos (Transactions):**

* Attributes: Fecha, tipo de movimiento, valor, saldo.





* Must have a unique primary key.











### Operations & Endpoints



The API must allow creating, editing, updating, and deleting records for Cliente, Cuenta, and Movimiento entities.



**Required Endpoints:**



* `/cuentas`



* `/clientes`



* `/movimientos`





### Business Logic & Validations



* Credit values are positive, and debit values are negative.





* The available balance must be stored in each transaction, calculated based on the movement type (addition or subtraction).





* If the balance is zero and a debit transaction is attempted, display the message: **"Saldo no disponible"**.





* Implement a daily withdrawal limit parameter (maximum value: $1000).





* If the available daily quota has been met, do not allow further debits and display the message: **"Cupo diario Excedido"**.





* **Reports:** Generate an account statement report specifying a date range and a client. This must display associated accounts, their balances, and the total debits/credits made during that period. The report endpoint (e.g., `/reportes?fecha=rango fechas`) must support both JSON and Base64 (PDF) formats.







## 4. Front-End Requirements







* **Layout:** Adapt the proposed layout mockup for the entity views (Cliente, Cuentas, Movimientos, Reportes).





* **CRUD:** Build the UI for creating, reading, updating, and deleting Clientes, Cuentas, and Movimientos.





* **Reporting:** Visually present the executed transactions using the reports endpoint. Include a button to download the report in PDF format.





* **Search:** Include an action that allows rapid searching of any record within the tables.





* **Validation:** All validation messages must be visibly displayed on the screen.







## 5. Extras (Bonus Points)







Implementing the following features is not mandatory but will increase your score:



* Use of lambdas





* Exception Handlers





* Functional programming





* Model-level attribute validation





* Use of at least one design pattern (e.g., Singleton, Decorator, Mediator, Strategy, MVC, etc.)







## 6. Deployment & Deliverables







### Deployment Instructions



* Generate a database script containing entities and the schema named `BaseDatos.sql`.





* Deploy the solution using Docker.





* Execute via Postman for verifications (`http://{servidor}:{puerto}/api/{metodo}...{Parámetros}`).







### Final Deliverables



1. A public GitHub repository with all generated files; place the URL in the exercise comments.





2. A `.zip` or `.rar` file containing all generated files.





3. An exported Postman JSON file for endpoint validation.