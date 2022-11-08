# How to Build and Run Locally
- Create a PostreSQL db called `lms_db`
- The above db should be accessible via username: `postgres` , password: `bnta_db_2022`
- Make sure that Java, node, and python3 are all installed on your computer (they are accessed via ProcessBuilder internally to compile/exec)

# Authentication and Authorisation
Auth is handled by Spring Security and JWT. There are 2 roles, `ADMIN` and `USER`, assigned on creation of a user record. Before you can use any of the below functionality, a login request with a username and password must be provided. If login is successful, a Jwt access_token is returned in the following format : 
```json
{
    "access_token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJmYWtlc3R1ZGVudCIsInJvbGVzIjpbIlVTRVIiXSwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL2xvZ2luIiwiZXhwIjoxNjY3NTc2NTk0LCJ1c2VySWQiOjJ9.go4em7DFZ8Cp_ZEZnnDfiUKzC9I_uRqpomYteCj_USM",
    "refresh_token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJmYWtlc3R1ZGVudCIsInJvbGVzIjpbIlVTRVIiXSwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL2xvZ2luIiwiZXhwIjoxNjY4MTc3Nzk0fQ.hbhFt0SiEsEdB4owEWgmxxc5uyEJB6h_QuzrLjp_kfE"
}

```
This access token must be included in the `Authorization` header in all subsequent API calls., prefixed by `'Bearer '`, to indicate to Spring Security that this token belongs to the user in question. This process can be handled by a service, to avoid repetition for every axios/fetch call. 

# CompileController

to use the compiler directly (not to test solutions to problems since EvalController handles that.), send a POST request to `/user/compile`. The Request body should include a `code` property
as well as a `lang` property. Currently supported languages are: 
- 'java' (Java)
- 'js' (Node)
- 'py' (Python)

This can be used with a 'run' button to allow the user to run their code with logs for debugging, as logs must be removed or commented out before submitting code to the EvalController.

Use the file-extension of the language in the lang property, as indicated above.

```json
{
  "lang": "java",
  "code": "public class Main {public static void main(String[] args) {System.out.println(\"Hello from Java POST test\");}}"
}
```

If you have provided syntactically valid code, you should get a JSON response in the format of a CompileResult object (id is null since we don't save
the compiled result in the DB. We only do that when through the EvalController):

```json
{
    "id": null,
    "output": "Hello BNTA",
    "errors": "",
    "lang": "js",
    "code": "console.log('Hello BNTA')",
    "compiled": true
}
```

# EvalController 

This is arguably the most important part of the API - where we submit candidate solutions, and evaluate them against the `TestSuite` of a specified `Problem`,
then return a `EvalResult`, which will either be successful, or not. No logging allowed in the code when submitting to EvalController, or they will mess up the output evaluation. POST request to: `/user/eval/{problemId}`, with following JSON format:

```json
{
  "lang": "java",
  "code": "public class Main {public int add(int a, int b) {return a + b;}}",
  "userId": 1
}
```
The response body will be of the `EvalResult` format:

```json
{
    "successful": true,
    "privateTestsPassed": true,
    "publicTestResults": [
        {
            "compileResult": {
                "id": null,
                "output": "15",
                "errors": "",
                "lang": "java",
                "code": "public class Main {public int solution(int a, int b) {return a + b;}public static void main(String[] args){System.out.println(new Main().solution(10, 5));}}",
                "compiled": true
            },
            "correct": true
        }
    ],
    "problem": {
        "id": 1,
        "title": "add",
        "description": "Create a function, addValues(a:int, b:int), which adds two integers together and returns the result.",
        "difficulty": "VERY_EASY",
        "testSuite": {
            "publicCases": [
                {
                    "id": 1,
                    "inputs": [
                        {
                            "id": 1,
                            "value": "10",
                            "dataType": "INT"
                        },
                        {
                            "id": 2,
                            "value": "5",
                            "dataType": "INT"
                        }
                    ],
                    "output": {
                        "id": 3,
                        "value": "15",
                        "dataType": "INT"
                    }
                }
            ],
            "privateCases": [
                {
                    "id": 2,
                    "inputs": [
                        {
                            "id": 4,
                            "value": "15",
                            "dataType": "INT"
                        },
                        {
                            "id": 5,
                            "value": "4",
                            "dataType": "INT"
                        }
                    ],
                    "output": {
                        "id": 6,
                        "value": "19",
                        "dataType": "INT"
                    }
                }
            ]
        },
        "startCode": {
            "id": 1,
            "js": "const addValues = (a, b)=> {\n\n}",
            "py": "def addValues(a, b):\n\nreturn",
            "java": ""
        },
        "tags": [
            "adding",
            "arithmetic"
        ]
    }
}

```

