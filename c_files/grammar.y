%{
#include <stdio.h>
#include "functions.h"
	int yylex(void);

	void yyerror(const char* err)
	{
		fprintf(stderr, "Error: %s\n", err);
	}
	extern FILE* yyin;
	extern struct codemon_file codemon;
	extern int i;
	extern int j;
%}
%token TOK_ERR TOK_ADD TOK_SUB TOK_MUL TOK_DIV TOK_LEFT TOK_RIGHT TOK_MOD
%union{
	int num;
	char *str;
	char let;
}

%token<num> TOK_VAL
%token<str> TOK_BEGIN TOK_OPCODE
%token<let> TOK_COMMA TOK_SEMIC TOK_MODE
%type <num> expression
%left TOK_ADD TOK_SUB TOK_COMMA
%left TOK_MUL TOK_DIV TOK_MOD
%%

codemon: codemon instruction {codemon.total_commands = i;}
	|	{codemon.total_commands = i;}
	;
instruction: TOK_OPCODE field TOK_COMMA field TOK_SEMIC	{j=0; strcpy(codemon.com_array[i].op_code, $1); free($1); i++;}
	| TOK_OPCODE field TOK_SEMIC	{strcpy(codemon.com_array[i].op_code, $1); free($1); j=0; codemon.com_array[i].b_mode = '#'; codemon.com_array[i].b = 0; i++;}
	| TOK_BEGIN TOK_VAL TOK_SEMIC	{codemon.begin_val = $2;}
	| TOK_OPCODE TOK_SEMIC			{strcpy(codemon.com_array[i].op_code, $1); free($1); j=0; codemon.com_array[i].a = 0; codemon.com_array[i].a_mode = '#'; codemon.com_array[i].b = 0; codemon.com_array[i].b_mode = '#'; i++;}
	;
field: TOK_MODE expression	{if(j==0){codemon.com_array[i].a_mode = $1; codemon.com_array[i].a = $2;} else {codemon.com_array[i].b_mode = $1; codemon.com_array[i].b = $2;}; j=1;}
	| expression	{if(j==0){codemon.com_array[i].a_mode = '$'; codemon.com_array[i].a = $1;} else {codemon.com_array[i].b_mode = '$'; codemon.com_array[i].b = $1;}; j=1;}
	;
expression: TOK_SUB TOK_VAL				{$$ = -$2;}
	| expression TOK_ADD expression	{$$ = $1 + $3;}
	| expression TOK_SUB expression	{$$ = $1 - $3;}
	| expression TOK_MUL expression	{$$ = $1 * $3;}
	| expression TOK_DIV expression	{if($3 != 0){$$ = $1 / $3;} else{yyerror("Division by zero!"); YYABORT;};}
	| expression TOK_MOD expression	{if($3 != 0){$$ = $1 % $3;} else{yyerror("Division by zero!"); YYABORT;};}
	| TOK_LEFT expression TOK_RIGHT	{$$ = $2;}
	| TOK_VAL						{$$ = $1;}
	| err							{yyerror("Undeclared Label!"); YYABORT;}
	;
err: TOK_ERR
%%