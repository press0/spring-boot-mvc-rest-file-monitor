[boot-startupt](images/fetchAll.png)



**Summary**

- [x] build and run unit tests
- [x] run
- [x] Get how many students are registered for a Class
- [x] Get how many students take more than one Class
- [x] Simulate multiple clients with a CyclicBarrier integration test
- [x] Monitor file for external updates and reload file


**build and run unit tests**

```
mvn clean package
```

**run**
```
java -jar target/bmw-0.1.0.jar
```
![boot-startupt] (/images/boot-startup.png)


Get how many students are registered for a Class
```
$ curl http://localhost:9000/register/studentsRegisteredForCourse/chemistry ; echo
2
```
Get how many students take more than one Class
```
$ curl http://localhost:9000/register/studentsTakingMoreThanOneCourse ; echo
1
```
Simulate multiple clients with a CyclicBarrier integration test
```
mvn -Dtest=CyclicBarrierIntegrationTest test
```
![integration-test] (/images/integration-test.png)

Monitor file for external updates and Reload
```
touch target/classes/file.csv 
```

![integration-test] (/images/file-monitor.png)






