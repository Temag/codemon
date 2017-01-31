#include "functions.h"

/********************************************************************/
int isElementOf(char character)
{
    char modes[] = {'$', '#', '[', ']', '*', '@', '{', '}', '<', '>'};

    for(int i=0; i<strlen(modes); i++)
    {
        if(character == modes[i])
            return i;
    }
    return -1;
}

/********************************************************************/

void convert(struct codemon_file codemon, FILE * output)
{
    uint64 opcode;
    uint64 a_mode;
    uint64 a;
    uint64 b_mode;
    uint64 b;
    uint64 instruction;
    uint32 begin=0;
    uint64 aval;
    uint64 bval;

    //printf("begin: %d\n a: %c\n b: %c\n", codemon.begin_val, codemon.com_array[0].a_mode, codemon.com_array[0].b_mode);
    /*Bit Shifts:
    58 for op_code
    54 for a mode
    25 for b mode
    29 for a value*/
    begin = begin | codemon.begin_val;
    fwrite(&begin, sizeof(uint32), 1, output);
    for(int i=0; i<codemon.total_commands; i++)
    {
        aval = ifNeg(codemon.com_array[i].a);
        bval = ifNeg(codemon.com_array[i].b);
        //printf("a: %d\na_mode: %c\nb: %d\nb_mode: %c\n", codemon.com_array[i].a, codemon.com_array[i].a_mode, codemon.com_array[i].b, codemon.com_array[i].b_mode);
        opcode=0;
        a_mode=0;
        a=0;
        b_mode=0;
        b=0;
        instruction=0;
        begin=0;
        opcode = (opcode | findCode(codemon.com_array[i].op_code))<<58;
        a_mode = (a_mode | findMode(codemon.com_array[i].a_mode))<<54;
        b_mode = (b_mode | findMode(codemon.com_array[i].b_mode))<<25;
        a = (a | aval<<29);
        b = b | bval;
        instruction = opcode | a_mode | b_mode | a | b;
        fwrite(&instruction, sizeof(uint64),1, output);
    }
}

/********************************************************************/

int ifNeg(int val)
{
    while(val < 0)
    {
        val = val + 8192;
    }
    if(val > 8192)
    {
        val = val % 8192;
    }
    return val;
}

/********************************************************************/















