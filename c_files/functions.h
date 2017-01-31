#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>
#include "arch.h"
#include "common.h"
#include "y.tab.h"
typedef struct Command{
    char op_code[4];
    char a_mode;
    int a;
    char b_mode;
    int b;
    int com_num;
    char a_label[10];
    char b_label[10];
    int label_toggle;
}com;

typedef struct table_value{
    char op_code[4];
    int b_val;
}t_val;

struct mode{
    char mode;
    int b_val;
};

struct label{
    char label[10];
    int command_num;
};

struct codemon_file{
    int begin_val;
    char begin_label[10];
    struct label label_array[50];
    com com_array[50];
    int total_commands;
    int total_labels;
};

/*
findCode:
Parameters: Char * containing an op_code
Purpose: Searches a structure containing all possible
op_codes and their respective hex values, returns hex value.
Returns: integer containing the hex value of the op_code
*/
int findCode(char * op_code);

/*
fileParse:
Parameteres: FILE * containing all the information to be parsed
Purpose: Parse file for specific values and saves them all into a
codemon_file
Returns: the created codemon_file
*/
struct codemon_file fileParse(FILE * input);

/*
isElementOf:
Parameters: Char containing a value parsed from the file
Purpose: Determines whether the character is an adressing mode
Returns: -1 if it is not an adressing mode >=0 otherwise
*/
int isElementOf(char character);

/*
createPkg:
Parameteres: .codemon file and the name of the codemon
Purpose: Bundle all necessary information aboutt he codemon into a single structure
Returns: the strcuture containing all necesary information to submit to the codex
*/
struct Codemon_pkg * createPkg(FILE * input, char * name);

/*
getName:
Parameters: char * containing the name of a file
Purpose: removes the .codemon to obtain the name of the codemon
Returns: char * containing the name of the codemon
*/
char * getName(const char * file_name);

/*
convert:
Parameters: takes all information gained about a codemon from parsing the file
Purpose: converts the character representation of the commands to a binary one
for submission to the Codex, must redirect to a file type .codemon
Returns: nothing, prints to stdout
*/
void convert(struct codemon_file, FILE * output);

/*
findMode:
Parameters: char containing an adressing mode
Purpose: retrieve hex value of specific adressing mode
Returns: int containing hex value of adressing mode
*/
int findMode(char mode);

/*
resolveLabels:
Parameters: codemon_file containing all necessary information of the codemon
Purpose: converts any labels to their true value (the difference in command number)
Returns: codemon_file with all labels resolved to their proper values
*/
struct codemon_file resolveLabels(struct codemon_file codemon);


/*
begin:
Parses for the begin statement and returns the value
Arguments: FILE *, char *, struct codemon_file
Returns: struct codemon_file
*/
struct codemon_file begin(FILE * input, char *character, struct codemon_file codemon);

/*
whiteSpace:
Parses the file eating any and all white space characters as well as parsing to
end of line if it encounters a comment (!)
Arguments: char, FILE *
Returns: char
*/
int whiteSpace(char character, FILE * input);

/*
ifANeg:
If a "-"" is encountered while parsing the "a" field, this function will
store it and naturalize it
end of line if it encounters a comment (!)
Arguments: char *, struct codemon_file, FILE *, int, int
Returns: struct codemon_file
*/
int ifNeg(int val);

/*
ifBNeg:
If a "-"" is encountered while parsing the "b" field, this function will
store it and naturalize it
end of line if it encounters a comment (!)
Arguments: char *, struct codemon_file, FILE *, int, int
Returns: struct codemon_file
*/
struct codemon_file ifBNeg(int val);

int parse(const char * fpath, FILE * output);

FILE * _commonConnect (Request, uint32);
int32  _sendCodemon   (struct Codemon_pkg *, FILE *);
int32  _recvPacket    (Packet, FILE *);
int32  _recvReport    (FILE *, FILE *);
uint32 _recvID        (FILE *);
