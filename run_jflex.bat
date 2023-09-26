@echo off
echo Deleting previous lexer class 
del src\ar\edu\unlu\teocomp1\grupo3\Lexer.java
jflex TeoComp1Lexer.flex -d src/ar/edu/unlu/teocomp1/grupo3