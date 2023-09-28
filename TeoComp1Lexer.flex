package ar.edu.unlu.teocomp1.grupo3;

import java.util.ArrayList;
import java.util.List;

import java_cup.runtime.Symbol;


%%

%{
	private List<Resultado> resultados = new ArrayList<>();

	private void agregarToken(String lexema, String token, int linea, int columna, long inicio, int largo) {
		resultados.add(new Resultado(lexema, token, linea, columna, inicio, largo));
	}

	private void agregarError(String lexema, int linea, int columna, long inicio, int largo) {
		resultados.add(new Resultado(lexema, linea, columna, inicio, largo));
	}

	public List<Resultado> getResultados() {
		return resultados;
	}
%}

/*%cupsym Simbolo*/
%cup
%public
%class Lexico
%line
%column
%char
%eofval{
	return null;
%eofval}


LETRA = [a-zA-Z]
DIGITO = [0-9]
ESPACIO = [ ]
TAB = \t
FORM_FEED = \f
/*ENTER = \r\n*/
ENTER = \n
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

	{ESPACIO}		{agregarToken(yytext(), "ESPACIO", yyline, yycolumn, yychar, yylength());}

	{ID}			{agregarToken(yytext(), "ID", yyline, yycolumn, yychar, yylength());}

	{CONST_STRING}	{agregarToken(yytext(), "CONST_STRING", yyline, yycolumn, yychar, yylength());}

	{CONST_INT}		{agregarToken(yytext(), "CONST_INT", yyline, yycolumn, yychar, yylength());}

	{CONST_FLOAT}	{agregarToken(yytext(), "CONST_FLOAT", yyline, yycolumn, yychar, yylength());}

	{CONST_BIN}		{agregarToken(yytext(), "CONST_BIN", yyline, yycolumn, yychar, yylength());}

	{CONST_HEX}		{agregarToken(yytext(), "CONST_HEX", yyline, yycolumn, yychar, yylength());}

	{COMMENT}		{agregarToken(yytext(), "COMMENT", yyline, yycolumn, yychar, yylength());}

	":="			{agregarToken(yytext(), "ASSIGN", yyline, yycolumn, yychar, yylength());}

	"+"				{agregarToken(yytext(), "OP_SUM", yyline, yycolumn, yychar, yylength());}

	"-"				{agregarToken(yytext(), "OP_RESTA", yyline, yycolumn, yychar, yylength());}

	"*"				{agregarToken(yytext(), "OP_MULT", yyline, yycolumn, yychar, yylength());}

	"/"				{agregarToken(yytext(), "OP_DIV", yyline, yycolumn, yychar, yylength());}

	"<>"			{agregarToken(yytext(), "OP_DIFF", yyline, yycolumn, yychar, yylength());}

	"<"				{agregarToken(yytext(), "OP_LT", yyline, yycolumn, yychar, yylength());}

	">"				{agregarToken(yytext(), "OP_GT", yyline, yycolumn, yychar, yylength());}

	"<="			{agregarToken(yytext(), "OP_LE", yyline, yycolumn, yychar, yylength());}

	">="			{agregarToken(yytext(), "OP_GE", yyline, yycolumn, yychar, yylength());}

	"=="			{agregarToken(yytext(), "OP_EQ", yyline, yycolumn, yychar, yylength());}

	"["				{agregarToken(yytext(), "LS_ABRIR", yyline, yycolumn, yychar, yylength());}

	"]"				{agregarToken(yytext(), "LS_CERRAR", yyline, yycolumn, yychar, yylength());}

	"{"				{agregarToken(yytext(), "BL_ABRIR", yyline, yycolumn, yychar, yylength());}

	"}"				{agregarToken(yytext(), "BL_CERRAR", yyline, yycolumn, yychar, yylength());}

	"("				{agregarToken(yytext(), "PAREN_ABRIR", yyline, yycolumn, yychar, yylength());}

	")"				{agregarToken(yytext(), "PAREN_CERRAR", yyline, yycolumn, yychar, yylength());}

	","				{agregarToken(yytext(), "COMA", yyline, yycolumn, yychar, yylength());}

	":"				{agregarToken(yytext(), "DOSP", yyline, yycolumn, yychar, yylength());}

	";"				{agregarToken(yytext(), "PYC", yyline, yycolumn, yychar, yylength());}

	{TAB}			{agregarToken(yytext(), "TAB", yyline, yycolumn, yychar, yylength());}

	{FORM_FEED}		{agregarToken(yytext(), "FORM_FEED", yyline, yycolumn, yychar, yylength());}

	{ENTER}			{agregarToken(/*yytext()*/ "", "ENTER", yyline, yycolumn, yychar, yylength());}
}

[^]					{ System.err.println("error: " + yytext()); agregarError(yytext(), yyline, yycolumn, yychar, yylength()); }
