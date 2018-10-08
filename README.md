# FastaFileReader
FastaFileReader helps to process the fasta files

## Running the application
Application expects two input params.

First argument is comma seperated file names to be processed.

Second argument is an Integer, it is the number of threads in the pool.

### Input validation

Application validates the input arguments, application silently returns if the required number of arguments are not provided.

Application takes the default value of 1 if invalid input is provided for the second argument.

Application validates the input file names and generates REPORT.TXT with default value of 0 for the FILE_CNT,SEQUENCE_CNT	and BASE_CNT if there is no valid input file is provided.

Application will try to process the input file if it is a valid gzip file. If the input is not a gzip file, that file will be ignored.
For example, if two input files are provided out of which one is valid gzip file and other one is an invalid file. After completing the execution, in REPORT.TXT we will see file count as one instead of two as the second file is an invalid file. 

### Assumptions

* Not handling the comments in input fasta files
* Not used any logging frameworks
