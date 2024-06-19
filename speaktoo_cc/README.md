# Dokumentasi API Speaktoo

Dokumentasi ini berisi tentang fitur - fitur yang ada dalam API Speaktoo. Dengan adanya dokumentasi ini diharapkan tim MD dapat memahami bagaimana cara menggunakan API ini.

### Link API
```
https://speaktoo-api-ygiysmsnnq-as.a.run.app
```

Bila API me-return JSON seperti dibawah atau mengalami server error, mohon menghubungi tim CC
```json
{
  "status": "fail",
  "message": "harap maklum"
}
```

## Sign Up

Fitur ini digunakan ketika user ingin melakukan sign up akun baru. Pastikan user belum pernah melakukan sign up dengan email yang sama.

### Request

- Method: POST
- URL: /email/signup
- Body Request:
```json
{
  "email": "String",
  "password": "String",
  "username": "String"
}
```
### Response

- Success Code: 201
- Response Body:
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
- Fail Code: 400
- Response Body:
```json
{
  "status": "fail",
  "message": "pastikan email dan password sesuai format"
}
```

## Login

Fitur ini digunakan ketika user ingin login dengan akun yang sudah tersedia. Pastikan credential user sudah benar.

### Request

- Method: POST
- URL: /email/login
- Body Request:
```json
{
  "email": "String",
  "password": "String"
}
```

### Response

- Success Code: 200
- Response Body:
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
- Fail Code: 400
- Response Body:
```json
{
  "status": "fail",
  "message": "pastikan sudah signup dan sesuai format"
}
```

## Get Words by Difficulty

Fitur ini digunakan ketika user ingin menampilkan seluruh word yang tersedia di aplikasi berdasarkan difficulty-nya.

### Request

- Method: POST
- URL: /words/${difficulty}
- Body Request:
```json
{
  "uid": "String"
}
```

### Response
- Success Code: 200
- Response Body:
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
- Fail Code: 404
- Response Body:
```json
{
  "status": "fail",
  "message": "gagal get words"
}
```

## Get Word

Fitur ini digunakan ketika user ingin mengetahui informasi detail dari sebuah word. Meaning yang di-return oleh API berisi Array dari beberapa definisi yang ada dari word tersebut. Audio yang di-return oleh API berupa URL cara pengucapan word.

### Request

- Method: GET
- URL: /word/${word}

### Response

- Success Code: 200
- Response Body:
```json
{
  "status": "success",
  "message": "berhasil get",
  "data": {
    "word": "String",
    "meaning": "Array",
    "audio": "mp3"
  }
}
```
- Fail Code: 404
- Response Body:
```json
{
  "status": "fail",
  "message": "gagal get word"
}
```

## Update Progress

Fitur ini digunakan ketika user ingin mengupdate progress. Pastikan uid sudah benar.

### Request

- Method: PUT
- URL: /user/progress
- Body Request:
```json
{
  "uid": "String",
  "progress": "Int"
}
```
### Response

- Success Code: 200
- Response Body:
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
- Fail Code: 400
- Response Body:
```json
{
  "status": "fail",
  "message": "gagal update progress"
}
```

## Post Logs

Fitur ini digunakan ketika user telah menyelesaikan suatu word. Dalam request, "wid" adalah "word_id".

### Request
- Method: POST
- URL: /user/logs
- Body Request:
```json
{
  "uid": "String",
  "wid": "Int"
}
```

### Response
- Success Code: 200
- Response Body:
```json
{
  "status": "success",
  "message": "berhasil post logs"
}
```
- Fail Code: 400
- Response Body:
```json
{
  "status": "fail",
  "message": "gagal post logs"
}
```

## Edit Username

Fitur ini digunakan ketika user ingin meng-edit username. Pastikan uid sudah benar.

### Request
- Method: PUT
- URL: /user/username
-  Body Request:
```json
{
  "uid": "String",
  "username": "String"
}
```

### Response
- Success Code: 200
- Response Body:
```json
{
  "status": "success",
  "message": "berhasil mengganti username",
  "data": "String"
}
```
- Fail Code: 404
- Response Body:
```json
{
  "status": "fail",
  "message": "mohon cek kembail akun anda"
}
```

## Upload Profile Picture

Fitur ini digunakan ketika user ingin meng-upload sebuah profile picture. API akan return URL dari file profile picture. Pastikan uid sudah benar.

### Request
- Method: POST
- URL: /user/profile
- Body Request:
```json
{
  "uid": "String",
  "image": "File"
}
```

### Response
- Success Code: 200
- Response Body:
```json
{
  "status": "success",
  "message": "berhasil upload profile picture",
  "data": "url"
}
```
- Fail Code: 400
- Response Body:
```json
{
  "status": "fail",
  "message": "mohon cek kembail file anda"
}
```
