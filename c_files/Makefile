codemon: y.tab.o lex.yy.o test.o table.o parse.o pkg.o client.o
	gcc -Wall table.o test.o parse.o pkg.o client.o lex.yy.o y.tab.o -ll -o codemon 
test.o: test.c functions.h
	gcc -c test.c
table.o: table.c functions.h
	gcc -c table.c 
parse.o: parse.c functions.h
	gcc -c parse.c 
pkg.o: pkg.c functions.h
	gcc -c pkg.c 
client.o: client.c common.h arch.h
	gcc -c client.c
y.tab.o: y.tab.c
	gcc -c y.tab.c
y.tab.c: intr1.y
	yacc -d intr1.y
lex.yy.o: lex.yy.c
	gcc -c lex.yy.c
lex.yy.c:state2.l
	lex state2.l
clean:
	rm *.o
	rm codemon
