package com.bnta.codecompiler.components;

import com.bnta.codecompiler.models.problems.*;
import com.bnta.codecompiler.models.tests.TestCase;
import com.bnta.codecompiler.models.tests.TestSuite;
import com.bnta.codecompiler.models.users.Cohort;
import com.bnta.codecompiler.models.users.Role;
import com.bnta.codecompiler.models.users.User;
import com.bnta.codecompiler.services.problems.*;
import com.bnta.codecompiler.services.tests.TestCaseService;
import com.bnta.codecompiler.services.tests.TestSuiteService;
import com.bnta.codecompiler.services.users.CohortService;
import com.bnta.codecompiler.services.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;

@Profile("!production")
@Component
public class DataLoader implements ApplicationRunner {
    @Autowired
    UserService userService;
    @Autowired
    CohortService cohortService;
    @Autowired
    ProblemService problemService;
    @Autowired
    ProblemSetService problemSetService;
    @Autowired
    SolutionService solutionService;
    @Autowired
    TestSuiteService testSuiteService;
    @Autowired
    TestCaseService testCaseService;
    @Autowired
    StartCodeService startCodeService;
    @Autowired
    DataService ds;

    @Value("${environment}")
    private String env;


    @Override
    public void run(ApplicationArguments args) {
        if(env.equals("production")) return;
        // use the factory methods ONLY, i.e 'newUser', 'newSolution', NOT 'new User' or 'new Solution'.
        Cohort[] cohorts = {
                newCohort("C8", LocalDate.now()),
                newCohort("C7", LocalDate.of(2022,10,05)),
                newCohort("C6", LocalDate.of(2022,8,05)),
                newCohort("C5", LocalDate.of(2022,3,05)),
        };
        
        var admin = newUser("admin", "admin@fakeaddress.com", "132456", null, new ArrayList<>(List.of(Role.ADMIN)));
        var fakestudent = newUser("fakestudent", "student@fakeaddress.com", "phonypassword", cohorts[0], new ArrayList<>(List.of(Role.USER)));
//        var eoan = newUser("eoan", "eoan.odea@bnta.uk", "testtest", cohorts[0], List.of(Role.USER));
        var student2 = newUser("student2", "student2@fakeaddress.com", "phonypassword", cohorts[0], new ArrayList<>(List.of(Role.USER)));

        var fakestudent1 = newUser("johndoe", "johndoe@fakeaddress.com", "phonypassword", cohorts[0], new ArrayList<>(List.of(Role.USER)));

        var fakestudent2 = newUser("janesmith", "janesmith@fakeaddress.com", "phonypassword", cohorts[0], new ArrayList<>(List.of(Role.USER)));

        var fakestudent3 = newUser("bobjohnson", "bobjohnson@fakeaddress.com", "phonypassword", cohorts[0], new ArrayList<>(List.of(Role.USER)));

        var fakestudent4 = newUser("samanthalee", "samanthalee@fakeaddress.com", "phonypassword", cohorts[0], new ArrayList<>(List.of(Role.USER)));

        var fakestudent5 = newUser("davidkim", "davidkim@fakeaddress.com", "phonypassword", cohorts[1], new ArrayList<>(List.of(Role.USER)));

        var fakestudent6 = newUser("olivianguyen", "olivianguyen@fakeaddress.com", "phonypassword", cohorts[1], new ArrayList<>(List.of(Role.USER)));

        var fakestudent7 = newUser("williamchen", "williamchen@fakeaddress.com", "phonypassword", cohorts[1], new ArrayList<>(List.of(Role.USER)));

        var fakestudent8 = newUser("lindarodriguez", "lindarodriguez@fakeaddress.com", "phonypassword", cohorts[2], new ArrayList<>(List.of(Role.USER)));

        var fakestudent9 = newUser("danielpark", "danielpark@fakeaddress.com", "phonypassword", cohorts[2], new ArrayList<>(List.of(Role.USER)));

        var fakestudent10 = newUser("maggiewong", "maggiewong@fakeaddress.com", "phonypassword", cohorts[2], new ArrayList<>(List.of(Role.USER)));

        var fakestudent11 = newUser("juanperez", "juanperez@fakeaddress.com", "phonypassword", cohorts[3], new ArrayList<>(List.of(Role.USER)));
        var fakestudent12 = newUser("emilydavis", "emilydavis@fakeaddress.com", "phonypassword", cohorts[3], new ArrayList<>(List.of(Role.USER)));
        var fakestudent13 = newUser("michaellee", "michaellee@fakeaddress.com", "phonypassword", cohorts[3], new ArrayList<>(List.of(Role.USER)));


        User[] users = {admin, fakestudent, student2, fakestudent1, fakestudent2, fakestudent3, fakestudent4, fakestudent5, fakestudent6, fakestudent7, fakestudent8, fakestudent9, fakestudent10, fakestudent11, fakestudent12, fakestudent13};


        var addValues = newProblem("addValues", "Create a function, addValues(a:int, b:int), which adds two integers together and returns the result.",
                Difficulty.VERY_EASY,
                newTestSuite(new ArrayList<>(Arrays.asList(newTestCase(
                                new ArrayList<>(List.of(ds.of(10), ds.of(5))), ds.of(15)))),
                        new ArrayList<>(Arrays.asList(newTestCase(
                                new ArrayList<>(List.of(ds.of(15), ds.of(4))), ds.of(19))))
                ),
                newStartCode("const addValues = (a, b)=> {\n\n}",
                        "def addValues(a, b):\n\nreturn",
                        ""),
                new HashSet<>(Arrays.asList("arithmetic", "adding")));

        var sumOfArray = newProblem("sumOfArray", "Create a function, sumOfArray(vals: int[]), which returns the sum of all integers in a given array.",
                Difficulty.HARD,
                newTestSuite(new ArrayList<>(Arrays.asList(newTestCase(
                                new ArrayList<>(List.of(ds.of(new Integer[]{10, 5, 15}))), ds.of(30)))),
                        new ArrayList<>(Arrays.asList(newTestCase(
                                List.of(ds.of(new Integer[]{15, 4, -8})), ds.of(11))))
                ),
                newStartCode("const sumOfArray = (int[] vals)=> {\n\n}",
                        "def addValues(vals:\n\nreturn",
                        ""),
                new HashSet<>(Arrays.asList("arithmetic", "adding")));

        var productOfArray = newProblem("productOfArray", "Create a function, productOfArray(vals: int[]), which accepts an array of integers and returns their product.",
                Difficulty.MEDIUM,
                newTestSuite(
                                new ArrayList<>(Arrays.asList(newTestCase(List.of(
                                        ds.of(new Integer[]{10, 5, 2})), ds.of(100)))),

                                new ArrayList<>(Arrays.asList(newTestCase(
                                        new ArrayList<>(List.of(ds.of(new Integer[]{5, 5, 3}))),
                                        ds.of(75))))
                ), newStartCode("const productOfArray = (vals) => {\n\n}",
                        "",
                        ""),
                new HashSet<>(Arrays.asList("arithmetic", "product")));

        var largestElement = newProblem("largestElement", "Create a function, largestElement(vals: int[]), which accepts an array of integers and returns the largest integer.",
                Difficulty.EASY,
                newTestSuite(
                        new ArrayList<>(Arrays.asList(newTestCase(List.of(
                                ds.of(new Integer[]{1, 2, 3, 4})), ds.of(4)))),

                        new ArrayList<>(Arrays.asList(newTestCase(
                                List.of(ds.of(new Integer[]{5, 7, 3, 8, 1})),
                                ds.of(8))))
                ), newStartCode("const largestElement = (vals) => {\n\n}",
                        "",
                        ""),
                new HashSet<>(Arrays.asList("arrays", "max")));

        var reverseString = newProblem("reverseString", "Create a function, reverseString(str: string), which accepts a string and returns the string in reverse.",
                Difficulty.EASY,
                newTestSuite(
                        new ArrayList<>(Arrays.asList(newTestCase(new ArrayList<>(List.of(
                                ds.of("hello"))), ds.of("olleh")))),

                        new ArrayList<>(Arrays.asList(newTestCase(
                                new ArrayList<>(List.of(ds.of("apple"))),
                                ds.of("elppa"))))
                ), newStartCode("const reverseString = (str) => {\n\n}",
                        "",
                        ""),
                new HashSet<>(Arrays.asList("strings", "reverse")));

        var palindrome = newProblem("palindrome", "Create a function, palindrome(str: string), which accepts a string and returns true if the string is a palindrome and false otherwise.",
                Difficulty.MEDIUM,
                newTestSuite(
                        new ArrayList<>(Arrays.asList(newTestCase(List.of(
                                ds.of("racecar")), ds.of(true)))),

                        new ArrayList<>(Arrays.asList(newTestCase(
                                List.of(ds.of("apple")),
                                ds.of(false))))
                ), newStartCode("const palindrome = (str) => {\n\n}",
                        "",
                        ""),
                new HashSet<>(Arrays.asList("strings", "palindrome")));

        var reverseLinkedList = newProblem("reverseLinkedList", "Create a function, reverseLinkedList(head: ListNode), which accepts the head of a singly linked list and returns the reversed linked list.",
                Difficulty.HARD,
                newTestSuite(
                        new ArrayList<>(Arrays.asList(newTestCase(List.of(
                                ds.of(new Integer[]{1, 2, 3})), ds.of(new Integer[]{3, 2, 1})))),

                        new ArrayList<>(Arrays.asList(newTestCase(List.of(ds.of(new Integer[]{1, 2, 3, 4, 5})), ds.of(new Integer[]{5, 4, 3, 2, 1}))
                        ))
                ), newStartCode("const reverseLinkedList = (head) => {\n\n}",
                "",
                ""),
                new HashSet<>(Arrays.asList("linked list", "reverse")));

        var bubbleSort = newProblem("bubbleSort", "Create a function, bubbleSort(vals: int[]), which accepts an array of integers and returns the array sorted using the bubble sort algorithm.",
                Difficulty.MEDIUM,
                newTestSuite(
                        new ArrayList<>(Arrays.asList(newTestCase(List.of(
                                ds.of(new Integer[]{4, 2, 1, 5})), ds.of(new Integer[]{1, 2, 4, 5})))),

                        new ArrayList<>(Arrays.asList(newTestCase(
                                        List.of(ds.of(new Integer[]{5, 3, 9, 1, 7})), ds.of(new Integer[]{1, 3, 5, 7, 9}))
                        ))
                ), newStartCode("const bubbleSort = (vals) => {\n\n}",
                "",
                ""),
                new HashSet<>(Arrays.asList("sorting", "bubble")));

        Problem[] problems = {addValues, sumOfArray, productOfArray, largestElement, reverseString, palindrome, reverseLinkedList, bubbleSort};

        Set<String> tags = Set.of("One tag", "A second tag", "A third tag");
        newProblemSet("Beginner Problem Set", "A perfectly legitimate description of a problem set",
                Set.of(addValues, sumOfArray, productOfArray), Difficulty.EASY, tags);

        newProblemSet("Intermediate Problem Set", "A perfectly legitimate description of a problem set",
                Set.of(largestElement, reverseString, palindrome), Difficulty.MEDIUM, tags);

        newProblemSet("Difficult Problem Set", "A perfectly legitimate description of a problem set",
                Set.of(reverseLinkedList, bubbleSort, palindrome), Difficulty.HARD, tags);

        newSolution("const addValues = (a, b) => a + b", "js", 100, addValues, admin, LocalDate.now());
        newSolution("const addValues = (a, b) => a + b", "java", 90, addValues, admin, LocalDate.of(2022,9,05));
        newSolution("const addValues = (a, b) => a - b", "js", 40, addValues, fakestudent, LocalDate.of(2022,9,05));
        newSolution("const addValues = (a, b) => a - b", "java", 20, addValues, fakestudent, LocalDate.of(2022,8,05));
        newSolution("const addValues = (a, b) => a - b", "java", 15 , productOfArray, fakestudent, LocalDate.of(2022,8,05));


        String[] solutions = { "const addValues = (a, b) => a - b", "function factorial(n) { return n < 2 ? 1 : n * factorial(n-1); }" };
        String[] languages = { "java", "python", "javascript" };

        LocalDate startDate = LocalDate.of(2022, 6, 1);
        LocalDate endDate = LocalDate.of(2023, 2, 28);
        Random random = new Random();

        for (int i = 0; i < 20; i++) {
            String solution = solutions[random.nextInt(solutions.length)];
            String language = languages[random.nextInt(languages.length)];
            int score = random.nextInt(101);

            Random rand = new Random();
            int index = rand.nextInt(users.length);
            User student = users[index];

            LocalDate date = startDate.plusDays(random.nextInt((int) endDate.toEpochDay() - (int) startDate.toEpochDay() + 1));
            newSolution(solution, language, score, productOfArray, student, date);
        }
    }

