# zero-knowledge-proof
Zero_Knowledge-proof demonstration using discrete log.  

#Problem
Peggy wants to prove to Victor that she knows the discrete log of a given value in a given group.
Peggy proves to know the value of x (for example her password).

#Implementaion:
Say g is the generator and p is a large prime.
Peggy calculates first for one time the value y = (g^x) mod p  and sends the value to Victor.
Peggy repeatedly calculates a random value r and C = (g^r) mod p . She tranfers the value C to Victor.
Victor requests Peggy the value "r" (i.e. request=0) or "(x+r) mod (p-1)" i.e. request=1. 
In the first case Victor verifies C = (g^r) mod p. In the second case he verifies C.y = {g^((x+r)mod(p-1))} mod p .
This is verified for a fixed number of iterations to ensure Peggy does possess the true value of x.

