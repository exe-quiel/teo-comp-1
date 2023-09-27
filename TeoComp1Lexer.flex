package ar.edu.unlu.teocomp1.grupo3;

import java_cup.runtime.Symbol;


%%


/*%cupsym Simbolo*/
%cup
%public
%class Lexico
%line
%column
%char


LETRA = [a-zA-Z]
DIGITO = [0-9]
ESPACIO = [ ]
TAB = \t
FORM_FEED = \f
ENTER = \r\n
CAR_ESP = [\-_!?] 
ID = {LETRA}({LETRA}|{DIGITO}|_)*
CONST_INT = {DIGITO}+
CONST_FLOAT = ({DIGITO}*\.{DIGITO}+)|({DIGITO}+\.{DIGITO}*)
COMILLA = \"
CONST_STRING = {COMILLA}({LETRA}|{DIGITO}|{ESPACIO}|{TAB}|{CAR_ESP})*{COMILLA}
BARRA = \/
COMMENT = {BARRA}\*({LETRA}|{DIGITO}|{ESPACIO}|{TAB}|{ENTER}|{CAR_ESP})*\*{BARRA}
CONST_BIN = \((0|1)+,2\)
CONST_HEX = \((DIGITO|[A-F]|[a-f])+,16\)

%%

<YYINITIAL> {

	{ESPACIO}		{System.out.println("Token ESPACIO encontrado, Lexema "+ yytext());}

	{ID}			{System.out.println("Token ID encontrado, Lexema "+ yytext());}

	{CONST_INT}		{System.out.println("Token CONST_INT, encontrado Lexema "+ yytext());}

	{CONST_FLOAT}	{System.out.println("Token CONST_FLOAT, encontrado Lexema "+ yytext());}

	{CONST_BIN}		{System.out.println("Token CONST_BIN, encontrado Lexema "+ yytext());}

	{CONST_HEX}		{System.out.println("Token CONST_HEX, encontrado Lexema "+ yytext());}

	{CONST_STRING}	{System.out.println("Token CONST_STRING, encontrado Lexema "+ yytext());}

	{COMMENT}		{System.out.println("Token COMMENT, encontrado Lexema "+ yytext());}

	":="			{System.out.println("Token ASSIGN encontrado, Lexema "+ yytext());}

	"+"				{System.out.println("Token OP_SUM encontrado, Lexema "+ yytext());}

	"-"				{System.out.println("Token OP_RESTA encontrado, Lexema "+ yytext());}

	"*"				{System.out.println("Token OP_MULT encontrado, Lexema "+ yytext());}

	"/"				{System.out.println("Token OP_DIV encontrado, Lexema "+ yytext());}

	"<>"			{System.out.println("Token OP_DIFF encontrado, Lexema "+ yytext());}

	"<"				{System.out.println("Token OP_LT encontrado, Lexema "+ yytext());}

	">"				{System.out.println("Token OP_GT encontrado, Lexema "+ yytext());}

	"<="			{System.out.println("Token OP_LE encontrado, Lexema "+ yytext());}

	">="			{System.out.println("Token OP_GE encontrado, Lexema "+ yytext());}

	"=="			{System.out.println("Token OP_EQ encontrado, Lexema "+ yytext());}

	"["				{System.out.println("Token LS_ABRIR encontrado, Lexema "+ yytext());}

	"]"				{System.out.println("Token LS_CERRAR encontrado, Lexema "+ yytext());}

	"{"				{System.out.println("Token BL_ABRIR encontrado, Lexema "+ yytext());}

	"}"				{System.out.println("Token BL_CERRAR encontrado, Lexema "+ yytext());}

	"("				{System.out.println("Token PR_ABRIR encontrado, Lexema "+ yytext());}

	")"				{System.out.println("Token PR_CERRAR encontrado, Lexema "+ yytext());}

	","				{System.out.println("Token COMA encontrado, Lexema "+ yytext());}

	":"				{System.out.println("Token DOSP encontrado, Lexema "+ yytext());}

	";"				{System.out.println("Token PYC encontrado, Lexema "+ yytext());}

	{TAB}			{System.out.println("Token TAB encontrado, Lexema "+ yytext());}

	{FORM_FEED}		{System.out.println("Token FORM_FEED encontrado, Lexema "+ yytext());}

	{ENTER}			{System.out.println("Token ENTER encontrado, Lexema "+ yytext());}
}

[^]					{ throw new Error("Caracter no permitido: <" + yytext() + "> en la linea [" + yyline + "] columna [" + yycolumn + "]"); }
