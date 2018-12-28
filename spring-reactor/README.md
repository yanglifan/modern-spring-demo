# 实现原理

示例：

```java
Flux.just("tom", "jack", "allen")
    .map(s -> s.concat("@qq.com"))
    .subscribe(System.out::println);
```



## 声明阶段

用 `FluxArray` 表示 `Flux.fromArray` 操作，用 `FluxMap/FluxMapFuseable` 表示 `flux.map` 操作。每个 `FluxArray`、`FluxMap/FluxMapFuseable` 都是 `FluxOperator` 的子类。`FluxOperator` 有一个 `source` 成员变量，用于指向上一个 `Flux`。

## subscribe 阶段

看一下 `FluxMapFuseable`  中 `subscribe` 方法的实现：

```java
public void subscribe(CoreSubscriber<? super R> actual) {
	if (actual instanceof ConditionalSubscriber) {
		ConditionalSubscriber<? super R> cs = (ConditionalSubscriber<? super R>) actual;
		source.subscribe(new MapFuseableConditionalSubscriber<>(cs, mapper));
		return;
	}
	source.subscribe(new MapFuseableSubscriber<>(actual, mapper));
}
```

`source` 是 `FluxArray`。将最终的 subscriber 和 mapper 封装为一个新 subscriber，然后订阅上一个 Flux。

## onSubscribe 阶段

FluxArray -> MapFuseableSubscriber -> LambdaSubscriber

FluxArray subscribe 调用 onSubscribe

```java
public void onSubscribe(Subscription s) {
	if (Operators.validate(this.s, s)) {
		this.s = (QueueSubscription<T>) s;
		actual.onSubscribe(this);
	}
}
```

`actual` 就是被封装为 `LambdaSubscriber` 的 `System.out::println`

## request 阶段

LambdaSubscriber -> MapFuseableSubscriber -> FluxArray 

## onNext 阶段

再反过来