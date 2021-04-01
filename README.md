# RahulSingh_BackEnd_SpringBootDeveloper

# Getting Started

### How to run application
1. import project in intellij
2. Run mvn clean install
3. Run "AlertServiceApplication"
4. Application should be UP and running on port 9898

### Test api

1. Create Team using postman hit api "http://localhost:9898/team", with below payload
 
HTTP Method  - POST 
Request - 
  `{
       "teamName" : "test123",
       "developers" : [
           {
             "name": "testing",
             "phoneNumber": "9561111111"  
           },
           {
             "name": "Test",
             "phoneNumber": "9561154999"  
           }
       ]
   }` 
   
Response - 

`{
     "teamId": "e35bbe15-c135-4b9a-abd7-0a6ae04fdc25"
 }`

2. Send alert for sample team id 
HTTP Method -  POST
URL - http://localhost:9898/e35bbe15-c135-4b9a-abd7-0a6ae04fdc25/alert

Response -

`
{
"success":"alert sent"
}
`
### Access h2 console

1. hit url - http://localhost:9898/h2
2. use below details for connecting

    JDBC URL - jdbc:h2:mem:testdb
    Driver Class - org.h2.Driver
3. Two Tables will be present, named - Teams, Developers.


