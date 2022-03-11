# User survey system

For running application You need
`jdk16`, `maven` installed

`mysql` database server up and running.

create database with name  `survey`

execute `db.sql` scripts on db

In root project location run following.

```mvn clean package```

After that in `/target` directory You will find file named 
`user-survey-system.jar`

To run application You need to run command in terminal or cmd
```java -jar user-survey-system.jar```

If no exceptions in log then application up and running

You can follow API endpoint definitions