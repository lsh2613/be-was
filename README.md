# 📖 TIL (1/17) 📖
# 자바 쓰레드 모델

JDK 5 이전에는 Thread 클래스와 Runnable 인터페이스를 활용하여 스레드를 다뤘지만 JDK 5부터 도입된 java.util.concurrent 패키를 통해 더욱 간편하게 병렬 프로그래밍을 지원하게 되었다.

## Thread VS Runnable

Thread는 클래스로 상속받아 사용해야 하며 해당 Thread가 수행할 run()메소드를 오버라이딩 하여 사용한다.

반면, Runnable은 인터페이스로 Thread에서 사용하는 run()을 Functional Interface로 구현하여 재사용성이 높고, 코드의 일관성을 유지할 수 있어서 Thread보다 더 효율적인 방법이다.

> Functional Interface
>
> - 함수형 인터페이스로 추상 메서드가 오직 하나인 인터페이스를 의미
> - 람다를 사용할 수 있어 편리함

## Thread, Runnable VS Concurrent

Thread 클래스는 기본적인 스레드 지원을 제공하지만, 스레드 생성, 시작, 중지 등의 저수준 작업을 직접 처리해야 합니다. 반면에 Concurrent 패키지는 Executor 프레임워크, 스레드 풀, 스레드 안전한 컬렉션 등 다양한 도구와 클래스를 제공하여 병렬 프로그래밍을 더욱 편리하게 지원합니다.

Concurrent 패키지는 고수준의 스레드 관리 기능을 제공하여 스레드 간의 동기화와 상호 작용을 보다 쉽게 처리할 수 있다.

## Concurrent 패키지

### 주요 기능

- Locks : 상호 배제를 사용할 수 있는 클래스를 제공한다.
- Atomic : 동기화가 되어있는 변수를 제공한다.
- Executors : 쓰레드 풀 생성, 쓰레드 생명주기 관리, Task 등록과 실행 등을 간편하게 처리할 수 있다.
- Queue : thread-safe한 FIFO 큐를 제공한다.
- Synchronizers : 특수한 목적의 동기화를 처리하는 5개의 클래스를 제공한다. (Semaphroe, CountDownLatch, CyclicBarrier, Phaser, Exchanger)

## Excecutor, ExcecutorService, Excecutors

모두 스레드의 생성과 관리를 추상화하여 멀티스레드 애플리케이션을 보다 쉽게 구현할 수 있게 도와준다.

## Excecutor

가장 기본적인 인터페이스로, 단일 메소드 `execute(Runnable command)`를 통해 새 작업(Runnable 객체)을 실행하기 위한 방법을 제공

## ExcecutorService

Executor를 확장한 인터페이스로, 스레드 풀을 포함한 더 복잡한 서비스를 관리하기 위한 메소드를 추가로 정의

## Excecutors

유틸리티 클래스로, Executor, ExecutorService, ScheduledExecutorService 등을 쉽게 생성할 수 있는 정적 팩토리 메소드들을 제공합니다. 이 클래스를 사용하면 개발자는 복잡한 스레드 풀을 직접 구현하지 않고도 간단하게 멀티스레딩 환경을 설정할 수 있도록 도와줌