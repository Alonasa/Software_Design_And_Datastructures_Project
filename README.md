## The Sørensen-Dice application
The Sørensen-Dice similarity console application computes two text documents. The user selects Subject file and Query file, and an optional output path through a menu-driven user interface. The program changes both files into presence-only word sets, calculates the Dice similarity, provides a clear summary, and saves a formatted report to disk.
Subject and Query files require the full path, or the user will receive the error.

### Key Features
- Menu-based console user interface for file input and execution(Used from the previous project)
- Concurrent processing of Subject and Query using virtual threads (Java 21)
- Callable tasks for clean control of concurrency
- Thread-safe token storage using ConcurrentHashMap
- Word tokenisation: lowercase, punctuation removed (UTF‑8 I/O, buffered)
- Sørensen-Dice similarity on presence-only sets: dice = (2 × |A ∩ B|) / (|A| + |B|)
- Report includes file paths, unique token counts, intersection size, and percentage similarity

### Architecture (package ie.atu.sw)
- Runner/Main: entry which render menu
- Menu: offer 5 options and based on the selected process, the action
- ThreadsProcessor: call 2 Callable tasks on a virtual-thread-per-task executor. Compute and write the report to the selected file or ./out.txt
- FilesProcessor: handles file I/O, tokenisation, and report formatting/writing
- SimilarityProcessor: manages intersection and Dice utilities
- Helpers: UtilMethods, ConsoleColour, ProgressBar

### How It Works
1) User provides file paths through the menu.
2) Two Callable<Map<String,Integer>> tasks index the files concurrently on virtual threads.
3) The app computes unique token counts, intersection size, and Dice similarity.
4) Results are printed and saved to the specified report file.

### Build and Run
- Build JAR: jar -cf dice.jar *
- Run: java -cp .\dice.jar ie.atu.sw.Runner

Requirements and Notes
- Java 21 (virtual threads) recommended
- The application is very simple and based on the beginner level programming skills

