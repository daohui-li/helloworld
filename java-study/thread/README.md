# Thread studies

### Basic techniques
1. Use _Thread_ (or _Runnable_) directly (see _thread_ package)
 - Remember to use _sychchronized_, if a method or property can be accessed by multiple threads
 - Avoid deadlock (as shown in _Basics_)
 - Use second parameter as guard to avoid deadlock
 - Alternatively, using _Lock_, such as _ReentrantLock_ (see _FriendWithLock_)
2. Use library's classes:
 - Atomic types (e.g. _AtomicInteger_)
 - _BlockingQueue_ (e.g. _LinkedBlockingDequeue_)
 - _ScheduledExecutorService_ and _Executors_ is the factory to create the _Thread_ pool
 - Use Fork and Join framework: two types of calculations
   * calculation returns a value: _RecursiveTask<Type>_ (see _FibonacciTask)
   * calculation without returning a value: _RecursiveAction_ (see _SortAction_)
   