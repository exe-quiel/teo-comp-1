# Trabajo práctico: compilador

## Primera entrega (5/10)

Consiste en la implementación en Java de un analizador lexicográfico para un lenguaje inventado por nosotros. Usamos [JFlex](https://www.jflex.de/) para generar una clase llamada [Lexico.java](src/ar/edu/unlu/teocomp1/grupo3/Lexico.java), que se encarga de realizar el análisis. Este archivo se genera a partir de [TeoComp1Lexer.flex](TeoComp1Lexer.flex), donde se defininen las reglas lexicográficas del lenguaje.

Para ejecutar el programa, se debe correr el método `main` de la clase [VentanaMain.java](src/ar/edu/unlu/teocomp1/grupo3/VentanaMain.java).

## Segunda entrega

(TO-DO)

## Lenguaje

### Palabras reservadas

Palabra reservada | Explicación
---|---
`if` | Delimita el inicio de una estructura alternativa. Seguido de una expresión booleana
`endif` | Delimita el fin de una estructura alternativa
`repeat` | Delimita el inicio de una estructura repetitiva
`until` | Delimita el fin de una estructura repetitiva. Seguido de una expresión booleana
`write` | Función que recibe un parámetro y lo imprime por pantalla
`minimo` | Función que recibe una lista y devuelve el menor elemento
`maximo` | Función que recibe una lista y devuelve el mayor elemento
`define` | Delimita el inicio del bloque de definición de variables
`enddefine` | Delimita el fin del bloque de definición de variables
`program` | Delimita el inicio del bloque de la definición del programa
`end` | Delimita el fin del bloque de la definición del programa
`and` | Operador lógico (conjunción)
`or` | Operador lógico (disyunción)

### Operadores

Operador | Explicación
---|---
`:=` | Asignación
`+` | Suma
`-` | Resta
`*` | Multiplicación
`/` | División
`<>` | Distinto
`<` | Menor
`>` | Mayor
`<=` | Menor o igual
`>=` | Mayor o igual
`==` | Igual

### Otros

Token | Explicación | Ejemplo
---|---|---
`CTE_STRING` | Literal string | `"Esto es un string"`
`CTE_INT` | Literal entero | `1`
`CTE_FLOAT` | Literal decimal | `1.0` `.3` `4.`
`CONST_BIN` | Literal binario | `(1,2)` `(0,2)` `(11,2)`
`CTE_HEX` | Literal hexadecimal | `(0,16)` `(a,16)` `(A,16)`
`LS_ABRIR` | Indica el comienzo de una lista | `[`
`LS_CERRAR` | Indica el fin de una lista | `]`
`BL_ABRIR` | Indica el inicio de un bloque de código | `{`
`BL_CERRAR` | Indica el fin de un bloque de código | `}`
`PAREN_ABRIR` | Indica el comienzo de una expresión |  `(`
`PAREN_CERRAR` | Indica el fin de una expresión |  `)`
`COMA` | Separa elementos dentro de una lista | `,`
`DOSP`| Separa los tipos de los identificadores en la | `:`
`PYC` | Indica el final de una instrucción | `;`


### Ejemplo de programa

```
define
  str : string, str_vacio
  int : contador
  bin : bin_var
  hex : hex_var
enddefine

program
  string := "hola-?";
  bin_var := (10,2);
  hex_var := (af,16);
  str_vacio := "";

  contador := 0
  repeat {
    contador := contador + 1;
    if ((contador / 2) <> 2) {
      write(contador);
    } endif
  } until (contador == 5);

  write(maximo([1,5,8])); /* Devuelve 8 */
  write(minimo([1,5,8])); /* Devuelve 1 */
end
```
