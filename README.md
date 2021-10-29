# TelemedicineRestAPI

API REST in Java for the communication between an application an a MySQL data base.
The API recieves https and produces a response which is send as a JSON string, the application gets the JSON repsonse and converts it using the library Gson to an APIRespose object then it shows to the user the error message if it is an error, or continues with the normal execution if it is not.
