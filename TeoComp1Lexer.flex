package ar.edu.unlu.teocomp1.grupo3;

import java_cup.runtime.Symbol;
import java.util.List;
import java.util.ArrayList;

%%

%{
	private List<Resultado>	resultados = new ArrayList<>();
	
	private void agregarToken(String token){
		resultados.add(new Resultado(token, this.yytext(), this.yyline, this.yycolumn, this.yychar, this.yylength()));
	}
	
	private void agregarError(){
		resultados.add(new Resultado(this.yytext(), this.yyline, this.yycolumn, this.yychar, this.yylength()));
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
ENTER = \r\n
/*ENTER = \n */
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

	{ESPACIO}		{agregarToken("ESPACIO");}

	{ID}			{agregarToken("ID");}

	{CONST_STRING}	{agregarToken("CONST_STRING");}

	{CONST_INT}		{agregarToken("CONST_INT");}

	{CONST_FLOAT}	{agregarToken("CONST_FLOAT");}

	{CONST_BIN}		{agregarToken("CONST_BIN");}

	{CONST_HEX}		{agregarToken("CONST_HEX");}

	{COMMENT}		{agregarToken("COMMENT");}

	":="			{agregarToken("ASSIGN");}

	"+"				{agregarToken("OP_SUM");}

	"-"				{agregarToken("OP_RESTA");}

	"*"				{agregarToken("OP_MULT");}

	"/"				{agregarToken("OP_DIV");}

	"<>"			{agregarToken("OP_DIFF");}

	"<"				{agregarToken("OP_LT");}

	">"				{agregarToken("OP_GT");}

	"<="			{agregarToken("OP_LE");}

	">="			{agregarToken("OP_GE");}

	"=="			{agregarToken("OP_EQ");}

	"["				{agregarToken("LS_ABRIR");}

	"]"				{agregarToken("LS_CERRAR");}

	"{"				{agregarToken("BL_ABRIR");}

	"}"				{agregarToken("BL_CERRAR");}

	"("				{agregarToken("PAREN_ABRIR");}

	")"				{agregarToken("PAREN_CERRAR");}

	","				{agregarToken("COMA");}

	":"				{agregarToken("DOSP");}

	";"				{agregarToken("PYC");}

	{TAB}			{agregarToken("TAB");}

	{FORM_FEED}		{agregarToken("FORM_FEED");}

	{ENTER}			{agregarToken("ENTER");}
}

[^]					{agregarError(); }