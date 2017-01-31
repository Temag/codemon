#include "functions.h"
extern int yyparse(void);
struct codemon_file codemon;
extern FILE * yyin;
int main(int argc, char * argv[])
{
    int l;
    FILE * input;
    FILE * input2;
    FILE * output;
    char * name, *name1;
    uint32 ID=0;
    uint32 limit; 
    uint32 N;
    struct Codemon_pkg * pkg ;
    struct Codemon_pkg * pkg2;
    output = fopen("8.codemon", "w");

    switch(argv[1][1])
    {
        case 'c':
            yyin = fopen(argv[2], "r");
            //codemon = fileParse(input);
            yyparse();
            for(l=0; l<codemon.total_commands; l++)
            {
                printf("%s %c%d %c%d\n", codemon.com_array[l].op_code, codemon.com_array[l].a_mode, codemon.com_array[l].a, codemon.com_array[l].b_mode, codemon.com_array[l].b);
            }
            //codemon = resolveLabels(codemon);
            convert(codemon, output);
            fclose(yyin);
            break;

        case 't':
            input = fopen(argv[2], "rb");
            name = getName(argv[2]);
            limit = atoi(argv[3]);
            pkg = createPkg(input, name);

            ID = runTest(pkg, NULL, limit);
            printf("ID: %d\n", ID);
            free(pkg);
            free(name);
            break;

        case 's':
        	name = getName(argv[2]);
            input = fopen(argv[2], "r");
            name1 = getName(argv[3]);
            input2 = fopen(argv[3], "r");
            
            limit = atoi(argv[4]);
            pkg = createPkg(input, name);
            pkg2 = createPkg(input2, name1);

            ID = runTest(pkg, pkg2, limit);
            printf("ID: %d\n", ID);
            free(pkg);
            free(pkg2);
            free(name);
            free(name1);
            break;

        case 'p':
            N = atoi(argv[2]);
            input = fopen(argv[3], "r");
            name = getName(argv[3]);
            pkg = createPkg(input, name);
            switch(N)
            {
                case 2:
                    ID = runPvP(pkg, 2);
                    break;
                case 3:
                    ID = runPvP(pkg, 3);
                    break;
                case 4:
                    ID = runPvP(pkg, 4);
                    break;
            }
            printf("ID: %d\n", ID);
            free(pkg);
            free(name);
            break;

        case 'r':
            ID = atoi(argv[2]);
            N = getReport(ID, stdout);
            break;
    }
    
}