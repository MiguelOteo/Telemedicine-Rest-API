# TelemedicineRestAPI

API REST in Java for the communication between an application an a MySQL data base.
The API recieves https which are use to search, update etc data from the database and produces a response which is send as a JSON string, the application gets the JSON repsonse and converts it using the library Gson to an APIRespose object then it is shown to the user the error message if it is an error, or continues with the normal execution if it is not.

## Maven Project configuration and set-up

### 1. Tomcat installation
A server is needed to run the RestAPI.                                                              
You can download the tomcat server on the following link: https://tomcat.apache.org/download-90.cgi                                                                                 
After downloading it, unzip the folder and place it on the path it better suits you on your PC.

Then on Eclipse click on "Window", "Preferences" and search for the option "Server" then click on "Runtime Enviroment" and add the Tomcat by selecting the file previously downloaded.

### 2. Maven project configuration (For Eclipse)
Download the project from github and open it with Eclipse. 
In order for the project to work be sure to have all the possible plugins needed for maven projects, then right-click on the project, click on properties, build path and then click on the option "Add Library".

Lastly, select the option "Server runtime" and add the "Apache Tomcat vX.0 (The version you dowloaded on step 1)".

### 3. Libraries configuration
                     
This project requires the addition of three .jar to work, paste them in "WebContent/WEB-INF/lib".

1. gson .jar (Link: https://jar-download.com/artifacts/com.google.code.gson/gson/2.8.2/source-code)
2. json .jar (Link: https://jar-download.com/artifacts/org.json)
3. mysql-connector-java .jar (Link: https://dev.mysql.com/downloads/windows/installer/8.0.html)

Lastly, add them to the project build path.

# Reminder
This API it is used to handle a MySQL database, for this project to work MySQL server needs to be created for the API to access there
You can download MySQL with all the tools here: https://dev.mysql.com/downloads/mysql/
