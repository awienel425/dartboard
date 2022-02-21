# Dart Board Application

## Problem Statement:

The dart board is a circular board consist of 20 sections,  
each section (slice) is further divided into 3 sections (aka _BlockType_):

* Single
* Double
* Triple

During a game of darts, the player needs to achieve a score by throwing at most three darts (aka _DartThrow_)  
at the dart board per turn (_Play_). To win the player also needs to ensure that the last _DartThrow_ is a double (i.e.
lands in the _Double_ section)  
For example, if the target is 4, the player can win by throwing one of the following combinations:

* [0,0, double 2]
* [0,double 2, 0 (Declined)]
* [double 2, 0 (Declined to throw),0 (Declined to throw)]
* [1,1,double 1]

Create a program that takes as input the target that needs to be achieved in three throws  
and returns a set of all winnable plays.  
This can be done by implementing the WinnablePlayCalculator interface.

## Solution:

This has been implemented in two ways:

### WinnablePlayCalculatorFlatMapService:

This implementation uses map and flatmap functions to calculating the required dart throw permutations  
one dart at a time and only considers winnable throws at each point in time.

### WinnablePlayCalculatorBruteForceService:

A brute force implementation , loops 3 times over the all possible scores that can be achieved by a single throw  
and creates plays for all of these outcomes.  
We then filter them down into winnable outcomes.  
This brute force strategy is mitigate by using sequences that would lazily perform the calculations needed. The
advantage of this approach is that it minimizes complexity and allows easy readable code.

## To Run

WinnablePlayCalculatorTest.kt can be run to test both these implementations
 
