# Trendyol Backend Bootcamp - Databases NoSQL exercise

## Start a couchbase instance locally
Run following command from your terminal to start a couchbase instance with an admin client.
```
docker run -d --name bootcampdb -p 8091-8094:8091-8094 -p 11210:11210 couchbase
```

- Visit the admin client from http://localhost:8091/
- Follow setup a new cluster. You can give it any name.
- While configuring disk, memory and services uncheck 
search, analytics and eventing services and finish with other 
default configurations. You can check what these services are good 
for from Couchbase Documentation.
- Go to Buckets tab from the left navigation and click "ADD BUCKET" 
on top right of the page.
- Give it "sellers" as name
- From Security tab, click "ADD USER" and define a username 
and password for our application.

## Exercises
Make all tests in SellerServiceTest pass.

Every test method has following parts:
- Given: Preparing the state for the test
- When: Execution of the part to be tested
- Then: Assertions and checks for expected results

You are only allowed to change the part marked with //TODO: in test class.
You can change every other component in this application.

This repository is just a sample implementation to demonstrate spring data couchbase.
You can always try to improve the design and think on a bigger scale.

## How to submit
Fork this repository, commit your changes and open a pull request to be reviewed.
[How to open a pull request from fork](https://docs.github.com/en/github/collaborating-with-pull-requests/proposing-changes-to-your-work-with-pull-requests/creating-a-pull-request-from-a-fork)

## Reference
- [Couchbase Documentation](https://docs.couchbase.com/server/current/learn/architecture-overview.html)
- [N1QL](https://docs.couchbase.com/server/current/n1ql/query.html)
- [Spring-Data/Couchbase](https://docs.spring.io/spring-data/couchbase/docs/current/reference/html/)
- [Couchbase Dockerhub](https://hub.docker.com/_/couchbase)
- [CAP theorem / RDBMS and NoSQL](https://bikas-katwal.medium.com/mongodb-vs-cassandra-vs-rdbms-where-do-they-stand-in-the-cap-theorem-1bae779a7a15)
