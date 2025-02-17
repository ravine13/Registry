## Registry

# Pre-requisites
Ensure you have the following installed:

JDK 8 (for SOAP API compatibility),
Apache Maven (for building the project),
Apache Tomcat (or another servlet container),
MySQL Database (for data storage).


## Database Setup
Create a MySQL database named contacts_db (or any preferred name).

# Run the following SQL script to create the contacts table:

    sql
    CREATE TABLE contacts (
        id INT PRIMARY KEY AUTO_INCREMENT,
        full_name VARCHAR(255) NOT NULL,
        phone_number VARCHAR(50) NOT NULL,
        email VARCHAR(255),
        id_number VARCHAR(50),
        dob DATE,
        gender VARCHAR(10),
        organization VARCHAR(255),
        masked_name VARCHAR(255),
        masked_phone VARCHAR(255),
        hashed_phone VARCHAR(255)
    );
## Update the database connection details in DBConnection.java:

 Update the database connection details in DBConnection.java (located in src/main/java/com/test/contactregistry2/config).


## Build and Deploy
Clone the repository:


git clone
cd ContactRegistryApp
Build the project using Maven:

mvn clean package


Open a browser and go to:
http://localhost:8080/ContactRegistryApp


## Running the Application

Run the Project Locally,
Ensure MySQL is running,
Ensure Tomcat is running.

## License

This project is licensed under the [MIT License](LICENSE).  
You are free to use, modify, and distribute this software as long as you include the original license.  
See the `LICENSE` file for more details.
