# Contract API Speaktoo

## Sign Up

Method: POST

URL: /email/signup
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

## Login

Method: POST

URL: /email/login
### Body Request:
```json
{
  "email": "String",
  "password": "String"
}
```
### Response
#### success
code: 200

Response Body:
```json
{
  "status": "success",
  "message": "user berhasil ditemukan",
  "data": {
    "uid": "String",
    "email": "String",
    "username": "String",
    "progress": "Int",
    "profile_pic": "String"
  }
}
```
#### fail
Code: 400

Response Body:
```json
{
  "status": "fail",
  "message": "pastikan sudah signup dan sesuai format"
}
```

## Get Words by Difficulty

Method: POST

URL: /words/${difficulty}
### Body Request:
```json
{
  "uid": "String"
}
```
### Response
#### success
code: 200

Response Body:
```json
{
  "status": "success",
  "message": "berhasil get words by difficulty",
  "data": [
    {
      "word_id": "Int",
      "word": "String",
      "completed": "Boolean"
    },
    {
      "word_id": "Int",
      "word": "String",
      "completed": "Boolean"
    },
    {
      "word_id": "Int",
      "word": "String",
      "completed": "Boolean"
    }
  ]
}

```
#### fail
Code: 404

Response Body:
```json
{
  “status”: “fail”,
  “message”: “gagal get words”
}
```

## Get Word

Method: POST

URL: /word/${word}

### Response
#### success
code: 200

Response Body:
```json
{
  "status": "success",
  "message": "berhasil get",
  "data": {
    “word”: "String",
    “meaning” : "Array",
    “audio” : "mp3"
  }
}
```
#### fail
Code: 404

Response Body:
```json
{
  “status”: “fail”,
  “message”: “gagal get word”
}
```

## Update Progress

Method: PUT

URL: /user/progress

### Body Request:
```json
{
  "uid": "String",
  "progress": "Int"
}
```

### Response
#### success
code: 200

Response Body:
```json
{
  "status": "success",
  "message": "berhasil update progress",
  "data": {
    "uid": "String",
    "progress": "Int"
  }
}
```
#### fail
Code: 400

Response Body:
```json
{
  "status": "fail",
  "message": "gagal update progress"
}
```

## Post Logs

Method: POST

URL: /user/logs

### Body Request:
```json
{
  "uid": "String",
  "wid": "Int"
}
```

### Response
#### success
code: 200

Response Body:
```json
{
  "status": "success",
  "message": "berhasil post logs"
}
```
#### fail
Code: 400

Response Body:
```json
{
  "status": "fail",
  "message": "gagal post logs"
}
```

## Edit Username

Method: PUT

URL: /user/username

### Body Request:
```json
{
  "uid": "String",
  "username": "String"
}
```

### Response
#### success
code: 200

Response Body:
```json
{
  "status": "success",
  "message": "berhasil mengganti username",
  "data": "String"
}
```
#### fail
Code: 404

Response Body:
```json
{
  "status": "fail",
  "message": "mohon cek kembail akun anda"
}
```

## Upload Profile Picture

Method: POST

URL: /user/profile

### Body Request:
```json
{
  "uid": "String",
  "image": "File"
}
```

### Response
#### success
code: 200

Response Body:
```json
{
  "status": "success",
  "message": "berhasil upload profile picture"
}
```
#### fail
Code: 400

Response Body:
```json
{
  "status": "fail",
  "message": "mohon cek kembail file anda"
}
```
