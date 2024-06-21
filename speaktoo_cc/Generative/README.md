# Dokumentasi API Speaktoo Generative AI

Dokumentasi ini berisi tentang fitur generative AI yang ada dalam API Speaktoo. Dengan adanya dokumentasi ini diharapkan tim MD dapat memahami bagaimana cara menggunakan API ini.

### Link API
```
https://speaktoo-generative-ygiysmsnnq-et.a.run.app
```

Bila API me-return JSON seperti dibawah atau mengalami server error, mohon menghubungi tim CC
```json
{
  "status": "Error",
  "message": "Error generating content"
}
```

## Generate

Fitur ini digunakan ketika user ingin memendapatkan feedback dari tutor.

### Request

- Method: POST
- URL: /generate
- Body Request:
```json
{
  "word": "String",
  "audio": "Wav file"
}
```
### Response

- Success Code: 200
- Response Body:
```json
{
  "status": "success",
  "result": {
    "word": "String",
    "phonetics": "String",
    "feedback": "String"
  }
}
```
- Fail Code: 500
- Response Body:
```json
{
  "status": "Error",
  "message": "Error generating content"
}
```