    private Problem newProblem(String title, String desc, Difficulty diff, TestSuite testSuite, StartCode startCode, Set<String> tags) {
        return problemService.add(new Problem(title, desc, diff, testSuite, startCode, tags));
    }

    private Solution newSolution(String code, String lang, int correctness, Problem problem, User user, LocalDate date) {
        Solution solution = solutionService.add(new Solution(code, lang, correctness, problem, user));
        solution.setSubmissionDate(date);
        solutionService.update(solution);
        return solution;
    }

    private ProblemSet newProblemSet(String title, String description, Set<Problem> problems, Difficulty difficulty, Set<String> tags) {
        return problemSetService.add(new ProblemSet(title, description, problems, difficulty, tags));
    }

    private TestSuite newTestSuite(List<TestCase> publicCases, List<TestCase> privateCases) {
        return testSuiteService.add(new TestSuite(publicCases, privateCases));
    }

    private TestCase newTestCase(List<Data> inputs, Data output) {
        return testCaseService.add(new TestCase(inputs, output));
    }

    private StartCode newStartCode(String js, String py, String java) {
        return startCodeService.add(new StartCode(js, py, java));
    }

    private Cohort newCohort(String name, LocalDate date) {
        Cohort cohort = cohortService.add(new Cohort(name));
        cohort.setStartDate(date);
        cohortService.update(cohort);
        return cohort;
    }

//    private User newUser(String uname, String email, String password, Cohort cohort, List<Role> roles, boolean bipassRegistration) {
//        return userService.add(new User(uname, email, password, cohort, roles), bipassRegistration);
//    }
    private User newUser(String uname, String email, String password, Cohort cohort, List<Role> roles) {
        return userService.add(new User(uname, email, password, cohort, roles), true);
    }

}
