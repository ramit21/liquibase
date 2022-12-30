# liquibase
liquibase with springboot

Start the maven springboot application.

Open h2 console at http://localhost:8080/h2-console/, and login with user name/pwd 
and datasource url as configured in application.properties. 
This file also points to master liquibase changelog.

See the data inserted into H2 by Liquibase, by running this query in h2 console:

select * from person;

Notice 2 different ways of giving change logs under changes folder.
First one creates table as configured in the changelog xml.
Second one has changelog point to a sql file.

