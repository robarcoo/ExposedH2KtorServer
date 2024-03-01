# Exposed H2 Ktor Server

This project is a simple Ktor Server that used Exposed and H2 to store data sent from client-side. 

Running Application.kt will start a server that saves data in database on computer, so after stopping server data is saved and can be retrieved later. Server includes working with multiple entities, one-to-many relationship, GET-requests, POST-requests (that adds and saves new data on the server), PUT-requests (updates old data and saves it), DELETE-requests (removes old data). 

Test data is added when creating server however when adding new data H2 has id conflict because it starts from 1 even if some data is present in the table. Requests don't return any text, just success or fail HTTP respose code.
