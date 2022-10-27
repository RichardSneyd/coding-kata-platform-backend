package com.bnta.codecompiler.components;

import com.bnta.codecompiler.models.problems.*;
import com.bnta.codecompiler.models.tests.TestCase;
import com.bnta.codecompiler.models.tests.TestSuite;
import com.bnta.codecompiler.models.users.Role;
import com.bnta.codecompiler.models.users.User;
import com.bnta.codecompiler.services.problems.*;
import com.bnta.codecompiler.services.tests.TestCaseService;
import com.bnta.codecompiler.services.tests.TestSuiteService;
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
    public void run(ApplicationArguments args) throws Exception {
        // use the factory methods ONLY, i.e 'newUser', 'newSolution', NOT 'new User' or 'new Solution'.

        User[] users = {
                newUser("richard", "fakepassword", null, Role.ADMIN),
                newUser("fakestudent", "phonypassword", "C7", Role.USER)
        };

        Problem[] problems = {
                newProblem("addValues", "Create a function, addValues(a:int, b:int), which adds two integers together and returns the result.",
                        Difficulty.VERY_EASY,
                        newTestSuite(new HashSet<>(Arrays.asList(newTestCase(
                                        List.of(ds.of(10), ds.of(5)), ds.of(15)))),
                                new HashSet<>(Arrays.asList(newTestCase(
                                        List.of(ds.of(15), ds.of(4)), ds.of(19))))
                        ),
                        newStartCode("const addValues = (a, b)=> {\n\n}",
                                "def addValues(a, b):\n\nreturn",
                                ""),
                        new HashSet<>(Arrays.asList("arithmetic", "adding"))),

                newProblem("sum", "Create a function, sum(vals: int[]), which returns the sum of all integers in a given array.",
                        Difficulty.VERY_EASY,
                        newTestSuite(new HashSet<>(Arrays.asList(newTestCase(
                                        List.of(ds.of(new Integer[]{10, 5, 15})), ds.of(30)))),
                                new HashSet<>(Arrays.asList(newTestCase(
                                        List.of(ds.of(new Integer[]{15, 4, -8})), ds.of(11))))
                        ),
                        newStartCode("const addValues = (a, b)=> {\n\n}",
                                "def addValues(a, b):\n\nreturn",
                                ""),
                        new HashSet<>(Arrays.asList("arithmetic", "adding"))),

                newProblem("getProduct", "Create a function, getProduct(vals: int[]), which accepts an array of integers and returns their product.",
                        Difficulty.VERY_EASY,
                        newTestSuite(new HashSet<>(
                                        Arrays.asList(newTestCase(List.of(
                                                ds.of(new Integer[]{10, 5, 2})), ds.of(100)))),
                                new HashSet<>(
                                        Arrays.asList(newTestCase(
                                                List.of(ds.of(new Integer[]{5, 5, 3})),
                                                ds.of(75))))
                        ), newStartCode("const getProduct = (vals) => {\n\n}",
                                "",
                                ""),
                        new HashSet<>(Arrays.asList("arithmetic", "product")))
        };

    }

    private Problem newProblem(String title, String desc, Difficulty diff, TestSuite testSuite, StartCode startCode, Set<String> tags) {
        return problemService.add(new Problem(title, desc, diff, testSuite, startCode, tags));
    }

    private ProblemSet newProblemSet(String title, String description, Set<Problem> problems, Difficulty difficulty, Set<String> tags) {
        return problemSetService.add(new ProblemSet(title, description, problems, difficulty, tags));
    }

    private Solution newSolution(String code, String lang, Boolean correct, Problem problem, User user) {
        return solutionService.add(new Solution(code, lang, correct, problem, user));
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

    private User newUser(String uname, String password, String cohort, Role role) {
        return userService.add(new User(uname, password, cohort, role));
    }

}