if the eval was successful, a `Solution` record will have been added to the DB as well.

# Problems
Problems are assigned a difficulty level. They can also be assigned tags. In addition to the usual GET routes, you can get them by tag or difficulty:

Get all: GET `/user/problems`

Get by id: GET `/user/problems/{problemId}`

Get by tag: GET `/user/problems/tag/{tag}`

Get by difficulty: GET `/user/problems/difficulty/{difficulty}`

## Create a problem like so: 
Create new: POST `admin/problems/`

```json
{
  "title": "add",
  "description": "Create a function, addValues(a:int, b:int), which adds two integers together and returns the result.",
  "difficulty": "VERY_EASY",
  "testSuite": {
    "publicCases": [
      {
        "inputs": [
          {
            "value": "10",
            "dataType": "INT"
          },
          {
            "value": "5",
            "dataType": "INT"
          }
        ],
        "output": {
          "value": "15",
          "dataType": "INT"
        }
      }
    ],
    "privateCases": [
      {
        "inputs": [
          {
            "value": "15",
            "dataType": "INT"
          },
          {
            "value": "4",
            "dataType": "INT"
          }
        ],
        "output": {
          "value": "19",
          "dataType": "INT"
        }
      }
    ]
  }
}
```
The Controller and Service have been written to loop through all the testCases, inputs etc., and save them to the respective repos. If you have provided the data in the proper format, you will get a Problem response, complete with the id's for all the inner objects (TestSuite, TestCase, Data...):

```json
{
    "id": 4,
    "title": "add",
    "description": "Create a function, addValues(a:int, b:int), which adds two integers together and returns the result.",
    "difficulty": "VERY_EASY",
    "testSuite": {
        "publicCases": [
            {
                "id": 7,
                "inputs": [
                    {
                        "id": 15,
                        "value": "10",
                        "dataType": "INT"
                    },
                    {
                        "id": 16,
                        "value": "5",
                        "dataType": "INT"
                    }
                ],
                "output": {
                    "id": 17,
                    "value": "15",
                    "dataType": "INT"
                }
            }
        ],
        "privateCases": [
            {
                "id": 8,
                "inputs": [
                    {
                        "id": 18,
                        "value": "15",
                        "dataType": "INT"
                    },
                    {
                        "id": 19,
                        "value": "4",
                        "dataType": "INT"
                    }
                ],
                "output": {
                    "id": 20,
                    "value": "19",
                    "dataType": "INT"
                }
            }
        ]
    },
    "startCode": null,
    "tags": null
}
```

# Difficulty Settings
Possible difficulty values are: 
`VERY_EASY`, `EASY`, `MEDIUM`, `HARD`, `VERY_HARD`.

# Input and Output Values
Values can be of the following 8 types, as defined in the DATATYPE Enum:
`INT`, `INT_ARRAY`, `STRING`, `STRING_ARRAY`, `FLOAT`, `FLOAT_ARRAY`, `BOOLEAN`, `BOOLEAN_ARRAY`

# Users
Only accounts with the ADMIN permission can create new users.

New User: POST `/admin/users/`

Global Leaderboard: GET `/user/users/leaderboard`

Cohort Leaderboard: GET `user/users/leaderboard/{cohort}`

# Password Reset

Forgot password (request reset email): GET `/user/users/password/forgot/{userId}`

Reset Password (call from reset form): POST `/user/users/password/reset`

the reset expects a json payload of type `PasswordResetInput`. Provide the secret you received in the reset email (it's a request param in the url):

```json
{
  "userId": 1,
  "secret": "dfqwpeoiasdafdklnqiwenfaisdn",
  "newPassword": "blablabla"
}
```








