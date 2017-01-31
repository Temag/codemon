#include "functions.h"
int findCode(char * op_code)
{
    int value;
    t_val table[] = {
        {.op_code = "DAT", .b_val = 0x00}, {.op_code = "MOV", .b_val = 0x01}, 
        {.op_code = "ADD", .b_val = 0x02}, {.op_code = "SUB", .b_val = 0x03}, 
        {.op_code = "MUL", .b_val = 0x04}, {.op_code = "DIV", .b_val = 0x05}, 
        {.op_code = "MOD", .b_val = 0x06}, {.op_code = "JMP", .b_val = 0x07}, 
        {.op_code = "JMZ", .b_val = 0x08}, {.op_code = "JMN", .b_val = 0x09}, 
        {.op_code = "DJN", .b_val = 0x0A}, {.op_code = "SEQ", .b_val = 0x0B}, 
        {.op_code = "SNE", .b_val = 0x0C}, {.op_code = "SLT", .b_val = 0x0D}, 
        {.op_code = "SET", .b_val = 0x0E}, {.op_code = "CLR", .b_val = 0x0F}, 
        {.op_code = "FRK", .b_val = 0x10}, {.op_code = "NOP", .b_val = 0x11},
        {.op_code = "RND", .b_val = 0x12}
    };

    

    for(int i=0; i<18; i++)
    {
        if(strcasecmp(table[i].op_code, op_code) == 0)
        {
            return table[i].b_val;
        }
    }
    return -1;
}
int findMode(char mode)
{
    int value;
    struct mode m_table[] = {
            {.mode = '$', .b_val = 0x00}, {.mode = '#', .b_val = 0x01}, 
            {.mode = '[', .b_val = 0x02}, {.mode = ']', .b_val = 0x03}, 
            {.mode = '*', .b_val = 0x04}, {.mode = '@', .b_val = 0x05}, 
            {.mode = '{', .b_val = 0x06}, {.mode = '}', .b_val = 0x07}, 
            {.mode = '<', .b_val = 0x08}, {.mode = '>', .b_val = 0x09}, 
        };
    for(int i=0; i<18; i++)
    {
        if(m_table[i].mode == mode)
        {
            return m_table[i].b_val;
        }
    }
    return -1;
}

/*Com * create_command(char * op_code, char a_mode, int a, char b_mode, int b, int com_num)
{
    Com * new_command = malloc(sizeof(Com));
    new_command->op_code = op_code;
    new_command->a_mode = a_mode;
    new_command->a = a;
    new_command->b_mode = b_mode;
    new_command->b = b;
    new_command->com_num = com_num;

    return new_command;
}*/