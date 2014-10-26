performance-webapp
==================

Java web application used for performance testing and analysis presentation/class.

JAX-RS REST service simulates a number of possible bottlenecks:

* /performance-webapp/fast - does nothing, returns thread name in text/plain
* /performance-webapp/sleep/<time> - sleeps for "time" milliseconds
* /performance-webapp/uuid/<n> - generates "n" Random UUIDs
* /performance-webapp/fib/<n> - generate "n"th number in fibonnaci sequence
* /performance-webapp/synch/sleep/<time> - sleeps for "time" milliseconds in synchronized block
* /performance-webapp/synch/uuid/<n> - generates "n" Random UUIDs in synchronized block
* /performance-webapp/synch/fib/<n> - generate "n"th number in fibonnaci sequence in synchronized block
* /performance-webapp/executor/sleep/<time> - sleeps for "time" milliseconds using executor service
* /performance-webapp/executor/uuid/<n> - generates "n" Random UUIDs using executor service
* /performance-webapp/executor/fib/<n> - generate "n"th number in fibonnaci sequence using executor service
* /performance-webapp/deadlock/1 - Lock object 1 then 2
* /performance-webapp/deadlock/2 - Lock object 2 then 1
