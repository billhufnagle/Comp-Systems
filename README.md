# Comp-Systems
For group 7 Computer Systems

Birthday attack: Python program for checking Hash collision of first 10 hex digits.

Bitcoinheader: Java class and main method for hash puzzle.
        The target value held is in a form found on http://learnmeabitcoin.com/glossary/bits It is a compact form of the target so that it fits into the 4 byte space. The first 2 hex digits are the exponent, and the last are the coefficient. The puzzle method runs the hash of the header incrementing the nonce until the hash is below the target value, it then prints the correct expanded target and the hash of the header along with the number of times the loop was ran.

