# FileStorage

This is a web application that provides file storage and sharing functionality similar to Dropbox. It allows users to upload and download files, manage their storage, and perform administrative tasks. The application is built using Java and Spring Boot framework.

## Prerequisites

Before running the application, make sure you have the following:

- Java Development Kit (JDK) installed (version 8 or higher)
- Apache Maven installed
- MySQL database server installed and running
- Configuration details for application.properties file
- Configuration details for DataSource in the application's Java code

## Getting Started

Follow these steps to set up and run the CloudStore application:

1. Clone or download the CloudStore repository to your local machine.
2. Open the project in your preferred IDE.
3. Provide the necessary configuration details in the `application.properties` file, located in the `src/main/resources` directory. Replace the placeholder values with your actual database connection details, AWS credentials, and other required settings.
4. In the Java code, locate the `getDataSource()` method within the DataSource configuration class (usually annotated with `@Configuration`). Update the method with the appropriate database connection details.
5. Open a terminal or command prompt, navigate to the project's root directory, and run the following command to build the application:
   ```
   mvn install
   ```
6. Once the build is successful, run the following command to start the application:
   ```
   mvn spring-boot:run
   ```
7. The application will now start and be accessible at `http://localhost:8080` in your web browser.

## Usage

CloudStore provides the following functionality:

- **User Registration**: Upon accessing the application, you can register a new user account by providing a username, email, password, and confirming the password.
- **User Login**: Use the provided credentials to log in as a regular user or an admin.
- **Regular User**: Regular users can upload files, view their uploaded files, and download files by clicking on them.
- **Admin User**: Admin users have additional privileges. They can reactivate locked user accounts, view and manage user accounts.
- **Account Locking**: When a regular user enters an incorrect password four times, the account will be locked. Admin users can unlock these accounts.

## Default User Accounts

When the application is first run or the database is empty, the following default user accounts are available for login:

- Regular User:
  - Username: user
  - Password: pass
- Admin User:
  - Username: admin
  - Password: admin

## Contributing

Contributions to FileStorage are welcome! If you encounter any issues, have suggestions, or would like to contribute code improvements, please open an issue or submit a pull request on the GitHub repository.

## Acknowledgements

CloudStore was developed by Mohamed Berte. Special thanks to the contributors and the open-source community for their valuable resources and libraries used in this project.

## Contact

If you have any questions or inquiries about CloudStore, please contact moh_berte@hotmail.com .
