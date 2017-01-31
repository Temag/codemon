#include "functions.h"


struct Codemon_pkg * createPkg(FILE * input, char * name)
{
    uint32 begin;
    uint64 command;
    int lines = 0;
    struct Codemon_pkg * new_pkg = malloc(sizeof(struct Codemon_pkg));
    strcpy(new_pkg->name, name);

    /*Reads the begin value (first 32 bits)*/
    fread(&begin,sizeof(uint32),1,input);
    new_pkg->begin = begin;

    while((fread(&command,sizeof(uint64),1,input)) == 1)
    {
        new_pkg->program[lines] = command;
        lines++;
    }
    new_pkg->lines = lines;
    return new_pkg;
}

char * getName(const char * file_name)
{
    char * name;
    char buffer[19];
    int i = 0;
    while(file_name[i] != '.')
    {
        buffer[i] = file_name[i];
        i++;
    }

    buffer[i] = '\0';

    name = malloc((sizeof(char)*strlen(buffer)) + 1);
    strcpy(name, buffer);

    return name;
}