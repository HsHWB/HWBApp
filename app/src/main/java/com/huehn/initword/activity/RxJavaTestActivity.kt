package com.huehn.initword.activity

import android.annotation.SuppressLint
import android.content.res.AssetManager
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.TextView
import com.google.gson.Gson
import com.huehn.initword.R
import com.huehn.initword.basecomponent.base.BaseActivity
import com.huehn.initword.bean.*
import com.huehn.initword.core.app.App
import com.huehn.initword.core.utils.Rxjava.RxJavaUtils
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function
import io.reactivex.functions.Function3
import io.reactivex.internal.operators.observable.ObservableCreate
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import java.io.File
import java.util.concurrent.TimeUnit

class RxJavaTestActivity : BaseActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rxjava)

        var textView = findViewById(R.id.textview) as TextView
        textView.setOnClickListener {
//            baseRxJavaUse()
//            mapRxJavaUse()
//            flatMapRxJavaUser()
//            flatMapRxJavaUser2()
//            contactMapRxJavaUser()
//            concatRxJavaUser()
//            mergeRxJavaUser()
//            zipRxJavaUser()
//            timerRxJavaUse()
//            intervalRxJavaUse()
            maxRxJavaUse()
        }

        var textView2 = findViewById(R.id.textview2) as TextView
        textView2.setOnClickListener {
            subscription?.run { this.request(64) }
        }
    }

    private var subscription : Subscription? = null

    fun maxRxJavaUse(){
        Flowable.create<Int>({
            emitter ->  for (i in 0..1000) {
//                Thread.sleep(1)
                emitter.onNext(i)
            }
        }, BackpressureStrategy.DROP)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Subscriber<Int>{
                override fun onComplete() {
                    println("maxRxJavaUse onComplete")
                }

                override fun onSubscribe(s: Subscription?) {
                    this@RxJavaTestActivity.subscription = s
                }

                override fun onNext(t: Int?) {
                    println(t)
                }

                override fun onError(t: Throwable?) {
                    t?.printStackTrace()
                }

            })
    }


    fun timerRxJavaUse(){
        Observable.timer(2, TimeUnit.SECONDS)
                .observeOn(Schedulers.io())
                .subscribe(object : Observer<Long>{
                    override fun onComplete() {
                        println("onComplete")
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: Long) {
                        println(t)
                    }

                    override fun onError(e: Throwable) {
                    }

                })
    }
    fun intervalRxJavaUse(){
        Observable.interval(0, 2, TimeUnit.SECONDS)
                .take(10)//次数
                .observeOn(Schedulers.io())
                .subscribe(object : Observer<Long>{
                    override fun onComplete() {
                        println("onComplete")
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: Long) {
                        println(t)
                    }

                    override fun onError(e: Throwable) {
                    }

                })
    }

    fun getPersonJson() : PersonData{
        App.getApp().assets.open("person_json.json").bufferedReader().run {
            var data : String? = this.readLine()
            var buffer : StringBuffer = StringBuffer()
            while (data != null){
                buffer.append(data)
                data = this.readLine()
            }
            var jsonObject : JSONObject = JSONObject(buffer.toString())
            var gson : PersonData = Gson().fromJson(jsonObject.toString(), PersonData::class.java)
            return gson
        }

    }

    //"person_json.json"
    fun <T> getPersonJson(clazz : Class<T>, path : String) : T{
        App.getApp().assets.open(path).bufferedReader().run {
            var data : String? = this.readLine()
            var buffer : StringBuffer = StringBuffer()
            while (data != null){
                buffer.append(data)
                data = this.readLine()
            }
            var jsonObject : JSONObject = JSONObject(buffer.toString())
            var gson : T = Gson().fromJson(jsonObject.toString(), clazz)
            return gson
        }

    }

    /**
     * 最基础的用法
     */
    fun baseRxJavaUse(){
        var observable : Disposable = Observable.create<PersonData>(ObservableOnSubscribe<PersonData>{
            emitter: ObservableEmitter<PersonData> ->
                emitter.onNext(getPersonJson())
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    it.person.forEach {
                        (name, age) -> println("huehn Person data : $name, $age")
                    }
                }, {
                    it.printStackTrace()
                })

//        var observable2 : Observable<String> = Observable.create{
////         create(((emitter: ObservableEmitter<String!>) → Unit)!)
//            //(emitter: ObservableEmitter<String!>) → Unit 是否等于 void subscribe(@NonNull ObservableEmitter<T> emitter) throws Exception;
//            //发射器，事件源，这个是SAM转换,ObservableOnSubscribe
//            //这个java接口里面只有一个抽象方法void subscribe(@NonNull ObservableEmitter<T> emitter)
//
//        }

    }

    fun concatRxJavaUser(){
        var personData1 : List<PersonDetail> = getPersonJson(PersonData::class.java, "person_json.json").person
        var personData2 : List<PersonDetail> = getPersonJson(PersonData::class.java, "person_json2.json").person
        var observable: Observable<List<PersonDetail>> = Observable.just(personData1)
        var observable2: Observable<List<PersonDetail>> = Observable.just(personData2)

        Observable.concat(observable, observable2).subscribe {
            println(it)
        }


    }


    fun mergeRxJavaUser() {
        var personData1: List<PersonDetail> = getPersonJson(PersonData::class.java, "person_json.json").person
        var personData2: List<PersonDetail> = getPersonJson(PersonData::class.java, "person_json2.json").person
        var observable: Observable<List<PersonDetail>> = Observable.just(personData1)
        var observable2: Observable<List<PersonDetail>> = Observable.just(personData2)

//        var observable: Observable<List<PersonDetail>> = Observable.create<List<PersonDetail>>
//                            { emitter: ObservableEmitter<List<PersonDetail>> -> (personData1)}.doOnNext({})
//        var observable2: Observable<List<PersonDetail>> = Observable.create<List<PersonDetail>>
//        { emitter: ObservableEmitter<List<PersonDetail>> -> (personData2)}.doOnNext({})

        Observable.merge(observable.delay(1000, TimeUnit.MILLISECONDS), observable2.delay(1000, TimeUnit.MILLISECONDS))
                .observeOn(Schedulers.io())
                .subscribe {
                     it.forEach{
                         println("huehn name : ${it.name}, age : ${it.age}")
                     }

                }
    }

    fun zipRxJavaUser(){
        var personData1: List<PersonDetail> = getPersonJson(PersonData::class.java, "person_json.json").person
        var personData2: List<PersonDetail> = getPersonJson(PersonData::class.java, "person_json2.json").person
        var roleData : List<RoleDetail> = getPersonJson(RoleData::class.java, "role_json.json").data
        var observable: Observable<List<PersonDetail>> = Observable.just(personData1)
        var observable2: Observable<List<PersonDetail>> = Observable.just(personData2)
        var observableRole: Observable<List<RoleDetail>> = Observable.just(roleData)

        Observable.zip(observable, observable2, observableRole, Function3<List<PersonDetail>, List<PersonDetail>, List<RoleDetail>, MultableData> {
            l1, l2, l3 ->
                println("1")
            var multableData : MultableData = MultableData(l1, l2, l3)
                return@Function3 multableData
        }).subscribe({
                it ->
                println("2")
                var list1 = it.personDetail1
                var list2 = it.personDetail2
                var listRole = it.roleDetail
                println(list1)
                println(list2)
                println(listRole)
        }, {

        })
    }


    fun flatMapRxJavaUser() {
        var observable: Disposable =
        Observable.create<PersonData> { it ->
            it.onNext(getPersonJson())
        }.flatMap(
            object : Function<PersonData, Observable<PersonDetail>> {
                override fun apply(t: PersonData): Observable<PersonDetail> {
                    return Observable.fromIterable(t.person)
                }

        }).subscribe({
            println("flatMapRxJavaUser : ${it.name}")
//            println(it.name)
        }, {
            it.printStackTrace()
        })
    }

    fun flatMapRxJavaUser2() {
        var list = mutableListOf<PersonData>(getPersonJson(), getPersonJson())
        var observable: Disposable = Observable.create<PersonData> {
            emitter ->
            list.forEach {
                emitter.onNext(it)
            }
        }.flatMap(
                object : Function<PersonData, Observable<PersonDetail>> {
                    override fun apply(t: PersonData): Observable<PersonDetail> {
                        return Observable.fromIterable(t.person).delay(1000, TimeUnit.MILLISECONDS)
                    }

                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    println("flatMapRxJavaUser : ${it.name}")
                }, {
                    it.printStackTrace()
                })
    }

    fun contactMapRxJavaUser() {
        var list = mutableListOf<PersonData>(getPersonJson(), getPersonJson(), getPersonJson(), getPersonJson(), getPersonJson())
        var observable: Disposable = Observable.fromIterable(list).concatMap(
                object : Function<PersonData, Observable<PersonDetail>> {
                    override fun apply(t: PersonData): Observable<PersonDetail> {
                        return Observable.fromIterable(t.person)
                    }

                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    println("contactMapRxJavaUser : ${it.name}")
                }, {
                    it.printStackTrace()
                })
    }

    fun mapRxJavaUse() {
        var observable: Disposable =
                Observable.create<PersonData>(ObservableOnSubscribe<PersonData> { emitter: ObservableEmitter<PersonData> ->
                    emitter.onNext(getPersonJson())
                }).map({
                    it.person[0]
                }
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    println("mapRxJavaUse : ${it.age}")
                }, {

                })
    }
