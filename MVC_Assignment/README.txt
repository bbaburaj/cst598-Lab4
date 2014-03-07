News Information Site
******************************************************************************************
The following application enables users to access news via 
http://localhost:8080/lab3Task4


The current news items present in the Website are:
Title				Body				  Owner
"Reporter News 1", "This is the body-1", "reporter1"
"Reporter News 2", "This is the body-2", "reporter1"
"Reporter News 3", "This is the body-3", "reporter1"
"Reporter News 4", "This is the body-4", "reporter1"
"Reporter News 5", "This is the body-5", "reporter2"
"Reporter News 6", "This is the body-6", "reporter3"
"Reporter News 7", "This is the body-7", "reporter4"
"Public News 1", "This is the public body-1", "public"
"Public News 2", "This is the public body-2", "public"
"Public News 3", "This is the public body-3", "public"
"Public News 4", "This is the public body-4", "public"

Current users in the persistent file(resources/login.txt) are:
subscriber1
reporter1

Hence when you login with username/password: subscriber1/subscriber1 
it would take you in to the website automatically.

The DAO and Model
******************************************************************************************
The DAO has been implemented based on the very first revision provided by Dr.Gary hence it 
would not be the same as the one provided by Dr.Gary recently.

Accessing the static file
******************************************************************************************
The new user info would be persisted in
${tomcat-home}/webapps/lab3Task2/resources/login.txt

Remembering your last visit
******************************************************************************************
The website remembers your visit until the session is completely closed (all tabs are closed)
The website also remembers students who are registered already based on their id.

