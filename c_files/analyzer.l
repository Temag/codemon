%{
	#include <stdio.h>
	#include "functions.h"
	int resolveLabel(char * yytext, int l);
	int label_count = 0;
	int line = 0;
	static char array[50][10];
	static int carray[50];
	int start = 0;
	static int flag = 0;
	int i=0;
	int j=0;

%}

%x label comment
%option caseless
%option noyywrap
whitespace	[ \n\t]
labels		[a-zA-Z][a-zA-Z_]*[0-9]*:
flabel		[a-zA-Z][a-zA-Z_]*[0-9]*
opcode		(DAT|MOV|ADD|SUB|MUL|DIV|MOD|JMP|JMZ|JMN|DJN|SEQ|SNE|SLT|SET|CLR|FRK|NOP|RND)
modes		[$#\[\]@{}<>]?
value		[0-9]+
%%
	int comment_caller = INITIAL;
	if(start == 0)
	{
		comment_caller = label;
		BEGIN(label);
	}

<label>{labels}	{strcpy(array[label_count], yytext); carray[label_count] = line; label_count++;}
<label><<EOF>>	{line = 0; fseek(yyin, 0, SEEK_SET); comment_caller = INITIAL; start = 1; BEGIN(INITIAL);}
<label>!		BEGIN(comment);
<label>;		{line++;}		
<label>\n		;
<label>.		;
<comment>\n 	{BEGIN(comment_caller);};
<comment><<EOF>>	{if(comment_caller == label){line = 0; fseek(yyin, 0, SEEK_SET); comment_caller = INITIAL; start = 1; BEGIN(INITIAL);} else{yyterminate();}};
<comment>.		;

begin@	{return TOK_BEGIN;}
{value}	{yylval.num = atoi(yytext); flag = 0; return TOK_VAL;}
{labels}	;
{opcode}	{yylval.str = strdup(yytext); flag = 1; return TOK_OPCODE;}
{modes}		{yylval.let = yytext[0]; flag = 0; return TOK_MODE;}
\( 			{return TOK_LEFT;}
\)			{return TOK_RIGHT;}
\+			{return TOK_ADD;}
\-			{flag = 0; return TOK_SUB;}
\*			{if(flag ==0){return TOK_MUL;} else { yylval.let = yytext[0]; flag =0; return TOK_MODE;};}
\/			{return TOK_DIV;}
%			{return TOK_MOD;}
{flabel}	{if((yylval.num = resolveLabel(yytext, line)) < 8193){return TOK_VAL;} else{return TOK_ERR;};}
,			{flag = 1; return TOK_COMMA;}
;			{line++; return TOK_SEMIC;}
!			BEGIN(comment);
{whitespace} 	;
<<EOF>>		{yyterminate();}
.				{printf("Error: Parse Error!\n"); exit(0);}



%%

int resolveLabel(char * text, int l)
{
	int i, size, new_val;
	size = strlen(text);
	for(i=0; i<label_count; i++)
	{
		if(strncmp(array[i], text, size) == 0)
		{
			new_val = carray[i]-l;
			return new_val;
		}
	}
	//fprintf(stderr, "Error: Label has not been declared!\n");
	return 8193;
}