//        var observable : Disposable =
//                Observable.create<PersonData>(ObservableOnSubscribe<PersonData>{
//                    emitter: ObservableEmitter<PersonData> ->
//                    emitter.onNext(getPersonJson())
//                }).flatMap(
//                    Function<PersonData, Observable<PersonDetail>> {
//                        Observable.create { emitter -> emitter.onNext(it.person[0]) }
//                    }
//                )
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({
//                    println("mapRxJavaUse : ${it.age}")
//                }, {
//
//                })
//    }

//        var mCompositeDisposable : CompositeDisposable = CompositeDisposable()
//        mCompositeDisposable.add(Observable.interval(1000, TimeUnit.MILLISECONDS)
//                .subscribeOn(Schedulers.computation())
//                .observeOn(Schedulers.computation())
//                .map(
//                    object : Function<Long, PersonDetail> {
//                        override fun apply(t: Long): PersonDetail {
//                            return PersonDetail("A", "28")
//                        }
//                    }
//                ).subscribe({
//                    println(it.name)
//                }, {
//                    it.printStackTrace()
//                }))
//                .subscribe { food ->
//                    KLog.d(TAG, "[xyj][CPUEngine][CPU] " + food)
//                })
//    }

}
//var observable : Disposable = Observable.create<PersonData>(ObservableOnSubscribe<PersonData>{
//    emitter: ObservableEmitter<PersonData> ->
//    emitter.onNext(getPersonJson())
//}).map {
//    object : Function<PersonData, PersonDetail>{
//        override fun apply(t: PersonData): PersonDetail {
////                    return it.takeIf { it.person.isNotEmpty() }.run {
////                        it.person[0]
////                    }
//            return it.person[0]
//        }
//    }
//}.subscribeOn(Schedulers.io())
//        .observeOn(AndroidSchedulers.mainThread())
//        .subscribe({
////                    println("huehn Person data : ${it.}, ${it.}")
//////                    it.person.forEach {
//////                        (name, age) -> println("huehn Person data : $name, $age")
//////                    }
//        }, {
//            it.printStackTrace()
//        })
//}

