@echo off
echo Borrando Sintactico.java anterior 
del src\ar\edu\unlu\teocomp1\grupo3\Sintactico.java
rem Opciones de jcup https://versioncontrolseidl.in.tum.de/parsergenerators/cup/-/blob/master/src/java/java_cup/Main.java?ref_type=heads#L286
java -jar java-cup-11b_1.jar -parser Sintactico -destdir src/ar/edu/unlu/teocomp1/grupo3 Sintactico.cup