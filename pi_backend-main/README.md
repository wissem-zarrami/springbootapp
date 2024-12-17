
![VitaVerse splash](./docs/splash.png)

# VitaVerse Backend

Welcome to VitaVerse Backend! This backend system serves as the backbone for the VitaVerse website, a platform dedicated to promoting sports activities and healthy eating habits.

## Features

- **User Management**: Manage user accounts, including registration, authentication, and profile management.
- **Content Management**: Handle the creation, retrieval, update, and deletion (CRUD) operations for articles, recipes, exercises, and other content.
- **Community Interaction**: Enable users to interact with each other through comments, likes, and sharing functionalities.
- **Search**: Implement search functionality to allow users to find relevant content easily.
- **Analytics**: Track user engagement, content popularity, and other metrics to gain insights into user behavior.
- **Security**: Ensure data privacy and integrity through robust authentication, authorization, and encryption mechanisms.
- **Performance**: Optimize backend performance to deliver a smooth and responsive user experience.

## Technologies Used

- **Spring Boot**: Framework for building and deploying Java-based applications.
- **Spring Security**: Provides authentication and authorization features to secure the application.
- **Spring Data JPA**: Simplifies data access and persistence using the Java Persistence API (JPA).
- **MySQL**: Relational database management system for storing application data.
- **Spring MVC**: Model-View-Controller architecture for developing web applications.
- **Swagger**: Generate API documentation and enable interactive API exploration.
- **JUnit**: Framework for writing and running unit tests to ensure code quality.
- **Maven**: Build automation tool for managing project dependencies and building the application.

## API Documentation

The API documentation is generated using Swagger and can be accessed at [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) after running the application.

## Contributing

1. Pull the repository: `git pull` or just download it
2. Create a new branch: `git branch wissem` => creates a branch for `wissem`
3. Use that branch: `git checkout wissem`
4. Make your changes and commit them: `git add .` ==> `git commit -m 'commentaire'` ==> `git push`
5. go back to the main branch: `git checkout main`
6. Merge your branch with the main branch: `git merge wissem` ==> will add everything new wissem wrote to the branch "main"
7. push the merge `git push origin main`
8. pull everything: `git pull`
9. repeat.

## License

This project is licensed under the [MIT License](LICENSE).

## Contact

If you have any questions or suggestions regarding VitaVerse backend, feel free to contact us at [contact@VitaVerse.com](mailto:contact@VitaVerse.com).
