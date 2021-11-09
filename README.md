# TelemedicineRestAPI

API REST in Java for the communication between an application an a MySQL data base.
The API recieves https which are use to search, update etc data from the database and produces a response which is send as a JSON string, the application gets the JSON repsonse and converts it using the library Gson to an APIRespose object then it is shown to the user the error message if it is an error, or continues with the normal execution if it is not.

## Project configuration and set-up

### 1. Tomcat installation
A server is needed to run the RestAPI.                                                              
You can download the tomcat server on the following link: https://tomcat.apache.org/download-90.cgi                                                                                 
After downloading it, unzip the folder and place it on the path it better suits you on your PC.

### 2. Project configuration (For Eclipse)

### 3. Libraries configuration

First, create add a new folder to the project inside "WebContent" folder and called it "WEB-INF" then add a new folder inside of it and name it "lib"
The "lib" file will be where all the .jar will go.

This project requires the addition of three .jar to work, paste them in "lib".

1. gson .jar
2. json .jar
3. mysql-connector-java .jar

Lastly, add them to the project build path.
