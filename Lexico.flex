package ar.edu.unlu.teocomp1.grupo3;

import java_cup.runtime.*;
import java.util.List;
import java.util.ArrayList;

%%

%{
    private List<Token> tokens = new ArrayList<>();
    
    private void agregarToken(String token){
        tokens.add(new Token(token, this.yytext(), this.yyline, this.yycolumn, this.yychar, this.yylength()));
    }

    /*private void agregarError(){
        tokens.add(new Token(this.yytext(), this.yyline, this.yycolumn, this.yychar, this.yylength()));
    } */

    public List<Token> getTokens() {
        return tokens;
    }

%}

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
/*ENTER = \r\n */
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

    "if"              {agregarToken("PR_IF"); return new Symbol(sym.PR_IF, this.yytext());}

    "else"              {agregarToken("PR_ELSE"); return new Symbol(sym.PR_ELSE, this.yytext());}

    "endif"           {agregarToken("PR_ENDIF"); return new Symbol(sym.PR_ENDIF, this.yytext());}

    "repeat"          {agregarToken("PR_REPEAT"); return new Symbol(sym.PR_REPEAT, this.yytext());}

    "until"           {agregarToken("PR_UNTIL"); return new Symbol(sym.PR_UNTIL, this.yytext());}

    "write"           {agregarToken("PR_WRITE"); return new Symbol(sym.PR_WRITE, this.yytext());}

    "minimo"          {agregarToken("PR_MINIMO"); return new Symbol(sym.PR_MINIMO, this.yytext());}

    "maximo"          {agregarToken("PR_MAXIMO"); return new Symbol(sym.PR_MAXIMO, this.yytext());}

    "define"          {agregarToken("PR_DEFINE"); return new Symbol(sym.PR_DEFINE, this.yytext());}

    "enddefine"       {agregarToken("PR_ENDDEFINE"); return new Symbol(sym.PR_ENDDEFINE, this.yytext());}

    "program"         {agregarToken("PR_PROGRAM"); return new Symbol(sym.PR_PROGRAM, this.yytext());}

    "end"             {agregarToken("PR_END"); return new Symbol(sym.PR_END, this.yytext());}

    "and"             {agregarToken("PR_AND"); return new Symbol(sym.PR_AND, this.yytext());}

    "or"              {agregarToken("PR_OR"); return new Symbol(sym.PR_OR, this.yytext());}
    
    "int"             {agregarToken("PR_INT"); return new Symbol(sym.PR_INT, this.yytext());}
    
    "str"             {agregarToken("PR_STR"); return new Symbol(sym.PR_STR, this.yytext());}
    
    "float"           {agregarToken("PR_FLOAT"); return new Symbol(sym.PR_FLOAT, this.yytext());}

    {ESPACIO}         {/*System.out.println("espacio");*/}

    {ID}              {agregarToken("ID"); return new Symbol(sym.ID, this.yytext());}

    {CONST_STRING}    {
                       if (this.yylength() <= 32) {
                           agregarToken("CONST_STRING");
                           return new Symbol(sym.CONST_STRING, this.yytext());
                       } else {
                           System.err.printf ("ERROR: Se encontro string con %s caracteres \n", this.yylength()-2);
                       }
                      }

    {CONST_INT}       {
                       if (Integer.parseInt(this.yytext()) < 65536) {
                           agregarToken("CONST_INT");
                           return new Symbol(sym.CONST_INT, this.yytext());
                       } else {
                           System.err.printf ("ERROR: Se encontro int mayor a 16 bits: %s \n", this.yytext());
                       }
                      }

    {CONST_FLOAT}     {
                       try {
                           Float.parseFloat(this.yytext());
                           agregarToken("CONST_FLOAT");
                           return new Symbol(sym.CONST_FLOAT, this.yytext());
                       } catch (NumberFormatException e){ 
                           System.err.printf ("ERROR: Se encontro float invalido: %s \n", this.yytext());
                       }
                      }

    {CONST_BIN}       {agregarToken("CONST_BIN"); return new Symbol(sym.CONST_BIN, this.yytext());}

    {CONST_HEX}       {agregarToken("CONST_HEX"); return new Symbol(sym.CONST_HEX, this.yytext());}

    {COMMENT}         {/*System.out.println("COMMENT");*/}

    ":="              {agregarToken("ASSIGN"); return new Symbol(sym.ASSIGN, this.yytext());}

    "+"               {agregarToken("OP_SUM"); return new Symbol(sym.OP_SUM, this.yytext());}

    "-"               {agregarToken("OP_RESTA"); return new Symbol(sym.OP_RESTA, this.yytext());}

    "*"               {agregarToken("OP_MULT"); return new Symbol(sym.OP_MULT, this.yytext());}

    "/"               {agregarToken("OP_DIV"); return new Symbol(sym.OP_DIV, this.yytext());}

    "<>"              {agregarToken("OP_DIFF"); return new Symbol(sym.OP_DIFF, this.yytext());}

    "<"               {agregarToken("OP_LT"); return new Symbol(sym.OP_LT, this.yytext());}

    ">"               {agregarToken("OP_GT"); return new Symbol(sym.OP_GT, this.yytext());}

    "<="              {agregarToken("OP_LE"); return new Symbol(sym.OP_LE, this.yytext());}

    ">="              {agregarToken("OP_GE"); return new Symbol(sym.OP_GE, this.yytext());}

    "=="              {agregarToken("OP_EQ"); return new Symbol(sym.OP_EQ, this.yytext());}

    "["               {agregarToken("LS_ABRIR"); return new Symbol(sym.LS_ABRIR, this.yytext());}

    "]"               {agregarToken("LS_CERRAR"); return new Symbol(sym.LS_CERRAR, this.yytext());}

    "{"               {agregarToken("BL_ABRIR"); return new Symbol(sym.BL_ABRIR, this.yytext());}

    "}"               {agregarToken("BL_CERRAR"); return new Symbol(sym.BL_CERRAR, this.yytext());}

    "("               {agregarToken("PAREN_ABRIR"); return new Symbol(sym.PAREN_ABRIR, this.yytext());}

    ")"               {agregarToken("PAREN_CERRAR"); return new Symbol(sym.PAREN_CERRAR, this.yytext());}

    ","               {agregarToken("COMA"); return new Symbol(sym.COMA, this.yytext());}

    ":"               {agregarToken("DOSP"); return new Symbol(sym.DOSP, this.yytext());}

    ";"               {agregarToken("PYC"); return new Symbol(sym.PYC, this.yytext());}

    {TAB}             {/*System.out.println("tab");*/}

    {FORM_FEED}       {/*System.out.println("FORM_FEED");*/}

    {ENTER}           {/*System.out.println("ENTER");*/}
}

[^]                   {System.err.printf("ERROR: Lexema: %s | linea %s | Columna %s\n", this.yytext(), this.yyline, this.yycolumn);}