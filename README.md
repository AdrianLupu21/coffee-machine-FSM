# coffee-machine-FSM
A Finite State Machine modeled after a coffee vending machine.
### Tech stack:

* [Maven](https://maven.apache.org/)
* [SLF4J](https://www.slf4j.org/)
* [JUnit 5](https://junit.org/junit5/)
* [JDK 11](https://www.oracle.com/java/technologies/javase/jdk11-archive-downloads.html)

<p>The program runs inside a loop until the user enter the "exit" command. There are 3 threads running in parallel, the main thread,
a thread used to check the state of the ingredients, and a thread used to process user commands. A publish-subscribe design pattern was used
for the communication between the main thread and the thread used for command processing. There are two queues used for commands and arguments.
The main thread receives a command from the console. The command is published to the queue and then the processor thread will consume the command.
After the command was received, the processor thread will enter in a sleep mode and wait for an argument to that command. When the main thread receives
an argument from the user it will publish it to the arguemnt queue and notify the processor thread to wake up. Each interaction with the program is done
using this mechanism.</p>

<h1>Finite States diagram</h1>

![FSM Diagram](https://i.ibb.co/YhNJ7cf/Automat-cafea.png)



<h1>States of the machine</h1>
<p>This concept of a vending machine is modeled in such a way that the user will always be able to receive the change.
If the machine is out of banknotes, it will print a receipt which can be used to buy other products.
The intial state, IDLE, is altered when the user chooses a product, after which he will be prompted to select the sugar quantity.
In order to start the preparation process, the user must insert credit (banknotes/card/receipt) into the machine. After the product
was retrieved from the exit hatch, the machin will go back to IDLE. Also, to keep in mind is the fact that the user will receive
change after he paid with banknotes.</p>

![FSM Table](https://i.ibb.co/tL04Q2S/fsm-table.png)
