# PayLens API
#### PayLens API is used Spring-Boot as Framework with Java Programming Language

### Spring-Boot Dependencies
#### In PayLens API we have many dependencies i.e :
- JWT
- Lombok
- MySql Connector
- Spring data JPA
- Spring Dev-tools
- Spring Security
- Spring Starter Mail
- Spring Starter Test
- Spring Validation

### How To Run This Application : 
#### mvn spring-boot:run (If you have maven)
#### mvnw spring-boot:run (If you don't have maven)

### ERD
![ERD_PayLens] (https://drive.google.com/file/d/1V_Ju2SdtZhzJG85kx3MoLxaG3FRKMQZl/view?usp=sharing)

### Method, Request Body, Path URL, Path Variable
1.	Register Process
-	Register (POST)
#### localhost:4000/paylens/backend/users/register
{
    "username": "paylens admin",
    "email": "paylens@mail.com",
    "password": "paylens1"
}

-	Create PIN (POST)
#### localhost:4000/paylens/backend/users/register/pin/{userId}
{
    "pin": "123456"
}

2.	Login Process
-	Login (POST)
#### localhost:4000/paylens/backend/users/login
{
    "email": "paylens@mail.com",
    "password": "paylens1"
}

3.	Phone Number `Bearer ${token}`
-	Add Phone Number (POST)
#### localhost:4000/paylens/backend/users/phone-number/{detailUserId}
{
    "phoneNumber": "081234567890"
}

-	Delete Phone Number (DELETE)
#### localhost:4000/paylens/backend/users/phone-number/delete/{detailUserId}

4.	Profile Picture `Bearer ${token}`
-	Add Profile Picture (PUT)
#### localhost:4000/paylens/backend/files/change-picture/{filesId}/{userId}

Form Data {key: file}, {value: (fileName)}
 

-	Get Profile Picture (GET)
#### localhost:4000/paylens/backend/files/{filesId}

5.	Transaction `Bearer ${token}`
-	Top Up (POST)
#### localhost:4000/paylens/backend/top_up/{userId}
{
    "amount" : 50000,
    "pin" : "123456"
}

-	Transfer (POST)
#### localhost:4000/paylens/backend/transfer/{userId}
{
    "amount" : 15000,
    "notes" : "duit jajan bulan ini",
    "username" : "paylens admin2",
    "pin" : "123456"
}

6.	Get All Data History `Bearer ${token}`
-	History (GET)
#### localhost:4000/paylens/backend/history/{userId}

7.	Forgot Password
-	Forgot Password within Send Email (POST)
#### localhost:4000/paylens/backend/users/email
{
    "recipient" : "paylens4@mail.com",
    "subject" : "test paylens2",
    "message" : "test mengirim pesan melalui dto/ request body berhasil"
}

8.	Change Password
-	Change Password (PUT)
#### localhost:4000/paylens/backend/users/change-password/{userId}
{
    "password" : "paylens123"
}


