# BrightCode Backend:
- [How to Build and Run Locally](#how-to-build-and-run-locally)
- [Authentication and Authorisation](#authentication-and-authorisation)
- [CompileController](#compilecontroller)
- [EvalController](#evalcontroller)
- [Problems](#problems)
- [Solutions](#solutions)
- [Problem Sets](#problem-sets)
- [Difficulty Settings](#difficulty-settings)
- [Input & Output Values](#input-and-output-values)
- [Users](#users)
- [Leaderboards](#get-users--leaderboards)
- [Password Reset](#password-reset)
- [Cohorts](#cohorts)
- [Scoring](#scoring)

# How to Build and Run Locally

- Create a PostreSQL db called `lms_db`
- The above db should be accessible via username: `postgres` , password: `bnta_db_2022`
- Make sure that Java, node, and python3 are all installed on your computer (they are accessed via ProcessBuilder internally to compile/exec)

# Authentication and Authorisation

Auth is handled by Spring Security and JWT. There are 2 roles, `ADMIN` and `USER`, assigned on creation of a user record. Before you can use any of the below functionality, a login request with a username and password must be provided. If login is successful, a Jwt access_token is returned:

```json
{
  "access_token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJmYWtlc3R1ZGVudCIsInJvbGVzIjpbIlVTRVIiXSwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL2xvZ2luIiwiZXhwIjoxNjY3NTc2NTk0LCJ1c2VySWQiOjJ9.go4em7DFZ8Cp_ZEZnnDfiUKzC9I_uRqpomYteCj_USM",
  "refresh_token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJmYWtlc3R1ZGVudCIsInJvbGVzIjpbIlVTRVIiXSwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL2xvZ2luIiwiZXhwIjoxNjY4MTc3Nzk0fQ.hbhFt0SiEsEdB4owEWgmxxc5uyEJB6h_QuzrLjp_kfE"
}

```

This access token must be included in the `Authorization` header in all subsequent API calls., prefixed by `'Bearer '`, to indicate to Spring Security that this token belongs to the user in question. This process can be handled by a service, to avoid repetition for every axios/fetch call.

# CompileController

to use the compiler directly (not to test solutions to problems since EvalController handles that.), send a POST request to `/user/compile`. The Request body should include a `code` property as well as a `lang` property. Currently supported languages are:

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

If you have provided syntactically valid code, you should get a JSON response in the format of a CompileResult object (id is null since we don't save the compiled result in the DB. We only do that when through the EvalController):

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

This is arguably the most important part of the API - where we submit attempted solutions, and evaluate them against the `TestSuite` of a specified `Problem`,
then return a `EvalResult`, which will either be successful, or not. User logging, such as sout or console.log, are programmatically removed before evaluation to avoid outcome corruption. POST request to: `/user/eval/{problemId}`, with following
JSON format:

```json
{
  "lang": "java",
  "code": "public class Main {public int add(int a, int b) {return a + b;}}",
  "userId": 1
}
```

The response body will be of the `EvalResult` format. The code is automatically sanitised for logging before being evaluated. The result of running the test **with** the users logs can be accessed in the `testResultsWithLogs` property. This allows us to feed back to them the output from their manual debugging/testing. The test results after the logs have been automatically removed, are accessed via `publicTestResults`:

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
  "testResultsWithLogs": [
    {
      "compileResult": {
        "id": null,
        "output": "user logs\n15",
        "errors": "",
        "lang": "java",
        "code": "public class Main {public int solution(int a, int b) {System.out.println(\"user logs\"); return a + b;}public static void main(String[] args){System.out.println(new Main().solution(10, 5));}}",
        "compiled": true
      },
      "correct": false
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

All problems are assigned a difficulty level. They can also be assigned tags. In addition to the usual GET routes, you can
get them by tag or difficulty:

Get all: GET `/user/problems`

Get by id: GET `/user/problems/{problemId}`

Get by tag: GET `/user/problems/tag/{tag}`

Get by difficulty: GET `/user/problems/difficulty/{difficulty}`

## Create a problem:

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

The Controller and Service have been written to loop through all the testCases, inputs etc., and save them to the
respective repos. If you have provided the data in the proper format, you will get a Problem response, complete with the
id's for all the inner objects (TestSuite, TestCase, Data...):

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

## Solutions
You can retrieve all the solutions for a problem like so:

GET `/user/problems/{problemId}/solutions`

# Problem Sets
Problem Sets are basically groups of problems, with a title and description for the group.

Get all: GET `/user/problems/sets`
Get by id: GET `/user/problems/sets/{id}`

Make new set: POST `/admin/problems/sets`

Payload example:

```json
{
  "title": "Loops 101",
  "description": "A group of problems to practice the fundamentals of loops",
  "problems": [
    {"id":  1},
    {"id": 2}
  ]
}
```

## Difficulty Settings

Possible difficulty values are:
`VERY_EASY`, `EASY`, `MEDIUM`, `HARD`, `VERY_HARD`.

## Input and Output Values

Values can be of the following 8 types, as defined in the DATATYPE Enum:
`INT`, `INT_ARRAY`, `STRING`, `STRING_ARRAY`, `FLOAT`, `FLOAT_ARRAY`, `BOOLEAN`, `BOOLEAN_ARRAY`

# Users

Only ADMIN accounts can create, udpate or delete users. While you can create new users individually here, such as for ADMIN accounts (trainers etc), it is generally best practice to create USER accounts (students) when you create a new cohort via the CohortController, documented below. This is the fastest and most efficient approach.

New User: POST `/admin/users/`, with in input of the format:

```json
{
  "username": "fakestudent",
  "email": "fake@phony.com",
  "cohort": {
    "id": 1
  },
  "roles": [
    "USER"
  ],
  "startDate": "2022-11-08"
}
```

The user will be issued an email to set up their own password.

## Get Users & Leaderboards
All Users: GET `/admin/users`

User by id: GET `/user/users/{userId}`

Global Leaderboard: GET `/admin/users/leaderboard`

Leaderboard by Cohort Id: GET `user/users/leaderboard/{cohortId}`

Leaderboard by Cohort Name: GET `user/users/leaderboard/cohort-name/{cohortName}`


These routes will return User objects:

```json
[
    {
        "id": 3,
        "username": "JoeBlogs",
        "email": "joeblogs@hotmail.com",
        "cohort": null,
        "roles": [
            "USER"
        ],
        "completedProblems": [],
        "score": 0,
        "joinDate": "2022-11-09",
        "solutions": []
    },
    {
        "id": 1,
        "username": "richard",
        "email": "richard@fakeaddress.com",
        "cohort": null,
        "completedProblems": [],
        "roles": [
            "ADMIN"
        ],
        "score": 0,
        "joinDate": "2022-11-09",
        "solutions": []
    },
    {
        "id": 2,
        "username": "fakestudent",
        "email": "student@fakeaddress.com",
        "cohort": {
            "id": 1,
            "name": "C7",
            "startDate": "2022-11-09"
        }, 
        "completedProblems": [],
        "roles": [
            "USER"
        ],
        "score": 0,
        "joinDate": "2022-11-09",
        "solutions": []
    },
    {
        "id": 4,
        "username": "CaptainCrisps",
        "email": "captaincrisps@nowhere.com",
        "cohort": null,
        "completedProblems": [],
        "roles": [
            "USER"
        ],
        "score": 0,
        "joinDate": "2022-11-09",
        "solutions": []
    }
]
```

## Update and Delete Users

Update: PUT `/admin/users`
Delete: DELETE `/admin/users/{userId}`

## Password Reset

Forgot password (request reset email): GET `/password/forgot/{userEmail}`

Reset Password (call from reset form): POST `/password/reset`

the reset expects a json payload of type `PasswordResetInput`. Provide the secret you received in the reset email (it's
a request param in the url):

```json
{
  "userId": 1,
  "secret": "dfqwpeoiasdafdklnqiwenfaisdn",
  "newPassword": "blablabla"
}
```

# Cohorts

Cohorts can only be created by ADMIN users:

POST `/admin/cohorts`

You can create the cohort members at the same time, as demonstarted below. The payload should follow this format:

```json
{
  "name": "C8",
  "members": [
    {
      "username": "JoeBlogs",
      "email": "joeblogs@hotmail.com"
    },
    {
      "username": "CaptainCrisps",
      "email": "captaincrisps@nowhere.com"
    }
  ]
}
```
If successful, you will be returned a cohort object complete with startDate and id properties. Any new members submitted will also have been processed, and emailed to set their own password: 

```json
{
  "id": 2,
  "name": "C8",
  "startDate": "2022-11-09",
  "members": [
    {
      "id": 3,
      "username": "JoeBloggs",
      "email": "richardsneyd@hotmail.com",
      "roles": [
        "USER"
      ],
      "score": 0,
      "joinDate": "2022-11-09",
      "solutions": []
    },
    {
      "id": 4,
      "username": "CaptainCrisps",
      "email": "captaincrisps@nowhere.com",
      "roles": [
        "USER"
      ],
      "score": 0,
      "joinDate": "2022-11-09",
      "solutions": []
    }
  ]
}
```

## Get  Cohorts

All: GET `user/cohorts` 

```json
[
  {
    "id": 1,
    "name": "C7",
    "startDate": "2022-11-09",
    "members": [
      {
        "id": 2,
        "username": "fakestudent",
        "email": "student@fakeaddress.com",
        "roles": [
          "USER"
        ],
        "score": 0,
        "joinDate": "2022-11-09",
        "solutions": []
      }
    ]
  },
  {
    "id": 2,
    "name": "C8",
    "startDate": "2022-11-09",
    "members": [
      {
        "id": 3,
        "username": "JoeBlogs",
        "email": "joeblogs@hotmail.com",
        "roles": [
          "USER"
        ],
        "score": 0,
        "joinDate": "2022-11-09",
        "solutions": []
      },
      {
        "id": 4,
        "username": "CaptainCrisps",
        "email": "captaincrisps@nowhere.com",
        "roles": [
          "USER"
        ],
        "score": 0,
        "joinDate": "2022-11-09",
        "solutions": []
      }
    ]
  }
]
```

By Id: GET `user/cohorts/{cohortId}`

By Name: GET `user/cohorts/by-name/{cohortName}`

Delete Cohort: DELETE `user/cohorts/{cohortId}`

Update cohort: PUT `user/cohorts`

## Scoring

The emphasis with the scoring system is on encouraging and rewarded perseverance, to increase practice time and engagement. Scores are awarded based on the difficulty level of the problem. Points are awarded for multiple solutions to the same problem, if they are in different languages. So, the user could submit 3 solutions: One in Java, Python and JS respectively, and receive points for each of those solutions. Points are only awarded *once for each language*.



