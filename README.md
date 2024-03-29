# liquibase
liquibase with springboot

Start the maven springboot application.

Open h2 console at http://localhost:8080/h2-console/, and login with user name/pwd 
and datasource url as configured in application.properties. 
This file also points to master liquibase changelog.

See the data inserted into H2 by Liquibase, by running this query in h2 console:

select * from person;

Hit endpoint http://localhost:8080/person/all to fetch all saved Person entities.

Notice 2 different ways of giving change logs under changes folder.
First one creates table as configured in the changelog xml.
Second one has changelog point to a sql file.

**Project Structure**
Note how we segregate Db related things in pom (H2, Liquibase, Data JPA dependencies),
 entities, JPA repos and the Data source class in the data submodule, and include it as a dependency
 in the application module. the service in app module auto wires the JPA repo.

Also note how Hikari data pool is being created using application.properties. To point the application to
actual DB , simply update the app properties.

**Liquibase tables**

Liquibase uses DATABASECHANGELOGLOCK table to ensure only once instance of 
the application is running the liquibase change logs at a time.

DATABASECHANGELOG table is used to maintain a history of changelogs already executed
so that these changelogs are not executed on next application start.

**Rollback**

Ideally all changelogs should have their corresponding rollback scripts as well.
If certain changes are deemed unfit, rollback command can be executed upto a specified tag,
or upto a certain count of changelog entries, 
and all the changes are rolled back and their rollback scripts executed as specified.
Some operations like add column support rollback out of the box. But others like insert statement
need a custom rollback script to be specified.

Eg of rollback command:

mvn liquibase:rollback -Dliquibase.rollbackTag=1.0

As a best practice, one change set should have only a single change in it. 
Don't club sql statements in a single changeset.
This helps with rollback in case of an issue with of the changes.

**Implementing Liquibase on an existing DB**

Easiest option is to leave historical data as is, and start implementing Liquibase tables from a certain point of time.

Another approach is to create changelogs of historical tables. 
Then use changelogsync command to mark these old changelogs, so that they do not get executed by liquibase when app comes up.

A third approach is to use 'liquibase generateChangeLog' command which automatically gneerates changelogs for existing db structure.
But there are chances that some db changelogs might get skipped using this approach.

**Changeset hash**

Developers cannot change existing changelogs. Each changelog once executed gets a changeset hash
stored as MD5SUM in DATABASECHANGELOG table. 
If you change the changeset again, the hash changes.
At time of running liquibase, any mismatch in the has is reported as error
and the operation is not allowed.
Although not encouraged, if you really need to update an existing changelog, then set the MD5SUM column as null in db.






