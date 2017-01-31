#include "functions.h"
#include "Codemon.h"
extern int yyparse(void);
extern void yyrestart(FILE *);
struct codemon_file codemon;
extern FILE * yyin;
extern int i, line, label_count, start;
JNIEXPORT void JNICALL Java_Codemon_parse
  (JNIEnv *env, jobject obj, jstring file_name, jstring file_path, jstring c_path)
  {
    const char * file = (*env)->GetStringUTFChars(env, file_name, 0);
    const char * fpath = (*env)->GetStringUTFChars(env, file_path, 0);
    const char * cpath = (*env)->GetStringUTFChars(env, c_path, 0);
	int err, j;
    FILE * output;
	
	codemon.begin_val=-1;
	
    output = fopen(cpath, "w");
    if(output == NULL)
    {
        //return error code
        exit(0);
    }
	err = parse(fpath, output);
	
	//yyparse();
	if(err == 0)
	{
		printf("begin@%d\n", codemon.begin_val);
		for(j=0; j<codemon.total_commands; j++)
		{
			printf("%s %c%d, %c%d;\n",codemon.com_array[j].op_code, codemon.com_array[j].a_mode, codemon.com_array[j].a, codemon.com_array[j].b_mode, codemon.com_array[j].b);
		}
		printf("\n");
    	convert(codemon, output);
    }
    (*env)->ReleaseStringUTFChars(env, file_name, file);
    (*env)->ReleaseStringUTFChars(env, file_path, fpath);
    (*env)->ReleaseStringUTFChars(env, c_path, cpath);
    fclose(output);
  }
  
  int parse(const char * fpath, FILE * output)
  {
  	int err;
  	yyin = fopen(fpath, "r");
  	if(yyin == NULL)
  	{
  		printf("Could not open file\n");
  		exit(0);
  	}
  	yyrestart(yyin);
  	err = yyparse();
  	if(codemon.begin_val < 0)
  	{
  		err = 1;
  		printf("Error: Improper or missing begin statement!\n");
  	}
  	line = 0;
  	label_count = 0;
  	start = 0;
  	i=0;
  	fclose(yyin);
  	return err;
  }

  JNIEXPORT jint JNICALL Java_Codemon_runTest
  (JNIEnv *env, jobject obj, jstring file_name, jstring file_path, jstring r_path, jint iterations)
  {
    const char * file = (*env)->GetStringUTFChars(env, file_name, 0);
    const char * fpath = (*env)->GetStringUTFChars(env, file_path, 0);
    const char * rpath = (*env)->GetStringUTFChars(env, r_path, 0);
    //printf("file: %s\n fpath: %s\n rpath: %s\n", file, fpath, rpath);

    uint32 limit = (uint32) iterations;
    FILE * input, * output;
    char * name;
    uint32 ID=0, N;
    struct Codemon_pkg * pkg;

    input = fopen(fpath, "r");
    if(input == NULL)
    {
        //return error code
        exit(0);
    }
    name = getName(file);
    pkg = createPkg(input, name);

    ID = runTest(pkg, NULL, limit);

    printf("rpath: %s\n", rpath);

    printf("ID: %d\n", ID);
    (*env)->ReleaseStringUTFChars(env, file_name, file);
    (*env)->ReleaseStringUTFChars(env, file_path, fpath);
    (*env)->ReleaseStringUTFChars(env, r_path, rpath);
    free(pkg);
    free(name);
    fclose(input);
    return ID;

  }

  JNIEXPORT int JNICALL Java_Codemon_runSelf
  (JNIEnv *env, jobject obj, jstring file1_name, jstring file2_name, jstring file_path, jstring file2_path, jstring r_path, jint iterations)
  {
    const char * file = (*env)->GetStringUTFChars(env, file1_name, 0);
    const char * file2 = (*env)->GetStringUTFChars(env, file2_name, 0);
    const char * fpath = (*env)->GetStringUTFChars(env, file_path, 0);
    const char * f2path = (*env)->GetStringUTFChars(env, file2_path, 0);
    const char * rpath = (*env)->GetStringUTFChars(env, r_path, 0);
    //printf("file: %s\n file2: %s\n fpath: %s\n f2path: %s\n rpath: %s\n", file, file2, fpath, f2path, rpath);
    
    uint32 limit = (uint32) iterations;
    FILE * input, * input2;
    char * name, * name2;
    uint32 ID=0, N;
    struct Codemon_pkg * pkg;
    struct Codemon_pkg * pkg2;

    input = fopen(fpath, "r");
    name = getName(file);
    input2 = fopen(f2path, "r");
    name2 = getName(file2);

    pkg = createPkg(input, name);
    pkg2 = createPkg(input2, name2);

    ID = runTest(pkg, pkg2, limit);

    printf("ID: %d\n", ID);
    free(pkg);
    free(pkg2);
    free(name);
    free(name2);
    (*env)->ReleaseStringUTFChars(env, file1_name, file);
    (*env)->ReleaseStringUTFChars(env, file2_name, file2);
    (*env)->ReleaseStringUTFChars(env, file_path, fpath);
    (*env)->ReleaseStringUTFChars(env, file2_path, f2path);
    (*env)->ReleaseStringUTFChars(env, r_path, rpath);
    fclose(input);
    fclose(input2);
    return ID;
  }

  JNIEXPORT void JNICALL Java_Codemon_runPVP
  (JNIEnv *env, jobject obj, jstring file_name, jstring file_path, jstring r_path, jint iterations, jint vs)
  {
    const char * file = (*env)->GetStringUTFChars(env, file_name, 0);
    const char * fpath = (*env)->GetStringUTFChars(env, file_path, 0);
    const char * rpath = (*env)->GetStringUTFChars(env, r_path, 0);
    //printf("file: %s\n fpath: %s\n rpath: %s\n", file, fpath, rpath);

    int pvp = (int) vs;
    uint32 limit = (uint32) iterations;
    FILE * input, * output;
    char * name;
    uint32 ID=0, N;
    struct Codemon_pkg * pkg;

    input = fopen(fpath, "r");
    name = getName(file);
    pkg = createPkg(input, name);

    switch(pvp)
    {
        case 2:
            ID = runPvP(pkg, 2);
            printf("%d\n", ID);
            break;
        case 3:
            ID = runPvP(pkg, 3);
            printf("%d\n", ID);
            break;
        case 4:
            ID = runPvP(pkg, 4);
            printf("%d\n", ID);
            break;
    }

    free(pkg);
    free(name);
    fclose(input);
    (*env)->ReleaseStringUTFChars(env, file_name, file);
  }

  JNIEXPORT jint JNICALL Java_Codemon_getReport
  (JNIEnv *env, jobject obj, jstring file_name, jstring reportID)
  {
    const char * file = (*env)->GetStringUTFChars(env, file_name, 0);
    const char * report = (*env)->GetStringUTFChars(env, reportID, 0);
    //printf("file: %s\n", file);
    int ID = atoi(report);
    uint32 N;
    FILE * output;
    output = fopen(file, "w");
    
    N = getReport(ID, output);
    
    (*env)->ReleaseStringUTFChars(env, file_name, file);
    (*env)->ReleaseStringUTFChars(env, reportID, report);
    fclose(output);
    return N;
  }