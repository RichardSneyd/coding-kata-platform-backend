# Compiler

to use the compiler, send a POST request to `{domain.com}/compile`. The Request body should include a `code` property
as well as a `lang` property. Currently supported language compilers are: 
- java (Java 18.0.2)
- js (Node 16.16.0)
- py (Python 3.9.13)

```json
{
  "lang": "java",
  "code": "public class Main {public static void main(String[] args) {System.out.println(\"Hello from Java POST test\");}}"
}
```

If you have provided syntactically valid code, you should get a JSON response in the format:

```json
{
  "message": "Hello from Java POST test",
  "correct": true,
  "lang": "java"
}
```