all: javac lex yacc gcc path

javac:
	javac codemon/Codemon.java codemon/Fight.java codemon/Panel.java codemon/Trainer.java codemon/Visualize.java
lex:
	lex c_files/analyzer.l
yacc:
	yacc -d c_files/grammar.y

javah:
	javah -jni codemon/Codemon
gcc: codemon
	gcc -dynamiclib -o codemon/libcodemon.jnilib -I/Library/Java/JavaVirtualMachines/jdk1.8.0_60.jdk/Contents/Home/include -I/Library/Java/JavaVirtualMachines/jdk1.8.0_60.jdk/Contents/Home/include/darwin c_files/jnitest.c c_files/parse.c c_files/pkg.c c_files/table.c c_files/client.c c_files/lex.yy.c c_files/y.tab.c -ll -framework JavaVM
path:
	export LD_LIBRARY_PATH=$${LD_LIBRARY_PATH}:.
run:
	java Codemon
clean:
	rm *.o