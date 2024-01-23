# Mersenne-Prime-Searcher

Program implements the Lucas-Lehmer test algorithm to search for Mersenne numbers, which have the form 2^n - 1, where n is a positive integer. The algorithm leverages multithreading to concurrently process multiple exponents, speeding up the search for Mersenne numbers. The project uses standard Java libraries and multithreading features from the 'java.util.concurrent' package, such as 'ExecutorService' and 'Future', to organize parallel processing. For optimization, the program checks Mersenne numbers for exponents in the range from 1 to 82589933, and it outputs information about found Mersenne primes along with the execution time to the console.