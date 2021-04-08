                                                                 Bulletin Board Server Project.
                
1)About the project.
               
This is a project of the server part of the purpose of which the management 
of the authors of the ads work with the headings.
This project performs theft operations as well as sending e-mails
to authors who are subscribed to a specific ad under a specific heading and
a specific description.

2)Start the project locally.

2.1 Required to install the project.

* Java 8
* MySql 8.0.18

2.2 How to run project.

You should create environmental variables that are defined in 
the resources package like:

* db.properties.
* mail.properties.
* jwt.properties.

2.2.1 For db.properties you must set the value like:

jdbc.driverClassName=${Value}
jdbc.url=${Value}
jdbc.username=${Value}
jdbc.password=${Value}
hibernate.show_sql=${Value}
hibernate.dialect=${Value}
hibernate.show_ddl=${Value}
jdbc.url_test=${Value}

2.2.2 For mail.properties you must set the value like:

host=${Value}
port=${Value}
user_name=${Value}
mail_password=${Value}
tran_prop_key=${Value}
tran_prop_value=${Value}
auth_key=${Value}
auth_value=${Value}
ssl_key=${Value}
ssl_value=${Value}
start_enable_key=${Value}
start_enable_value=${Value}

2.2.3 For jwt.properties you must set the value like:

jwt.secret=${Value}

2.3 You must create the database manually or automatically.

1)Scripts for creating a database and tables can be found in the root of the project
in the directory sql.

2)On the other hand, jpa can create a database and tables automatically,
which you must set in db.properties.
hibernate.show_ddl=${Value} - true instead of false.


2.4 You must save the author to be able to obtain a token for the author and
to be able to use the token to access secure endpoints.
If you do not do this and try to access the protected endpoints you will get a 401
unauthenticated error.

2.4.1 To save the author use url: http://localhost:port/myapp/author/authors
port - means your port which you set.

2.4.2 To get a token use url: http://localhost:port/myapp/author/authentication
and you get token like: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJtZXNzYWdlIjoiSldUIFJ
1bGVzISIsImlhdCI6MTQ1OTQ0ODExOSwiZXhwIjoxNDU5NDU0NTE5fQ.-yIVBD5b73C75osbmwwshQNRC7frWUYrqaTjTpza2y4


3.You can run checkStyle,pmd,findbugs,sonarLint in the life cycle of the maven section (plugins).

3.1 You can use Apache Tomcat server to run and deploy this application and
create a war file in the maven section (plugins).

3.2 You must use Apache Maven tool to collect the project. 
You can find all the dependencies in the maven pom file of the project.
             
