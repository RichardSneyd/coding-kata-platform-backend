# Scracthpad - ideas

// how to create custom rules for problems which work equally across all supported languages;

Use JSON as the common denominator??

Simplified evaluation:
 - for given inputs, which can only be integers, strings, booleans, or arrays of each, expect specified output

when submitting solution for evaluation, check for 'public static void main', and prompt to remove if present
Also, check if code is syntactically valid before allowing submission


Test JS first
Then Java
Then Python

Numbers:
- getFactorial(num: int): int
- getAbsoluteDiff(num1: int, num2: int): int

Arrays:
- getProduct(nums: int[]): int
- average(nums: int[]): float
- containOddNumbers(nums: int[]): boolean
- isSequence(nums: int[]): boolean
- dominantMember(nums: int[]): int
- hasDuplicates(nums: int[]) : boolean

String:
- isPalindrome(str: string): boolean
- isAllSameCase(str: string): boolean
- anyContainIllegalChars(candidateStrings: string[], illegalChars: string): boolean