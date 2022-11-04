# How to Build and Run Locally
- Create a PostreSQL db called `lms_db`
- The above db should be accessible via username: `postgres` , password: `bnta_db_2022`
- Make sure that Java, node, and python3 are all installed on your computer (they are accessed via ProcessBuilder internally to compile/exec)

# Authentication and Authorisation
Auth is handled by Spring Security and JWT. Before you can use any of the below functionality, a login request with a username and password must be provided. If login is successful, a Jwt access_token is returned in the following format : 
```json
{
    "access_token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJmYWtlc3R1ZGVudCIsInJvbGVzIjpbIlVTRVIiXSwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL2xvZ2luIiwiZXhwIjoxNjY3NTc2NTk0LCJ1c2VySWQiOjJ9.go4em7DFZ8Cp_ZEZnnDfiUKzC9I_uRqpomYteCj_USM",
    "refresh_token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJmYWtlc3R1ZGVudCIsInJvbGVzIjpbIlVTRVIiXSwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL2xvZ2luIiwiZXhwIjoxNjY4MTc3Nzk0fQ.hbhFt0SiEsEdB4owEWgmxxc5uyEJB6h_QuzrLjp_kfE"
}

```
This access token must be included in the `Authorization` header in all subsequent API calls., prefixed by `'Bearer '`, to indicate to Spring Security that this token belongs to the user in question. This process can be handled by a service, to avoid repetition for every axios/fetch call. 

# CompileController

to use the compiler directly (not to test solutions to problems since EvalController handles that. More as a playground), send a POST request to `/user/compile`. The Request body should include a `code` property
as well as a `lang` property. Currently supported languages are: 
- 'java' (Java)
- 'js' (Node)
- 'py' (Python)

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
then return a `EvalResult`, which will either be successful, or not. POST request to: `/user/eval/{problemId}`, with following JSON format:

```json
{
  "lang": "java",
  "code": "public class Main {public static void main(String[] args) {System.out.println(\"Hello from Java POST test\");}}"
}
```