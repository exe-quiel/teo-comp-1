@echo off
echo Borrando Lexico.java anterior 
del src\ar\edu\unlu\teocomp1\grupo3\Lexico.java
jflex-full-1.7.0.jar Lexico.flex -d src/ar/edu/unlu/teocomp1/grupo3