# Zero-knowledge-proof
Zero_Knowledge-proof demonstration using discrete log.  

#Problem:

Peggy wants to prove to Victor that she knows the discrete log of a given value in a given group.
Peggy proves to know the value of x (for example her password).

#Implementaion:

> Say g is the generator and p is a large prime.

> Peggy calculates first for one time the value y = (g^x) mod p  and sends the value to Victor.

> Peggy repeatedly calculates a random value r and C = (g^r) mod p . She tranfers the value C to Victor.

> Victor requests Peggy the value "r" (i.e. request=0) or "(x+r) mod (p-1)" i.e. request=1. 

> In the first case Victor verifies C = (g^r) mod p. In the second case he verifies C.y = {g^((x+r)mod(p-1))} mod p .

This is verified for a fixed number of iterations to ensure Peggy does possess the true value of x.


# Oblivios transfer
Demonstration of Rabin's 1-2 oblivious transfer protocol.

#Problem:
The sender(Alice) has two messages m0 and m1, and the receiver(Bob) has a bit b, and the receiver wishes to receive mb, without the sender learning b, while the sender wants to ensure that the receiver receives only one of the two messages.

#Implementation:

> Alice chooses two messages m0,m1 along with a RSA key pair, comprising the modulus N, the public exponent e and the private exponent d.

> Alice generates two random values, x0, x1 and sends them to Bob along with her public modulus and exponent.

> Bob picks bit b to be either 0 or 1, and selects either the first or second as xb.

> Bob generates a random value k and blinds xb by computing v = (xb + k^e)mod N , which he sends to Alice.
  Alice doesn't know (and hopefully cannot determine) which of x0, x1 Bob chose. She applies both of her random values and comes up with   two possible values for k i.e. k0 = {(v- x0) ^ d}mod N and k1 = {(v- x1) ^ d}mod N .One of these will be equal to k and can be           correctly   decrypted by Bob (but not Alice), while the other will produce a meaningless random value that does not reveal any           information about k.
  
> Alice combines the two secret messages with each of the possible keys, m0' = m0 + k0 and  m1' = m1 + k1 , and sends them both to Bob.

> Bob knows which of the two messages can be unblinded with k, so he is able to compute exactly one of the messages mb = mb' - k.


