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
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.*;

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


    @Override
    public void run(ApplicationArguments args) {
        // use the factory methods ONLY, i.e 'newUser', 'newSolution', NOT 'new User' or 'new Solution'.
        Cohort[] cohorts = {
                newCohort("C7")
        };

        var richard = newUser("richard", "richard@fakeaddress.com", "fakepassword", null, List.of(Role.ADMIN));
        var fakestudent = newUser("fakestudent", "student@fakeaddress.com", "phonypassword", cohorts[0], List.of(Role.USER));
        var eoan = newUser("eoan", "eoan.odea@bnta.uk", "testtest", cohorts[0], List.of(Role.USER));
        var student2 = newUser("student2", "student2@fakeaddress.com", "phonypassword", cohorts[0], List.of(Role.USER));
        User[] users = {richard, fakestudent, eoan};


        var addValues = newProblem("addValues", "Create a function, addValues(a:int, b:int), which adds two integers together and returns the result.",
                Difficulty.VERY_EASY,
                newTestSuite(new HashSet<>(Arrays.asList(newTestCase(
                                List.of(ds.of(10), ds.of(5)), ds.of(15)))),
                        new HashSet<>(Arrays.asList(newTestCase(
                                List.of(ds.of(15), ds.of(4)), ds.of(19))))
                ),
                newStartCode("const addValues = (a, b)=> {\n\n}",
                        "def addValues(a, b):\n\nreturn",
                        ""),
                new HashSet<>(Arrays.asList("arithmetic", "adding")));

        var sumOfArray = newProblem("sumOfArray", "Create a function, sumOfArray(vals: int[]), which returns the sum of all integers in a given array.",
                Difficulty.HARD,
                newTestSuite(new HashSet<>(Arrays.asList(newTestCase(
                                List.of(ds.of(new Integer[]{10, 5, 15})), ds.of(30)))),
                        new HashSet<>(Arrays.asList(newTestCase(
                                List.of(ds.of(new Integer[]{15, 4, -8})), ds.of(11))))
                ),
                newStartCode("const sumOfArray = (int[] vals)=> {\n\n}",
                        "def addValues(vals:\n\nreturn",
                        ""),
                new HashSet<>(Arrays.asList("arithmetic", "adding")));

        var productOfArray = newProblem("productOfArray", "Create a function, productOfArray(vals: int[]), which accepts an array of integers and returns their product.",
                Difficulty.MEDIUM,
                newTestSuite(new HashSet<>(
                                Arrays.asList(newTestCase(List.of(
                                        ds.of(new Integer[]{10, 5, 2})), ds.of(100)))),
                        new HashSet<>(
                                Arrays.asList(newTestCase(
                                        List.of(ds.of(new Integer[]{5, 5, 3})),
                                        ds.of(75))))
                ), newStartCode("const productOfArray = (vals) => {\n\n}",
                        "",
                        ""),
                new HashSet<>(Arrays.asList("arithmetic", "product")));

        Problem[] problems = {addValues, sumOfArray, productOfArray};

        Set<String> tags = Set.of("One tag", "A second tag", "A third tag");
        newProblemSet("Sample Problem Set", "A perfectly legitimate description of a problem set",
                Set.of(addValues, sumOfArray, productOfArray), Difficulty.EASY, tags);

        newSolution("const addValues = (a, b) => a + b", "js", true, addValues, richard);
        newSolution("const addValues = (a, b) => a + b", "java", true, addValues, richard);
        newSolution("const addValues = (a, b) => a - b", "js", false, addValues, fakestudent);
        newSolution("const addValues = (a, b) => a - b", "java", false, addValues, fakestudent);
        newSolution("const addValues = (a, b) => a - b", "java", false, productOfArray, fakestudent);
//        newSolution("const sumOfArray = (arr: int[]) => let sum = 0; \narr.forEach((num) => {sum += num});", "js",
//                true, productOfArray, fakestudent);
    }

    private Problem newProblem(String title, String desc, Difficulty diff, TestSuite testSuite, StartCode startCode, Set<String> tags) {
        return problemService.add(new Problem(title, desc, diff, testSuite, startCode, tags));
    }

    private Solution newSolution(String code, String lang, boolean correct, Problem problem, User user) {
        return solutionService.add(new Solution(code, lang, correct, problem, user));
    }

    private ProblemSet newProblemSet(String title, String description, Set<Problem> problems, Difficulty difficulty, Set<String> tags) {
        return problemSetService.add(new ProblemSet(title, description, problems, difficulty, tags));
    }

    private TestSuite newTestSuite(Set<TestCase> publicCases, Set<TestCase> privateCases) {
        return testSuiteService.add(new TestSuite(publicCases, privateCases));
    }

    private TestCase newTestCase(List<Data> inputs, Data output) {
        return testCaseService.add(new TestCase(inputs, output));
    }

    private StartCode newStartCode(String js, String py, String java) {
        return startCodeService.add(new StartCode(js, py, java));
    }

    private Cohort newCohort(String name) {
        return cohortService.add(new Cohort(name));
    }

    private User newUser(String uname, String email, String password, Cohort cohort, List<Role> roles, boolean bipassRegistration) {
        return userService.add(new User(uname, email, password, cohort, roles), bipassRegistration);
    }
    private User newUser(String uname, String email, String password, Cohort cohort, List<Role> roles) {
        return userService.add(new User(uname, email, password, cohort, roles), true);
    }

}
