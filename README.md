# Lançamento de sondas
Exercício de simulador de sonda. O sistema deverá movimentar uma sonda dentro de um planalto e tratar possíveis problemas.

## Serviços existentes


* **URL**

    `/mission/startMisssion`

* **METHOD**

`POST`

* **BODY**

```json
{
    "x": 5,
    "y": 5
}
```

* **SUCCESS RESPONSE**

    * **Code:** `201`<br/>
      **Content:**
      ```json
      {
        "maxCoordinate": {
          "x": 5,
          "y": 5
        },
        "deployedProbes": []
      }
      ```
* **ERROR RESPONSE**

    * **Code:** `400`<br/>
      **Content:**
      ```json
      {
        "exception": "MethodArgumentNotValidException",
        "detailedMessage": "Required field not provided",
        "fieldErros": [
          {
            "field": "x",
            "message": "may not be null"
          }
        ]
      }
      ```

