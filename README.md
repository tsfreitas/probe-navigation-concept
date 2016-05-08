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

---

* **URL**

    `/mission/probe/:probeName/send`

* **METHOD**

`POST`

* **URL PARAMS**<br/>
    **Required**<br/>
    `probeName=[string]`

* **BODY**

```json
{
  "x": 1,
  "y": 1,
  "direction": "NORTH"
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
        "deployedProbes": [
          {
            "probeName": "pathFinder",
            "coordinate": {
              "x": 1,
              "y": 1
            },
            "direction": "NORTH"
          }
        ]
      }
      ```
* **ERROR RESPONSE**

    * **Code:** `400`<br/>
      **Content:**

    ```json
    {
        "exception": "AlreadyExistProbeException",
        "detailedMessage": "Probe already exists",
        "fieldErros": []
    }
    ```

    ```json
    {
        "exception": "MethodArgumentNotValidException",
        "detailedMessage": "Required field not provided",
        "fieldErros": [
        {
            "field": "direction",
            "message": "não pode ser nulo"
        }
        ]
    }
    ```

    * **Code:** `500`<br/>
      **Content:**
    ```json
    {
        "exception": "CrashException",
        "detailedMessage": "The probe has crashed. Abort mission!!",
        "fieldErros": []
    }
    ```


---

* **URL**

    `/mission/probe/:probeName/commands`

* **METHOD**

`PUT`

* **URL PARAMS**<br/>
    **Required**<br/>
    `probeName=[string]`

* **BODY**

```json
{
  "commands": "MMMM"
}
```

* **SUCCESS RESPONSE**

    * **Code:** `200`<br/>
      **Content:**

      ```json
      {
        "maxCoordinate": {
          "x": 5,
          "y": 5
        },
        "deployedProbes": [
          {
            "probeName": "pathFinder",
            "coordinate": {
              "x": 1,
              "y": 5
            },
            "direction": "NORTH"
          }
        ]
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
             "field": "commands",
             "message": "não pode ser nulo"
           }
         ]
       }
      ```

    * **Code:** `500`<br/>
      **Content:**

      ```json
      {
        "exception": "CrashException",
        "detailedMessage": "The probe has crashed. Abort mission!!",
        "fieldErros": []
      }
      ```



---

*  **URL**

`/mission/report`

* **METHOD**

 `GET`

* **SUCCESS RESPONSE**

    * **Code:** `200` <br/>
      **Content:**

      ```json
      {
          "maxCoordinate": {
              "x": 10,
              "y": 15
          },
          "deployedProbes": [{
              "probeName": "probe1",
              "direction": "NORTH",
              "coordinate": {
                  "x": 1,
                  "y": 4
              }
          }]
      }
      ```

---

*  **URL**

`/mission/probe/:probeName`

* **METHOD**

 `GET`

* **URL PARAMS**<br/>
    **Required**<br/>
    `probeName=[string]`

* **SUCCESS RESPONSE**

    * **Code:** `200` <br/>
      **Content:**

      ```json
      {
          "maxCoordinate": {
              "x": 10,
              "y": 15
          },
          "deployedProbes": [{
              "probeName": "probe1",
              "direction": "NORTH",
              "coordinate": {
                  "x": 1,
                  "y": 4
              }
          }]
      }
      ```
---

*  **URL**

`/mission/offline`

* **METHOD**

 `POST`

* **BODY**<br/>
  **encoding**: `TEXT/PLAIN`

```text
5 5
1 2 N
LMLMLMLMM
3 3 E
MMRMMRMRRM
```
* **SUCCESS RESPONSE**

    * **Code:** `200` <br/>
      **Content:**

      ```json
      {
        "maxCoordinate": {
          "x": 5,
          "y": 5
        },
        "deployedProbes": [
          {
            "probeName": "probe1",
            "coordinate": {
              "x": 1,
              "y": 3
            },
            "direction": "NORTH"
          },
          {
            "probeName": "probe2",
            "coordinate": {
              "x": 5,
              "y": 1
            },
            "direction": "EAST"
          }
        ]
      }
      ```

* **ERROR RESPONSE**

    * **Code:** `400`<br/>
      **Content:**

      ```json
       {
         "exception": "OfflineCommandsException",
         "detailedMessage": "Offline command not accepted. Follow this pattern: '5 5\n1 2 N\nLMLMLMLMM\n3 3 E\nMMRMMRMRRM\n'",
         "fieldErros": []
       }
      ```
    * **Code:** `500`<br/>
      **Content:**

      ```json
      {
        "exception": "CrashException",
        "detailedMessage": "The probe has crashed. Abort mission!!",
        "fieldErros": []
      }
      ```



