Threeterson Lock and Paper Marker Simulation
This project simulates a concurrent marking process using a custom lock mechanism (Threeterson lock), where three threads (markers) work on marking papers concurrently. It ensures thread synchronization using the lock to prevent race conditions and ensure fair access to shared resources.

Components
1. Threeterson Lock
A custom implementation of a Lock using a variant of Peterson's algorithm for three threads. This lock ensures that only one thread can access the critical section at a time, preventing race conditions. It uses a busy-wait mechanism with some backoff to avoid excessive CPU usage.

2. Paper Class
Represents a paper with random answers that need to be marked. The answers are compared to a predefined memo, and the paper receives a score based on the number of correct answers.

3. Marker Threads
Three threads (represented by the Marker class) attempt to mark papers concurrently. Each thread locks the Threeterson lock, marks a paper, and then releases the lock for the next thread.

4. Output
The output class collects log messages generated by the threads during the marking process. The logs include messages related to the lock acquisition, thread activities, and paper marking.

5. OutputValidator
Validates the log output to ensure that the Threeterson lock was used correctly and no race conditions occurred.


Compile and run:
javac *.java
java Main

Main Flow
The Main class sets up the simulation with 3 Marker threads and a queue of Paper objects.
Each marker locks the Threeterson lock to mark a paper.
The Output class captures log messages.
After all papers are marked, the OutputValidator checks if the logging and synchronization were done correctly.

Validation
The OutputValidator checks if the output is valid by verifying:

Threads progress through increasing levels.
Only one thread holds the lock at any given time.
The log matches the expected flow of thread actions.
