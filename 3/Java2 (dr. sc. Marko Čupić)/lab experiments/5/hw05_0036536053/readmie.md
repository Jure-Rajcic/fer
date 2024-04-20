# Java Blog Application - User Guide

This guide provides instructions for setting up and using the Java Blog Application. This application, designed natively in Java, utilizes the MVC architecture, Servlets as controllers, JPA with Hibernate for database management, and JSPs for user display. It is intended to serve as a platform for blogs created by registered users (authors). Everyone, regardless of whether they are logged in or not, can view blogs and previously posted comments, but only logged-in users can comment on blogs.

## Setup

1. **Database Setup:** Use the following commands in your terminal to establish the database:

    ```bash
    jrajcic@HRZG1MC065 derby-skripte % ./ij-console.sh 
    ij> run 'napravi-blog-bazu.sql';       
    connect 'jdbc:derby://localhost:1527/blogBaza;user=sa;password=sapwd22;create=true'; 
    CALL SYSCS_UTIL.SYSCS_CREATE_USER('blogDBAdmin', 'blogDBPassword');
    CALL SYSCS_UTIL.SYSCS_SET_DATABASE_PROPERTY('derby.database.fullAccessUsers', 'sa,blogDBAdmin');
    disconnect;
    connect 'jdbc:derby://localhost:1527/blogBaza;user=blogDBAdmin;password=blogDBPassword'; // It is recommended not to disconnect so that you can check the tables during work 
    ```

2. **Populate Tables:** Set the following in the `src/main/resources/META-INF/persistence.xml` file:

    ```xml
    <property name="hibernate.hbm2ddl.auto" value="create" />
    ```

    Then, run `src/main/java/hr/fer/zemris/java/tecaj_13/console/Example1.java` to populate the newly created tables.

3. **Server Start-Up:** Set the following in the `src/main/resources/META-INF/persistence.xml` file:

    ```xml
    <property name="hibernate.hbm2ddl.auto" value="update" />
    ```

    Run the following command to start the server:

    ```bash
    mvn clean jetty:run
    ```

    The main page can be accessed at [http://localhost:8080/blog/servleti/main](http://localhost:8080/blog/servleti/main)

    To find all the authors, go to [http://localhost:8080/blog/servleti/author/](http://localhost:8080/blog/servleti/author/). Each author has a password that is equal to their nickname, e.g., author 'jure' has the password 'jure' if you want to log in as him.

## Features

### Blog Creation and Viewing

- **Registered Users:** As an author, once logged in, you can create new blogs and edit existing ones you've authored.
- **All Users:** Everyone, whether logged in or not, can view all blogs and comments posted thus far. 

### Commenting

- **Logged-in Users:** You can comment on blogs. Only logged-in users have the ability to comment.

## Troubleshooting

If you encounter any issues while using the Java Blog Application:

1. Verify that your database is set up correctly and is connected.
2. Check your server status to ensure it's up and running.
3. Make sure that you have correctly entered the login credentials if you are a registered user.


## Additional User Scenarios

### Registration with Taken Nickname

If you attempt to register with a nickname that's already in use, you'll be redirected back to the registration page. However, the fields for your first name, last name, email, and other previously valid entries will still be populated. This feature saves you from having to re-enter all your information. You'll simply need to choose a different nickname to complete your registration.

### Attempt to Comment as Anonymous User While Logged In

If you're logged into the system (for instance, as 'Jure') and open another tab where you're logged in under the same user name, you won't be able to comment as an anonymous user. If you try to do so (for instance, try to comment on 'Roko's' page), your attempt will fail. This is because the system recognizes your logged-in status across all open tabs. If you wish to comment as an anonymous user, you need to log out from all open tabs first.
