Working example for https://github.com/quarkusio/quarkus/issues/43078


# reactive-postgre-local-error

This project uses Quarkus trying a larger native query against the postgresql database.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Query in pure sql/postgresql

Can be found in [native.sql](./native.sql)

## Failure

When running the scheduler in the java program the hibernate postgre dialect fails with this

```log
2024-09-06 12:51:38,031 ERROR [io.qua.sch.com.run.StatusEmitterInvoker] (vert.x-eventloop-thread-2) Error occurred while executing task for trigger IntervalTrigger [id=1_MemoryCache#updateCache, interval=5000]: java.util.concurrent.CompletionException: java.lang.NullPointerException: Cannot invoke "String.hashCode()" because "<local6>" is null
        at java.base/java.util.concurrent.CompletableFuture.encodeThrowable(CompletableFuture.java:332)
        at java.base/java.util.concurrent.CompletableFuture.completeThrowable(CompletableFuture.java:347)
        at java.base/java.util.concurrent.CompletableFuture.uniWhenComplete(CompletableFuture.java:874)
        at java.base/java.util.concurrent.CompletableFuture$UniWhenComplete.tryFire(CompletableFuture.java:841)
        at java.base/java.util.concurrent.CompletableFuture.postComplete(CompletableFuture.java:510)
        at java.base/java.util.concurrent.CompletableFuture.completeExceptionally(CompletableFuture.java:2194)
        at io.smallrye.context.impl.wrappers.SlowContextualConsumer.accept(SlowContextualConsumer.java:21)
        at io.smallrye.mutiny.helpers.UniCallbackSubscriber.onFailure(UniCallbackSubscriber.java:62)
        at io.smallrye.mutiny.operators.uni.UniOperatorProcessor.onFailure(UniOperatorProcessor.java:55)
        at io.smallrye.mutiny.operators.uni.UniOperatorProcessor.onFailure(UniOperatorProcessor.java:55)
        at io.smallrye.mutiny.operators.uni.UniOnTerminationCall$UniOnTerminationCallProcessor.lambda$onFailure$3(UniOnTerminationCall.java:79)
        at io.smallrye.context.impl.wrappers.SlowContextualConsumer.accept(SlowContextualConsumer.java:21)
        at io.smallrye.mutiny.helpers.UniCallbackSubscriber.onItem(UniCallbackSubscriber.java:73)
        at io.smallrye.mutiny.operators.uni.UniOperatorProcessor.onItem(UniOperatorProcessor.java:47)
        at io.smallrye.mutiny.operators.uni.builders.UniCreateFromCompletionStage$CompletionStageUniSubscription.forwardResult(UniCreateFromCompletionStage.java:63)
        at java.base/java.util.concurrent.CompletableFuture.uniWhenComplete(CompletableFuture.java:863)
        at java.base/java.util.concurrent.CompletableFuture.uniWhenCompleteStage(CompletableFuture.java:887)
        at java.base/java.util.concurrent.CompletableFuture.whenComplete(CompletableFuture.java:2357)
        at java.base/java.util.concurrent.CompletableFuture.whenComplete(CompletableFuture.java:144)
        at io.smallrye.mutiny.operators.uni.builders.UniCreateFromCompletionStage$CompletionStageUniSubscription.forward(UniCreateFromCompletionStage.java:51)
        at io.smallrye.mutiny.operators.uni.builders.UniCreateFromCompletionStage.subscribe(UniCreateFromCompletionStage.java:35)
        at io.smallrye.mutiny.operators.AbstractUni.subscribe(AbstractUni.java:36)
        at io.smallrye.mutiny.operators.uni.UniRunSubscribeOn.lambda$subscribe$0(UniRunSubscribeOn.java:27)
        at org.hibernate.reactive.context.impl.VertxContext.execute(VertxContext.java:91)
        at io.smallrye.mutiny.operators.uni.UniRunSubscribeOn.subscribe(UniRunSubscribeOn.java:25)
        at io.smallrye.mutiny.operators.AbstractUni.subscribe(AbstractUni.java:36)
        at io.smallrye.mutiny.groups.UniSubscribe.withSubscriber(UniSubscribe.java:51)
        at io.smallrye.mutiny.groups.UniSubscribe.with(UniSubscribe.java:110)
        at io.smallrye.mutiny.groups.UniSubscribe.with(UniSubscribe.java:88)
        at io.smallrye.mutiny.operators.uni.UniOnTerminationCall$UniOnTerminationCallProcessor.onFailure(UniOnTerminationCall.java:78)
        at io.smallrye.mutiny.operators.uni.UniOnTermination$UniOnTerminationProcessor.onFailure(UniOnTermination.java:52)
        at io.smallrye.mutiny.operators.uni.UniOperatorProcessor.onFailure(UniOperatorProcessor.java:55)
        at io.smallrye.mutiny.operators.uni.UniOperatorProcessor.onFailure(UniOperatorProcessor.java:55)
        at io.smallrye.mutiny.operators.uni.builders.UniCreateFromCompletionStage$CompletionStageUniSubscription.forwardResult(UniCreateFromCompletionStage.java:58)
        at java.base/java.util.concurrent.CompletableFuture.uniWhenComplete(CompletableFuture.java:863)
        at java.base/java.util.concurrent.CompletableFuture$UniWhenComplete.tryFire(CompletableFuture.java:841)
        at java.base/java.util.concurrent.CompletableFuture.postComplete(CompletableFuture.java:510)
        at java.base/java.util.concurrent.CompletableFuture.complete(CompletableFuture.java:2179)
        at io.vertx.core.Future.lambda$toCompletionStage$3(Future.java:581)
        at io.vertx.core.impl.future.FutureImpl$4.onSuccess(FutureImpl.java:176)
        at io.vertx.core.impl.future.FutureBase.emitSuccess(FutureBase.java:66)
        at io.vertx.core.impl.future.FutureImpl.tryComplete(FutureImpl.java:259)
        at io.vertx.sqlclient.impl.QueryResultBuilder.tryComplete(QueryResultBuilder.java:88)
        at io.vertx.sqlclient.impl.QueryResultBuilder.tryComplete(QueryResultBuilder.java:32)
        at io.vertx.core.Promise.complete(Promise.java:66)
        at io.vertx.core.Promise.handle(Promise.java:51)
        at io.vertx.core.Promise.handle(Promise.java:29)
        at io.vertx.core.impl.future.FutureImpl$4.onSuccess(FutureImpl.java:176)
        at io.vertx.core.impl.future.FutureBase.lambda$emitSuccess$0(FutureBase.java:60)
        at io.vertx.core.impl.ContextImpl.execute(ContextImpl.java:298)
        at io.vertx.core.impl.DuplicatedContext.execute(DuplicatedContext.java:171)
        at io.vertx.core.impl.future.FutureBase.emitSuccess(FutureBase.java:57)
        at io.vertx.core.impl.future.FutureImpl.tryComplete(FutureImpl.java:259)
        at io.vertx.core.impl.future.PromiseImpl.onSuccess(PromiseImpl.java:49)
        at io.vertx.core.impl.future.PromiseImpl.handle(PromiseImpl.java:41)
        at io.vertx.core.impl.future.PromiseImpl.handle(PromiseImpl.java:23)
        at io.vertx.sqlclient.impl.command.CommandResponse.fire(CommandResponse.java:46)
        at io.vertx.sqlclient.impl.SocketConnectionBase.handleMessage(SocketConnectionBase.java:324)
        at io.vertx.pgclient.impl.PgSocketConnection.handleMessage(PgSocketConnection.java:114)
        at io.vertx.sqlclient.impl.SocketConnectionBase.lambda$init$0(SocketConnectionBase.java:137)
        at io.vertx.core.impl.ContextImpl.emit(ContextImpl.java:328)
        at io.vertx.core.impl.ContextImpl.emit(ContextImpl.java:321)
        at io.vertx.core.net.impl.NetSocketImpl.handleMessage(NetSocketImpl.java:388)
        at io.vertx.core.net.impl.ConnectionBase.read(ConnectionBase.java:159)
        at io.vertx.core.net.impl.VertxHandler.channelRead(VertxHandler.java:153)
        at io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:442)
        at io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:420)
        at io.netty.channel.AbstractChannelHandlerContext.fireChannelRead(AbstractChannelHandlerContext.java:412)
        at io.netty.channel.CombinedChannelDuplexHandler$DelegatingChannelHandlerContext.fireChannelRead(CombinedChannelDuplexHandler.java:436)
        at io.vertx.pgclient.impl.codec.PgDecoder.fireCommandResponse(PgDecoder.java:52)
        at io.vertx.pgclient.impl.codec.PgCommandCodec.handleReadyForQuery(PgCommandCodec.java:137)
        at io.vertx.pgclient.impl.codec.PgDecoder.decodeReadyForQuery(PgDecoder.java:248)
        at io.vertx.pgclient.impl.codec.PgDecoder.channelRead(PgDecoder.java:107)
        at io.netty.channel.CombinedChannelDuplexHandler.channelRead(CombinedChannelDuplexHandler.java:251)
        at io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:442)
        at io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:420)
        at io.netty.channel.AbstractChannelHandlerContext.fireChannelRead(AbstractChannelHandlerContext.java:412)
        at io.netty.channel.DefaultChannelPipeline$HeadContext.channelRead(DefaultChannelPipeline.java:1410)
        at io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:440)
        at io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:420)
        at io.netty.channel.DefaultChannelPipeline.fireChannelRead(DefaultChannelPipeline.java:919)
        at io.netty.channel.nio.AbstractNioByteChannel$NioByteUnsafe.read(AbstractNioByteChannel.java:166)
        at io.netty.channel.nio.NioEventLoop.processSelectedKey(NioEventLoop.java:788)
        at io.netty.channel.nio.tLoop.processSelectedKeysOptimized(NioEventLoop.java:724)
        at io.netty.channel.nio.NioEventLoop.processSelectedKeys(NioEventLoop.java:650)
        at io.netty.channel.nio.NioEventLoop.run(NioEventLoop.java:562)
        at io.netty.util.concurrent.SingleThreadEventExecutor$4.run(SingleThreadEventExecutor.java:997)
        at io.netty.util.internal.ThreadExecutorMap$2.run(ThreadExecutorMap.java:74)
        at io.netty.util.concurrent.FastThreadLocalRunnable.run(FastThreadLocalRunnable.java:30)
        at java.base/java.lang.Thread.run(Thread.java:1583)
Caused by: java.lang.NullPointerException: Cannot invoke "String.hashCode()" because "<local6>" is null
        at org.hibernate.dialect.PostgreSQLDialect.resolveSqlTypeDescriptor(PostgreSQLDialect.java:316)
        at org.hibernate.sql.results.jdbc.internal.ResultSetAccess.resolveType(ResultSetAccess.java:93)
        at org.hibernate.reactive.sql.results.internal.ReactiveDeferredResultSetAccess.resolveType(ReactiveDeferredResultSetAccess.java:110)
        at org.hibernate.query.results.implicit.ImplicitResultClassBuilder.buildResult(ImplicitResultClassBuilder.java:59)
        at org.hibernate.query.results.ResultSetMappingImpl.resolve(ResultSetMappingImpl.java:229)
        at org.hibernate.reactive.sql.results.ReactiveResultSetMapping.lambda$reactiveResolve$0(ReactiveResultSetMapping.java:62)
        at java.base/java.util.concurrent.CompletableFuture$UniApply.tryFire(CompletableFuture.java:646)
        ... 54 more
```

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell script
./mvnw compile quarkus:dev
```

or

```shell script
mvn compile quarkus:dev
```

> **_NOTE:_** Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Packaging and running the application

The application can be packaged using:

```shell script
./mvnw package
```

or

```shell script
mvn package
```

It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:

```shell script
./mvnw package -Dquarkus.package.jar.type=uber-jar
```

or

```shell script
mvn package -Dquarkus.package.jar.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using:

```shell script
./mvnw package -Dnative
```

or

```shell script
mvn package -Dnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using:

```shell script
./mvnw package -Dnative -Dquarkus.native.container-build=true
```

or

```shell script
mvn package -Dnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/reactive-postgre-local-error-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.

## Related Guides

- Scheduler ([guide](https://quarkus.io/guides/scheduler)): Schedule jobs and tasks
- Reactive PostgreSQL client ([guide](https://quarkus.io/guides/reactive-sql-clients)): Connect to the PostgreSQL database using the reactive pattern
