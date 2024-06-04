# Contract API Speaktoo

## Sign Up

Method: POST
URL: /apiv1/email/signup
### Body Request:
```json
{
  "email": "String",
  "password": "String",
  "username": "String"
}
```
### Response
#### success
code: 201
Response Body:
```json
{
  "status": "success",
  "message": "user berhasil ditambahkan",
  "data": {
    "uid": "String",
    "email": "String",
    "username": "String"
  }
}
```
#### fail
Code: 400
Response Body:
```json
{
  "status": "fail",
  "message": "pastikan email dan password sesuai format"
}
```
