package com.cqcb.chuizi.rxbus;



import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;


/**
 * 没有背压处理的rxbus 基于Rxjava2  Subject<Object> bus
 *
 *
 * Created by zaxcler on 2017/3/3.
 */

public class RxBus {

    private static volatile RxBus mInstance;

    private final Subject<Object> bus;


    public RxBus()
    {
        bus = PublishSubject.create().toSerialized();
    }

    /**
     * 单例模式RxBus
     *
     * @return
     */
    public static RxBus get()
    {

        if (mInstance == null)
        {
            synchronized (RxBus.class)
            {
                if (mInstance == null)
                {
                    mInstance = new RxBus();
                }
            }
        }

        return mInstance;
    }


    /**
     * 发送消息
     * 只要注册对象是同一个类，所有注册了该类的订阅都会收到
     *
     * @param object
     */
    public void post(Object object)
    {

        bus.onNext(object);

    }

    /**
     * 接收消息
     * 按照类来进行区分
     * @param eventType
     * @param <T>
     * @return
     */
    public <T> Observable<T> toObserverable(Class<T> eventType)
    {
        return bus.ofType(eventType);
    }


    /**
     * 提供了一个新的事件,根据code进行分发
     * @param code 事件code
     * @param o
     */
    public void post(int code, Object o){
        bus.onNext(new RxMessage(code,o));

    }


    /**
     *
     * 根据传递的code和 eventType 类型返回特定类型(eventType)的 被观察者
     * @param code 事件code
     * @param eventType 事件类型
     * @param <T>
     * @return
     */
    public <T> Observable<T> toObserverable(final int code, final Class<T> eventType) {
        return bus.ofType(RxMessage.class).filter(new Predicate<RxMessage>() {
            @Override
            public boolean test(@NonNull RxMessage o) throws Exception {
               return o.getCode() == code;
            }
        }).map(new Function<RxMessage, Object>() {
            @Override
            public Object apply(@NonNull RxMessage o) throws Exception {
                return o.getObject();
            }
        }).cast(eventType);
    }

}