## TICKET-101 conclusion
After reviewing the intern's code, I have made a few notes that I will share below. However, there is a problem with the home task assignment itself, where it was not clearly stated about how the segmentation should work. The examples in assignment also would not work with the presented code. I hope I am not wrong for assuming that intern's interpretation of segmentation is the right one. 

### What was done nicely
* The structure of the code is pretty logical, the folders make a nice division between classes' purpose
* Custom exceptions are very helpful in identifying the errors and sending an error message to the user
* All classes and methods are documented, and it's easy to tell what each of them are for

### What could be done better
* Magic numbers in getCreditModifier method in DecisionEngine class could be also put in DecisionEngineConstants, so that it would be easy to understand what they mean, or be easily changed.
* Loan period slider says 6 months is the starting period for loan, but it is in fact 12. The mismatch could be confusing.
* DecisionEngine could be divided into separate classes, as it handles business logic, validation, and exception handling, which goes again SOLID architecture.

## Most important shortcoming
Even though custom exceptions are done really nicely, responses for generic java exceptions have java error 
messages instead of intended "An unexpected error occurred", which could lead to serious data leak by exposing stack traces. 
I noticed that specifically when any of request parameters are null, because even though it throws an error with a message, it still has an HTTP 200 OK response, which is wrong and not an expected behaviour.
It happened due to service just returning a loan decision, but with an exception error message, instead of throwing the exception itself, which resulted in this shortcoming.
<br/>
Fix:
* To prevent unexpected data exposure to users, I changed the server's error messages to "Unexpected error occurred!" in calculateApprovedLoan.
You can use logger for debugging, since the result will not be shown to user, but in developer's console, ensuring more security.
* Now this try-catch block actually throws any caught unexpected exception, resulting in proper error handling. 
```java
try {
    verifyInputs(personalCode, loanAmount, loanPeriod);
} catch (Exception e) {
    throw new UnexpectedException("Unexpected error occurred!");
}
```

## TICKET-102
For the second part of the task, I implemented an age restricting algorithm for decision loans.
* Checking age constraints and throwing new InvalidAgeException.
```java
if (age < DecisionEngineConstants.MINIMUM_AGE_FOR_LOAN || age > DecisionEngineConstants.MAXIMUM_AGE_FOR_LOAN) {
            throw new InvalidAgeException("Invalid age!");
        }
```
* Age retrieving is made with EstonianPersonalCodeValidator from estonian-personal-code-validator:1.6.
* Minimum age is 18, maximum age is 70 (an arbitrary expected lifetime for the Baltic Scope).
* Added a few tests to check the age constraint algorithm, as changing the previous tests' input data to pass with new constraints